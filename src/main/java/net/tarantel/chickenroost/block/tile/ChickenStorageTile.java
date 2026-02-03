package net.tarantel.chickenroost.block.tile;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import net.tarantel.chickenroost.item.base.RoostUltimateItem;
import net.tarantel.chickenroost.util.ModDataComponents;
import net.tarantel.chickenroost.util.RoostItemContainerContents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChickenStorageTile extends BlockEntity {
   public final ItemStackHandler itemHandler = new ItemStackHandler(2147483) {
      protected void onContentsChanged(int slot) {
         ChickenStorageTile.this.setChanged();

         assert ChickenStorageTile.this.level != null;

         if (!ChickenStorageTile.this.level.isClientSide()) {
            ChickenStorageTile.this.level
               .sendBlockUpdated(ChickenStorageTile.this.getBlockPos(), ChickenStorageTile.this.getBlockState(), ChickenStorageTile.this.getBlockState(), 3);
         }
      }

      public boolean isItemValid(int slot, @NotNull ItemStack stack) {
         return stack.getItem() instanceof RoostUltimateItem || stack.getItem() instanceof ChickenSeedBase;
      }
   };
   public static final BlockCapability<IItemHandler, @Nullable Direction> ITEM_HANDLER_BLOCK = BlockCapability.create(
      ResourceLocation.fromNamespaceAndPath("mymod", "item_handler"), IItemHandler.class, Direction.class
   );

   public void drops() {
      SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
      SimpleContainer block = new SimpleContainer(1);
      int integer = 0;
      ItemStack itemStack = new ItemStack((ItemLike)ModBlocks.CHICKENSTORAGE.get());
      NonNullList<ItemStack> items = inventory.getItems();

      for (int i = 0; i < this.itemHandler.getSlots(); i++) {
         items.set(i, this.itemHandler.getStackInSlot(i));
         if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
            integer += this.itemHandler.getStackInSlot(i).getCount();
         }
      }

      itemStack.set((DataComponentType)ModDataComponents.CONTAINER.value(), RoostItemContainerContents.fromItems(inventory.getItems()));
      itemStack.set((DataComponentType)ModDataComponents.STORAGEAMOUNT.value(), integer);
      block.setItem(0, itemStack.copy());
      Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
   }

   public ChickenStorageTile(BlockPos pos, BlockState state) {
      super(ModBlockEntities.CHICKENSTORAGE.get(), pos, state);
   }

   public void setHandler(ItemStackHandler itemStackHandler) {
      for (int i = 0; i < itemStackHandler.getSlots(); i++) {
         this.itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
      }
   }

   public void onLoad() {
      super.onLoad();
      this.setChanged();
   }

   @Nullable
   public IItemHandler getItemHandlerCapability(@Nullable Direction side) {
      return side == null ? this.itemHandler : this.itemHandler;
   }

   public void saveAdditional(CompoundTag nbt, @NotNull Provider lookup) {
      nbt.put("inventory", this.itemHandler.serializeNBT(lookup));
      super.saveAdditional(nbt, lookup);
   }

   public void loadAdditional(@NotNull CompoundTag nbt, @NotNull Provider lookup) {
      super.loadAdditional(nbt, lookup);
      this.itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));
   }

   public static void tick(Level level, BlockPos pos, BlockState state, ChickenStorageTile pEntity) {
      if (!level.isClientSide()) {
         setChanged(level, pos, state);
      }
   }

   @Nullable
   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   @NotNull
   public CompoundTag getUpdateTag(@NotNull Provider registries) {
      return new CompoundTag();
   }
}
