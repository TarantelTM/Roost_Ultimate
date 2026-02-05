package net.tarantel.chickenroost.util;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.*;

public class ServerBiomeCache {

    private static final Map<String, List<String>> ENTITY_TO_BIOMES = new HashMap<>();

    public static void rebuild(ServerLevel level) {
        ENTITY_TO_BIOMES.clear();

        RegistryAccess access = level.registryAccess();

        HolderLookup.RegistryLookup<BiomeModifier> modifiers =
                access.lookupOrThrow(NeoForgeRegistries.Keys.BIOME_MODIFIERS);

        HolderLookup.RegistryLookup<Biome> biomes =
                access.lookupOrThrow(Registries.BIOME);

        modifiers.listElements().forEach(modHolder -> {
            BiomeModifier modifier = modHolder.value();
            if (!(modifier instanceof BiomeModifiers.AddSpawnsBiomeModifier add)) return;

            WeightedList<MobSpawnSettings.SpawnerData> spawners = add.spawners();

            // 1.21.11: WeightedList -> unwrap() -> List<Weighted<SpawnerData>>
            for (Weighted<MobSpawnSettings.SpawnerData> weighted : spawners.unwrap()) {
                MobSpawnSettings.SpawnerData data = weighted.value(); // <- wichtig
                var entityId = BuiltInRegistries.ENTITY_TYPE.getKey(data.type());

                List<String> list = ENTITY_TO_BIOMES.computeIfAbsent(
                        entityId.toString(),
                        k -> new ArrayList<>()
                );

                HolderSet<Biome> biomeSet = add.biomes();

                if (biomeSet instanceof HolderSet.Named<Biome> named) {
                    TagKey<Biome> tag = named.key();
                    HolderSet.Named<Biome> tagged = biomes.getOrThrow(tag);
                    tagged.forEach(h -> addBiomeKeyString(h, list));

                } else if (biomeSet instanceof HolderSet.Direct<Biome> direct) {
                    direct.forEach(h -> addBiomeKeyString(h, list));
                }
            }
        });

        // writeJson(level);
    }

    private static void addBiomeKeyString(Holder<Biome> biomeHolder, List<String> list) {
        biomeHolder.unwrapKey().ifPresent(key -> {
            // Mojang-Mappings: location() -> ResourceLocation
            // Falls deine Mappings hier anders heißen: nimm key.toString() als Fallback.
            try {
                Identifier rl = key.identifier();
                list.add(rl.toString());
            } catch (Throwable t) {
                list.add(key.toString());
            }
        });
    }
}
