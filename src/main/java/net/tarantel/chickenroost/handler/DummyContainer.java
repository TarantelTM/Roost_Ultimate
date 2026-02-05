package net.tarantel.chickenroost.handler;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public final class DummyContainer implements Container {

    private final int size;

    public DummyContainer(int size) {
        this.size = size;
    }

    @Override
    public int getContainerSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        // no-op
    }

    @Override
    public void setChanged() {
        // no-op, aber NICHT null!
    }

    @Override
    public boolean stillValid(net.minecraft.world.entity.player.Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        // no-op
    }
}
