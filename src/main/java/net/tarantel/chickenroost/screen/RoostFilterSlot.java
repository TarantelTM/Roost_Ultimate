package net.tarantel.chickenroost.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.tarantel.chickenroost.block.tile.FeederTile;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import org.jetbrains.annotations.NotNull;

public class RoostFilterSlot extends SlotItemHandler {

    private final BlockPos roostPos;
    private final FeederTile feeder;

    public RoostFilterSlot(
            IItemHandler handler,
            int index,
            int x,
            int y,
            FeederTile feeder,
            BlockPos roostPos
    ) {
        super(handler, index, x, y);
        this.feeder = feeder;
        this.roostPos = roostPos;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.getItem() instanceof ChickenSeedBase;
    }

    @Override
    public void set(@NotNull ItemStack stack) {
        super.set(stack);
        feeder.setPreferredSeed(roostPos,
                stack.isEmpty() ? null : stack.getItem());
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
