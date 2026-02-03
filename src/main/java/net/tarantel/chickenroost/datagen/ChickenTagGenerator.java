package net.tarantel.chickenroost.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import net.neoforged.fml.loading.FMLPaths;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.util.ChickenData;

public class ChickenTagGenerator {
   public static void onServerStarting() {
      List<ChickenData> chickens = ChickenRoostMod.chickens;
      if (chickens != null && !chickens.isEmpty()) {
         Path root = FMLPaths.GAMEDIR.get().resolve("crlib/resources");

         try {
            generateTierTags(chickens, root);
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }
   }

   private static void generateTierTags(List<ChickenData> chickens, Path root) throws IOException {
      Path tagRoot = root.resolve("data/c/tags/item/roost");
      Files.createDirectories(tagRoot);
      JsonObject[] tierTags = new JsonObject[10];

      for (int i = 1; i <= 9; i++) {
         tierTags[i] = new JsonObject();
         tierTags[i].addProperty("replace", false);
         tierTags[i].add("values", new JsonArray());
      }

      JsonObject tiered = new JsonObject();
      tiered.addProperty("replace", false);
      tiered.add("values", new JsonArray());

      for (ChickenData chicken : chickens) {
         int tier = chicken.getTier();
         if (tier >= 1 && tier <= 9) {
            String itemId = "chicken_roost:" + chicken.getId();
            tierTags[tier].getAsJsonArray("values").add(optionalItem(itemId));
            tiered.getAsJsonArray("values").add(optionalItem(itemId));
         }
      }

      for (int i = 1; i <= 9; i++) {
         Files.writeString(tagRoot.resolve("tier" + i + ".json"), ChickenModelGenerator.GSON.toJson(tierTags[i]));
      }

      Files.writeString(tagRoot.resolve("tiered.json"), ChickenModelGenerator.GSON.toJson(tiered));
   }

   private static JsonObject optionalItem(String id) {
      JsonObject obj = new JsonObject();
      obj.addProperty("id", id);
      obj.addProperty("required", false);
      return obj;
   }
}
