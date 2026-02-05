package net.tarantel.chickenroost.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {



    public static class ServerConfig{
        private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        public static ForgeConfigSpec.ConfigValue<String> comment1 = BUILDER.define("comment1", "Defines the Roost Production Speed in Seconds:");
        public static ForgeConfigSpec.ConfigValue<Integer> roost_speed_tick = BUILDER.define("roostspeed", 10);
        public static ForgeConfigSpec.ConfigValue<String> comment12 = BUILDER.define("comment12", "Defines the Level split for output in roost:");
        public static ForgeConfigSpec.ConfigValue<Double> leveloutputsplit = BUILDER.define("leveloutputsplit", 2.0);

        public static ForgeConfigSpec.ConfigValue<String> comment2 = BUILDER.define("comment2", "Defines the Breeding Speed in Seconds:");
        public static ForgeConfigSpec.ConfigValue<Integer> breed_speed_tick = BUILDER.define("breederspeed", 15);

        public static ForgeConfigSpec.ConfigValue<String> comment3 = BUILDER.define("comment3", "Defines the Training Speed in Seconds:");
        public static ForgeConfigSpec.ConfigValue<Integer> training_speed_tick = BUILDER.define("trainerspeed", 10);

        public static ForgeConfigSpec.ConfigValue<String> comment4 = BUILDER.define("comment4", "Defines the Soul Extraction Speed in Seconds:");
        public static ForgeConfigSpec.ConfigValue<Integer> extractor_speedtimer = BUILDER.define("soulextractspeed", 5);

        public static ForgeConfigSpec.ConfigValue<String> comment5 = BUILDER.define("comment5", "Defines the Soul Breeding Speed in Seconds:");
        public static ForgeConfigSpec.ConfigValue<Integer> soulbreed_speedtimer = BUILDER.define("soulbreedspeed", 10);

        public static ForgeConfigSpec.ConfigValue<String> comment6 = BUILDER.define("comment6", "Defines the passive XP gained while Breeding or inside the Roost in Ratio like 0.1f = 10");
        public static ForgeConfigSpec.ConfigValue<Double> roostxp = BUILDER.define("passivexp", 0.1d);

        public static ForgeConfigSpec.ConfigValue<String> comment7 = BUILDER.define("comment7", "Defines the XP Amount per Seed Tier:");
        public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_1 = BUILDER.define("seeds_tier_1", 50);
        public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_2 = BUILDER.define("seeds_tier_2", 200);
        public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_3 = BUILDER.define("seeds_tier_3", 300);
        public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_4 = BUILDER.define("seeds_tier_4", 500);
        public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_5 = BUILDER.define("seeds_tier_5", 700);
        public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_6 = BUILDER.define("seeds_tier_6", 1000);
        public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_7 = BUILDER.define("seeds_tier_7", 1500);
        public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_8 = BUILDER.define("seeds_tier_8", 2500);
        public static ForgeConfigSpec.ConfigValue<Integer> food_xp_tier_9 = BUILDER.define("seeds_tier_9", 5000);

        public static ForgeConfigSpec.ConfigValue<String> comment8 = BUILDER.define("comment8", "Defines the XP required for a Levelup per Tier:");
        public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_1 = BUILDER.define("xp_tier_1", 50);
        public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_2 = BUILDER.define("xp_tier_2", 250);
        public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_3 = BUILDER.define("xp_tier_3", 1250);
        public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_4 = BUILDER.define("xp_tier_4", 6250);
        public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_5 = BUILDER.define("xp_tier_5", 12500);
        public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_6 = BUILDER.define("xp_tier_6", 25000);
        public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_7 = BUILDER.define("xp_tier_7", 32500);
        public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_8 = BUILDER.define("xp_tier_8", 40000);
        public static ForgeConfigSpec.ConfigValue<Integer> xp_tier_9 = BUILDER.define("xp_tier_9", 50000);
        public static ForgeConfigSpec.ConfigValue<String> comment9 = BUILDER.define("comment9", "Defines the max Level per Tier:");
        public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_1 = BUILDER.define("level_tier_1", 128);
        public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_2 = BUILDER.define("level_tier_2", 128);
        public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_3 = BUILDER.define("level_tier_3", 128);
        public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_4 = BUILDER.define("level_tier_4", 128);
        public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_5 = BUILDER.define("level_tier_5", 128);
        public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_6 = BUILDER.define("level_tier_6", 128);
        public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_7 = BUILDER.define("level_tier_7", 128);
        public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_8 = BUILDER.define("level_tier_8", 128);
        public static ForgeConfigSpec.ConfigValue<Integer> maxlevel_tier_9 = BUILDER.define("level_tier_9", 128);
        public static ForgeConfigSpec.ConfigValue<String> comment10 = BUILDER.define("comment10", "Enable Breeder Crafting in both ways, this means it dont matter if u put the Chicken right or left.");
        public static ForgeConfigSpec.ConfigValue<Boolean> enablebothways = BUILDER.define("enableboth", true);
        public static ForgeConfigSpec.ConfigValue<String> comment11 = BUILDER.define("comment11", "Change the Collectors max collecting Range:");
        public static ForgeConfigSpec.ConfigValue<Integer> collectorrange = BUILDER.define("collector_maxrange", 30);
        public static ForgeConfigSpec.ConfigValue<String> comment13 = BUILDER.define("comment13", "Change the Feeder max feeding Range:");
        public static ForgeConfigSpec.ConfigValue<Integer> feederrange = BUILDER.define("feeder_maxrange", 30);

        public static final ForgeConfigSpec SPEC = BUILDER.build();
    }



    public static class ClientConfig{
        private static final ForgeConfigSpec.Builder ClientBUILDER = new ForgeConfigSpec.Builder();

        public static ForgeConfigSpec.ConfigValue<String> cb = ClientBUILDER.define("cb", "Colorblindmode:");
        public static ForgeConfigSpec.ConfigValue<Boolean> breeder_cb = ClientBUILDER.define("breeder_cb", false);
        public static ForgeConfigSpec.ConfigValue<Boolean> trainer_cb = ClientBUILDER.define("trainer_cb", false);
        public static ForgeConfigSpec.ConfigValue<Boolean> extractor_cb = ClientBUILDER.define("extractor_cb", false);
        public static ForgeConfigSpec.ConfigValue<Boolean> roost_cb = ClientBUILDER.define("roost_cb", false);



        public static final ForgeConfigSpec ClientSpec = ClientBUILDER.build();
    }


}