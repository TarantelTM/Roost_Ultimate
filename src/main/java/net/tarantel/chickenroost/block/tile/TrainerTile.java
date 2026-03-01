package net.tarantel.chickenroost.block.tile;

import java.util.Objects;
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
import net.tarantel.chickenroost.handler.TrainerHandler;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import net.tarantel.chickenroost.networking.SyncAutoOutputPayload;
import net.tarantel.chickenroost.networking.SyncTrainerLevelPayload;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

public class TrainerTile extends BlockEntity implements MenuProvider, GeoBlockEntity, ICollectorTarget {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
      protected void onContentsChanged(int slot) {
         TrainerTile.this.setChanged();
         if (slot == 0) {
            TrainerTile.this.resetProgress();
         }

         assert TrainerTile.this.level != null;

         if (!TrainerTile.this.level.isClientSide()) {
            TrainerTile.this.level.sendBlockUpdated(TrainerTile.this.getBlockPos(), TrainerTile.this.getBlockState(), TrainerTile.this.getBlockState(), 3);
         }
      }

      public int getSlotLimit(int slot) {
         if (slot == 0) {
            return 1;
         } else {
            return slot == 1 ? 64 : 0;
         }
      }

      public boolean isItemValid(int slot, @NotNull ItemStack stack) {
         return switch (slot) {
            case 0 -> stack.getItem() instanceof ChickenItemBase;
            case 1 -> stack.getItem() instanceof ChickenSeedBase;
            default -> super.isItemValid(slot, stack);
         };
      }
   };
   private static final int CHICKEN_SLOT = 0;
   private final IItemHandler ccView = new IItemHandler() {
      public int getSlots() {
         return TrainerTile.this.itemHandler.getSlots();
      }

      @NotNull
      public ItemStack getStackInSlot(int slot) {
         return TrainerTile.this.itemHandler.getStackInSlot(slot);
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
         return TrainerTile.this.itemHandler.getSlotLimit(slot);
      }

      public boolean isItemValid(int slot, @NotNull ItemStack stack) {
         return TrainerTile.this.itemHandler.isItemValid(slot, stack);
      }
   };
   private static final RawAnimation CRAFTING = RawAnimation.begin().then("training", LoopType.LOOP);
   private static final RawAnimation IDLE = RawAnimation.begin().then("idle", LoopType.LOOP);
   private String customName = "TRAINER";
   protected final ContainerData data;
   public int progress = 0;
   public int maxProgress = (Integer)Config.training_speed_tick.get() * 20;
   public final int[] LevelList;
   public final int[] XPList;
   public final int[] XPAmountList;
   private final IItemHandler itemHandlerSided = new InputOutputItemHandler(this.itemHandler, (i, stack) -> i == 0 || i == 1, i -> i == 0);
   public static ItemStack MyChicken;
   public static ChickenSeedBase FoodItem;
   public static ChickenItemBase ChickenItem;
   private boolean autoOutput = false;
   private boolean lastRedstonePowered = false;
   private boolean autoOutputByRedstone = false;
   private int autoOutputLevel = 128;

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public double getTick(Object blockEntity) {
      return RenderUtil.getCurrentTick();
   }

   @Override
   public int getReadSlot() {
      return 0;
   }

   @Nullable
   @Override
   public IItemHandler getItemHandler() {
      return this.ccView;
   }

   public void registerControllers(ControllerRegistrar controllerRegistrar) {
      controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
   }

   private PlayState predicate(AnimationState<GeoAnimatable> state) {
      AnimationController<GeoAnimatable> controller = state.getController();
      controller.triggerableAnim("craft", CRAFTING);
      controller.triggerableAnim("idle", IDLE);
      return PlayState.CONTINUE;
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

   public TrainerTile(BlockPos pos, BlockState state) {
      super(ModBlockEntities.TRAINER.get(), pos, state);
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
               case 0 -> TrainerTile.this.progress;
               case 1 -> TrainerTile.this.maxProgress;
               default -> 0;
            };
         }

         public void set(int index, int value) {
            switch (index) {
               case 0:
                  TrainerTile.this.progress = value;
                  break;
               case 1:
                  TrainerTile.this.maxProgress = value;
            }
         }

         public int getCount() {
            return 2;
         }
      };
   }

   @NotNull
   public Component getDisplayName() {
      return Component.translatable("name.chicken_roost.trainer");
   }

   @Nullable
   public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
      return new TrainerHandler(id, inventory, this, this.data);
   }

   @Nullable
   public IItemHandler getItemHandlerCapability(@Nullable Direction side) {
      return (IItemHandler)(side == null ? this.itemHandler : this.itemHandlerSided);
   }

   public void onLoad() {
      super.onLoad();
      this.setChanged();
   }

   public void saveAdditional(CompoundTag nbt, @NotNull Provider lookup) {
      nbt.put("inventory", this.itemHandler.serializeNBT(lookup));
      nbt.putInt("trainer.progress", this.progress);
      nbt.putString("trainer.custom_name", this.customName);
      nbt.putBoolean("AutoOutput", this.autoOutput);
      nbt.putBoolean("LastRedstonePowered", this.lastRedstonePowered);
      nbt.putBoolean("AutoOutputByRedstone", this.autoOutputByRedstone);
      nbt.putInt("TrainerAutoOutputLevel", this.autoOutputLevel);
      super.saveAdditional(nbt, lookup);
   }

   public void loadAdditional(@NotNull CompoundTag nbt, @NotNull Provider lookup) {
      super.loadAdditional(nbt, lookup);
      this.itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));
      this.progress = nbt.getInt("trainer.progress");
      this.customName = nbt.getString("trainer.custom_name");
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

      if (nbt.contains("TrainerAutoOutputLevel")) {
         this.autoOutputLevel = nbt.getInt("TrainerAutoOutputLevel");
      } else {
         this.autoOutputLevel = 0;
      }
   }

   public void drops() {
      SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
      SimpleContainer block = new SimpleContainer(1);
      ItemStack itemStack = new ItemStack((ItemLike)ModBlocks.TRAINER.get());
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

   public static void tick(Level level, BlockPos pos, BlockState state, TrainerTile pEntity) {
      if (!level.isClientSide()) {
         ItemStack chickenStack = pEntity.itemHandler.getStackInSlot(0);
         ItemStack seedStack = pEntity.itemHandler.getStackInSlot(1);
         boolean hasChicken = chickenStack.getItem() instanceof ChickenItemBase;
         boolean hasSeed = seedStack.getItem() instanceof ChickenSeedBase;
         if (hasChicken) {
            ChickenItemBase chickenItem = (ChickenItemBase)chickenStack.getItem();
            if (!chickenStack.has((DataComponentType)ModDataComponents.CHICKENLEVEL.value())) {
               chickenStack.set((DataComponentType)ModDataComponents.CHICKENLEVEL.value(), 0);
            }

            if (!chickenStack.has((DataComponentType)ModDataComponents.CHICKENXP.value())) {
               chickenStack.set((DataComponentType)ModDataComponents.CHICKENXP.value(), 0);
            }

            int levelNow = (Integer)chickenStack.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value());
            int maxLevel = pEntity.LevelList[chickenItem.currentchickena(chickenStack)];
            chickenStack.set((DataComponentType)ModDataComponents.MAXLEVEL.value(), levelNow >= maxLevel);
            pEntity.setChanged();
         }

         if (hasChicken && hasSeed) {
            ChickenItemBase chickenItemx = (ChickenItemBase)chickenStack.getItem();
            int chickenLevel = (Integer)chickenStack.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value());
            int maxLevel = pEntity.LevelList[chickenItemx.currentchickena(chickenStack)];
            if (chickenLevel < maxLevel) {
               pEntity.progress++;
               pEntity.triggerAnim("controller", "craft");
               if (pEntity.progress >= pEntity.maxProgress) {
                  craftItem(pEntity);
               }
            } else {
               pEntity.resetProgress();
               pEntity.triggerAnim("controller", "idle");
            }
         } else {
            pEntity.resetProgress();
            pEntity.triggerAnim("controller", "idle");
            setChanged(level, pos, state);
         }

         boolean powered = level.hasNeighborSignal(pos);
         if (powered && !pEntity.lastRedstonePowered && !pEntity.autoOutput) {
            pEntity.setAutoOutputFromRedstone(true);
         }

         if (!powered && pEntity.lastRedstonePowered && pEntity.autoOutputByRedstone) {
            pEntity.setAutoOutputFromRedstone(false);
         }

         pEntity.lastRedstonePowered = powered;
         if (pEntity.isAutoOutputEnabled()) {
            tryPushChickenDown(level, pos, state, pEntity);
         }
      }
   }

   @Override
   public void setAutoOutputClient(boolean enabled) {
      this.autoOutput = enabled;
   }

   private void resetProgress() {
      this.progress = 0;
   }

   public final void setChanged() {
      this.setChanged(true);
   }

   protected void setChanged(boolean updateComparator) {
   }

   private static void craftItem(TrainerTile pEntity) {
      ChickenItem = (ChickenItemBase)pEntity.itemHandler.getStackInSlot(0).getItem();
      MyChicken = pEntity.itemHandler.getStackInSlot(0);
      FoodItem = (ChickenSeedBase)pEntity.itemHandler.getStackInSlot(1).getItem().getDefaultInstance().getItem();
      SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());

      for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
         inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
      }

      int ChickenXP;
      if (MyChicken.has(ModDataComponents.CHICKENLEVEL) && MyChicken.has(ModDataComponents.CHICKENXP)) {
         ChickenXP = (Integer)MyChicken.get((DataComponentType)ModDataComponents.CHICKENXP.value());
      } else {
         int ChickenLevel = 0;
         ChickenXP = 0;
         MyChicken.set((DataComponentType)ModDataComponents.CHICKENLEVEL.value(), ChickenLevel);
         MyChicken.set((DataComponentType)ModDataComponents.CHICKENXP.value(), ChickenXP);
      }

      if (pEntity.itemHandler.getStackInSlot(0).getItem() instanceof ChickenItemBase) {
         if ((Integer)MyChicken.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value())
            < pEntity.LevelList[ChickenItem.currentchickena(ChickenItem.getDefaultInstance())]) {
            if (pEntity.itemHandler.getStackInSlot(1).getItem() instanceof ChickenSeedBase) {
               if (ChickenXP + pEntity.XPAmountList[FoodItem.getCurrentMaxXp()]
                  >= pEntity.XPList[ChickenItem.currentchickena(ChickenItem.getDefaultInstance())]) {
                  MyChicken.set(
                     (DataComponentType)ModDataComponents.CHICKENLEVEL.value(),
                     (Integer)MyChicken.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value()) + 1
                  );
                  MyChicken.set((DataComponentType)ModDataComponents.CHICKENXP.value(), 0);
                  int chickenlvl = (Integer)MyChicken.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value());
                  int chickenxp = (Integer)MyChicken.get((DataComponentType)ModDataComponents.CHICKENXP.value());
               } else {
                  MyChicken.set((DataComponentType)ModDataComponents.CHICKENXP.value(), ChickenXP + pEntity.XPAmountList[FoodItem.getCurrentMaxXp()]);
                  int chickenlvl = (Integer)MyChicken.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value());
                  int var7 = (Integer)MyChicken.get((DataComponentType)ModDataComponents.CHICKENXP.value());
               }
            }

            pEntity.itemHandler.extractItem(1, 1, false);
            pEntity.itemHandler.extractItem(0, 0, false);
            pEntity.itemHandler.setStackInSlot(0, MyChicken);
            pEntity.resetProgress();
         } else {
            pEntity.resetProgress();
         }
      }
   }

   @Nullable
   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   @NotNull
   public CompoundTag getUpdateTag(@NotNull Provider prov) {
      return this.saveWithFullMetadata(prov);
   }

   public int getAutoOutputLevel() {
      return this.autoOutputLevel;
   }

   public void setAutoOutputLevel(int level) {
      this.autoOutputLevel = Math.max(0, level);
      this.syncTrainerLevelToClients();
      this.setChanged();
      if (this.level != null && !this.level.isClientSide) {
         this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
      }
   }

   public void setAutoOutputLevelClient(int level) {
      this.autoOutputLevel = Math.max(0, level);
   }

   private static void tryPushChickenDown(Level level, BlockPos pos, BlockState state, TrainerTile tile) {
      ItemStack stack = tile.itemHandler.getStackInSlot(0);
      if (!stack.isEmpty()) {
         if (stack.getItem() instanceof ChickenItemBase) {
            if (stack.has((DataComponentType)ModDataComponents.CHICKENLEVEL.value())) {
               Integer chickenLevel = (Integer)stack.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value());
               if (chickenLevel != null) {
                  if (chickenLevel >= tile.autoOutputLevel) {
                     IItemHandler below = (IItemHandler)level.getCapability(ItemHandler.BLOCK, pos.below(), Direction.UP);
                     if (below != null) {
                        ItemStack remaining = stack.copy();

                        for (int s = 0; s < below.getSlots() && !remaining.isEmpty(); s++) {
                           remaining = below.insertItem(s, remaining, false);
                        }

                        if (remaining.getCount() != stack.getCount()) {
                           int moved = stack.getCount() - remaining.getCount();
                           ItemStack newStack = stack.copy();
                           newStack.shrink(moved);
                           if (newStack.isEmpty()) {
                              newStack = ItemStack.EMPTY;
                           }

                           tile.itemHandler.setStackInSlot(0, newStack);
                           setChanged(level, pos, state);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void syncTrainerLevelToClients() {
      if (this.level instanceof ServerLevel serverLevel) {
         PacketDistributor.sendToPlayersTrackingChunk(
            serverLevel,
            serverLevel.getChunkAt(this.worldPosition).getPos(),
            new SyncTrainerLevelPayload(this.worldPosition, this.autoOutputLevel),
            new CustomPacketPayload[0]
         );
      }
   }
}
