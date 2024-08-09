package net.tarantel.chickenroost.block.tile;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.handler.Trainer_Handler;
import net.tarantel.chickenroost.item.base.*;
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

public class Trainer_Tile extends BlockEntity implements MenuProvider, GeoBlockEntity {
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
                case 1 -> (stack.is(ItemTags.create(ChickenRoostMod.commonsource("seeds/tiered"))) || stack.is(ItemTags.create(ChickenRoostMod.commonsource("seeds"))));
                default -> super.isItemValid(slot, stack);
            };
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
        int maxProgresss = maxProgress;  // Max Progress
        int progressArrowSize = 200; // This is the height in pixels of your arrow

        return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
    }
    public Trainer_Tile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAINER.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> Trainer_Tile.this.progress;
                    case 1 -> Trainer_Tile.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> Trainer_Tile.this.progress = value;
                    case 1 -> Trainer_Tile.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("name.chicken_roost.trainer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new Trainer_Handler(id, inventory, this, this.data);
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
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookup) {
        nbt.put("inventory", itemHandler.serializeNBT(lookup));
        nbt.putInt("trainer.progress", this.progress);
        super.saveAdditional(nbt, lookup);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider lookup) {
        super.loadAdditional(nbt, lookup);
        itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));
        progress = nbt.getInt("trainer.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, inventory);
    }

    public static ItemStack MyChicken;
    public static ChickenSeedBase FoodItem;
    public static ChickenItemBase ChickenItem;
    public static int[] LevelList = {Config.maxlevel_tier_1.get().intValue(), Config.maxlevel_tier_2.get().intValue(), Config.maxlevel_tier_3.get().intValue(),Config.maxlevel_tier_4.get().intValue(),Config.maxlevel_tier_5.get().intValue(),Config.maxlevel_tier_6.get().intValue(),Config.maxlevel_tier_7.get().intValue(),Config.maxlevel_tier_8.get().intValue(),Config.maxlevel_tier_9.get().intValue()};
    public static int[] XPList = {Config.xp_tier_1.get().intValue(), Config.xp_tier_2.get().intValue(), Config.xp_tier_3.get().intValue(),Config.xp_tier_4.get().intValue(),Config.xp_tier_5.get().intValue(),Config.xp_tier_6.get().intValue(),Config.xp_tier_7.get().intValue(),Config.xp_tier_8.get().intValue(),Config.xp_tier_9.get().intValue()};
    public static int[] XPAmountList = {Config.food_xp_tier_1.get().intValue(), Config.food_xp_tier_2.get().intValue(), Config.food_xp_tier_3.get().intValue(), Config.food_xp_tier_4.get().intValue(), Config.food_xp_tier_5.get().intValue(), Config.food_xp_tier_6.get().intValue(), Config.food_xp_tier_7.get().intValue(), Config.food_xp_tier_8.get().intValue(), Config.food_xp_tier_9.get().intValue()};


    public static void tick(Level level, BlockPos pos, BlockState state, Trainer_Tile pEntity) {
        if (level.isClientSide()) {
            return;
        }
        if (pEntity.itemHandler.getStackInSlot(0).getItem().asItem() instanceof ChickenItemBase &&
                pEntity.itemHandler.getStackInSlot(1).getItem().asItem() instanceof ChickenSeedBase)
        {
            ChickenItem = (ChickenItemBase) pEntity.itemHandler.getStackInSlot(0).getItem();
            MyChicken = pEntity.itemHandler.getStackInSlot(0);
            FoodItem = (ChickenSeedBase) pEntity.itemHandler.getStackInSlot(1).getItem().getDefaultInstance().getItem();
            int ChickenLevel = MyChicken.get(ModDataComponents.CHICKENLEVEL);

            if(ChickenLevel < LevelList[ChickenItem.currentchickena]){
                pEntity.progress++;
                pEntity.triggerAnim("controller", "craft");
                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }
                /*else
                {
                    System.out.println("555555555555555");
                    pEntity.resetProgress();
                    pEntity.triggerAnim("controller", "idle");
                    setChanged(level, pos, state);
                }*/
            }
        }
        else
        {
            pEntity.resetProgress();
            pEntity.triggerAnim("controller", "idle");
            setChanged(level, pos, state);
        }

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

    private static void craftItem(Trainer_Tile pEntity) {
        ChickenItem = (ChickenItemBase) pEntity.itemHandler.getStackInSlot(0).getItem();
        MyChicken = pEntity.itemHandler.getStackInSlot(0);
        FoodItem = (ChickenSeedBase) pEntity.itemHandler.getStackInSlot(1).getItem().getDefaultInstance().getItem();
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
            int ChickenXP = MyChicken.get(ModDataComponents.CHICKENXP.value());

            if (pEntity.itemHandler.getStackInSlot(0).getItem() instanceof ChickenItemBase) {

                if (MyChicken.get(ModDataComponents.CHICKENLEVEL.value()) < LevelList[ChickenItem.currentchickena]) {
                    if (pEntity.itemHandler.getStackInSlot(1).getItem() instanceof ChickenSeedBase) {
                        if (ChickenXP + (XPAmountList[FoodItem.currentmaxxpp] / 10) >= XPList[ChickenItem.currentchickena]) {
                            MyChicken.set(ModDataComponents.CHICKENLEVEL.value(), (MyChicken.get(ModDataComponents.CHICKENLEVEL.value()) + 1));
                            MyChicken.set(ModDataComponents.CHICKENXP.value(), 0);
                        } else {
                            MyChicken.set(ModDataComponents.CHICKENXP.value(), (int) (ChickenXP + ((int) XPAmountList[FoodItem.currentmaxxpp])));
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

    private static boolean hasRecipe(Trainer_Tile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        if(entity.itemHandler.getStackInSlot(0).is((ItemTags.create(ChickenRoostMod.commonsource("roost/tiered")))) && (entity.itemHandler.getStackInSlot(1).is((ItemTags.create(ChickenRoostMod.commonsource("seeds/tiered")))) || entity.itemHandler.getStackInSlot(1).is((ItemTags.create(ChickenRoostMod.commonsource("seeds"))))) ) {
        return true;
        }
        return false;
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider prov) {
        return saveWithFullMetadata(prov);
    }
}