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
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.TrainerHandler;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.networking.SyncAutoOutputPayload;
import net.tarantel.chickenroost.networking.SyncTrainerLevelPayload;
import net.tarantel.chickenroost.util.ChickenStats;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

import java.util.Objects;

public class TrainerTile extends BlockEntity implements MenuProvider, GeoBlockEntity, ICollectorTarget {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtil.getCurrentTick();
    }

    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(slot == 0){
                resetProgress();
            }
            assert level != null;
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }

        }

        @Override
        public int getSlotLimit(int slot)
        {
            if(slot == 0){
                return 1;
            }
            if(slot == 1){
                return 64;
            }
            return 0;
        }
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> (stack.getItem() instanceof ChickenItemBase);
                case 1 -> (stack.getItem() instanceof ChickenSeedBase);
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int CHICKEN_SLOT = 0;

    @Override
    public int getReadSlot() {
        return CHICKEN_SLOT;
    }

    @Override
    public @Nullable IItemHandler getItemHandler() {
        return ccView;
    }

    private final IItemHandler ccView = new IItemHandler() {
        @Override
        public int getSlots() {
            return itemHandler.getSlots();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return itemHandler.getStackInSlot(slot);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return stack;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return itemHandler.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return itemHandler.isItemValid(slot, stack);
        }
    };

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private static final RawAnimation CRAFTING = RawAnimation.begin().then("training", Animation.LoopType.LOOP);
    private static final RawAnimation IDLE = RawAnimation.begin().then("idle", Animation.LoopType.LOOP);
    private PlayState predicate(AnimationState<GeoAnimatable> state) {
        AnimationController<GeoAnimatable> controller = state.getController();
        controller.triggerableAnim("craft", CRAFTING);
        controller.triggerableAnim("idle", IDLE);

        return PlayState.CONTINUE;
    }
    private String customName = "TRAINER";

    public void setCustomName(String name) {
        if (name == null) name = "";
        this.customName = name;
        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public String getCustomName() {
        return this.customName;
    }




    public ItemStack getRenderStack() {
        ItemStack stack;


        if (!itemHandler.getStackInSlot(0).isEmpty()) {
            stack = itemHandler.getStackInSlot(0);
        } else {
            stack = ItemStack.EMPTY;
        }
        return stack;
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }


    protected final ContainerData data;
    public int progress = 0;
    public int maxProgress =  Config.training_speed_tick.get() * 20;

    public int getScaledProgress() {
        int progresss = progress;
        int maxProgresss = maxProgress;
        int progressArrowSize = 200;

        return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
    }

    public final int[] LevelList;
    public final int[] XPList;
    public final int[] XPAmountList;

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

        this.data = new ContainerData() {
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
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("name.chicken_roost.trainer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new TrainerHandler(id, inventory, this, this.data);
    }

    private final IItemHandler itemHandlerSided = new InputOutputItemHandler(itemHandler, (i, stack) -> i == 0 || i == 1, i -> i == 0);


    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {
        if(side == null)
            return itemHandler;

        return itemHandlerSided;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        setChanged();
    }
    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.@NotNull Provider lookup) {
        nbt.put("inventory", itemHandler.serializeNBT(lookup));
        nbt.putInt("trainer.progress", this.progress);
        nbt.putString("trainer.custom_name", this.customName);
        nbt.putBoolean("AutoOutput", autoOutput);
        nbt.putBoolean("LastRedstonePowered", lastRedstonePowered);
        nbt.putBoolean("AutoOutputByRedstone", autoOutputByRedstone);
        nbt.putInt("TrainerAutoOutputLevel", this.autoOutputLevel);
        super.saveAdditional(nbt, lookup);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider lookup) {
        super.loadAdditional(nbt, lookup);
        itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));
        progress = nbt.getInt("trainer.progress");
        this.customName = nbt.getString("trainer.custom_name");
        if (nbt.contains("AutoOutput")) {
            this.autoOutput = nbt.getBoolean("AutoOutput");
        } else {
            this.autoOutput = false;
        }
        if (nbt.contains("LastRedstonePowered")) {
            this.lastRedstonePowered = nbt.getBoolean("LastRedstonePowered");
        } else {
            this.lastRedstonePowered = false;
        }

        if(nbt.contains("AutoOutputByRedstone")){
            this.autoOutputByRedstone = nbt.getBoolean("AutoOutputByRedstone");
        } else {
            this.autoOutputByRedstone = false;
        }

        if (nbt.contains("TrainerAutoOutputLevel")) {
            this.autoOutputLevel = nbt.getInt("TrainerAutoOutputLevel");
        } else {
            this.autoOutputLevel = 0;
        }

    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        SimpleContainer block = new SimpleContainer(1);

        ItemStack itemStack = new ItemStack(ModBlocks.TRAINER.get());
        NonNullList<ItemStack> items = inventory.getItems();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            items.set(i, itemHandler.getStackInSlot(i));
        }
        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inventory.getItems()));
        block.setItem(0, itemStack.copy());

        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }

    public static ItemStack MyChicken;
    public static ChickenSeedBase FoodItem;
    public static ChickenItemBase ChickenItem;

    private boolean autoOutput = false;


    private boolean lastRedstonePowered = false;
    private boolean autoOutputByRedstone = false;

    public boolean isAutoOutputEnabled() {
        return autoOutput;
    }

    public void setAutoOutputEnabled(boolean enabled) {
        this.autoOutput = enabled;
        this.autoOutputByRedstone = false;

        if (level != null && !level.isClientSide) {
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


    public static void tick(Level level, BlockPos pos, BlockState state, TrainerTile pEntity) {
        if (level.isClientSide()) {
            return;
        }

        ItemStack chickenStack = pEntity.itemHandler.getStackInSlot(0);
        ItemStack seedStack = pEntity.itemHandler.getStackInSlot(1);

        boolean hasChicken = chickenStack.getItem() instanceof ChickenItemBase;
        boolean hasSeed = seedStack.getItem() instanceof ChickenSeedBase;


        if (hasChicken) {
            ChickenItemBase chickenItem = (ChickenItemBase) chickenStack.getItem();


            if (!chickenStack.has(ModDataComponents.CHICKENLEVEL.value())) {
                chickenStack.set(ModDataComponents.CHICKENLEVEL.value(), 0);
            }
            if (!chickenStack.has(ModDataComponents.CHICKENXP.value())) {
                chickenStack.set(ModDataComponents.CHICKENXP.value(), 0);
            }

            int levelNow = chickenStack.get(ModDataComponents.CHICKENLEVEL.value());
            int maxLevel = pEntity.LevelList[
                    chickenItem.currentchickena(chickenStack)
                    ];


            chickenStack.set(
                    ModDataComponents.MAXLEVEL.value(),
                    levelNow >= maxLevel
            );

            pEntity.setChanged();
        }


        if (hasChicken && hasSeed) {

            ChickenItemBase chickenItem = (ChickenItemBase) chickenStack.getItem();
            int chickenLevel = chickenStack.get(ModDataComponents.CHICKENLEVEL.value());
            int maxLevel = pEntity.LevelList[
                    chickenItem.currentchickena(chickenStack)
                    ];

            if (chickenLevel < maxLevel) {
                pEntity.progress++;
                pEntity.triggerAnim("controller", "craft");

                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }
            } else {

                pEntity.resetProgress();
                pEntity.triggerAnim("controller", "idle");
            }

        }

        else {
            pEntity.resetProgress();
            pEntity.triggerAnim("controller", "idle");
            setChanged(level, pos, state);
        }



        boolean powered = level.hasNeighborSignal(pos);

        if (powered && !pEntity.lastRedstonePowered) {
            if (!pEntity.autoOutput) {
                pEntity.setAutoOutputFromRedstone(true);
            }
        }

        if (!powered && pEntity.lastRedstonePowered) {
            if (pEntity.autoOutputByRedstone) {
                pEntity.setAutoOutputFromRedstone(false);
            }
        }

        pEntity.lastRedstonePowered = powered;


        if (pEntity.isAutoOutputEnabled()) {
            tryPushChickenDown(level, pos, state, pEntity);
        }
    }


    public void setAutoOutputClient(boolean enabled) {
        this.autoOutput = enabled;
    }

    private void resetProgress() {
        this.progress = 0;
    }
    
    @Override
    public final void setChanged() {
        setChanged(true);
    }

    protected void setChanged(boolean updateComparator) {

    }


    private static void craftItem(TrainerTile pEntity) {
        ChickenItem = (ChickenItemBase) pEntity.itemHandler.getStackInSlot(0).getItem();
        MyChicken = pEntity.itemHandler.getStackInSlot(0);
        FoodItem = (ChickenSeedBase) pEntity.itemHandler.getStackInSlot(1).getItem().getDefaultInstance().getItem();
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        int chickenlvl;
        int chickenxp;
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        int ChickenLevel;
        int ChickenXP;
        if(MyChicken.has(ModDataComponents.CHICKENLEVEL) && MyChicken.has(ModDataComponents.CHICKENXP)){
            ChickenXP = MyChicken.get(ModDataComponents.CHICKENXP.value());
        }
        else {
            ChickenLevel = 0;
            ChickenXP = 0;
            MyChicken.set(ModDataComponents.CHICKENLEVEL.value(), ChickenLevel);
            MyChicken.set(ModDataComponents.CHICKENXP.value(), ChickenXP);
        }

            if (pEntity.itemHandler.getStackInSlot(0).getItem() instanceof ChickenItemBase) {

                if (MyChicken.get(ModDataComponents.CHICKENLEVEL.value()) < pEntity.LevelList[ChickenItem.currentchickena(ChickenItem.getDefaultInstance())]) {
                    if (pEntity.itemHandler.getStackInSlot(1).getItem() instanceof ChickenSeedBase) {
                        if (ChickenXP + (pEntity.XPAmountList[FoodItem.getCurrentMaxXp()]) >= pEntity.XPList[ChickenItem.currentchickena(ChickenItem.getDefaultInstance())]) {
                            MyChicken.set(ModDataComponents.CHICKENLEVEL.value(), (MyChicken.get(ModDataComponents.CHICKENLEVEL.value()) + 1));
                            MyChicken.set(ModDataComponents.CHICKENXP.value(), 0);

                            chickenlvl = MyChicken.get(ModDataComponents.CHICKENLEVEL.value());
                            chickenxp = MyChicken.get(ModDataComponents.CHICKENXP.value());


                        } else {
                            MyChicken.set(ModDataComponents.CHICKENXP.value(), ChickenXP + pEntity.XPAmountList[FoodItem.getCurrentMaxXp()]);
                            chickenlvl = MyChicken.get(ModDataComponents.CHICKENLEVEL.value());
                            chickenxp = MyChicken.get(ModDataComponents.CHICKENXP.value());

                        }
                    }

                    pEntity.itemHandler.extractItem(1, 1, false);
                    pEntity.itemHandler.extractItem(0, 0, false);
                    pEntity.itemHandler.setStackInSlot(0, MyChicken);

                    pEntity.resetProgress();
                }
                else
                {
                    pEntity.resetProgress();
                }
            }
        }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider prov) {
        return saveWithFullMetadata(prov);
    }


    private int autoOutputLevel = 128;

    public int getAutoOutputLevel() {
        return autoOutputLevel;
    }


    public void setAutoOutputLevel(int level) {
        this.autoOutputLevel = Math.max(0, level);
        syncTrainerLevelToClients();
        setChanged();
        if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }


    public void setAutoOutputLevelClient(int level) {
        this.autoOutputLevel = Math.max(0, level);
    }





    private static void tryPushChickenDown(Level level, BlockPos pos, BlockState state, TrainerTile tile) {
        ItemStack stack = tile.itemHandler.getStackInSlot(CHICKEN_SLOT);
        if (stack.isEmpty()) return;
        if (!(stack.getItem() instanceof ChickenItemBase)) return;


        if (!stack.has(ModDataComponents.CHICKENLEVEL.value())) return;
        Integer chickenLevel = stack.get(ModDataComponents.CHICKENLEVEL.value());
        if (chickenLevel == null) return;


        if (chickenLevel < tile.autoOutputLevel) return;



        IItemHandler below = level.getCapability(
                Capabilities.ItemHandler.BLOCK,
                pos.below(),
                net.minecraft.core.Direction.UP
        );
        if (below == null) return;

        ItemStack remaining = stack.copy();

        for (int s = 0; s < below.getSlots() && !remaining.isEmpty(); s++) {
            remaining = below.insertItem(s, remaining, false);
        }


        if (remaining.getCount() == stack.getCount()) return;


        int moved = stack.getCount() - remaining.getCount();
        ItemStack newStack = stack.copy();
        newStack.shrink(moved);
        if (newStack.isEmpty()) newStack = ItemStack.EMPTY;

        tile.itemHandler.setStackInSlot(CHICKEN_SLOT, newStack);
        setChanged(level, pos, state);
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


}