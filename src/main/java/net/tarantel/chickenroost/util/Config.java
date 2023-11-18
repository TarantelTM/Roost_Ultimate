package net.tarantel.chickenroost.util;

import net.minecraftforge.common.ForgeConfigSpec;

@SuppressWarnings("ALL")

public class Config {

    public static final ForgeConfigSpec.Builder mycfg = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec SPEC;

    static {
        initmycfg(mycfg);
        SPEC = mycfg.build();

    }

    public static ForgeConfigSpec.IntValue roost_speed_tick;
    public static ForgeConfigSpec.IntValue breed_speed_tick;
    public static ForgeConfigSpec.IntValue training_speed_tick;

    public static ForgeConfigSpec.IntValue extractor_speedtimer;
    public static ForgeConfigSpec.IntValue soulbreed_speedtimer;
    public static ForgeConfigSpec.IntValue trainingxp_perfood;
    public static ForgeConfigSpec.IntValue food_xp_tier_1;
    public static ForgeConfigSpec.IntValue food_xp_tier_2;
    public static ForgeConfigSpec.IntValue food_xp_tier_3;
    public static ForgeConfigSpec.IntValue food_xp_tier_4;
    public static ForgeConfigSpec.IntValue food_xp_tier_5;
    public static ForgeConfigSpec.IntValue food_xp_tier_6;
    public static ForgeConfigSpec.IntValue food_xp_tier_7;
    public static ForgeConfigSpec.IntValue food_xp_tier_8;
    public static ForgeConfigSpec.IntValue food_xp_tier_9;

    public static ForgeConfigSpec.IntValue xp_tier_1;

    public static ForgeConfigSpec.IntValue xp_tier_2;

    public static ForgeConfigSpec.IntValue xp_tier_3;

    public static ForgeConfigSpec.IntValue xp_tier_4;

    public static ForgeConfigSpec.IntValue xp_tier_5;

    public static ForgeConfigSpec.IntValue xp_tier_6;

    public static ForgeConfigSpec.IntValue xp_tier_7;

    public static ForgeConfigSpec.IntValue xp_tier_8;
    public static ForgeConfigSpec.IntValue xp_tier_9;

    public static ForgeConfigSpec.IntValue maxlevel_tier_1;
    public static ForgeConfigSpec.IntValue maxlevel_tier_2;
    public static ForgeConfigSpec.IntValue maxlevel_tier_3;
    public static ForgeConfigSpec.IntValue maxlevel_tier_4;
    public static ForgeConfigSpec.IntValue maxlevel_tier_5;
    public static ForgeConfigSpec.IntValue maxlevel_tier_6;
    public static ForgeConfigSpec.IntValue maxlevel_tier_7;
    public static ForgeConfigSpec.IntValue maxlevel_tier_8;
    public static ForgeConfigSpec.IntValue maxlevel_tier_9;


    public static void initmycfg(ForgeConfigSpec.Builder mycfg) {
        mycfg.push("Roost Ultimate Config");
        mycfg.comment("Production Speed in Seconds");
        roost_speed_tick = mycfg.defineInRange("Roost Speed", 20, 1, 214748364);
        breed_speed_tick = mycfg.defineInRange("Breeder Speed", 30, 1, 214748364);
        training_speed_tick = mycfg.defineInRange("Trainer Speed", 25, 1, 214748364);
        //extractor_speedtimer = mycfg.defineInRange("Soul Extractor Speed", 10, 1, 2147483647);
        //soulbreed_speedtimer = mycfg.defineInRange("Soul Breeder Speed", 40, 1, 2147483647);
        mycfg.comment("             ");
        mycfg.comment("             ");
        mycfg.comment("Seeds Tier 1-9 and Vanilla is the XP Amount for the tiered Seeds.");
        trainingxp_perfood = mycfg.defineInRange("Vanilla Seeds", 20, 1, 214748364);
        food_xp_tier_1 = mycfg.defineInRange("Seeds Tier 1", 100, 1, 214748364);
        food_xp_tier_2 = mycfg.defineInRange("Seeds Tier 2", 125, 1, 214748364);
        food_xp_tier_3 = mycfg.defineInRange("Seeds Tier 3", 150, 1, 214748364);
        food_xp_tier_4 = mycfg.defineInRange("Seeds Tier 4", 300, 1, 214748364);
        food_xp_tier_5 = mycfg.defineInRange("Seeds Tier 5", 350, 1, 214748364);
        food_xp_tier_6 = mycfg.defineInRange("Seeds Tier 6", 500, 1, 214748364);
        food_xp_tier_7 = mycfg.defineInRange("Seeds Tier 7", 700, 1, 214748364);
        food_xp_tier_8 = mycfg.defineInRange("Seeds Tier 8", 1000, 1, 214748364);
        food_xp_tier_9 = mycfg.defineInRange("Seeds Tier 9", 2500, 1, 214748364);
        mycfg.comment("             ");
        mycfg.comment("             ");
        mycfg.comment("XP Tier 1-9 - is the needed XP Amount for a levelup for each Tier of Chicken.");
        xp_tier_1 = mycfg.defineInRange("XP Tier 1", 500, 1, 214748364);
        xp_tier_2 = mycfg.defineInRange("XP Tier 2", 2500, 1, 214748364);
        xp_tier_3 = mycfg.defineInRange("XP Tier 3", 12500, 1, 214748364);
        xp_tier_4 = mycfg.defineInRange("XP Tier 4", 62500, 1, 214748364);
        xp_tier_5 = mycfg.defineInRange("XP Tier 5", 125000, 1, 214748364);
        xp_tier_6 = mycfg.defineInRange("XP Tier 6", 250000, 1, 214748364);
        xp_tier_7 = mycfg.defineInRange("XP Tier 7", 325000, 1, 214748364);
        xp_tier_8 = mycfg.defineInRange("XP Tier 8", 400000, 1, 214748364);
        xp_tier_9 = mycfg.defineInRange("XP Tier 9", 500000, 1, 214748364);
        mycfg.comment("             ");
        mycfg.comment("             ");
        mycfg.comment("Tier 1-9 - sets the maxlevel for each Tier of Chicken.");
        maxlevel_tier_1 = mycfg.defineInRange("Max Level Tier 1", 60, 1, 214748364);
        maxlevel_tier_2 = mycfg.defineInRange("Max Level Tier 2", 60, 1, 214748364);
        maxlevel_tier_3 = mycfg.defineInRange("Max Level Tier 3", 60, 1, 214748364);
        maxlevel_tier_4 = mycfg.defineInRange("Max Level Tier 4", 60, 1, 214748364);
        maxlevel_tier_5 = mycfg.defineInRange("Max Level Tier 5", 60, 1, 214748364);
        maxlevel_tier_6 = mycfg.defineInRange("Max Level Tier 6", 60, 1, 214748364);
        maxlevel_tier_7 = mycfg.defineInRange("Max Level Tier 7", 60, 1, 214748364);
        maxlevel_tier_8 = mycfg.defineInRange("Max Level Tier 8", 60, 1, 214748364);
        maxlevel_tier_9 = mycfg.defineInRange("Max Level Tier 9", 60, 1, 214748364);
        mycfg.pop();
    }
}
