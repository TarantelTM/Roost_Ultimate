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
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.SoulExtractorHandler;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.networking.SyncAutoOutputPayload;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.SoulExtractorRecipe;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class SoulExtractorTile extends BlockEntity implements MenuProvider, ICollectorTarget {

    // -------------------------
    // CONSTANTS
    // -------------------------
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    @Override
    public int getReadSlot() {
        return INPUT_SLOT;
    }

    // -------------------------
    // STATE
    // -------------------------
    private boolean migrating = false;

    public int progress = 0;
    public int maxProgress = Config.extractor_speedtimer.get() * 20;

    public int getScaledProgress() {
        int progressArrowSize = 200;
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    private String customName = "EXTRACTOR";

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

    // -------------------------
    // INVENTORY (TRANSFER)
    // -------------------------
    public final UniversalItemInventory inventory = new UniversalItemInventory(
            2,

            // INSERT rules (internes Crafting darf Output slot auch schreiben)
            (slot, res) -> switch (slot) {
                case INPUT_SLOT -> res.test(s -> s.getItem() instanceof ChickenItemBase);
                case OUTPUT_SLOT -> true; // intern fürs Crafting
                default -> false;
            },

            // EXTRACT rules (intern)
            slot -> true,

            // SLOT LIMIT
            slot -> 64,

            // CHANGE callback
            slot -> {
                if (!migrating) {
                    setChanged();
                    if (slot == INPUT_SLOT) resetProgress();
                    if (level != null && !level.isClientSide()) {
                        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                    }
                }
            }
    );

    /**
     * ✅ Automation-View wie früher (InputOnly / OutputOnly)
     * - Insert nur Slot 0
     * - Extract nur Slot 1
     *
     * Damit funktionieren Vanilla Hopper genau wie früher:
     * Hopper oben/seitlich kann Chicken rein, unten kann Output raus.
     */
    public ResourceHandler<ItemResource> getAutomationInventory(@Nullable Direction side) {
        // gleiche Regeln für alle Seiten; wenn du willst, kannst du side-basiert unterscheiden
        return new ResourceHandler<>() {
            @Override
            public int size() {
                return inventory.size();
            }

            @Override
            public ItemResource getResource(int index) {
                return inventory.getResource(index);
            }

            @Override
            public long getAmountAsLong(int index) {
                return inventory.getAmountAsLong(index);
            }

            @Override
            public long getCapacityAsLong(int index, ItemResource resource) {
                return inventory.getCapacityAsLong(index, resource);
            }

            @Override
            public boolean isValid(int index, ItemResource resource) {
                // Automation darf nur in Slot 0 rein
                if (index == INPUT_SLOT) return inventory.isValid(index, resource);
                // Output slot ist für Automation NICHT valide zum Insert
                return false;
            }

            @Override
            public int insert(int index, ItemResource resource, int amount, TransactionContext tx) {
                // Automation Insert nur Slot 0
                if (index != INPUT_SLOT) return 0;
                return inventory.insert(index, resource, amount, tx);
            }

            @Override
            public int extract(int index, ItemResource resource, int amount, TransactionContext tx) {
                // Automation Extract nur Slot 1
                if (index != OUTPUT_SLOT) return 0;
                return inventory.extract(index, resource, amount, tx);
            }
        };
    }

    // -------------------------
    // HELPERS
    // -------------------------
    public ItemStack getRenderStack() {
        return inventory.getStackDirect(INPUT_SLOT);
    }

    /**
     * Feature-Kompatibilität: alte Signatur behalten.
     * Falls du irgendwo ItemStackHandler reinkopierst (Migration/Tests),
     * übernimmt das die Stacks direkt in die neue Inventory-Struktur.
     */
    public void setHandler(net.neoforged.neoforge.items.ItemStackHandler oldHandler) {
        for (int i = 0; i < Math.min(oldHandler.getSlots(), inventory.size()); i++) {
            inventory.setStackDirect(i, oldHandler.getStackInSlot(i));
        }
    }

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
                case 0 -> SoulExtractorTile.this.progress;
                case 1 -> SoulExtractorTile.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulExtractorTile.this.progress = value;
                case 1 -> SoulExtractorTile.this.maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public SoulExtractorTile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_EXTRACTOR.get(), pos, state);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("name.chicken_roost.soul_extractor_");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new SoulExtractorHandler(id, inventory, this, this.data);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        setChanged();
    }

    // -------------------------
    // SAVE / LOAD
    // -------------------------
    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);

        inventory.serialize(out);

        out.putInt("soul_extractor.progress", this.progress);
        out.putInt("soul_extractor.max_progress", this.maxProgress);
        out.putString("soul_extractor.custom_name", this.customName);

        out.putBoolean("AutoOutput", autoOutput);
        out.putBoolean("LastRedstonePowered", lastRedstonePowered);
        out.putBoolean("AutoOutputByRedstone", autoOutputByRedstone);
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);

        inventory.deserialize(in);

        this.progress = in.getIntOr("soul_extractor.progress", 0);
        this.maxProgress = in.getIntOr("soul_extractor.max_progress", Config.extractor_speedtimer.get() * 20);
        this.customName = in.getStringOr("soul_extractor.custom_name", "EXTRACTOR");

        this.autoOutput = in.getBooleanOr("AutoOutput", false);
        this.lastRedstonePowered = in.getBooleanOr("LastRedstonePowered", false);
        this.autoOutputByRedstone = in.getBooleanOr("AutoOutputByRedstone", false);
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
        ItemStack itemStack = new ItemStack(ModBlocks.SOUL_EXTRACTOR);

        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inv.getItems()));
        block.setItem(0, itemStack.copy());

        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
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
    public static void tick(Level level, BlockPos pos, BlockState state, SoulExtractorTile e) {
        if (level.isClientSide()) return;

        boolean powered = level.hasNeighborSignal(pos);

        if (powered && !e.lastRedstonePowered) {
            if (!e.autoOutput) {
                e.setAutoOutputFromRedstone(true);
            }
        }

        if (!powered && e.lastRedstonePowered) {
            if (e.autoOutputByRedstone) {
                e.setAutoOutputFromRedstone(false);
            }
        }

        e.lastRedstonePowered = powered;

        if (hasRecipe(e)) {
            e.progress++;
            if (e.progress >= e.maxProgress) {
                craftItem(e);
            }
        } else {
            e.resetProgress();
        }

        if (e.isAutoOutputEnabled()) {
            tryPushOutputDown(level, pos, state, e);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    // -------------------------
    // OUTPUT PUSH (TRANSFER, ATOMIC)
    // -------------------------
    private static void tryPushOutputDown(Level level, BlockPos pos, BlockState state, SoulExtractorTile tile) {
        ItemStack outputStack = tile.inventory.getStackDirect(OUTPUT_SLOT);
        if (outputStack.isEmpty()) return;

        ResourceHandler<ItemResource> below = getItemHandler(level, pos.below(), Direction.UP);
        if (below == null) return;

        ItemResource resource = ItemResource.of(outputStack);
        int amount = outputStack.getCount();

        try (Transaction tx = Transaction.openRoot()) {

            // 1) rein unten
            int inserted = below.insert(resource, amount, tx);
            if (inserted <= 0) return;

            // 2) exakt diese Menge aus unserem Output entfernen
            int extracted = tile.inventory.extract(OUTPUT_SLOT, resource, inserted, tx);
            if (extracted <= 0) return;

            tx.commit();

            setChanged(level, pos, state);
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    // -------------------------
    // CRAFTING (TRANSFER, ATOMIC)
    // -------------------------
    private static void craftItem(SoulExtractorTile e) {
        Level level = e.level;
        if (level == null || level.isClientSide()) return;

        SimpleContainer inv = new SimpleContainer(e.inventory.size());
        for (int i = 0; i < e.inventory.size(); i++) {
            inv.setItem(i, e.inventory.getStackDirect(i));
        }

        Optional<RecipeHolder<SoulExtractorRecipe>> recipe =
                level.getServer().getRecipeManager().getRecipeFor(
                        ModRecipes.SOUL_EXTRACTION_TYPE.get(),
                        getRecipeInput(inv),
                        level
                );

        if (recipe.isEmpty()) {
            e.resetProgress();
            return;
        }

        ItemStack result = recipe.get().value().output().value().getDefaultInstance();
        if (result.isEmpty()) {
            e.resetProgress();
            return;
        }

        ItemStack input = e.inventory.getStackDirect(INPUT_SLOT);
        if (!(input.getItem() instanceof ChickenItemBase)) {
            e.resetProgress();
            return;
        }

        try (Transaction tx = Transaction.openRoot()) {

            // 1) Output +1 einfügen (passt nur wenn hasRecipe true war)
            ItemResource outRes = ItemResource.of(result);
            int inserted = e.inventory.insert(OUTPUT_SLOT, outRes, 1, tx);
            if (inserted <= 0) return;

            // 2) Input -1 verbrauchen
            ItemResource inRes = ItemResource.of(input);
            int extracted = e.inventory.extract(INPUT_SLOT, inRes, 1, tx);
            if (extracted <= 0) return;

            tx.commit();
            e.resetProgress();
        }
    }

    // -------------------------
    // RECIPE HELPERS
    // -------------------------
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

    private static boolean hasRecipe(SoulExtractorTile e) {
        Level level = e.level;
        if (level == null) return false;

        SimpleContainer inv = new SimpleContainer(e.inventory.size());
        for (int i = 0; i < e.inventory.size(); i++) {
            inv.setItem(i, e.inventory.getStackDirect(i));
        }

        Optional<RecipeHolder<SoulExtractorRecipe>> recipe =
                level.getServer().getRecipeManager().getRecipeFor(
                        ModRecipes.SOUL_EXTRACTION_TYPE.get(),
                        getRecipeInput(inv),
                        level
                );

        if (recipe.isEmpty()) return false;

        // maxProgress dynamisch aktualisieren (Feature wie vorher)
        recipe.ifPresent(r -> e.maxProgress = Config.extractor_speedtimer.get() * r.value().time());

        ItemStack outSlot = inv.getItem(OUTPUT_SLOT);
        ItemStack result = recipe.get().value().output().value().getDefaultInstance();

        // Output Slot: leer oder gleicher Item+Components und noch Platz
        return outSlot.isEmpty()
                || (ItemStack.isSameItemSameComponents(outSlot, result)
                && outSlot.getCount() < outSlot.getMaxStackSize());
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
