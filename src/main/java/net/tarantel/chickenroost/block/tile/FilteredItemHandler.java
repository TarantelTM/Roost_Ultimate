package net.tarantel.chickenroost.block.tile;

import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.function.IntPredicate;

public final class FilteredItemHandler implements ResourceHandler<ItemResource> {

    private final ResourceHandler<ItemResource> parent;
    private final IntPredicate canInsert;
    private final IntPredicate canExtract;

    public FilteredItemHandler(
            ResourceHandler<ItemResource> parent,
            IntPredicate canInsert,
            IntPredicate canExtract
    ) {
        this.parent = parent;
        this.canInsert = canInsert;
        this.canExtract = canExtract;
    }

    @Override public int size() { return parent.size(); }
    @Override public ItemResource getResource(int i) { return parent.getResource(i); }
    @Override public long getAmountAsLong(int i) { return parent.getAmountAsLong(i); }
    @Override public long getCapacityAsLong(int i, ItemResource r) { return parent.getCapacityAsLong(i, r); }
    @Override public boolean isValid(int i, ItemResource r) { return parent.isValid(i, r); }

    @Override
    public int insert(int i, ItemResource r, int amt, TransactionContext tx) {
        return canInsert.test(i) ? parent.insert(i, r, amt, tx) : 0;
    }

    @Override
    public int extract(int i, ItemResource r, int amt, TransactionContext tx) {
        return canExtract.test(i) ? parent.extract(i, r, amt, tx) : 0;
    }
}
