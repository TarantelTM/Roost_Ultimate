package net.tarantel.chickenroost.handler;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.tarantel.chickenroost.util.TagManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModChickenSlot extends Slot {

    public ModChickenSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }
    @Override
    public boolean canInsert(@NotNull ItemStack stack) {
        if(stack.isIn(TagManager.Items.BREEDABLE)){

            return true;
        }
        return false;
    }


    @Override
    public int getMaxItemCount(ItemStack stack) {
        return 1;
    }

    public static boolean isChicken(ItemStack stack) {
        return stack.isIn(TagManager.Items.BREEDABLE);
    }


}