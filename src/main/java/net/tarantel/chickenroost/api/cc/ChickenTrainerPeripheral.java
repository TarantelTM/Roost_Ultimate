package net.tarantel.chickenroost.api.cc;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.TrainerTile;
import net.tarantel.chickenroost.util.ModDataComponents;

import javax.annotation.Nullable;

public class ChickenTrainerPeripheral implements IPeripheral {

    private final Object tile;

    public ChickenTrainerPeripheral(Object tile) {
        this.tile = tile;
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
        if (tile instanceof TrainerTile t) {
            return t.progress > 0;
        }
        return false;
    }

    @Nullable
    private ICollectorTarget collector() {
        return tile instanceof ICollectorTarget c ? c : null;
    }

    @Nullable
    private IItemHandler handler() {
        var c = collector();
        return c != null ? c.getItemHandler() : null;
    }


    @Override
    public String getType() {
        return "chicken_trainer";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return other instanceof ChickenTrainerPeripheral p && p.tile == this.tile;
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
        if (name.length() > 32) name = name.substring(0, 32);

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
    public final int getReadSlot() {
        var c = collector();
        return c != null ? c.getReadSlot() + 1 : -1;
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

    private ItemStack getChickenStack() {
        var c = collector();
        var h = handler();
        if (c == null || h == null) return ItemStack.EMPTY;

        int slot = c.getReadSlot();
        if (slot < 0 || slot >= h.getSlots()) return ItemStack.EMPTY;

        return h.getStackInSlot(slot);
    }


    @LuaFunction(mainThread = true)
    public final int getAutoOutputLevel() {
        if (tile instanceof TrainerTile t) {
            return t.getAutoOutputLevel();
        }
        return 0;
    }

    @LuaFunction(mainThread = true)
    public final void setAutoOutputLevel(int level) {
        if (tile instanceof TrainerTile t) {
            t.setAutoOutputLevel(level);
        }
    }

    @LuaFunction
    public final boolean supportsTrainerControl() {
        return collector() != null && tile instanceof TrainerTile;
    }

    @LuaFunction(mainThread = true)
    public final int getProgress() {
        if (tile instanceof TrainerTile t) {
            return t.progress;
        }
        return 0;
    }

    @LuaFunction(mainThread = true)
    public final int getMaxProgress() {
        if (tile instanceof TrainerTile t) {
            return t.maxProgress;
        }
        return 0;
    }

    @LuaFunction(mainThread = true)
    public final double getProgressPercent() {
        if (tile instanceof TrainerTile t && t.maxProgress > 0) {
            return (double) t.progress / (double) t.maxProgress;
        }
        return 0.0;
    }

}
