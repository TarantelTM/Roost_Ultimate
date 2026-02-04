package net.tarantel.chickenroost.handler;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.SoulExtractorTile;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import org.jetbrains.annotations.NotNull;

public class SoulExtractorHandler extends AbstractContainerMenu {
   public final SoulExtractorTile blockEntity;
   public final Level level;
   private final ContainerData data;
   private static final int HOTBAR_SLOT_COUNT = 9;
   private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
   private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
   private static final int PLAYER_INVENTORY_SLOT_COUNT = 27;
   private static final int VANILLA_SLOT_COUNT = 36;
   private static final int VANILLA_FIRST_SLOT_INDEX = 0;
   private static final int TE_INVENTORY_FIRST_SLOT_INDEX = 36;
   private static final int TE_INVENTORY_SLOT_COUNT = 2;

   public SoulExtractorHandler(int id, Inventory inv, FriendlyByteBuf extraData) {
      this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
   }

   public SoulExtractorHandler(int id, Inventory inv, BlockEntity entity, ContainerData data) {
      super(ModHandlers.SOUL_EXTRACTOR_MENU.get(), id);
      checkContainerSize(inv, 2);
      this.blockEntity = (SoulExtractorTile)entity;
      this.level = inv.player.level();
      this.data = data;
      this.addPlayerInventory(inv);
      this.addPlayerHotbar(inv);
      ItemCapabilityMenuHelper.getCapabilityItemHandler(this.level, this.blockEntity).ifPresent(itemHandler -> {
         this.addSlot(new SlotItemHandler(itemHandler, 0, 29, 38) {
            public boolean mayPlace(@NotNull ItemStack stack) {
               return stack.getItem() instanceof ChickenItemBase;
            }
         });
         this.addSlot(new SlotItemHandler(itemHandler, 1, 111, 38) {
            public boolean mayPlace(@NotNull ItemStack stack) {
               return false;
            }
         });
      });
      this.addDataSlots(data);
   }

   public boolean isCrafting() {
      return this.data.get(0) > 0;
   }

   public int getProgress() {
      return this.data.get(0);
   }

   public int getMaxProgress() {
      return this.data.get(1);
   }

   public int getScaledProgress(int arrowWidth) {
      int progress = this.getProgress();
      int maxProgress = this.getMaxProgress();
      return maxProgress != 0 && progress != 0 ? progress * arrowWidth / maxProgress : 0;
   }

   @NotNull
   public ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
      Slot sourceSlot = (Slot)this.slots.get(index);
      if (!sourceSlot.hasItem()) {
         return ItemStack.EMPTY;
      } else {
         ItemStack sourceStack = sourceSlot.getItem();
         ItemStack copyOfSourceStack = sourceStack.copy();
         if (index < 36) {
            if (!this.moveItemStackTo(sourceStack, 36, 38, false)) {
               return ItemStack.EMPTY;
            }
         } else {
            if (index >= 38) {
               System.out.println("Invalid slotIndex:" + index);
               return ItemStack.EMPTY;
            }

            if (!this.moveItemStackTo(sourceStack, 0, 36, false)) {
               return ItemStack.EMPTY;
            }
         }

         if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
         } else {
            sourceSlot.setChanged();
         }

         sourceSlot.onTake(playerIn, sourceStack);
         return copyOfSourceStack;
      }
   }

   public boolean stillValid(@NotNull Player player) {
      return stillValid(ContainerLevelAccess.create(this.level, this.blockEntity.getBlockPos()), player, (Block)ModBlocks.SOUL_EXTRACTOR.get());
   }

   private void addPlayerInventory(Inventory playerInventory) {
      for (int i = 0; i < 3; i++) {
         for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
         }
      }
   }

   private void addPlayerHotbar(Inventory playerInventory) {
      for (int i = 0; i < 9; i++) {
         this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
      }
   }

   public BlockEntity getBlockEntity() {
      return this.blockEntity;
   }
}
