package net.tarantel.chickenroost.screen;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CustomSlot extends Slot {
    public CustomSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        // Define what items can be placed in this slot
        return false; // Allow any item
    }
    @Override
    public boolean mayPickup(Player p_40228_) {
        return false;
    }

}