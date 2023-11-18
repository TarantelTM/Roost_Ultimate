package net.tarantel.chickenroost.util;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class SpawnConfig {

    public static final ForgeConfigSpec.Builder mycfg = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec SPEC;

    static {
        initmycfg(mycfg);
        SPEC = mycfg.build();

    }

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> exampleStringListConfigEntry;
    public static ForgeConfigSpec.IntValue c_weight_stone;
    public static ForgeConfigSpec.IntValue c_weight_cobble;
    public static ForgeConfigSpec.IntValue c_weight_sand;
    public static ForgeConfigSpec.IntValue c_weight_bone;
    public static ForgeConfigSpec.IntValue c_weight_oakwood;
    public static ForgeConfigSpec.IntValue c_weight_endstone;
    public static ForgeConfigSpec.IntValue c_weight_netherrack;

    public static ForgeConfigSpec.IntValue c_min_stone;
    public static ForgeConfigSpec.IntValue c_min_cobble;
    public static ForgeConfigSpec.IntValue c_min_sand;
    public static ForgeConfigSpec.IntValue c_min_bone;
    public static ForgeConfigSpec.IntValue c_min_oakwood;
    public static ForgeConfigSpec.IntValue c_min_endstone;
    public static ForgeConfigSpec.IntValue c_min_netherrack;

    public static ForgeConfigSpec.IntValue c_max_stone;
    public static ForgeConfigSpec.IntValue c_max_cobble;
    public static ForgeConfigSpec.IntValue c_max_sand;
    public static ForgeConfigSpec.IntValue c_max_bone;
    public static ForgeConfigSpec.IntValue c_max_oakwood;
    public static ForgeConfigSpec.IntValue c_max_endstone;
    public static ForgeConfigSpec.IntValue c_max_netherrack;

    public static void initmycfg(ForgeConfigSpec.Builder mycfg) {
        mycfg.push("Roost Ultimate Spawn Config");
        c_weight_stone = mycfg.defineInRange("Stone Chicken - Spawnweight", 14, 1, 1000);
        c_min_stone = mycfg.defineInRange("Stone Chicken - Groupsize Min", 1, 1, 1000);
        c_max_stone = mycfg.defineInRange("Stone Chicken - Groupsize Max", 6, 1, 1000);

        c_weight_cobble = mycfg.defineInRange("Cobble Chicken - Spawnweight", 14, 1, 1000);
        c_min_cobble = mycfg.defineInRange("Cobble Chicken - Groupsize Min", 1, 1, 1000);
        c_max_cobble = mycfg.defineInRange("Cobble Chicken - Groupsize Max", 6, 1, 1000);

        c_weight_sand = mycfg.defineInRange("Sand Chicken - Spawnweight", 14, 1, 1000);
        c_min_sand = mycfg.defineInRange("Sand Chicken - Groupsize Min", 1, 1, 1000);
        c_max_sand = mycfg.defineInRange("Sand Chicken - Groupsize Max", 6, 1, 1000);

        c_weight_bone = mycfg.defineInRange("Bone Chicken - Spawnweight", 20, 1, 1000);
        c_min_bone = mycfg.defineInRange("Bone Chicken - Groupsize Min", 1, 1, 1000);
        c_max_bone = mycfg.defineInRange("Bone Chicken - Groupsize Max", 2, 1, 1000);

        c_weight_oakwood = mycfg.defineInRange("Log Chicken - Spawnweight", 14, 1, 1000);
        c_min_oakwood = mycfg.defineInRange("Log Chicken - Groupsize Min", 1, 1, 1000);
        c_max_oakwood = mycfg.defineInRange("Log Chicken - Groupsize Max", 6, 1, 1000);

        c_weight_endstone = mycfg.defineInRange("Endstone Chicken - Spawnweight", 15, 1, 1000);
        c_min_endstone = mycfg.defineInRange("Endstone Chicken - Groupsize Min", 1, 1, 1000);
        c_max_endstone = mycfg.defineInRange("Endstone Chicken - Groupsize Max", 2, 1, 1000);

        c_weight_netherrack = mycfg.defineInRange("Netherrack Chicken - Spawnweight", 15, 1, 1000);
        c_min_netherrack = mycfg.defineInRange("Netherrack Chicken - Groupsize Min", 1, 1, 1000);
        c_max_netherrack = mycfg.defineInRange("Netherrack Chicken - Groupsize Max", 2, 1, 1000);

        /*c_min_stone = mycfg.defineInRange("Stone Chicken - Groupsize Min", 1, 1, 1000);
        c_min_cobble = mycfg.defineInRange("Cobble Chicken - Groupsize Min", 1, 1, 1000);
        c_min_sand = mycfg.defineInRange("Sand Chicken - Groupsize Min", 1, 1, 1000);
        c_min_bone = mycfg.defineInRange("Bone Chicken - Groupsize Min", 1, 1, 1000);
        c_min_oakwood = mycfg.defineInRange("Log Chicken - Groupsize Min", 1, 1, 1000);
        c_min_endstone = mycfg.defineInRange("Endstone Chicken - Groupsize Min", 1, 1, 1000);
        c_min_netherrack = mycfg.defineInRange("Netherrack Chicken - Groupsize Min", 1, 1, 1000);

        c_max_stone = mycfg.defineInRange("Stone Chicken - Groupsize Max", 6, 1, 1000);
        c_max_cobble = mycfg.defineInRange("Cobble Chicken - Groupsize Max", 6, 1, 1000);
        c_max_sand = mycfg.defineInRange("Sand Chicken - Groupsize Max", 6, 1, 1000);
        c_max_bone = mycfg.defineInRange("Bone Chicken - Groupsize Max", 2, 1, 1000);
        c_max_oakwood = mycfg.defineInRange("Log Chicken - Groupsize Max", 6, 1, 1000);
        c_max_endstone = mycfg.defineInRange("Endstone Chicken - Groupsize Max", 2, 1, 1000);
        c_max_netherrack = mycfg.defineInRange("Netherrack Chicken - Groupsize Max", 2, 1, 1000);*/

        mycfg.pop();
    }
}