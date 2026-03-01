package net.tarantel.chickenroost.api.cc;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.TrainerTile;
import net.tarantel.chickenroost.util.ModDataComponents;

public class ChickenTrainerPeripheral implements IPeripheral {
   private final Object tile;

   public ChickenTrainerPeripheral(Object tile) {
      this.tile = tile;
   }

   @LuaFunction(mainThread = true)
   public final boolean hasChicken() {
      ICollectorTarget c = this.collector();
      if (c == null) {
         return false;
      } else {
         IItemHandler h = c.getItemHandler();
         if (h == null) {
            return false;
         } else {
            int slot = c.getReadSlot();
            return slot >= 0 && slot < h.getSlots() ? !h.getStackInSlot(slot).isEmpty() : false;
         }
      }
   }

   @LuaFunction(mainThread = true)
   public final boolean isWorking() {
      return this.tile instanceof TrainerTile t ? t.progress > 0 : false;
   }

   @Nullable
   private ICollectorTarget collector() {
      return this.tile instanceof ICollectorTarget c ? c : null;
   }

   @Nullable
   private IItemHandler handler() {
      ICollectorTarget c = this.collector();
      return c != null ? c.getItemHandler() : null;
   }

   public String getType() {
      return "chicken_trainer";
   }

   public boolean equals(@Nullable IPeripheral other) {
      return other instanceof ChickenTrainerPeripheral p && p.tile == this.tile;
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
   public final int getReadSlot() {
      ICollectorTarget c = this.collector();
      return c != null ? c.getReadSlot() + 1 : -1;
   }

   @LuaFunction(mainThread = true)
   public final int getChickenLevel() {
      ItemStack chicken = this.getChickenStack();
      if (chicken.isEmpty()) {
         return 0;
      } else {
         Integer lvl = (Integer)chicken.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value());
         return lvl != null ? lvl : 0;
      }
   }

   @LuaFunction(mainThread = true)
   public final int getChickenXp() {
      ItemStack chicken = this.getChickenStack();
      if (chicken.isEmpty()) {
         return 0;
      } else {
         Integer xp = (Integer)chicken.get((DataComponentType)ModDataComponents.CHICKENXP.value());
         return xp != null ? xp : 0;
      }
   }

   private ItemStack getChickenStack() {
      ICollectorTarget c = this.collector();
      IItemHandler h = this.handler();
      if (c != null && h != null) {
         int slot = c.getReadSlot();
         return slot >= 0 && slot < h.getSlots() ? h.getStackInSlot(slot) : ItemStack.EMPTY;
      } else {
         return ItemStack.EMPTY;
      }
   }

   @LuaFunction(mainThread = true)
   public final int getAutoOutputLevel() {
      return this.tile instanceof TrainerTile t ? t.getAutoOutputLevel() : 0;
   }

   @LuaFunction(mainThread = true)
   public final void setAutoOutputLevel(int level) {
      if (this.tile instanceof TrainerTile t) {
         t.setAutoOutputLevel(level);
      }
   }

   @LuaFunction
   public final boolean supportsTrainerControl() {
      return this.collector() != null && this.tile instanceof TrainerTile;
   }

   @LuaFunction(mainThread = true)
   public final int getProgress() {
      return this.tile instanceof TrainerTile t ? t.progress : 0;
   }

   @LuaFunction(mainThread = true)
   public final int getMaxProgress() {
      return this.tile instanceof TrainerTile t ? t.maxProgress : 0;
   }

   @LuaFunction(mainThread = true)
   public final double getProgressPercent() {
      return this.tile instanceof TrainerTile t && t.maxProgress > 0 ? (double)t.progress / t.maxProgress : 0.0;
   }
}
