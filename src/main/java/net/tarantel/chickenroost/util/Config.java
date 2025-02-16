package net.tarantel.chickenroost.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.ConfigValue<Integer> roost_speed_tick = BUILDER.comment("Defines the Roost Production Speed in Seconds:").define("roostspeed", 10);
    public static ForgeConfigSpec.ConfigValue<Integer> breed_speed_tick = BUILDER.comment("Defines the Breeding Speed in Seconds:").define("breederspeed", 15);
    public static ForgeConfigSpec.ConfigValue<Integer> training_speed_tick = BUILDER.comment("Defines the Training Speed in Seconds:").define("trainerspeed", 10);
    public static ForgeConfigSpec.ConfigValue<Integer> extractor_speedtimer = BUILDER.comment("Defines the Soul Extraction Speed in Seconds:").define("soulextractspeed", 5);
    public static ForgeConfigSpec.ConfigValue<Integer> soulbreed_speedtimer = BUILDER.comment("Defines the Soul Breeding Speed in Seconds:").define("soulbreedspeed", 10);
    public static ForgeConfigSpec.ConfigValue<Float> roostxp = BUILDER.comment("Defines the passive XP gained while Breeding or inside the Roost in Ratio like 0.1f = 10%").define("passivexp", 0.1f);
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_1 = BUILDER.comment("Defines the XP Amount per Seed Tier:").define("seeds_tier_1", 50);
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_2 = BUILDER.define("seeds_tier_2", 200);
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_3 = BUILDER.define("seeds_tier_3", 300);
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_4 = BUILDER.define("seeds_tier_4", 500);
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_5 = BUILDER.define("seeds_tier_5", 700);
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_6 = BUILDER.define("seeds_tier_6", 1000);
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_7 = BUILDER.define("seeds_tier_7", 1500);
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_8 = BUILDER.define("seeds_tier_8", 2500);
    public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_9 = BUILDER.define("seeds_tier_9", 5000);
    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_1 = BUILDER.comment("Defines the XP required for a Levelup per Tier").define("xp_tier_1", 50);
    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_2 = BUILDER.define("xp_tier_2", 250);
    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_3 = BUILDER.define("xp_tier_3", 1250);
    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_4 = BUILDER.define("xp_tier_4", 6250);
    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_5 = BUILDER.define("xp_tier_5", 12500);
    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_6 = BUILDER.define("xp_tier_6", 25000);
    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_7 = BUILDER.define("xp_tier_7", 32500);
    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_8 = BUILDER.define("xp_tier_8", 40000);
    public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_9 = BUILDER.define("xp_tier_9", 50000);
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_1 = BUILDER.comment("Defines the max Level per Tier").define("level_tier_1", 128);
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_2 = BUILDER.define("level_tier_2", 128);
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_3 = BUILDER.define("level_tier_3", 128);
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_4 = BUILDER.define("level_tier_4", 128);
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_5 = BUILDER.define("level_tier_5", 128);
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_6 = BUILDER.define("level_tier_6", 128);
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_7 = BUILDER.define("level_tier_7", 128);
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_8 = BUILDER.define("level_tier_8", 128);
    public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_9 = BUILDER.define("level_tier_9", 128);

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}