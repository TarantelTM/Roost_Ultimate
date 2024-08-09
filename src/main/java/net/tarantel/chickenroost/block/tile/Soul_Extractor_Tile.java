package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tarantel.chickenroost.handler.SoulExtractor_Handler;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.Soul_Extractor_Recipe;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Soul_Extractor_Tile extends BlockEntity implements MenuProvider {

    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> (stack.getItem() instanceof ChickenItemBase);
                case 1 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

   // private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

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
    public int maxProgress = ((int) Config.extractor_speedtimer.get() * 20);

    public int getScaledProgress() {
        int progresss = progress;
        int maxProgresss = maxProgress;  // Max Progress
        int progressArrowSize = 200; // This is the height in pixels of your arrow

        return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
    }
    public Soul_Extractor_Tile(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_EXTRACTOR.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> Soul_Extractor_Tile.this.progress;
                    case 1 -> Soul_Extractor_Tile.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> Soul_Extractor_Tile.this.progress = value;
                    case 1 -> Soul_Extractor_Tile.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("name.chicken_roost.soul_extractor_");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new SoulExtractor_Handler(id, inventory, this, this.data);
    }

    private final IItemHandler itemHandlerSided = new InputOutputItemHandler(itemHandler, (i, stack) -> i == 0, i -> i == 1);


    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {
        if(side == null)
            return itemHandler;

        return itemHandlerSided;
    }

    @Override
    public void onLoad() {
        super.onLoad();
       // lazyItemHandler = LazyOptional.of(() -> itemHandler);
        if(!level.isClientSide()) {
          ///  ModMessages.sendToClients(new ExtractorStackSyncS2CPacket(this.itemHandler, worldPosition));
        }
        setChanged();
    }

   /*@Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }*/

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookup) {
        nbt.put("inventory", itemHandler.serializeNBT(lookup));
        nbt.putInt("soul_extractor.progress", this.progress);
        super.saveAdditional(nbt, lookup);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider lookup) {
        super.loadAdditional(nbt, lookup);
        itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));
        progress = nbt.getInt("soul_extractor.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, inventory);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, Soul_Extractor_Tile pEntity) {
        if(level.isClientSide()) {
            return;
        }
        setChanged(level, pos, state);
      ///  ModMessages.sendToClients(new ExtractorStackSyncS2CPacket(pEntity.itemHandler, pEntity.worldPosition));
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

    record OrbInput(SimpleContainer inventory) implements RecipeInput {
        @Override
        public ItemStack getItem(int index) {
            return inventory.getItem(index).copy();
        }

        @Override
        public int size() {
            return inventory.getContainerSize();
        }
    }
    private static void craftItem(Soul_Extractor_Tile pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        Optional<RecipeHolder<Soul_Extractor_Recipe>> recipe = Optional.empty();
        if (level != null) {
            recipe = level.getRecipeManager().getRecipeFor(ModRecipes.SOUL_EXTRACTION_TYPE.get(), getRecipeInput(inventory), level);
        }

        if(hasRecipe(pEntity)) {
            //pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.itemHandler.setStackInSlot(1, new ItemStack(recipe.get().value().output.copy().getItem(),
                    pEntity.itemHandler.getStackInSlot(1).getCount() + 1));

            pEntity.resetProgress();
        }
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
    private static boolean hasRecipe(Soul_Extractor_Tile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        /*Optional<RecipeHolder<Soul_Extractor_Recipe>> recipe = level.getRecipeManager()
                .getRecipeFor(Soul_Extractor_Recipe.Type.INSTANCE, inventory, level);*/
        Optional<RecipeHolder<Soul_Extractor_Recipe>> recipe = Optional.empty();
        if (level != null) {
            recipe = level.getRecipeManager().getRecipeFor(ModRecipes.SOUL_EXTRACTION_TYPE.get(), getRecipeInput(inventory), level);
        }

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().value().output.copy().getItem().getDefaultInstance());
    }


    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(1).getItem() == stack.getItem() || inventory.getItem(1).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(1).getMaxStackSize() > inventory.getItem(1).getCount();
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