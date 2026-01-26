package net.tarantel.chickenroost.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.util.ChickenData;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@EventBusSubscriber(modid = "chicken_roost", bus = EventBusSubscriber.Bus.MOD, value = net.neoforged.api.distmarker.Dist.CLIENT)
public class ChickenModelGenerator {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @SubscribeEvent
    public static void register(RegisterColorHandlersEvent.Item event){
        List<ChickenData> chickens = ChickenRoostMod.chickens;

        ChickenModelGenerator.generateModels(chickens);

    }
    public static void generateModels(List<ChickenData> chickens) {

        Path root = FMLPaths.GAMEDIR.get().resolve("crlib/resources");
        Path modelDir = root.resolve("assets/chicken_roost/models/item");
        Path langFile = root.resolve("assets/chicken_roost/lang/en_us.json");
        Path packMeta = root.resolve("pack.mcmeta");

        try {
            if (!Files.exists(packMeta)) {
                Files.writeString(packMeta, """
    {
      "pack": {
        "pack_format": 34,
        "description": {
         "text": "ChickenRoost Generated Resources"
      }
    }
    """);
            }
            Files.createDirectories(modelDir);
            Files.createDirectories(langFile.getParent());

            JsonObject lang = Files.exists(langFile)
                    ? GSON.fromJson(Files.readString(langFile), JsonObject.class)
                    : new JsonObject();

            for (ChickenData chicken : chickens) {


                Files.writeString(
                        modelDir.resolve(chicken.getId() + ".json"),
                        """
                        {
                          "parent": "chicken_roost:item/c_chickenbase"
                        }
                        """
                );


                lang.addProperty(
                        "item.chicken_roost." + chicken.getId(),
                        chicken.getChickenName()
                );
                lang.addProperty(
                        "entity.chicken_roost." + chicken.getId(),
                        chicken.getChickenName()
                );
            }

            Files.writeString(langFile, GSON.toJson(lang));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
