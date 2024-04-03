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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tarantel.chickenroost.Config;
import net.tarantel.chickenroost.block.Breeder_Block;
import net.tarantel.chickenroost.handlers.Breeder_Handler;
import net.tarantel.chickenroost.recipes.Breeder_Recipe;
import net.tarantel.chickenroost.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Breeder_Tile extends BlockEntity implements MenuProvider {
    public static int x;
    public static int y;
    public static int z;
    private final ItemStackHandler itemHandler = new ItemStackHandler(10) {
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
            if(slot == 1){
                return 64;
            }
            if(slot == 0){
                return 1;
            }
            if(slot == 2){
                return 1;
            }
            if(slot == 3){
                return 1;
            }
            if(slot == 4){
                return 1;
            }
            if(slot == 5){
                return 1;
            }
            if(slot == 6){
                return 1;
            }
            if(slot == 7){
                return 1;
            }
            if(slot == 8){
                return 1;
            }
            if(slot == 9){
                return 1;
            }
            return 0;
        }
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> (stack.is(ItemTags.create(new ResourceLocation("forge:roost/tiered"))));
                case 1 -> (stack.is(ItemTags.create(new ResourceLocation("forge:seeds/tiered"))));
                case 2 -> false;
                case 3 -> false;
                case 4 -> false;
                case 5 -> false;
                case 6 -> false;
                case 7 -> false;
                case 8 -> false;
                case 9 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public ItemStack getRenderStack() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(0).isEmpty()) {
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
    public int maxProgress = ( Config.BREEDERSPEED.get() * 20);
    public int getScaledProgress() {
        int progresss = progress;
        int maxProgresss = maxProgress;  // Max Progress
        int progressArrowSize = 200; // This is the height in pixels of your arrow

        return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
    }

    public Breeder_Tile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BREEDER.get(), pos, state);

        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> Breeder_Tile.this.progress;
                    case 1 -> Breeder_Tile.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> Breeder_Tile.this.progress = value;
                    case 1 -> Breeder_Tile.this.maxProgress = value;
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
        return Component.nullToEmpty("");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new Breeder_Handler(id, inventory, this, this.data);
    }

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new HashMap<>();
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
            for (Direction direction : Arrays.asList(Direction.DOWN, Direction.UP, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH)) {
                directionWrappedHandlerMap.put (direction, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9, (i, s) -> false)));
            }
            for (Direction direction : Arrays.asList(Direction.DOWN,Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.UP)) {
                directionWrappedHandlerMap.put (direction, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 2 || index == 3 || index == 4 || index == 5 || index == 6 || index == 7 || index == 8 || index == 9,
                        (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))));
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Breeder_Block.FACING);

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

    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("breeder.progress", this.progress);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("breeder.progress");


    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, inventory);
    }



    public static void tick(Level levelL, BlockPos pos, BlockState state, Breeder_Tile pEntity) {
        if(levelL.isClientSide()) {
            return;
        }
        setChanged(levelL, pos, state);
        //ModMessages.sendToClients(new BreederItemStackSyncS2CPacket(pEntity.itemHandler, pEntity.worldPosition));
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();



        if(hasRecipe(pEntity) && (pEntity.itemHandler.getStackInSlot(2) == ItemStack.EMPTY
                || pEntity.itemHandler.getStackInSlot(3) == ItemStack.EMPTY ||
                pEntity.itemHandler.getStackInSlot(4) == ItemStack.EMPTY ||
                pEntity.itemHandler.getStackInSlot(5) == ItemStack.EMPTY ||
                pEntity.itemHandler.getStackInSlot(6) == ItemStack.EMPTY ||
                pEntity.itemHandler.getStackInSlot(7) == ItemStack.EMPTY ||
                pEntity.itemHandler.getStackInSlot(8) == ItemStack.EMPTY ||
                pEntity.itemHandler.getStackInSlot(9) == ItemStack.EMPTY)) {
            {
                int _value = 2;
                BlockPos _pos = new BlockPos(x, y, z);
                BlockState _bs = levelL.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("mynewstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
                    levelL.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
            }
            pEntity.progress++;


            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();
            {
                int _value = 1;
                BlockPos _pos = new BlockPos(x, y, z);
                BlockState _bs = levelL.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("mynewstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
                    levelL.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
            }

            setChanged(levelL, pos, state);
        }


    }
    private void resetProgress() {

        this.progress = 0;
    }
    static ItemStack ChickenOutput = ItemStack.EMPTY;
    private static void craftItem(Breeder_Tile pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        List<Breeder_Recipe> recipe = Objects.requireNonNull(pEntity.getLevel()).getRecipeManager()
                .getRecipesFor(Breeder_Recipe.Type.INSTANCE, inventory, pEntity.getLevel());
        if(hasRecipe(pEntity)) {
            Random ran = new Random();
            int RandomOutputs = ran.nextInt(recipe.size());
            ChickenOutput = new ItemStack(recipe.get(RandomOutputs).getResultItem().getItem());
            if(pEntity.itemHandler.getStackInSlot(2) == ItemStack.EMPTY) {
                pEntity.itemHandler.extractItem(0, 0, true);
                pEntity.itemHandler.extractItem(1, 1, false);
                pEntity.itemHandler.setStackInSlot(2, ChickenOutput);

                pEntity.resetProgress();
            } else if (pEntity.itemHandler.getStackInSlot(3) == ItemStack.EMPTY) {
                pEntity.itemHandler.extractItem(0, 0, true);
                pEntity.itemHandler.extractItem(1, 1, false);
                pEntity.itemHandler.setStackInSlot(3, ChickenOutput);

                pEntity.resetProgress();

            }else if (pEntity.itemHandler.getStackInSlot(4) == ItemStack.EMPTY) {
                pEntity.itemHandler.extractItem(0, 0, true);
                pEntity.itemHandler.extractItem(1, 1, false);
                pEntity.itemHandler.setStackInSlot(4, ChickenOutput);

                pEntity.resetProgress();

            }else if (pEntity.itemHandler.getStackInSlot(5) == ItemStack.EMPTY) {
                pEntity.itemHandler.extractItem(0, 0, true);
                pEntity.itemHandler.extractItem(1, 1, false);
                pEntity.itemHandler.setStackInSlot(5, ChickenOutput);

                pEntity.resetProgress();

            }else if (pEntity.itemHandler.getStackInSlot(6) == ItemStack.EMPTY) {
                pEntity.itemHandler.extractItem(0, 0, true);
                pEntity.itemHandler.extractItem(1, 1, false);
                pEntity.itemHandler.setStackInSlot(6, ChickenOutput);

                pEntity.resetProgress();

            }else if (pEntity.itemHandler.getStackInSlot(7) == ItemStack.EMPTY) {
                pEntity.itemHandler.extractItem(0, 0, true);
                pEntity.itemHandler.extractItem(1, 1, false);
                pEntity.itemHandler.setStackInSlot(7, ChickenOutput);

                pEntity.resetProgress();

            }else if (pEntity.itemHandler.getStackInSlot(8) == ItemStack.EMPTY) {
                pEntity.itemHandler.extractItem(0, 0, true);
                pEntity.itemHandler.extractItem(1, 1, false);
                pEntity.itemHandler.setStackInSlot(8, ChickenOutput);

                pEntity.resetProgress();

            }else if (pEntity.itemHandler.getStackInSlot(9) == ItemStack.EMPTY) {
                pEntity.itemHandler.extractItem(0, 0, true);
                pEntity.itemHandler.extractItem(1, 1, false);
                pEntity.itemHandler.setStackInSlot(9, ChickenOutput);

                pEntity.resetProgress();

            }
            else {
                pEntity.resetProgress();
            }
        }
        else {
            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(Breeder_Tile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<Breeder_Recipe> recipe = Objects.requireNonNull(level).getRecipeManager()
                .getRecipeFor(Breeder_Recipe.Type.INSTANCE, inventory, level);


        return recipe.isPresent();
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