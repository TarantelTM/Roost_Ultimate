package net.tarantel.chickenroost.spawn;

import net.minecraft.resources.ResourceLocation;

public record SpawnerEntry(
        ResourceLocation entityId,
        int weight,
        int min,
        int max
) {}