package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.common.capabilities.Capability;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tarantel.chickenroost.Config;
import net.tarantel.chickenroost.block.Trainer_Block;
import net.tarantel.chickenroost.handlers.Trainer_Handler;
import net.tarantel.chickenroost.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.RawAnimation;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Trainer_Tile extends BlockEntity implements MenuProvider, IAnimatable {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
    /*@Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }*/

    /*@Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }*/

    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
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
                case 0 -> (stack.is(ItemTags.create(new ResourceLocation("forge:roost/tiered"))));
                case 1 -> (stack.is(ItemTags.create(new ResourceLocation("forge:seeds/tiered"))) || stack.is(ItemTags.create(new ResourceLocation("forge:seeds"))));
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    /*@Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }*/

    //private static final RawAnimation CRAFTING = RawAnimation.begin().then("training", Animation.LoopType.LOOP);
    //private static final RawAnimation IDLE = RawAnimation.begin().then("idle", Animation.LoopType.LOOP);
    /*private PlayState predicate(AnimationState<GeoAnimatable> state) {
        AnimationController<GeoAnimatable> controller = state.getController();
        controller.triggerableAnim("craft", CRAFTING);
        controller.triggerableAnim("idle", IDLE);

        return PlayState.CONTINUE;
    }*/
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
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
    public int maxProgress = ((int) Config.TRAINERSPEED.get() * 20);

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
        return Component.nullToEmpty("name.chicken_roost.trainer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new Trainer_Handler(id, inventory, this, this.data);
    }

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new HashMap<>();
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
            /*for (Direction direction : Arrays.asList(Direction.DOWN, Direction.UP, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH)) {
                directionWrappedHandlerMap.put (direction, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 1, (i, s) -> false)));
            }*/
            for (Direction direction : Arrays.asList(Direction.DOWN,Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.UP)) {
                directionWrappedHandlerMap.put (direction, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 0 || index == 1,
                        (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))));
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Trainer_Block.FACING);

                if(side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        setChanged();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("trainer.progress", this.progress);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("trainer.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, inventory);
    }

    public static Integer triggerAnim = 0;

    public static void tick(Level level, BlockPos pos, BlockState state, Trainer_Tile pEntity) {
        if (level.isClientSide()) {
            return;
        }

        ////Tier1
        if (pEntity.itemHandler.getStackInSlot(0).is((ItemTags.create(new ResourceLocation("forge:roost/tiered")))) &&
                (pEntity.itemHandler.getStackInSlot(1).is((ItemTags.create(new ResourceLocation("forge:seeds/tiered")))) ||
                        pEntity.itemHandler.getStackInSlot(1).is((ItemTags.create(new ResourceLocation("forge:seeds")))))) {
            MyChicken = pEntity.itemHandler.getStackInSlot(0);
            int ChickenLevel = MyChicken.getOrCreateTag().getInt("roost_lvl");
            if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier1"))) && ChickenLevel < Config.MAX_LEVEL_TIER_1.get()) {

                pEntity.progress++;
                pEntity.triggerAnim = 1;
                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }

            } else if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier2"))) && ChickenLevel < Config.MAX_LEVEL_TIER_2.get())
            {
                pEntity.progress++;
                pEntity.triggerAnim = 1;
                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }
            } else if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier3"))) && ChickenLevel < Config.MAX_LEVEL_TIER_3.get())
            {
                pEntity.progress++;
                pEntity.triggerAnim = 1;
                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }
            }
            else if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier4"))) && ChickenLevel < Config.MAX_LEVEL_TIER_4.get())
            {
                pEntity.progress++;
                pEntity.triggerAnim = 1;
                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }
            }
            else if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier5"))) && ChickenLevel < Config.MAX_LEVEL_TIER_5.get())
            {
                pEntity.progress++;
                pEntity.triggerAnim = 1;
                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }
            }
            else if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier6"))) && ChickenLevel < Config.MAX_LEVEL_TIER_6.get())
            {
                pEntity.progress++;
                pEntity.triggerAnim = 1;
                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }
            }
            else if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier7"))) && ChickenLevel < Config.MAX_LEVEL_TIER_7.get())
            {
                pEntity.progress++;
                pEntity.triggerAnim = 1;
                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }
            }
            else if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier8"))) && ChickenLevel < Config.MAX_LEVEL_TIER_8.get())
            {
                pEntity.progress++;
                pEntity.triggerAnim = 1;
                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }
            }
            else if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier9"))) && ChickenLevel < Config.MAX_LEVEL_TIER_9.get())
            {
                pEntity.progress++;
                pEntity.triggerAnim = 1;
                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }
            }
            else
            {
                pEntity.resetProgress();
                pEntity.triggerAnim = 0;
                setChanged(level, pos, state);
            }
        }
        else
        {
            pEntity.resetProgress();
            pEntity.triggerAnim = 0;
            setChanged(level, pos, state);
        }

    }

    


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<Trainer_Tile>(this, "controller", 0, this::predicate));
        //controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }



    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationController<E> controller = event.getController();

            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;

    }
    private void resetProgress() {
        this.progress = 0;
    }

    static ItemStack MyChicken;
    static ItemStack MySeed;
    private static void craftItem(Trainer_Tile pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        if (pEntity.itemHandler.getStackInSlot(0).is((ItemTags.create(new ResourceLocation("forge:roost/tiered")))) &&
                (pEntity.itemHandler.getStackInSlot(1).is((ItemTags.create(new ResourceLocation("forge:seeds/tiered")))) || (pEntity.itemHandler.getStackInSlot(1).is((ItemTags.create(new ResourceLocation("forge:seeds"))))))) {
            MyChicken = pEntity.itemHandler.getStackInSlot(0);
            int ChickenLevel = MyChicken.getOrCreateTag().getInt("roost_lvl");
            /*if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier1"))) && ChickenLevel >= Config.maxlevel_tier_1.get()) {
                pEntity.resetProgress();
            }*/
            int ChickenXP = MyChicken.getOrCreateTag().getInt("roost_xp");
            /////TIER 1
            if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier1")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") <= Config.MAX_LEVEL_TIER_1.get()) {
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds")))) {
                        if (ChickenXP + Config.VANILLA_SEED_XP.get() >= Config.MAX_XP_TIER_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.VANILLA_SEED_XP.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_1.get() >= Config.MAX_XP_TIER_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_1.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_2.get() >= Config.MAX_XP_TIER_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_2.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_3.get() >= Config.MAX_XP_TIER_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_3.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_4.get() >= Config.MAX_XP_TIER_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_4.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_5.get() >= Config.MAX_XP_TIER_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_5.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_6.get() >= Config.MAX_XP_TIER_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_6.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_7.get() >= Config.MAX_XP_TIER_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_7.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_8.get() >= Config.MAX_XP_TIER_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_8.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_9.get() >= Config.MAX_XP_TIER_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_9.get());
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
            /////TIER 2
            if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier2")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") <= Config.MAX_LEVEL_TIER_2.get()) {
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds")))) {
                        if (ChickenXP + Config.VANILLA_SEED_XP.get() >= Config.MAX_XP_TIER_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.VANILLA_SEED_XP.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_1.get() >= Config.MAX_XP_TIER_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_1.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_2.get() >= Config.MAX_XP_TIER_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_2.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_3.get() >= Config.MAX_XP_TIER_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_3.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_4.get() >= Config.MAX_XP_TIER_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_4.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_5.get() >= Config.MAX_XP_TIER_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_5.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_6.get() >= Config.MAX_XP_TIER_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_6.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_7.get() >= Config.MAX_XP_TIER_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_7.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_8.get() >= Config.MAX_XP_TIER_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_8.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_9.get() >= Config.MAX_XP_TIER_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_9.get());
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
            /////TIER 3
            if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier3")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") <= Config.MAX_LEVEL_TIER_3.get()) {
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds")))) {
                        if (ChickenXP + Config.VANILLA_SEED_XP.get() >= Config.MAX_XP_TIER_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.VANILLA_SEED_XP.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_1.get() >= Config.MAX_XP_TIER_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_1.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_2.get() >= Config.MAX_XP_TIER_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_2.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_3.get() >= Config.MAX_XP_TIER_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_3.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_4.get() >= Config.MAX_XP_TIER_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_4.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_5.get() >= Config.MAX_XP_TIER_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_5.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_6.get() >= Config.MAX_XP_TIER_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_6.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_7.get() >= Config.MAX_XP_TIER_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_7.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_8.get() >= Config.MAX_XP_TIER_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_8.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_9.get() >= Config.MAX_XP_TIER_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_9.get());
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
            /////TIER 4
            if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier4")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") <= Config.MAX_LEVEL_TIER_4.get()) {
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds")))) {
                        if (ChickenXP + Config.VANILLA_SEED_XP.get() >= Config.MAX_XP_TIER_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.VANILLA_SEED_XP.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_1.get() >= Config.MAX_XP_TIER_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_1.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_2.get() >= Config.MAX_XP_TIER_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_2.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_3.get() >= Config.MAX_XP_TIER_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_3.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_4.get() >= Config.MAX_XP_TIER_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_4.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_5.get() >= Config.MAX_XP_TIER_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_5.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_6.get() >= Config.MAX_XP_TIER_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_6.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_7.get() >= Config.MAX_XP_TIER_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_7.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_8.get() >= Config.MAX_XP_TIER_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_8.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_9.get() >= Config.MAX_XP_TIER_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_9.get());
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
            /////TIER 5
            if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier5")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") <= Config.MAX_LEVEL_TIER_5.get()) {
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds")))) {
                        if (ChickenXP + Config.VANILLA_SEED_XP.get() >= Config.MAX_XP_TIER_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.VANILLA_SEED_XP.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_1.get() >= Config.MAX_XP_TIER_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_1.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_2.get() >= Config.MAX_XP_TIER_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_2.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_3.get() >= Config.MAX_XP_TIER_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_3.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_4.get() >= Config.MAX_XP_TIER_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_4.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_5.get() >= Config.MAX_XP_TIER_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_5.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_6.get() >= Config.MAX_XP_TIER_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_6.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_7.get() >= Config.MAX_XP_TIER_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_7.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_8.get() >= Config.MAX_XP_TIER_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_8.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_9.get() >= Config.MAX_XP_TIER_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_9.get());
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
            /////TIER 6
            if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier6")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") <= Config.MAX_LEVEL_TIER_6.get()) {
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds")))) {
                        if (ChickenXP + Config.VANILLA_SEED_XP.get() >= Config.MAX_XP_TIER_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.VANILLA_SEED_XP.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_1.get() >= Config.MAX_XP_TIER_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_1.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_2.get() >= Config.MAX_XP_TIER_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_2.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_3.get() >= Config.MAX_XP_TIER_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_3.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_4.get() >= Config.MAX_XP_TIER_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_4.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_5.get() >= Config.MAX_XP_TIER_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_5.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_6.get() >= Config.MAX_XP_TIER_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_6.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_7.get() >= Config.MAX_XP_TIER_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_7.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_8.get() >= Config.MAX_XP_TIER_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_8.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_9.get() >= Config.MAX_XP_TIER_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_9.get());
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
            /////TIER 7
            if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier7")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") <= Config.MAX_LEVEL_TIER_7.get()) {
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds")))) {
                        if (ChickenXP + Config.VANILLA_SEED_XP.get() >= Config.MAX_XP_TIER_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.VANILLA_SEED_XP.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_1.get() >= Config.MAX_XP_TIER_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_1.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_2.get() >= Config.MAX_XP_TIER_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_2.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_3.get() >= Config.MAX_XP_TIER_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_3.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_4.get() >= Config.MAX_XP_TIER_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_4.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_5.get() >= Config.MAX_XP_TIER_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_5.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_6.get() >= Config.MAX_XP_TIER_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_6.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_7.get() >= Config.MAX_XP_TIER_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_7.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_8.get() >= Config.MAX_XP_TIER_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_8.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_9.get() >= Config.MAX_XP_TIER_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_9.get());
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
            /////TIER 8
            if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier8")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") <= Config.MAX_LEVEL_TIER_8.get()) {
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds")))) {
                        if (ChickenXP + Config.VANILLA_SEED_XP.get() >= Config.MAX_XP_TIER_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.VANILLA_SEED_XP.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_1.get() >= Config.MAX_XP_TIER_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_1.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_2.get() >= Config.MAX_XP_TIER_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_2.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_3.get() >= Config.MAX_XP_TIER_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_3.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_4.get() >= Config.MAX_XP_TIER_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_4.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_5.get() >= Config.MAX_XP_TIER_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_5.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_6.get() >= Config.MAX_XP_TIER_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_6.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_7.get() >= Config.MAX_XP_TIER_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_7.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_8.get() >= Config.MAX_XP_TIER_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_8.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_9.get() >= Config.MAX_XP_TIER_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_9.get());
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
            /////TIER 9
            if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:roost/tier9")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") <= Config.MAX_LEVEL_TIER_9.get()) {
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds")))) {
                        if (ChickenXP + Config.VANILLA_SEED_XP.get() >= Config.MAX_XP_TIER_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.VANILLA_SEED_XP.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_1.get() >= Config.MAX_XP_TIER_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_1.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_2.get() >= Config.MAX_XP_TIER_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_2.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_3.get() >= Config.MAX_XP_TIER_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_3.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_4.get() >= Config.MAX_XP_TIER_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_4.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_5.get() >= Config.MAX_XP_TIER_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_5.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_6.get() >= Config.MAX_XP_TIER_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_6.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_7.get() >= Config.MAX_XP_TIER_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_7.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_8.get() >= Config.MAX_XP_TIER_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_8.get());
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + Config.SEED_XP_TIER_9.get() >= Config.MAX_XP_TIER_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", ChickenXP + (int) Config.SEED_XP_TIER_9.get());
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
    }
    private static boolean hasRecipe(Trainer_Tile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        if(entity.itemHandler.getStackInSlot(0).is((ItemTags.create(new ResourceLocation("forge:roost/tiered")))) && (entity.itemHandler.getStackInSlot(1).is((ItemTags.create(new ResourceLocation("forge:seeds/tiered")))) || entity.itemHandler.getStackInSlot(1).is((ItemTags.create(new ResourceLocation("forge:seeds"))))) ) {
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
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}