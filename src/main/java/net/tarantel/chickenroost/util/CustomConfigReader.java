package net.tarantel.chickenroost.util;

import com.google.gson.Gson;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;

public class CustomConfigReader {

    private static final Gson GSON = new Gson();
    private static final String FILE =
            FMLPaths.GAMEDIR.get().toString() + "/crlib/seedconfig.json";

    public static RoostConfig load() {

        File file = new File(FILE);

        if (!file.exists()) {
            RoostConfig cfg = new RoostConfig();
            CustomConfigWriter.save(cfg);
            return cfg;
        }

        try (FileReader reader = new FileReader(file)) {
            return GSON.fromJson(reader, RoostConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new RoostConfig();
    }
}
