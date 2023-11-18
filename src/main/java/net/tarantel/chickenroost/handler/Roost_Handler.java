package net.tarantel.chickenroost.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.tarantel.chickenroost.util.TagManager;

public class roost_handler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;


    public roost_handler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, new SimpleInventory(3), new ArrayPropertyDelegate(2));
    }



    public roost_handler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.ROOST, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = propertyDelegate;
        this.addSlot(new Slot(inventory, 1, 29, 38){
            @Override
            public boolean canInsert(ItemStack stack) {
                if(stack.isIn(TagManager.Items.CHICKEN)){
                    return true;
                }
                return false;
            }
            @Override
            public int getMaxItemCount() {
                return 1;
            }
            /*@Override
            public void onQuickTransfer(ItemStack newItem, ItemStack original) {
                int i = original.getCount() - newItem.getCount();
                if (i > 0) {
                    this.onCrafted(original, 1);
                }

            }*/
        });
        this.addSlot(new Slot(inventory, 0, 11, 15){
            @Override
            public boolean canInsert(ItemStack stack) {
                if(stack.isIn(TagManager.Items.SEEDS)){
                    return true;
                }
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 2, 111, 38){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(propertyDelegate);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);  // Max Progress
        int progressArrowSize = 39;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }


    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot sslot = (Slot)this.slots.get(slot);
        if (sslot != null && sslot.hasStack()) {
            ItemStack itemStack2 = sslot.getStack();
            itemStack = itemStack2.copy();
            if (slot == 0) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot == 1) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            }else if (slot == 2) {
                if (!this.insertItem(itemStack2, 3, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemStack2.isIn(TagManager.Items.SEEDS)) {
                if (!this.insertItem(itemStack2, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (((Slot)this.slots.get(0)).hasStack() || !((Slot)this.slots.get(0)).canInsert(itemStack2)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemStack3 = itemStack2.copy();
                itemStack3.setCount(1);
                itemStack2.decrement(1);
                ((Slot)this.slots.get(0)).setStack(itemStack3);
            }

            if (itemStack2.isEmpty()) {
                sslot.setStack(ItemStack.EMPTY);
            } else {
                sslot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            sslot.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }



    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
    @Override
    public void onContentChanged(Inventory inventory) {
        this.sendContentUpdates();
    }
}