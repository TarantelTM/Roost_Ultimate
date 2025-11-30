package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.RoostHandler;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.RoostRecipe;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class RoostTile extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(slot == 1){
                resetProgress();
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
                case 0 -> (stack.getItem() instanceof ChickenSeedBase);
                case 1 -> (stack.getItem() instanceof ChickenItemBase);
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };


    private String customName = "ROOST";

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

        if(!itemHandler.getStackInSlot(1).isEmpty()) {
            stack = itemHandler.getStackInSlot(1);
        } else {
            stack = ItemStack.EMPTY;
        }

        return stack;
    }
    @Override
    public void setChanged() {
        if (this.level != null) {
            setChanged(this.level, this.worldPosition, this.getBlockState());
            getRenderStack();
        }

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
        int maxProgresss = maxProgress;
        int progressArrowSize = 200;

        return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
    }

    public final int[] LevelList;
    public final int[] XPList;
    public final int[] XPAmountList;



    public RoostTile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROOST.get(), pos, state);

        this.LevelList = new int[]{
                Objects.requireNonNullElse(Config.maxlevel_tier_1.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_2.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_3.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_4.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_5.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_6.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_7.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_8.get(), 128),
                Objects.requireNonNullElse(Config.maxlevel_tier_9.get(), 128)
        };

        this.XPList = new int[]{
                Config.xp_tier_1.get(),
                Config.xp_tier_2.get(),
                Config.xp_tier_3.get(),
                Config.xp_tier_4.get(),
                Config.xp_tier_5.get(),
                Config.xp_tier_6.get(),
                Config.xp_tier_7.get(),
                Config.xp_tier_8.get(),
                Config.xp_tier_9.get()
        };

        this.XPAmountList = new int[]{
                Config.food_xp_tier_1.get(),
                Config.food_xp_tier_2.get(),
                Config.food_xp_tier_3.get(),
                Config.food_xp_tier_4.get(),
                Config.food_xp_tier_5.get(),
                Config.food_xp_tier_6.get(),
                Config.food_xp_tier_7.get(),
                Config.food_xp_tier_8.get(),
                Config.food_xp_tier_9.get()
        };


        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> RoostTile.this.progress;
                    case 1 -> RoostTile.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> RoostTile.this.progress = value;
                    case 1 -> RoostTile.this.maxProgress = value;
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
        return new RoostHandler(id, inventory, this, this.data);
    }
    private final IItemHandler itemHandlerSided = new InputOutputItemHandler(itemHandler, (i, stack) -> i == 0 || i == 1, i -> i == 2);


    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {
        if(side == null)
            return itemHandler;

        return itemHandlerSided;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        setChanged();
        getRenderStack();

    }
    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.@NotNull Provider lookup) {
        nbt.put("inventory", itemHandler.serializeNBT(lookup));
        nbt.putInt("roost.progress", this.progress);
        nbt.putString("roost.custom_name", this.customName);
        super.saveAdditional(nbt, lookup);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider lookup) {
        super.loadAdditional(nbt, lookup);
        itemHandler.deserializeNBT(lookup,nbt.getCompound("inventory"));
        progress = nbt.getInt("roost.progress");
        this.customName = nbt.getString("roost.custom_name");

    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        SimpleContainer block = new SimpleContainer(1);

        ItemStack itemStack = new ItemStack(ModBlocks.ROOST.get());
        NonNullList<ItemStack> items = inventory.getItems();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            items.set(i, itemHandler.getStackInSlot(i));
        }
        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inventory.getItems()));
        block.setItem(0, itemStack.copy());

        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, RoostTile pEntity) {
        if(level.isClientSide()) {
            return;
        }
        setChanged(level, pos, state);
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

    public static RecipeInput getRecipeInput(SimpleContainer inventory) {
        return new RecipeInput() {
            @Override
            public @NotNull ItemStack getItem(int index) {
                return inventory.getItem(index).copy();
            }

            @Override
            public int size() {
                return inventory.getContainerSize();
            }
        };
    }

    private static void craftItem(RoostTile pEntity) {
        MyChicken = (ChickenItemBase) pEntity.itemHandler.getStackInSlot(1).getItem().getDefaultInstance().getItem();
        ChickenItem = pEntity.itemHandler.getStackInSlot(1);
        FoodItem = (ChickenSeedBase) pEntity.itemHandler.getStackInSlot(0).getItem().getDefaultInstance().getItem();
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        Optional<RecipeHolder<RoostRecipe>> recipe = Optional.empty();
        if (level != null) {
            recipe = level.getRecipeManager().getRecipeFor(ModRecipes.ROOST_TYPE.get(), getRecipeInput(inventory), level);

        }

        if (hasRecipe(pEntity)) {
            int ChickenLevel;
            int ChickenXP;
            if(ChickenItem.has(ModDataComponents.CHICKENLEVEL) && ChickenItem.has(ModDataComponents.CHICKENXP)){
                ChickenLevel = (ChickenItem.get(ModDataComponents.CHICKENLEVEL.value()) / 2 + recipe.get().value().output().getCount());
                ChickenXP = ChickenItem.get(ModDataComponents.CHICKENXP.value());
            }
            else {
                ChickenLevel = 0;
                ChickenXP = 0;
                ChickenItem.set(ModDataComponents.CHICKENLEVEL.value(), ChickenLevel);
                ChickenItem.set(ModDataComponents.CHICKENXP.value(), ChickenXP);
            }

            ItemStack itemstack1 = recipe.get().value().assemble( getRecipeInput(inventory),level.registryAccess());
            int newCount = (pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel);
            itemstack1.setCount(Math.min(newCount, 64));

            if (pEntity.itemHandler.getStackInSlot(1).getItem() instanceof ChickenItemBase) {

                if (ChickenItem.get(ModDataComponents.CHICKENLEVEL.value()) < pEntity.LevelList[MyChicken.currentchickena(MyChicken.getDefaultInstance())]) {
                    if (pEntity.itemHandler.getStackInSlot(0).getItem() instanceof ChickenSeedBase) {
                        if ((ChickenXP + (pEntity.XPAmountList[FoodItem.getCurrentMaxXp()] * Config.roostxp.get()) >= pEntity.XPList[MyChicken.currentchickena(MyChicken.getDefaultInstance())])) {
                            ChickenItem.set(ModDataComponents.CHICKENLEVEL.value(), (ChickenItem.get(ModDataComponents.CHICKENLEVEL.value()) + 1));
                            ChickenItem.set(ModDataComponents.CHICKENXP.value(), 0);

                        } else {

                            ChickenItem.set(ModDataComponents.CHICKENXP.value(), (int) ((ChickenXP + pEntity.XPAmountList[FoodItem.getCurrentMaxXp()] * Config.roostxp.get())));
                        }
                    }
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(1, ChickenItem);
                    pEntity.itemHandler.setStackInSlot(2, itemstack1.copy());

                    pEntity.resetProgress();
                }
                else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(2, itemstack1.copy());
                    pEntity.resetProgress();
                }
            }

        }
    }


    private static boolean hasRecipe(RoostTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<RecipeHolder<RoostRecipe>> recipe = Optional.empty();
        if (level != null) {
            recipe = level.getRecipeManager().getRecipeFor(ModRecipes.ROOST_TYPE.get(), getRecipeInput(inventory), level);
            recipe.ifPresent(roostRecipeRecipeHolder -> entity.maxProgress = (Config.roost_speed_tick.get() * roostRecipeRecipeHolder.value().time()));
        }



        if (recipe.isEmpty()) {
            return false;
        }


        ItemStack planned = recipe.get().value().assemble(getRecipeInput(inventory), level.registryAccess());
        int fullPlanned = computePlannedCountWithChickenLevel(entity, recipe.get().value());
        int maxStack = planned.getMaxStackSize();
        int batch = Math.min(Math.max(1, fullPlanned), maxStack);

        ItemStack out = inventory.getItem(2);
        int placeable = getPlaceableAmount(out, planned);

        return placeable >= batch;
    }





    private static int getPlaceableAmount(ItemStack out, ItemStack planned) {
        int maxStack = planned.getMaxStackSize();
        if (out.isEmpty()) {

            return maxStack;
        }

        if (!ItemStack.isSameItemSameComponents(out, planned)) {
            return 0;
        }
        return Math.max(0, maxStack - out.getCount());
    }


    private static int computePlannedCountWithChickenLevel(RoostTile entity, RoostRecipe r) {
        int baseOut = r.output().getCount();
        int addByLevel = 0;
        ItemStack chickenStack = entity.itemHandler.getStackInSlot(1);
        if (!chickenStack.isEmpty() && chickenStack.has(ModDataComponents.CHICKENLEVEL)) {
            Integer cl = chickenStack.get(ModDataComponents.CHICKENLEVEL.value());
            if (cl != null) {
                addByLevel = cl / 2;
            }
        }
        return Math.max(1, baseOut + addByLevel);
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider prov) {
        return saveWithFullMetadata(prov);
    }

}