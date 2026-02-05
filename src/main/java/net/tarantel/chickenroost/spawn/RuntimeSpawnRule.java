package net.tarantel.chickenroost.spawn;

import net.minecraft.world.entity.EntityType;

public record RuntimeSpawnRule(
        BiomeMatcher biomeMatcher,
        EntityType<?> entity,
        int weight,
        int min,
        int max
) {}