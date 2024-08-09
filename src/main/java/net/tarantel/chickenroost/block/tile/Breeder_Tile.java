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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.handler.Breeder_Handler;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.recipes.Breeder_Recipe;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Breeder_Tile extends BlockEntity implements MenuProvider {
    public static int x;
    public static int y;
    public static int z;
    public final ItemStackHandler itemHandler = new ItemStackHandler(10) {
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
                case 0 -> (stack.getItem() instanceof ChickenItemBase);
                case 1 -> (stack.is(ItemTags.create(ChickenRoostMod.commonsource("seeds/tiered"))));
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
    //private BlockCapabilityCache<IItemHandler, @Nullable Direction> capCache;
    //private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
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
    public int maxProgress = ( Config.breed_speed_tick.get() * 20);

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
        return Component.translatable("");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new Breeder_Handler(id, inventory, this, this.data);
    }

    private final Map<Direction, Lazy<WrappedHandler>> directionWrappedHandlerMap =
            new HashMap<>();


    private final IItemHandler itemHandlerSided = new InputOutputItemHandler(itemHandler, (i, stack) -> i == 0 || i == 1, i -> i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9);


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
        nbt.putInt("breeder.progress", this.progress);
        super.saveAdditional(nbt, lookup);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider lookup) {
        super.loadAdditional(nbt, lookup);
        itemHandler.deserializeNBT(lookup,nbt.getCompound("inventory"));
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
    static String outpit;
    private static void craftItem(Breeder_Tile pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        List<RecipeHolder<Breeder_Recipe>> recipe= level.getRecipeManager().getRecipesFor(Breeder_Recipe.Type.INSTANCE, getRecipeInput(inventory), level);
        /*if (level != null) {
            recipe = level.getRecipeManager().getRecipesFor(Breeder_Recipe.Type.INSTANCE, getRecipeInput(inventory), level);
        }*/
        if(hasRecipe(pEntity)) {
            //outpit = pEntity.itemHandler.getStackInSlot(0).getOrCreateTag().getString("output");
            //Item elseitem = pEntity.itemHandler.getStackInSlot(0).getItem().asItem();
            //int RandomOutput;
            //RandomOutput = Mth.nextInt(RandomSource.create(), 0, (recipe.size() -1));
            Random ran = new Random();
            int RandomOutputs = ran.nextInt(recipe.size());


            //ChickenOutput = new ItemStack((ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(ResourceLocation.withDefaultNamespace(outpit))).getRandomElement(RandomSource.create()).orElseGet(() -> elseitem)));
            ChickenOutput = new ItemStack(recipe.get(RandomOutputs).value().output.getItem());
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

        Optional<RecipeHolder<Breeder_Recipe>> recipe = Optional.empty();
        if (level != null) {
            recipe = level.getRecipeManager().getRecipeFor(ModRecipes.BASIC_BREEDING_TYPE.get(), getRecipeInput(inventory), level);
        }


        return recipe.isPresent();
    }
    public static RecipeInput getRecipeInput(SimpleContainer inventory) {
        return new RecipeInput() {
            @Override
            public ItemStack getItem(int index) {
                return inventory.getItem(index).copy();
            }

            @Override
            public int size() {
                return inventory.getContainerSize();
            }
        };
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