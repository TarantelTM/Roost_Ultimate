package net.tarantel.chickenroost.util;

import net.neoforged.neoforge.common.ModConfigSpec;


@SuppressWarnings("ALL")

public class Config {

    public static final ModConfigSpec.Builder mycfg = new ModConfigSpec.Builder();

    public static ModConfigSpec SPEC;

    static {
        initmycfg(mycfg);
        SPEC = mycfg.build();

    }

    public static ModConfigSpec.ConfigValue<Integer> roost_speed_tick;
    public static ModConfigSpec.ConfigValue<Integer> breed_speed_tick;
    public static ModConfigSpec.ConfigValue<Integer> training_speed_tick;

    public static ModConfigSpec.ConfigValue<Integer> extractor_speedtimer;
    public static ModConfigSpec.ConfigValue<Integer> soulbreed_speedtimer;
    public static ModConfigSpec.ConfigValue<Integer> trainingxp_perfood;
    public static ModConfigSpec.ConfigValue<Float> roostxp;
    public static ModConfigSpec.ConfigValue<Integer> food_xp_tier_1;
    public static ModConfigSpec.ConfigValue<Integer> food_xp_tier_2;
    public static ModConfigSpec.ConfigValue<Integer> food_xp_tier_3;
    public static ModConfigSpec.ConfigValue<Integer> food_xp_tier_4;
    public static ModConfigSpec.ConfigValue<Integer> food_xp_tier_5;
    public static ModConfigSpec.ConfigValue<Integer> food_xp_tier_6;
    public static ModConfigSpec.ConfigValue<Integer> food_xp_tier_7;
    public static ModConfigSpec.ConfigValue<Integer> food_xp_tier_8;
    public static ModConfigSpec.ConfigValue<Integer> food_xp_tier_9;

    public static ModConfigSpec.ConfigValue<Integer> xp_tier_1;

    public static ModConfigSpec.ConfigValue<Integer> xp_tier_2;

    public static ModConfigSpec.ConfigValue<Integer> xp_tier_3;

    public static ModConfigSpec.ConfigValue<Integer> xp_tier_4;

    public static ModConfigSpec.ConfigValue<Integer> xp_tier_5;

    public static ModConfigSpec.ConfigValue<Integer> xp_tier_6;

    public static ModConfigSpec.ConfigValue<Integer> xp_tier_7;

    public static ModConfigSpec.ConfigValue<Integer> xp_tier_8;
    public static ModConfigSpec.ConfigValue<Integer> xp_tier_9;

    public static ModConfigSpec.ConfigValue<Integer> maxlevel_tier_1;
    public static ModConfigSpec.ConfigValue<Integer> maxlevel_tier_2;
    public static ModConfigSpec.ConfigValue<Integer> maxlevel_tier_3;
    public static ModConfigSpec.ConfigValue<Integer> maxlevel_tier_4;
    public static ModConfigSpec.ConfigValue<Integer> maxlevel_tier_5;
    public static ModConfigSpec.ConfigValue<Integer> maxlevel_tier_6;
    public static ModConfigSpec.ConfigValue<Integer> maxlevel_tier_7;
    public static ModConfigSpec.ConfigValue<Integer> maxlevel_tier_8;
    public static ModConfigSpec.ConfigValue<Integer> maxlevel_tier_9;




    public static void initmycfg(ModConfigSpec.Builder mycfg) {
        mycfg.push("Roost Ultimate Config");
        mycfg.comment("Production Speed in Seconds");
        roost_speed_tick = mycfg.define("Roost Speed", 20);
        breed_speed_tick = mycfg.define("Breeder Speed", 30);
        training_speed_tick = mycfg.define("Trainer Speed", 25);
        extractor_speedtimer = mycfg.define("Soul Extractor Speed", 10);
        soulbreed_speedtimer = mycfg.define("Soul Breeder Speed", 40);
        mycfg.comment("Roost XP - 0.1F = 10% of Trainer - 0.0F = Disabled");
        roostxp = mycfg.define("Roost XP", 0.1f);
        mycfg.comment("Seeds Tier 1-9 and Vanilla is the XP Amount for the tiered Seeds.");
        trainingxp_perfood = mycfg.define("Vanilla Seeds", 20);
        food_xp_tier_1 = mycfg.define("Seeds Tier 1", 100);
        food_xp_tier_2 = mycfg.define("Seeds Tier 2", 125);
        food_xp_tier_3 = mycfg.define("Seeds Tier 3", 150);
        food_xp_tier_4 = mycfg.define("Seeds Tier 4", 300);
        food_xp_tier_5 = mycfg.define("Seeds Tier 5", 350);
        food_xp_tier_6 = mycfg.define("Seeds Tier 6", 500);
        food_xp_tier_7 = mycfg.define("Seeds Tier 7", 700);
        food_xp_tier_8 = mycfg.define("Seeds Tier 8", 1000);
        food_xp_tier_9 = mycfg.define("Seeds Tier 9", 2500);
        mycfg.comment("XP Tier 1-9 - is the needed XP Amount for a levelup for each Tier of Chicken.");
        xp_tier_1 = mycfg.define("XP Tier 1", 500);
        xp_tier_2 = mycfg.define("XP Tier 2", 2500);
        xp_tier_3 = mycfg.define("XP Tier 3", 12500);
        xp_tier_4 = mycfg.define("XP Tier 4", 62500);
        xp_tier_5 = mycfg.define("XP Tier 5", 125000);
        xp_tier_6 = mycfg.define("XP Tier 6", 250000);
        xp_tier_7 = mycfg.define("XP Tier 7", 325000);
        xp_tier_8 = mycfg.define("XP Tier 8", 400000);
        xp_tier_9 = mycfg.define("XP Tier 9", 500000);
        mycfg.comment("Tier 1-9 - sets the maxlevel for each Tier of Chicken.");
        maxlevel_tier_1 = mycfg.define("Max Level Tier 1", 60);
        maxlevel_tier_2 = mycfg.define("Max Level Tier 2", 60);
        maxlevel_tier_3 = mycfg.define("Max Level Tier 3", 60);
        maxlevel_tier_4 = mycfg.define("Max Level Tier 4", 60);
        maxlevel_tier_5 = mycfg.define("Max Level Tier 5", 60);
        maxlevel_tier_6 = mycfg.define("Max Level Tier 6", 60);
        maxlevel_tier_7 = mycfg.define("Max Level Tier 7", 60);
        maxlevel_tier_8 = mycfg.define("Max Level Tier 8", 60);
        maxlevel_tier_9 = mycfg.define("Max Level Tier 9", 60);
        mycfg.pop();
    }
}
