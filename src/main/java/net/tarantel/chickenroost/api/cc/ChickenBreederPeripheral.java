package net.tarantel.chickenroost.api.cc;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.BreederTile;
import net.tarantel.chickenroost.util.ModDataComponents;

public class ChickenBreederPeripheral implements IPeripheral {
   private final Object tile;

   public ChickenBreederPeripheral(Object tile) {
      this.tile = tile;
   }

   @LuaFunction(mainThread = true)
   public final boolean hasChicken() {
      if (this.tile instanceof BreederTile b) {
         IItemHandler h = b.getItemHandler();
         return h == null ? false : !h.getStackInSlot(0).isEmpty() || !h.getStackInSlot(2).isEmpty();
      } else {
         return false;
      }
   }

   @LuaFunction(mainThread = true)
   public final boolean isWorking() {
      return this.tile instanceof BreederTile b ? b.progress > 0 : false;
   }

   @Nullable
   private ICollectorTarget collector() {
      return this.tile instanceof ICollectorTarget c ? c : null;
   }

   public String getType() {
      return "chicken_breeder";
   }

   public boolean equals(@Nullable IPeripheral other) {
      return other instanceof ChickenBreederPeripheral p && p.tile == this.tile;
   }

   @LuaFunction(mainThread = true)
   public final String getName() {
      ICollectorTarget c = this.collector();
      return c != null && c.getCustomName() != null ? c.getCustomName() : "";
   }

   @LuaFunction(mainThread = true)
   public final void setName(String name) {
      ICollectorTarget c = this.collector();
      if (c != null) {
         if (name == null) {
            name = "";
         }

         if (name.length() > 32) {
            name = name.substring(0, 32);
         }

         c.setCustomName(name);
      }
   }

   @LuaFunction(mainThread = true)
   public final boolean getAutoOutput() {
      ICollectorTarget c = this.collector();
      return c != null && c.isAutoOutputEnabled();
   }

   @LuaFunction(mainThread = true)
   public final void setAutoOutput(boolean enabled) {
      ICollectorTarget c = this.collector();
      if (c != null) {
         c.setAutoOutputFromGui(enabled);
      }
   }

   @LuaFunction
   public final boolean supportsCollectorControl() {
      return this.collector() != null;
   }

   @Nullable
   private IItemHandler handler() {
      return this.tile instanceof ICollectorTarget c ? c.getItemHandler() : null;
   }

   @LuaFunction(mainThread = true)
   public final int getChickenLevel() {
      ICollectorTarget c = this.collector();
      if (c == null) {
         return 0;
      } else {
         IItemHandler h = c.getItemHandler();
         if (h == null) {
            return 0;
         } else {
            int slot = c.getReadSlot();
            if (slot >= 0 && slot < h.getSlots()) {
               ItemStack chicken = h.getStackInSlot(slot);
               if (chicken.isEmpty()) {
                  return 0;
               } else {
                  Integer lvl = (Integer)chicken.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value());
                  return lvl != null ? lvl : 0;
               }
            } else {
               return 0;
            }
         }
      }
   }

   @LuaFunction(mainThread = true)
   public final int getChickenXp() {
      ICollectorTarget c = this.collector();
      if (c == null) {
         return 0;
      } else {
         IItemHandler h = c.getItemHandler();
         if (h == null) {
            return 0;
         } else {
            int slot = c.getReadSlot();
            if (slot >= 0 && slot < h.getSlots()) {
               ItemStack chicken = h.getStackInSlot(slot);
               if (chicken.isEmpty()) {
                  return 0;
               } else {
                  Integer xp = (Integer)chicken.get((DataComponentType)ModDataComponents.CHICKENXP.value());
                  return xp != null ? xp : 0;
               }
            } else {
               return 0;
            }
         }
      }
   }

   @LuaFunction
   public final int getReadSlot() {
      ICollectorTarget c = this.collector();
      return c != null ? c.getReadSlot() + 1 : -1;
   }

   @LuaFunction(mainThread = true)
   public final int getInventorySize() {
      ICollectorTarget c = this.collector();
      return c != null && c.getItemHandler() != null ? c.getItemHandler().getSlots() : 0;
   }

   @LuaFunction(mainThread = true)
   public final int getItemCount(int slot) {
      ICollectorTarget c = this.collector();
      if (c != null && c.getItemHandler() != null) {
         ItemStack stack = c.getItemHandler().getStackInSlot(slot - 1);
         return stack.isEmpty() ? 0 : stack.getCount();
      } else {
         return 0;
      }
   }

   private ItemStack getChickenInSlot(int slot) {
      if (this.tile instanceof BreederTile breeder) {
         IItemHandler h = breeder.getItemHandler();
         if (h == null) {
            return ItemStack.EMPTY;
         } else {
            return slot >= 0 && slot < h.getSlots() ? h.getStackInSlot(slot) : ItemStack.EMPTY;
         }
      } else {
         return ItemStack.EMPTY;
      }
   }

   @LuaFunction(mainThread = true)
   public final int getChickenLevelInSlot(int slot) {
      int internalSlot = slot - 1;
      if (internalSlot != 0 && internalSlot != 2) {
         return 0;
      } else {
         ItemStack chicken = this.getChickenInSlot(internalSlot);
         if (chicken.isEmpty()) {
            return 0;
         } else {
            Integer lvl = (Integer)chicken.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value());
            return lvl != null ? lvl : 0;
         }
      }
   }

   @LuaFunction(mainThread = true)
   public final int getChickenXpInSlot(int slot) {
      int internalSlot = slot - 1;
      if (internalSlot != 0 && internalSlot != 2) {
         return 0;
      } else {
         ItemStack chicken = this.getChickenInSlot(internalSlot);
         if (chicken.isEmpty()) {
            return 0;
         } else {
            Integer xp = (Integer)chicken.get((DataComponentType)ModDataComponents.CHICKENXP.value());
            return xp != null ? xp : 0;
         }
      }
   }

   @LuaFunction
   public final int getLeftChickenLevel() {
      return this.getChickenLevelInSlot(1);
   }

   @LuaFunction
   public final int getRightChickenLevel() {
      return this.getChickenLevelInSlot(3);
   }

   @LuaFunction
   public final int getLeftChickenXp() {
      return this.getChickenXpInSlot(1);
   }

   @LuaFunction
   public final int getRightChickenXp() {
      return this.getChickenXpInSlot(3);
   }

   @LuaFunction(mainThread = true)
   public int getProgress() {
      return this.tile instanceof BreederTile b ? b.progress : 0;
   }

   @LuaFunction(mainThread = true)
   public int getMaxProgress() {
      return this.tile instanceof BreederTile b ? b.maxProgress : 0;
   }

   @LuaFunction(mainThread = true)
   public double getProgressPercent() {
      return this.tile instanceof BreederTile b && b.maxProgress > 0 ? (double)b.progress / b.maxProgress : 0.0;
   }
}
