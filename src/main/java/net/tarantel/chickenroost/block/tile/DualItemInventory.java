package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

/**
 * Ein Inventar, das "nativ" das neue Transfer-System anbietet (ResourceHandler<ItemResource>)
 * und zusätzlich einen Legacy/Vanilla-View als IItemHandler (für Hopper/alte Pipes).
 *
 * Intern nutzt es NeoForges ItemStacksResourceHandler, der Transaction/Snapshot korrekt abwickelt.
 */
public final class DualItemInventory {

    /**
     * Callback für "Inventar hat sich geändert" (typisch: BE.setChanged(); level.sendBlockUpdated(...)).
     */
    @FunctionalInterface
    public interface ChangeListener {
        void onChanged(int slot);
    }

    private final Impl handler;
    private final IItemHandler vanillaView;

    /**
     * @param size          Anzahl Slots
     * @param canInsert     (slot, resource) -> darf rein?
     * @param canExtract    slot -> darf raus?
     * @param slotLimit     slot -> max stacksize pro Slot (z.B. 1 für Input-Slots)
     * @param onChanged     wird nach Commit einer Änderung aufgerufen (slot)
     */
    public DualItemInventory(
            int size,
            BiPredicate<Integer, ItemResource> canInsert,
            IntPredicate canExtract,
            IntUnaryOperator slotLimit,
            @Nullable ChangeListener onChanged
    ) {
        Objects.requireNonNull(canInsert, "canInsert");
        Objects.requireNonNull(canExtract, "canExtract");
        Objects.requireNonNull(slotLimit, "slotLimit");

        this.handler = new Impl(size, canInsert, canExtract, slotLimit, onChanged);
        // NeoForge liefert den Adapter in diese Richtung: ResourceHandler -> IItemHandler
        this.vanillaView = IItemHandler.of(this.handler);
    }

    /**
     * Alternative: vorhandene Stack-Liste (z.B. wenn du NonNullList schon nutzt)
     */
    public DualItemInventory(
            NonNullList<ItemStack> stacks,
            BiPredicate<Integer, ItemResource> canInsert,
            IntPredicate canExtract,
            IntUnaryOperator slotLimit,
            @Nullable ChangeListener onChanged
    ) {
        Objects.requireNonNull(stacks, "stacks");
        Objects.requireNonNull(canInsert, "canInsert");
        Objects.requireNonNull(canExtract, "canExtract");
        Objects.requireNonNull(slotLimit, "slotLimit");

        this.handler = new Impl(stacks, canInsert, canExtract, slotLimit, onChanged);
        this.vanillaView = IItemHandler.of(this.handler);
    }

    /** Neues System (für moderne Transfer-Interaktion) */
    public ResourceHandler<ItemResource> transfer() {
        return handler;
    }

    /** Legacy/Vanilla View (für Hopper/alte Pipes) */
    public IItemHandler vanilla() {
        return vanillaView;
    }

    /** Direkter Zugriff auf interne Stacks (z.B. Render/GUI). NICHT modifizieren ohne setStack(). */
    public ItemStack getStack(int slot) {
        return handler.getStack(slot);
    }

    /** Setzt Stack direkt (z.B. Laden aus NBT). */
    public void setStack(int slot, ItemStack stack) {
        handler.setStack(slot, stack);
    }

    /** Größe */
    public int size() {
        return handler.size();
    }

    // ------------------------------------------------------------
    // Implementation
    // ------------------------------------------------------------

    private static final class Impl extends ItemStacksResourceHandler {

        private final BiPredicate<Integer, ItemResource> canInsert;
        private final IntPredicate canExtract;
        private final IntUnaryOperator slotLimit;
        private final @Nullable ChangeListener onChanged;

        private Impl(
                int size,
                BiPredicate<Integer, ItemResource> canInsert,
                IntPredicate canExtract,
                IntUnaryOperator slotLimit,
                @Nullable ChangeListener onChanged
        ) {
            super(size);
            this.canInsert = canInsert;
            this.canExtract = canExtract;
            this.slotLimit = slotLimit;
            this.onChanged = onChanged;
        }

        private Impl(
                NonNullList<ItemStack> stacks,
                BiPredicate<Integer, ItemResource> canInsert,
                IntPredicate canExtract,
                IntUnaryOperator slotLimit,
                @Nullable ChangeListener onChanged
        ) {
            super(stacks);
            this.canInsert = canInsert;
            this.canExtract = canExtract;
            this.slotLimit = slotLimit;
            this.onChanged = onChanged;
        }

        @Override
        public boolean isValid(int index, ItemResource resource) {
            // insert-regeln (auch für "ist dieses resource ok?"-Checks)
            return canInsert.test(index, resource);
        }

        @Override
        protected int getCapacity(int index, ItemResource resource) {
            // Slotlimit UND max stack size des Items berücksichtigen
            int limit = slotLimit.applyAsInt(index);
            if (resource == null || resource.isEmpty()) {
                // Schätzung für leere Resource
                return limit;
            }
            return Math.min(limit, resource.getMaxStackSize());
        }

        @Override
        public int extract(int index, ItemResource resource, int amount, net.neoforged.neoforge.transfer.transaction.TransactionContext tx) {
            // optionaler Extract-Filter: wenn Slot nicht extrahierbar -> 0
            if (!canExtract.test(index)) return 0;
            return super.extract(index, resource, amount, tx);
        }

        @Override
        protected void onContentsChanged(int index, ItemStack newStack) {
            // Wird von ItemStacksResourceHandler nach "commit" einer Änderung sauber getriggert.
            if (onChanged != null) onChanged.onChanged(index);
        }

        // Convenience (ItemStacksResourceHandler hält intern NonNullList "stacks" geschützt in der Superklasse)
        private ItemStack getStack(int slot) {
            return this.stacks.get(slot);
        }

        private void setStack(int slot, ItemStack stack) {
            this.stacks.set(slot, stack);
            // Direkte Setzung gilt sofort (typisch beim Laden). Falls du das auch syncen willst:
            if (onChanged != null) onChanged.onChanged(slot);
        }
    }
}
