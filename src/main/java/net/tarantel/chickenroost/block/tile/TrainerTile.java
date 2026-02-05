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
import net.tarantel.chickenroost.handler.TrainerHandler;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import net.tarantel.chickenroost.networking.SyncAutoOutputPayload;
import net.tarantel.chickenroost.networking.SyncTrainerLevelPayload;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;

public class TrainerTile extends BlockEntity implements MenuProvider, GeoBlockEntity, ICollectorTarget {

    // -------------------------
    // GEO / ANIMATION
    // -------------------------
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public enum AnimState {
        IDLE,
        CRAFTING
    }

    private AnimState animState = AnimState.IDLE;
    private AnimState lastClientAnimState = null;

    public void setAnimState(AnimState state) {
        if (this.animState != state) {
            this.animState = state;
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }

    public AnimState getAnimState() {
        return animState;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>("controller", 0, this::predicate)
                        .receiveTriggeredAnimations()
                        .triggerableAnim("craft", RawAnimation.begin().thenLoop("training"))
                        .triggerableAnim("idle", RawAnimation.begin().thenLoop("idle"))
        );
    }

    private PlayState predicate(AnimationTest<GeoAnimatable> state) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public void applyClientAnimation() {
        if (level == null || !level.isClientSide()) return;

        if (animState != lastClientAnimState) {
            lastClientAnimState = animState;
            switch (animState) {
                case IDLE -> triggerAnim("controller", "idle");
                case CRAFTING -> triggerAnim("controller", "craft");
            }
        }
    }

    // -------------------------
    // STATE
    // -------------------------
    private boolean migrating = false;

    private String customName = "TRAINER";

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

    public int progress = 0;
    public int maxProgress = Config.training_speed_tick.get() * 20;

    public int getScaledProgress() {
        int progressArrowSize = 200;
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    // -------------------------
    // INVENTORY (TRANSFER)
    // -------------------------
    private static final int CHICKEN_SLOT = 0;
    private static final int SEED_SLOT = 1;

    @Override
    public int getReadSlot() {
        return CHICKEN_SLOT;
    }

    public final UniversalItemInventory inventory = new UniversalItemInventory(
            2,

            // INSERT rules
            (slot, res) -> switch (slot) {
                case CHICKEN_SLOT -> res.test(s -> s.getItem() instanceof ChickenItemBase);
                case SEED_SLOT -> res.test(s -> s.getItem() instanceof ChickenSeedBase);
                default -> false;
            },

            // EXTRACT rules (Spieler + interne Logik)
            slot -> true,

            // SLOT LIMIT
            slot -> slot == CHICKEN_SLOT ? 1 : 64,

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

    public ItemStack getRenderStack() {
        return inventory.getStackDirect(CHICKEN_SLOT);
    }

    private static @Nullable ResourceHandler<ItemResource> getItemHandler(Level level, BlockPos pos, @Nullable Direction side) {
        return level.getCapability(Capabilities.Item.BLOCK, pos, side);
    }

    // -------------------------
    // AUTO OUTPUT / REDSTONE
    // -------------------------
    private boolean autoOutput = false;
    private boolean lastRedstonePowered = false;
    private boolean autoOutputByRedstone = false;

    private int autoOutputLevel = 128;

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
        syncAutoOutputToClients();
    }

    private void setAutoOutputFromRedstone(boolean enabled) {
        this.autoOutput = enabled;
        this.autoOutputByRedstone = enabled;
        syncAutoOutputToClients();
    }

    private void syncAutoOutputToClients() {
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

    public int getAutoOutputLevel() {
        return autoOutputLevel;
    }

    public void setAutoOutputLevel(int level) {
        this.autoOutputLevel = Math.max(0, level);
        syncTrainerLevelToClients();
        setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public void setAutoOutputLevelClient(int level) {
        this.autoOutputLevel = Math.max(0, level);
    }

    private void syncTrainerLevelToClients() {
        if (level instanceof ServerLevel serverLevel) {
            PacketDistributor.sendToPlayersTrackingChunk(
                    serverLevel,
                    serverLevel.getChunkAt(worldPosition).getPos(),
                    new SyncTrainerLevelPayload(worldPosition, autoOutputLevel)
            );
        }
    }

    // -------------------------
    // TRAINING CONFIG ARRAYS
    // -------------------------
    public final int[] LevelList;
    public final int[] XPList;
    public final int[] XPAmountList;

    // -------------------------
    // MENU/DATA
    // -------------------------
    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> TrainerTile.this.progress;
                case 1 -> TrainerTile.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> TrainerTile.this.progress = value;
                case 1 -> TrainerTile.this.maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public TrainerTile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAINER.get(), pos, state);

        this.LevelList = new int[]{
                Objects.requireNonNullElse(Config.maxlevel_tier_1.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_2.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_3.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_4.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_5.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_6.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_7.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_8.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_9.get(), 128)
        };

        this.XPList = new int[]{
                Config.xp_tier_1.get(),
                Config.xp_tier_2.get(),
                Config.xp_tier_3.get(),
                Config.xp_tier_4.get(),
                Config.xp_tier_5.get(),
                Config.xp_tier_6.get(),
                Config.xp_tier_7.get(),
                Config.xp_tier_8.get(),
                Config.xp_tier_9.get()
        };

        this.XPAmountList = new int[]{
                Config.food_xp_tier_1.get(),
                Config.food_xp_tier_2.get(),
                Config.food_xp_tier_3.get(),
                Config.food_xp_tier_4.get(),
                Config.food_xp_tier_5.get(),
                Config.food_xp_tier_6.get(),
                Config.food_xp_tier_7.get(),
                Config.food_xp_tier_8.get(),
                Config.food_xp_tier_9.get()
        };
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("name.chicken_roost.trainer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new TrainerHandler(id, inv, this, this.data);
    }

    // -------------------------
    // LOAD / SAVE
    // -------------------------
    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);

        inventory.serialize(out);

        out.putInt("trainer.progress", this.progress);
        out.putInt("trainer.max_progress", this.maxProgress);

        out.putString("trainer.custom_name", this.customName);

        out.putBoolean("AutoOutput", this.autoOutput);
        out.putBoolean("LastRedstonePowered", this.lastRedstonePowered);
        out.putBoolean("AutoOutputByRedstone", this.autoOutputByRedstone);

        out.putInt("TrainerAutoOutputLevel", this.autoOutputLevel);

        out.putString("AnimState", animState.name());
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);

        inventory.deserialize(in);

        this.progress = in.getIntOr("trainer.progress", 0);
        this.maxProgress = in.getIntOr("trainer.max_progress", Config.training_speed_tick.get() * 20);

        this.customName = in.getStringOr("trainer.custom_name", "TRAINER");

        this.autoOutput = in.getBooleanOr("AutoOutput", false);
        this.lastRedstonePowered = in.getBooleanOr("LastRedstonePowered", false);
        this.autoOutputByRedstone = in.getBooleanOr("AutoOutputByRedstone", false);

        this.autoOutputLevel = in.getIntOr("TrainerAutoOutputLevel", 128);

        this.animState = AnimState.valueOf(in.getStringOr("AnimState", "IDLE"));
    }

    @Override
    public void onLoad() {
        super.onLoad();
        setChanged();
        if (level != null && level.isClientSide()) {
            applyClientAnimation();
        }
    }

    // -------------------------
    // DROPS (Contents into block item)
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
        ItemStack itemStack = new ItemStack(ModBlocks.TRAINER);

        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inv.getItems()));
        block.setItem(0, itemStack.copy());

        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }

    // -------------------------
    // TICK
    // -------------------------
    public static void tick(Level level, BlockPos pos, BlockState state, TrainerTile t) {
        if (level.isClientSide()) return;

        ItemStack chickenStack = t.inventory.getStackDirect(CHICKEN_SLOT);
        ItemStack seedStack = t.inventory.getStackDirect(SEED_SLOT);

        boolean hasChicken = chickenStack.getItem() instanceof ChickenItemBase;
        boolean hasSeed = seedStack.getItem() instanceof ChickenSeedBase;

        AnimState targetAnimState = AnimState.IDLE;

        // Init / maintain chicken component data when present
        if (hasChicken) {
            if (!chickenStack.has(ModDataComponents.CHICKENLEVEL.value())) {
                chickenStack.set(ModDataComponents.CHICKENLEVEL.value(), 0);
            }
            if (!chickenStack.has(ModDataComponents.CHICKENXP.value())) {
                chickenStack.set(ModDataComponents.CHICKENXP.value(), 0);
            }

            ChickenItemBase chickenItem = (ChickenItemBase) chickenStack.getItem();
            int tier = chickenItem.currentchickena(chickenStack);
            int levelNow = chickenStack.get(ModDataComponents.CHICKENLEVEL.value());
            int maxLevel = t.LevelList[tier];

            chickenStack.set(ModDataComponents.MAXLEVEL.value(), levelNow >= maxLevel);

            // Wichtig: Stack wurde mutiert -> sauber zurückschreiben (serverseitig)
            t.writeBackChickenIfNeeded(chickenStack);
        } else {
            targetAnimState = AnimState.IDLE;
        }

        // Training loop
        if (hasChicken && hasSeed) {
            ChickenItemBase chickenItem = (ChickenItemBase) chickenStack.getItem();
            int tier = chickenItem.currentchickena(chickenStack);

            int chickenLevel = chickenStack.get(ModDataComponents.CHICKENLEVEL.value());
            int maxLevel = t.LevelList[tier];

            if (chickenLevel < maxLevel) {
                t.progress++;
                targetAnimState = AnimState.CRAFTING;

                if (t.progress >= t.maxProgress) {
                    craftItem(t);
                }
            } else {
                t.resetProgress();
                targetAnimState = AnimState.IDLE;
            }
        } else {
            t.resetProgress();
            targetAnimState = AnimState.IDLE;
        }

        // Animation state once
        t.setAnimState(targetAnimState);

        // Redstone logic
        boolean powered = level.hasNeighborSignal(pos);

        if (powered && !t.lastRedstonePowered) {
            if (!t.autoOutput) t.setAutoOutputFromRedstone(true);
        }

        if (!powered && t.lastRedstonePowered) {
            if (t.autoOutputByRedstone) t.setAutoOutputFromRedstone(false);
        }

        t.lastRedstonePowered = powered;

        // Auto output
        if (t.isAutoOutputEnabled()) {
            tryPushChickenDown(level, pos, state, t);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    /**
     * Wenn wir am ChickenStack Components setzen (Level/XP/MAXLEVEL),
     * schreiben wir den Stack serverseitig transaktional zurück, damit
     * Inventar/Sync/Comparator/Automation sauber reagieren.
     */
    private void writeBackChickenIfNeeded(ItemStack mutatedChickenStack) {
        if (level == null || level.isClientSide()) return;
        if (mutatedChickenStack.isEmpty()) return;

        ItemResource res = ItemResource.of(mutatedChickenStack);
        try (Transaction tx = Transaction.openRoot()) {
            // Slot 0 ist immer Count 1
            inventory.extract(CHICKEN_SLOT, res, 1, tx);
            inventory.insert(CHICKEN_SLOT, res, 1, tx);
            tx.commit();
        }
    }

    // -------------------------
    // CRAFTING / TRAINING (TRANSFER-ATOMIC)
    // -------------------------
    private static void craftItem(TrainerTile t) {
        Level level = t.level;
        if (level == null || level.isClientSide()) return;

        ItemStack chickenStack = t.inventory.getStackDirect(CHICKEN_SLOT);
        ItemStack seedStack = t.inventory.getStackDirect(SEED_SLOT);

        if (!(chickenStack.getItem() instanceof ChickenItemBase chickenItem)) {
            t.resetProgress();
            return;
        }
        if (!(seedStack.getItem() instanceof ChickenSeedBase foodItem)) {
            t.resetProgress();
            return;
        }

        // Ensure base components exist
        if (!chickenStack.has(ModDataComponents.CHICKENLEVEL.value())) {
            chickenStack.set(ModDataComponents.CHICKENLEVEL.value(), 0);
        }
        if (!chickenStack.has(ModDataComponents.CHICKENXP.value())) {
            chickenStack.set(ModDataComponents.CHICKENXP.value(), 0);
        }

        int tier = chickenItem.currentchickena(chickenStack);
        int currentLevel = chickenStack.get(ModDataComponents.CHICKENLEVEL.value());
        int currentXP = chickenStack.get(ModDataComponents.CHICKENXP.value());

        int maxLevel = t.LevelList[tier];
        if (currentLevel >= maxLevel) {
            t.resetProgress();
            return;
        }

        int xpNeeded = t.XPList[tier];
        int xpGain = t.XPAmountList[foodItem.getCurrentMaxXp()];

        try (Transaction tx = Transaction.openRoot()) {

            // 1) Update XP/Level on chicken (stack mutation)
            if (currentXP + xpGain >= xpNeeded) {
                chickenStack.set(ModDataComponents.CHICKENLEVEL.value(), currentLevel + 1);
                chickenStack.set(ModDataComponents.CHICKENXP.value(), 0);
            } else {
                chickenStack.set(ModDataComponents.CHICKENXP.value(), currentXP + xpGain);
            }

            // Also maintain MAXLEVEL flag
            int newLevel = chickenStack.get(ModDataComponents.CHICKENLEVEL.value());
            chickenStack.set(ModDataComponents.MAXLEVEL.value(), newLevel >= maxLevel);

            // 2) Write back chicken atomically (extract+insert 1)
            ItemResource chickenRes = ItemResource.of(chickenStack);
            t.inventory.extract(CHICKEN_SLOT, chickenRes, 1, tx);
            t.inventory.insert(CHICKEN_SLOT, chickenRes, 1, tx);

            // 3) Consume 1 seed
            ItemResource seedRes = ItemResource.of(seedStack);
            t.inventory.extract(SEED_SLOT, seedRes, 1, tx);

            // 4) Commit all
            tx.commit();
            t.resetProgress();
        }
    }

    // -------------------------
    // AUTO OUTPUT (TRANSFER-ATOMIC)
    // -------------------------
    private static void tryPushChickenDown(Level level, BlockPos pos, BlockState state, TrainerTile tile) {
        ItemStack stack = tile.inventory.getStackDirect(CHICKEN_SLOT);
        if (stack.isEmpty()) return;
        if (!(stack.getItem() instanceof ChickenItemBase)) return;

        Integer chickenLevel = stack.getOrDefault(ModDataComponents.CHICKENLEVEL.value(), 0);
        if (chickenLevel < tile.autoOutputLevel) return;

        ResourceHandler<ItemResource> below =
                getItemHandler(level, pos.below(), Direction.UP);
        if (below == null) return;

        ItemResource resource = ItemResource.of(stack);

        try (Transaction tx = Transaction.openRoot()) {

            // Insert 1 chicken into target
            int inserted = below.insert(resource, 1, tx);
            if (inserted <= 0) return;

            // Remove exactly the same from our inventory (same tx!)
            int extracted = tile.inventory.extract(CHICKEN_SLOT, resource, inserted, tx);
            if (extracted <= 0) return;

            tx.commit();
            setChanged(level, pos, state);
            level.sendBlockUpdated(pos, state, state, 3);
        }
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
