package net.tarantel.chickenroost.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsonChickenReader {
   private static final Logger LOGGER = LoggerFactory.getLogger("ChickenRoost");
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final String CONFIG_PATH = "/crlib/chicken_config_v4-2-0.json";
   private static final String CUSTOM_DIR = "/crlib/custom/";
   private static List<ChickenData> CACHE = null;

   public static synchronized List<ChickenData> readItemsFromFile() {
      if (CACHE != null) {
         return CACHE;
      } else {
         List<ChickenData> defaults = new ArrayList<>();
         addDefaultChickens(defaults);
         File file = new File(FMLPaths.GAMEDIR.get() + "/crlib/chicken_config_v4-2-0.json");
         if (!file.exists()) {
            GsonChickenWriter.writeItemsToFile(defaults);
            mergeCustomChickens(defaults);
            CACHE = defaults;
            return CACHE;
         } else {
            List<ChickenData> json;
            try (FileReader reader = new FileReader(file)) {
               Type type = (new TypeToken<List<ChickenData>>() {}).getType();
               json = (List<ChickenData>)GSON.fromJson(reader, type);
            } catch (Exception var9) {
               CACHE = defaults;
               return CACHE;
            }

            if (json == null) {
               CACHE = defaults;
               return CACHE;
            } else {
               Map<String, ChickenData> merged = new LinkedHashMap<>();

               for (ChickenData def : defaults) {
                  merged.put(def.getId(), def);
               }

               for (ChickenData cfg : json) {
                  ChickenData base = merged.get(cfg.getId());
                  if (base != null) {
                     cfg.setId(base.getId());
                     merged.put(base.getId(), cfg);
                  } else {
                     merged.put(cfg.getId(), cfg);
                  }
               }

               CACHE = new ArrayList<>(merged.values());
               mergeCustomChickens(CACHE);
               return CACHE;
            }
         }
      }
   }

   private static void mergeCustomChickens(List<ChickenData> target) {
      List<ChickenData> custom = readCustomDirectory();
      if (custom.isEmpty()) {
         return;
      }
      Map<String, ChickenData> existing = new LinkedHashMap<>();
      for (ChickenData c : target) {
         existing.put(c.getId(), c);
      }
      int added = 0;
      for (ChickenData c : custom) {
         if (!existing.containsKey(c.getId())) {
            target.add(c);
            existing.put(c.getId(), c);
            added++;
         } else {
            LOGGER.warn("[ChickenRoost] Custom chicken '{}' skipped — already exists in config", c.getId());
         }
      }
      if (added > 0) {
         LOGGER.info("[ChickenRoost] Loaded {} custom chicken(s) from crlib/custom/", added);
      }
   }

   private static List<ChickenData> readCustomDirectory() {
      File customDir = new File(FMLPaths.GAMEDIR.get() + CUSTOM_DIR);
      if (!customDir.exists()) {
         customDir.mkdirs();
      }
      writeExampleFile(customDir);
      File[] files = customDir.listFiles((dir, name) -> name.endsWith(".json") && !name.startsWith("_"));
      if (files == null || files.length == 0) {
         return Collections.emptyList();
      }
      List<ChickenData> result = new ArrayList<>();
      Type type = (new TypeToken<List<ChickenData>>() {}).getType();
      for (File f : files) {
         try (FileReader reader = new FileReader(f)) {
            List<ChickenData> entries = GSON.fromJson(reader, type);
            if (entries == null) continue;
            for (ChickenData entry : entries) {
               if (entry.validateAndApplyDefaults()) {
                  result.add(entry);
                  LOGGER.debug("[ChickenRoost] Custom chicken '{}' ({}) loaded from {}", entry.getId(), entry.getChickenName(), f.getName());
               } else {
                  LOGGER.warn("[ChickenRoost] Invalid custom chicken entry in {} — missing 'id' or 'dropitem'", f.getName());
               }
            }
         } catch (Exception e) {
            LOGGER.error("[ChickenRoost] Failed to parse custom chicken file: {}", f.getName(), e);
         }
      }
      return result;
   }

   private static void writeExampleFile(File customDir) {
      File example = new File(customDir, "_example_chickens.json");
      if (example.exists()) {
         return;
      }
      String content = "[\n"
         + "  { \"id\": \"c_neutron\", \"tier\": 9, \"dropitem\": \"avaritia:neutron_pile\" },\n"
         + "  {\n"
         + "    \"ChickenName\": \"Antimatter Chicken\",\n"
         + "    \"MobOrMonster\": \"Monster\",\n"
         + "    \"id\": \"c_antimatter\",\n"
         + "    \"itemtexture\": \"whitechicken\",\n"
         + "    \"mobtexture\": \"whitechicken\",\n"
         + "    \"dropitem\": \"mekanism:pellet_antimatter\",\n"
         + "    \"eggtime\": 600,\n"
         + "    \"tier\": 9\n"
         + "  }\n"
         + "]";
      try (FileWriter writer = new FileWriter(example)) {
         writer.write(content);
      } catch (IOException e) {
         LOGGER.error("[ChickenRoost] Failed to write example file: {}", example.getAbsolutePath(), e);
      }
   }

   private static void addDefaultChickens(List<ChickenData> items) {
      items.add(
         new ChickenData(
            "Soul Sand Chicken",
            "Monster",
            "c_soulsand",
            "soulsandchicken",
            "soulariumchicken",
            "minecraft:soul_sand",
            600,
            1,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Ink Chicken", "Mob", "c_ink", "blackchicken", "blackchicken", "minecraft:ink_sac", 600, 1, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Bone Meal Chicken",
            "Mob",
            "c_bonemeal",
            "whitechicken",
            "whitechicken",
            "minecraft:bone_meal",
            600,
            1,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Bone Chicken", "Mob", "c_bone", "whitechicken", "whitechicken", "minecraft:bone", 600, 1, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Flint Chicken", "Mob", "c_flint", "flintchicken", "flintchicken", "minecraft:flint", 600, 1, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Red Chicken", "Mob", "c_red", "redchicken", "redchicken", "minecraft:red_dye", 600, 1, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Yellow Chicken",
            "Mob",
            "c_yellow",
            "yellowchicken",
            "yellowchicken",
            "minecraft:yellow_dye",
            600,
            1,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "White Chicken", "Mob", "c_white", "whitechicken", "whitechicken", "minecraft:white_dye", 600, 1, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Green Chicken", "Mob", "c_green", "greenchicken", "greenchicken", "minecraft:green_dye", 600, 1, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Black Chicken", "Mob", "c_black", "blackchicken", "blackchicken", "minecraft:black_dye", 600, 1, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Cobblestone Chicken",
            "Mob",
            "c_cobble",
            "graychicken",
            "cobblestone",
            "chicken_roost:stone_essence",
            600,
            1,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Sand Chicken", "Mob", "c_sand", "sandchicken", "sandchicken", "minecraft:sand", 600, 1, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Gravel Chicken", "Mob", "c_gravel", "graychicken", "graychicken", "minecraft:gravel", 600, 1, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Granite Chicken",
            "Mob",
            "c_granit",
            "garnetchicken",
            "graychicken",
            "chicken_roost:stone_essence",
            600,
            1,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Andesite Chicken",
            "Mob",
            "c_andesite",
            "blackchicken",
            "blackchicken",
            "chicken_roost:stone_essence",
            600,
            1,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Birch Chicken",
            "Mob",
            "c_birchwood",
            "whitechicken",
            "whitechicken",
            "chicken_roost:wood_essence",
            600,
            1,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Oak Chicken",
            "Mob",
            "c_oakwood",
            "brownchicken",
            "brownchicken",
            "chicken_roost:wood_essence",
            600,
            1,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Queen Slime Chicken",
            "Mob",
            "c_queenslime",
            "yellowgarnetchicken",
            "yellowgarnetchicken",
            "minecraft:stone",
            600,
            1,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Feather Chicken", "Mob", "c_feather", "ghastchicken", "ghastchicken", "minecraft:feather", 600, 1, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Nether Brick Chicken",
            "Monster",
            "c_netherbrick",
            "brownchicken",
            "brownchicken",
            "minecraft:stone",
            600,
            1,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Dark Oak Chicken",
            "Mob",
            "c_darkoak",
            "brownchicken",
            "brownchicken",
            "chicken_roost:wood_essence",
            600,
            1,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Acacia Chicken",
            "Mob",
            "c_acaciawood",
            "brownchicken",
            "brownchicken",
            "chicken_roost:wood_essence",
            600,
            1,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Jungle Chicken",
            "Mob",
            "c_junglewood",
            "pulsatingironchicken",
            "pulsatingironchicken",
            "chicken_roost:wood_essence",
            600,
            1,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Nautilus Chicken",
            "Mob",
            "c_nautilusshell",
            "conductiveironchicken",
            "conductiveironchicken",
            "minecraft:stone",
            600,
            1,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Honey Chicken",
            "Mob",
            "c_honeycomb",
            "yellowchicken",
            "yellowchicken",
            "minecraft:honeycomb",
            600,
            1,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Diorite Chicken",
            "Mob",
            "c_diorite",
            "iridiumchicken",
            "iridiumchicken",
            "chicken_roost:stone_essence",
            600,
            1,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Stone Chicken",
            "Mob",
            "c_stone",
            "stoneburntchicken",
            "stoneburntchicken",
            "chicken_roost:stone_essence",
            600,
            1,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Wool Chicken",
            "Mob",
            "c_wool",
            "enoricrystalchicken",
            "enoricrystalchicken",
            "minecraft:white_wool",
            600,
            1,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Spruce Chicken",
            "Mob",
            "c_sprucewood",
            "brownchicken",
            "brownchicken",
            "chicken_roost:wood_essence",
            600,
            1,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Quartz Chicken",
            "Monster",
            "c_quartz",
            "quartzchicken",
            "quartzchicken",
            "minecraft:quartz",
            600,
            1,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Blue Chicken", "Mob", "c_blue", "bluechicken", "bluechicken", "minecraft:blue_dye", 600, 1, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Vanilla Chicken", "Mob", "c_vanilla", "saltchicken", "saltchicken", "minecraft:egg", 600, 1, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Tuff Chicken", "Mob", "c_tuff", "graychicken", "cobblestone", "minecraft:tuff", 600, 1, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Glowstone Chicken",
            "Monster",
            "c_glowstone",
            "glowstonechicken",
            "glowstonechicken",
            "minecraft:glowstone_dust",
            600,
            2,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Iron Chicken", "Mob", "c_iron", "ironchicken", "ironchicken", "minecraft:iron_ingot", 600, 2, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Redstone Chicken", "Mob", "c_redstone", "redchicken", "redchicken", "minecraft:redstone", 600, 2, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Lapis Chicken",
            "Mob",
            "c_lapis",
            "lightbluechicken",
            "lightbluechicken",
            "minecraft:lapis_lazuli",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "String Chicken",
            "Monster",
            "c_string",
            "destructioncorechicken",
            "destructioncorechicken",
            "minecraft:string",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Orange Chicken",
            "Mob",
            "c_orange",
            "orangechicken",
            "orangechicken",
            "minecraft:orange_dye",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Light Blue Chicken",
            "Mob",
            "c_light_blue",
            "lightbluechicken",
            "lightbluechicken",
            "minecraft:light_blue_dye",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Lime Chicken", "Mob", "c_lime", "limechicken", "limechicken", "minecraft:lime_dye", 600, 2, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Pink Chicken", "Mob", "c_pink", "pinkchicken", "pinkchicken", "minecraft:pink_dye", 600, 2, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Gray Chicken", "Mob", "c_gray", "graychicken", "graychicken", "minecraft:gray_dye", 600, 2, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Cyan Chicken", "Mob", "c_cyan", "cyanchicken", "cyanchicken", "minecraft:cyan_dye", 600, 2, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Purple Chicken",
            "Mob",
            "c_purple",
            "purplechicken",
            "purplechicken",
            "minecraft:purple_dye",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Brown Chicken", "Mob", "c_brown", "brownchicken", "brownchicken", "minecraft:brown_dye", 600, 2, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Crimson Chicken",
            "Monster",
            "c_crimstonstem",
            "brownchicken",
            "brownchicken",
            "minecraft:crimson_stem",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Warped Chicken",
            "Monster",
            "c_warpedstem",
            "brownchicken",
            "brownchicken",
            "minecraft:warped_stem",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Netherrack Chicken",
            "Monster",
            "c_netherrack",
            "netherwartchicken",
            "netherwartchicken",
            "minecraft:netherrack",
            600,
            2,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Paper Chicken", "Mob", "c_paper", "platinumchicken", "platinumchicken", "minecraft:paper", 600, 2, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Sugar Chicken",
            "Mob",
            "c_sugar",
            "enoricrystalchicken",
            "enoricrystalchicken",
            "minecraft:sugar_cane",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Coal Chicken", "Mob", "c_coal", "coalchicken", "coalchicken", "minecraft:coal", 600, 2, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Char Coal Chicken",
            "Mob",
            "c_charcoal",
            "coalchicken",
            "coalchicken",
            "minecraft:charcoal",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Snow Chicken",
            "Mob",
            "c_snow",
            "snowballchicken",
            "snowballchicken",
            "minecraft:snowball",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Apple Chicken",
            "Mob",
            "c_apple",
            "restoniacrystalchicken",
            "restoniacrystalchicken",
            "minecraft:apple",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Melon Chicken", "Mob", "c_melon", "greenchicken", "greenchicken", "minecraft:melon_slice", 600, 2, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Glow Berries Chicken",
            "Mob",
            "c_glowberries",
            "glowstonechicken",
            "glowstonechicken",
            "minecraft:glow_berries",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Sweet Berries Chicken",
            "Mob",
            "c_sweetberries",
            "redstonecrystalchicken",
            "redstonecrystalchicken",
            "minecraft:sweet_berries",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Beetroot Chicken",
            "Mob",
            "c_beetroot",
            "redstonealloychicken",
            "redstonealloychicken",
            "minecraft:beetroot",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Carrot Chicken",
            "Mob",
            "c_carrot",
            "energeticalloychicken",
            "energeticalloychicken",
            "minecraft:carrot",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Gunpowder Chicken",
            "Mob",
            "c_tnt",
            "gunpowderchicken",
            "gunpowderchicken",
            "minecraft:gunpowder",
            600,
            2,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Nickel Chicken",
            "Mob",
            "c_nickel",
            "nickelchicken",
            "nickelchicken",
            "chicken_roost:ingot_nickel",
            600,
            2,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Black Quartz Chicken",
            "Mob",
            "c_blackquartz",
            "blackquartzchicken",
            "blackquartzchicken",
            "actuallyadditions:black_quartz",
            600,
            2,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Lava Chicken", "Mob", "c_lava", "lavachicken", "lavachicken", "chicken_roost:lava_egg", 600, 2, false, true, true, false, true, true, false, true
         )
      );
      items.add(
         new ChickenData(
            "Water Chicken",
            "Mob",
            "c_water",
            "waterchicken",
            "waterchicken",
            "chicken_roost:water_egg",
            600,
            2,
            false,
            true,
            true,
            false,
            false,
            true,
            false,
            true
         )
      );
      items.add(
         new ChickenData(
            "Glass Chicken", "Mob", "c_glass", "glasschicken", "glasschicken", "minecraft:glass", 600, 3, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Magenta Chicken",
            "Mob",
            "c_magenta",
            "magentachicken",
            "magentachicken",
            "minecraft:magenta_dye",
            600,
            3,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Light Gray Chicken",
            "Mob",
            "c_light_gray",
            "graychicken",
            "graychicken",
            "minecraft:light_gray_dye",
            600,
            3,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Tinted Glass Chicken",
            "Mob",
            "c_tintedglass",
            "glasschicken",
            "glasschicken",
            "minecraft:stone",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Copper Chicken",
            "Mob",
            "c_copper",
            "copperchicken",
            "copperchicken",
            "minecraft:copper_ingot",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Living Rock Chicken",
            "Mob",
            "c_livingrock",
            "moonstonechicken",
            "moonstonechicken",
            "botania:livingrock",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Living Wood Chicken",
            "Mob",
            "c_livingwood",
            "magicalwoodchicken",
            "magicalwoodchicken",
            "botania:livingwood",
            600,
            3,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Soul Soil Chicken",
            "Monster",
            "c_soulsoil",
            "soulariumchicken",
            "soulariumchicken",
            "minecraft:soul_soil",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Basalt Chicken",
            "Monster",
            "c_basalt",
            "basalzrodchicken",
            "basalzrodchicken",
            "minecraft:basalt",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Clay Chicken", "Mob", "c_clay", "claychicken", "claychicken", "minecraft:clay_ball", 600, 3, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Rabbit Chicken",
            "Mob",
            "c_rabbithide",
            "bronzechicken",
            "bronzechicken",
            "minecraft:rabbit_hide",
            600,
            3,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Leather Chicken",
            "Mob",
            "c_leather",
            "bronzechicken",
            "bronzechicken",
            "minecraft:leather",
            600,
            3,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Sponge Chicken", "Mob", "c_sponge", "brasschicken", "brasschicken", "minecraft:sponge", 600, 3, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Lead Chicken", "Mob", "c_lead", "leadchicken", "leadchicken", "chicken_roost:ingot_lead", 600, 3, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Nether Wart Chicken",
            "Monster",
            "c_netherwart",
            "netherwartchicken",
            "netherwartchicken",
            "minecraft:nether_wart",
            600,
            3,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Spider Eye Chicken",
            "Monster",
            "c_spidereye",
            "redstonealloychicken",
            "redstonealloychicken",
            "minecraft:spider_eye",
            600,
            3,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Aluminium Chicken",
            "Mob",
            "c_aluminium",
            "aluminumchicken",
            "aluminiumchicken",
            "chicken_roost:ingot_aluminum",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Sulfur Chicken", "Mob", "c_sulfur", "sulfurchicken", "sulfurchicken", "thermal:sulfur", 600, 3, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Quartz Enriched Iron Chicken",
            "Mob",
            "c_quartzenrichediron",
            "quartzenrichedironchicken",
            "quartzenrichedironchicken",
            "refinedstorage:quartz_enriched_iron",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Osmium Chicken",
            "Mob",
            "c_osmium",
            "osmiumchicken",
            "osmiumchicken",
            "mekanism:ingot_osmium",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Gold Chicken", "Mob", "c_gold", "goldchicken", "goldchicken", "minecraft:gold_ingot", 600, 3, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Silver Chicken",
            "Mob",
            "c_silver",
            "silverorechicken",
            "silverchicken",
            "chicken_roost:ingot_silver",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Steel Chicken",
            "Mob",
            "c_steel",
            "steelchicken",
            "steelchicken",
            "chicken_roost:ingot_steel",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Invar Chicken",
            "Mob",
            "c_invar",
            "invarchicken",
            "invarchicken",
            "chicken_roost:ingot_invar",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Graphite Chicken",
            "Mob",
            "c_graphite",
            "graphitechicken",
            "graphitechicken",
            "immersiveengineering:ingot_hop_graphite",
            600,
            3,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Rubber Chicken", "Mob", "c_rubber", "rubberchicken", "rubberchicken", "thermal:rubber", 600, 3, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Restonia Crystal Chicken",
            "Mob",
            "c_restoniacrystal",
            "restoniacrystalchicken",
            "restoniacrystalchicken",
            "actuallyadditions:restonia_crystal",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData("Slag Chicken", "Mob", "c_slag", "slagchicken", "slagchicken", "thermal:slag", 600, 3, true, true, true, false, true, true, true, true)
      );
      items.add(
         new ChickenData(
            "Palis Crystal Chicken",
            "Mob",
            "c_paliscrystal",
            "paliscrystalchicken",
            "paliscrystalchicken",
            "actuallyadditions:palis_crystal",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Conductive Iron Chicken",
            "Mob",
            "c_conductiveiron",
            "conductiveironchicken",
            "conductiveironchicken",
            "enderio:conductive_alloy_ingot",
            600,
            3,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Platinum Chicken",
            "Mob",
            "c_platinum",
            "platinumchicken",
            "platinumchicken",
            "chicken_roost:ingot_platinum",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Obsidian Chicken",
            "Monster",
            "c_obsidian",
            "obsidianchicken",
            "obsidianchicken",
            "minecraft:obsidian",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Slime Chicken", "Mob", "c_slime", "slimechicken", "slimechicken", "minecraft:slime_ball", 600, 4, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Tin Chicken",
            "Mob",
            "c_tin",
            "quartzenrichedironchicken",
            "quartzenrichedironchicken",
            "chicken_roost:ingot_tin",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Rotten Chicken",
            "Monster",
            "c_rotten",
            "richslagchicken",
            "richslagchicken",
            "minecraft:rotten_flesh",
            600,
            4,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Zinc Chicken", "Mob", "c_zinc", "zincchicken", "zincchicken", "chicken_roost:ingot_zinc", 600, 4, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Niter Chicken", "Mob", "c_niter", "vinteumchicken", "vinteumchicken", "thermal:niter", 600, 4, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Coal Coke Chicken", "Mob", "c_coke", "coalchicken", "coalchicken", "thermal:coal_coke", 600, 4, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Tar Chicken", "Mob", "c_tar", "blackquartzchicken", "blackquartzchicken", "thermal:tar", 600, 4, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Apatite Chicken",
            "Mob",
            "c_apatite",
            "sapphirechicken",
            "sapphirechicken",
            "thermal:apatite",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Bitumen Chicken", "Mob", "c_bitumen", "coalchicken", "coalchicken", "thermal:bitumen", 600, 4, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Constantan Chicken",
            "Mob",
            "c_constantan",
            "constantanchicken",
            "constantanchicken",
            "thermal:constantan_nugget",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Blaze Rod Chicken",
            "Monster",
            "c_blazerod",
            "blazechicken",
            "blazechicken",
            "minecraft:blaze_rod",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Blaze Powder Chicken",
            "Monster",
            "c_blazepowder",
            "blazechicken",
            "blazechicken",
            "minecraft:blaze_powder",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Signalum Chicken",
            "Mob",
            "c_signalum",
            "signalumchicken",
            "signalumchicken",
            "chicken_roost:ingot_signalum",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Silicon Chicken", "Mob", "c_silicon", "siliconchicken", "siliconchicken", "ae2:silicon", 600, 4, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Electrum Chicken",
            "Mob",
            "c_electrum",
            "electrumchicken",
            "electrumchicken",
            "chicken_roost:ingot_electrum",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Diamond Chicken",
            "Mob",
            "c_diamond",
            "diamondchicken",
            "diamondchicken",
            "minecraft:diamond",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Amber Chicken", "Mob", "c_amber", "amberchicken", "amberchicken", "cyclic:gem_amber", 600, 4, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Energetic Alloy Chicken",
            "Mob",
            "c_energeticalloy",
            "energeticalloychicken",
            "energeticalloychicken",
            "enderio:energetic_alloy_ingot",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Saltpeter Chicken",
            "Mob",
            "c_saltpeter",
            "saltpeterchicken",
            "saltpeterchicken",
            "gtceu:raw_saltpeter",
            600,
            4,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Soularium Chicken",
            "Mob",
            "c_soularium",
            "soulariumchicken",
            "soulariumchicken",
            "enderio:soularium_ingot",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Rich Slag Chicken",
            "Mob",
            "c_richslag",
            "richslagchicken",
            "richslagchicken",
            "thermal:rich_slag",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Salt Chicken", "Mob", "c_salt", "saltchicken", "saltchicken", "mekanism:salt", 600, 4, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Void Crystal Chicken",
            "Mob",
            "c_voidcrystal",
            "voidcrystalchicken",
            "voidcrystalchicken",
            "actuallyadditions:void_crystal",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Magma Slime Chicken",
            "Mob",
            "c_magmaslime",
            "magmaslime",
            "magmaslime",
            "minecraft:stone",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Prismarine Shard Chicken",
            "Mob",
            "c_prismarineshard",
            "pshardchicken",
            "pshard_chicken",
            "minecraft:prismarine_shard",
            600,
            4,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Magma Cream Chicken",
            "Monster",
            "c_magmacream",
            "magmachicken",
            "magmachicken",
            "minecraft:magma_cream",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Cinnabar Chicken",
            "Mob",
            "c_cinnabar",
            "cinnabarchicken",
            "cinnabarchicken",
            "thermal:cinnabar",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Basalz Chicken",
            "Mob",
            "c_basalz",
            "basalzrodchicken",
            "basalzrodchicken",
            "thermal:basalz_powder",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Blood Chicken",
            "Monster",
            "c_blood",
            "bloodchicken",
            "bloodchicken",
            "bloodmagic:weakbloodshard",
            600,
            5,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Lumium Chicken",
            "Mob",
            "c_lumium",
            "lumiumchicken",
            "lumiumchicken",
            "chicken_roost:ingot_lumium",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Chorus Fruit Chicken",
            "Monster",
            "c_chorusfruit",
            "magentachicken",
            "magentachicken",
            "minecraft:chorus_fruit",
            600,
            5,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Chrome Chicken",
            "Mob",
            "c_chrome",
            "chromechicken",
            "chromechicken",
            "chicken_roost:ingot_chrome",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Ender Pearl Chicken",
            "Monster",
            "c_enderpearl",
            "enderchicken",
            "enderchicken",
            "minecraft:ender_pearl",
            600,
            5,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Endstone Chicken",
            "Monster",
            "c_endstone",
            "bulletchicken",
            "bulletchicken",
            "minecraft:end_stone",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Refined Iron Chicken",
            "Mob",
            "c_refinediron",
            "refinedironchicken",
            "refinedironchicken",
            "techreborn:refined_iron_ingot",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Bronze Chicken",
            "Mob",
            "c_bronze",
            "bronzechicken",
            "bronzechicken",
            "chicken_roost:ingot_bronze",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Emerald Chicken",
            "Mob",
            "c_emerald",
            "emeradiccrystalchicken",
            "emeradiccrystalchicken",
            "minecraft:emerald",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Ghast Tear Chicken",
            "Monster",
            "c_ghasttear",
            "ghastchicken",
            "ghastchicken",
            "minecraft:ghast_tear",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Enori Crystal Chicken",
            "Mob",
            "c_enoricrystal",
            "enoricrystalchicken",
            "enoricrystalchicken",
            "actuallyadditions:enori_crystal",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Blue Slime Chicken", "Mob", "c_blueslime", "blueslime", "blueslime", "minecraft:stone", 600, 5, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Redstone Alloy Chicken",
            "Mob",
            "c_redstonealloy",
            "redstonealloychicken",
            "redstonealloychicken",
            "enderio:redstone_alloy_ingot",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Electrical Steel Chicken",
            "Mob",
            "c_electricalsteel",
            "electricalsteelchicken",
            "electricalsteelchicken",
            "minecraft:stone",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Dark Steel Chicken",
            "Mob",
            "c_darksteel",
            "darksteelchicken",
            "darksteelchicken",
            "enderio:dark_steel_ingot",
            600,
            5,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Chickenosto", "Mob", "c_chickenosto", "chickenosto", "chickenosto", "minecraft:cake", 600, 5, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Funway Chicken", "Mob", "c_funway", "funwaychicken", "funwaychicken", "minecraft:stone", 600, 5, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Breeze Chicken", "Mob", "c_breeze", "breeze", "breeze", "minecraft:breeze_rod", 600, 5, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Enderium Chicken",
            "Mob",
            "c_enderium",
            "enderiumchicken",
            "enderiumchicken",
            "chicken_roost:ingot_enderium",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Manasteel Chicken",
            "Mob",
            "c_manasteel",
            "manasteelchicken",
            "manasteelchicken",
            "botania:manasteel_ingot",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Blitz Chicken",
            "Mob",
            "c_blitz",
            "blitzrodchicken",
            "blitzrodchicken",
            "thermal:blitz_powder",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Amethyst Bronze Chicken",
            "Mob",
            "c_amethystbronze",
            "purplechicken",
            "purplechicken",
            "tconstruct:amethyst_bronze_nugget",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Hepatizon Chicken",
            "Mob",
            "c_hepatizon",
            "tanzanitechicken",
            "tanzanitechicken",
            "tconstruct:hepatizon_nugget",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Rose Gold Chicken",
            "Mob",
            "c_rosegold",
            "pinkslimechicken",
            "pinkslimechicken",
            "tconstruct:rose_gold_nugget",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Ruby Chicken", "Mob", "c_ruby", "rubychicken", "rubychicken", "silentgems:ruby", 600, 6, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Sapphire Chicken",
            "Mob",
            "c_sapphire",
            "sapphirechicken",
            "sapphirechicken",
            "silentgems:sapphire",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Slime Steel Chicken",
            "Mob",
            "c_slimesteel",
            "sapphirechicken",
            "sapphirechicken",
            "tconstruct:slimesteel_nugget",
            600,
            6,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Brass Chicken", "Mob", "c_brass", "brasschicken", "brasschicken", "create:brass_ingot", 600, 6, false, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Certus Quartz Chicken",
            "Mob",
            "c_certusquartz",
            "quartzenrichedironchicken",
            "quartzenrichedironchicken",
            "ae2:certus_quartz_crystal",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Ender Eye Chicken",
            "Monster",
            "c_endereye",
            "enderchicken",
            "enderchicken",
            "minecraft:ender_eye",
            600,
            6,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Bio Fuel Chicken",
            "Mob",
            "c_biofuel",
            "enderiumchicken",
            "enderiumchicken",
            "mekanism:bio_fuel",
            600,
            6,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Uranium Chicken",
            "Mob",
            "c_uranium",
            "uraniumchicken",
            "uraniumchicken",
            "chicken_roost:ingot_uranium",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Tungsten Chicken",
            "Mob",
            "c_tungsten",
            "tungstenchicken",
            "tungstenchicken",
            "chicken_roost:ingot_tungsten",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Yellorium Chicken",
            "Mob",
            "c_yellorium",
            "yelloriumchicken",
            "yelloriumchicken",
            "bigreactors:yellorium_ingot",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Cobalt Chicken",
            "Mob",
            "c_cobald",
            "cobaltchicken",
            "cobaltchicken",
            "tconstruct:cobalt_nugget",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Prismarine Crystal Chicken",
            "Mob",
            "c_prismarinecrystal",
            "pcrystalchicken",
            "pcrystal_chicken",
            "minecraft:prismarine_crystals",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Emeradic Crystal Chicken",
            "Mob",
            "c_emeradiccrystal",
            "emeradiccrystalchicken",
            "emeradiccrystalchicken",
            "actuallyadditions:emeradic_crystal",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Pulsating Iron Chicken",
            "Mob",
            "c_pulsatingiron",
            "pulsatingironchicken",
            "pulsatingironchicken",
            "enderio:pulsating_alloy_ingot",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Purple Slime Chicken",
            "Mob",
            "c_purpleslime",
            "purpleslime",
            "purpleslime",
            "minecraft:stone",
            600,
            6,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Vibrant Alloy Chicken",
            "Mob",
            "c_vibrantalloy",
            "vibrantalloychicken",
            "vibrantalloychicken",
            "enderio:vibrant_alloy_ingot",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Dark Gem Chicken",
            "Mob",
            "c_darkgem",
            "darkgemchicken",
            "darkgemchicken",
            "evilcraft:dark_gem",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Ardite Chicken",
            "Monster",
            "c_ardite",
            "arditechicken",
            "arditechicken",
            "minecraft:stone",
            600,
            6,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "XP Chicken", "Mob", "c_xp", "xpchicken", "xpchicken", "minecraft:experience_bottle", 600, 6, true, true, true, false, true, true, true, true
         )
      );
      items.add(
         new ChickenData(
            "Pig Iron Chicken",
            "Mob",
            "c_pigiron",
            "pigironchicken",
            "pigironchicken",
            "tconstruct:pig_iron_nugget",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Blizz Chicken",
            "Mob",
            "c_blizz",
            "blizzrodchicken",
            "blizzrodchicken",
            "thermal:blizz_powder",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Manyullyn Chicken",
            "Mob",
            "c_manyullyn",
            "manyullynchicken",
            "manyullynchicken",
            "tconstruct:manyullyn_nugget",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Charged Certus Quartz Chicken",
            "Mob",
            "c_chargedcertus",
            "quartzchicken",
            "quartzchicken",
            "minecraft:stone",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Fluix Crystal Chicken",
            "Mob",
            "c_fluixcrystal",
            "lunarreactivedustchicken",
            "lunarreactivedustchicken",
            "minecraft:stone",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Allthemodium Chicken",
            "Mob",
            "c_allthemodium",
            "energeticalloychicken",
            "energeticalloychicken",
            "allthemodium:allthemodium_ingot",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Amethyst Shard Chicken",
            "Mob",
            "c_amethystshard",
            "amethystchicken",
            "amethystchicken",
            "minecraft:amethyst_shard",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Terrasteel Chicken",
            "Mob",
            "c_terrasteel",
            "terrasteelchicken",
            "terrasteelchicken",
            "botania:terrasteel_ingot",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Hot Tungsten Steel Chicken",
            "Mob",
            "c_hottungstensteel",
            "silverorechicken",
            "tungstensteelchicken",
            "minecraft:stone",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Netherite Chicken",
            "Monster",
            "c_netherite",
            "netherwartchicken",
            "netherwartchicken",
            "minecraft:netherite_scrap",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Nether Star Chicken",
            "Mob",
            "c_netherstar",
            "blitzrodchicken",
            "blitzrodchicken",
            "minecraft:nether_star",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Tungsten Steel Chicken",
            "Mob",
            "c_tungstensteel",
            "tungstensteelchicken",
            "tungstensteelchicken",
            "chicken_roost:ingot_tungstensteel",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Diamantine Crystal Chicken",
            "Mob",
            "c_diamantinecrystal",
            "diamantinecrystalchicken",
            "diamantinecrystalchicken",
            "minecraft:stone",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      if (ModList.get().isLoaded("biggerreactors")) {
         items.add(
            new ChickenData(
               "Cyanite Chicken",
               "Mob",
               "c_cyanite",
               "cyanitechicken",
               "cyanitechicken",
               "biggerreactors:cyanite_ingot",
               600,
               7,
               false,
               true,
               true,
               false,
               true,
               true,
               true,
               true
            )
         );
      } else {
         items.add(
            new ChickenData(
               "Cyanite Chicken",
               "Mob",
               "c_cyanite",
               "cyanitechicken",
               "cyanitechicken",
               "bigreactors:cyanite_ingot",
               600,
               7,
               false,
               true,
               true,
               false,
               true,
               true,
               true,
               true
            )
         );
      }

      items.add(
         new ChickenData(
            "Magical Wood Chicken",
            "Mob",
            "c_magicalwood",
            "magicalwoodchicken",
            "magicalwoodchicken",
            "minecraft:stone",
            600,
            7,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Redstone Crystal Chicken",
            "Mob",
            "c_redstonecrystal",
            "redstonecrystalchicken",
            "redstonecrystalchicken",
            "minecraft:stone",
            600,
            7,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      if (ModList.get().isLoaded("biggerreactors")) {
         items.add(
            new ChickenData(
               "Blutonium Chicken",
               "Mob",
               "c_blutonium",
               "blutoniumchicken",
               "blutoniumchicken",
               "biggerreactors:blutonium_ingot",
               600,
               8,
               false,
               true,
               true,
               false,
               true,
               true,
               true,
               true
            )
         );
      } else {
         items.add(
            new ChickenData(
               "Blutonium Chicken",
               "Mob",
               "c_blutonium",
               "blutoniumchicken",
               "blutoniumchicken",
               "bigreactors:blutonium_ingot",
               600,
               8,
               false,
               true,
               true,
               false,
               true,
               true,
               true,
               true
            )
         );
      }

      items.add(
         new ChickenData(
            "Elementium Chicken",
            "Mob",
            "c_elementium",
            "elementiumchicken",
            "elementiumchicken",
            "botania:elementium_ingot",
            600,
            8,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Knight Slime Chicken",
            "Mob",
            "c_knightslime",
            "knightslimechicken",
            "knightslimechicken",
            "minecraft:stone",
            600,
            8,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Adamantium Chicken",
            "Mob",
            "c_adamantium",
            "emeradiccrystalchicken",
            "emeradiccrystalchicken",
            "chicken_roost:ingot_adamantium",
            600,
            8,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Iridium Chicken",
            "Mob",
            "c_iridium",
            "iridiumchicken",
            "iridiumchicken",
            "chicken_roost:ingot_iridium",
            600,
            8,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Vibranium Chicken",
            "Mob",
            "c_vibranium",
            "vibrantalloychicken",
            "vibrantalloychicken",
            "allthemodium:vibranium_ingot",
            600,
            8,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Lunar Reactive Dust Chicken",
            "Mob",
            "c_lunarreactivedust",
            "lunarreactivedustchicken",
            "lunarreactivedustchicken",
            "minecraft:stone",
            600,
            8,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Stoneburnt Chicken",
            "Mob",
            "c_stoneburnt",
            "stoneburntchicken",
            "stoneburntchicken",
            "minecraft:stone",
            600,
            8,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Draconium Chicken",
            "Monster",
            "c_draconium",
            "draconiumchicken",
            "draconiumchicken",
            "draconicevolution:draconium_ingot",
            600,
            8,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Titanium Chicken",
            "Mob",
            "c_titanium",
            "titaniumchicken",
            "titaniumchicken",
            "chicken_roost:ingot_titanum",
            600,
            9,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Unobtainium Chicken",
            "Mob",
            "c_unobtainium",
            "vinteumchicken",
            "vinteumchicken",
            "allthemodium:unobtainium_ingot",
            600,
            9,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Demon Metal Chicken",
            "Mob",
            "c_demonmetal",
            "demonmetalchicken",
            "demonmetalchicken",
            "minecraft:stone",
            600,
            9,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Moonstone Chicken",
            "Mob",
            "c_moonstone",
            "moonstonechicken",
            "moonstonechicken",
            "arsmagicalegacy:moonstone",
            600,
            9,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Mana Infused Chicken",
            "Mob",
            "c_manainfused",
            "manasteelchicken",
            "manasteelchicken",
            "minecraft:stone",
            600,
            9,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
      items.add(
         new ChickenData(
            "Awakened Draconium Chicken",
            "Monster",
            "c_awakeneddraconium",
            "draconiumawakenedchicken",
            "draconiumawakenedchicken",
            "draconicevolution:awakened_draconium_ingot",
            600,
            9,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true
         )
      );
   }
}
