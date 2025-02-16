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
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.blocks.Roost_Block;
import net.tarantel.chickenroost.handler.Roost_Handler;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.recipes.Breeder_Recipe;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.Roost_Recipe;
import net.tarantel.chickenroost.recipes.Soul_Extractor_Recipe;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
public class Roost_Tile extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
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
                return 64;
            }
            if(slot == 1) {
                return 1;
            }
            if(slot == 2) {
                return 64;
            }
            return 0;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> (stack.is(ItemTags.create(new ResourceLocation("forge:seeds/tiered"))));
                case 1 -> (stack.getItem() instanceof AnimatedChicken_1 || stack.getItem() instanceof AnimatedChicken_2 || stack.getItem() instanceof AnimatedChicken_3 || stack.getItem() instanceof AnimatedChicken_4 || stack.getItem() instanceof AnimatedChicken_5 || stack.getItem() instanceof AnimatedChicken_6 || stack.getItem() instanceof AnimatedChicken_7 || stack.getItem() instanceof AnimatedChicken_8 || stack.getItem() instanceof AnimatedChicken_9);
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public ItemStack getRenderStack() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(1).isEmpty()) {
            stack = itemHandler.getStackInSlot(1);
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
    public int maxProgress = ( Config.roost_speed_tick.get() * 20);

    public int getScaledProgress() {
        int progresss = progress;
        int maxProgresss = maxProgress;  // Max Progress
        int progressArrowSize = 200; // This is the height in pixels of your arrow

        return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
    }

    public Roost_Tile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROOST.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> Roost_Tile.this.progress;
                    case 1 -> Roost_Tile.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> Roost_Tile.this.progress = value;
                    case 1 -> Roost_Tile.this.maxProgress = value;
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

        return Component.translatable("name.chicken_roost.roost");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new Roost_Handler(id, inventory, this, this.data);
    }
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new HashMap<>();
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
            for (Direction direction : Arrays.asList(Direction.DOWN, Direction.UP, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH)) {
                directionWrappedHandlerMap.put (direction, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2, (i, s) -> false)));
            }
            for (Direction direction : Arrays.asList(Direction.DOWN,Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.UP)) {
                directionWrappedHandlerMap.put (direction, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 2,
                        (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))));
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Roost_Block.FACING);

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
        if(!level.isClientSide()) {
            ///  ModMessages.sendToClients(new RoostItemStackSyncS2CPacket(this.itemHandler, worldPosition));
        }
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
        nbt.putInt("roost.progress", this.progress);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("roost.progress");

    }

    public void drops() {
        // Create a SimpleContainer to hold the items from the item handler
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        // Create an ItemStack for the block
        ItemStack itemStack = new ItemStack(ModBlocks.ROOST.get());

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


    public static void tick(Level level, BlockPos pos, BlockState state, Roost_Tile pEntity) {
        if(level.isClientSide()) {
            return;
        }
        setChanged(level, pos, state);
        /// ModMessages.sendToClients(new RoostItemStackSyncS2CPacket(pEntity.itemHandler, pEntity.worldPosition));
        if(hasRecipe(pEntity)) {
            pEntity.progress++;


            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();


            setChanged(level, pos, state);
        }

    }
    private void resetProgress() {

        this.progress = 0;
    }
    public static ItemStack ChickenItem;
    public static ChickenSeedBase FoodItem;
    public static ChickenItemBase MyChicken;
    public static int[] LevelList = {Config.maxlevel_tier_1.get().intValue(), Config.maxlevel_tier_2.get().intValue(), Config.maxlevel_tier_3.get().intValue(),Config.maxlevel_tier_4.get().intValue(),Config.maxlevel_tier_5.get().intValue(),Config.maxlevel_tier_6.get().intValue(),Config.maxlevel_tier_7.get().intValue(),Config.maxlevel_tier_8.get().intValue(),Config.maxlevel_tier_9.get().intValue()};
    public static int[] XPList = {Config.xp_tier_1.get().intValue(), Config.xp_tier_2.get().intValue(), Config.xp_tier_3.get().intValue(),Config.xp_tier_4.get().intValue(),Config.xp_tier_5.get().intValue(),Config.xp_tier_6.get().intValue(),Config.xp_tier_7.get().intValue(),Config.xp_tier_8.get().intValue(),Config.xp_tier_9.get().intValue()};
    public static int[] XPAmountList = {Config.food_xp_tier_1.get().intValue(), Config.food_xp_tier_2.get().intValue(), Config.food_xp_tier_3.get().intValue(), Config.food_xp_tier_4.get().intValue(), Config.food_xp_tier_5.get().intValue(), Config.food_xp_tier_6.get().intValue(), Config.food_xp_tier_7.get().intValue(), Config.food_xp_tier_8.get().intValue(), Config.food_xp_tier_9.get().intValue()};


    private static void craftItem(Roost_Tile pEntity) {
        MyChicken = (ChickenItemBase) pEntity.itemHandler.getStackInSlot(1).getItem().getDefaultInstance().getItem();
        ChickenItem = pEntity.itemHandler.getStackInSlot(1);
        FoodItem = (ChickenSeedBase) pEntity.itemHandler.getStackInSlot(0).getItem().getDefaultInstance().getItem();
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        Optional<Roost_Recipe> recipe = level.getRecipeManager()
                .getRecipeFor(Roost_Recipe.Type.INSTANCE, inventory, level);
        if (level != null) {
            if (hasRecipe(pEntity)) {
                int ChickenLevel = (int) (ChickenItem.getOrCreateTag().getInt("roost_lvl") / 2 + recipe.get().output.getCount());
                int ChickenXP = ChickenItem.getOrCreateTag().getInt("roost_xp");
                ItemStack itemstack1 = recipe.get().output.copy();
                itemstack1.setCount(pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel);

                if (pEntity.itemHandler.getStackInSlot(1).getItem() instanceof ChickenItemBase) {

                    if (ChickenItem.getOrCreateTag().getInt("roost_lvl") < LevelList[MyChicken.currentchickena]) {
                        if (pEntity.itemHandler.getStackInSlot(0).getItem() instanceof ChickenSeedBase) {
                            if ((ChickenXP + (XPAmountList[FoodItem.currentmaxxpp] * Config.roostxp.get()) >= XPList[MyChicken.currentchickena])) {
                                ChickenItem.getOrCreateTag().putInt("roost_lvl", (ChickenItem.getOrCreateTag().getInt("roost_lvl") + 1));
                                ChickenItem.getOrCreateTag().putInt("roost_xp", 0);

                            } else {

                                ChickenItem.getOrCreateTag().putInt("roost_xp", (int) ((ChickenXP + ((int) XPAmountList[FoodItem.currentmaxxpp]) * Config.roostxp.get())));
                            }
                        }
                        pEntity.itemHandler.extractItem(0, 1, false);
                        pEntity.itemHandler.extractItem(1, 0, true);
                        pEntity.itemHandler.setStackInSlot(1, ChickenItem);
                        pEntity.itemHandler.setStackInSlot(2, itemstack1.copy());
                        pEntity.resetProgress();
                    } else {
                        pEntity.itemHandler.extractItem(0, 1, false);
                        pEntity.itemHandler.extractItem(1, 0, true);
                        pEntity.itemHandler.setStackInSlot(2, itemstack1.copy());
                        pEntity.resetProgress();
                    }
                }

            }
        }
    }

    private static boolean hasRecipe(Roost_Tile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<Roost_Recipe> recipe = level.getRecipeManager()
                .getRecipeFor(Roost_Recipe.Type.INSTANCE, inventory, level);

        if(recipe.isPresent()){
            entity.maxProgress = ( Config.roost_speed_tick.get() * recipe.get().time);
        }

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().output.getItem().getDefaultInstance());
    }


    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(2).getItem() == stack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
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