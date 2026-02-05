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
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.ContainerHelper;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.BreederHandler;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import net.tarantel.chickenroost.networking.SyncAutoOutputPayload;
import net.tarantel.chickenroost.recipes.BreederRecipe;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.util.Config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class BreederTile extends BlockEntity implements MenuProvider, ICollectorTarget {

    // -------------------------
    // STATE
    // -------------------------
    private boolean migrating = false;

    public int progress = 0;
    public int maxProgress = (Config.breed_speed_tick.get() * 20);

    private static final int CHICKEN_SLOT = 0;

    @Override
    public int getReadSlot() {
        return CHICKEN_SLOT;
    }

    private String customName = "BREEDER";

    public void setCustomName(String name) {
        if (name == null) name = "";
        this.customName = name;
        setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public String getCustomName() {
        return this.customName;
    }

    public ItemStack currentOutput = ItemStack.EMPTY;

    // -------------------------
    // NEW INVENTORY (TRANSFER)
    // -------------------------
    public final UniversalItemInventory inventory = new UniversalItemInventory(
            12,

            // INSERT rules (intern + crafting)
            (slot, res) -> switch (slot) {
                case 0, 2 -> res.test(s -> s.getItem() instanceof ChickenItemBase);
                case 1 -> res.test(s -> s.getItem() instanceof ChickenSeedBase);
                default -> true; // ✅ Output-Slots erlauben Insert (für Crafting!)
            },

            // EXTRACT rules (intern)
            slot -> true,

            // SLOT LIMIT
            slot -> (slot == 0 || slot == 2) ? 1 : 64,

            // CHANGE callback
            slot -> {
                if (!migrating) {
                    setChanged();
                    if (slot == 0 || slot == 2) resetProgress();
                    if (level != null && !level.isClientSide()) {
                        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                    }
                }
            }
    );


    /**
     * GUI/Container-View:
     * Storage bleibt Transfer-Inventory, aber GUI arbeitet oft mit IItemHandler-Slots.
     */
    //public final IItemHandler guiItemHandler = IItemHandler.of(inventory);

    private static @Nullable ResourceHandler<ItemResource> getItemHandler(Level level, BlockPos pos, @Nullable Direction side) {
        return level.getCapability(Capabilities.Item.BLOCK, pos, side);
    }

    // -------------------------
    // MENU/DATA
    // -------------------------
    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> BreederTile.this.progress;
                case 1 -> BreederTile.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> BreederTile.this.progress = value;
                case 1 -> BreederTile.this.maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public int getScaledProgress() {
        int p = progress;
        int mp = this.maxProgress;
        int arrow = 200;
        return mp != 0 && p != 0 ? p * arrow / mp : 0;
    }

    public BreederTile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BREEDER.get(), pos, state);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        // ✅ BreederHandler muss ggfs. statt ItemStackHandler nun guiItemHandler verwenden
        return new BreederHandler(id, inv, this, this.data);
    }

    // -------------------------
    // RENDER HELPERS
    // -------------------------
    public ItemStack getRenderStack1() {
        return inventory.getStackDirect(0);
    }

    public ItemStack getRenderStack2() {
        return inventory.getStackDirect(2);
    }

    public ItemStack getRenderStack3() {
        return inventory.getStackDirect(1);
    }

    // -------------------------
    // SAVE/LOAD
    // -------------------------
    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);

        inventory.serialize(out);

        out.putInt("breeder.progress", this.progress);
        out.putBoolean("AutoOutput", autoOutput);
        out.putBoolean("LastRedstonePowered", lastRedstonePowered);
        out.putBoolean("AutoOutputByRedstone", autoOutputByRedstone);
        out.putString("breeder.custom_name", this.customName);
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);

        inventory.deserialize(in);

        this.customName = in.getStringOr("breeder.custom_name", "BREEDER");
        this.progress = in.getIntOr("breeder.progress", 0);
        this.autoOutput = in.getBooleanOr("AutoOutput", false);
        this.lastRedstonePowered = in.getBooleanOr("LastRedstonePowered", false);
        this.autoOutputByRedstone = in.getBooleanOr("AutoOutputByRedstone", false);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        setChanged();
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
        for (int i = 0; i < inventory.size(); i++) {
            inv.setItem(i, inventory.getStackDirect(i));
        }

        SimpleContainer block = new SimpleContainer(1);
        ItemStack itemStack = new ItemStack(ModBlocks.BREEDER);

        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inv.getItems()));
        block.setItem(0, itemStack.copy());

        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }

    // -------------------------
    // RECIPE HELPERS
    // -------------------------
    private static boolean hasFreeOrStackableSlot(BreederTile e, ItemStack result) {
        for (int slot = 3; slot <= 11; slot++) {
            ItemStack inSlot = e.inventory.getStackDirect(slot);

            if (inSlot.isEmpty()) return true;

            if (ItemStack.isSameItemSameComponents(inSlot, result)
                    && inSlot.getCount() < inSlot.getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }

    public static RecipeInput getRecipeInput(SimpleContainer inventory) {
        return new RecipeInput() {
            @Override
            public @NotNull ItemStack getItem(int index) {
                return inventory.getItem(index).copy();
            }

            @Override
            public int size() {
                return inventory.getContainerSize();
            }
        };
    }

    // -------------------------
    // AUTO OUTPUT / REDSTONE
    // -------------------------
    private boolean autoOutput = false;
    private boolean lastRedstonePowered = false;
    private boolean autoOutputByRedstone = false;

    public boolean isAutoOutputEnabled() {
        return autoOutput;
    }

    public void setAutoOutputEnabled(boolean enabled) {
        this.autoOutput = enabled;
        this.autoOutputByRedstone = false;

        if (level != null && !level.isClientSide()) {
            setChanged(level, worldPosition, getBlockState());
        }
    }

    public void setAutoOutputFromGui(boolean enabled) {
        this.autoOutput = enabled;
        this.autoOutputByRedstone = false;
        syncToClients();
    }

    private void setAutoOutputFromRedstone(boolean enabled) {
        this.autoOutput = enabled;
        this.autoOutputByRedstone = enabled;
        syncToClients();
    }

    private void syncToClients() {
        if (level instanceof ServerLevel serverLevel) {
            PacketDistributor.sendToPlayersTrackingChunk(
                    serverLevel,
                    serverLevel.getChunkAt(worldPosition).getPos(),
                    new SyncAutoOutputPayload(worldPosition, autoOutput)
            );
        }
        setChanged();
    }

    public void setAutoOutputClient(boolean enabled) {
        this.autoOutput = enabled;
    }

    // -------------------------
    // TICK
    // -------------------------
    public static void tick(Level levelL, BlockPos pos, BlockState state, BreederTile e) {
        if (levelL.isClientSide()) return;

        boolean powered = levelL.hasNeighborSignal(pos);

        if (powered && !e.lastRedstonePowered) {
            if (!e.autoOutput) e.setAutoOutputFromRedstone(true);
        }

        if (!powered && e.lastRedstonePowered) {
            if (e.autoOutputByRedstone) e.setAutoOutputFromRedstone(false);
        }

        e.lastRedstonePowered = powered;

        setChanged(levelL, pos, state);

        // SimpleContainer nur fürs Rezept-System (wie bisher)
        SimpleContainer recipeInv = new SimpleContainer(e.inventory.size());
        for (int i = 0; i < e.inventory.size(); i++) {
            recipeInv.setItem(i, e.inventory.getStackDirect(i));
        }

        RecipeManager rm = levelL.getServer().getRecipeManager();

        List<RecipeHolder<BreederRecipe>> recipes =
                rm.recipeMap()
                        .getRecipesFor(
                                ModRecipes.BASIC_BREEDING_TYPE.get(),
                                getRecipeInput(recipeInv),
                                levelL
                        )
                        .toList();

        if (!recipes.isEmpty()) {

            if (e.progress == 0 || e.currentOutput.isEmpty()) {
                e.currentOutput = pickRandomVariant(recipes);

                Optional<RecipeHolder<BreederRecipe>> first =
                        ((ServerLevel) levelL).recipeAccess()
                                .getRecipeFor(ModRecipes.BASIC_BREEDING_TYPE.get(), getRecipeInput(recipeInv), levelL);

                first.ifPresent(holder -> e.maxProgress = (Config.breed_speed_tick.get() * holder.value().time()));
            }

            if (hasFreeOrStackableSlot(e, e.currentOutput)) {
                e.progress++;

                if (e.progress >= e.maxProgress) {
                    craftItem(e);
                    e.currentOutput = ItemStack.EMPTY;
                }
            } else {
                e.resetProgress();
                e.currentOutput = ItemStack.EMPTY;
            }

        } else {
            e.resetProgress();
            e.currentOutput = ItemStack.EMPTY;
            setChanged(levelL, pos, state);
        }

        if (e.isAutoOutputEnabled()) {
            tryPushBreederOutputsDown(levelL, pos, state, e);
        }
    }

    // -------------------------
    // NEW-SYSTEM OUTPUT PUSH (ATOMIC)
    // -------------------------
    private static void tryPushBreederOutputsDown(Level level, BlockPos pos, BlockState state, BreederTile tile) {

        ResourceHandler<ItemResource> below = getItemHandler(level, pos.below(), Direction.UP);
        if (below == null) return;

        boolean changed = false;

        for (int outputSlot = 3; outputSlot <= 11; outputSlot++) {
            ItemStack stackInSlot = tile.inventory.getStackDirect(outputSlot);
            if (stackInSlot.isEmpty()) continue;

            ItemResource res = ItemResource.of(stackInSlot);
            int amount = stackInSlot.getCount();

            try (Transaction tx = Transaction.openRoot()) {

                // 1) rein in den Target
                int inserted = below.insert(res, amount, tx);
                if (inserted <= 0) continue;

                // 2) exakt dieselbe Menge aus unserem Slot entfernen (im selben tx!)
                int extracted = tile.inventory.extract(outputSlot, res, inserted, tx);
                if (extracted <= 0) {
                    // sollte praktisch nie passieren, aber sicher ist sicher
                    continue;
                }

                tx.commit();
                changed = true;
            }
        }

        if (changed) {
            setChanged(level, pos, state);
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    // -------------------------
    // CRAFTING
    // -------------------------
    private void resetProgress() {
        this.progress = 0;
    }

    private static ItemStack pickRandomVariant(List<RecipeHolder<BreederRecipe>> recipes) {
        Random ran = new Random();
        int randomIndex = ran.nextInt(recipes.size());
        RecipeHolder<BreederRecipe> randomRecipe = recipes.get(randomIndex);
        return new ItemStack(randomRecipe.value().output().value());
    }

    private static void craftItem(BreederTile e) {
        Level level = e.level;
        if (level == null || level.isClientSide()) return;

        ItemStack chickenOutput = e.currentOutput;
        if (chickenOutput == null || chickenOutput.isEmpty()) {
            e.resetProgress();
            return;
        }

        try (Transaction tx = Transaction.openRoot()) {

            // 1️⃣ Output einfügen
            ItemResource out = ItemResource.of(chickenOutput);
            int remaining = chickenOutput.getCount();

            for (int slot = 3; slot <= 11 && remaining > 0; slot++) {
                remaining -= e.inventory.insert(slot, out, remaining, tx);
            }

            if (remaining > 0) {
                // passt nicht komplett rein → abbrechen
                return;
            }

            // 2️⃣ Seeds verbrauchen (IM SELBEN TX!)
            if (ChickenRoostMod.CONFIG.BreederSeeds) {
                consumeOne(e, 1, tx);
            }

            // 3️⃣ Commit = alles atomar
            tx.commit();
            e.resetProgress();
            e.currentOutput = ItemStack.EMPTY;
        }
    }


    /**
     * Verbraucht 1 Item aus einem Slot mit Transfer-API korrekt (Transaction).
     */
    private static void consumeOne(
            BreederTile e,
            int slot,
            TransactionContext tx
    ) {
        ItemStack stack = e.inventory.getStackDirect(slot);
        if (stack.isEmpty()) return;

        ItemResource res = ItemResource.of(stack);
        e.inventory.extract(slot, res, 1, tx);
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
