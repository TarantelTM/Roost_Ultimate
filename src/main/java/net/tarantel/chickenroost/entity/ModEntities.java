

package net.tarantel.chickenroost.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.util.ChickenConfig;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, ChickenRoostMod.MODID);
	//region color chicken

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BLOOD = registerMonster("c_blood",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_RED = registerMob(
			"c_red",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_YELLOW = registerMob("c_yellow",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_WHITE = registerMob("c_white",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ORANGE = registerMob("c_orange",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_MAGENTA = registerMob("c_magenta",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_LIGHT_BLUE = registerMob("c_light_blue",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_LIME = registerMob("c_lime",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_PINK = registerMob("c_pink",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_GRAY = registerMob("c_gray",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_LIGHT_GRAY = registerMob("c_light_gray",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_CYAN = registerMob("c_cyan",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_PURPLE = registerMob("c_purple",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BROWN = registerMob("c_brown",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_GREEN = registerMob("c_green",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BLACK = registerMob("c_black",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	//endregion
	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_COBBLE = registerMob("c_cobble",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_LAVA = registerMonster("c_lava",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_WATER = registerMob("c_water",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);
	/*public static final RegistryObject<EntityType<AChickenBreezeEntity>> A_CHICKEN_BREEZE = registerMob("c_breeze",AChickenBreezeEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);*/
	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_FLINT = registerMob("c_flint",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_SAND = registerMob("c_sand",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_GRAVEL = registerMob("c_gravel",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_GRANIT = registerMob("c_granit",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ANDERSITE = registerMob("c_andesite",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_COPPER = registerMob("c_copper",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_IRON = registerMob("c_iron",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_REDSTONE = registerMob("c_redstone",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_LAPIS = registerMob("c_lapis",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_DIAMOND = registerMob("c_diamond",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_OBSIDIAN = registerMob("c_obsidian",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_GOLD = registerMob("c_gold",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_SLIME = registerMob("c_slime",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BIRCHWOOD = registerMob("c_birchwood",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_OAKWOOD = registerMob("c_oakwood",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_OSMIUM = registerMob("c_osmium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_MEKANISM_TIN = registerMob("c_tin",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_MEKANISM_BRONZE = registerMob("c_bronze",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_MEKANISM_STEEL = registerMob("c_steel",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_MEKANISM_URANIUM = registerMob("c_uranium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_MEKANISM_LEAD = registerMob("c_lead",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_MEKANISM_BIO_FUEL = registerMob("c_biofuel",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_AE_SILICON = registerMob("c_silicon",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_AE_CERTUS_QUARTZ = registerMob("c_certusquartz",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_AE_FLUIX_CRYSTAL = registerMob("c_fluixcrystal",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_AE_CHARGED_CERTUS = registerMob("c_chargedcertus",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BOTANIA_MANASTEEL = registerMob("c_manasteel",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BOTANIA_TERRASTEEL = registerMob("c_terrasteel",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BOTANIA_ELEMENTIUM = registerMob("c_elementium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BOTANIA_LIVINGROCK = registerMob("c_livingrock",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BOTANIA_LIVINGWOOD = registerMob("c_livingwood",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_CRIMSTON_STEM = registerMonster("c_crimstonstem",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);


	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_WARPED_STEM = registerMonster("c_warpedstem",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_SPRUCEWOOD = registerMob("c_sprucewood",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_GLASS = registerMob("c_glass",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_WOOL = registerMob("c_wool",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_SOUL_SAND = registerMonster("c_soulsand",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_NETHERRACK = registerMonster("c_netherrack",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_SOUL_SOIL = registerMonster("c_soulsoil",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BASALT = registerMonster("c_basalt",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_INK = registerMob("c_ink",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_PAPER = registerMob("c_paper",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_CLAY = registerMob("c_clay",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_QUARTZ = registerMonster("c_quartz",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_AMETHYST_SHARD = registerMob("c_amethystshard",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_EMERALD = registerMob("c_emerald",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_TNT = registerMob("c_tnt",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_DIORITE = registerMob("c_diorite",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_STONE = registerMob("c_stone",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_NETHER_STAR = registerMob("c_netherstar",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_NETHER_WART = registerMonster("c_netherwart",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ENDER_EYE = registerMonster("c_endereye",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_GLOWSTONE = registerMonster("c_glowstone",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BLAZE_ROD = registerMonster("c_blazerod",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_SUGAR = registerMob("c_sugar",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BONE_MEAL = registerMob("c_bonemeal",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ENDER_PEARL = registerMonster("c_enderpearl",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BONE = registerMonster("c_bone",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_DARK_OAK = registerMob("c_darkoak",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ACACIA_WOOD = registerMob("c_acaciawood",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_JUNGLE_WOOD = registerMob("c_junglewood",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_NAUTILUS_SHELL = registerMob("c_nautilusshell",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_HONEYCOMB = registerMob("c_honeycomb",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_RABBIT_HIDE = registerMob("c_rabbithide",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_PRISMARINE_SHARD = registerMob("c_prismarineshard",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_NETHER_BRICK = registerMonster("c_netherbrick",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_CHORUS_FRUIT = registerMonster("c_chorusfruit",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_COAL = registerMob("c_coal",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_CHAR_COAL = registerMob("c_charcoal",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_LEATHER = registerMob("c_leather",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_STRING = registerMonster("c_string",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_FEATHER = registerMob("c_feather",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENSNOW = registerMob("c_snow",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENAPPLE = registerMob("c_apple",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENSPONGE = registerMob("c_sponge",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENMELON = registerMob("c_melon",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENMAGMACREAM = registerMonster("c_magmacream",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENBLAZEPOWDER = registerMonster("c_blazepowder",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENGLOWBERRIES = registerMob("c_glowberries",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENSWEETBERRIES = registerMob("c_sweetberries",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENTINTEDGLASS = registerMob("c_tintedglass",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENNETHERITE = registerMonster("c_netherite",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENBEETROOT = registerMob("c_beetroot",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENSPIDEREYE = registerMonster("c_spidereye",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENCARROT = registerMob("c_carrot",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENROTTEN = registerMonster("c_rotten",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKENGHASTTEAR = registerMonster("c_ghasttear",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ALUMINIUM = registerMob("c_aluminium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ZINC = registerMob("c_zinc",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_SILVER = registerMob("c_silver",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_NICKEL = registerMob("c_nickel",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ADAMANTIUM = registerMob("c_adamantium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BRASS = registerMob("c_brass",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_CHROME = registerMob("c_chrome",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ELECTRUM = registerMob("c_electrum",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_INVAR = registerMob("c_invar",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_IRIDIUM = registerMob("c_iridium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_PLATINUM = registerMob("c_platinum",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_REFINEDIRON = registerMob("c_refinediron",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_TITANIUM = registerMob("c_titanium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_TUNGSTEN = registerMob("c_tungsten",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_TUNGSTENSTEEL = registerMob("c_tungstensteel",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_YELLORIUM = registerMob("c_yellorium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BLUTONIUM = registerMob("c_blutonium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ALLTHEMODIUM = registerMob("c_allthemodium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_VIBRANIUM = registerMob("c_vibranium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_UNOBTAINIUM = registerMob("c_unobtainium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ENDSTONE = registerMonster("c_endstone",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_COBALD = registerMob("c_cobald",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_HEPATIZON = registerMob("c_hepatizon",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_KNIGHT_SLIME = registerMob("c_knightslime",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_MANYULLYN = registerMob("c_manyullyn",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_PIG_IRON = registerMob("c_pigiron",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_QUEEN_SLIME = registerMob("c_queenslime",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ROSE_GOLD = registerMob("c_rosegold",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_SLIMESTEEL = registerMob("c_slimesteel",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_AMETHYST_BRONZE = registerMob("c_amethystbronze",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_QUARTZ_ENRICHED_IRON = registerMob("c_quartzenrichediron",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_APATITE = registerMob("c_apatite",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BASALZ = registerMob("c_basalz",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BITUMEN = registerMob("c_bitumen",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BLITZ = registerMob("c_blitz",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_BLIZZ = registerMob("c_blizz",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_CINNABAR = registerMob("c_cinnabar",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_COKE = registerMob("c_coke",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_CONSTANTAN = registerMob("c_constantan",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_ENDERIUM = registerMob("c_enderium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_LUMIUM = registerMob("c_lumium",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_NITER = registerMob("c_niter",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_SAPPHIRE = registerMob("c_sapphire",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_RUBY = registerMob("c_ruby",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_SIGNALUM = registerMob("c_signalum",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_SULFUR = registerMob("c_sulfur",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_TAR = registerMob("c_tar",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	public static final RegistryObject<EntityType<BaseChickenEntity>> GHOST_CHICKEN = registerMob("c_ghostchicken",
			BaseChickenEntity::new,
			0.4f, 0.7f,
			0x302219, 0xACACAC
	);

	private static <T extends Entity> RegistryObject<EntityType<T>> registerMob(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		return REGISTRY.register(name, () -> EntityType.Builder.of(factory, MobCategory.CREATURE)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.build(name));
	}

	private static <T extends Entity> RegistryObject<EntityType<T>> registerMonster(
			String name, EntityType.EntityFactory<T> factory,
			float width, float height, int primaryColor, int secondaryColor
	) {
		return REGISTRY.register(name, () -> EntityType.Builder.of(factory, MobCategory.MONSTER)
				.sized(0.4f, 0.7f)
				.clientTrackingRange(8)
				.build(name));
	}
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			BaseChickenEntity.init();
		});
	}


	public static void initChickenConfig() {

		ItemStack lavaegg = new ItemStack(ModItems.LAVA_EGG.get());
		ItemStack wateregg = new ItemStack(ModItems.WATER_EGG.get());
		CompoundTag fluidTagLava = new CompoundTag();
		CompoundTag fluidTagWater = new CompoundTag();
		fluidTagLava.putString("FluidName", "minecraft:lava");
		fluidTagLava.putInt("Amount", 1000);
		fluidTagWater.putString("FluidName", "minecraft:water");
		fluidTagWater.putInt("Amount", 1000);

// Create a new CompoundTag for the root NBT data
		CompoundTag nbtDataLava = new CompoundTag();
		CompoundTag nbtDataWater = new CompoundTag();
		nbtDataLava.put("Fluid", fluidTagLava);
		nbtDataWater.put("Fluid", fluidTagWater);

// Set the NBT data to the ItemStack
		lavaegg.setTag(nbtDataLava);
		wateregg.setTag(nbtDataWater);


		ChickenConfig.setDropStack(A_CHICKEN_BLOOD.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_BLOOD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_RED.get(), new ItemStack(ModItems.RED_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_RED.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_YELLOW.get(), new ItemStack(ModItems.YELLOW_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_YELLOW.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_WHITE.get(), new ItemStack(ModItems.WHITE_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_WHITE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ORANGE.get(), new ItemStack(ModItems.ORANGE_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_ORANGE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_MAGENTA.get(), new ItemStack(ModItems.MAGENTA_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_MAGENTA.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_LIGHT_BLUE.get(), new ItemStack(ModItems.LIGHT_BLUE_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_LIGHT_BLUE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_LIME.get(), new ItemStack(ModItems.LIME_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_LIME.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_PINK.get(), new ItemStack(ModItems.PINK_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_PINK.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_GRAY.get(), new ItemStack(ModItems.GRAY_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_GRAY.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_LIGHT_GRAY.get(), new ItemStack(ModItems.LIGHT_GRAY_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_LIGHT_GRAY.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_CYAN.get(), new ItemStack(ModItems.CYAN_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_CYAN.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_PURPLE.get(), new ItemStack(ModItems.PURPLE_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_PURPLE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BROWN.get(), new ItemStack(ModItems.BROWN_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_BROWN.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_GREEN.get(), new ItemStack(ModItems.GREEN_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_GREEN.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BLACK.get(), new ItemStack(ModItems.BLACK_EGG.get()));
		ChickenConfig.setEggTime(A_CHICKEN_BLACK.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_COBBLE.get(), new ItemStack(ModItems.STONE_ESSENCE.get()));
		ChickenConfig.setEggTime(A_CHICKEN_COBBLE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_LAVA.get(), lavaegg);
		ChickenConfig.setEggTime(A_CHICKEN_LAVA.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_WATER.get(), wateregg);
		ChickenConfig.setEggTime(A_CHICKEN_WATER.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_FLINT.get(), new ItemStack(Items.FLINT));
		ChickenConfig.setEggTime(A_CHICKEN_FLINT.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_SAND.get(), new ItemStack(Items.SAND));
		ChickenConfig.setEggTime(A_CHICKEN_SAND.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_GRAVEL.get(), new ItemStack(Items.GRAVEL));
		ChickenConfig.setEggTime(A_CHICKEN_GRAVEL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_GRANIT.get(), new ItemStack(ModItems.STONE_ESSENCE.get()));
		ChickenConfig.setEggTime(A_CHICKEN_GRANIT.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ANDERSITE.get(), new ItemStack(ModItems.STONE_ESSENCE.get()));
		ChickenConfig.setEggTime(A_CHICKEN_ANDERSITE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_COPPER.get(), new ItemStack(Items.COPPER_INGOT));
		ChickenConfig.setEggTime(A_CHICKEN_COPPER.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_IRON.get(), new ItemStack(Items.IRON_INGOT));
		ChickenConfig.setEggTime(A_CHICKEN_IRON.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_REDSTONE.get(), new ItemStack(Items.REDSTONE));
		ChickenConfig.setEggTime(A_CHICKEN_REDSTONE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_LAPIS.get(), new ItemStack(Items.LAPIS_LAZULI));
		ChickenConfig.setEggTime(A_CHICKEN_LAPIS.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_DIAMOND.get(), new ItemStack(Items.DIAMOND));
		ChickenConfig.setEggTime(A_CHICKEN_DIAMOND.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_OBSIDIAN.get(), new ItemStack(Items.OBSIDIAN));
		ChickenConfig.setEggTime(A_CHICKEN_OBSIDIAN.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_GOLD.get(), new ItemStack(Items.GOLD_INGOT));
		ChickenConfig.setEggTime(A_CHICKEN_GOLD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_SLIME.get(), new ItemStack(Items.SLIME_BALL));
		ChickenConfig.setEggTime(A_CHICKEN_SLIME.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BIRCHWOOD.get(), new ItemStack(ModItems.WOOD_ESSENCE.get()));
		ChickenConfig.setEggTime(A_CHICKEN_BIRCHWOOD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_OAKWOOD.get(), new ItemStack(ModItems.WOOD_ESSENCE.get()));
		ChickenConfig.setEggTime(A_CHICKEN_OAKWOOD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_OSMIUM.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_OSMIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_MEKANISM_TIN.get(), new ItemStack(ModItems.INGOT_TIN.get()));
		ChickenConfig.setEggTime(A_CHICKEN_MEKANISM_TIN.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_MEKANISM_BRONZE.get(), new ItemStack(ModItems.INGOT_BRONZE.get()));
		ChickenConfig.setEggTime(A_CHICKEN_MEKANISM_BRONZE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_MEKANISM_STEEL.get(), new ItemStack(ModItems.INGOT_STEEL.get()));
		ChickenConfig.setEggTime(A_CHICKEN_MEKANISM_STEEL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_MEKANISM_URANIUM.get(), new ItemStack(ModItems.INGOT_URANIUM.get()));
		ChickenConfig.setEggTime(A_CHICKEN_MEKANISM_URANIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_MEKANISM_LEAD.get(), new ItemStack(ModItems.INGOT_LEAD.get()));
		ChickenConfig.setEggTime(A_CHICKEN_MEKANISM_LEAD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_MEKANISM_BIO_FUEL.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_MEKANISM_BIO_FUEL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_AE_SILICON.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_AE_SILICON.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_AE_CERTUS_QUARTZ.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_AE_CERTUS_QUARTZ.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_AE_FLUIX_CRYSTAL.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_AE_FLUIX_CRYSTAL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_AE_CHARGED_CERTUS.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_AE_CHARGED_CERTUS.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BOTANIA_MANASTEEL.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_BOTANIA_MANASTEEL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BOTANIA_TERRASTEEL.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_BOTANIA_TERRASTEEL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BOTANIA_ELEMENTIUM.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_BOTANIA_ELEMENTIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BOTANIA_LIVINGROCK.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_BOTANIA_LIVINGROCK.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BOTANIA_LIVINGWOOD.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_BOTANIA_LIVINGWOOD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_CRIMSTON_STEM.get(), new ItemStack(Items.CRIMSON_STEM));
		ChickenConfig.setEggTime(A_CHICKEN_CRIMSTON_STEM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_WARPED_STEM.get(), new ItemStack(Items.WARPED_STEM));
		ChickenConfig.setEggTime(A_CHICKEN_WARPED_STEM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_SPRUCEWOOD.get(), new ItemStack(ModItems.WOOD_ESSENCE.get()));
		ChickenConfig.setEggTime(A_CHICKEN_SPRUCEWOOD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_GLASS.get(), new ItemStack(Items.GLASS));
		ChickenConfig.setEggTime(A_CHICKEN_GLASS.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_WOOL.get(), new ItemStack(Items.WHITE_WOOL));
		ChickenConfig.setEggTime(A_CHICKEN_WOOL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_SOUL_SAND.get(), new ItemStack(Items.SOUL_SAND));
		ChickenConfig.setEggTime(A_CHICKEN_SOUL_SAND.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_NETHERRACK.get(), new ItemStack(Items.NETHERRACK));
		ChickenConfig.setEggTime(A_CHICKEN_NETHERRACK.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_SOUL_SOIL.get(), new ItemStack(Items.SOUL_SOIL));
		ChickenConfig.setEggTime(A_CHICKEN_SOUL_SOIL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BASALT.get(), new ItemStack(Items.BASALT));
		ChickenConfig.setEggTime(A_CHICKEN_BASALT.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_INK.get(), new ItemStack(Items.INK_SAC));
		ChickenConfig.setEggTime(A_CHICKEN_INK.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_PAPER.get(), new ItemStack(Items.SUGAR_CANE));
		ChickenConfig.setEggTime(A_CHICKEN_PAPER.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_CLAY.get(), new ItemStack(Items.CLAY_BALL));
		ChickenConfig.setEggTime(A_CHICKEN_CLAY.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_QUARTZ.get(), new ItemStack(Items.QUARTZ));
		ChickenConfig.setEggTime(A_CHICKEN_QUARTZ.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_AMETHYST_SHARD.get(), new ItemStack(Items.AMETHYST_SHARD));
		ChickenConfig.setEggTime(A_CHICKEN_AMETHYST_SHARD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_EMERALD.get(), new ItemStack(Items.EMERALD));
		ChickenConfig.setEggTime(A_CHICKEN_EMERALD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_TNT.get(), new ItemStack(Items.GUNPOWDER));
		ChickenConfig.setEggTime(A_CHICKEN_TNT.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_DIORITE.get(), new ItemStack(ModItems.STONE_ESSENCE.get()));
		ChickenConfig.setEggTime(A_CHICKEN_DIORITE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_STONE.get(), new ItemStack(ModItems.STONE_ESSENCE.get()));
		ChickenConfig.setEggTime(A_CHICKEN_STONE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_NETHER_STAR.get(), new ItemStack(Items.NETHER_STAR));
		ChickenConfig.setEggTime(A_CHICKEN_NETHER_STAR.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_NETHER_WART.get(), new ItemStack(Items.NETHER_WART));
		ChickenConfig.setEggTime(A_CHICKEN_NETHER_WART.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ENDER_EYE.get(), new ItemStack(Items.ENDER_EYE));
		ChickenConfig.setEggTime(A_CHICKEN_ENDER_EYE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_GLOWSTONE.get(), new ItemStack(Items.GLOWSTONE));
		ChickenConfig.setEggTime(A_CHICKEN_GLOWSTONE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BLAZE_ROD.get(), new ItemStack(Items.BLAZE_ROD));
		ChickenConfig.setEggTime(A_CHICKEN_BLAZE_ROD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_SUGAR.get(), new ItemStack(Items.SUGAR));
		ChickenConfig.setEggTime(A_CHICKEN_SUGAR.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BONE_MEAL.get(), new ItemStack(Items.BONE_MEAL));
		ChickenConfig.setEggTime(A_CHICKEN_BONE_MEAL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ENDER_PEARL.get(), new ItemStack(Items.ENDER_PEARL));
		ChickenConfig.setEggTime(A_CHICKEN_ENDER_PEARL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BONE.get(), new ItemStack(Items.BONE));
		ChickenConfig.setEggTime(A_CHICKEN_BONE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_DARK_OAK.get(), new ItemStack(ModItems.WOOD_ESSENCE.get()));
		ChickenConfig.setEggTime(A_CHICKEN_DARK_OAK.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ACACIA_WOOD.get(), new ItemStack(ModItems.WOOD_ESSENCE.get()));
		ChickenConfig.setEggTime(A_CHICKEN_ACACIA_WOOD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_JUNGLE_WOOD.get(), new ItemStack(ModItems.WOOD_ESSENCE.get()));
		ChickenConfig.setEggTime(A_CHICKEN_JUNGLE_WOOD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_NAUTILUS_SHELL.get(), new ItemStack(Items.NAUTILUS_SHELL));
		ChickenConfig.setEggTime(A_CHICKEN_NAUTILUS_SHELL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_HONEYCOMB.get(), new ItemStack(Items.HONEYCOMB));
		ChickenConfig.setEggTime(A_CHICKEN_HONEYCOMB.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_RABBIT_HIDE.get(), new ItemStack(Items.RABBIT_HIDE));
		ChickenConfig.setEggTime(A_CHICKEN_RABBIT_HIDE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_PRISMARINE_SHARD.get(), new ItemStack(Items.PRISMARINE_SHARD));
		ChickenConfig.setEggTime(A_CHICKEN_PRISMARINE_SHARD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_NETHER_BRICK.get(), new ItemStack(Items.NETHER_BRICK));
		ChickenConfig.setEggTime(A_CHICKEN_NETHER_BRICK.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_CHORUS_FRUIT.get(), new ItemStack(Items.CHORUS_FRUIT));
		ChickenConfig.setEggTime(A_CHICKEN_CHORUS_FRUIT.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_COAL.get(), new ItemStack(Items.COAL));
		ChickenConfig.setEggTime(A_CHICKEN_COAL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_CHAR_COAL.get(), new ItemStack(Items.CHARCOAL));
		ChickenConfig.setEggTime(A_CHICKEN_CHAR_COAL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_LEATHER.get(), new ItemStack(Items.LEATHER));
		ChickenConfig.setEggTime(A_CHICKEN_LEATHER.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_STRING.get(), new ItemStack(Items.STRING));
		ChickenConfig.setEggTime(A_CHICKEN_STRING.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_FEATHER.get(), new ItemStack(Items.FEATHER));
		ChickenConfig.setEggTime(A_CHICKEN_FEATHER.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENSNOW.get(), new ItemStack(Items.SNOWBALL));
		ChickenConfig.setEggTime(A_CHICKENSNOW.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENAPPLE.get(), new ItemStack(Items.APPLE));
		ChickenConfig.setEggTime(A_CHICKENAPPLE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENSPONGE.get(), new ItemStack(Items.SPONGE));
		ChickenConfig.setEggTime(A_CHICKENSPONGE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENMELON.get(), new ItemStack(Items.MELON));
		ChickenConfig.setEggTime(A_CHICKENMELON.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENMAGMACREAM.get(), new ItemStack(Items.MAGMA_CREAM));
		ChickenConfig.setEggTime(A_CHICKENMAGMACREAM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENBLAZEPOWDER.get(), new ItemStack(Items.BLAZE_POWDER));
		ChickenConfig.setEggTime(A_CHICKENBLAZEPOWDER.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENGLOWBERRIES.get(), new ItemStack(Items.GLOW_BERRIES));
		ChickenConfig.setEggTime(A_CHICKENGLOWBERRIES.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENSWEETBERRIES.get(), new ItemStack(Items.SWEET_BERRIES));
		ChickenConfig.setEggTime(A_CHICKENSWEETBERRIES.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENTINTEDGLASS.get(), new ItemStack(Items.TINTED_GLASS));
		ChickenConfig.setEggTime(A_CHICKENTINTEDGLASS.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENNETHERITE.get(), new ItemStack(Items.NETHERITE_INGOT));
		ChickenConfig.setEggTime(A_CHICKENNETHERITE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENBEETROOT.get(), new ItemStack(Items.BEETROOT));
		ChickenConfig.setEggTime(A_CHICKENBEETROOT.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENSPIDEREYE.get(), new ItemStack(Items.SPIDER_EYE));
		ChickenConfig.setEggTime(A_CHICKENSPIDEREYE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENCARROT.get(), new ItemStack(Items.CARROT));
		ChickenConfig.setEggTime(A_CHICKENCARROT.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENROTTEN.get(), new ItemStack(Items.ROTTEN_FLESH));
		ChickenConfig.setEggTime(A_CHICKENROTTEN.get(), 600);
		ChickenConfig.setDropStack(A_CHICKENGHASTTEAR.get(), new ItemStack(Items.GHAST_TEAR));
		ChickenConfig.setEggTime(A_CHICKENGHASTTEAR.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ALUMINIUM.get(), new ItemStack(ModItems.INGOT_ALUMINUM.get()));
		ChickenConfig.setEggTime(A_CHICKEN_ALUMINIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ZINC.get(), new ItemStack(ModItems.INGOT_ZINC.get()));
		ChickenConfig.setEggTime(A_CHICKEN_ZINC.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_SILVER.get(), new ItemStack(ModItems.INGOT_SILVER.get()));
		ChickenConfig.setEggTime(A_CHICKEN_SILVER.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_NICKEL.get(), new ItemStack(ModItems.INGOT_NICKEL.get()));
		ChickenConfig.setEggTime(A_CHICKEN_NICKEL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ADAMANTIUM.get(), new ItemStack(ModItems.INGOT_ADAMANTIUM.get()));
		ChickenConfig.setEggTime(A_CHICKEN_ADAMANTIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BRASS.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_BRASS.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_CHROME.get(), new ItemStack(ModItems.INGOT_CHROME.get()));
		ChickenConfig.setEggTime(A_CHICKEN_CHROME.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ELECTRUM.get(), new ItemStack(ModItems.INGOT_ELECTRUM.get()));
		ChickenConfig.setEggTime(A_CHICKEN_ELECTRUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_INVAR.get(), new ItemStack(ModItems.INGOT_INVAR.get()));
		ChickenConfig.setEggTime(A_CHICKEN_INVAR.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_IRIDIUM.get(), new ItemStack(ModItems.INGOT_IRIDIUM.get()));
		ChickenConfig.setEggTime(A_CHICKEN_IRIDIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_PLATINUM.get(), new ItemStack(ModItems.INGOT_PLATINUM.get()));
		ChickenConfig.setEggTime(A_CHICKEN_PLATINUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_REFINEDIRON.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_REFINEDIRON.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_TITANIUM.get(), new ItemStack(ModItems.INGOT_TITANUM.get()));
		ChickenConfig.setEggTime(A_CHICKEN_TITANIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_TUNGSTEN.get(), new ItemStack(ModItems.INGOT_TUNGSTEN.get()));
		ChickenConfig.setEggTime(A_CHICKEN_TUNGSTEN.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_TUNGSTENSTEEL.get(), new ItemStack(ModItems.INGOT_TUNGSTENSTEEL.get()));
		ChickenConfig.setEggTime(A_CHICKEN_TUNGSTENSTEEL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_YELLORIUM.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_YELLORIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BLUTONIUM.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_BLUTONIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ALLTHEMODIUM.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_ALLTHEMODIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_VIBRANIUM.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_VIBRANIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_UNOBTAINIUM.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_UNOBTAINIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ENDSTONE.get(), new ItemStack(Items.END_STONE));
		ChickenConfig.setEggTime(A_CHICKEN_ENDSTONE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_COBALD.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_COBALD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_HEPATIZON.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_HEPATIZON.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_KNIGHT_SLIME.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_KNIGHT_SLIME.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_MANYULLYN.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_MANYULLYN.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_PIG_IRON.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_PIG_IRON.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_QUEEN_SLIME.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_QUEEN_SLIME.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ROSE_GOLD.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_ROSE_GOLD.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_SLIMESTEEL.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_SLIMESTEEL.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_AMETHYST_BRONZE.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_AMETHYST_BRONZE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_QUARTZ_ENRICHED_IRON.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_QUARTZ_ENRICHED_IRON.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_APATITE.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_APATITE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BASALZ.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_BASALZ.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BITUMEN.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_BITUMEN.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BLITZ.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_BLITZ.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_BLIZZ.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_BLIZZ.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_CINNABAR.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_CINNABAR.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_COKE.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_COKE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_CONSTANTAN.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_CONSTANTAN.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_ENDERIUM.get(), new ItemStack(ModItems.INGOT_ENDERIUM.get()));
		ChickenConfig.setEggTime(A_CHICKEN_ENDERIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_LUMIUM.get(), new ItemStack(ModItems.INGOT_LUMIUM.get()));
		ChickenConfig.setEggTime(A_CHICKEN_LUMIUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_NITER.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_NITER.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_SAPPHIRE.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_SAPPHIRE.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_RUBY.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_RUBY.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_SIGNALUM.get(), new ItemStack(ModItems.INGOT_SIGNALUM.get()));
		ChickenConfig.setEggTime(A_CHICKEN_SIGNALUM.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_SULFUR.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_SULFUR.get(), 600);
		ChickenConfig.setDropStack(A_CHICKEN_TAR.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(A_CHICKEN_TAR.get(), 600);
		ChickenConfig.setDropStack(GHOST_CHICKEN.get(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism:osmium_ingot"))));
		ChickenConfig.setEggTime(GHOST_CHICKEN.get(), 600);
	}
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(A_CHICKEN_COBBLE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BLOOD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_RED.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_YELLOW.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_WHITE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ORANGE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_MAGENTA.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_LIGHT_BLUE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_LIME.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_PINK.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_GRAY.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_LIGHT_GRAY.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_CYAN.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_PURPLE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BROWN.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_GREEN.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BLACK.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_LAVA.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_WATER.get(), BaseChickenEntity.createAttributes().build());
		//event.put(A_CHICKEN_BREEZE.get(), AChickenBreezeEntity.createAttributes().build());
		event.put(A_CHICKEN_FLINT.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_SAND.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_GRAVEL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_GRANIT.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ANDERSITE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_COPPER.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_IRON.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_REDSTONE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_LAPIS.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_DIAMOND.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_OBSIDIAN.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_GOLD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_SLIME.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BIRCHWOOD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_OAKWOOD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_OSMIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_MEKANISM_TIN.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_MEKANISM_BRONZE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_MEKANISM_STEEL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_MEKANISM_URANIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_MEKANISM_LEAD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_MEKANISM_BIO_FUEL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_AE_SILICON.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_AE_CERTUS_QUARTZ.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_AE_FLUIX_CRYSTAL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_AE_CHARGED_CERTUS.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BOTANIA_MANASTEEL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BOTANIA_TERRASTEEL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BOTANIA_ELEMENTIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BOTANIA_LIVINGROCK.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BOTANIA_LIVINGWOOD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_CRIMSTON_STEM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_WARPED_STEM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_SPRUCEWOOD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_GLASS.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_WOOL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_SOUL_SAND.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_NETHERRACK.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_SOUL_SOIL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BASALT.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_INK.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_PAPER.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_CLAY.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_QUARTZ.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_AMETHYST_SHARD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_EMERALD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_TNT.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_DIORITE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_STONE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_NETHER_STAR.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_NETHER_WART.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ENDER_EYE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_GLOWSTONE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BLAZE_ROD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_SUGAR.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BONE_MEAL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ENDER_PEARL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BONE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_DARK_OAK.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ACACIA_WOOD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_JUNGLE_WOOD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_NAUTILUS_SHELL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_HONEYCOMB.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_RABBIT_HIDE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_PRISMARINE_SHARD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_NETHER_BRICK.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_CHORUS_FRUIT.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_COAL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_CHAR_COAL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_LEATHER.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_STRING.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_FEATHER.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENSNOW.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENAPPLE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENSPONGE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENMELON.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENMAGMACREAM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENBLAZEPOWDER.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENGLOWBERRIES.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENSWEETBERRIES.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENTINTEDGLASS.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENNETHERITE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENBEETROOT.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENSPIDEREYE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENCARROT.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENROTTEN.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKENGHASTTEAR.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ALUMINIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ZINC.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_SILVER.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_NICKEL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ADAMANTIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BRASS.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_CHROME.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ELECTRUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_INVAR.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_IRIDIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_PLATINUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_REFINEDIRON.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_TITANIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_TUNGSTEN.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_TUNGSTENSTEEL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_YELLORIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BLUTONIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ALLTHEMODIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_VIBRANIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_UNOBTAINIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ENDSTONE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_COBALD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_HEPATIZON.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_KNIGHT_SLIME.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_MANYULLYN.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_PIG_IRON.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_QUEEN_SLIME.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ROSE_GOLD.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_SLIMESTEEL.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_AMETHYST_BRONZE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_QUARTZ_ENRICHED_IRON.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_APATITE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BASALZ.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BITUMEN.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BLITZ.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_BLIZZ.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_CINNABAR.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_COKE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_CONSTANTAN.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_ENDERIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_LUMIUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_NITER.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_SAPPHIRE.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_RUBY.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_SIGNALUM.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_SULFUR.get(), BaseChickenEntity.createAttributes().build());
		event.put(A_CHICKEN_TAR.get(), BaseChickenEntity.createAttributes().build());
		event.put(GHOST_CHICKEN.get(), BaseChickenEntity.createAttributes().build());
	}
}