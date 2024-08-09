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
        roost_speed_tick = mycfg.define("Roost Speed", 10);
        breed_speed_tick = mycfg.define("Breeder Speed", 15);
        training_speed_tick = mycfg.define("Trainer Speed", 10);
        extractor_speedtimer = mycfg.define("Soul Extractor Speed", 5);
        soulbreed_speedtimer = mycfg.define("Soul Breeder Speed", 10);
        mycfg.comment("Roost XP - 0.1F = 10% of Trainer - 0.0F = Disabled");
        roostxp = mycfg.define("Roost XP", 0.1f);
        mycfg.comment("Seeds Tier 1-9 and Vanilla is the XP Amount for the tiered Seeds.");
        food_xp_tier_1 = mycfg.define("Seeds Tier 1", 50);
        food_xp_tier_2 = mycfg.define("Seeds Tier 2", 200);
        food_xp_tier_3 = mycfg.define("Seeds Tier 3", 300);
        food_xp_tier_4 = mycfg.define("Seeds Tier 4", 500);
        food_xp_tier_5 = mycfg.define("Seeds Tier 5", 700);
        food_xp_tier_6 = mycfg.define("Seeds Tier 6", 1000);
        food_xp_tier_7 = mycfg.define("Seeds Tier 7", 1500);
        food_xp_tier_8 = mycfg.define("Seeds Tier 8", 2500);
        food_xp_tier_9 = mycfg.define("Seeds Tier 9", 5000);
        mycfg.comment("XP Tier 1-9 - is the needed XP Amount for a levelup for each Tier of Chicken.");
        xp_tier_1 = mycfg.define("XP Tier 1", 50);
        xp_tier_2 = mycfg.define("XP Tier 2", 250);
        xp_tier_3 = mycfg.define("XP Tier 3", 1250);
        xp_tier_4 = mycfg.define("XP Tier 4", 6250);
        xp_tier_5 = mycfg.define("XP Tier 5", 12500);
        xp_tier_6 = mycfg.define("XP Tier 6", 25000);
        xp_tier_7 = mycfg.define("XP Tier 7", 32500);
        xp_tier_8 = mycfg.define("XP Tier 8", 40000);
        xp_tier_9 = mycfg.define("XP Tier 9", 50000);
        mycfg.comment("Tier 1-9 - sets the maxlevel for each Tier of Chicken.");
        maxlevel_tier_1 = mycfg.define("Max Level Tier 1", 128);
        maxlevel_tier_2 = mycfg.define("Max Level Tier 2", 128);
        maxlevel_tier_3 = mycfg.define("Max Level Tier 3", 128);
        maxlevel_tier_4 = mycfg.define("Max Level Tier 4", 128);
        maxlevel_tier_5 = mycfg.define("Max Level Tier 5", 128);
        maxlevel_tier_6 = mycfg.define("Max Level Tier 6", 128);
        maxlevel_tier_7 = mycfg.define("Max Level Tier 7", 128);
        maxlevel_tier_8 = mycfg.define("Max Level Tier 8", 128);
        maxlevel_tier_9 = mycfg.define("Max Level Tier 9", 128);
        mycfg.pop();
    }
}
