package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.blocks.Trainer_Block;
import net.tarantel.chickenroost.handler.Trainer_Handler;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Trainer_Tile extends BlockEntity implements MenuProvider, GeoBlockEntity, ICollectorTarget {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
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
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    private static final int OUTPUT_SLOT = 1;
    @Nullable
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
    public int maxProgress = ((int) Config.ServerConfig.training_speed_tick.get() * 20);

    public int getScaledProgress() {
        int progresss = progress;
        int maxProgresss = maxProgress;  // Max Progress
        int progressArrowSize = 200; // This is the height in pixels of your arrow

        return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
    }

    public final int[] LevelList;
    public final int[] XPList;
    public final int[] XPAmountList;

    public Trainer_Tile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAINER.get(), pos, state);

        this.LevelList = new int[]{
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_1.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_2.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_3.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_4.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_5.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_6.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_7.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_8.get(), 128),
                Objects.requireNonNullElse(Config.ServerConfig.maxlevel_tier_9.get(), 128)
        };

        this.XPList = new int[]{
                Config.ServerConfig.xp_tier_1.get(),
                Config.ServerConfig.xp_tier_2.get(),
                Config.ServerConfig.xp_tier_3.get(),
                Config.ServerConfig.xp_tier_4.get(),
                Config.ServerConfig.xp_tier_5.get(),
                Config.ServerConfig.xp_tier_6.get(),
                Config.ServerConfig.xp_tier_7.get(),
                Config.ServerConfig.xp_tier_8.get(),
                Config.ServerConfig.xp_tier_9.get()
        };

        this.XPAmountList = new int[]{
                Config.ServerConfig.food_xp_tier_1.get(),
                Config.ServerConfig.food_xp_tier_2.get(),
                Config.ServerConfig.food_xp_tier_3.get(),
                Config.ServerConfig.food_xp_tier_4.get(),
                Config.ServerConfig.food_xp_tier_5.get(),
                Config.ServerConfig.food_xp_tier_6.get(),
                Config.ServerConfig.food_xp_tier_7.get(),
                Config.ServerConfig.food_xp_tier_8.get(),
                Config.ServerConfig.food_xp_tier_9.get()
        };
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

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new HashMap<>();
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
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
        nbt.putString("trainer.custom_name", this.customName);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("trainer.progress");
        this.customName = nbt.getString("trainer.custom_name");
    }

    public void drops() {
        // Create a SimpleContainer to hold the items from the item handler
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        // Create an ItemStack for the block
        ItemStack itemStack = new ItemStack(ModBlocks.TRAINER.get());

        // Save the inventory contents to the ItemStack's NBT
        CompoundTag nbt = new CompoundTag();
        ListTag itemsTag = new ListTag();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                stack.save(itemTag);
                itemsTag.add(itemTag);
            }
        }
        nbt.put("Items", itemsTag);
        itemStack.setTag(nbt);

        // Create a SimpleContainer to hold the block's ItemStack
        SimpleContainer block = new SimpleContainer(1);
        block.setItem(0, itemStack.copy());

        // Drop the contents in the world
        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }

    public static ItemStack MyChicken;
    public static ChickenSeedBase FoodItem;
    public static ChickenItemBase ChickenItem;

    public static void tick(Level level, BlockPos pos, BlockState state, Trainer_Tile pEntity) {
        if (level.isClientSide()) {
            return;
        }

        if (pEntity.itemHandler.getStackInSlot(0).getItem() instanceof ChickenItemBase
                && pEntity.itemHandler.getStackInSlot(1).getItem() instanceof ChickenSeedBase) {

            ChickenItem = (ChickenItemBase) pEntity.itemHandler.getStackInSlot(0).getItem();
            MyChicken = pEntity.itemHandler.getStackInSlot(0);

            // 1.20.1: Defaults per NBT sicherstellen (entspricht deinem DataComponent-Default)
            var tag = MyChicken.getOrCreateTag();
            if (!tag.contains("roost_lvl", net.minecraft.nbt.Tag.TAG_INT)) {
                tag.putInt("roost_lvl", 0);
            }
            if (!tag.contains("roost_xp", net.minecraft.nbt.Tag.TAG_INT)) {
                tag.putInt("roost_xp", 0);
            }

            FoodItem = (ChickenSeedBase) pEntity.itemHandler.getStackInSlot(1).getItem();

            int chickenLevel = tag.getInt("roost_lvl");

            // Hier passt du deine Index-Logik an dein 1.20.1 Feld an:
            // In 1.21 hattest du: ChickenItem.currentchickena(ChickenItem.getDefaultInstance())
            // In 1.20 nutzt du: ChickenItem.currentchickena (Feld)
            if (chickenLevel < pEntity.LevelList[ChickenItem.currentchickena]) {
                pEntity.progress++;
                pEntity.triggerAnim("controller", "craft");

                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }
            }

        } else {
            pEntity.resetProgress();
            pEntity.triggerAnim("controller", "idle");
            setChanged(level, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }
    private static final String NBT_LEVEL_KEY = "roost_lvl";
    private static final String NBT_XP_KEY    = "roost_xp";

    private static void craftItem(Trainer_Tile pEntity) {
        ChickenItem = (ChickenItemBase) pEntity.itemHandler.getStackInSlot(0).getItem();
        MyChicken   = pEntity.itemHandler.getStackInSlot(0);
        FoodItem    = (ChickenSeedBase) pEntity.itemHandler.getStackInSlot(1).getItem().getDefaultInstance().getItem();

        // (Optional) inventory wird bei dir zwar gebaut, aber hier nicht benutzt – ich lasse es drin, falls du es später brauchst
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        // ---- 1.20.1: Level/XP aus NBT + Defaults wie in 1.21 ----
        var tag = MyChicken.getOrCreateTag();

        if (!tag.contains(NBT_LEVEL_KEY, net.minecraft.nbt.Tag.TAG_INT)) {
            tag.putInt(NBT_LEVEL_KEY, 0);
        }
        if (!tag.contains(NBT_XP_KEY, net.minecraft.nbt.Tag.TAG_INT)) {
            tag.putInt(NBT_XP_KEY, 0);
        }

        int chickenLevel = tag.getInt(NBT_LEVEL_KEY);
        int chickenXP    = tag.getInt(NBT_XP_KEY);

        if (pEntity.itemHandler.getStackInSlot(0).getItem() instanceof ChickenItemBase) {

            // 1.20.1: du nutzt currentchickena als Feld (wie in deinem Tick)
            int tierIndex = ChickenItem.currentchickena;
            int maxLevel  = pEntity.LevelList[tierIndex];
            int xpNeeded  = pEntity.XPList[tierIndex];

            if (chickenLevel < maxLevel) {
                if (pEntity.itemHandler.getStackInSlot(1).getItem() instanceof ChickenSeedBase) {

                    // 1.21-Logik: + XPAmountList[FoodItem.getCurrentMaxXp()]
                    // 1.20.1 bei dir: FoodItem.currentmaxxpp
                    int addXp = (int) pEntity.XPAmountList[FoodItem.getCurrentMaxXp()]; // ggf. /10, falls du das wirklich willst

                    if (chickenXP + addXp >= xpNeeded) {
                        tag.putInt(NBT_LEVEL_KEY, chickenLevel + 1);
                        tag.putInt(NBT_XP_KEY, 0);
                    } else {
                        tag.putInt(NBT_XP_KEY, chickenXP + addXp);
                    }
                }

                pEntity.itemHandler.extractItem(1, 1, false); // seed verbrauchen
                pEntity.itemHandler.setStackInSlot(0, MyChicken); // Stack zurückschreiben (NBT ist schon drin)
                pEntity.resetProgress();
            } else {
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