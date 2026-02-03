package net.tarantel.chickenroost.util;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import net.neoforged.fml.loading.FMLPaths;

public class CustomConfigReader {
   private static final Gson GSON = new Gson();
   private static final String FILE = FMLPaths.GAMEDIR.get().toString() + "/crlib/seedconfig.json";

   public static RoostConfig load() {
      File file = new File(FILE);
      if (!file.exists()) {
         RoostConfig cfg = new RoostConfig();
         CustomConfigWriter.save(cfg);
         return cfg;
      } else {
         try {
            RoostConfig var2;
            try (FileReader reader = new FileReader(file)) {
               var2 = (RoostConfig)GSON.fromJson(reader, RoostConfig.class);
            }

            return var2;
         } catch (Exception var6) {
            var6.printStackTrace();
            return new RoostConfig();
         }
      }
   }
}
