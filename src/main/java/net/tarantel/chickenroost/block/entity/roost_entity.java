package net.tarantel.chickenroost.block.entity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.tarantel.chickenroost.block.roost_block;
import net.tarantel.chickenroost.handler.roost_handler;
import net.tarantel.chickenroost.network.DataReceiver;
import net.tarantel.chickenroost.recipemanager.Roost_Recipe;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.TagManager;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class roost_entity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory{

    private int SlotNumber = 1;
    private ItemStack newoutput = ItemStack.EMPTY;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = Config.roost_speed_tick.get() * 20;

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

    public roost_entity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROOST, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> roost_entity.this.progress;
                    case 1 -> roost_entity.this.maxProgress;
                    default -> 0;
                };
            }


            public void set(int index, int value) {
                switch (index) {
                    case 0 -> roost_entity.this.progress = value;
                    case 1 -> roost_entity.this.maxProgress = value;
                }
            }

            public int size() {
                return 2;
            }
        };
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = Objects.requireNonNull(this.getWorld()).getBlockState(this.pos).get(roost_block.FACING);


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
                if (stack.isIn(TagManager.Items.CHICKEN)) {
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
        Direction localDir = Objects.requireNonNull(this.getWorld()).getBlockState(this.pos).get(roost_block.FACING);

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
            default -> side.getOpposite() == Direction.SOUTH && slot == 2 ||
                    side.getOpposite() == Direction.EAST && slot == 2 ||
                    side.getOpposite() == Direction.WEST && slot == 2 ||
                    side.getOpposite() == Direction.NORTH && slot == 2 ||
                    side.getOpposite() == Direction.UP && slot == 2 ||
                    side.getOpposite() == Direction.DOWN && slot == 2;


            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == 2 ||
                    side.rotateYClockwise() == Direction.EAST && slot == 2 ||
                    side.rotateYClockwise() == Direction.WEST && slot == 2 ||
                    side.rotateYClockwise() == Direction.NORTH && slot == 2 ||
                    side.rotateYClockwise() == Direction.UP && slot == 2 ||
                    side.rotateYClockwise() == Direction.DOWN && slot == 2;


            case SOUTH -> side == Direction.SOUTH && slot == 2 ||
                    side == Direction.EAST && slot == 2 ||
                    side == Direction.WEST && slot == 2 ||
                    side == Direction.NORTH && slot == 2 ||
                    side == Direction.UP && slot == 2 ||
                    side == Direction.DOWN && slot == 2;


            case WEST -> side == Direction.SOUTH && slot == 2 ||
                    side == Direction.EAST && slot == 2 ||
                    side == Direction.WEST && slot == 2 ||
                    side == Direction.NORTH && slot == 2 ||
                    side == Direction.DOWN && slot == 2 ||
                    side == Direction.UP && slot == 2;


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
        return new roost_handler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("roost.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("roost.progress");
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
    public static void tick(World world, BlockPos blockPos, BlockState state, roost_entity entity) {
        if(world.isClient()) {
            return;
        }
        if(hasRecipe(entity)) {
            entity.progress++;
            markDirty(world, blockPos, state);
            if(entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            markDirty(world, blockPos, state);
        }
    }


    private static void craftItem(roost_entity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        List<Roost_Recipe> recipe = entity.getWorld().getRecipeManager()
                .getAllMatches(Roost_Recipe.Type.INSTANCE, inventory, entity.getWorld());
        int randomoutput;
        randomoutput = MathHelper.nextInt(Random.create(), 0, recipe.size() -1);
        if(hasRecipe(entity)) {
            int ChickenLevel = (int) (entity.getStack(1).getOrCreateNbt().getInt("roost_lvl") / 2 + 1);
            entity.removeStack(0, 1);
            entity.setStack(2, new ItemStack(recipe.get(randomoutput).newOutput().getItem(),
                        entity.getStack(2).getCount() + ChickenLevel));

            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(roost_entity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<Roost_Recipe> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(Roost_Recipe.Type.INSTANCE, inventory, entity.getWorld());


        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().newOutput().getItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }
}