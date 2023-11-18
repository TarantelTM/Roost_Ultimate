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
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.tarantel.chickenroost.block.trainer_block;
import net.tarantel.chickenroost.handler.trainer_handler;
import net.tarantel.chickenroost.network.DataReceiver;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.MyItemTags;
import net.tarantel.chickenroost.util.TagManager;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class trainer_entity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory{

    private int SlotNumber = 1;
    private ItemStack newoutput = ItemStack.EMPTY;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = Config.training_speed_tick.get() * 20;


    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++){
            this.inventory.set(i, inventory.get(i));
        }
    }

    /*@Override
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
    }*/

    public trainer_entity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAINER, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> trainer_entity.this.progress;
                    case 1 -> trainer_entity.this.maxProgress;
                    default -> 0;
                };
            }


            public void set(int index, int value) {
                switch (index) {
                    case 0 -> trainer_entity.this.progress = value;
                    case 1 -> trainer_entity.this.maxProgress = value;
                }
            }

            public int size() {
                return 2;
            }
        };
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = Objects.requireNonNull(this.getWorld()).getBlockState(this.pos).get(trainer_block.FACING);


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
        return new trainer_handler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("trainer.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("trainer.progress");
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
    //static ItemStack MyChicken;
    public static void craftItem(trainer_entity pEntity) {
        SimpleInventory inventory = new SimpleInventory(pEntity.size());
        for (int i = 0; i < pEntity.size(); i++) {
            inventory.setStack(i, pEntity.getStack(i));
        }

        if (pEntity.getStack(1).isIn((MyItemTags.of("fabric:roost/chickens"))) &&
                (pEntity.getStack(0).isIn((MyItemTags.of("fabric:seeds/tiered"))) || (pEntity.getStack(0).isIn((MyItemTags.of("fabric:seeds")))))) {
            //MyChicken = pEntity.getStack(1);
            int ChickenLevel = pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl");
            /*if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier1"))) && ChickenLevel >= Config.maxlevel_tier_1.get()) {
                pEntity.resetProgress();
            }*/
            int ChickenXP = pEntity.getStack(1).getOrCreateNbt().getInt("roost_xp");
            /////TIER 1
            if (pEntity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier1"))) {
                if (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_1.get()) {
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds"))) {
                        if (ChickenXP + Config.trainingxp_perfood.get() >= Config.xp_tier_1.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);

                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.trainingxp_perfood.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier1"))) {
                        if (ChickenXP + Config.food_xp_tier_1.get() >= Config.xp_tier_1.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_1.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier2"))) {
                        if (ChickenXP + Config.food_xp_tier_2.get() >= Config.xp_tier_1.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_2.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier3"))) {
                        if (ChickenXP + Config.food_xp_tier_3.get() >= Config.xp_tier_1.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_3.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier4"))) {
                        if (ChickenXP + Config.food_xp_tier_4.get() >= Config.xp_tier_1.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_4.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier5"))) {
                        if (ChickenXP + Config.food_xp_tier_5.get() >= Config.xp_tier_1.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_5.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier6"))) {
                        if (ChickenXP + Config.food_xp_tier_6.get() >= Config.xp_tier_1.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_6.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier7"))) {
                        if (ChickenXP + Config.food_xp_tier_7.get() >= Config.xp_tier_1.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_7.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier8"))) {
                        if (ChickenXP + Config.food_xp_tier_8.get() >= Config.xp_tier_1.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_8.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier9"))) {
                        if (ChickenXP + Config.food_xp_tier_9.get() >= Config.xp_tier_1.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_9.get());
                        }
                    }
                }

                pEntity.removeStack(0, 1);
                //pEntity.getStack(1).getOrCreateNbt().putInt();
                //pEntity.setStack(1, MyChicken.getItem().asItem().getDefaultStack());
                pEntity.resetProgress();
            }
            /////TIER 2
            if (pEntity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier2"))) {
                if (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_2.get()) {
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds"))) {
                        if (ChickenXP + Config.trainingxp_perfood.get() >= Config.xp_tier_2.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);

                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.trainingxp_perfood.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier1"))) {
                        if (ChickenXP + Config.food_xp_tier_1.get() >= Config.xp_tier_2.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_1.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier2"))) {
                        if (ChickenXP + Config.food_xp_tier_2.get() >= Config.xp_tier_2.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_2.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier3"))) {
                        if (ChickenXP + Config.food_xp_tier_3.get() >= Config.xp_tier_2.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_3.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier4"))) {
                        if (ChickenXP + Config.food_xp_tier_4.get() >= Config.xp_tier_2.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_4.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier5"))) {
                        if (ChickenXP + Config.food_xp_tier_5.get() >= Config.xp_tier_2.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_5.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier6"))) {
                        if (ChickenXP + Config.food_xp_tier_6.get() >= Config.xp_tier_2.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_6.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier7"))) {
                        if (ChickenXP + Config.food_xp_tier_7.get() >= Config.xp_tier_2.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_7.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier8"))) {
                        if (ChickenXP + Config.food_xp_tier_8.get() >= Config.xp_tier_2.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_8.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier9"))) {
                        if (ChickenXP + Config.food_xp_tier_9.get() >= Config.xp_tier_2.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_9.get());
                        }
                    }
                }

                pEntity.removeStack(0, 1);
                //pEntity.getStack(1).getOrCreateNbt().putInt();
                //pEntity.setStack(1, MyChicken.getItem().asItem().getDefaultStack());
                pEntity.resetProgress();
            }
            /////TIER 3
            if (pEntity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier3"))) {
                if (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_3.get()) {
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds"))) {
                        if (ChickenXP + Config.trainingxp_perfood.get() >= Config.xp_tier_3.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);

                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.trainingxp_perfood.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier1"))) {
                        if (ChickenXP + Config.food_xp_tier_1.get() >= Config.xp_tier_3.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_1.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier2"))) {
                        if (ChickenXP + Config.food_xp_tier_2.get() >= Config.xp_tier_3.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_2.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier3"))) {
                        if (ChickenXP + Config.food_xp_tier_3.get() >= Config.xp_tier_3.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_3.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier4"))) {
                        if (ChickenXP + Config.food_xp_tier_4.get() >= Config.xp_tier_3.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_4.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier5"))) {
                        if (ChickenXP + Config.food_xp_tier_5.get() >= Config.xp_tier_3.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_5.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier6"))) {
                        if (ChickenXP + Config.food_xp_tier_6.get() >= Config.xp_tier_3.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_6.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier7"))) {
                        if (ChickenXP + Config.food_xp_tier_7.get() >= Config.xp_tier_3.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_7.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier8"))) {
                        if (ChickenXP + Config.food_xp_tier_8.get() >= Config.xp_tier_3.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_8.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier9"))) {
                        if (ChickenXP + Config.food_xp_tier_9.get() >= Config.xp_tier_3.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_9.get());
                        }
                    }
                }

                pEntity.removeStack(0, 1);
                //pEntity.getStack(1).getOrCreateNbt().putInt();
                //pEntity.setStack(1, MyChicken.getItem().asItem().getDefaultStack());
                pEntity.resetProgress();
            }
            /////TIER 4
            if (pEntity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier4"))) {
                if (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_4.get()) {
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds"))) {
                        if (ChickenXP + Config.trainingxp_perfood.get() >= Config.xp_tier_4.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);

                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.trainingxp_perfood.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier1"))) {
                        if (ChickenXP + Config.food_xp_tier_1.get() >= Config.xp_tier_4.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_1.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier2"))) {
                        if (ChickenXP + Config.food_xp_tier_2.get() >= Config.xp_tier_4.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_2.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier3"))) {
                        if (ChickenXP + Config.food_xp_tier_3.get() >= Config.xp_tier_4.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_3.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier4"))) {
                        if (ChickenXP + Config.food_xp_tier_4.get() >= Config.xp_tier_4.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_4.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier5"))) {
                        if (ChickenXP + Config.food_xp_tier_5.get() >= Config.xp_tier_4.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_5.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier6"))) {
                        if (ChickenXP + Config.food_xp_tier_6.get() >= Config.xp_tier_4.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_6.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier7"))) {
                        if (ChickenXP + Config.food_xp_tier_7.get() >= Config.xp_tier_4.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_7.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier8"))) {
                        if (ChickenXP + Config.food_xp_tier_8.get() >= Config.xp_tier_4.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_8.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier9"))) {
                        if (ChickenXP + Config.food_xp_tier_9.get() >= Config.xp_tier_4.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_9.get());
                        }
                    }
                }

                pEntity.removeStack(0, 1);
                //pEntity.getStack(1).getOrCreateNbt().putInt();
                //pEntity.setStack(1, MyChicken.getItem().asItem().getDefaultStack());
                pEntity.resetProgress();
            }
            /////TIER 5
            if (pEntity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier5"))) {
                if (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_5.get()) {
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds"))) {
                        if (ChickenXP + Config.trainingxp_perfood.get() >= Config.xp_tier_5.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);

                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.trainingxp_perfood.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier1"))) {
                        if (ChickenXP + Config.food_xp_tier_1.get() >= Config.xp_tier_5.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_1.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier2"))) {
                        if (ChickenXP + Config.food_xp_tier_2.get() >= Config.xp_tier_5.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_2.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier3"))) {
                        if (ChickenXP + Config.food_xp_tier_3.get() >= Config.xp_tier_5.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_3.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier4"))) {
                        if (ChickenXP + Config.food_xp_tier_4.get() >= Config.xp_tier_5.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_4.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier5"))) {
                        if (ChickenXP + Config.food_xp_tier_5.get() >= Config.xp_tier_5.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_5.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier6"))) {
                        if (ChickenXP + Config.food_xp_tier_6.get() >= Config.xp_tier_5.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_6.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier7"))) {
                        if (ChickenXP + Config.food_xp_tier_7.get() >= Config.xp_tier_5.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_7.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier8"))) {
                        if (ChickenXP + Config.food_xp_tier_8.get() >= Config.xp_tier_5.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_8.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier9"))) {
                        if (ChickenXP + Config.food_xp_tier_9.get() >= Config.xp_tier_5.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_9.get());
                        }
                    }
                }

                pEntity.removeStack(0, 1);
                //pEntity.getStack(1).getOrCreateNbt().putInt();
                //pEntity.setStack(1, MyChicken.getItem().asItem().getDefaultStack());
                pEntity.resetProgress();
            }
            /////TIER 6
            if (pEntity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier6"))) {
                if (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_6.get()) {
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds"))) {
                        if (ChickenXP + Config.trainingxp_perfood.get() >= Config.xp_tier_6.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);

                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.trainingxp_perfood.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier1"))) {
                        if (ChickenXP + Config.food_xp_tier_1.get() >= Config.xp_tier_6.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_1.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier2"))) {
                        if (ChickenXP + Config.food_xp_tier_2.get() >= Config.xp_tier_6.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_2.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier3"))) {
                        if (ChickenXP + Config.food_xp_tier_3.get() >= Config.xp_tier_6.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_3.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier4"))) {
                        if (ChickenXP + Config.food_xp_tier_4.get() >= Config.xp_tier_6.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_4.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier5"))) {
                        if (ChickenXP + Config.food_xp_tier_5.get() >= Config.xp_tier_6.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_5.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier6"))) {
                        if (ChickenXP + Config.food_xp_tier_6.get() >= Config.xp_tier_6.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_6.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier7"))) {
                        if (ChickenXP + Config.food_xp_tier_7.get() >= Config.xp_tier_6.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_7.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier8"))) {
                        if (ChickenXP + Config.food_xp_tier_8.get() >= Config.xp_tier_6.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_8.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier9"))) {
                        if (ChickenXP + Config.food_xp_tier_9.get() >= Config.xp_tier_6.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_9.get());
                        }
                    }
                }

                pEntity.removeStack(0, 1);
                //pEntity.getStack(1).getOrCreateNbt().putInt();
                //pEntity.setStack(1, MyChicken.getItem().asItem().getDefaultStack());
                pEntity.resetProgress();
            }
            /////TIER 7
            if (pEntity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier7"))) {
                if (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_7.get()) {
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds"))) {
                        if (ChickenXP + Config.trainingxp_perfood.get() >= Config.xp_tier_7.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);

                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.trainingxp_perfood.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier1"))) {
                        if (ChickenXP + Config.food_xp_tier_1.get() >= Config.xp_tier_7.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_1.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier2"))) {
                        if (ChickenXP + Config.food_xp_tier_2.get() >= Config.xp_tier_7.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_2.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier3"))) {
                        if (ChickenXP + Config.food_xp_tier_3.get() >= Config.xp_tier_7.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_3.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier4"))) {
                        if (ChickenXP + Config.food_xp_tier_4.get() >= Config.xp_tier_7.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_4.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier5"))) {
                        if (ChickenXP + Config.food_xp_tier_5.get() >= Config.xp_tier_7.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_5.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier6"))) {
                        if (ChickenXP + Config.food_xp_tier_6.get() >= Config.xp_tier_7.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_6.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier7"))) {
                        if (ChickenXP + Config.food_xp_tier_7.get() >= Config.xp_tier_7.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_7.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier8"))) {
                        if (ChickenXP + Config.food_xp_tier_8.get() >= Config.xp_tier_7.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_8.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier9"))) {
                        if (ChickenXP + Config.food_xp_tier_9.get() >= Config.xp_tier_7.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_9.get());
                        }
                    }
                }

                pEntity.removeStack(0, 1);
                //pEntity.getStack(1).getOrCreateNbt().putInt();
                //pEntity.setStack(1, MyChicken.getItem().asItem().getDefaultStack());
                pEntity.resetProgress();
            }
            /////TIER 8
            if (pEntity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier8"))) {
                if (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_8.get()) {
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds"))) {
                        if (ChickenXP + Config.trainingxp_perfood.get() >= Config.xp_tier_8.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);

                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.trainingxp_perfood.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier1"))) {
                        if (ChickenXP + Config.food_xp_tier_1.get() >= Config.xp_tier_8.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_1.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier2"))) {
                        if (ChickenXP + Config.food_xp_tier_2.get() >= Config.xp_tier_8.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_2.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier3"))) {
                        if (ChickenXP + Config.food_xp_tier_3.get() >= Config.xp_tier_8.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_3.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier4"))) {
                        if (ChickenXP + Config.food_xp_tier_4.get() >= Config.xp_tier_8.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_4.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier5"))) {
                        if (ChickenXP + Config.food_xp_tier_5.get() >= Config.xp_tier_8.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_5.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier6"))) {
                        if (ChickenXP + Config.food_xp_tier_6.get() >= Config.xp_tier_8.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_6.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier7"))) {
                        if (ChickenXP + Config.food_xp_tier_7.get() >= Config.xp_tier_8.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_7.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier8"))) {
                        if (ChickenXP + Config.food_xp_tier_8.get() >= Config.xp_tier_8.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_8.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier9"))) {
                        if (ChickenXP + Config.food_xp_tier_9.get() >= Config.xp_tier_8.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_9.get());
                        }
                    }
                }

                pEntity.removeStack(0, 1);
                //pEntity.getStack(1).getOrCreateNbt().putInt();
                //pEntity.setStack(1, MyChicken.getItem().asItem().getDefaultStack());
                pEntity.resetProgress();
            }
            /////TIER 9
            if (pEntity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier9"))) {
                if (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_9.get()) {
                    if (pEntity.getStack(0).getItem().asItem() == Items.WHEAT_SEEDS.asItem()) {
                        if (ChickenXP + Config.trainingxp_perfood.get() >= Config.xp_tier_9.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);

                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.trainingxp_perfood.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier1"))) {
                        if (ChickenXP + Config.food_xp_tier_1.get() >= Config.xp_tier_9.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_1.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier2"))) {
                        if (ChickenXP + Config.food_xp_tier_2.get() >= Config.xp_tier_9.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_2.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier3"))) {
                        if (ChickenXP + Config.food_xp_tier_3.get() >= Config.xp_tier_9.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_3.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier4"))) {
                        if (ChickenXP + Config.food_xp_tier_4.get() >= Config.xp_tier_9.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_4.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier5"))) {
                        if (ChickenXP + Config.food_xp_tier_5.get() >= Config.xp_tier_9.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_5.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier6"))) {
                        if (ChickenXP + Config.food_xp_tier_6.get() >= Config.xp_tier_9.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_6.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier7"))) {
                        if (ChickenXP + Config.food_xp_tier_7.get() >= Config.xp_tier_9.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_7.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier8"))) {
                        if (ChickenXP + Config.food_xp_tier_8.get() >= Config.xp_tier_9.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_8.get());
                        }
                    }
                    if (pEntity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tier9"))) {
                        if (ChickenXP + Config.food_xp_tier_9.get() >= Config.xp_tier_9.get()) {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_lvl", (pEntity.getStack(1).getOrCreateNbt().getInt("roost_lvl") + 1));
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", 0);
                        } else {
                            pEntity.getStack(1).getOrCreateNbt().putInt("roost_xp", ChickenXP + (int) Config.food_xp_tier_9.get());
                        }
                    }
                }

                pEntity.removeStack(0, 1);
                //pEntity.getStack(1).getOrCreateNbt().putInt();
                //pEntity.setStack(1, MyChicken.getItem().asItem().getDefaultStack());
                pEntity.resetProgress();
            }
        }
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, trainer_entity entity) {
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

    public static boolean ischickennotmax(trainer_entity entity){
        if(entity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier1")) && entity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_1.get()){
            return true;
        }else if (entity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier2")) && entity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_2.get()){
            return true;
        }else if (entity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier3")) && entity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_3.get()){
            return true;
        }else if (entity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier4")) && entity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_4.get()){
            return true;
        }else if (entity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier5")) && entity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_5.get()){
            return true;
        }else if (entity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier6")) && entity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_6.get()){
            return true;
        }else if (entity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier7")) && entity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_7.get()){
            return true;
        }else if (entity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier8")) && entity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_8.get()){
            return true;
        }else if (entity.getStack(1).isIn(MyItemTags.of("fabric:roost/tier9")) && entity.getStack(1).getOrCreateNbt().getInt("roost_lvl") < Config.maxlevel_tier_9.get()){
            return true;
        }
        return false;
    }
    private static boolean hasRecipe(trainer_entity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        if((entity.getStack(0).isIn(MyItemTags.of("fabric:seeds")) || entity.getStack(0).isIn(MyItemTags.of("fabric:seeds/tiered"))) && ischickennotmax(entity)){
           return true;
        }
        return false;
    }

    /*private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }*/
}