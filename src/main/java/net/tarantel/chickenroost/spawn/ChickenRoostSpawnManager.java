package net.tarantel.chickenroost.spawn;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.tarantel.chickenroost.entity.BaseChickenEntity;

public class ChickenRoostSpawnManager {

    @SubscribeEvent
    public static void onCheckSpawn(MobSpawnEvent.FinalizeSpawn event) {
        if (!(event.getEntity() instanceof BaseChickenEntity mob)) return;

        Level level = (Level) event.getLevel();
        var biome = level.getBiome(mob.blockPosition());

        for (RuntimeSpawnRule rule : BiomeModifierReloadListener.RULES) {

            if (mob.getType() != rule.entity()) continue;
            if (!rule.biomeMatcher().matches(biome)) continue;

            // Gewicht
            if (level.random.nextInt(100) >= rule.weight()) {
                event.setResult(Event.Result.DENY);
                return;
            }

            // Gruppenspawn
            GroupSpawnHelper.spawnGroup(
                    level,
                    mob,
                    rule.entity(),
                    rule.min(),
                    rule.max()
            );

            event.setResult(Event.Result.ALLOW);
            return;
        }

        for (RemoveSpawnRule rule : BiomeModifierReloadListener.REMOVE_RULES) {
            if (!rule.biomeMatcher().matches(biome)) continue;

            ResourceLocation id = mob.getType().builtInRegistryHolder().key().location();
            if (rule.entities().contains(id)) {
                event.setResult(Event.Result.DENY);
                return;
            }
        }
    }
}