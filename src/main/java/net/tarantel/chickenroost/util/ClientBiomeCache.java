package net.tarantel.chickenroost.util;



import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

@Mod(value = "chicken_roost", dist = Dist.CLIENT)
public class ClientBiomeCache {

    private static final Map<String, List<String>> ENTITY_TO_BIOMES = new HashMap<>();
    public static boolean initialized = false;

    private static final Path CACHE_FILE = Minecraft.getInstance().gameDirectory.toPath().resolve("chicken_biomes.json");

    /** Initialisiert den Cache aus der Registry und speichert in JSON */
    public static void initialize(Level level) {
        if (initialized || level == null) return;
        initialized = true;

        ENTITY_TO_BIOMES.clear();

        RegistryAccess registryAccess = level.registryAccess();
        Registry<BiomeModifier> biomeModifierRegistry = registryAccess.registryOrThrow(NeoForgeRegistries.Keys.BIOME_MODIFIERS);
        Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);

        for (var entry : biomeModifierRegistry.entrySet()) {
            BiomeModifier modifier = entry.getValue();
            if (!(modifier instanceof BiomeModifiers.AddSpawnsBiomeModifier(
                    HolderSet<Biome> biomes, List<net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData> spawners
            ))) continue;

            for (var spawner : spawners) {
                ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(spawner.type);

                List<String> biomeList = ENTITY_TO_BIOMES.computeIfAbsent(entityId.toString(), k -> new ArrayList<>());

                if (biomes instanceof HolderSet.Named<Biome> named) {
                    TagKey<Biome> tag = named.key();
                    biomeRegistry.getTag(tag).ifPresent(tagged -> {
                        for (Holder<Biome> holder : tagged) {
                            ResourceLocation loc = biomeRegistry.getKey(holder.value());
                            if (loc != null && !biomeList.contains(loc.toString())) biomeList.add(loc.toString());
                        }
                    });
                } else if (biomes instanceof HolderSet.Direct<Biome> direct) {
                    for (Holder<Biome> holder : direct) {
                        ResourceLocation loc = biomeRegistry.getKey(holder.value());
                        if (loc != null && !biomeList.contains(loc.toString())) biomeList.add(loc.toString());
                    }
                }
            }
        }

        saveToJson();
    }

    /** Speichert die aktuelle Map in eine JSON im Spielordner */
    public static void saveToJson() {
        try (Writer writer = Files.newBufferedWriter(CACHE_FILE)) {
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(ENTITY_TO_BIOMES, writer);
            System.out.println("[GlobalBiomeCache] Cache gespeichert: " + CACHE_FILE);
        } catch (Exception e) {
            System.err.println("[GlobalBiomeCache] Fehler beim Speichern!");
            e.printStackTrace();
        }
    }

    /** Gibt Biomes für ein EntityId zurück */
    public static List<String> getBiomes(String entityId) {
        return ENTITY_TO_BIOMES.getOrDefault(entityId, List.of());
    }
}
