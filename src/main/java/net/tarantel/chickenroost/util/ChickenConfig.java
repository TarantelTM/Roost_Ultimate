package net.tarantel.chickenroost.util;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class ChickenConfig {
    private static final Map<EntityType<?>, ItemStack> DROP_STACKS = new HashMap<>();
    private static final Map<EntityType<?>, Integer> EGG_TIMES = new HashMap<>();
    private static final Map<EntityType<?>, Boolean> IS_FIRE = new HashMap<>();
    private static final Map<EntityType<?>, Boolean> IS_PROJECTILE = new HashMap<>();
    private static final Map<EntityType<?>, Boolean> IS_EXPLOSION = new HashMap<>();
    private static final Map<EntityType<?>, Boolean> IS_FALL = new HashMap<>();
    private static final Map<EntityType<?>, Boolean> IS_DROWNING = new HashMap<>();
    private static final Map<EntityType<?>, Boolean> IS_FREEZING = new HashMap<>();
    private static final Map<EntityType<?>, Boolean> IS_LIGHTNING = new HashMap<>();
    private static final Map<EntityType<?>, Boolean> IS_WITHER = new HashMap<>();
    private static final Map<EntityType<?>, Integer> TIER = new HashMap<>();




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
    public static void setIsFire(EntityType<?> type, Boolean source) {
        IS_FIRE.put(type, source);

    }
    public static void setIsProjectile(EntityType<?> type, Boolean source) {
        IS_PROJECTILE.put(type, source);

    }
    public static void setIsExplosion(EntityType<?> type, Boolean source) {
        IS_EXPLOSION.put(type, source);

    }
    public static void setIsFall(EntityType<?> type, Boolean source) {
        IS_FALL.put(type, source);

    }
    public static void setIsDrowning(EntityType<?> type, Boolean source) {
        IS_DROWNING.put(type, source);

    }
    public static void setIsFreezing(EntityType<?> type, Boolean source) {
        IS_FREEZING.put(type, source);

    }
    public static void setIsLightning(EntityType<?> type, Boolean source) {
        IS_LIGHTNING.put(type, source);

    }
    public static void setIsWither(EntityType<?> type, Boolean source) {
        IS_WITHER.put(type, source);

    }

    public static void setTier(EntityType<?> type, Integer tier){
        TIER.put(type, tier);
    }

    public static boolean getIsFire(EntityType<?> type) {
        return IS_FIRE.getOrDefault(type, false);
    }
    public static boolean getIsProjectile(EntityType<?> type) {
        return IS_PROJECTILE.getOrDefault(type, false);
    }
    public static boolean getIsExplosion(EntityType<?> type) {
        return IS_EXPLOSION.getOrDefault(type, false);
    }
    public static boolean getIsFall(EntityType<?> type) {
        return IS_FALL.getOrDefault(type, false);
    }
    public static boolean getIsDrowning(EntityType<?> type) {
        return IS_DROWNING.getOrDefault(type, false);
    }
    public static boolean getIsFreezing(EntityType<?> type) {
        return IS_FREEZING.getOrDefault(type, false);
    }
    public static boolean getIsLightning(EntityType<?> type) {
        return IS_LIGHTNING.getOrDefault(type, false);
    }
    public static boolean getIsWither(EntityType<?> type) {
        return IS_WITHER.getOrDefault(type, false);
    }

    public static Integer getTier(EntityType<?> type) {
        return TIER.getOrDefault(type, 1);
    }

    public static ItemStack getDropStack(EntityType<?> type) {
        return DROP_STACKS.getOrDefault(type, ItemStack.EMPTY);
    }

    public static int getEggTime(EntityType<?> type) {
        return EGG_TIMES.getOrDefault(type, 6000);
    }
}