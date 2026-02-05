package net.tarantel.chickenroost.util;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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

import java.io.Reader;
import java.lang.reflect.Type;
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
    private static final Gson GSON = new Gson();

    private static Path getFile() {
        return Minecraft.getInstance()
                .gameDirectory
                .toPath()
                .resolve("chicken_biomes.json");
    }


    public static synchronized void reloadFromDisk() {
        ENTITY_TO_BIOMES.clear();

        Path file = getFile();
        if (!Files.exists(file)) {
            return;
        }

        try (Reader reader = Files.newBufferedReader(file)) {
            Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
            Map<String, List<String>> data = GSON.fromJson(reader, type);

            if (data != null) {
                ENTITY_TO_BIOMES.putAll(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<String> getBiomes(String entityId) {
        return ENTITY_TO_BIOMES.getOrDefault(entityId, List.of());
    }
}
