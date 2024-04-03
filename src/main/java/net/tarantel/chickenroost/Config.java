package net.tarantel.chickenroost;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {


    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();



    public static final ForgeConfigSpec.ConfigValue<Float> ROOSTXPRATIO = BUILDER
            .comment("Roost XP Ratio")
            .define("Ratio", 0.1f);
    public static final ForgeConfigSpec.ConfigValue<Boolean> GUIDEBOOK = BUILDER
            .comment("Enable Crafting Grid for Guidebook")
            .define("Enabled", true);

    public static final ForgeConfigSpec.ConfigValue<Integer> ROOSTSPEED = BUILDER
            .comment("Roost Processing Speed in Seconds")
            .define("Roost Speed", 20);

    public static final ForgeConfigSpec.ConfigValue<Integer> BREEDERSPEED = BUILDER
            .comment("Breeder Processing Speed in Seconds")
            .define("Breeder Speed", 30);

    public static final ForgeConfigSpec.ConfigValue<Integer> TRAINERSPEED = BUILDER
            .comment("Trainer Processing Speed in Seconds")
            .define("Trainer Speed", 25);

    public static final ForgeConfigSpec.ConfigValue<Integer> SOULBREEDERSPEED = BUILDER
            .comment("Soul Breeder Processing Speed in Seconds")
            .define("Soul Breeder Speed", 40);

    public static final ForgeConfigSpec.ConfigValue<Integer> SOULEXTRACTORSPEED = BUILDER
            .comment("Soul Extractor Processing Speed in Seconds")
            .define("Soul Extractor Speed", 10);


    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LEVEL_TIER_1 = BUILDER
            .comment("Set Max level for Tier 1 Chicken")
            .define("Max level Tier 1", 60);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LEVEL_TIER_2 = BUILDER
            .comment("Set Max level for Tier 2 Chicken")
            .define("Max level Tier 2", 60);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LEVEL_TIER_3 = BUILDER
            .comment("Set Max level for Tier 3 Chicken")
            .define("Max level Tier 3", 60);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LEVEL_TIER_4 = BUILDER
            .comment("Set Max level for Tier 4 Chicken")
            .define("Max level Tier 4", 60);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LEVEL_TIER_5 = BUILDER
            .comment("Set Max level for Tier 5 Chicken")
            .define("Max level Tier 5", 60);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LEVEL_TIER_6 = BUILDER
            .comment("Set Max level for Tier 6 Chicken")
            .define("Max level Tier 6", 60);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LEVEL_TIER_7 = BUILDER
            .comment("Set Max level for Tier 7 Chicken")
            .define("Max level Tier 7", 60);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LEVEL_TIER_8 = BUILDER
            .comment("Set Max level for Tier 8 Chicken")
            .define("Max level Tier 8", 60);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LEVEL_TIER_9 = BUILDER
            .comment("Set Max level for Tier 9 Chicken")
            .define("Max level Tier 9", 60);


    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_XP_TIER_1 = BUILDER
            .comment("Set required XP for Levelup for Tier 1 Chicken")
            .define("Required XP Tier 1", 500);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_XP_TIER_2 = BUILDER
            .comment("Set required XP for Levelup for Tier 2 Chicken")
            .define("Required XP Tier 2", 2500);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_XP_TIER_3 = BUILDER
            .comment("Set required XP for Levelup for Tier 3 Chicken")
            .define("Required XP Tier 3", 12500);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_XP_TIER_4 = BUILDER
            .comment("Set required XP for Levelup for Tier 4 Chicken")
            .define("Required XP Tier 4", 62500);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_XP_TIER_5 = BUILDER
            .comment("Set required XP for Levelup for Tier 5 Chicken")
            .define("Required XP Tier 5", 125000);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_XP_TIER_6 = BUILDER
            .comment("Set required XP for Levelup for Tier 6 Chicken")
            .define("Required XP Tier 6", 250000);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_XP_TIER_7 = BUILDER
            .comment("Set required XP for Levelup for Tier 7 Chicken")
            .define("Required XP Tier 7", 325000);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_XP_TIER_8 = BUILDER
            .comment("Set required XP for Levelup for Tier 8 Chicken")
            .define("Required XP Tier 8", 400000);
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_XP_TIER_9 = BUILDER
            .comment("Set required XP for Levelup for Tier 9 Chicken")
            .define("Required XP Tier 9", 500000);



    public static final ForgeConfigSpec.ConfigValue<Integer> VANILLA_SEED_XP = BUILDER
            .comment("Set XP Amount for Vanilla Seeds")
            .define("XP", 100);
    public static final ForgeConfigSpec.ConfigValue<Integer> SEED_XP_TIER_1 = BUILDER
            .comment("Set XP Amount for Seed Tier 1")
            .define("Seed Tier 1", 100);
    public static final ForgeConfigSpec.ConfigValue<Integer> SEED_XP_TIER_2 = BUILDER
            .comment("Set XP Amount for Seed Tier 2")
            .define("Seed Tier 2", 125);
    public static final ForgeConfigSpec.ConfigValue<Integer> SEED_XP_TIER_3 = BUILDER
            .comment("Set XP Amount for Seed Tier 3")
            .define("Seed Tier 3", 150);
    public static final ForgeConfigSpec.ConfigValue<Integer> SEED_XP_TIER_4 = BUILDER
            .comment("Set XP Amount for Seed Tier 4")
            .define("Seed Tier 4", 300);
    public static final ForgeConfigSpec.ConfigValue<Integer> SEED_XP_TIER_5 = BUILDER
            .comment("Set XP Amount for Seed Tier 5")
            .define("Seed Tier 5", 350);
    public static final ForgeConfigSpec.ConfigValue<Integer> SEED_XP_TIER_6 = BUILDER
            .comment("Set XP Amount for Seed Tier 6")
            .define("Seed Tier 6", 500);
    public static final ForgeConfigSpec.ConfigValue<Integer> SEED_XP_TIER_7 = BUILDER
            .comment("Set XP Amount for Seed Tier 7")
            .define("Seed Tier 7", 700);
    public static final ForgeConfigSpec.ConfigValue<Integer> SEED_XP_TIER_8 = BUILDER
            .comment("Set XP Amount for Seed Tier 8")
            .define("Seed Tier 8", 1000);
    public static final ForgeConfigSpec.ConfigValue<Integer> SEED_XP_TIER_9 = BUILDER
            .comment("Set XP Amount for Seed Tier 9")
            .define("Seed Tier 9", 2500);

    static final ForgeConfigSpec SPEC = BUILDER.build();

}
