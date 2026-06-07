package net.tarantel.chickenroost.block.tile;

import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.SoulExtractorHandler;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.networking.SyncAutoOutputPayload;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.SoulExtractorRecipe;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoulExtractorTile extends BlockEntity implements MenuProvider, ICollectorTarget {
   public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
      protected void onContentsChanged(int slot) {
         SoulExtractorTile.this.setChanged();
         if (slot == 0) {
            SoulExtractorTile.this.resetProgress();
         }

         assert SoulExtractorTile.this.level != null;

         if (!SoulExtractorTile.this.level.isClientSide()) {
            SoulExtractorTile.this.level
               .sendBlockUpdated(SoulExtractorTile.this.getBlockPos(), SoulExtractorTile.this.getBlockState(), SoulExtractorTile.this.getBlockState(), 3);
         }
      }

      public boolean isItemValid(int slot, @NotNull ItemStack stack) {
         return switch (slot) {
            case 0 -> stack.getItem() instanceof ChickenItemBase;
            case 1 -> false;
            default -> super.isItemValid(slot, stack);
         };
      }
   };
   private static final int CHICKEN_SLOT = 0;
   private final IItemHandler ccView = new IItemHandler() {
      public int getSlots() {
         return SoulExtractorTile.this.itemHandler.getSlots();
      }

      @NotNull
      public ItemStack getStackInSlot(int slot) {
         return SoulExtractorTile.this.itemHandler.getStackInSlot(slot);
      }

      @NotNull
      public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
         return stack;
      }

      @NotNull
      public ItemStack extractItem(int slot, int amount, boolean simulate) {
         return ItemStack.EMPTY;
      }

      public int getSlotLimit(int slot) {
         return SoulExtractorTile.this.itemHandler.getSlotLimit(slot);
      }

      public boolean isItemValid(int slot, @NotNull ItemStack stack) {
         return SoulExtractorTile.this.itemHandler.isItemValid(slot, stack);
      }
   };
   private String customName = "EXTRACTOR";
   protected final ContainerData data;
   public int progress = 0;
   public int maxProgress = (Integer)Config.extractor_speedtimer.get() * 20;
   private final IItemHandler itemHandlerSided = new InputOutputItemHandler(this.itemHandler, (i, stack) -> i == 0, i -> i == 1);
   private boolean autoOutput = false;
   private boolean lastRedstonePowered = false;
   private boolean autoOutputByRedstone = false;

   @Override
   public int getReadSlot() {
      return 0;
   }

   @Nullable
   @Override
   public IItemHandler getItemHandler() {
      return this.ccView;
   }

   @Override
   public void setCustomName(String name) {
      if (name == null) {
         name = "";
      }

      this.customName = name;
      this.setChanged();
      if (this.level != null && !this.level.isClientSide) {
         this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
      }
   }

   @Override
   public String getCustomName() {
      return this.customName;
   }

   public ItemStack getRenderStack() {
      ItemStack stack;
      if (!this.itemHandler.getStackInSlot(0).isEmpty()) {
         stack = this.itemHandler.getStackInSlot(0);
      } else {
         stack = ItemStack.EMPTY;
      }

      return stack;
   }

   public void setHandler(ItemStackHandler itemStackHandler) {
      for (int i = 0; i < itemStackHandler.getSlots(); i++) {
         this.itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
      }
   }

   public int getScaledProgress() {
      int progresss = this.progress;
      int maxProgresss = this.maxProgress;
      int progressArrowSize = 200;
      return maxProgresss != 0 && progresss != 0 ? progresss * progressArrowSize / maxProgresss : 0;
   }

   public SoulExtractorTile(BlockPos pos, BlockState state) {
      super(ModBlockEntities.SOUL_EXTRACTOR.get(), pos, state);
      this.data = new ContainerData() {
         public int get(int index) {
            return switch (index) {
               case 0 -> SoulExtractorTile.this.progress;
               case 1 -> SoulExtractorTile.this.maxProgress;
               default -> 0;
            };
         }

         public void set(int index, int value) {
            switch (index) {
               case 0:
                  SoulExtractorTile.this.progress = value;
                  break;
               case 1:
                  SoulExtractorTile.this.maxProgress = value;
            }
         }

         public int getCount() {
            return 2;
         }
      };
   }

   @NotNull
   public Component getDisplayName() {
      return Component.translatable("name.chicken_roost.soul_extractor_");
   }

   @Nullable
   public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
      return new SoulExtractorHandler(id, inventory, this, this.data);
   }

   @Nullable
   public IItemHandler getItemHandlerCapability(@Nullable Direction side) {
      return (IItemHandler)(side == null ? this.itemHandler : this.itemHandlerSided);
   }

   public void onLoad() {
      super.onLoad();

      assert this.level != null;

      if (!this.level.isClientSide()) {
         this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
      }

      this.setChanged();
   }

   public void saveAdditional(CompoundTag nbt, @NotNull Provider lookup) {
      nbt.put("inventory", this.itemHandler.serializeNBT(lookup));
      nbt.putInt("soul_extractor.progress", this.progress);
      nbt.putBoolean("AutoOutput", this.autoOutput);
      nbt.putBoolean("LastRedstonePowered", this.lastRedstonePowered);
      nbt.putBoolean("AutoOutputByRedstone", this.autoOutputByRedstone);
      nbt.putString("soul_extractor.custom_name", this.customName);
      super.saveAdditional(nbt, lookup);
   }

   public void loadAdditional(@NotNull CompoundTag nbt, @NotNull Provider lookup) {
      super.loadAdditional(nbt, lookup);
      this.itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));
      this.progress = nbt.getInt("soul_extractor.progress");
      this.customName = nbt.getString("soul_extractor.custom_name");
      if (nbt.contains("AutoOutput")) {
         this.autoOutput = nbt.getBoolean("AutoOutput");
      } else {
         this.autoOutput = false;
      }

      if (nbt.contains("LastRedstonePowered")) {
         this.lastRedstonePowered = nbt.getBoolean("LastRedstonePowered");
      } else {
         this.lastRedstonePowered = false;
      }

      if (nbt.contains("AutoOutputByRedstone")) {
         this.autoOutputByRedstone = nbt.getBoolean("AutoOutputByRedstone");
      } else {
         this.autoOutputByRedstone = false;
      }
   }

   public void drops() {
      SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
      SimpleContainer block = new SimpleContainer(1);
      ItemStack itemStack = new ItemStack((ItemLike)ModBlocks.SOUL_EXTRACTOR.get());
      NonNullList<ItemStack> items = inventory.getItems();

      for (int i = 0; i < this.itemHandler.getSlots(); i++) {
         items.set(i, this.itemHandler.getStackInSlot(i));
      }

      itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inventory.getItems()));
      block.setItem(0, itemStack.copy());
      Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
   }

   @Override
   public boolean isAutoOutputEnabled() {
      return this.autoOutput;
   }

   public void setAutoOutputEnabled(boolean enabled) {
      this.autoOutput = enabled;
      this.autoOutputByRedstone = false;
      if (this.level != null && !this.level.isClientSide) {
         setChanged(this.level, this.worldPosition, this.getBlockState());
      }
   }

   @Override
   public void setAutoOutputFromGui(boolean enabled) {
      this.autoOutput = enabled;
      this.autoOutputByRedstone = false;
      this.syncToClients();
   }

   private void setAutoOutputFromRedstone(boolean enabled) {
      this.autoOutput = enabled;
      this.autoOutputByRedstone = enabled;
      this.syncToClients();
   }

   private void syncToClients() {
      if (this.level instanceof ServerLevel serverLevel) {
         PacketDistributor.sendToPlayersTrackingChunk(
            serverLevel,
            serverLevel.getChunkAt(this.worldPosition).getPos(),
            new SyncAutoOutputPayload(this.worldPosition, this.autoOutput),
            new CustomPacketPayload[0]
         );
      }

      this.setChanged();
   }

   public static void tick(Level level, BlockPos pos, BlockState state, SoulExtractorTile pEntity) {
      if (!level.isClientSide()) {
         boolean powered = level.hasNeighborSignal(pos);
         if (powered && !pEntity.lastRedstonePowered && !pEntity.autoOutput) {
            pEntity.setAutoOutputFromRedstone(true);
         }

         if (!powered && pEntity.lastRedstonePowered && pEntity.autoOutputByRedstone) {
            pEntity.setAutoOutputFromRedstone(false);
         }

         pEntity.lastRedstonePowered = powered;
         setChanged(level, pos, state);
         if (hasRecipe(pEntity)) {
            pEntity.progress++;
            if (pEntity.progress >= pEntity.maxProgress) {
               craftItem(pEntity);
            }
         } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
         }

         if (pEntity.isAutoOutputEnabled()) {
            tryPushOutputDown(level, pos, state, pEntity);
         }
      }
   }

   @Override
   public void setAutoOutputClient(boolean enabled) {
      this.autoOutput = enabled;
   }

   private static void tryPushOutputDown(Level level, BlockPos pos, BlockState state, SoulExtractorTile tile) {
      ItemStack outputStack = tile.itemHandler.getStackInSlot(1);
      if (!outputStack.isEmpty()) {
         IItemHandler belowHandler = (IItemHandler)level.getCapability(ItemHandler.BLOCK, pos.below(), Direction.UP);
         if (belowHandler != null) {
            ItemStack remaining = outputStack.copy();

            for (int slot = 0; slot < belowHandler.getSlots() && !remaining.isEmpty(); slot++) {
               remaining = belowHandler.insertItem(slot, remaining, false);
            }

            if (remaining.getCount() != outputStack.getCount()) {
               int moved = outputStack.getCount() - remaining.getCount();
               ItemStack newStack = outputStack.copy();
               newStack.shrink(moved);
               if (newStack.isEmpty()) {
                  newStack = ItemStack.EMPTY;
               }

               tile.itemHandler.setStackInSlot(1, newStack);
               setChanged(level, pos, state);
            }
         }
      }
   }

   private void resetProgress() {
      this.progress = 0;
   }

   private static void craftItem(SoulExtractorTile pEntity) {
      Level level = pEntity.level;
      SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());

      for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
         inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
      }

      Optional<RecipeHolder<SoulExtractorRecipe>> recipe = Optional.empty();
      if (level != null) {
         recipe = level.getRecipeManager().getRecipeFor(ModRecipes.SOUL_EXTRACTION_TYPE.get(), getRecipeInput(inventory), level);
      }

      if (hasRecipe(pEntity)) {
         pEntity.itemHandler.extractItem(0, 1, false);
         pEntity.itemHandler
            .setStackInSlot(
               1, new ItemStack(((SoulExtractorRecipe)recipe.get().value()).output().copy().getItem(), pEntity.itemHandler.getStackInSlot(1).getCount() + 1)
            );
         pEntity.resetProgress();
      }
   }

   public static RecipeInput getRecipeInput(final SimpleContainer inventory) {
      return new RecipeInput() {
         @NotNull
         public ItemStack getItem(int index) {
            return inventory.getItem(index).copy();
         }

         public int size() {
            return inventory.getContainerSize();
         }
      };
   }

   private static boolean hasRecipe(SoulExtractorTile entity) {
      Level level = entity.level;
      SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());

      for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
         inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
      }

      Optional<RecipeHolder<SoulExtractorRecipe>> recipe = Optional.empty();
      if (level != null) {
         recipe = level.getRecipeManager().getRecipeFor(ModRecipes.SOUL_EXTRACTION_TYPE.get(), getRecipeInput(inventory), level);
         recipe.ifPresent(
            soulExtractorRecipeRecipeHolder -> entity.maxProgress = (Integer)Config.extractor_speedtimer.get()
               * ((SoulExtractorRecipe)soulExtractorRecipeRecipeHolder.value()).time()
         );
      }

      return recipe.isPresent()
         && canInsertAmountIntoOutputSlot(inventory)
         && canInsertItemIntoOutputSlot(inventory, ((SoulExtractorRecipe)recipe.get().value()).output().copy().getItem().getDefaultInstance());
   }

   private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
      return inventory.getItem(1).getItem() == stack.getItem() || inventory.getItem(1).isEmpty();
   }

   private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
      return inventory.getItem(1).getMaxStackSize() > inventory.getItem(1).getCount();
   }

   @Nullable
   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   @NotNull
   public CompoundTag getUpdateTag(@NotNull Provider prov) {
      return this.saveWithFullMetadata(prov);
   }
}
