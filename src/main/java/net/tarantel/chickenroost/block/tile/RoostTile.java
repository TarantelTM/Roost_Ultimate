package net.tarantel.chickenroost.block.tile;

import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
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
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.handler.RoostHandler;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import net.tarantel.chickenroost.networking.SyncAutoOutputPayload;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.RoostRecipe;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RoostTile extends BlockEntity implements MenuProvider, ICollectorTarget {
   private RecipeHolder<RoostRecipe> cachedRecipe = null;
   private boolean recipeDirty = true;

   public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
      protected void onContentsChanged(int slot) {
         RoostTile.this.setChanged();
         if (slot == 0 || slot == 1) {
            RoostTile.this.recipeDirty = true;
            RoostTile.this.cachedRecipe = null;
         }
         if (slot == 1) {
            RoostTile.this.resetProgress();
         }
      }

      public int getSlotLimit(int slot) {
         if (slot == 0) {
            return 64;
         } else if (slot == 1) {
            return 1;
         } else {
            return slot == 2 ? 64 : 0;
         }
      }

      public boolean isItemValid(int slot, @NotNull ItemStack stack) {
         return switch (slot) {
            case 0 -> stack.getItem() instanceof ChickenSeedBase;
            case 1 -> stack.getItem() instanceof ChickenItemBase;
            case 2 -> false;
            default -> super.isItemValid(slot, stack);
         };
      }
   };
   private final IItemHandler ccView = new IItemHandler() {
      public int getSlots() {
         return RoostTile.this.itemHandler.getSlots();
      }

      @NotNull
      public ItemStack getStackInSlot(int slot) {
         return RoostTile.this.itemHandler.getStackInSlot(slot);
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
         return RoostTile.this.itemHandler.getSlotLimit(slot);
      }

      public boolean isItemValid(int slot, @NotNull ItemStack stack) {
         return RoostTile.this.itemHandler.isItemValid(slot, stack);
      }
   };
   private static final int CHICKEN_SLOT = 1;
   private static final int OUTPUT_SLOT = 2;
   private String customName = "ROOST";
   protected final ContainerData data;
   public int progress = 0;
   public int maxProgress = (Integer)Config.roost_speed_tick.get() * 20;
   public final int[] LevelList;
   public final int[] XPList;
   public final int[] XPAmountList;
   private final IItemHandler itemHandlerSided = new InputOutputItemHandler(this.itemHandler, (i, stack) -> i == 0 || i == 1, i -> i == 2);
   private boolean autoOutput = false;
   private boolean lastRedstonePowered = false;
   private boolean autoOutputByRedstone = false;
   private boolean cachedPowered = false;
   public static ItemStack ChickenItem;
   public static ChickenSeedBase FoodItem;
   public static ChickenItemBase MyChicken;

   @Nullable
   @Override
   public IItemHandler getItemHandler() {
      return this.ccView;
   }

   @Override
   public int getReadSlot() {
      return 1;
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
      if (!this.itemHandler.getStackInSlot(1).isEmpty()) {
         stack = this.itemHandler.getStackInSlot(1);
      } else {
         stack = ItemStack.EMPTY;
      }

      return stack;
   }

   public void setChanged() {
      if (this.level != null) {
         setChanged(this.level, this.worldPosition, this.getBlockState());
         this.getRenderStack();
      }
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

   public RoostTile(BlockPos pos, BlockState state) {
      super(ModBlockEntities.ROOST.get(), pos, state);
      this.LevelList = new int[]{
         (Integer)Objects.requireNonNullElse((Integer)Config.maxlevel_tier_1.get(), 128),
         (Integer)Objects.requireNonNullElse((Integer)Config.maxlevel_tier_2.get(), 128),
         (Integer)Objects.requireNonNullElse((Integer)Config.maxlevel_tier_3.get(), 128),
         (Integer)Objects.requireNonNullElse((Integer)Config.maxlevel_tier_4.get(), 128),
         (Integer)Objects.requireNonNullElse((Integer)Config.maxlevel_tier_5.get(), 128),
         (Integer)Objects.requireNonNullElse((Integer)Config.maxlevel_tier_6.get(), 128),
         (Integer)Objects.requireNonNullElse((Integer)Config.maxlevel_tier_7.get(), 128),
         (Integer)Objects.requireNonNullElse((Integer)Config.maxlevel_tier_8.get(), 128),
         (Integer)Objects.requireNonNullElse((Integer)Config.maxlevel_tier_9.get(), 128)
      };
      this.XPList = new int[]{
         (Integer)Config.xp_tier_1.get(),
         (Integer)Config.xp_tier_2.get(),
         (Integer)Config.xp_tier_3.get(),
         (Integer)Config.xp_tier_4.get(),
         (Integer)Config.xp_tier_5.get(),
         (Integer)Config.xp_tier_6.get(),
         (Integer)Config.xp_tier_7.get(),
         (Integer)Config.xp_tier_8.get(),
         (Integer)Config.xp_tier_9.get()
      };
      this.XPAmountList = new int[]{
         (Integer)Config.food_xp_tier_1.get(),
         (Integer)Config.food_xp_tier_2.get(),
         (Integer)Config.food_xp_tier_3.get(),
         (Integer)Config.food_xp_tier_4.get(),
         (Integer)Config.food_xp_tier_5.get(),
         (Integer)Config.food_xp_tier_6.get(),
         (Integer)Config.food_xp_tier_7.get(),
         (Integer)Config.food_xp_tier_8.get(),
         (Integer)Config.food_xp_tier_9.get()
      };
      this.data = new ContainerData() {
         public int get(int index) {
            return switch (index) {
               case 0 -> RoostTile.this.progress;
               case 1 -> RoostTile.this.maxProgress;
               default -> 0;
            };
         }

         public void set(int index, int value) {
            switch (index) {
               case 0:
                  RoostTile.this.progress = value;
                  break;
               case 1:
                  RoostTile.this.maxProgress = value;
            }
         }

         public int getCount() {
            return 2;
         }
      };
   }

   @NotNull
   public Component getDisplayName() {
      return Component.translatable("");
   }

   @Nullable
   public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
      return new RoostHandler(id, inventory, this, this.data);
   }

   @Nullable
   public IItemHandler getItemHandlerCapability(@Nullable Direction side) {
      return (IItemHandler)(side == null ? this.itemHandler : this.itemHandlerSided);
   }

   public void onLoad() {
      super.onLoad();
      if (this.level != null) {
         this.cachedPowered = this.level.hasNeighborSignal(this.worldPosition);
      }
      this.setChanged();
      this.getRenderStack();
   }

   public void updateRedstoneSignal() {
      if (this.level != null) {
         this.cachedPowered = this.level.hasNeighborSignal(this.worldPosition);
      }
   }

   public void saveAdditional(CompoundTag nbt, @NotNull Provider lookup) {
      nbt.put("inventory", this.itemHandler.serializeNBT(lookup));
      nbt.putInt("roost.progress", this.progress);
      nbt.putString("roost.custom_name", this.customName);
      nbt.putBoolean("AutoOutput", this.autoOutput);
      nbt.putBoolean("LastRedstonePowered", this.lastRedstonePowered);
      nbt.putBoolean("AutoOutputByRedstone", this.autoOutputByRedstone);
      super.saveAdditional(nbt, lookup);
   }

   public void loadAdditional(@NotNull CompoundTag nbt, @NotNull Provider lookup) {
      super.loadAdditional(nbt, lookup);
      this.itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));
      this.progress = nbt.getInt("roost.progress");
      this.customName = nbt.getString("roost.custom_name");
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
      ItemStack itemStack = new ItemStack((ItemLike)ModBlocks.ROOST.get());
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

   public static void tick(Level level, BlockPos pos, BlockState state, RoostTile pEntity) {
      if (!level.isClientSide()) {
         ItemStack chickenStack = pEntity.itemHandler.getStackInSlot(1);
         if (!chickenStack.isEmpty() && chickenStack.getItem() instanceof ChickenItemBase chickenItem) {
            if (!chickenStack.has((DataComponentType)ModDataComponents.CHICKENLEVEL.value())) {
               chickenStack.set((DataComponentType)ModDataComponents.CHICKENLEVEL.value(), 0);
            }

            if (!chickenStack.has((DataComponentType)ModDataComponents.CHICKENXP.value())) {
               chickenStack.set((DataComponentType)ModDataComponents.CHICKENXP.value(), 0);
            }

            int levelNow = (Integer)chickenStack.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value());
            int maxLevel = pEntity.LevelList[chickenItem.currentchickena(chickenStack)];
            boolean isMax = levelNow >= maxLevel;
            if (!chickenStack.has((DataComponentType)ModDataComponents.MAXLEVEL.value())
               || (Boolean)chickenStack.get((DataComponentType)ModDataComponents.MAXLEVEL.value()) != isMax) {
               chickenStack.set((DataComponentType)ModDataComponents.MAXLEVEL.value(), isMax);
               pEntity.setChanged();
            }
         }

         boolean powered = pEntity.cachedPowered;
         if (powered && !pEntity.lastRedstonePowered && !pEntity.autoOutput) {
            pEntity.setAutoOutputFromRedstone(true);
         }

         if (!powered && pEntity.lastRedstonePowered && pEntity.autoOutputByRedstone) {
            pEntity.setAutoOutputFromRedstone(false);
         }

         pEntity.lastRedstonePowered = powered;

         ItemStack outputStack = pEntity.itemHandler.getStackInSlot(2);
         boolean outputFull = !outputStack.isEmpty() && outputStack.getCount() >= outputStack.getMaxStackSize();

         if (chickenStack.isEmpty() || outputFull) {
            if (pEntity.progress != 0) {
               pEntity.resetProgress();
               setChanged(level, pos, state);
            }
         } else if (hasRecipe(pEntity)) {
            pEntity.progress++;
            setChanged(level, pos, state);
            if (pEntity.progress >= pEntity.maxProgress) {
               craftItem(pEntity);
            }
         } else {
            if (pEntity.progress != 0) {
               pEntity.resetProgress();
               setChanged(level, pos, state);
            }
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

   private static void tryPushOutputDown(Level level, BlockPos pos, BlockState state, RoostTile tile) {
      ItemStack outputStack = tile.itemHandler.getStackInSlot(2);
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

               tile.itemHandler.setStackInSlot(2, newStack);
               setChanged(level, pos, state);
            }
         }
      }
   }

   private void resetProgress() {
      this.progress = 0;
   }

   public static RecipeInput getRecipeInput(final SimpleContainer inventory) {
      return new RecipeInput() {
         @NotNull
         public ItemStack getItem(int index) {
            return inventory.getItem(index);
         }

         public int size() {
            return inventory.getContainerSize();
         }
      };
   }

   private static void craftItem(RoostTile pEntity) {
      if (pEntity.cachedRecipe == null) return;
      RoostRecipe recipe = pEntity.cachedRecipe.value();

      MyChicken = (ChickenItemBase)pEntity.itemHandler.getStackInSlot(1).getItem().getDefaultInstance().getItem();
      ChickenItem = pEntity.itemHandler.getStackInSlot(1);
      Level level = pEntity.level;
      if (level == null) return;

      SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
      for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
         inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
      }

      if (ChickenRoostMod.CONFIG.RoostSeeds) {
         FoodItem = (ChickenSeedBase)pEntity.itemHandler.getStackInSlot(0).getItem().getDefaultInstance().getItem();
         int ChickenLevel;
         int ChickenXP;
         if (ChickenItem.has(ModDataComponents.CHICKENLEVEL) && ChickenItem.has(ModDataComponents.CHICKENXP)) {
            ChickenLevel = (Integer)ChickenItem.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value()) / 2
               + recipe.output().getCount();
            ChickenXP = (Integer)ChickenItem.get((DataComponentType)ModDataComponents.CHICKENXP.value());
         } else {
            ChickenLevel = 0;
            ChickenXP = 0;
            ChickenItem.set((DataComponentType)ModDataComponents.CHICKENLEVEL.value(), ChickenLevel);
            ChickenItem.set((DataComponentType)ModDataComponents.CHICKENXP.value(), ChickenXP);
         }

         ItemStack itemstack1 = recipe.assemble(getRecipeInput(inventory), level.registryAccess());
         int newCount = pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevel;
         itemstack1.setCount(Math.min(newCount, 64));
         if (pEntity.itemHandler.getStackInSlot(1).getItem() instanceof ChickenItemBase) {
            if ((Integer)ChickenItem.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value())
               < pEntity.LevelList[MyChicken.currentchickena(MyChicken.getDefaultInstance())]) {
               if (pEntity.itemHandler.getStackInSlot(0).getItem() instanceof ChickenSeedBase) {
                  if (ChickenXP + pEntity.XPAmountList[FoodItem.getCurrentMaxXp()] * (Double)Config.roostxp.get()
                     >= pEntity.XPList[MyChicken.currentchickena(MyChicken.getDefaultInstance())]) {
                     ChickenItem.set(
                        (DataComponentType)ModDataComponents.CHICKENLEVEL.value(),
                        (Integer)ChickenItem.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value()) + 1
                     );
                     ChickenItem.set((DataComponentType)ModDataComponents.CHICKENXP.value(), 0);
                  } else {
                     ChickenItem.set(
                        (DataComponentType)ModDataComponents.CHICKENXP.value(),
                        (int)(ChickenXP + pEntity.XPAmountList[FoodItem.getCurrentMaxXp()] * (Double)Config.roostxp.get())
                     );
                  }
               }

               pEntity.itemHandler.extractItem(0, 1, false);
               pEntity.itemHandler.extractItem(1, 0, true);
               pEntity.itemHandler.setStackInSlot(1, ChickenItem);
               pEntity.itemHandler.setStackInSlot(2, itemstack1.copy());
               pEntity.resetProgress();
            } else {
               pEntity.itemHandler.extractItem(0, 1, false);
               pEntity.itemHandler.extractItem(1, 0, true);
               pEntity.itemHandler.setStackInSlot(2, itemstack1.copy());
               pEntity.resetProgress();
            }
         }
      } else {
         int ChickenLevelx;
         if (ChickenItem.has(ModDataComponents.CHICKENLEVEL) && ChickenItem.has(ModDataComponents.CHICKENXP)) {
            ChickenLevelx = (Integer)ChickenItem.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value()) / 2
               + recipe.output().getCount();
         } else {
            ChickenLevelx = 0;
            ChickenItem.set((DataComponentType)ModDataComponents.CHICKENLEVEL.value(), ChickenLevelx);
            ChickenItem.set((DataComponentType)ModDataComponents.CHICKENXP.value(), 0);
         }

         ItemStack itemstack1 = recipe.assemble(getRecipeInput(inventory), level.registryAccess());
         int newCount = pEntity.itemHandler.getStackInSlot(2).getCount() + ChickenLevelx;
         itemstack1.setCount(Math.min(newCount, 64));
         pEntity.itemHandler.extractItem(1, 0, true);
         pEntity.itemHandler.setStackInSlot(2, itemstack1.copy());
         pEntity.resetProgress();
      }
   }

   private static boolean hasRecipe(RoostTile entity) {
      Level level = entity.level;
      if (level == null) return false;

      if (entity.recipeDirty) {
         entity.recipeDirty = false;
         SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
         for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
         }
         Optional<RecipeHolder<RoostRecipe>> found = level.getRecipeManager()
            .getRecipeFor(ModRecipes.ROOST_TYPE.get(), getRecipeInput(inventory), level);
         entity.cachedRecipe = found.orElse(null);
         if (entity.cachedRecipe != null) {
            entity.maxProgress = (Integer)Config.roost_speed_tick.get() * entity.cachedRecipe.value().time();
         }
      }

      if (entity.cachedRecipe == null) {
         return false;
      }

      RoostRecipe recipe = entity.cachedRecipe.value();
      ItemStack planned = recipe.output().getItem().getDefaultInstance();
      planned.applyComponents(recipe.output().getComponentsPatch());
      int fullPlanned = computePlannedCountWithChickenLevel(entity, recipe);
      int maxStack = planned.getMaxStackSize();
      int batch = Math.min(Math.max(1, fullPlanned), maxStack);
      ItemStack out = entity.itemHandler.getStackInSlot(2);
      int placeable = getPlaceableAmount(out, planned);
      return placeable >= batch;
   }

   private static int getPlaceableAmount(ItemStack out, ItemStack planned) {
      int maxStack = planned.getMaxStackSize();
      if (out.isEmpty()) {
         return maxStack;
      } else {
         return !ItemStack.isSameItemSameComponents(out, planned) ? 0 : Math.max(0, maxStack - out.getCount());
      }
   }

   private static int computePlannedCountWithChickenLevel(RoostTile entity, RoostRecipe r) {
      int baseOut = r.output().getCount();
      int addByLevel = 0;
      ItemStack chickenStack = entity.itemHandler.getStackInSlot(1);
      if (!chickenStack.isEmpty() && chickenStack.has(ModDataComponents.CHICKENLEVEL)) {
         Integer cl = (Integer)chickenStack.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value());
         if (cl != null) {
            addByLevel = cl / 2;
         }
      }

      return Math.max(1, baseOut + addByLevel);
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
