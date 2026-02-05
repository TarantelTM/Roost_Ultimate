package net.tarantel.chickenroost.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@OnlyIn(Dist.CLIENT)
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
