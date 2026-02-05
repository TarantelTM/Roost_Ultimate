package net.tarantel.chickenroost.spawn;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Set;

public class BiomeMatcher {

    private final Set<ResourceLocation> biomes;

    public BiomeMatcher(Set<ResourceLocation> biomes) {
        this.biomes = biomes;
    }

    public boolean matches(Holder<Biome> biome) {
        return biome.unwrapKey()
                .map(key -> biomes.contains(key.location()))
                .orElse(false);
    }
}