package net.tarantel.chickenroost.api.cc;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.RoostTile;
import net.tarantel.chickenroost.util.ModDataComponents;

import javax.annotation.Nullable;

public class ChickenRoostPeripheral implements IPeripheral {

    private final RoostTile roost;

    public ChickenRoostPeripheral(RoostTile roost) {
        this.roost = roost;
    }


    @LuaFunction(mainThread = true)
    public final boolean hasChicken() {
        var c = collector();
        if (c == null) return false;

        var h = c.getItemHandler();
        if (h == null) return false;

        int slot = c.getReadSlot();
        if (slot < 0 || slot >= h.getSlots()) return false;

        return !h.getStackInSlot(slot).isEmpty();
    }

    @LuaFunction(mainThread = true)
    public final boolean isWorking() {
        return roost.progress > 0 && roost.progress < roost.maxProgress;
    }

    @Override
    public String getType() {
        return "chicken_roost";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return other instanceof ChickenRoostPeripheral p && p.roost == this.roost;
    }



    @Nullable
    private ICollectorTarget collector() {
        return roost;
    }

    @Nullable
    private IItemHandler handler() {
        var c = collector();
        return c != null ? c.getItemHandler() : null;
    }

    private ItemStack getChickenStack() {
        var c = collector();
        var h = handler();
        if (c == null || h == null) return ItemStack.EMPTY;

        int slot = c.getReadSlot();
        if (slot < 0 || slot >= h.getSlots()) return ItemStack.EMPTY;

        return h.getStackInSlot(slot);
    }



    @LuaFunction(mainThread = true)
    public final String getName() {
        var c = collector();
        return c != null && c.getCustomName() != null ? c.getCustomName() : "";
    }

    @LuaFunction(mainThread = true)
    public final void setName(String name) {
        var c = collector();
        if (c == null) return;

        if (name == null) name = "";
        if (name.length() > 32) {
            name = name.substring(0, 32);
        }

        c.setCustomName(name);
    }



    @LuaFunction(mainThread = true)
    public final boolean getAutoOutput() {
        var c = collector();
        return c != null && c.isAutoOutputEnabled();
    }

    @LuaFunction(mainThread = true)
    public final void setAutoOutput(boolean enabled) {
        var c = collector();
        if (c != null) {
            c.setAutoOutputFromGui(enabled);
        }
    }


    @LuaFunction(mainThread = true)
    public final int getChickenLevel() {
        ItemStack chicken = getChickenStack();
        if (chicken.isEmpty()) return 0;

        Integer lvl = chicken.get(ModDataComponents.CHICKENLEVEL.value());
        return lvl != null ? lvl : 0;
    }

    @LuaFunction(mainThread = true)
    public final int getChickenXp() {
        ItemStack chicken = getChickenStack();
        if (chicken.isEmpty()) return 0;

        Integer xp = chicken.get(ModDataComponents.CHICKENXP.value());
        return xp != null ? xp : 0;
    }



    @LuaFunction
    public final int getReadSlot() {
        var c = collector();
        return c != null ? c.getReadSlot() + 1 : -1;
    }

    @LuaFunction
    public final int getInventorySize() {
        var h = handler();
        return h != null ? h.getSlots() : 0;
    }

    @LuaFunction(mainThread = true)
    public int getProgress() {
        if (roost instanceof RoostTile r) {
            return r.progress;
        }
        return 0;
    }

    @LuaFunction(mainThread = true)
    public int getMaxProgress() {
        if (roost instanceof RoostTile r) {
            return r.maxProgress;
        }
        return 0;
    }

    @LuaFunction(mainThread = true)
    public double getProgressPercent() {
        if (roost instanceof RoostTile r && r.maxProgress > 0) {
            return (double) r.progress / (double) r.maxProgress;
        }
        return 0;
    }

}
