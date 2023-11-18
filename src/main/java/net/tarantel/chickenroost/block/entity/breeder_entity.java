package net.tarantel.chickenroost.block.entity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.tarantel.chickenroost.block.breeder_block;
import net.tarantel.chickenroost.handler.breeder_handler;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.network.DataReceiver;
import net.tarantel.chickenroost.recipemanager.BasicBreedingRecipe;
import net.tarantel.chickenroost.recipemanager.ModRecipes;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.TagManager;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class breeder_entity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory{

    private int SlotNumber = 1;
    private ItemStack newoutput = ItemStack.EMPTY;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(10, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = Config.breed_speed_tick.get() * 20;

    //private final int SlotNumber = 2;

    public ItemStack getRenderStack() {

        if(this.getStack(1).isEmpty()){
            return ItemStack.EMPTY;
        }else {
            return this.getStack(1);
        }
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++){
            this.inventory.set(i, inventory.get(i));
        }
    }

    @Override
    public void markDirty() {
        if(!world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for(int i = 0; i < inventory.size(); i++) {
                data.writeItemStack(inventory.get(i));
            }
            data.writeBlockPos(getPos());

            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, DataReceiver.CHICKEN_SYNC, data);
            }
        }

        super.markDirty();
    }

    public breeder_entity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BREEDER, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> breeder_entity.this.progress;
                    case 1 -> breeder_entity.this.maxProgress;
                    default -> 0;
                };
            }


            public void set(int index, int value) {
                switch (index) {
                    case 0 -> breeder_entity.this.progress = value;
                    case 1 -> breeder_entity.this.maxProgress = value;
                }
            }

            public int size() {
                return 2;
            }
        };
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = Objects.requireNonNull(this.getWorld()).getBlockState(this.pos).get(breeder_block.FACING);


        /*if(side == Direction.UP || side == Direction.DOWN) {
            return false;
        }*/

        if(slot == 0){
            if(stack.isIn(TagManager.Items.SEEDS)){
                return true;
            }
            return false;
        }
        if (slot == 1) {
            if(inventory.get(1) == ItemStack.EMPTY) {
                if (stack.isIn(TagManager.Items.BREEDABLE)) {
                    return true;
                }
            }

            return false;
        }


        // Top insert 1
        // Right insert 1
        // Left insert 0
        return switch (localDir) {
            default ->
                    side == Direction.NORTH && slot == 1 || slot == 0 ||
                            side == Direction.EAST && slot == 1 || slot == 0 ||
                            side == Direction.WEST && slot == 0 || slot == 1 ||
                            side == Direction.SOUTH && slot == 0 || slot == 1||
                            side == Direction.UP && slot == 0 || slot == 1||
                            side == Direction.DOWN && slot == 0 || slot == 1;
            case EAST ->
                    side == Direction.NORTH && slot == 1 || slot == 0 ||
                            side == Direction.EAST && slot == 1 || slot == 0 ||
                            side == Direction.WEST && slot == 0 || slot == 1 ||
                            side == Direction.SOUTH && slot == 0 || slot == 1||
                            side == Direction.UP && slot == 0 || slot == 1||
                            side == Direction.DOWN && slot == 0 || slot == 1;
            case SOUTH ->
                    side == Direction.NORTH && slot == 1 || slot == 0 ||
                            side == Direction.EAST && slot == 1 || slot == 0 ||
                            side == Direction.WEST && slot == 0 || slot == 1 ||
                            side == Direction.SOUTH && slot == 0 || slot == 1||
                            side == Direction.UP && slot == 0 || slot == 1||
                            side == Direction.DOWN && slot == 0 || slot == 1;
            case WEST ->
                    side == Direction.NORTH && slot == 1 || slot == 0 ||
                            side == Direction.EAST && slot == 1 || slot == 0 ||
                            side == Direction.WEST && slot == 0 || slot == 1 ||
                            side == Direction.SOUTH && slot == 0 || slot == 1||
                            side == Direction.UP && slot == 0 || slot == 1||
                            side == Direction.DOWN && slot == 0 || slot == 1;
        };


    }

    @Override
    public int getMaxCountPerStack() {
        //SimpleInventory inventoryy = inventory;
        if(inventory.equals(1)){
            return 1;
        }
        return 64;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = Objects.requireNonNull(this.getWorld()).getBlockState(this.pos).get(breeder_block.FACING);

        /*if(side == Direction.UP) {
            return false;
        }*/


        // Down extract 2
        /*if(side == Direction.DOWN) {
            return slot == 2;
        }*/

        // bottom extract 2
        // right extract 2
        return switch (localDir) {
            default -> side.getOpposite() == Direction.SOUTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side.getOpposite() == Direction.EAST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side.getOpposite() == Direction.WEST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side.getOpposite() == Direction.NORTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side.getOpposite() == Direction.UP && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side.getOpposite() == Direction.DOWN && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9;


            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side.rotateYClockwise() == Direction.EAST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side.rotateYClockwise() == Direction.WEST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side.rotateYClockwise() == Direction.NORTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side.rotateYClockwise() == Direction.UP && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side.rotateYClockwise() == Direction.DOWN && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9;


            case SOUTH -> side == Direction.SOUTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side == Direction.EAST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side == Direction.WEST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side == Direction.NORTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side == Direction.UP && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side == Direction.DOWN && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9;


            case WEST -> side == Direction.SOUTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side == Direction.EAST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side == Direction.WEST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side == Direction.NORTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side == Direction.DOWN && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
                    side == Direction.UP && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9;


        };
    }


    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new breeder_handler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("breeder.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("breeder.progress");
    }

    private void resetProgress() {
        this.progress = 0;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, this.inventory, true);
        return nbtCompound;
    }
    public static void tick(World world, BlockPos blockPos, BlockState state, breeder_entity entity) {
        if(world.isClient()) {
            return;
        }
        //markDirty(world, blockPos, state);
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();

        if(hasRecipe(entity)) {
            {
                int _value = 2;
                var _bs = state;
                if (_bs.getBlock().getStateManager().getProperty("mynewstate") instanceof IntProperty _integerProp && _integerProp.getValues().contains(_value))
                    world.setBlockState(blockPos, _bs.with(_integerProp, _value), 3);
            }
            entity.progress++;
            markDirty(world, blockPos, state);
            if(entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            {
                int _value = 1;
                if (state.getBlock().getStateManager().getProperty("mynewstate") instanceof IntProperty _integerProp && _integerProp.getValues().contains(_value))
                    world.setBlockState(blockPos, state.with(_integerProp, _value), 3);
            }
            entity.resetProgress();
            markDirty(world, blockPos, state);
        }
    }


    private static void craftItem(breeder_entity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        List<BasicBreedingRecipe> recipe = entity.getWorld().getRecipeManager()
                .getAllMatches(BasicBreedingRecipe.Type.INSTANCE, inventory, entity.getWorld());
        int randomoutput;
        randomoutput = MathHelper.nextInt(Random.create(), 0, recipe.size() -1);
        if(hasRecipe(entity)) {
            entity.removeStack(0, 1);

            if(entity.getStack(2).isEmpty()){
                entity.setStack(2, new ItemStack(recipe.get(randomoutput).newOutput().getItem(),
                        entity.getStack(2).getCount() + 1));
            } else if (entity.getStack(3).isEmpty()){
                entity.setStack(3, new ItemStack(recipe.get(randomoutput).newOutput().getItem(),
                        entity.getStack(3).getCount() + 1));

            }else if (entity.getStack(4).isEmpty()){
                entity.setStack(4, new ItemStack(recipe.get(randomoutput).newOutput().getItem(),
                        entity.getStack(4).getCount() + 1));

            }else if (entity.getStack(5).isEmpty()){
                entity.setStack(5, new ItemStack(recipe.get(randomoutput).newOutput().getItem(),
                        entity.getStack(5).getCount() + 1));

            }else if (entity.getStack(6).isEmpty()){
                entity.setStack(6, new ItemStack(recipe.get(randomoutput).newOutput().getItem(),
                        entity.getStack(6).getCount() + 1));

            }else if (entity.getStack(7).isEmpty()){
                entity.setStack(7, new ItemStack(recipe.get(randomoutput).newOutput().getItem(),
                        entity.getStack(7).getCount() + 1));

            }else if (entity.getStack(8).isEmpty()){
                entity.setStack(8, new ItemStack(recipe.get(randomoutput).newOutput().getItem(),
                        entity.getStack(8).getCount() + 1));

            }else if (entity.getStack(9).isEmpty()){
                entity.setStack(9, new ItemStack(recipe.get(randomoutput).newOutput().getItem(),
                        entity.getStack(9).getCount() + 1));

            }


            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(breeder_entity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<BasicBreedingRecipe> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(BasicBreedingRecipe.Type.INSTANCE, inventory, entity.getWorld());

        if(match.isPresent()){
            for(int i = 2; i <= 9; i++){
                if(inventory.getStack(i).isEmpty()){
                    return true;
                }

            }

        }
        return false;
        /*return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput().getItem());*/
    }

    /*private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack(2).isEmpty();
    }*/

    /*private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }*/
}