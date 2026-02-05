package net.tarantel.chickenroost.spawn;

import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public record RemoveSpawnRule(
        BiomeMatcher biomeMatcher,
        Set<ResourceLocation> entities
) {}