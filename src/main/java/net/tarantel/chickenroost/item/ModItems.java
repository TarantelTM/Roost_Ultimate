package net.tarantel.chickenroost.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.base.*;

public class ModItems {

	//public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChickenRoostMod.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChickenRoostMod.MODID);
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChickenRoostMod.MODID);

	public static final RegistryObject<Item> CHICKEN_BOOK = ITEMS.register("book", () -> new ChickenBook(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC)));
	///Ingots
	//public static final RegistryObject<Item> INGOT_BRASS = ITEMS.register("ingot_brass", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_ELECTRUM = ITEMS.register("ingot_electrum", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_SILVER = ITEMS.register("ingot_silver", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_ZINC = ITEMS.register("ingot_zinc", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> INGOT_BRONZE = ITEMS.register("ingot_bronze", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_LEAD = ITEMS.register("ingot_lead", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_STEEL = ITEMS.register("ingot_steel", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_TIN = ITEMS.register("ingot_tin", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_URANIUM = ITEMS.register("ingot_uranium", () -> new Item(new Item.Properties()));


	public static final RegistryObject<Item> INGOT_ALUMINUM = ITEMS.register("ingot_aluminum", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_CHROME = ITEMS.register("ingot_chrome", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> INGOT_INVAR = ITEMS.register("ingot_invar", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_IRIDIUM = ITEMS.register("ingot_iridium", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> INGOT_NICKEL = ITEMS.register("ingot_nickel", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_PLATINUM = ITEMS.register("ingot_platinum", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> INGOT_TITANUM = ITEMS.register("ingot_titanum", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_TUNGSTEN = ITEMS.register("ingot_tungsten", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_TUNGSTENSTEEL = ITEMS.register("ingot_tungstensteel", () -> new Item(new Item.Properties()));


	public static final RegistryObject<Item> INGOT_ENDERIUM = ITEMS.register("ingot_enderium", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_ADAMANTIUM = ITEMS.register("ingot_adamantium", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_LUMIUM = ITEMS.register("ingot_lumium", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_SIGNALUM = ITEMS.register("ingot_signalum", () -> new Item(new Item.Properties()));

	//region Chicken - Tier 1
	public static final RegistryObject<Item> E_CHICKEN_COBBLE = ITEMS.register("c_cobble", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "graychicken"));
	public static final RegistryObject<Item> E_CHICKEN_SAND = ITEMS.register("c_sand", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "sandchicken"));
	public static final RegistryObject<Item> E_CHICKEN_GRAVEL = ITEMS.register("c_gravel", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "graychicken"));
	public static final RegistryObject<Item> E_CHICKEN_GRANIT = ITEMS.register("c_granit", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "garnetchicken"));
	public static final RegistryObject<Item> E_CHICKEN_ANDESITE = ITEMS.register("c_andesite", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blackchicken"));
	public static final RegistryObject<Item> E_CHICKEN_BIRCHWOOD = ITEMS.register("c_birchwood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "whitechicken"));
	public static final RegistryObject<Item> E_CHICKEN_OAKWOOD = ITEMS.register("c_oakwood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken"));
	public static final RegistryObject<Item> ECHICKENQUEENSLIME = ITEMS.register("c_queenslime", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "yellowgarnetchicken"));
	public static final RegistryObject<Item> E_CHICKEN_TINTEDGLASS = ITEMS.register("c_tintedglass", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "glasschicken"));
	public static final RegistryObject<Item> ECHICKENFEATHER = ITEMS.register("c_feather", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ghastchicken"));
	public static final RegistryObject<Item> E_CHICKEN_PRISMARINE_SHARD = ITEMS.register("c_prismarineshard", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "saltchicken"));
	public static final RegistryObject<Item> E_CHICKEN_NETHER_BRICK = ITEMS.register("c_netherbrick", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken"));
	public static final RegistryObject<Item> E_CHICKEN_DARK_OAK = ITEMS.register("c_darkoak", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken"));
	public static final RegistryObject<Item> E_CHICKEN_ACACIAWOOD = ITEMS.register("c_acaciawood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken"));
	public static final RegistryObject<Item> E_CHICKEN_JUNGLEWOOD = ITEMS.register("c_junglewood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "pulsatingironchicken"));
	public static final RegistryObject<Item> E_CHICKEN_NAUTILUS_SHELL = ITEMS.register("c_nautilusshell", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "conductiveironchicken"));
	public static final RegistryObject<Item> E_CHICKEN_HONEYCOMB = ITEMS.register("c_honeycomb", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "yellowchicken"));
	public static final RegistryObject<Item> E_CHICKEN_DIORITE = ITEMS.register("c_diorite", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "iridiumchicken"));
	public static final RegistryObject<Item> E_CHICKEN_STONE = ITEMS.register("c_stone", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "stoneburntchicken"));
	public static final RegistryObject<Item> E_CHICKEN_AMETHYST_SHARD = ITEMS.register("c_amethystshard", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "amethystchicken"));
	public static final RegistryObject<Item> E_CHICKEN_WOOL = ITEMS.register("c_wool", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "enoricrystalchicken"));
	public static final RegistryObject<Item> E_CHICKEN_SPRUCEWOOD = ITEMS.register("c_sprucewood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken"));
	public static final RegistryObject<Item> CHICKENCHICKEN = ITEMS.register("c_vanilla", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "saltchicken"));
	//endregion

	//region Chicken - Tier 2
	public static final RegistryObject<Item> E_CHICKEN_FLINT = ITEMS.register("c_flint", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "flintchicken"));
	public static final RegistryObject<Item> E_CHICKEN_CRIMSTON_STEM = ITEMS.register("c_crimstonstem", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken"));
	public static final RegistryObject<Item> E_CHICKEN_WARPED_STEM = ITEMS.register("c_warpedstem", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken"));
	public static final RegistryObject<Item> E_CHICKEN_GLASS = ITEMS.register("c_glass", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "glasschicken"));
	public static final RegistryObject<Item> E_CHICKEN_NETHERRACK = ITEMS.register("c_netherrack", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "netherwartchicken"));
	public static final RegistryObject<Item> E_CHICKEN_INK = ITEMS.register("c_ink", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blackchicken"));
	public static final RegistryObject<Item> E_CHICKEN_PAPER = ITEMS.register("c_paper", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "platinumchicken"));
	public static final RegistryObject<Item> E_CHICKEN_SUGAR = ITEMS.register("c_sugar", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "enoricrystalchicken"));
	public static final RegistryObject<Item> E_CHICKEN_BONE_MEAL = ITEMS.register("c_bonemeal", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "whitechicken"));
	public static final RegistryObject<Item> E_CHICKEN_BONE = ITEMS.register("c_bone", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "whitechicken"));
	public static final RegistryObject<Item> E_CHICKEN_COAL = ITEMS.register("c_coal", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "coalchicken"));
	public static final RegistryObject<Item> E_CHICKEN_CHAR_COAL = ITEMS.register("c_charcoal", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "coalchicken"));
	public static final RegistryObject<Item> ECHICKENSNOW = ITEMS.register("c_snow", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "snowballchicken"));
	public static final RegistryObject<Item> ECHICKENAPPLE = ITEMS.register("c_apple", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "restoniacrystalchicken"));
	public static final RegistryObject<Item> E_CHICKEN_MELON = ITEMS.register("c_melon", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "greenchicken"));
	public static final RegistryObject<Item> E_CHICKENGLOWBERRIES = ITEMS.register("c_glowberries", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "glowstonechicken"));
	public static final RegistryObject<Item> E_CHICKEN_SWEETBERRIES = ITEMS.register("c_sweetberries", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "redstonecrystalchicken"));
	public static final RegistryObject<Item> E_CHICKENBEETROOT = ITEMS.register("c_beetroot", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "redstonealloychicken"));
	public static final RegistryObject<Item> E_CHICKENCARROT = ITEMS.register("c_carrot", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "energeticalloychicken"));
	//endregion

	//region Chicken - Tier 3

	public static final RegistryObject<Item> E_CHICKEN_COPPER = ITEMS.register("c_copper", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "copperchicken"));
	public static final RegistryObject<Item> E_CHICKEN_BOTANIA_LIVINGROCK = ITEMS.register("c_livingrock", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "moonstonechicken"));
	public static final RegistryObject<Item> E_CHICKEN_BOTANIA_LIVINGWOOD = ITEMS.register("c_livingwood", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "magicalwoodchicken"));
	public static final RegistryObject<Item> E_CHICKEN_SOUL_SAND = ITEMS.register("c_soulsand", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "soulsandchicken"));
	public static final RegistryObject<Item> E_CHICKEN_SOUL_SOIL = ITEMS.register("c_soulsoil", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "soulariumchicken"));
	public static final RegistryObject<Item> E_CHICKEN_BASALT = ITEMS.register("c_basalt", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "basalzrodchicken"));
	public static final RegistryObject<Item> E_CHICKEN_CLAY = ITEMS.register("c_clay", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "claychicken"));
	public static final RegistryObject<Item> E_CHICKEN_RABBIT_HIDE = ITEMS.register("c_rabbithide", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "bronzechicken"));
	public static final RegistryObject<Item> E_CHICKEN_LEATHER = ITEMS.register("c_leather", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "bronzechicken"));
	public static final RegistryObject<Item> E_CHICKEN_STRING = ITEMS.register("c_string", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "destructioncorechicken"));
	public static final RegistryObject<Item> ECHICKENSPONGE = ITEMS.register("c_sponge", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brasschicken"));
	public static final RegistryObject<Item> E_CHICKEN_SPIDEREYE = ITEMS.register("c_spidereye", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "redstonealloychicken"));
	//endregion

	//region Chicken - Tier 4
	public static final RegistryObject<Item> E_CHICKEN_IRON = ITEMS.register("c_iron", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ironchicken"));
	public static final RegistryObject<Item> E_CHICKEN_REDSTONE = ITEMS.register("c_redstone", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "redchicken"));
	public static final RegistryObject<Item> E_CHICKEN_LAPIS = ITEMS.register("c_lapis", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "lightbluechicken"));
	public static final RegistryObject<Item> E_CHICKEN_OBSIDIAN = ITEMS.register("c_obsidian", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "obsidianchicken"));
	public static final RegistryObject<Item> E_CHICKEN_SLIME = ITEMS.register("c_slime", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "slimechicken"));
	public static final RegistryObject<Item> E_CHICKEN_MEKANISM_TIN = ITEMS.register("c_tin", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "quartzenrichedironchicken"));
	public static final RegistryObject<Item> E_CHICKEN_MEKANISM_LEAD = ITEMS.register("c_lead", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "leadchicken"));
	public static final RegistryObject<Item> E_CHICKEN_NETHER_WART = ITEMS.register("c_netherwart", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "netherwartchicken"));
	public static final RegistryObject<Item> E_CHICKEN_MAGMACREAM = ITEMS.register("c_magmacream", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "magmachicken"));
	public static final RegistryObject<Item> E_CHICKEN_ROTTEN = ITEMS.register("c_rotten", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "richslagchicken"));
	public static final RegistryObject<Item> E_CHICKEN_ZINC = ITEMS.register("c_zinc", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "zincchicken"));
	public static final RegistryObject<Item> E_CHICKEN_ALUMINIUM = ITEMS.register("c_aluminium", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "aluminumchicken"));
	public static final RegistryObject<Item> ECHICKENNITER = ITEMS.register("c_niter", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "vinteumchicken"));
	public static final RegistryObject<Item> ECHICKENCINNABAR = ITEMS.register("c_cinnabar", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "cinnabarchicken"));
	public static final RegistryObject<Item> ECHICKENCOKE = ITEMS.register("c_coke", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "coalchicken"));
	public static final RegistryObject<Item> E_CHICKEN_TAR = ITEMS.register("c_tar", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blackquartzchicken"));
	public static final RegistryObject<Item> E_CHICKEN_SULFUR = ITEMS.register("c_sulfur", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "sulfurchicken"));
	public static final RegistryObject<Item> ECHICKENAPATITE = ITEMS.register("c_apatite", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "sapphirechicken"));
	public static final RegistryObject<Item> ECHICKENBASALZ = ITEMS.register("c_basalz", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "basalzrodchicken"));
	public static final RegistryObject<Item> ECHICKENBITUMEN = ITEMS.register("c_bitumen", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "coalchicken"));
	//endregion

	//region Chicken - Tier 5
	public static final RegistryObject<Item> E_CHICKEN_GOLD = ITEMS.register("c_gold", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "goldchicken"));
	public static final RegistryObject<Item> E_CHICKEN_AE_SILICON = ITEMS.register("c_silicon", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "siliconchicken"));
	public static final RegistryObject<Item> E_CHICKEN_BOTANIA_MANASTEEL = ITEMS.register("c_manasteel", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "manasteelchicken"));
	public static final RegistryObject<Item> E_CHICKEN_QUARTZ = ITEMS.register("c_quartz", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "quartzchicken"));
	public static final RegistryObject<Item> E_CHICKEN_TNT = ITEMS.register("c_tnt", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "gunpowderchicken"));
	public static final RegistryObject<Item> E_CHICKEN_GLOWSTONE = ITEMS.register("c_glowstone", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "glowstonechicken"));
	public static final RegistryObject<Item> E_CHICKEN_BLAZE_ROD = ITEMS.register("c_blazerod", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blazechicken"));
	public static final RegistryObject<Item> E_CHICKEN_ENDER_PEARL = ITEMS.register("c_enderpearl", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "enderchicken"));
	public static final RegistryObject<Item> E_CHICKEN_CHORUS_FRUIT = ITEMS.register("c_chorusfruit", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "magentachicken"));
	public static final RegistryObject<Item> E_CHICKENBLAZEPOWDER = ITEMS.register("c_blazepowder", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blazechicken"));
	public static final RegistryObject<Item> E_CHICKEN_SILVER = ITEMS.register("c_silver", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "silverorechicken"));
	public static final RegistryObject<Item> E_CHICKEN_CHROME = ITEMS.register("c_chrome", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "chromechicken"));
	public static final RegistryObject<Item> E_CHICKEN_REFINED_IRON = ITEMS.register("c_refinediron", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "refinedironchicken"));
	public static final RegistryObject<Item> E_CHICKEN_ENDSTONE = ITEMS.register("c_endstone", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "bulletchicken"));
	public static final RegistryObject<Item> E_CHICKEN_SIGNALUM = ITEMS.register("c_signalum", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "signalumchicken"));
	public static final RegistryObject<Item> ECHICKENLUMIUM = ITEMS.register("c_lumium", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "lumiumchicken"));
	public static final RegistryObject<Item> ECHICKENCONSTANTAN = ITEMS.register("c_constantan", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "constantanchicken"));
	public static final RegistryObject<Item> ECHICKENENDERIUM = ITEMS.register("c_enderium", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "enderiumchicken"));
	public static final RegistryObject<Item> ECHICKENQUARTZENRICHEDIRON = ITEMS.register("c_quartzenrichediron", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "quartzenrichedironchicken"));
	public static final RegistryObject<Item> ECHICKENBLITZ = ITEMS.register("c_blitz", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blitzrodchicken"));
	public static final RegistryObject<Item> ECHICKENBLIZZ = ITEMS.register("c_blizz", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blizzrodchicken"));
	//endregion

	//region Chicken - Tier 6
	public static final RegistryObject<Item> E_OSMIUM_CHICKEN = ITEMS.register("c_osmium", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "osmiumchicken"));
	public static final RegistryObject<Item> E_CHICKEN_CERTUSQ = ITEMS.register("c_certusquartz", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "quartzenrichedironchicken"));
	public static final RegistryObject<Item> E_CHICKEN_MEKANISM_BRONZE = ITEMS.register("c_bronze", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "bronzechicken"));
	public static final RegistryObject<Item> E_CHICKEN_MEKANISM_STEEL = ITEMS.register("c_steel", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "steelchicken"));
	public static final RegistryObject<Item> E_CHICKEN_MEKANISM_URANIUM = ITEMS.register("c_uranium", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "uraniumchicken"));
	public static final RegistryObject<Item> E_CHICKEN_MEKANISM_BIO_FUEL = ITEMS.register("c_biofuel", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "enderiumchicken"));
	public static final RegistryObject<Item> E_CHICKEN_EMERALD = ITEMS.register("c_emerald", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "emeradiccrystalchicken"));
	public static final RegistryObject<Item> E_CHICKEN_ENDER_EYE = ITEMS.register("c_endereye", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "enderchicken"));
	public static final RegistryObject<Item> E_CHICKEN_GHASTTEAR = ITEMS.register("c_ghasttear", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ghastchicken"));
	public static final RegistryObject<Item> E_CHICKEN_BRASS = ITEMS.register("c_brass", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brasschicken"));
	public static final RegistryObject<Item> ECHICKENAMETHYSTBRONZE = ITEMS.register("c_amethystbronze", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "purplechicken"));
	public static final RegistryObject<Item> ECHICKENSLIMESTEEL = ITEMS.register("c_slimesteel", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "sapphirechicken"));
	public static final RegistryObject<Item> ECHICKENROSEGOLD = ITEMS.register("c_rosegold", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "pinkslimechicken"));
	public static final RegistryObject<Item> ECHICKENHEPATIZON = ITEMS.register("c_hepatizon", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "tanzanitechicken"));
	public static final RegistryObject<Item> ECHICKENKNIGHTSLIME = ITEMS.register("c_knightslime", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "knightslimechicken"));
	public static final RegistryObject<Item> E_CHICKEN_TUNGSTEN = ITEMS.register("c_tungsten", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "tungstenchicken"));
	public static final RegistryObject<Item> E_CHICKEN_YELLORIUM = ITEMS.register("c_yellorium", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "yelloriumchicken"));
	public static final RegistryObject<Item> ECHICKENPIGIRON = ITEMS.register("c_pigiron", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "pigironchicken"));
	public static final RegistryObject<Item> ECHICKENRUBY = ITEMS.register("c_ruby", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "rubychicken"));
	public static final RegistryObject<Item> ECHICKENSAPPHIRE = ITEMS.register("c_sapphire", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "sapphirechicken"));
	//endregion

	//region Chicken - Tier 7
	public static final RegistryObject<Item> E_CHICKEN_DIAMOND = ITEMS.register("c_diamond", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "diamondchicken"));
	public static final RegistryObject<Item> E_CHICKEN_AE_FLUIX_CRYSTAL = ITEMS.register("c_fluixcrystal", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "lunarreactivedustchicken"));
	public static final RegistryObject<Item> E_CHICKEN_AE_CHARGED_CERTUS = ITEMS.register("c_chargedcertus", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "quartzchicken"));
	public static final RegistryObject<Item> E_CHICKEN_BOTANIA_TERRASTEEL = ITEMS.register("c_terrasteel", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "terrasteelchicken"));
	public static final RegistryObject<Item> E_CHICKEN_BOTANIA_ELEMENTIUM = ITEMS.register("c_elementium", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "elementiumchicken"));
	public static final RegistryObject<Item> E_CHICKEN_NETHER_STAR = ITEMS.register("c_netherstar", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blitzrodchicken"));
	public static final RegistryObject<Item> E_CHICKEN_NETHERITE = ITEMS.register("c_netherite", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "netherwartchicken"));
	public static final RegistryObject<Item> E_CHICKEN_NICKEL = ITEMS.register("c_nickel", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "nickelchicken"));
	public static final RegistryObject<Item> E_CHICKEN_ELECTRUM = ITEMS.register("c_electrum", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "electrumchicken"));
	public static final RegistryObject<Item> E_CHICKEN_HOT_TUNGSTEN_STEEL = ITEMS.register("c_hottungstensteel", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "silverorechicken"));
	public static final RegistryObject<Item> E_CHICKEN_INVAR = ITEMS.register("c_invar", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "invarchicken"));
	public static final RegistryObject<Item> E_CHICKEN_BLUTONIUM = ITEMS.register("c_blutonium", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blutoniumchicken"));
	public static final RegistryObject<Item> E_CHICKEN_ALLTHEMODIUM = ITEMS.register("c_allthemodium", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "energeticalloychicken"));
	public static final RegistryObject<Item> E_CHICKEN_TUNGSTENSTEEL = ITEMS.register("c_tungstensteel", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "tungstensteelchicken"));
	public static final RegistryObject<Item> ECHICKENCOBALD = ITEMS.register("c_cobald", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "cobaltchicken"));
	public static final RegistryObject<Item> ECHICKENMANYULLYN = ITEMS.register("c_manyullyn", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "manyullynchicken"));
	//endregion

	//region Chicken - Tier 8
	public static final RegistryObject<Item> E_CHICKEN_ADAMANTIUM = ITEMS.register("c_adamantium", () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "emeradiccrystalchicken"));
	public static final RegistryObject<Item> E_CHICKEN_IRIDIUM = ITEMS.register("c_iridium", () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "iridiumchicken"));
	public static final RegistryObject<Item> E_CHICKEN_PLATINUM = ITEMS.register("c_platinum", () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "platinumchicken"));
	public static final RegistryObject<Item> E_CHICKEN_VIBRANIUM = ITEMS.register("c_vibranium", () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "vibrantalloychicken"));
	//endregion

	//region Chicken - Tier 9
	public static final RegistryObject<Item> E_CHICKEN_TITANIUM = ITEMS.register("c_titanium", () -> new AnimatedChicken_9(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "titaniumchicken"));
	public static final RegistryObject<Item> E_CHICKEN_UNOBTAINIUM = ITEMS.register("c_unobtainium", () -> new AnimatedChicken_9(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "vinteumchicken"));
	//endregion

	//region Tools
	public static final RegistryObject<Item> CHICKEN_SCANNER = ITEMS.register("chicken_scanner", () -> new ChickenScannerItem());
	public static final RegistryObject<Item> CHICKEN_STICK = ITEMS.register("chicken_stick", () -> new AnimatedChickenStick(new Item.Properties()));
	//endregion

	//region Essence
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_1 = ITEMS.register("chicken_essence_tier_1", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_2 = ITEMS.register("chicken_essence_tier_2", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_3 = ITEMS.register("chicken_essence_tier_3", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_4 = ITEMS.register("chicken_essence_tier_4", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_5 = ITEMS.register("chicken_essence_tier_5", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_6 = ITEMS.register("chicken_essence_tier_6", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_7 = ITEMS.register("chicken_essence_tier_7", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_8 = ITEMS.register("chicken_essence_tier_8", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_9 = ITEMS.register("chicken_essence_tier_9", () -> new Essence_Soul());
	//endregion


	//region SEEDS
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_1 = ITEMS.register("chicken_food_tier_1", () -> new ModItemNameBlockItem_1(ModBlocks.SEED_CROP_1.get(), new Item.Properties()));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_2 = ITEMS.register("chicken_food_tier_2", () -> new ModItemNameBlockItem_2(ModBlocks.SEED_CROP_2.get(), new Item.Properties()));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_3 = ITEMS.register("chicken_food_tier_3", () -> new ModItemNameBlockItem_3(ModBlocks.SEED_CROP_3.get(), new Item.Properties()));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_4 = ITEMS.register("chicken_food_tier_4", () -> new ModItemNameBlockItem_4(ModBlocks.SEED_CROP_4.get(), new Item.Properties()));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_5 = ITEMS.register("chicken_food_tier_5", () -> new ModItemNameBlockItem_5(ModBlocks.SEED_CROP_5.get(), new Item.Properties()));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_6 = ITEMS.register("chicken_food_tier_6", () -> new ModItemNameBlockItem_6(ModBlocks.SEED_CROP_6.get(), new Item.Properties()));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_7 = ITEMS.register("chicken_food_tier_7", () -> new ModItemNameBlockItem_7(ModBlocks.SEED_CROP_7.get(), new Item.Properties()));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_8 = ITEMS.register("chicken_food_tier_8", () -> new ModItemNameBlockItem_8(ModBlocks.SEED_CROP_8.get(), new Item.Properties()));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_9 = ITEMS.register("chicken_food_tier_9", () -> new ModItemNameBlockItem_9(ModBlocks.SEED_CROP_9.get(), new Item.Properties()));
	//endregion

	public static final RegistryObject<BlockItem> SOUL_BREEDER = ITEMS.register("soul_breeder",
			() -> new AnimatedSoulBreederBlockItem(ModBlocks.SOUL_BREEDER.get(),
					new Item.Properties()));
	public static final RegistryObject<BlockItem> TRAINER = ITEMS.register("trainer",
			() -> new AnimatedTrainerBlockItem(ModBlocks.TRAINER.get(),
					new Item.Properties()));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
