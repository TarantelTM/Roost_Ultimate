//package net.tarantel.chickenroost.block.entity;
//
//import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
//import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
//import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.entity.BlockEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.inventory.Inventories;
//import net.minecraft.inventory.SimpleInventory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NbtCompound;
//import net.minecraft.network.PacketByteBuf;
//import net.minecraft.screen.NamedScreenHandlerFactory;
//import net.minecraft.screen.PropertyDelegate;
//import net.minecraft.screen.ScreenHandler;
//import net.minecraft.server.network.ServerPlayerEntity;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.state.property.IntProperty;
//import net.minecraft.text.Text;
//import net.minecraft.util.collection.DefaultedList;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Direction;
//import net.minecraft.world.World;
//import net.tarantel.chickenroost.block.breeder_block;
//import net.tarantel.chickenroost.handler.breeder_handler;
//import net.tarantel.chickenroost.network.DataReceiver;
//import net.tarantel.chickenroost.recipemanager.BasicBreedingRecipe;
//import net.tarantel.chickenroost.util.Config;
//import net.tarantel.chickenroost.util.TagManager;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//public class breeder_entityy extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory{
//
//    private int SlotNumber = 1;
//    private ItemStack newoutput = ItemStack.EMPTY;
//    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(10, ItemStack.EMPTY);
//    protected final PropertyDelegate propertyDelegate;
//    private int progress = 0;
//    private int maxProgress = Config.breed_speed_tick.get() * 20;
//
//    //private final int SlotNumber = 2;
//
//    public ItemStack getRenderStack() {
//
//        if(this.getStack(1).isEmpty()){
//            return ItemStack.EMPTY;
//        }else {
//            return this.getStack(1);
//        }
//    }
//
//    public void setInventory(DefaultedList<ItemStack> inventory) {
//        for (int i = 0; i < inventory.size(); i++){
//            this.inventory.set(i, inventory.get(i));
//        }
//    }
//
//    @Override
//    public void markDirty() {
//        if(!world.isClient()) {
//            PacketByteBuf data = PacketByteBufs.create();
//            data.writeInt(inventory.size());
//            for(int i = 0; i < inventory.size(); i++) {
//                data.writeItemStack(inventory.get(i));
//            }
//            data.writeBlockPos(getPos());
//
//            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
//                ServerPlayNetworking.send(player, DataReceiver.CHICKEN_SYNC, data);
//            }
//        }
//
//        super.markDirty();
//    }
//
//    public breeder_entityy(BlockPos pos, BlockState state) {
//        super(ModBlockEntities.BREEDER, pos, state);
//        this.propertyDelegate = new PropertyDelegate() {
//            public int get(int index) {
//                return switch (index) {
//                    case 0 -> breeder_entityy.this.progress;
//                    case 1 -> breeder_entityy.this.maxProgress;
//                    default -> 0;
//                };
//            }
//
//
//            public void set(int index, int value) {
//                switch (index) {
//                    case 0 -> breeder_entityy.this.progress = value;
//                    case 1 -> breeder_entityy.this.maxProgress = value;
//                }
//            }
//
//            public int size() {
//                return 2;
//            }
//        };
//    }
//
//    @Override
//    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
//        Direction localDir = Objects.requireNonNull(this.getWorld()).getBlockState(this.pos).get(breeder_block.FACING);
//
//
//        /*if(side == Direction.UP || side == Direction.DOWN) {
//            return false;
//        }*/
//
//        if(slot == 0){
//            if(stack.isIn(TagManager.Items.SEEDS)){
//                return true;
//            }
//            return false;
//        }
//        if (slot == 1) {
//            if(inventory.get(1) == ItemStack.EMPTY) {
//                if (stack.isIn(TagManager.Items.BREEDABLE)) {
//                    return true;
//                }
//            }
//
//            return false;
//        }
//
//
//        // Top insert 1
//        // Right insert 1
//        // Left insert 0
//        return switch (localDir) {
//            default ->
//                    side == Direction.NORTH && slot == 1 || slot == 0 ||
//                            side == Direction.EAST && slot == 1 || slot == 0 ||
//                            side == Direction.WEST && slot == 0 || slot == 1 ||
//                            side == Direction.SOUTH && slot == 0 || slot == 1||
//                            side == Direction.UP && slot == 0 || slot == 1||
//                            side == Direction.DOWN && slot == 0 || slot == 1;
//            case EAST ->
//                    side == Direction.NORTH && slot == 1 || slot == 0 ||
//                            side == Direction.EAST && slot == 1 || slot == 0 ||
//                            side == Direction.WEST && slot == 0 || slot == 1 ||
//                            side == Direction.SOUTH && slot == 0 || slot == 1||
//                            side == Direction.UP && slot == 0 || slot == 1||
//                            side == Direction.DOWN && slot == 0 || slot == 1;
//            case SOUTH ->
//                    side == Direction.NORTH && slot == 1 || slot == 0 ||
//                            side == Direction.EAST && slot == 1 || slot == 0 ||
//                            side == Direction.WEST && slot == 0 || slot == 1 ||
//                            side == Direction.SOUTH && slot == 0 || slot == 1||
//                            side == Direction.UP && slot == 0 || slot == 1||
//                            side == Direction.DOWN && slot == 0 || slot == 1;
//            case WEST ->
//                    side == Direction.NORTH && slot == 1 || slot == 0 ||
//                            side == Direction.EAST && slot == 1 || slot == 0 ||
//                            side == Direction.WEST && slot == 0 || slot == 1 ||
//                            side == Direction.SOUTH && slot == 0 || slot == 1||
//                            side == Direction.UP && slot == 0 || slot == 1||
//                            side == Direction.DOWN && slot == 0 || slot == 1;
//        };
//
//
//    }
//
//    @Override
//    public int getMaxCountPerStack() {
//        //SimpleInventory inventoryy = inventory;
//        if(inventory.equals(1)){
//            return 1;
//        }
//        return 64;
//    }
//
//    @Override
//    public boolean canExtract(int slot, ItemStack stack, Direction side) {
//        Direction localDir = Objects.requireNonNull(this.getWorld()).getBlockState(this.pos).get(breeder_block.FACING);
//
//        /*if(side == Direction.UP) {
//            return false;
//        }*/
//
//
//        // Down extract 2
//        /*if(side == Direction.DOWN) {
//            return slot == 2;
//        }*/
//
//        // bottom extract 2
//        // right extract 2
//        return switch (localDir) {
//            default -> side.getOpposite() == Direction.SOUTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.getOpposite() == Direction.EAST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.getOpposite() == Direction.WEST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.getOpposite() == Direction.NORTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.getOpposite() == Direction.UP && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.getOpposite() == Direction.DOWN && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9;
//
//
//            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.rotateYClockwise() == Direction.EAST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.rotateYClockwise() == Direction.WEST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.rotateYClockwise() == Direction.NORTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.rotateYClockwise() == Direction.UP && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.rotateYClockwise() == Direction.DOWN && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9;
//
//
//            case SOUTH -> side == Direction.SOUTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side == Direction.EAST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side == Direction.WEST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side == Direction.NORTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side == Direction.UP && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side == Direction.DOWN && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9;
//
//
//            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.rotateYCounterclockwise() == Direction.EAST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.rotateYCounterclockwise() == Direction.WEST && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.rotateYCounterclockwise() == Direction.NORTH && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.rotateYCounterclockwise() == Direction.DOWN && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9 ||
//                    side.rotateYCounterclockwise() == Direction.UP && slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8 || slot == 9;
//
//
//        };
//    }
//
//
//    @Override
//    public DefaultedList<ItemStack> getItems() {
//        return this.inventory;
//    }
//
//    @Override
//    public Text getDisplayName() {
//        return Text.literal("");
//    }
//
//    @Nullable
//    @Override
//    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
//        return new breeder_handler(syncId, inv, this, this.propertyDelegate);
//    }
//
//    @Override
//    protected void writeNbt(NbtCompound nbt) {
//        super.writeNbt(nbt);
//        Inventories.writeNbt(nbt, inventory);
//        nbt.putInt("breeder.progress", progress);
//    }
//
//    @Override
//    public void readNbt(NbtCompound nbt) {
//        Inventories.readNbt(nbt, inventory);
//        super.readNbt(nbt);
//        progress = nbt.getInt("breeder.progress");
//    }
//
//    private void resetProgress() {
//        this.progress = 0;
//    }
//
//    @Override
//    public NbtCompound toInitialChunkDataNbt() {
//        NbtCompound nbtCompound = new NbtCompound();
//        Inventories.writeNbt(nbtCompound, this.inventory, true);
//        return nbtCompound;
//    }
//    public static void tick(World world, BlockPos blockPos, BlockState state, breeder_entityy entity) {
//        if(world.isClient()) {
//            return;
//        }
//        //markDirty(world, blockPos, state);
//        int x = blockPos.getX();
//        int y = blockPos.getY();
//        int z = blockPos.getZ();
//
//        if(hasRecipe(entity)) {
//            {
//                int _value = 2;
//                var _bs = state;
//                if (_bs.getBlock().getStateManager().getProperty("mynewstate") instanceof IntProperty _integerProp && _integerProp.getValues().contains(_value))
//                    world.setBlockState(blockPos, _bs.with(_integerProp, _value), 3);
//            }
//            entity.progress++;
//            markDirty(world, blockPos, state);
//            if(entity.progress >= entity.maxProgress) {
//                craftItem(entity);
//            }
//        } else {
//            {
//                int _value = 1;
//                if (state.getBlock().getStateManager().getProperty("mynewstate") instanceof IntProperty _integerProp && _integerProp.getValues().contains(_value))
//                    world.setBlockState(blockPos, state.with(_integerProp, _value), 3);
//            }
//            entity.resetProgress();
//            markDirty(world, blockPos, state);
//        }
//    }
//
//
//    private static void craftItem(breeder_entityy entity) {
//        SimpleInventory inventory = new SimpleInventory(entity.size());
//        for (int i = 0; i < entity.size(); i++) {
//            inventory.setStack(i, entity.getStack(i));
//        }
//        int sloti = entity.SlotNumber;
//
//        List<BasicBreedingRecipe> recipe = Objects.requireNonNull(entity.getWorld()).getRecipeManager()
//                .getAllMatches(BasicBreedingRecipe.Type.INSTANCE, inventory, entity.getWorld());
//
//        Optional<BasicBreedingRecipe> match = Objects.requireNonNull(entity.getWorld()).getRecipeManager()
//                .getFirstMatch(BasicBreedingRecipe.Type.INSTANCE, inventory, entity.getWorld());
//
//        //if(entity.SlotNumber >= 9){
//         //   entity.SlotNumber = 1;
//
//        if(match.isPresent()) {
//            for(int e = 2; e < 9; e++) {
//                entity.SlotNumber ++;
//                System.out.println("craftItem:match.isPresent - " + entity.SlotNumber + "ITEM:" + entity.newoutput.getItem().asItem().toString() + "INFOS: " + recipe.size());
//                if(entity.SlotNumber >= 9){
//                    entity.SlotNumber = 2;
//                }
//                for ()
//                for (int i = 0; i < recipe.size(); i++) {
//
//                        if ((inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack() || inventory.getStack(e) == ItemStack.EMPTY)
//                                || (inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack()
//                                && inventory.getStack(e).getMaxCount() > inventory.getStack(e).getCount())) {
//                            entity.newoutput = recipe.get(i).getOutput();
//
//
//                            //for(int f = 2; f < inventory.size(); f++) {
//
//                                //if(entity.getStack(entity.SlotNumber) == entity.newoutput) {
//                                    entity.removeStack(0, 1);
//                                    entity.setStack(entity.SlotNumber, new ItemStack(entity.newoutput.getItem(), entity.getStack(entity.SlotNumber).getCount() + 1));
//                                    System.out.println("craftItem:match.isPresent - ITEM:" + entity.newoutput.getItem().asItem().toString());
//                                    //entity.SlotNumber = 1;
//                                    entity.resetProgress();
//                                    break;
//                                //}
//
//
//                            }
//
//                        }
//                break;
//
//                }}
//            //int randomoutput;
//            //randomoutput = MathHelper.nextInt(Random.create(), 0, recipe.size() -1);
//
//            //ItemStack RandomChicken = ItemStack.EMPTY;
//            //RandomChicken = new ItemStack(recipe.get(randomoutput).getOutput().getItem());
//        //entity.removeStack(0, 1);
//
//            /*if(entity.getStack(2) == ItemStack.EMPTY || entity.getStack(2) == RandomChicken){
//                entity.setStack(2, new ItemStack(RandomChicken.getItem(), entity.getStack(2).getCount() + 1));
//
//            } else if (entity.getStack(3) == ItemStack.EMPTY || entity.getStack(3) == RandomChicken) {
//                entity.setStack(3, new ItemStack(RandomChicken.getItem(), entity.getStack(3).getCount() + 1));
//
//            }else if (entity.getStack(4) == ItemStack.EMPTY || entity.getStack(4) == RandomChicken) {
//                entity.setStack(4, new ItemStack(RandomChicken.getItem(), entity.getStack(4).getCount() + 1));
//
//            }else if (entity.getStack(5) == ItemStack.EMPTY || entity.getStack(5) == RandomChicken) {
//                entity.setStack(5, new ItemStack(RandomChicken.getItem(), entity.getStack(5).getCount() + 1));
//
//            }else if (entity.getStack(6) == ItemStack.EMPTY || entity.getStack(6) == RandomChicken) {
//                entity.setStack(6, new ItemStack(RandomChicken.getItem(), entity.getStack(6).getCount() + 1));
//
//            }else if (entity.getStack(7) == ItemStack.EMPTY || entity.getStack(7) == RandomChicken) {
//                entity.setStack(7, new ItemStack(RandomChicken.getItem(), entity.getStack(7).getCount() + 1));
//
//            }else if (entity.getStack(8) == ItemStack.EMPTY || entity.getStack(8) == RandomChicken) {
//                entity.setStack(8, new ItemStack(RandomChicken.getItem(), entity.getStack(8).getCount() + 1));
//
//            }else if (entity.getStack(9) == ItemStack.EMPTY || entity.getStack(9) == RandomChicken) {
//                entity.setStack(9, new ItemStack(RandomChicken.getItem(), entity.getStack(9).getCount() + 1));
//
//            }*/
//
//
//            /*for(int e = 2; e < inventory.size(); e++) {
//
//                if(entity.getStack(e) == entity.newoutput) {
//                    entity.setStack(e, new ItemStack(entity.newoutput.getItem(), entity.getStack(e).getCount() + 1));
//                    System.out.println(entity.newoutput.toString() + e);
//                    //entity.SlotNumber = 1;
//
//                    break;
//                }
//                entity.resetProgress();
//                }*/
//
//
//
//        }
//
//
//    private static boolean hasRecipe(breeder_entityy entity) {
//        SimpleInventory inventory = new SimpleInventory(entity.size());
//        for (int i = 0; i < entity.size(); i++) {
//            inventory.setStack(i, entity.getStack(i));
//        }
//
//        List<BasicBreedingRecipe> recipe = Objects.requireNonNull(entity.getWorld()).getRecipeManager()
//                .getAllMatches(BasicBreedingRecipe.Type.INSTANCE, inventory, entity.getWorld());
//
//        Optional<BasicBreedingRecipe> match = Objects.requireNonNull(entity.getWorld()).getRecipeManager()
//                .getFirstMatch(BasicBreedingRecipe.Type.INSTANCE, inventory, entity.getWorld());
//
//        for(int e = 2; e < 9; e++) {
//            //entity.SlotNumber ++;
//            //System.out.println(entity.SlotNumber);
//            //if(entity.SlotNumber >= 9){
//            //    entity.SlotNumber = 1;
//           // }
//            for (int i = 0; i < recipe.size(); i++) {
//                if(match.isPresent()){
//                if ((inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack() || inventory.getStack(e) == ItemStack.EMPTY)
//                        || (inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack()
//                        && inventory.getStack(e).getMaxCount() > inventory.getStack(e).getCount())) {
//                    //entity.newoutput = recipe.get(i).getOutput().copy();
//                    //System.out.println(entity.SlotNumber);
//
//
//                    return true;
//                }}
//
//            }}
//
//        return false;
//
//    }
//
//    /*private static boolean containsItemStack(breeder_entity entity, final  Item item){
//        SimpleInventory inventory = new SimpleInventory(entity.size());
//        for (int i = 0; i < entity.size(); i++) {
//            inventory.setStack(i, entity.getStack(i));
//        }
//
//        List<BasicBreedingRecipe> recipe = Objects.requireNonNull(entity.getWorld()).getRecipeManager()
//                .getAllMatches(BasicBreedingRecipe.Type.INSTANCE, inventory, entity.getWorld());
//        for(int e = 2; e < inventory.size(); e++) {
//            for (int i = 0; i < recipe.size(); i++) {
//                if ((inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack() || inventory.getStack(e) == ItemStack.EMPTY)
//                        || (inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack()
//                        && inventory.getStack(e).getMaxCount() > inventory.getStack(e).getCount())) {
//                    return !recipe.isEmpty() && true;
//                }*/
//                /*} else if ((inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack() || inventory.getStack(3) == ItemStack.EMPTY)
//                        || (inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack()
//                        && inventory.getStack(e).getMaxCount() > inventory.getStack(3).getCount())) {
//                    return true;
//                } else if ((inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack() || inventory.getStack(4) == ItemStack.EMPTY)
//                        || (inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack()
//                        && inventory.getStack(e).getMaxCount() > inventory.getStack(4).getCount())) {
//                    return true;
//                } else if ((inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack() || inventory.getStack(5) == ItemStack.EMPTY)
//                        || (inventory.getStack(5) == recipe.get(i).getOutput().getItem().getDefaultStack()
//                        && inventory.getStack(5).getMaxCount() > inventory.getStack(5).getCount())) {
//                    return true;
//                } else if ((inventory.getStack(6) == recipe.get(i).getOutput().getItem().getDefaultStack() || inventory.getStack(6) == ItemStack.EMPTY)
//                        || (inventory.getStack(6) == recipe.get(i).getOutput().getItem().getDefaultStack()
//                        && inventory.getStack(6).getMaxCount() > inventory.getStack(6).getCount())) {
//                    return true;
//                } else if ((inventory.getStack(7) == recipe.get(i).getOutput().getItem().getDefaultStack() || inventory.getStack(7) == ItemStack.EMPTY)
//                        || (inventory.getStack(7) == recipe.get(i).getOutput().getItem().getDefaultStack()
//                        && inventory.getStack(7).getMaxCount() > inventory.getStack(7).getCount())) {
//                    return true;
//                } else if ((inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack() || inventory.getStack(8) == ItemStack.EMPTY)
//                        || (inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack()
//                        && inventory.getStack(e).getMaxCount() > inventory.getStack(8).getCount())) {
//                    return true;
//                } else if ((inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack() || inventory.getStack(e) == ItemStack.EMPTY)
//                        || (inventory.getStack(e) == recipe.get(i).getOutput().getItem().getDefaultStack()
//                        && inventory.getStack(e).getMaxCount() > inventory.getStack(9).getCount())) {
//                    return true;
//                }*/
//
//           // }
//      //  }
//      //  return false;
//   // }
//    /*private static boolean canInsertItemIntoOutputSlot1(SimpleInventory inventory, Item output) {
//        return inventory.getStack(2).getItem() == output
//                || inventory.getStack(2).isEmpty();
//    }
//    private static boolean canInsertItemIntoOutputSlot2(SimpleInventory inventory, Item output) {
//        return inventory.getStack(3).getItem() == output
//                || inventory.getStack(3).isEmpty();
//    }
//    private static boolean canInsertItemIntoOutputSlot3(SimpleInventory inventory, Item output) {
//        return inventory.getStack(4).getItem() == output
//                || inventory.getStack(4).isEmpty();
//    }
//    private static boolean canInsertItemIntoOutputSlot4(SimpleInventory inventory, Item output) {
//        return inventory.getStack(5).getItem() == output
//                || inventory.getStack(5).isEmpty();
//    }
//    private static boolean canInsertItemIntoOutputSlot5(SimpleInventory inventory, Item output) {
//        return inventory.getStack(6).getItem() == output
//                || inventory.getStack(6).isEmpty();
//    }
//    private static boolean canInsertItemIntoOutputSlot6(SimpleInventory inventory, Item output) {
//        return inventory.getStack(7).getItem() == output
//                || inventory.getStack(7).isEmpty();
//    }
//    private static boolean canInsertItemIntoOutputSlot7(SimpleInventory inventory, Item output) {
//        return inventory.getStack(8).getItem() == output
//                || inventory.getStack(8).isEmpty();
//    }
//    private static boolean canInsertItemIntoOutputSlot8(SimpleInventory inventory, Item output) {
//        return inventory.getStack(9).getItem() == output
//                || inventory.getStack(9).isEmpty();
//    }
//
//
//
//    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
//        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
//    }*/
//
//
//}
//
//
//
//
//