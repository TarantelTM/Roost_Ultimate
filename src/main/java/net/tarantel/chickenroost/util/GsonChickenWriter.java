package net.tarantel.chickenroost.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import net.neoforged.fml.loading.FMLPaths;

public class GsonChickenWriter {
   public static void writeItemsToFile(List<ChickenData> items) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String filePath = FMLPaths.GAMEDIR.get().toString() + "/crlib/chicken_config_v4-2-0.json";
      File directory = new File(FMLPaths.GAMEDIR.get().toString() + "/crlib");
      if (!directory.exists()) {
         directory.mkdirs();
      }

      try (FileWriter writer = new FileWriter(filePath)) {
         gson.toJson(items, writer);
         System.out.println("JSON created: " + filePath);
      } catch (IOException var9) {
         var9.printStackTrace();
      }
   }
}
