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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.LazyOptional;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tarantel.chickenroost.block.blocks.Roost_Block;
import net.tarantel.chickenroost.handler.Roost_Handler;
///import net.tarantel.chickenroost.network.ModMessages;
///import net.tarantel.chickenroost.network.RoostItemStackSyncS2CPacket;
import net.tarantel.chickenroost.recipes.Roost_Recipe;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
public class Roost_Tile extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
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
                case 1 -> (stack.is(ItemTags.create(new ResourceLocation("forge:roost/tiered"))));
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
        if (cap == Capabilities.ITEM_HANDLER) {
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
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, inventory);
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


    //public ModConfigSpec.ConfigValue[] configs;

  //  public List<Integer> XPTiers = new ArrayList<Integer>();
   // public List<ModConfigSpec.ConfigValue> configs = new ArrayList<ModConfigSpec.ConfigValue>();


  /*  public void Init(){
        XPTiers.add(Config.food_xp_tier_1.get());
        XPTiers.add(Config.food_xp_tier_2.get());
        XPTiers.add(Config.food_xp_tier_3.get());
        XPTiers.add(Config.food_xp_tier_4.get());
        XPTiers.add(Config.food_xp_tier_5.get());
        XPTiers.add(Config.food_xp_tier_6.get());
        XPTiers.add(Config.food_xp_tier_7.get());
        XPTiers.add(Config.food_xp_tier_8.get());
        XPTiers.add(Config.food_xp_tier_9.get());


    }*/


   /* public static void getXP(int id) {
        if(id == 1) {
            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (+ChickenXP + ((int) Config.food_xp_tier_1.get() * Config.roostxp.get())));
        }
        if(id == 2) {
            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (+ChickenXP + ((int) Config.food_xp_tier_2.get() * Config.roostxp.get())));
        }
        if(id == 3) {
            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (+ChickenXP + ((int) Config.food_xp_tier_3.get() * Config.roostxp.get())));
        }
        if(id == 4) {
            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (+ChickenXP + ((int) Config.food_xp_tier_4.get() * Config.roostxp.get())));
        }
        if(id == 5) {
            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (+ChickenXP + ((int) Config.food_xp_tier_5.get() * Config.roostxp.get())));
        }
        if(id == 6) {
            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (+ChickenXP + ((int) Config.food_xp_tier_6.get() * Config.roostxp.get())));
        }
        if(id == 7) {
            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (+ChickenXP + ((int) Config.food_xp_tier_7.get() * Config.roostxp.get())));
        }
        if(id == 8) {
            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (+ChickenXP + ((int) Config.food_xp_tier_8.get() * Config.roostxp.get())));
        }
        if(id == 9) {
            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (+ChickenXP + ((int) Config.food_xp_tier_9.get() * Config.roostxp.get())));
        }
    }*/

    /*public static int ChickenTier(){

        Roost_Tile tile =
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        if(inventory.getItem(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier1")))){
            return 1;
        }
        if(inventory.getItem(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier2")))){
            return 2;
        }
        if(inventory.getItem(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier3")))){
            return 3;
        }
        if(inventory.getItem(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier4")))){
            return 4;
        }
        if(inventory.getItem(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier5")))){
            return 5;
        }
        if(inventory.getItem(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier6")))){
            return 6;
        }
        if(inventory.getItem(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier7")))){
            return 7;
        }
        if(inventory.getItem(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier8")))){
            return 8;
        }
        if(inventory.getItem(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier9")))){
            return 9;
        }


        return 0;
    }*/


  /*  public static int ChickenTierXP(int id){

        id = ChickenTier();
        if(id == 1){
            return Config.food_xp_tier_1.get();
        }
        if(id == 2){
            return Config.food_xp_tier_2.get();
        }
        if(id == 3){
            return Config.food_xp_tier_3.get();
        }
        if(id == 4){
            return Config.food_xp_tier_4.get();
        }
        if(id == 5){
            return Config.food_xp_tier_5.get();
        }
        if(id == 6){
            return Config.food_xp_tier_6.get();
        }
        if(id == 7){
            return Config.food_xp_tier_7.get();
        }
        if(id == 8){
            return Config.food_xp_tier_8.get();
        }
        if(id == 9){
            return Config.food_xp_tier_9.get();
        }

        return 0;
    }*/
    public static ItemStack ChickenItem;

    //public static int ChickenXP;
    public static ItemStack MyChicken;
    private static void craftItem(Roost_Tile pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        Optional<RecipeHolder<Roost_Recipe>> recipe = level.getRecipeManager()
                .getRecipeFor(Roost_Recipe.Type.INSTANCE, inventory, level);
        //System.out.println(2500 / 10);

        //int fasfasf = ChickenTierXP(pEntity);
        if (hasRecipe(pEntity)) {
            ChickenItem = pEntity.itemHandler.getStackInSlot(1);
            int ChickenLevel = (int) (ChickenItem.getOrCreateTag().getInt("roost_lvl") / 2 + recipe.get().value().output.getCount());
            //if (pEntity.itemHandler.getStackInSlot(1).is((ItemTags.create(new ResourceLocation("forge:roost/tiered")))) &&
            //       (pEntity.itemHandler.getStackInSlot(0).is((ItemTags.create(new ResourceLocation("forge:seeds/tiered")))) || (pEntity.itemHandler.getStackInSlot(0).is((ItemTags.create(new ResourceLocation("forge:seeds"))))))) {
            MyChicken = pEntity.itemHandler.getStackInSlot(1);
            //int ChickenLevel = MyChicken.getOrCreateTag().getInt("roost_lvl");
            /*if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier1"))) && ChickenLevel >= Config.maxlevel_tier_1.get()) {
                pEntity.resetProgress();
            }*/
            int ChickenXP = MyChicken.getOrCreateTag().getInt("roost_xp");
            /////TIER 1
            if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier1")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") < Config.maxlevel_tier_1.get()) {
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + (Config.food_xp_tier_1.get() / 10) >= Config.xp_tier_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_1.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + (Config.food_xp_tier_2.get() / 10) >= Config.xp_tier_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_2.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + (Config.food_xp_tier_3.get() / 10) >= Config.xp_tier_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_3.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + (Config.food_xp_tier_4.get() / 10) >= Config.xp_tier_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_4.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + (Config.food_xp_tier_5.get() / 10) >= Config.xp_tier_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_5.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + (Config.food_xp_tier_6.get() / 10) >= Config.xp_tier_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_6.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + (Config.food_xp_tier_7.get() / 10) >= Config.xp_tier_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_7.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + (Config.food_xp_tier_8.get() / 10) >= Config.xp_tier_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_8.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + (Config.food_xp_tier_9.get() / 10) >= Config.xp_tier_1.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_9.get() * Config.roostxp.get())));
                        }
                    }
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(1, MyChicken);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.copy().getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
                else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
            }
            /////TIER 2
            if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier2")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") < Config.maxlevel_tier_2.get()) {
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + (Config.food_xp_tier_1.get() / 10)>= Config.xp_tier_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_1.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + (Config.food_xp_tier_2.get() / 10)>= Config.xp_tier_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_2.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + (Config.food_xp_tier_3.get() / 10)>= Config.xp_tier_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_3.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + (Config.food_xp_tier_4.get() / 10)>= Config.xp_tier_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_4.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + (Config.food_xp_tier_5.get() / 10)>= Config.xp_tier_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_5.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + (Config.food_xp_tier_6.get() / 10)>= Config.xp_tier_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_6.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + (Config.food_xp_tier_7.get() / 10)>= Config.xp_tier_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_7.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + (Config.food_xp_tier_8.get() / 10)>= Config.xp_tier_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_8.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + (Config.food_xp_tier_9.get() / 10) >= Config.xp_tier_2.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_9.get() * Config.roostxp.get())));
                        }
                    }
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(1, MyChicken);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.copy().getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
                else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
            }
            /////TIER 3
            if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier3")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") < Config.maxlevel_tier_3.get()) {
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + (Config.food_xp_tier_1.get() / 10)>= Config.xp_tier_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_1.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + (Config.food_xp_tier_2.get() / 10)>= Config.xp_tier_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_2.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + (Config.food_xp_tier_3.get() / 10)>= Config.xp_tier_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_3.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + (Config.food_xp_tier_4.get() / 10)>= Config.xp_tier_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_4.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + (Config.food_xp_tier_5.get() / 10)>= Config.xp_tier_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_5.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + (Config.food_xp_tier_6.get() / 10)>= Config.xp_tier_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_6.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + (Config.food_xp_tier_7.get() / 10)>= Config.xp_tier_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_7.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + (Config.food_xp_tier_8.get() / 10)>= Config.xp_tier_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_8.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + (Config.food_xp_tier_9.get() / 10)>= Config.xp_tier_3.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_9.get() * Config.roostxp.get())));
                        }
                    }
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(1, MyChicken);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.copy().getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
                else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
            }
            /////TIER 4
            if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier4")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") < Config.maxlevel_tier_4.get()) {
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + (Config.food_xp_tier_1.get() / 10)>= Config.xp_tier_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_1.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + (Config.food_xp_tier_2.get() / 10)>= Config.xp_tier_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_2.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + (Config.food_xp_tier_3.get() / 10)>= Config.xp_tier_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_3.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + (Config.food_xp_tier_4.get() / 10)>= Config.xp_tier_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_4.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + (Config.food_xp_tier_5.get() / 10)>= Config.xp_tier_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_5.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + (Config.food_xp_tier_6.get() / 10)>= Config.xp_tier_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_6.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + (Config.food_xp_tier_7.get() / 10)>= Config.xp_tier_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_7.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + (Config.food_xp_tier_8.get() / 10)>= Config.xp_tier_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_8.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + (Config.food_xp_tier_9.get() / 10)>= Config.xp_tier_4.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_9.get() * Config.roostxp.get())));
                        }
                    }
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(1, MyChicken);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.copy().getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
                else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
            }
            /////TIER 5
            if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier5")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") < Config.maxlevel_tier_5.get()) {
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + (Config.food_xp_tier_1.get() / 10)>= Config.xp_tier_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_1.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + (Config.food_xp_tier_2.get() / 10)>= Config.xp_tier_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_2.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + (Config.food_xp_tier_3.get() / 10)>= Config.xp_tier_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_3.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + (Config.food_xp_tier_4.get() / 10)>= Config.xp_tier_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_4.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + (Config.food_xp_tier_5.get() / 10)>= Config.xp_tier_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_5.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + (Config.food_xp_tier_6.get() / 10)>= Config.xp_tier_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_6.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + (Config.food_xp_tier_7.get() / 10)>= Config.xp_tier_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_7.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + (Config.food_xp_tier_8.get() / 10)>= Config.xp_tier_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_8.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + (Config.food_xp_tier_9.get() / 10)>= Config.xp_tier_5.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_9.get() * Config.roostxp.get())));
                        }
                    }
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(1, MyChicken);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.copy().getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
                else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
            }
            /////TIER 6
            if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier6")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") < Config.maxlevel_tier_6.get()) {
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + (Config.food_xp_tier_1.get() / 10)>= Config.xp_tier_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_1.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + (Config.food_xp_tier_2.get() / 10)>= Config.xp_tier_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_2.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + (Config.food_xp_tier_3.get() / 10)>= Config.xp_tier_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_3.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + (Config.food_xp_tier_4.get() / 10)>= Config.xp_tier_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_4.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + (Config.food_xp_tier_5.get() / 10)>= Config.xp_tier_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_5.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + (Config.food_xp_tier_6.get() / 10)>= Config.xp_tier_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_6.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + (Config.food_xp_tier_7.get() / 10)>= Config.xp_tier_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_7.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + (Config.food_xp_tier_8.get() / 10)>= Config.xp_tier_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_8.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + (Config.food_xp_tier_9.get() / 10)>= Config.xp_tier_6.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_9.get() * Config.roostxp.get())));
                        }
                    }
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(1, MyChicken);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.copy().getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
                else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
            }
            /////TIER 7
            if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier7")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") < Config.maxlevel_tier_7.get()) {
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + (Config.food_xp_tier_1.get() / 10)>= Config.xp_tier_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_1.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + (Config.food_xp_tier_2.get() / 10)>= Config.xp_tier_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_2.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + (Config.food_xp_tier_3.get() / 10)>= Config.xp_tier_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_3.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + (Config.food_xp_tier_4.get() / 10)>= Config.xp_tier_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_4.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + (Config.food_xp_tier_5.get() / 10)>= Config.xp_tier_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_5.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + (Config.food_xp_tier_6.get() / 10)>= Config.xp_tier_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_6.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + (Config.food_xp_tier_7.get() / 10)>= Config.xp_tier_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_7.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + (Config.food_xp_tier_8.get() / 10)>= Config.xp_tier_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_8.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + (Config.food_xp_tier_9.get() / 10)>= Config.xp_tier_7.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_9.get() * Config.roostxp.get())));
                        }
                    }

                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(1, MyChicken);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.copy().getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();

                }
                else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
            }
            /////TIER 8
            if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier8")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") < Config.maxlevel_tier_8.get()) {
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + (Config.food_xp_tier_1.get() / 10)>= Config.xp_tier_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_1.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + (Config.food_xp_tier_2.get() / 10)>= Config.xp_tier_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_2.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + (Config.food_xp_tier_3.get() / 10)>= Config.xp_tier_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_3.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + (Config.food_xp_tier_4.get() / 10)>= Config.xp_tier_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_4.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + (Config.food_xp_tier_5.get() / 10)>= Config.xp_tier_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_5.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + (Config.food_xp_tier_6.get() / 10)>= Config.xp_tier_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_6.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + (Config.food_xp_tier_7.get() / 10)>= Config.xp_tier_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_7.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + (Config.food_xp_tier_8.get() / 10)>= Config.xp_tier_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_8.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + (Config.food_xp_tier_9.get() / 10)>= Config.xp_tier_8.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_9.get() * Config.roostxp.get())));
                        }
                    }

                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(1, MyChicken);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.copy().getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
                else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
            }
            /////TIER 9
            if (pEntity.itemHandler.getStackInSlot(1).is(ItemTags.create(new ResourceLocation("forge:roost/tier9")))) {
                if (MyChicken.getOrCreateTag().getInt("roost_lvl") < Config.maxlevel_tier_9.get()) {
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier1")))) {
                        if (ChickenXP + (Config.food_xp_tier_1.get() / 10)>= Config.xp_tier_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_1.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier2")))) {
                        if (ChickenXP + (Config.food_xp_tier_2.get() / 10)>= Config.xp_tier_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_2.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier3")))) {
                        if (ChickenXP + (Config.food_xp_tier_3.get() / 10)>= Config.xp_tier_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_3.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier4")))) {
                        if (ChickenXP + (Config.food_xp_tier_4.get() / 10)>= Config.xp_tier_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_4.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier5")))) {
                        if (ChickenXP + (Config.food_xp_tier_5.get() / 10)>= Config.xp_tier_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_5.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier6")))) {
                        if (ChickenXP + (Config.food_xp_tier_6.get() / 10)>= Config.xp_tier_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_6.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier7")))) {
                        if (ChickenXP + (Config.food_xp_tier_7.get() / 10)>= Config.xp_tier_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_7.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier8")))) {
                        if (ChickenXP + (Config.food_xp_tier_8.get() / 10)>= Config.xp_tier_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_8.get() * Config.roostxp.get())));
                        }
                    }
                    if (pEntity.itemHandler.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:seeds/tier9")))) {
                        if (ChickenXP + (Config.food_xp_tier_9.get() / 10)>= Config.xp_tier_9.get()) {
                            MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                            MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                        } else {
                            MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) Config.food_xp_tier_9.get() * Config.roostxp.get())));
                        }
                    }
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(1, MyChicken);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
                else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.getItem(),
                            pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

                    pEntity.resetProgress();
                }
            }


            /*pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.itemHandler.extractItem(1, 0, true);
            pEntity.itemHandler.setStackInSlot(1, MyChicken);
            pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().value().output.copy().getItem(),
                    pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel));

            pEntity.resetProgress();*/
        }
    }

   // }

    private static boolean hasRecipe(Roost_Tile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<RecipeHolder<Roost_Recipe>> recipe = level.getRecipeManager()
                .getRecipeFor(Roost_Recipe.Type.INSTANCE, inventory, level);


        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().value().output.copy().getItem().getDefaultInstance());
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