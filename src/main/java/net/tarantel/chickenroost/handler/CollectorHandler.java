package net.tarantel.chickenroost.handler;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.CollectorTile;
import org.jetbrains.annotations.NotNull;

public class CollectorHandler extends AbstractContainerMenu {
   public final CollectorTile blockEntity;
   public final Level level;
   private static final int HOTBAR_SLOT_COUNT = 9;
   private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
   private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
   private static final int PLAYER_INVENTORY_SLOT_COUNT = 27;
   private static final int VANILLA_SLOT_COUNT = 36;
   private static final int VANILLA_FIRST_SLOT_INDEX = 0;
   private static final int TE_INVENTORY_FIRST_SLOT_INDEX = 36;
   private static final int TE_INVENTORY_SLOT_COUNT = 54;

   public CollectorHandler(int id, Inventory inv, FriendlyByteBuf buf) {
      this(id, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
   }

   public CollectorHandler(int id, Inventory inv, BlockEntity entity) {
      super(ModHandlers.COLLECTOR_MENU.get(), id);
      checkContainerSize(inv, 3);
      this.blockEntity = (CollectorTile)entity;
      this.level = inv.player.level();
      this.addPlayerInventory(inv);
      this.addPlayerHotbar(inv);
      int i = 36;
      ItemCapabilityMenuHelper.getCapabilityItemHandler(this.level, this.blockEntity).ifPresent(itemHandler -> {
         for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 9; k++) {
               this.addSlot(new SlotItemHandler(itemHandler, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
         }
      });
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
            if (!this.moveItemStackTo(sourceStack, 36, 90, false)) {
               return ItemStack.EMPTY;
            }
         } else {
            if (index >= 90) {
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
      return stillValid(ContainerLevelAccess.create(this.level, this.blockEntity.getBlockPos()), player, (Block)ModBlocks.COLLECTOR.get());
   }

   private void addPlayerInventory(Inventory playerInventory) {
      for (int i = 0; i < 3; i++) {
         for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 139 + i * 18));
         }
      }
   }

   private void addPlayerHotbar(Inventory playerInventory) {
      for (int i = 0; i < 9; i++) {
         this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 197));
      }
   }

   public BlockEntity getBlockEntity() {
      return this.blockEntity;
   }
}
