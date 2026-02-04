package net.tarantel.chickenroost.api.cc;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import javax.annotation.Nullable;
import net.neoforged.neoforge.items.IItemHandler;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.SoulExtractorTile;

public class ChickenSoulExtractorPeripheral implements IPeripheral {
   private final Object tile;

   public ChickenSoulExtractorPeripheral(Object tile) {
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
      return this.tile instanceof SoulExtractorTile s ? s.progress > 0 : false;
   }

   @Nullable
   private ICollectorTarget collector() {
      return this.tile instanceof ICollectorTarget c ? c : null;
   }

   public String getType() {
      return "chicken_soul_extractor";
   }

   public boolean equals(@Nullable IPeripheral other) {
      return other instanceof ChickenSoulExtractorPeripheral p && p.tile == this.tile;
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

   @LuaFunction(mainThread = true)
   public int getProgress() {
      return this.tile instanceof SoulExtractorTile s ? s.progress : 0;
   }

   @LuaFunction(mainThread = true)
   public int getMaxProgress() {
      return this.tile instanceof SoulExtractorTile s ? s.maxProgress : 0;
   }

   @LuaFunction(mainThread = true)
   public double getProgressPercent() {
      return this.tile instanceof SoulExtractorTile s && s.maxProgress > 0 ? (double)s.progress / s.maxProgress : 0.0;
   }
}
