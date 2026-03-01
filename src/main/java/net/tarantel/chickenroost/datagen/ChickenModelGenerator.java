package net.tarantel.chickenroost.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.util.ChickenData;

@EventBusSubscriber(modid = "chicken_roost", bus = Bus.MOD, value = Dist.CLIENT)
public class ChickenModelGenerator {
   public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

   @SubscribeEvent
   public static void register(Item event) {
      List<ChickenData> chickens = ChickenRoostMod.chickens;
      generateModels(chickens);
   }

   public static void generateModels(List<ChickenData> chickens) {
      Path root = FMLPaths.GAMEDIR.get().resolve("crlib/resources");
      Path modelDir = root.resolve("assets/chicken_roost/models/item");
      Path langFile = root.resolve("assets/chicken_roost/lang/en_us.json");
      Path packMeta = root.resolve("pack.mcmeta");

      try {
         if (!Files.exists(packMeta)) {
            Files.writeString(
               packMeta, "{\n  \"pack\": {\n    \"pack_format\": 34,\n    \"description\": {\n     \"text\": \"ChickenRoost Generated Resources\"\n  }\n}\n"
            );
         }

         Files.createDirectories(modelDir);
         Files.createDirectories(langFile.getParent());
         JsonObject lang = Files.exists(langFile) ? (JsonObject)GSON.fromJson(Files.readString(langFile), JsonObject.class) : new JsonObject();

         for (ChickenData chicken : chickens) {
            Files.writeString(modelDir.resolve(chicken.getId() + ".json"), "{\n  \"parent\": \"chicken_roost:item/c_chickenbase\"\n}\n");
            lang.addProperty("item.chicken_roost." + chicken.getId(), chicken.getChickenName());
            lang.addProperty("entity.chicken_roost." + chicken.getId(), chicken.getChickenName());
         }

         Files.writeString(langFile, GSON.toJson(lang));
      } catch (Exception var8) {
         var8.printStackTrace();
      }
   }
}
