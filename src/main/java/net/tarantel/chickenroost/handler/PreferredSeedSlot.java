package net.tarantel.chickenroost.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.block.tile.FeederTile;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PreferredSeedSlot extends Slot {

    private final FeederTile feeder;
    private final Supplier<BlockPos> targetSupplier;

    public PreferredSeedSlot(
            FeederTile feeder,
            Supplier<BlockPos> targetSupplier,
            int x,
            int y
    ) {
        // 🔒 Container ist Dummy – Slot ist Ghost
        super(new SimpleContainer(1), 0, x, y);
        this.feeder = feeder;
        this.targetSupplier = targetSupplier;
    }

    /* ================= SLOT STATE ================= */

    private BlockPos getTarget() {
        return targetSupplier.get();
    }

    @Override
    public boolean isActive() {
        // 🔥 Slot existiert NUR wenn Target vorhanden
        return getTarget() != null;
    }

    @Override
    public boolean isHighlightable() {
        return isActive();
    }

    /* ================= INSERT ================= */

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return isActive() && stack.getItem() instanceof ChickenSeedBase;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    /**
     * 🔥 Ghost-Slot:
     * Item wird NICHT gespeichert, nur gemappt
     */
    @Override
    public void set(@NotNull ItemStack stack) {
        BlockPos pos = getTarget();
        if (pos == null) return;

        feeder.setPreferredSeed(
                pos,
                stack.isEmpty() ? null : stack.getItem()
        );
    }

    /* ================= DISPLAY ================= */

    @Override
    public @NotNull ItemStack getItem() {
        BlockPos pos = getTarget();
        if (pos == null) return ItemStack.EMPTY;

        Item item = feeder.getPreferredSeed(pos);
        return item == null ? ItemStack.EMPTY : new ItemStack(item);
    }

    @Override
    public boolean mayPickup(@NotNull Player player) {
        return true;
    }

    @Override
    public boolean isFake() {
        return true; // 🔥 verhindert Vanilla-Interaktionen
    }
}
