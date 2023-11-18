package net.tarantel.chickenroost.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ChickenBookHandler extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final Level level;
    public final Player entity;
    public int x, y, z;
   // public final int page;
    public IItemHandler internal;
    public final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound = false;
    public final Container bookContainer;

    public ChickenBookHandler(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(ModHandlers.BOOK.get(), id);
        this.bookContainer = inv;
        this.entity = inv.player;
        this.level = inv.player.level();
        this.internal = new ItemStackHandler(12);
       // this.page = 0;
        BlockPos pos = null;
        if (extraData != null) {
            pos = extraData.readBlockPos();
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
        }



        /*this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, 258, 31) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player playerIn) {
                return false;
            }

            private final int slot = 0;
        }));*/
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }

    public Map<Integer, Slot> get() {
        return customSlots;
    }
}
