//package net.tarantel.chickenroost.api.cc;
//
//import dan200.computercraft.api.lua.LuaFunction;
//import dan200.computercraft.api.peripheral.IPeripheral;
//import net.tarantel.chickenroost.api.ICollectorTarget;
//import net.tarantel.chickenroost.block.tile.SoulExtractorTile;
//
//import javax.annotation.Nullable;
//
//public class ChickenSoulExtractorPeripheral implements IPeripheral {
//
//    private final Object tile;
//
//    public ChickenSoulExtractorPeripheral(Object tile) {
//        this.tile = tile;
//    }
//    @LuaFunction(mainThread = true)
//    public final boolean hasChicken() {
//        var c = collector();
//        if (c == null) return false;
//
//        var h = c.getItemHandler();
//        if (h == null) return false;
//
//        int slot = c.getReadSlot();
//        if (slot < 0 || slot >= h.getSlots()) return false;
//
//        return !h.getStackInSlot(slot).isEmpty();
//    }
//    @LuaFunction(mainThread = true)
//    public final boolean isWorking() {
//        if (tile instanceof SoulExtractorTile s) {
//            return s.progress > 0;
//        }
//        return false;
//    }
//
//    @Nullable
//    private ICollectorTarget collector() {
//        return tile instanceof ICollectorTarget c ? c : null;
//    }
//
//    @Override
//    public String getType() {
//        return "chicken_soul_extractor";
//    }
//
//    @Override
//    public boolean equals(@Nullable IPeripheral other) {
//        return other instanceof ChickenSoulExtractorPeripheral p && p.tile == this.tile;
//    }
//
//
//
//    @LuaFunction(mainThread = true)
//    public final String getName() {
//        var c = collector();
//        return c != null && c.getCustomName() != null ? c.getCustomName() : "";
//    }
//
//    @LuaFunction(mainThread = true)
//    public final void setName(String name) {
//        var c = collector();
//        if (c == null) return;
//
//        if (name == null) name = "";
//        if (name.length() > 32) {
//            name = name.substring(0, 32);
//        }
//
//        c.setCustomName(name);
//    }
//
//
//
//    @LuaFunction(mainThread = true)
//    public final boolean getAutoOutput() {
//        var c = collector();
//        return c != null && c.isAutoOutputEnabled();
//    }
//
//    @LuaFunction(mainThread = true)
//    public final void setAutoOutput(boolean enabled) {
//        var c = collector();
//        if (c != null) {
//            c.setAutoOutputFromGui(enabled);
//        }
//    }
//
//
//
//    @LuaFunction
//    public final boolean supportsCollectorControl() {
//        return collector() != null;
//    }
//
//    @LuaFunction(mainThread = true)
//    public int getProgress() {
//        if (tile instanceof SoulExtractorTile s) {
//            return s.progress;
//        }
//        return 0;
//    }
//
//    @LuaFunction(mainThread = true)
//    public int getMaxProgress() {
//        if (tile instanceof SoulExtractorTile s) {
//            return s.maxProgress;
//        }
//        return 0;
//    }
//
//    @LuaFunction(mainThread = true)
//    public double getProgressPercent() {
//        if (tile instanceof SoulExtractorTile s && s.maxProgress > 0) {
//            return (double) s.progress / (double) s.maxProgress;
//        }
//        return 0;
//    }
//
//}
//