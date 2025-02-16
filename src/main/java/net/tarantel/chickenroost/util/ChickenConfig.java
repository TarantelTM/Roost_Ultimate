package net.tarantel.chickenroost.util;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class ChickenConfig {
    private static final Map<EntityType<?>, ItemStack> DROP_STACKS = new HashMap<>();
    private static final Map<EntityType<?>, Integer> EGG_TIMES = new HashMap<>();

    public static void setDropStack(EntityType<?> type, ItemStack dropStack) {
        if(dropStack.isEmpty()){
            DROP_STACKS.put(type, new ItemStack(Items.EGG));
        }else {
            DROP_STACKS.put(type, dropStack);
        }
    }

    public static void setEggTime(EntityType<?> type, int eggTime) {
        EGG_TIMES.put(type, eggTime);
    }

    public static ItemStack getDropStack(EntityType<?> type) {
        return DROP_STACKS.getOrDefault(type, ItemStack.EMPTY);
    }

    public static int getEggTime(EntityType<?> type) {
        return EGG_TIMES.getOrDefault(type, 6000); // Default to 5 minutes (6000 ticks)
    }
}