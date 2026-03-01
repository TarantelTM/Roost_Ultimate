package net.tarantel.chickenroost.util;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public class WrappedHandler implements IItemHandlerModifiable {
   private final IItemHandlerModifiable handler;
   private final Predicate<Integer> extract;
   private final BiPredicate<Integer, ItemStack> insert;

   public WrappedHandler(IItemHandlerModifiable handler, Predicate<Integer> extract, BiPredicate<Integer, ItemStack> insert) {
      this.handler = handler;
      this.extract = extract;
      this.insert = insert;
   }

   public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
      this.handler.setStackInSlot(slot, stack);
   }

   public int getSlots() {
      return this.handler.getSlots();
   }

   @Nonnull
   public ItemStack getStackInSlot(int slot) {
      return this.handler.getStackInSlot(slot);
   }

   @Nonnull
   public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
      return this.insert.test(slot, stack) ? this.handler.insertItem(slot, stack, simulate) : stack;
   }

   @Nonnull
   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      return this.extract.test(slot) ? this.handler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
   }

   public int getSlotLimit(int slot) {
      return this.handler.getSlotLimit(slot);
   }

   public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
      return this.insert.test(slot, stack) && this.handler.isItemValid(slot, stack);
   }
}
