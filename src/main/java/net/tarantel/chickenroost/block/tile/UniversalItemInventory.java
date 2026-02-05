package net.tarantel.chickenroost.block.tile;


import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

/**
 * Universelles Inventar für BlockEntities:
 *
 * - nutzt NeoForge Transfer API (ResourceHandler<ItemResource>)
 * - ist automatisch Hopper-kompatibel
 * - slot-basiert
 * - transaktional
 * - wiederverwendbar
 */
public final class UniversalItemInventory extends ItemStacksResourceHandler {

    @FunctionalInterface
    public interface ChangeListener {
        void onSlotChanged(int slot);
    }

    private final BiPredicate<Integer, ItemResource> canInsert;
    private final IntPredicate canExtract;
    private final IntUnaryOperator slotLimit;
    private final ChangeListener changeListener;

    // ---------- Konstruktoren ----------

    public UniversalItemInventory(
            int size,
            BiPredicate<Integer, ItemResource> canInsert,
            IntPredicate canExtract,
            IntUnaryOperator slotLimit,
            ChangeListener changeListener
    ) {
        super(size);
        this.canInsert = Objects.requireNonNull(canInsert);
        this.canExtract = Objects.requireNonNull(canExtract);
        this.slotLimit = Objects.requireNonNull(slotLimit);
        this.changeListener = changeListener;
    }

    public UniversalItemInventory(
            NonNullList<ItemStack> stacks,
            BiPredicate<Integer, ItemResource> canInsert,
            IntPredicate canExtract,
            IntUnaryOperator slotLimit,
            ChangeListener changeListener
    ) {
        super(stacks);
        this.canInsert = Objects.requireNonNull(canInsert);
        this.canExtract = Objects.requireNonNull(canExtract);
        this.slotLimit = Objects.requireNonNull(slotLimit);
        this.changeListener = changeListener;
    }

    // ---------- Regeln ----------

    @Override
    public boolean isValid(int slot, ItemResource resource) {
        return canInsert.test(slot, resource);
    }

    @Override
    protected int getCapacity(int slot, ItemResource resource) {
        int limit = slotLimit.applyAsInt(slot);
        return resource == null || resource.isEmpty()
                ? limit
                : Math.min(limit, resource.getMaxStackSize());
    }

    @Override
    public int extract(
            int slot,
            ItemResource resource,
            int amount,
            net.neoforged.neoforge.transfer.transaction.TransactionContext tx
    ) {
        if (!canExtract.test(slot)) return 0;
        return super.extract(slot, resource, amount, tx);
    }

    // ---------- Change Hook ----------

    @Override
    protected void onContentsChanged(int slot, ItemStack newStack) {
        if (changeListener != null) {
            changeListener.onSlotChanged(slot);
        }
    }

    // ---------- Direkter Zugriff (GUI / Render / Save) ----------

    public ItemStack getStackDirect(int slot) {
        return stacks.get(slot);
    }

    public void setStackDirect(int slot, ItemStack stack) {
        stacks.set(slot, stack);
        if (changeListener != null) {
            changeListener.onSlotChanged(slot);
        }
    }
}
