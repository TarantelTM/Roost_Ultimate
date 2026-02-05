package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.RoostHandler;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import net.tarantel.chickenroost.networking.SyncAutoOutputPayload;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.RoostRecipe;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class RoostTile extends BlockEntity implements MenuProvider, ICollectorTarget {

    // -------------------------
    // SLOTS
    // -------------------------
    private static final int SEED_SLOT = 0;
    private static final int CHICKEN_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    @Override
    public int getReadSlot() {
        return CHICKEN_SLOT;
    }

    // -------------------------
    // STATE
    // -------------------------
    public int progress = 0;
    public int maxProgress = Config.roost_speed_tick.get() * 20;

    private boolean migrating = false;

    // -------------------------
    // CUSTOM NAME
    // -------------------------
    private String customName = "ROOST";

    public void setCustomName(String name) {
        if (name == null) name = "";
        this.customName = name;
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public String getCustomName() {
        return customName;
    }

    // -------------------------
    // INVENTORY (TRANSFER)
    // -------------------------
    public final UniversalItemInventory inventory = new UniversalItemInventory(
            3,

            // INSERT rules (intern)
            (slot, res) -> switch (slot) {
                case SEED_SLOT -> res.test(s -> s.getItem() instanceof ChickenSeedBase);
                case CHICKEN_SLOT -> res.test(s -> s.getItem() instanceof ChickenItemBase);
                case OUTPUT_SLOT -> true; // intern fürs Crafting
                default -> false;
            },

            // EXTRACT rules (intern)
            slot -> true,

            // SLOT LIMIT
            slot -> switch (slot) {
                case SEED_SLOT -> 64;
                case CHICKEN_SLOT -> 1;
                case OUTPUT_SLOT -> 64;
                default -> 0;
            },

            // CHANGE callback
            slot -> {
                if (!migrating) {
                    setChanged();
                    if (slot == CHICKEN_SLOT) resetProgress();
                    if (level != null && !level.isClientSide()) {
                        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                    }
                }
            }
    );

    /**
     * Automation-View (Hopper / Pipes)
     * - Insert: Seeds + Chicken
     * - Extract: Output only
     */
    public ResourceHandler<ItemResource> getAutomationInventory(@Nullable Direction side) {
        return new ResourceHandler<>() {
            @Override public int size() { return inventory.size(); }
            @Override public ItemResource getResource(int i) { return inventory.getResource(i); }
            @Override public long getAmountAsLong(int i) { return inventory.getAmountAsLong(i); }
            @Override public long getCapacityAsLong(int i, ItemResource r) { return inventory.getCapacityAsLong(i, r); }

            @Override
            public boolean isValid(int i, ItemResource r) {
                return i == SEED_SLOT || i == CHICKEN_SLOT;
            }

            @Override
            public int insert(int i, ItemResource r, int a, TransactionContext tx) {
                if (i != SEED_SLOT && i != CHICKEN_SLOT) return 0;
                return inventory.insert(i, r, a, tx);
            }

            @Override
            public int extract(int i, ItemResource r, int a, TransactionContext tx) {
                if (i != OUTPUT_SLOT) return 0;
                return inventory.extract(i, r, a, tx);
            }
        };
    }

    // -------------------------
    // HELPERS
    // -------------------------
    public ItemStack getRenderStack() {
        return inventory.getStackDirect(CHICKEN_SLOT);
    }

    public void setHandler(net.neoforged.neoforge.items.ItemStackHandler old) {
        for (int i = 0; i < Math.min(old.getSlots(), inventory.size()); i++) {
            inventory.setStackDirect(i, old.getStackInSlot(i));
        }
    }

    private static @Nullable ResourceHandler<ItemResource> getItemHandler(Level level, BlockPos pos, @Nullable Direction side) {
        return level.getCapability(Capabilities.Item.BLOCK, pos, side);
    }

    // -------------------------
    // DATA / MENU
    // -------------------------
    protected final ContainerData data = new ContainerData() {
        @Override public int get(int i) {
            return i == 0 ? progress : maxProgress;
        }
        @Override public void set(int i, int v) {
            if (i == 0) progress = v;
            else maxProgress = v;
        }
        @Override public int getCount() { return 2; }
    };

    public RoostTile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROOST.get(), pos, state);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player p) {
        return new RoostHandler(id, inv, this, data);
    }

    // -------------------------
    // SAVE / LOAD
    // -------------------------
    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);
        inventory.serialize(out);
        out.putInt("roost.progress", progress);
        out.putInt("roost.max_progress", maxProgress);
        out.putString("roost.custom_name", customName);
        out.putBoolean("AutoOutput", autoOutput);
        out.putBoolean("LastRedstonePowered", lastRedstonePowered);
        out.putBoolean("AutoOutputByRedstone", autoOutputByRedstone);
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);
        inventory.deserialize(in);
        progress = in.getIntOr("roost.progress", 0);
        maxProgress = in.getIntOr("roost.max_progress", Config.roost_speed_tick.get() * 20);
        customName = in.getStringOr("roost.custom_name", "ROOST");
        autoOutput = in.getBooleanOr("AutoOutput", false);
        lastRedstonePowered = in.getBooleanOr("LastRedstonePowered", false);
        autoOutputByRedstone = in.getBooleanOr("AutoOutputByRedstone", false);
    }

    // -------------------------
    // DROPS
    // -------------------------
    private boolean dropped = false;

    public boolean hasDropped() {
        return dropped;
    }

    public void markDropped() {
        this.dropped = true;
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.size());
        for (int i = 0; i < inventory.size(); i++) inv.setItem(i, inventory.getStackDirect(i));

        ItemStack block = new ItemStack(ModBlocks.ROOST);
        block.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inv.getItems()));
        Containers.dropContents(Objects.requireNonNull(level), worldPosition, new SimpleContainer(block));
    }

    // -------------------------
    // AUTO OUTPUT
    // -------------------------
    private boolean autoOutput = false;
    private boolean lastRedstonePowered = false;
    private boolean autoOutputByRedstone = false;

    public boolean isAutoOutputEnabled() { return autoOutput; }

    public void setAutoOutputFromGui(boolean enabled) {
        autoOutput = enabled;
        autoOutputByRedstone = false;
        syncToClients();
    }

    //@Override
    public void setAutoOutputClient(boolean enabled) {
        this.autoOutput = enabled;
    }


    private void setAutoOutputFromRedstone(boolean enabled) {
        autoOutput = enabled;
        autoOutputByRedstone = enabled;
        syncToClients();
    }

    private void syncToClients() {
        if (level instanceof ServerLevel sl) {
            PacketDistributor.sendToPlayersTrackingChunk(
                    sl, sl.getChunkAt(worldPosition).getPos(),
                    new SyncAutoOutputPayload(worldPosition, autoOutput)
            );
        }
        setChanged();
    }

    // -------------------------
    // TICK
    // -------------------------
    public static void tick(Level level, BlockPos pos, BlockState state, RoostTile e) {
        if (level.isClientSide()) return;

        ItemStack chicken = e.inventory.getStackDirect(CHICKEN_SLOT);
        if (!chicken.isEmpty() && chicken.getItem() instanceof ChickenItemBase ci) {
            chicken.set(ModDataComponents.CHICKENLEVEL.value(),
                    chicken.getOrDefault(ModDataComponents.CHICKENLEVEL.value(), 0));
            chicken.set(ModDataComponents.CHICKENXP.value(),
                    chicken.getOrDefault(ModDataComponents.CHICKENXP.value(), 0));
        }

        boolean powered = level.hasNeighborSignal(pos);
        if (powered && !e.lastRedstonePowered && !e.autoOutput) e.setAutoOutputFromRedstone(true);
        if (!powered && e.lastRedstonePowered && e.autoOutputByRedstone) e.setAutoOutputFromRedstone(false);
        e.lastRedstonePowered = powered;

        if (hasRecipe(e)) {
            e.progress++;
            if (e.progress >= e.maxProgress) craftItem(e);
        } else e.resetProgress();

        if (e.isAutoOutputEnabled()) tryPushOutputDown(level, pos, state, e);
    }

    // -------------------------
    // OUTPUT PUSH
    // -------------------------
    private static void tryPushOutputDown(Level level, BlockPos pos, BlockState state, RoostTile tile) {
        ItemStack out = tile.inventory.getStackDirect(OUTPUT_SLOT);
        if (out.isEmpty()) return;

        ResourceHandler<ItemResource> below = getItemHandler(level, pos.below(), Direction.UP);
        if (below == null) return;

        try (Transaction tx = Transaction.openRoot()) {
            int moved = below.insert(ItemResource.of(out), out.getCount(), tx);
            if (moved <= 0) return;
            tile.inventory.extract(OUTPUT_SLOT, ItemResource.of(out), moved, tx);
            tx.commit();
        }
    }

    // -------------------------
    // CRAFTING
    // -------------------------
    private static void craftItem(RoostTile e) {
        Level level = e.level;
        if (level == null) return;

        SimpleContainer inv = new SimpleContainer(3);
        for (int i = 0; i < 3; i++) inv.setItem(i, e.inventory.getStackDirect(i));

        Optional<RecipeHolder<RoostRecipe>> recipe =
                level.getServer().getRecipeManager()
                        .getRecipeFor(ModRecipes.ROOST_TYPE.get(), getRecipeInput(inv), level);

        if (recipe.isEmpty()) {
            e.resetProgress();
            return;
        }

        ItemStack chicken = e.inventory.getStackDirect(CHICKEN_SLOT);
        ItemStack seeds = e.inventory.getStackDirect(SEED_SLOT);
        ItemStack result = recipe.get().value().assemble(getRecipeInput(inv), level.registryAccess());

        int add = chicken.has(ModDataComponents.CHICKENLEVEL)
                ? chicken.get(ModDataComponents.CHICKENLEVEL.value()) / 2
                : 0;
        result.setCount(Math.min(result.getMaxStackSize(),
                e.inventory.getStackDirect(OUTPUT_SLOT).getCount() + Math.max(1, add)));

        try (Transaction tx = Transaction.openRoot()) {

            e.inventory.insert(OUTPUT_SLOT, ItemResource.of(result), result.getCount(), tx);

            if (ChickenRoostMod.CONFIG.RoostSeeds && !seeds.isEmpty()) {
                e.inventory.extract(SEED_SLOT, ItemResource.of(seeds), 1, tx);
            }

            tx.commit();
            e.resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    // -------------------------
    // RECIPE HELPERS
    // -------------------------
    public static RecipeInput getRecipeInput(SimpleContainer inv) {
        return new RecipeInput() {
            @Override public @NotNull ItemStack getItem(int i) { return inv.getItem(i).copy(); }
            @Override public int size() { return inv.getContainerSize(); }
        };
    }

    private static boolean hasRecipe(RoostTile e) {
        Level level = e.level;
        if (level == null) return false;

        SimpleContainer inv = new SimpleContainer(3);
        for (int i = 0; i < 3; i++) inv.setItem(i, e.inventory.getStackDirect(i));

        Optional<RecipeHolder<RoostRecipe>> recipe =
                level.getServer().getRecipeManager()
                        .getRecipeFor(ModRecipes.ROOST_TYPE.get(), getRecipeInput(inv), level);

        recipe.ifPresent(r -> e.maxProgress = Config.roost_speed_tick.get() * r.value().time());

        if (recipe.isEmpty()) return false;

        ItemStack out = inv.getItem(OUTPUT_SLOT);
        ItemStack planned = recipe.get().value().output().copy();

        return out.isEmpty()
                || (ItemStack.isSameItemSameComponents(out, planned)
                && out.getCount() < out.getMaxStackSize());
    }

    // -------------------------
    // SYNC
    // -------------------------
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider prov) {
        return saveWithFullMetadata(prov);
    }
}
