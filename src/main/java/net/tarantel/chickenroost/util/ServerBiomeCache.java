package net.tarantel.chickenroost.util;

import com.google.gson.GsonBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ServerBiomeCache {

    private static final Map<String, List<String>> ENTITY_TO_BIOMES = new HashMap<>();

    public static void rebuild(ServerLevel level) {
        ENTITY_TO_BIOMES.clear();

        RegistryAccess access = level.registryAccess();
        Registry<BiomeModifier> modifiers =
                access.registryOrThrow(ForgeRegistries.Keys.BIOME_MODIFIERS);
        Registry<Biome> biomes =
                access.registryOrThrow(Registries.BIOME);

        for (var entry : modifiers.entrySet()) {
            if (!(entry.getValue() instanceof ForgeBiomeModifiers.AddSpawnsBiomeModifier add)) continue;

            for (MobSpawnSettings.SpawnerData spawner : add.spawners()) {
                ResourceLocation entityId =
                        BuiltInRegistries.ENTITY_TYPE.getKey(spawner.type);

                List<String> list =
                        ENTITY_TO_BIOMES.computeIfAbsent(entityId.toString(), k -> new ArrayList<>());

                HolderSet<Biome> biomeSet = add.biomes();

                if (biomeSet instanceof HolderSet.Named<Biome> named) {
                    TagKey<Biome> tag = named.key();
                    biomes.getTag(tag).ifPresent(tagged ->
                            tagged.forEach(h -> addBiome(biomes, h, list))
                    );
                } else if (biomeSet instanceof HolderSet.Direct<Biome> direct) {
                    direct.forEach(h -> addBiome(biomes, h, list));
                }
            }
        }

        writeJson(level);
    }

    private static void addBiome(Registry<Biome> registry, Holder<Biome> holder, List<String> list) {
        ResourceLocation id = registry.getKey(holder.value());
        if (id != null && !list.contains(id.toString())) {
            list.add(id.toString());
        }
    }

    private static void writeJson(ServerLevel level) {
        try {
            Path file = level.getServer()
                    .getServerDirectory()
                    .toPath()
                    .resolve("chicken_biomes.json");

            try (Writer writer = Files.newBufferedWriter(file)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(ENTITY_TO_BIOMES, writer);
            }

            System.out.println("[ChickenRoost] Biome cache written");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
