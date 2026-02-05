package net.tarantel.chickenroost.handler;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class TransferSlot extends Slot {

    private final ResourceHandler<ItemResource> inv;
    private final int beSlot;
    private final Predicate<ItemStack> mayPlace;
    private final boolean clientSide;

    private ItemStack clientStack = ItemStack.EMPTY;

    public TransferSlot(
            Level level,
            Container dummy,          // ✅ vom Menu geliefert
            int containerSlotIndex,   // ✅ lokal, fortlaufend
            ResourceHandler<ItemResource> inv,
            int beSlot,
            int x,
            int y,
            Predicate<ItemStack> mayPlace
    ) {
        super(dummy, containerSlotIndex, x, y);
        this.inv = inv;
        this.beSlot = beSlot;
        this.mayPlace = mayPlace;
        this.clientSide = level.isClientSide();
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return mayPlace.test(stack);
    }

    @Override
    public @NotNull ItemStack getItem() {
        if (clientSide) return clientStack;

        ItemResource res = inv.getResource(beSlot);
        int amount = inv.getAmountAsInt(beSlot);
        return res.isEmpty() || amount == 0 ? ItemStack.EMPTY : res.toStack(amount);
    }

    @Override
    public void set(@NotNull ItemStack stack) {
        if (clientSide) {
            clientStack = stack.copy();
            return;
        }

        try (Transaction tx = Transaction.openRoot()) {
            ItemResource cur = inv.getResource(beSlot);
            int existing = inv.getAmountAsInt(beSlot);
            if (!cur.isEmpty() && existing > 0) {
                inv.extract(beSlot, cur, existing, tx);
            }

            if (!stack.isEmpty()) {
                ItemResource res = ItemResource.of(stack);
                inv.insert(beSlot, res, stack.getCount(), tx);
            }

            tx.commit();
        }

        super.setChanged();
    }

    @Override
    public @NotNull ItemStack remove(int amount) {
        if (clientSide) return clientStack.split(amount);

        ItemResource res = inv.getResource(beSlot);
        if (res.isEmpty()) return ItemStack.EMPTY;

        try (Transaction tx = Transaction.openRoot()) {
            int extracted = inv.extract(beSlot, res, amount, tx);
            if (extracted > 0) {
                tx.commit();
                return res.toStack(extracted);
            }
        }

        return ItemStack.EMPTY;
    }
}
