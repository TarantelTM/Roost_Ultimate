package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.handler.Roost_Handler;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.Roost_Recipe;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openjdk.nashorn.internal.runtime.logging.DebugLogger;
import org.openjdk.nashorn.internal.runtime.logging.Logger;

import java.util.*;

public class Roost_Tile extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            /*if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }*/
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
                case 0 -> (stack.is(ItemTags.create(ChickenRoostMod.commonsource("seeds/tiered"))));
                case 1 -> (stack.getItem() instanceof ChickenItemBase);
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

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
    private final IItemHandler itemHandlerSided = new InputOutputItemHandler(itemHandler, (i, stack) -> i == 0 || i == 1, i -> i == 2);


    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {
        if(side == null)
            return itemHandler;

        return itemHandlerSided;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        /*if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);*/
        setChanged();
        getRenderStack();

    }
    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookup) {
        nbt.put("inventory", itemHandler.serializeNBT(lookup));
        nbt.putInt("roost.progress", this.progress);
        super.saveAdditional(nbt, lookup);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider lookup) {
        super.loadAdditional(nbt, lookup);
        itemHandler.deserializeNBT(lookup,nbt.getCompound("inventory"));
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
        Optional<RecipeHolder<Roost_Recipe>> recipe = Optional.empty();
        if (level != null) {
            recipe = level.getRecipeManager().getRecipeFor(ModRecipes.ROOST_TYPE.get(), getRecipeInput(inventory), level);
        }
        if (hasRecipe(pEntity)) {
            int ChickenLevel = (int) (ChickenItem.get(ModDataComponents.CHICKENLEVEL.value()) / 2 + recipe.get().value().output.getCount());
            int ChickenXP = ChickenItem.get(ModDataComponents.CHICKENXP.value());

            if (pEntity.itemHandler.getStackInSlot(1).getItem() instanceof ChickenItemBase) {

                if (ChickenItem.get(ModDataComponents.CHICKENLEVEL.value()) < LevelList[MyChicken.currentchickena]) {
                    if (pEntity.itemHandler.getStackInSlot(0).getItem() instanceof ChickenSeedBase) {
                        if ((ChickenXP + (XPAmountList[FoodItem.currentmaxxpp] * Config.roostxp.get()) >= XPList[MyChicken.currentchickena])) {
                            ChickenItem.set(ModDataComponents.CHICKENLEVEL.value(), (ChickenItem.get(ModDataComponents.CHICKENLEVEL.value()) + 1));
                            ChickenItem.set(ModDataComponents.CHICKENXP.value(), 0);

                        } else {

                            ChickenItem.set(ModDataComponents.CHICKENXP.value(), (int) ((ChickenXP + ((int) XPAmountList[FoodItem.currentmaxxpp]) * Config.roostxp.get())));
                        }
                    }
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.itemHandler.extractItem(1, 0, true);
                    pEntity.itemHandler.setStackInSlot(1, ChickenItem);
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

        }
    }


    private static boolean hasRecipe(Roost_Tile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<RecipeHolder<Roost_Recipe>> recipe = Optional.empty();
        if (level != null) {
            recipe = level.getRecipeManager().getRecipeFor(ModRecipes.ROOST_TYPE.get(), getRecipeInput(inventory), level);
        }


        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().value().output.copy().getItem().getDefaultInstance());
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
    public CompoundTag getUpdateTag(HolderLookup.Provider prov) {
        return saveWithFullMetadata(prov);
    }

}