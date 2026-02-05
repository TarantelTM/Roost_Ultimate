package net.tarantel.chickenroost.spawn;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.*;

public class BiomeModifierReloadListener extends SimpleJsonResourceReloadListener {
    public static final List<RemoveSpawnRule> REMOVE_RULES = new ArrayList<>();

    public static final List<RuntimeSpawnRule> RULES = new ArrayList<>();

    public BiomeModifierReloadListener() {
        super(new Gson(), "forge/biome_modifier");
    }

    @Override
    protected void apply(
            Map<ResourceLocation, JsonElement> jsons,
            ResourceManager manager,
            ProfilerFiller profiler
    ) {
        RULES.clear();

        for (var entry : jsons.entrySet()) {
            ResourceLocation id = entry.getKey();

            // NUR chicken_roost
            if (!id.getNamespace().equals("chicken_roost")) continue;

            JsonObject json = entry.getValue().getAsJsonObject();

            if (!json.get("type").getAsString().equals("forge:add_spawns")) continue;

            Set<ResourceLocation> biomes = parseBiomes(json.get("biomes"));
            BiomeMatcher matcher = new BiomeMatcher(biomes);

            for (SpawnerEntry spawner : parseSpawners(json.get("spawners"))) {

                // Nur eigene Mobs
                if (!spawner.entityId().getNamespace().equals("chicken_roost")) continue;

                var entity = BuiltInRegistries.ENTITY_TYPE.get(spawner.entityId());
                if (entity == null) continue;

                RULES.add(new RuntimeSpawnRule(
                        matcher,
                        entity,
                        spawner.weight(),
                        spawner.min(),
                        spawner.max()
                ));
            }

            // ---------------- REMOVE SPAWNS ----------------
            if (json.get("type").getAsString().equals("forge:remove_spawns")) {

                //Set<ResourceLocation> biomes = parseBiomes(json.get("biomes"));
                //BiomeMatcher matcher = new BiomeMatcher(biomes);

                Set<ResourceLocation> entities = new HashSet<>();
                json.getAsJsonArray("entity_types")
                        .forEach(e -> entities.add(parseEntityId(e.getAsString())));

                REMOVE_RULES.add(new RemoveSpawnRule(matcher, entities));
            }
        }


    }

    // ---------------- helpers ----------------

    private static Set<ResourceLocation> parseBiomes(JsonElement element) {
        Set<ResourceLocation> result = new HashSet<>();

        if (element.isJsonArray()) {
            element.getAsJsonArray()
                    .forEach(e -> result.add(parseBiomeId(e.getAsString())));
        } else {
            result.add(parseBiomeId(element.getAsString()));
        }

        return result;
    }

    private static ResourceLocation parseBiomeId(String id) {
        if (!id.contains(":")) {
            return new ResourceLocation("minecraft", id);
        }
        return new ResourceLocation(id);
    }

    private static List<SpawnerEntry> parseSpawners(JsonElement element) {
        List<SpawnerEntry> list = new ArrayList<>();

        if (element.isJsonArray()) {
            element.getAsJsonArray()
                    .forEach(e -> list.add(parseSpawner(e.getAsJsonObject())));
        } else {
            list.add(parseSpawner(element.getAsJsonObject()));
        }

        return list;
    }

    private static SpawnerEntry parseSpawner(JsonObject obj) {
        return new SpawnerEntry(
                new ResourceLocation(obj.get("type").getAsString()),
                obj.get("weight").getAsInt(),
                obj.get("minCount").getAsInt(),
                obj.get("maxCount").getAsInt()
        );
    }

    private static ResourceLocation parseEntityId(String id) {
        if (!id.contains(":")) {
            return new ResourceLocation("minecraft", id);
        }
        return new ResourceLocation(id);
    }
}