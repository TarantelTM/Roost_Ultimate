package net.tarantel.chickenroost.item;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

public class SingleUseFluidHandler implements IFluidHandlerItem {
    private ItemStack container;

    public SingleUseFluidHandler(ItemStack stack) {

        this.container = stack.copy();
    }

    private UniversalFluidItem getItem() {
        return (UniversalFluidItem) container.getItem();
    }

    @Override
    public ItemStack getContainer() {
        return container;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        if (tank != 0 || container.isEmpty()) return FluidStack.EMPTY;
        UniversalFluidItem item = getItem();
        return new FluidStack(item.getFluid(), item.getAmountPerItem());
    }

    @Override
    public int getTankCapacity(int tank) {
        if (tank != 0) return 0;
        return getItem().getAmountPerItem();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return tank == 0 && stack.getFluid() == getItem().getFluid();
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {

        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || resource.getFluid() != getItem().getFluid()) {
            return FluidStack.EMPTY;
        }
        return drain(resource.getAmount(), action);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (container.isEmpty() || maxDrain <= 0) return FluidStack.EMPTY;

        UniversalFluidItem item = getItem();
        int amountPerItem = item.getAmountPerItem();


        int toDrain = Math.min(maxDrain, amountPerItem);
        if (toDrain <= 0) return FluidStack.EMPTY;

        FluidStack result = new FluidStack(item.getFluid(), toDrain);

        if (action.execute()) {

            container.shrink(1);
            if (container.getCount() <= 0) {
                container = ItemStack.EMPTY;
            }
        }

        return result;
    }
}
