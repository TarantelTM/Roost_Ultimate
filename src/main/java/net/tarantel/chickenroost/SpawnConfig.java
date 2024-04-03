package net.tarantel.chickenroost;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChickenRoostMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpawnConfig {


    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_All = BUILDER
            .comment("Enable/Disable Spawning")
            .define("Enable all Spawns", true);

    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Sponge = BUILDER
            .define("Enable Sponge Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_SpiderEye = BUILDER
            .define("Enable Spider Eye Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Snow = BUILDER
            .define("Enable Snow Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Sand = BUILDER
            .define("Enable Sand Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Rotten = BUILDER
            .define("Enable Rotten Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Rabbithide = BUILDER
            .define("Enable Rabbithide Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Oak = BUILDER
            .define("Enable Oak Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Netherrack = BUILDER
            .define("Enable Netherrack Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Melon = BUILDER
            .define("Enable Melon Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Leather = BUILDER
            .define("Enable Leather Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Jungle = BUILDER
            .define("Enable Jungle Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Acacia = BUILDER
            .define("Enable Acacia Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Apple = BUILDER
            .define("Enable Apple Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Beetroot = BUILDER
            .define("Enable Beetroot Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Birch = BUILDER
            .define("Enable Birch Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Carrot = BUILDER
            .define("Enable Carrot Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Honeycomb = BUILDER
            .define("Enable Honeycomb Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Glowberries = BUILDER
            .define("Enable Glow Berries Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_DarkOak = BUILDER
            .define("Enable Dark Oak Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Cobble = BUILDER
            .define("Enable Cobble Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Spruce = BUILDER
            .define("Enable Spruce Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Sugar = BUILDER
            .define("Enable Sugar Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Sweetberries = BUILDER
            .define("Enable Sweetberries Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_String = BUILDER
            .define("Enable String Chicken Spawning", true);

    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Stone = BUILDER
            .define("Enable Stone Chicken Spawning", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_Wool = BUILDER
            .define("Enable Wool Chicken Spawning", true);

    public static final ForgeConfigSpec.ConfigValue<Integer> Sponge_Spawnweight = BUILDER
            .comment("Set Spawnweights")
            .define("Sponge Spawnweight", 8);
    public static final ForgeConfigSpec.ConfigValue<Integer> SpiderEye_Spawnweight = BUILDER
            .define("Spider Eye Spawnweight", 10);
    public static final ForgeConfigSpec.ConfigValue<Integer> Snow_Spawnweight = BUILDER
            .define("Snow Spawnweight", 8);
    public static final ForgeConfigSpec.ConfigValue<Integer> Sand_Spawnweight = BUILDER
            .define("Sand Spawnweight", 20);
    public static final ForgeConfigSpec.ConfigValue<Integer> Rotten_Spawnweight = BUILDER
            .define("Rotten Spawnweight", 8);
    public static final ForgeConfigSpec.ConfigValue<Integer> Rabbithide_Spawnweight = BUILDER
            .define("Rabbit Hide Spawnweight", 8);
    public static final ForgeConfigSpec.ConfigValue<Integer> Oak_Spawnweight = BUILDER
            .define("Oak Spawnweight", 20);
    public static final ForgeConfigSpec.ConfigValue<Integer> Netherrack_Spawnweight = BUILDER
            .define("Netherrack Spawnweight", 40);
    public static final ForgeConfigSpec.ConfigValue<Integer> Melon_Spawnweight = BUILDER
            .define("Melon Spawnweight", 8);
    public static final ForgeConfigSpec.ConfigValue<Integer> Leather_Spawnweight = BUILDER
            .define("Leather Spawnweight", 8);
    public static final ForgeConfigSpec.ConfigValue<Integer> Jungle_Spawnweight = BUILDER
            .define("Jungle Spawnweight", 20);
    public static final ForgeConfigSpec.ConfigValue<Integer> Acacia_Spawnweight = BUILDER
            .define("Acacia Spawnweight", 20);
    public static final ForgeConfigSpec.ConfigValue<Integer> Apple_Spawnweight = BUILDER
            .define("Apple Spawnweight", 8);
    public static final ForgeConfigSpec.ConfigValue<Integer> Beetroot_Spawnweight = BUILDER
            .define("Beetroot Spawnweight", 8);
    public static final ForgeConfigSpec.ConfigValue<Integer> Birch_Spawnweight = BUILDER
            .define("Birch Spawnweight", 20);
    public static final ForgeConfigSpec.ConfigValue<Integer> Carrot_Spawnweight = BUILDER
            .define("Carrot Spawnweight", 8);
    public static final ForgeConfigSpec.ConfigValue<Integer> Honeycomb_Spawnweight = BUILDER
            .define("Honeycomb Spawnweight", 8);
    public static final ForgeConfigSpec.ConfigValue<Integer> Glowberries_Spawnweight = BUILDER
            .define("Glowberries Spawnweight", 0);
    public static final ForgeConfigSpec.ConfigValue<Integer> DarkOak_Spawnweight = BUILDER
            .define("Dark Oak Spawnweight", 20);
    public static final ForgeConfigSpec.ConfigValue<Integer> Cobble_Spawnweight = BUILDER
            .define("Cobble Spawnweight", 15);
    public static final ForgeConfigSpec.ConfigValue<Integer> Spruce_Spawnweight = BUILDER
            .define("Spruce Spawnweight", 20);
    public static final ForgeConfigSpec.ConfigValue<Integer> Sugar_Spawnweight = BUILDER
            .define("Sugar Spawnweight", 20);
    public static final ForgeConfigSpec.ConfigValue<Integer> Sweetberries_Spawnweight = BUILDER
            .define("Sweetberries Spawnweight", 8);
    public static final ForgeConfigSpec.ConfigValue<Integer> String_Spawnweight = BUILDER
            .define("String Spawnweight", 5);
    public static final ForgeConfigSpec.ConfigValue<Integer> Stone_Spawnweight = BUILDER
            .define("Stone Spawnweight", 8);
    public static final ForgeConfigSpec.ConfigValue<Integer> Wool_Spawnweight = BUILDER
            .define("Wool Spawnweight", 8);








    public static final ForgeConfigSpec.ConfigValue<Integer> Sponge_MinGroupsize = BUILDER
            .comment("Set MinGroupsizes")
            .define("Sponge MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> SpiderEye_MinGroupsize = BUILDER
            .define("Spider Eye MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Snow_MinGroupsize = BUILDER
            .define("Snow MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Sand_MinGroupsize = BUILDER
            .define("Sand MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Rotten_MinGroupsize = BUILDER
            .define("Rotten MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Rabbithide_MinGroupsize = BUILDER
            .define("Rabbit Hide MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Oak_MinGroupsize = BUILDER
            .define("Oak MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Netherrack_MinGroupsize = BUILDER
            .define("Netherrack MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Melon_MinGroupsize = BUILDER
            .define("Melon MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Leather_MinGroupsize = BUILDER
            .define("Leather MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Jungle_MinGroupsize = BUILDER
            .define("Jungle MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Acacia_MinGroupsize = BUILDER
            .define("Acacia MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Apple_MinGroupsize = BUILDER
            .define("Apple MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Beetroot_MinGroupsize = BUILDER
            .define("Beetroot MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Birch_MinGroupsize = BUILDER
            .define("Birch MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Carrot_MinGroupsize = BUILDER
            .define("Carrot MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Honeycomb_MinGroupsize = BUILDER
            .define("Honeycomb MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Glowberries_MinGroupsize = BUILDER
            .define("Glowberries MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> DarkOak_MinGroupsize = BUILDER
            .define("Dark Oak MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Cobble_MinGroupsize = BUILDER
            .define("Cobble MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Spruce_MinGroupsize = BUILDER
            .define("Spruce MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Sugar_MinGroupsize = BUILDER
            .define("Sugar MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Sweetberries_MinGroupsize = BUILDER
            .define("Sweetberries MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> String_MinGroupsize = BUILDER
            .define("String MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Stone_MinGroupsize = BUILDER
            .define("Stone MinGroupsize", 1);
    public static final ForgeConfigSpec.ConfigValue<Integer> Wool_MinGroupsize = BUILDER
            .define("Wool MinGroupsize", 1);


    /*public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineList("items", List.of("minecraft:iron_ingot"), SpawnConfig::validateItemName);




    public static Set<ResourceLocation> items;*/







    public static final ForgeConfigSpec.ConfigValue<Integer> Sponge_MaxGroupsize = BUILDER
            .comment("Set MaxGroupsizes")
            .define("Sponge MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> SpiderEye_MaxGroupsize = BUILDER
            .define("Spider Eye MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Snow_MaxGroupsize = BUILDER
            .define("Snow MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Sand_MaxGroupsize = BUILDER
            .define("Sand MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Rotten_MaxGroupsize = BUILDER
            .define("Rotten MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Rabbithide_MaxGroupsize = BUILDER
            .define("Rabbit Hide MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Oak_MaxGroupsize = BUILDER
            .define("Oak MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Netherrack_MaxGroupsize = BUILDER
            .define("Netherrack MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Melon_MaxGroupsize = BUILDER
            .define("Melon MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Leather_MaxGroupsize = BUILDER
            .define("Leather MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Jungle_MaxGroupsize = BUILDER
            .define("Jungle MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Acacia_MaxGroupsize = BUILDER
            .define("Acacia MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Apple_MaxGroupsize = BUILDER
            .define("Apple MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Beetroot_MaxGroupsize = BUILDER
            .define("Beetroot MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Birch_MaxGroupsize = BUILDER
            .define("Birch MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Carrot_MaxGroupsize = BUILDER
            .define("Carrot MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Honeycomb_MaxGroupsize = BUILDER
            .define("Honeycomb MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Glowberries_MaxGroupsize = BUILDER
            .define("Glowberries MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> DarkOak_MaxGroupsize = BUILDER
            .define("Dark Oak MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Cobble_MaxGroupsize = BUILDER
            .define("Cobble MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Spruce_MaxGroupsize = BUILDER
            .define("Spruce MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Sugar_MaxGroupsize = BUILDER
            .define("Sugar MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Sweetberries_MaxGroupsize = BUILDER
            .define("Sweetberries MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> String_MaxGroupsize = BUILDER
            .define("String MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Stone_MaxGroupsize = BUILDER
            .define("Stone MaxGroupsize", 2);
    public static final ForgeConfigSpec.ConfigValue<Integer> Wool_MaxGroupsize = BUILDER
            .define("Wool MaxGroupsize", 2);
    static final ForgeConfigSpec SPEC = BUILDER.build();

    /*private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && Registry.BIOME_REGISTRY.equals(itemName);
    }*/


    /*@SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        // convert the list of strings into a set of items
        items = ITEM_STRINGS.get().stream()
                .map(itemName -> ResourceLocation.of(itemName)
                .collect(Collectors.toSet());
    }*/
}
