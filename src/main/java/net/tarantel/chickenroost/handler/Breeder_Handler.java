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

public class breeder_handler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;


    public breeder_handler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, new SimpleInventory(10), new ArrayPropertyDelegate(2));
    }



    public breeder_handler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.BREEDER, syncId);
        checkSize(inventory, 10);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = propertyDelegate;
        this.addSlot(new Slot(inventory, 1, 7, 26){
            @Override
            public boolean canInsert(ItemStack stack) {
                if(stack.isIn(TagManager.Items.BREEDABLE)){
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
        this.addSlot(new Slot(inventory, 0, 34, 26){
            @Override
            public boolean canInsert(ItemStack stack) {
                if(stack.isIn(TagManager.Items.SEEDS)){
                    return true;
                }
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 2, 97, 26){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 3, 115, 26){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 4, 133, 26){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 5, 151, 26){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 6, 97, 44){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 7, 115, 44){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 8, 133, 44){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 9, 151, 44){
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
        int progressArrowSize = 39; // This is the width in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    /*@Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if(invSlot == 0){

            }
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }*/

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

    /*@Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        System.out.println("transferSlot called");

        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                    return ItemStack.EMPTY;
                }

                if (originalStack.isEmpty()) {
                    slot.setStack(ItemStack.EMPTY);
                }
                else {
                    slot.markDirty();
                }
            }
        }
        return newStack;
    }*/

    /*@Override
    public ItemStack transferSlot(PlayerEntity player, int slotId) {*/


        /*final Slot slot = this.slots.get(slotId);

        final int firstSlot = 0;
        final int secondSlot = 1;
        //final int lastSlot = this.isHopper() ? 50 : 38;

        ItemStack unmovedItems = ItemStack.EMPTY;

        if (slot.hasStack()) {

            ItemStack slotStack = slot.getStack();
            unmovedItems = slotStack.copy();

            // Output Slots
            if (slotId >= 2 && slotId <= 13) {

                // Attempt moving to player inventory.
                if (!this.insertItem(slotStack, firstSlot, secondSlot, true)) {

                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(slotStack, unmovedItems);
            }

            // Soil and Seed slots
            else if (slotId == 0 || slotId == 1) {

                // Attempt moving to player inventory.
                if (!this.insertItem(slotStack, firstSlot, secondSlot, true)) {

                    return ItemStack.EMPTY;
                }
            }

            else if (slotId >= firstSlot && slotId <= secondSlot) {

                final Slot soilSlot = this.slots.get(0);

                // Try to insert a soil
                if (!soilSlot.hasStack() && slotStack.isIn(TagManager.Items.SEEDS)) {

                    soilSlot.setStack(slotStack.split(64));
                    slot.setStack(slotStack);

                    if (slotStack.isEmpty()) {

                        return ItemStack.EMPTY;
                    }
                }

                final Slot cropSlot = this.slots.get(1);

                if (!cropSlot.hasStack() && slotStack.isIn(TagManager.Items.BREEDABLE)) {

                    cropSlot.setStack(slotStack.split(1));
                    slot.setStack(slotStack);

                    if (slotStack.isEmpty()) {

                        return ItemStack.EMPTY;
                    }
                }
            }

            if (slotStack.isEmpty()) {

                slot.setStack(ItemStack.EMPTY);
            }

            else {

                slot.markDirty();
            }

            if (slotStack.getCount() == unmovedItems.getCount()) {

                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, slotStack);
        }

        return unmovedItems;*/
      /*  return ItemStack.EMPTY;
    }*/








  /*  @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {

        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }*/



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

   /* @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }*/

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
   /* private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 10;  // must be the number of slots you have!

    @Override
    public ItemStack transferSlot(PlayerEntity playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!insertItem(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!insertItem(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.setStack(ItemStack.EMPTY);
        } else {
            sourceSlot.markDirty();
        }
        sourceSlot.onTakeItem(playerIn, sourceStack);
        return copyOfSourceStack;
    }*/



   /* @Override
    public void onSlotClick (int slotIndex, int button, SlotActionType actionType, PlayerEntity player){
        try {
            internalOnSlotClick(slotIndex, button, actionType, player);
        } catch (Exception var8) {
            CrashReport crashReport = CrashReport.create(var8, "Container click");
            CrashReportSection crashReportSection = crashReport.addElement("Click info");
            crashReportSection.add("Menu Type", () -> {
                return this.getType() != null ? Registry.SCREEN_HANDLER.getId(this.getType()).toString() : "<no type>";
            });
            crashReportSection.add("Menu Class", () -> {
                return this.getClass().getCanonicalName();
            });
            crashReportSection.add("Slot Count", this.slots.size());
            crashReportSection.add("Slot", slotIndex);
            crashReportSection.add("Button", button);
            crashReportSection.add("Type", actionType);
            throw new CrashException(crashReport);
        }
    }

    private void internalOnSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        PlayerInventory playerInventory = player.getInventory();
        Slot slot;
        ItemStack itemStack;
        ItemStack itemStack2;
        int j;
        int k;
        {
            int n;
            if ((actionType == SlotActionType.PICKUP || actionType == SlotActionType.QUICK_MOVE) && (button == 0 || button == 1)) {
                ClickType clickType = button == 0 ? ClickType.LEFT : ClickType.RIGHT;
                if (slotIndex == -999) {
                    if (!this.getCursorStack().isEmpty()) {
                        if (clickType == ClickType.LEFT) {
                            player.dropItem(this.getCursorStack(), true);
                            this.setCursorStack(ItemStack.EMPTY);
                        } else {
                            player.dropItem(this.getCursorStack().split(1), true);
                        }
                    }
                } else if (actionType == SlotActionType.QUICK_MOVE) {
                    if (slotIndex < 0) {
                        return;
                    }

                    slot = this.slots.get(slotIndex);
                    if (!slot.canTakeItems(player)) {
                        return;
                    }

                    this.transferSlot(player, slotIndex);
                } else {
                    if(slotIndex == -1 || slots.get(slotIndex) instanceof Slot && !canInsertIntoSlot(this.getCursorStack(), (Slot)this.slots.get(slotIndex)) && this.getCursorStack().getItem() != Items.AIR){
                        return;
                    }

                    if (slotIndex < 0) {
                        return;
                    }

                    slot = this.slots.get(slotIndex);
                    itemStack = slot.getStack();
                    ItemStack itemStack5 = this.getCursorStack();
                    player.onPickupSlotClick(itemStack5, slot.getStack(), clickType);
                    if (!itemStack5.onStackClicked(slot, clickType, player) && !itemStack.onClicked(itemStack5, slot, clickType, player, this.getCursorStackReference())) {
                        if (itemStack.isEmpty()) {
                            if (!itemStack5.isEmpty()) {
                                n = clickType == ClickType.LEFT ? itemStack5.getCount() : 1;
                                this.setCursorStack(slot.insertStack(itemStack5, n));
                            }
                        } else if (slot.canTakeItems(player)) {
                            if (itemStack5.isEmpty()) {
                                n = clickType == ClickType.LEFT ? itemStack.getCount() : (itemStack.getCount() + 1) / 2;
                                Optional<ItemStack> optional = slot.tryTakeStackRange(n, Integer.MAX_VALUE, player);
                                optional.ifPresent((stack) -> {
                                    this.setCursorStack(stack);
                                    slot.onTakeItem(player, stack);
                                });
                            } else if (slot.canInsert(itemStack5)) {
                                if (ItemStack.canCombine(itemStack, itemStack5)) {
                                    n = clickType == ClickType.LEFT ? itemStack5.getCount() : 1;
                                    this.setCursorStack(slot.insertStack(itemStack5, n));
                                } else if (itemStack5.getCount() >= slot.getMaxItemCount(itemStack5)) {
                                    slot.setStack(itemStack5);
                                    this.setCursorStack(itemStack);
                                }
                            } else if (ItemStack.canCombine(itemStack, itemStack5)) {
                                Optional<ItemStack> optional2 = slot.tryTakeStackRange(itemStack.getCount(), itemStack5.getMaxCount() - itemStack5.getCount(), player);
                                optional2.ifPresent((stack) -> {
                                    itemStack5.increment(stack.getCount());
                                    slot.onTakeItem(player, stack);
                                });
                            }
                        }
                    }

                    slot.markDirty();
                }
            } else {
                Slot slot3;
                int o;
                if (actionType == SlotActionType.SWAP) {
                    slot3 = (Slot)this.slots.get(slotIndex);
                    itemStack2 = playerInventory.getStack(button);
                    itemStack = slot3.getStack();
                    if (!itemStack2.isEmpty() || !itemStack.isEmpty()) {
                        if (itemStack2.isEmpty()) {
                            if (slot3.canTakeItems(player)) {
                                playerInventory.setStack(button, itemStack);
//                                slot3.onTake(itemStack.getCount());
                                slot3.setStack(ItemStack.EMPTY);
                                slot3.onTakeItem(player, itemStack);
                            }
                        } else if (itemStack.isEmpty()) {
                            if (slot3.canInsert(itemStack2)) {
                                o = slot3.getMaxItemCount(itemStack2);
                                if (itemStack2.getCount() > o) {
                                    slot3.setStack(itemStack2.split(o));
                                } else {
                                    playerInventory.setStack(button, ItemStack.EMPTY);
                                    slot3.setStack(itemStack2);
                                }
                            }
                        } else if (slot3.canTakeItems(player) && slot3.canInsert(itemStack2)) {
                            o = slot3.getMaxItemCount(itemStack2);
                            if (itemStack2.getCount() > o) {
                                slot3.setStack(itemStack2.split(o));
                                slot3.onTakeItem(player, itemStack);
                                if (!playerInventory.insertStack(itemStack)) {
                                    player.dropItem(itemStack, true);
                                }
                            } else {
                                playerInventory.setStack(button, itemStack);
                                slot3.setStack(itemStack2);
                                slot3.onTakeItem(player, itemStack);
                            }
                        }
                    }
                } else if (actionType == SlotActionType.CLONE && player.getAbilities().creativeMode && this.getCursorStack().isEmpty() && slotIndex >= 0) {
                    slot3 = (Slot)this.slots.get(slotIndex);
                    if (slot3.hasStack()) {
                        itemStack2 = slot3.getStack().copy();
                        itemStack2.setCount(itemStack2.getMaxCount());
                        this.setCursorStack(itemStack2);
                    }
                } else if (actionType == SlotActionType.THROW && this.getCursorStack().isEmpty() && slotIndex >= 0) {
                    slot3 = (Slot)this.slots.get(slotIndex);
                    j = button == 0 ? 1 : slot3.getStack().getCount();
                    itemStack = slot3.takeStackRange(j, Integer.MAX_VALUE, player);
                    player.dropItem(itemStack, true);
                } else if (actionType == SlotActionType.PICKUP_ALL && slotIndex >= 0) {
                    slot3 = (Slot)this.slots.get(slotIndex);
                    itemStack2 = this.getCursorStack();
                    if (!itemStack2.isEmpty() && (!slot3.hasStack() || !slot3.canTakeItems(player))) {
                        k = button == 0 ? 0 : this.slots.size() - 1;
                        o = button == 0 ? 1 : -1;

                        for(n = 0; n < 2; ++n) {
                            for(int p = k; p >= 0 && p < this.slots.size() && itemStack2.getCount() < itemStack2.getMaxCount(); p += o) {
                                Slot slot4 = (Slot)this.slots.get(p);
                                if (slot4.hasStack() && canInsertItemIntoSlot(slot4, itemStack2, true) && slot4.canTakeItems(player) && this.canInsertIntoSlot(itemStack2, slot4)) {
                                    ItemStack itemStack6 = slot4.getStack();
                                    if (n != 0 || itemStack6.getCount() != itemStack6.getMaxCount()) {
                                        ItemStack itemStack7 = slot4.takeStackRange(itemStack6.getCount(), itemStack2.getMaxCount() - itemStack2.getCount(), player);
                                        itemStack2.increment(itemStack7.getCount());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    private StackReference getCursorStackReference() {
        return new StackReference() {
            public ItemStack get() {
                return getCursorStack();
            }

            public boolean set(ItemStack stack) {
                setCursorStack(stack);
                return true;
            }
        };
    }*/
    @Override
    public void onContentChanged(Inventory inventory) {
        this.sendContentUpdates();
    }
}