package net.tarantel.chickenroost.item;

import com.google.gson.Gson;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.base.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;

public class ModItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ChickenRoostMod.MODID);
	public static final DeferredRegister<Item> ITEMSS = DeferredRegister.create(ForgeRegistries.ITEMS, ChickenRoostMod.MODID);
	///Ingots
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

	public static final RegistryObject<Item> LAVA_EGG = ITEMS.register("lava_egg", () -> new myownbucket( new myownbucket.Properties()
			.durability(0)
			.upperCrackingTemperature(0)
			.burningTemperature(99999)
			.freezingTemperature(0)
			.stacksTo(64)
			.disableBlockObtaining()
			.disableMilking()
			.disableEntityObtaining()
			.lowerCrackingTemperature(0)
	));
	public static final RegistryObject<Item> WATER_EGG = ITEMS.register("water_egg", () -> new myownbucket( new myownbucket.Properties()
			.durability(0)
			.upperCrackingTemperature(0)
			.burningTemperature(99999)
			.freezingTemperature(0)
			.stacksTo(64)
			.disableBlockObtaining()
			.disableMilking()
			.disableEntityObtaining()
			.lowerCrackingTemperature(0)
	));
	public static final RegistryObject<Item> STONE_ESSENCE = ITEMS.register("stone_essence", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> WOOD_ESSENCE = ITEMS.register("wood_essence", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	//region Color Eggs
	public static final RegistryObject<Item> RED_EGG = ITEMS.register("red_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_red"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_RED = ITEMS.register("c_red", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "redchicken", 0));
	public static final RegistryObject<Item> BLUE_EGG = ITEMS.register("blue_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_lapis"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> YELLOW_EGG = ITEMS.register("yellow_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_yellow"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_YELLOW = ITEMS.register("c_yellow", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "yellowchicken", 0));
	public static final RegistryObject<Item> WHITE_EGG = ITEMS.register("white_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_white"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_WHITE = ITEMS.register("c_white", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "whitechicken", 0));
	public static final RegistryObject<Item> ORANGE_EGG = ITEMS.register("orange_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_orange"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_ORANGE = ITEMS.register("c_orange", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "orangechicken", 0));
	public static final RegistryObject<Item> MAGENTA_EGG = ITEMS.register("magenta_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_magenta"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_MAGENTA = ITEMS.register("c_magenta", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "magentachicken", 0));
	public static final RegistryObject<Item> LIGHT_BLUE_EGG = ITEMS.register("light_blue_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_light_blue"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_LIGHT_BLUE = ITEMS.register("c_light_blue", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "lightbluechicken", 0));
	public static final RegistryObject<Item> LIME_EGG = ITEMS.register("lime_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_lime"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_LIME = ITEMS.register("c_lime", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "limechicken", 0));
	public static final RegistryObject<Item> PINK_EGG = ITEMS.register("pink_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_pink"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_PINK = ITEMS.register("c_pink", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "pinkchicken", 0));
	public static final RegistryObject<Item> GRAY_EGG = ITEMS.register("gray_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_gray"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_GRAY = ITEMS.register("c_gray", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "graychicken", 0));
	public static final RegistryObject<Item> LIGHT_GRAY_EGG = ITEMS.register("light_gray_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_light_gray"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_LIGHT_GRAY = ITEMS.register("c_light_gray", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "graychicken", 0));
	public static final RegistryObject<Item> CYAN_EGG = ITEMS.register("cyan_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_cyan"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_CYAN = ITEMS.register("c_cyan", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "cyanchicken", 0));
	public static final RegistryObject<Item> PURPLE_EGG = ITEMS.register("purple_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_purple"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_PURPLE = ITEMS.register("c_purple", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "purplechicken", 0));
	public static final RegistryObject<Item> BROWN_EGG = ITEMS.register("brown_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_brown"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_BROWN = ITEMS.register("c_brown", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken", 0));
	public static final RegistryObject<Item> GREEN_EGG = ITEMS.register("green_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_green"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_GREEN = ITEMS.register("c_green", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "greenchicken", 0));
	public static final RegistryObject<Item> BLACK_EGG = ITEMS.register("black_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_black"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> E_CHICKEN_BLACK = ITEMS.register("c_black", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blackchicken", 0));

	//endregion


	//region Chicken - Tier 1
	public static final RegistryObject<Item> E_CHICKEN_COBBLE = ITEMS.register("c_cobble", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "graychicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_SAND = ITEMS.register("c_sand", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "sandchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_GRAVEL = ITEMS.register("c_gravel", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "graychicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_GRANIT = ITEMS.register("c_granit", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "garnetchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_ANDESITE = ITEMS.register("c_andesite", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blackchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_BIRCHWOOD = ITEMS.register("c_birchwood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "whitechicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_OAKWOOD = ITEMS.register("c_oakwood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken", 0));
	public static final RegistryObject<Item> ECHICKENQUEENSLIME = ITEMS.register("c_queenslime", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "yellowgarnetchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_TINTEDGLASS = ITEMS.register("c_tintedglass", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "glasschicken", 0));
	public static final RegistryObject<Item> ECHICKENFEATHER = ITEMS.register("c_feather", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ghastchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_PRISMARINE_SHARD = ITEMS.register("c_prismarineshard", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "saltchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_NETHER_BRICK = ITEMS.register("c_netherbrick", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_DARK_OAK = ITEMS.register("c_darkoak", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_ACACIAWOOD = ITEMS.register("c_acaciawood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_JUNGLEWOOD = ITEMS.register("c_junglewood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "pulsatingironchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_NAUTILUS_SHELL = ITEMS.register("c_nautilusshell", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "conductiveironchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_HONEYCOMB = ITEMS.register("c_honeycomb", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "yellowchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_DIORITE = ITEMS.register("c_diorite", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "iridiumchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_STONE = ITEMS.register("c_stone", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "stoneburntchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_WOOL = ITEMS.register("c_wool", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "enoricrystalchicken", 0));
	public static final RegistryObject<Item> E_CHICKEN_SPRUCEWOOD = ITEMS.register("c_sprucewood", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken", 0));
	public static final RegistryObject<Item> CHICKENCHICKEN = ITEMS.register("c_vanilla", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "saltchicken", 0));
	//endregion

	//region Chicken - Tier 2
	public static final RegistryObject<Item> E_CHICKEN_FLINT = ITEMS.register("c_flint", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "flintchicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_LAVA = ITEMS.register("c_lava", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "lavachicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_WATER = ITEMS.register("c_water", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "waterchicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_CRIMSTON_STEM = ITEMS.register("c_crimstonstem", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_WARPED_STEM = ITEMS.register("c_warpedstem", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brownchicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_GLASS = ITEMS.register("c_glass", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "glasschicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_NETHERRACK = ITEMS.register("c_netherrack", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "netherwartchicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_INK = ITEMS.register("c_ink", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blackchicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_PAPER = ITEMS.register("c_paper", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "platinumchicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_SUGAR = ITEMS.register("c_sugar", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "enoricrystalchicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_BONE_MEAL = ITEMS.register("c_bonemeal", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "whitechicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_BONE = ITEMS.register("c_bone", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "whitechicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_COAL = ITEMS.register("c_coal", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "coalchicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_CHAR_COAL = ITEMS.register("c_charcoal", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "coalchicken", 1));
	public static final RegistryObject<Item> ECHICKENSNOW = ITEMS.register("c_snow", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "snowballchicken", 1));
	public static final RegistryObject<Item> ECHICKENAPPLE = ITEMS.register("c_apple", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "restoniacrystalchicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_MELON = ITEMS.register("c_melon", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "greenchicken", 1));
	public static final RegistryObject<Item> E_CHICKENGLOWBERRIES = ITEMS.register("c_glowberries", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "glowstonechicken", 1));
	public static final RegistryObject<Item> E_CHICKEN_SWEETBERRIES = ITEMS.register("c_sweetberries", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "redstonecrystalchicken", 1));
	public static final RegistryObject<Item> E_CHICKENBEETROOT = ITEMS.register("c_beetroot", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "redstonealloychicken", 1));
	public static final RegistryObject<Item> E_CHICKENCARROT = ITEMS.register("c_carrot", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "energeticalloychicken", 1));
	//endregion

	//region Chicken - Tier 3

	public static final RegistryObject<Item> E_CHICKEN_COPPER = ITEMS.register("c_copper", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "copperchicken", 2));
	public static final RegistryObject<Item> E_CHICKEN_BLOOD = ITEMS.register("c_blood", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "bloodchicken", 2));
	public static final RegistryObject<Item> E_CHICKEN_BOTANIA_LIVINGROCK = ITEMS.register("c_livingrock", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "moonstonechicken", 2));
	public static final RegistryObject<Item> E_CHICKEN_BOTANIA_LIVINGWOOD = ITEMS.register("c_livingwood", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "magicalwoodchicken", 2));
	public static final RegistryObject<Item> E_CHICKEN_SOUL_SAND = ITEMS.register("c_soulsand", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "soulsandchicken", 2));
	public static final RegistryObject<Item> E_CHICKEN_SOUL_SOIL = ITEMS.register("c_soulsoil", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "soulariumchicken", 2));
	public static final RegistryObject<Item> E_CHICKEN_BASALT = ITEMS.register("c_basalt", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "basalzrodchicken", 2));
	public static final RegistryObject<Item> E_CHICKEN_CLAY = ITEMS.register("c_clay", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "claychicken", 2));
	public static final RegistryObject<Item> E_CHICKEN_RABBIT_HIDE = ITEMS.register("c_rabbithide", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "bronzechicken", 2));
	public static final RegistryObject<Item> E_CHICKEN_LEATHER = ITEMS.register("c_leather", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "bronzechicken", 2));
	public static final RegistryObject<Item> E_CHICKEN_STRING = ITEMS.register("c_string", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "destructioncorechicken", 2));
	public static final RegistryObject<Item> ECHICKENSPONGE = ITEMS.register("c_sponge", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brasschicken", 2));
	public static final RegistryObject<Item> E_CHICKEN_SPIDEREYE = ITEMS.register("c_spidereye", () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "redstonealloychicken", 2));
	//endregion

	//region Chicken - Tier 4
	public static final RegistryObject<Item> E_CHICKEN_IRON = ITEMS.register("c_iron", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ironchicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_REDSTONE = ITEMS.register("c_redstone", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "redchicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_LAPIS = ITEMS.register("c_lapis", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "lightbluechicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_OBSIDIAN = ITEMS.register("c_obsidian", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "obsidianchicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_SLIME = ITEMS.register("c_slime", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "slimechicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_MEKANISM_TIN = ITEMS.register("c_tin", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "quartzenrichedironchicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_MEKANISM_LEAD = ITEMS.register("c_lead", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "leadchicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_NETHER_WART = ITEMS.register("c_netherwart", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "netherwartchicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_MAGMACREAM = ITEMS.register("c_magmacream", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "magmachicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_ROTTEN = ITEMS.register("c_rotten", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "richslagchicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_ZINC = ITEMS.register("c_zinc", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "zincchicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_ALUMINIUM = ITEMS.register("c_aluminium", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "aluminumchicken", 3));
	public static final RegistryObject<Item> ECHICKENNITER = ITEMS.register("c_niter", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "vinteumchicken", 3));
	public static final RegistryObject<Item> ECHICKENCINNABAR = ITEMS.register("c_cinnabar", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "cinnabarchicken", 3));
	public static final RegistryObject<Item> ECHICKENCOKE = ITEMS.register("c_coke", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "coalchicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_TAR = ITEMS.register("c_tar", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blackquartzchicken", 3));
	public static final RegistryObject<Item> E_CHICKEN_SULFUR = ITEMS.register("c_sulfur", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "sulfurchicken", 3));
	public static final RegistryObject<Item> ECHICKENAPATITE = ITEMS.register("c_apatite", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "sapphirechicken", 3));
	public static final RegistryObject<Item> ECHICKENBASALZ = ITEMS.register("c_basalz", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "basalzrodchicken", 3));
	public static final RegistryObject<Item> ECHICKENBITUMEN = ITEMS.register("c_bitumen", () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "coalchicken", 3));
	//endregion

	//region Chicken - Tier 5
	public static final RegistryObject<Item> E_CHICKEN_BLITZ = ITEMS.register("c_blitz", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blitzrodchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_BLIZZ = ITEMS.register("c_blizz", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blizzrodchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_CONSTANTAN = ITEMS.register("c_constantan", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "constantanchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_ENDERIUM = ITEMS.register("c_enderium", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "enderiumchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_LUMIUM = ITEMS.register("c_lumium", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "lumiumchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_QUARTZENRICHEDIRON = ITEMS.register("c_quartzenrichediron", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "quartzenrichedironchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_BLAZEPOWDER = ITEMS.register("c_blazepowder", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blazechicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_AE_SILICON = ITEMS.register("c_silicon", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "siliconchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_BLAZE_ROD = ITEMS.register("c_blazerod", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blazechicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_BOTANIA_MANASTEEL = ITEMS.register("c_manasteel", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "manasteelchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_CHORUS_FRUIT = ITEMS.register("c_chorusfruit", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "magentachicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_CHROME = ITEMS.register("c_chrome", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "chromechicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_ENDER_PEARL = ITEMS.register("c_enderpearl", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "enderchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_ENDSTONE = ITEMS.register("c_endstone", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "bulletchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_GLOWSTONE = ITEMS.register("c_glowstone", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "glowstonechicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_GOLD = ITEMS.register("c_gold", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "goldchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_QUARTZ = ITEMS.register("c_quartz", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "quartzchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_REFINED_IRON = ITEMS.register("c_refinediron", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "refinedironchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_SIGNALUM = ITEMS.register("c_signalum", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "signalumchicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_SILVER = ITEMS.register("c_silver", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "silverorechicken", 4));
	public static final RegistryObject<Item> E_CHICKEN_TNT = ITEMS.register("c_tnt", () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "gunpowderchicken", 4));
	//endregion

	//region Chicken - Tier 6
	public static final RegistryObject<Item> ECHICKENAMETHYSTBRONZE = ITEMS.register("c_amethystbronze", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "purplechicken", 5));
	public static final RegistryObject<Item> ECHICKENHEPATIZON = ITEMS.register("c_hepatizon", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "tanzanitechicken", 5));
	public static final RegistryObject<Item> ECHICKENKNIGHTSLIME = ITEMS.register("c_knightslime", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "knightslimechicken", 5));
	public static final RegistryObject<Item> ECHICKENPIGIRON = ITEMS.register("c_pigiron", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "pigironchicken", 5));
	public static final RegistryObject<Item> ECHICKENROSEGOLD = ITEMS.register("c_rosegold", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "pinkslimechicken", 5));
	public static final RegistryObject<Item> ECHICKENRUBY = ITEMS.register("c_ruby", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "rubychicken", 5));
	public static final RegistryObject<Item> ECHICKENSAPPHIRE = ITEMS.register("c_sapphire", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "sapphirechicken", 5));
	public static final RegistryObject<Item> ECHICKENSLIMESTEEL = ITEMS.register("c_slimesteel", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "sapphirechicken", 5));
	public static final RegistryObject<Item> E_CHICKEN_BRASS = ITEMS.register("c_brass", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "brasschicken", 5));
	public static final RegistryObject<Item> E_CHICKEN_CERTUSQ = ITEMS.register("c_certusquartz", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "quartzenrichedironchicken", 5));
	public static final RegistryObject<Item> E_CHICKEN_EMERALD = ITEMS.register("c_emerald", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "emeradiccrystalchicken", 5));
	public static final RegistryObject<Item> E_CHICKEN_ENDER_EYE = ITEMS.register("c_endereye", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "enderchicken", 5));
	public static final RegistryObject<Item> E_CHICKEN_GHASTTEAR = ITEMS.register("c_ghasttear", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ghastchicken", 5));
	public static final RegistryObject<Item> E_CHICKEN_MEKANISM_BIO_FUEL = ITEMS.register("c_biofuel", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "enderiumchicken", 5));
	public static final RegistryObject<Item> E_CHICKEN_MEKANISM_BRONZE = ITEMS.register("c_bronze", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "bronzechicken", 5));
	public static final RegistryObject<Item> E_CHICKEN_MEKANISM_STEEL = ITEMS.register("c_steel", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "steelchicken", 5));
	public static final RegistryObject<Item> E_CHICKEN_MEKANISM_URANIUM = ITEMS.register("c_uranium", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "uraniumchicken", 5));
	public static final RegistryObject<Item> E_CHICKEN_TUNGSTEN = ITEMS.register("c_tungsten", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "tungstenchicken", 5));
	public static final RegistryObject<Item> E_CHICKEN_YELLORIUM = ITEMS.register("c_yellorium", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "yelloriumchicken", 5));
	public static final RegistryObject<Item> E_OSMIUM_CHICKEN = ITEMS.register("c_osmium", () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "osmiumchicken", 5));
	//endregion

	//region Chicken - Tier 7
	public static final RegistryObject<Item> ECHICKENCOBALD = ITEMS.register("c_cobald", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "cobaltchicken", 6));
	public static final RegistryObject<Item> ECHICKENMANYULLYN = ITEMS.register("c_manyullyn", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "manyullynchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_AE_CHARGED_CERTUS = ITEMS.register("c_chargedcertus", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "quartzchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_AE_FLUIX_CRYSTAL = ITEMS.register("c_fluixcrystal", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "lunarreactivedustchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_ALLTHEMODIUM = ITEMS.register("c_allthemodium", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "energeticalloychicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_AMETHYST_SHARD = ITEMS.register("c_amethystshard", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "amethystchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_BLUTONIUM = ITEMS.register("c_blutonium", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blutoniumchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_BOTANIA_ELEMENTIUM = ITEMS.register("c_elementium", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "elementiumchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_BOTANIA_TERRASTEEL = ITEMS.register("c_terrasteel", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "terrasteelchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_DIAMOND = ITEMS.register("c_diamond", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "diamondchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_ELECTRUM = ITEMS.register("c_electrum", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "electrumchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_HOT_TUNGSTEN_STEEL = ITEMS.register("c_hottungstensteel", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "silverorechicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_INVAR = ITEMS.register("c_invar", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "invarchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_NETHERITE = ITEMS.register("c_netherite", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "netherwartchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_NETHER_STAR = ITEMS.register("c_netherstar", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "blitzrodchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_NICKEL = ITEMS.register("c_nickel", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "nickelchicken", 6));
	public static final RegistryObject<Item> E_CHICKEN_TUNGSTENSTEEL = ITEMS.register("c_tungstensteel", () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "tungstensteelchicken", 6));
	//endregion

	//region Chicken - Tier 8
	public static final RegistryObject<Item> E_CHICKEN_ADAMANTIUM = ITEMS.register("c_adamantium", () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "emeradiccrystalchicken", 7));
	public static final RegistryObject<Item> E_CHICKEN_IRIDIUM = ITEMS.register("c_iridium", () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "iridiumchicken", 7));
	public static final RegistryObject<Item> E_CHICKEN_PLATINUM = ITEMS.register("c_platinum", () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "platinumchicken", 7));
	public static final RegistryObject<Item> E_CHICKEN_VIBRANIUM = ITEMS.register("c_vibranium", () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "vibrantalloychicken", 7));
	//endregion

	//region Chicken - Tier 9
	public static final RegistryObject<Item> E_CHICKEN_TITANIUM = ITEMS.register("c_titanium", () -> new AnimatedChicken_9(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "titaniumchicken", 8));
	public static final RegistryObject<Item> E_CHICKEN_UNOBTAINIUM = ITEMS.register("c_unobtainium", () -> new AnimatedChicken_9(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "vinteumchicken", 8));
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
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_1 = ITEMS.register("chicken_food_tier_1", () -> new ModItemNameBlockItem_1(ModBlocks.SEED_CROP_1.get(), new Item.Properties(), 0));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_2 = ITEMS.register("chicken_food_tier_2", () -> new ModItemNameBlockItem_2(ModBlocks.SEED_CROP_2.get(), new Item.Properties(), 1));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_3 = ITEMS.register("chicken_food_tier_3", () -> new ModItemNameBlockItem_3(ModBlocks.SEED_CROP_3.get(), new Item.Properties(), 2));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_4 = ITEMS.register("chicken_food_tier_4", () -> new ModItemNameBlockItem_4(ModBlocks.SEED_CROP_4.get(), new Item.Properties(), 3));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_5 = ITEMS.register("chicken_food_tier_5", () -> new ModItemNameBlockItem_5(ModBlocks.SEED_CROP_5.get(), new Item.Properties(), 4));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_6 = ITEMS.register("chicken_food_tier_6", () -> new ModItemNameBlockItem_6(ModBlocks.SEED_CROP_6.get(), new Item.Properties(), 5));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_7 = ITEMS.register("chicken_food_tier_7", () -> new ModItemNameBlockItem_7(ModBlocks.SEED_CROP_7.get(), new Item.Properties(), 6));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_8 = ITEMS.register("chicken_food_tier_8", () -> new ModItemNameBlockItem_8(ModBlocks.SEED_CROP_8.get(), new Item.Properties(), 7));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_9 = ITEMS.register("chicken_food_tier_9", () -> new ModItemNameBlockItem_9(ModBlocks.SEED_CROP_9.get(), new Item.Properties(), 8));
	//endregion

	public static final RegistryObject<BlockItem> SOUL_BREEDER = ITEMSS.register("soul_breeder",
			() -> new AnimatedSoulBreederBlockItem(ModBlocks.SOUL_BREEDER.get(),
					new Item.Properties()){
			});
	public static final RegistryObject<BlockItem> TRAINER = ITEMSS.register("trainer",
			() -> new AnimatedTrainerBlockItem(ModBlocks.TRAINER.get(),
					new Item.Properties()));
	public static final RegistryObject<Item> CHICKENSTORAGE = ITEMS.register("chickenstorage",
			() -> new BlockItem(ModBlocks.CHICKENSTORAGE.get(), new Item.Properties().rarity(Rarity.EPIC)) {

				private static final DecimalFormat STORAGE_AMOUNT_FORMAT = new DecimalFormat("#,###,###");

				@Override
				public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
					super.appendHoverText(stack, level, tooltip, flag);

					// Add static tooltip lines
					tooltip.add(Component.literal("Very Big Storage"));
					tooltip.add(Component.literal("For Chickens and Seeds"));
					tooltip.add(Component.literal("Works only with AE2, RS, Simple Storage Network"));
					tooltip.add(Component.literal("or other Mods who access Block Inventories via Interfaces"));
					tooltip.add(Component.literal("Its harder than Obsidian and got a 10x explosion Resist"));

					// Add dynamic tooltip line if storage amount is present
					if (stack.hasTag() && stack.getTag().contains("StorageAmount")) {
						int storageAmount = stack.getTag().getInt("StorageAmount");
						tooltip.add(Component.literal("\u00A7e Stored Items: \u00A7a" + STORAGE_AMOUNT_FORMAT.format(storageAmount)));
					}
				}
			});



	public static int mycount;
	public static int tier = 0;
	public static String name = "";
	public static String id = "";
	public static String texture = "";

	public static void readthis() {

		try (Stream<Path> files = Files.list(Paths.get((FMLPaths.GAMEDIR.get().toString() + "/roostultimate/chickens")))) {
			mycount = (int) files.count();

		} catch (IOException e) {
			e.printStackTrace();
		}

		File file = new File("");
		com.google.gson.JsonObject mainObj = new com.google.gson.JsonObject();
		com.google.gson.JsonObject subObj = new com.google.gson.JsonObject();
		com.google.gson.JsonObject othersubObj = new com.google.gson.JsonObject();
		if (mycount > 0) {
			{
				//Creating a File object for directory
				File directoryPath = new File(FMLPaths.GAMEDIR.get().toString() + "/roostultimate/chickens");
				//List of all files and directories
				File filesList[] = directoryPath.listFiles();
				for(File filee : filesList) {
					try {
						file = filee;
						BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
						StringBuilder jsonstringbuilder = new StringBuilder();
						String line;
						while ((line = bufferedReader.readLine()) != null) {
							jsonstringbuilder.append(line);
						}
						bufferedReader.close();
						subObj = new Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
						subObj = subObj.get("chicken").getAsJsonObject();
						id = subObj.get("id").getAsString();
						texture = subObj.get("texture").getAsString();
						tier = subObj.get("tier").getAsInt();
						extrachickens(id, texture, tier);

					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

	private static RegistryObject<Item> extrachickens(String idd, String texturee, int tierr) {
		return switch (tierr) {
			case 1 -> ITEMS.register(idd, () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee, 0));
			case 2 -> ITEMS.register(idd, () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,1));
			case 3 -> ITEMS.register(idd, () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,2));
			case 4 -> ITEMS.register(idd, () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,3));
			case 5 -> ITEMS.register(idd, () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,4));
			case 6 -> ITEMS.register(idd, () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,5));
			case 7 -> ITEMS.register(idd, () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,6));
			case 8 -> ITEMS.register(idd, () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,7));
			case 9 -> ITEMS.register(idd, () -> new AnimatedChicken_9(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,8));
			default -> ITEMS.register(idd, () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,0));
		};
	}

	public static void register(IEventBus eventBus) {
		readthis();
		ITEMS.register(eventBus);
		ITEMSS.register(eventBus);
	}
}
