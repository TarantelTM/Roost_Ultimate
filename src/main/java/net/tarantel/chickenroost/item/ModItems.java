package net.tarantel.chickenroost.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.ModBlocks;

public class ModItems {

    public static final Item CHICKEN_STICK = registerItem("chicken_stick", new ChickenStickItem(new FabricItemSettings()));

    //public static final Item CHICKEN_SCANNER = registerItem("chicken_scanner", new ChickenScannerItem(new FabricItemSettings()));




    /////SEEDS


    /////TIER 1
    public static final Item C_COBBLE = registerItem("c_cobble", new chicken_tier_1(new FabricItemSettings())); //O
    //public static final Item EGG_C_COBBLE = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_OAKWOOD = registerItem("c_oakwood", new chicken_tier_1(new FabricItemSettings()));//O
    //////public static final Item EGG_C_OAKWOOD = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_ANDESITE = registerItem("c_andesite", new chicken_tier_1(new FabricItemSettings()));//X
    //////public static final Item EGG_C_ANDESITE = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_SAND = registerItem("c_sand", new chicken_tier_1(new FabricItemSettings()));//O
    ///public static final Item EGG_C_SAND = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_VANILLA = registerItem("c_vanilla", new chicken_tier_1(new FabricItemSettings()));//O
    ///public static final Item EGG_C_VANILLA = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_GRAVEL = registerItem("c_gravel", new chicken_tier_1(new FabricItemSettings()));//O
    ///public static final Item EGG_C_GRAVEL = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_DARKOAKWOOD = registerItem("c_darkoakwood", new chicken_tier_1(new FabricItemSettings()));//O
    /////public static final Item EGG_C_DARKOAKWOOD = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_GRANIT = registerItem("c_granit", new chicken_tier_1(new FabricItemSettings()));//O
    ///public static final Item EGG_C_GRANIT = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_BIRCHWOOD = registerItem("c_birchwood", new chicken_tier_1(new FabricItemSettings()));//O
    /////public static final Item EGG_C_BIRCHWOOD = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_SPRUCEWOOD = registerItem("c_sprucewood", new chicken_tier_1(new FabricItemSettings()));//O
    /////public static final Item EGG_C_SPRUCEWOOD = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_HONEYCOMB = registerItem("c_honeycomb", new chicken_tier_1(new FabricItemSettings()));//O
    ///public static final Item EGG_C_HONEYCOMB = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_FEATHER = registerItem("c_feather", new chicken_tier_1(new FabricItemSettings()));//O
    ///public static final Item EGG_C_FEATHER = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_WOOL = registerItem("c_wool", new chicken_tier_1(new FabricItemSettings()));//O
    ///public static final Item EGG_C_WOOL = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_ACACIAWOOD = registerItem("c_acaciawood", new chicken_tier_1(new FabricItemSettings()));//X
    /////public static final Item EGG_C_ACACIAWOOD = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_STONE = registerItem("c_stone", new chicken_tier_1(new FabricItemSettings()));//O
    ///public static final Item EGG_C_STONE = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_DIORITE = registerItem("c_diorite", new chicken_tier_1(new FabricItemSettings()));//O
    ///public static final Item EGG_C_DIORITE = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_JUNGLEWOOD = registerItem("c_junglewood", new chicken_tier_1(new FabricItemSettings()));//O
    /////public static final Item EGG_C_JUNGLEWOOD = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));


    /////TIER 2

    public static final Item C_MELON = registerItem("c_melon", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_MELON = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_WARPEDSTEM = registerItem("c_warpedstem", new chicken_tier_2(new FabricItemSettings()));//O
    /////public static final Item EGG_C_WARPEDSTEM = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_NETHERRACK = registerItem("c_netherrack", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_NETHERRACK = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_SNOW = registerItem("c_snow", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_SNOW = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_GLASS = registerItem("c_glass", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_GLASS = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_SUGAR = registerItem("c_sugar", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_SUGAR = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_CRIMSONSTEM = registerItem("c_crimsonstem", new chicken_tier_2(new FabricItemSettings()));//O
    /////public static final Item EGG_C_CRIMSONSTEM = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_FLINT = registerItem("c_flint", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_FLINT = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_APPLE = registerItem("c_apple", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_APPLE = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_BONE = registerItem("c_bone", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_BONE = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_COAL = registerItem("c_coal", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_COAL = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_CARROT = registerItem("c_carrot", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_CARROT = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_INK = registerItem("c_ink", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_INK = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_BEETROOT = registerItem("c_beetroot", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_BEETROOT = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_SWEETBERRIES = registerItem("c_sweetberries", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_SWEETBERRIES = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_GLOWBERRIES = registerItem("c_glowberries", new chicken_tier_2(new FabricItemSettings()));//O
    ///public static final Item EGG_C_GLOWBERRIES = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    /////TIER 3
    public static final Item C_SOULSOIL = registerItem("c_soulsoil", new chicken_tier_3(new FabricItemSettings()));//O
    ///public static final Item EGG_C_SOULSOIL = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));
    public static final Item C_STRING = registerItem("c_string", new chicken_tier_3(new FabricItemSettings()));//O
    ///public static final Item EGG_C_STRING = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_BASALT = registerItem("c_basalt", new chicken_tier_3(new FabricItemSettings()));//X
    ///public static final Item EGG_C_BASALT = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_COPPER = registerItem("c_copper", new chicken_tier_3(new FabricItemSettings()));//O
    ///public static final Item EGG_C_COPPER = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_CLAY = registerItem("c_clay", new chicken_tier_3(new FabricItemSettings()));//O
    ///public static final Item EGG_C_CLAY = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_SOULSAND = registerItem("c_soulsand", new chicken_tier_3(new FabricItemSettings()));//O
    ///public static final Item EGG_C_SOULSAND = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_SPONGE = registerItem("c_sponge", new chicken_tier_3(new FabricItemSettings()));//O
    ///public static final Item EGG_C_SPONGE = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_LEATHER = registerItem("c_leather", new chicken_tier_3(new FabricItemSettings()));//O
    ///public static final Item EGG_C_LEATHER = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    /////TIER 4
    public static final Item C_NETHERWART = registerItem("c_netherwart", new chicken_tier_4(new FabricItemSettings()));//O
    ///public static final Item EGG_C_NETHERWART = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_REDSTONE = registerItem("c_redstone", new chicken_tier_4(new FabricItemSettings()));//O
    ///public static final Item EGG_C_REDSTONE = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_LAPIS = registerItem("c_lapis", new chicken_tier_4(new FabricItemSettings()));//O
    ///public static final Item EGG_C_LAPIS = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_OBSIDIAN = registerItem("c_obsidian", new chicken_tier_4(new FabricItemSettings()));//O
    ///public static final Item EGG_C_OBSIDIAN = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_MAGMACREAM = registerItem("c_magmacream", new chicken_tier_4(new FabricItemSettings()));//O
    ///public static final Item EGG_C_MAGMACREAM = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_IRON = registerItem("c_iron", new chicken_tier_4(new FabricItemSettings()));//O
    ///public static final Item EGG_C_IRON = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_ROTTEN = registerItem("c_rotten", new chicken_tier_4(new FabricItemSettings()));//O
    ///public static final Item EGG_C_ROTTEN = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_SLIME = registerItem("c_slime", new chicken_tier_4(new FabricItemSettings()));//O
    ///public static final Item EGG_C_SLIME = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    /////TIER 5
    public static final Item C_CHORUSFRUIT = registerItem("c_chorusfruit", new chicken_tier_5(new FabricItemSettings()));//O
    ///public static final Item EGG_C_CHORUSFRUIT = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_GLOWSTONE = registerItem("c_glowstone", new chicken_tier_5(new FabricItemSettings()));//O
    ///public static final Item EGG_C_GLOWSTONE = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_ENDSTONE = registerItem("c_endstone", new chicken_tier_5(new FabricItemSettings()));//X
    ///public static final Item EGG_C_ENDSTONE = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_GOLD = registerItem("c_gold", new chicken_tier_5(new FabricItemSettings()));//O
    ///public static final Item EGG_C_GOLD = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_BLAZEROD = registerItem("c_blazerod", new chicken_tier_5(new FabricItemSettings()));//O
    ///public static final Item EGG_C_BLAZEROD = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_NETHERQUARTZ = registerItem("c_netherquartz", new chicken_tier_5(new FabricItemSettings()));//O
    ///public static final Item EGG_C_NETHERQUARTZ = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_TNT = registerItem("c_tnt", new chicken_tier_5(new FabricItemSettings()));//O
    ///public static final Item EGG_C_TNT = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_ENDERPEARL = registerItem("c_enderpearl", new chicken_tier_5(new FabricItemSettings()));//O
    ///public static final Item EGG_C_ENDERPEARL = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));


    /////TIER 6
    public static final Item C_EMERALD = registerItem("c_emerald", new chicken_tier_6(new FabricItemSettings()));//O
    ///public static final Item EGG_C_EMERALD = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_GHASTTEAR = registerItem("c_ghasttear", new chicken_tier_6(new FabricItemSettings()));//O
    ///public static final Item EGG_C_GHASTTEAR = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    /////TIER 7
    public static final Item C_DIAMOND = registerItem("c_diamond", new chicken_tier_7(new FabricItemSettings()));//O
    ///public static final Item EGG_C_DIAMOND = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_NETHERITE = registerItem("c_netherite", new chicken_tier_7(new FabricItemSettings()));//O
    ///public static final Item EGG_C_NETHERITE = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));

    public static final Item C_NETHERSTAR = registerItem("c_netherstar", new chicken_tier_7(new FabricItemSettings()));//O
    ///public static final Item EGG_C_NETHERSTAR = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));



    public static final Item CHICKEN_ESSENCE_TIER_1 = registerItem("chicken_essence_tier_1",
            new Chickenessence1Item(new FabricItemSettings()));
    public static final Item CHICKEN_ESSENCE_TIER_2 = registerItem("chicken_essence_tier_2",
            new ChickenEssenceTier2Item(new FabricItemSettings()));
    public static final Item CHICKEN_ESSENCE_TIER_3 = registerItem("chicken_essence_tier_3",
            new ChickenEssenceTier3Item(new FabricItemSettings()));
    public static final Item CHICKEN_ESSENCE_TIER_4 = registerItem("chicken_essence_tier_4",
            new ChickenEssenceTier4Item(new FabricItemSettings()));
    public static final Item CHICKEN_ESSENCE_TIER_5 = registerItem("chicken_essence_tier_5",
            new ChickenEssenceTier5Item(new FabricItemSettings()));
    public static final Item CHICKEN_ESSENCE_TIER_6 = registerItem("chicken_essence_tier_6",
            new ChickenEssenceTier6Item(new FabricItemSettings()));
    public static final Item CHICKEN_ESSENCE_TIER_7 = registerItem("chicken_essence_tier_7",
            new ChickenEssenceTier7Item(new FabricItemSettings()));
    public static final Item CHICKEN_ESSENCE_TIER_8 = registerItem("chicken_essence_tier_8",
            new ChickenEssenceTier8Item(new FabricItemSettings()));
    public static final Item CHICKEN_ESSENCE_TIER_9 = registerItem("chicken_essence_tier_9",
            new ChickenEssenceTier9Item(new FabricItemSettings()));

    /////SEEDS
    public static final Item CHICKEN_FOOD_TIER_1 = registerItem("chicken_food_tier_1", new AliasedBlockItem(ModBlocks.CROPBLOCK_1,new FabricItemSettings().maxCount(64).rarity(Rarity.COMMON)));
    public static final Item CHICKEN_FOOD_TIER_2 = registerItem("chicken_food_tier_2", new AliasedBlockItem(ModBlocks.CROPBLOCK_2,new FabricItemSettings().maxCount(64).rarity(Rarity.COMMON)));
    public static final Item CHICKEN_FOOD_TIER_3 = registerItem("chicken_food_tier_3", new AliasedBlockItem(ModBlocks.CROPBLOCK_3,new FabricItemSettings().maxCount(64).rarity(Rarity.COMMON)));
    public static final Item CHICKEN_FOOD_TIER_4 = registerItem("chicken_food_tier_4", new AliasedBlockItem(ModBlocks.CROPBLOCK_4,new FabricItemSettings().maxCount(64).rarity(Rarity.COMMON)));
    public static final Item CHICKEN_FOOD_TIER_5 = registerItem("chicken_food_tier_5", new AliasedBlockItem(ModBlocks.CROPBLOCK_5,new FabricItemSettings().maxCount(64).rarity(Rarity.COMMON)));
    public static final Item CHICKEN_FOOD_TIER_6 = registerItem("chicken_food_tier_6", new AliasedBlockItem(ModBlocks.CROPBLOCK_6,new FabricItemSettings().maxCount(64).rarity(Rarity.COMMON)));
    public static final Item CHICKEN_FOOD_TIER_7 = registerItem("chicken_food_tier_7", new AliasedBlockItem(ModBlocks.CROPBLOCK_7,new FabricItemSettings().maxCount(64).rarity(Rarity.COMMON)));
    public static final Item CHICKEN_FOOD_TIER_8 = registerItem("chicken_food_tier_8", new AliasedBlockItem(ModBlocks.CROPBLOCK_8,new FabricItemSettings().maxCount(64).rarity(Rarity.COMMON)));
    public static final Item CHICKEN_FOOD_TIER_9 = registerItem("chicken_food_tier_9", new AliasedBlockItem(ModBlocks.CROPBLOCK_9,new FabricItemSettings().maxCount(64).rarity(Rarity.COMMON)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ChickenRoostMod.MODID, name), item);
    }


    /*public static void addItemsToItemGroups() {
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_STICK);
        addToItemGroup(ModItemGroup.BLOCKS, C_COBBLE);
        addToItemGroup(ModItemGroup.BLOCKS, C_OAKWOOD);
        addToItemGroup(ModItemGroup.BLOCKS, C_ANDESITE);
        addToItemGroup(ModItemGroup.BLOCKS, C_SAND);
        addToItemGroup(ModItemGroup.BLOCKS, C_VANILLA);
        addToItemGroup(ModItemGroup.BLOCKS, C_GRAVEL);
        addToItemGroup(ModItemGroup.BLOCKS, C_DARKOAKWOOD);
        addToItemGroup(ModItemGroup.BLOCKS, C_GRANIT);
        addToItemGroup(ModItemGroup.BLOCKS, C_BIRCHWOOD);
        addToItemGroup(ModItemGroup.BLOCKS, C_SPRUCEWOOD);
        addToItemGroup(ModItemGroup.BLOCKS, C_HONEYCOMB);
        addToItemGroup(ModItemGroup.BLOCKS, C_FEATHER);
        addToItemGroup(ModItemGroup.BLOCKS, C_WOOL);
        addToItemGroup(ModItemGroup.BLOCKS, C_ACACIAWOOD);
        addToItemGroup(ModItemGroup.BLOCKS, C_STONE);
        addToItemGroup(ModItemGroup.BLOCKS, C_DIORITE);
        addToItemGroup(ModItemGroup.BLOCKS, C_JUNGLEWOOD);
        addToItemGroup(ModItemGroup.BLOCKS, C_MELON);
        addToItemGroup(ModItemGroup.BLOCKS, C_WARPEDSTEM);
        addToItemGroup(ModItemGroup.BLOCKS, C_NETHERRACK);
        addToItemGroup(ModItemGroup.BLOCKS, C_SNOW);
        addToItemGroup(ModItemGroup.BLOCKS, C_GLASS);
        addToItemGroup(ModItemGroup.BLOCKS, C_SUGAR);
        addToItemGroup(ModItemGroup.BLOCKS, C_CRIMSONSTEM);
        addToItemGroup(ModItemGroup.BLOCKS, C_FLINT);
        addToItemGroup(ModItemGroup.BLOCKS, C_APPLE);
        addToItemGroup(ModItemGroup.BLOCKS, C_BONE);
        addToItemGroup(ModItemGroup.BLOCKS, C_COAL);
        addToItemGroup(ModItemGroup.BLOCKS, C_CARROT);
        addToItemGroup(ModItemGroup.BLOCKS, C_INK);
        addToItemGroup(ModItemGroup.BLOCKS, C_BEETROOT);
        addToItemGroup(ModItemGroup.BLOCKS, C_SWEETBERRIES);
        addToItemGroup(ModItemGroup.BLOCKS, C_GLOWBERRIES);
        addToItemGroup(ModItemGroup.BLOCKS, C_SOULSOIL);
        addToItemGroup(ModItemGroup.BLOCKS, C_STRING);
        addToItemGroup(ModItemGroup.BLOCKS, C_BASALT);
        addToItemGroup(ModItemGroup.BLOCKS, C_COPPER);
        addToItemGroup(ModItemGroup.BLOCKS, C_CLAY);
        addToItemGroup(ModItemGroup.BLOCKS, C_SOULSAND);
        addToItemGroup(ModItemGroup.BLOCKS, C_SPONGE);
        addToItemGroup(ModItemGroup.BLOCKS, C_LEATHER);
        addToItemGroup(ModItemGroup.BLOCKS, C_NETHERWART);
        addToItemGroup(ModItemGroup.BLOCKS, C_REDSTONE);
        addToItemGroup(ModItemGroup.BLOCKS, C_LAPIS);
        addToItemGroup(ModItemGroup.BLOCKS, C_OBSIDIAN);
        addToItemGroup(ModItemGroup.BLOCKS, C_MAGMACREAM);
        addToItemGroup(ModItemGroup.BLOCKS, C_IRON);
        addToItemGroup(ModItemGroup.BLOCKS, C_ROTTEN);
        addToItemGroup(ModItemGroup.BLOCKS, C_SLIME);
        addToItemGroup(ModItemGroup.BLOCKS, C_CHORUSFRUIT);
        addToItemGroup(ModItemGroup.BLOCKS, C_GLOWSTONE);
        addToItemGroup(ModItemGroup.BLOCKS, C_ENDSTONE);
        addToItemGroup(ModItemGroup.BLOCKS, C_GOLD);
        addToItemGroup(ModItemGroup.BLOCKS, C_BLAZEROD);
        addToItemGroup(ModItemGroup.BLOCKS, C_NETHERQUARTZ);
        addToItemGroup(ModItemGroup.BLOCKS, C_TNT);
        addToItemGroup(ModItemGroup.BLOCKS, C_ENDERPEARL);
        addToItemGroup(ModItemGroup.BLOCKS, C_EMERALD);
        addToItemGroup(ModItemGroup.BLOCKS, C_GHASTTEAR);
        addToItemGroup(ModItemGroup.BLOCKS, C_DIAMOND);
        addToItemGroup(ModItemGroup.BLOCKS, C_NETHERITE);
        addToItemGroup(ModItemGroup.BLOCKS, C_NETHERSTAR);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_ESSENCE_TIER_1);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_ESSENCE_TIER_2);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_ESSENCE_TIER_3);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_ESSENCE_TIER_4);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_ESSENCE_TIER_5);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_ESSENCE_TIER_6);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_ESSENCE_TIER_7);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_ESSENCE_TIER_8);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_ESSENCE_TIER_9);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_FOOD_TIER_1);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_FOOD_TIER_2);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_FOOD_TIER_3);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_FOOD_TIER_4);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_FOOD_TIER_5);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_FOOD_TIER_6);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_FOOD_TIER_7);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_FOOD_TIER_8);
        addToItemGroup(ModItemGroup.BLOCKS, CHICKEN_FOOD_TIER_9);

    }*/

   /* public static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries ->
                entries.add(item));
    }*/

    public static void registerModItems() {
        ChickenRoostMod.LOGGER.debug("Registering Mod Items for " + ChickenRoostMod.MODID);
    }
}