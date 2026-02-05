package net.tarantel.chickenroost.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileWriter;

public class CustomConfigWriter {

    private static final Gson GSON =
            new GsonBuilder().setPrettyPrinting().create();

    private static final String DIR =
            FMLPaths.GAMEDIR.get().toString() + "/crlib";

    private static final String FILE = DIR + "/seedconfig.json";

    public static void save(RoostConfig config) {

        new File(DIR).mkdirs();

        try (FileWriter writer = new FileWriter(FILE)) {
            GSON.toJson(config, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}