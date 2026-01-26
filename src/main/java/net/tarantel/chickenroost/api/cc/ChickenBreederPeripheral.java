package net.tarantel.chickenroost.api.cc;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.BreederTile;
import net.tarantel.chickenroost.util.ModDataComponents;

import javax.annotation.Nullable;

public class ChickenBreederPeripheral implements IPeripheral {

    private final Object tile;


    public ChickenBreederPeripheral(Object tile) {
        this.tile = tile;
    }
    @LuaFunction(mainThread = true)
    public final boolean hasChicken() {
        if (!(tile instanceof BreederTile b)) return false;

        var h = b.getItemHandler();
        if (h == null) return false;

        return !h.getStackInSlot(0).isEmpty()
                || !h.getStackInSlot(2).isEmpty();
    }

    @LuaFunction(mainThread = true)
    public final boolean isWorking() {
        if (tile instanceof BreederTile b) {
            return b.progress > 0;
        }
        return false;
    }


    @Nullable
    private ICollectorTarget collector() {
        return tile instanceof ICollectorTarget c ? c : null;
    }

    @Override
    public String getType() {
        return "chicken_breeder";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return other instanceof ChickenBreederPeripheral p && p.tile == this.tile;
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

    @LuaFunction
    public final boolean supportsCollectorControl() {
        return collector() != null;
    }


    @Nullable
    private IItemHandler handler() {
        if (tile instanceof ICollectorTarget c) {
            return c.getItemHandler();
        }
        return null;
    }
    @LuaFunction(mainThread = true)
    public final int getChickenLevel() {
        var c = collector();
        if (c == null) return 0;

        IItemHandler h = c.getItemHandler();
        if (h == null) return 0;

        int slot = c.getReadSlot();
        if (slot < 0 || slot >= h.getSlots()) return 0;

        ItemStack chicken = h.getStackInSlot(slot);
        if (chicken.isEmpty()) return 0;

        Integer lvl = chicken.get(ModDataComponents.CHICKENLEVEL.value());
        return lvl != null ? lvl : 0;
    }


    @LuaFunction(mainThread = true)
    public final int getChickenXp() {
        var c = collector();
        if (c == null) return 0;

        IItemHandler h = c.getItemHandler();
        if (h == null) return 0;

        int slot = c.getReadSlot();
        if (slot < 0 || slot >= h.getSlots()) return 0;

        ItemStack chicken = h.getStackInSlot(slot);
        if (chicken.isEmpty()) return 0;

        Integer xp = chicken.get(ModDataComponents.CHICKENXP.value());
        return xp != null ? xp : 0;
    }

    @LuaFunction
    public final int getReadSlot() {
        var c = collector();
        return c != null ? c.getReadSlot() + 1 : -1;
    }


    @LuaFunction(mainThread = true)
    public final int getInventorySize() {
        var c = collector();
        if (c == null || c.getItemHandler() == null) return 0;
        return c.getItemHandler().getSlots();
    }

    @LuaFunction(mainThread = true)
    public final int getItemCount(int slot) {
        var c = collector();
        if (c == null || c.getItemHandler() == null) return 0;

        var stack = c.getItemHandler().getStackInSlot(slot - 1);
        return stack.isEmpty() ? 0 : stack.getCount();
    }

    private ItemStack getChickenInSlot(int slot) {
        if (!(tile instanceof BreederTile breeder)) return ItemStack.EMPTY;

        IItemHandler h = breeder.getItemHandler();
        if (h == null) return ItemStack.EMPTY;

        if (slot < 0 || slot >= h.getSlots()) return ItemStack.EMPTY;

        return h.getStackInSlot(slot);
    }
    @LuaFunction(mainThread = true)
    public final int getChickenLevelInSlot(int slot) {
        int internalSlot = slot - 1;

        if (internalSlot != 0 && internalSlot != 2) return 0;

        ItemStack chicken = getChickenInSlot(internalSlot);
        if (chicken.isEmpty()) return 0;

        Integer lvl = chicken.get(ModDataComponents.CHICKENLEVEL.value());
        return lvl != null ? lvl : 0;
    }

    @LuaFunction(mainThread = true)
    public final int getChickenXpInSlot(int slot) {
        int internalSlot = slot - 1;

        if (internalSlot != 0 && internalSlot != 2) return 0;

        ItemStack chicken = getChickenInSlot(internalSlot);
        if (chicken.isEmpty()) return 0;

        Integer xp = chicken.get(ModDataComponents.CHICKENXP.value());
        return xp != null ? xp : 0;
    }
    @LuaFunction
    public final int getLeftChickenLevel() {
        return getChickenLevelInSlot(1);
    }

    @LuaFunction
    public final int getRightChickenLevel() {
        return getChickenLevelInSlot(3);
    }

    @LuaFunction
    public final int getLeftChickenXp() {
        return getChickenXpInSlot(1);
    }

    @LuaFunction
    public final int getRightChickenXp() {
        return getChickenXpInSlot(3);
    }


    @LuaFunction(mainThread = true)
    public int getProgress() {
        if (tile instanceof BreederTile b) {
            return b.progress;
        }
        return 0;
    }

    @LuaFunction(mainThread = true)
    public int getMaxProgress() {
        if (tile instanceof BreederTile b) {
            return b.maxProgress;
        }
        return 0;
    }

    @LuaFunction(mainThread = true)
    public double getProgressPercent() {
        if (tile instanceof BreederTile b && b.maxProgress > 0) {
            return (double) b.progress / (double) b.maxProgress;
        }
        return 0;
    }

}