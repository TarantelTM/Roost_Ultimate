
package net.tarantel.chickenroost.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.entity.mods.aetwo.AChickenAECertusQuartzEntity;
import net.tarantel.chickenroost.entity.mods.aetwo.AChickenAEChargedCertusEntity;
import net.tarantel.chickenroost.entity.mods.aetwo.AChickenAEFluixCrystalEntity;
import net.tarantel.chickenroost.entity.mods.aetwo.AChickenAESiliconEntity;
import net.tarantel.chickenroost.entity.mods.allthemodium.AChickenAllthemodiumEntity;
import net.tarantel.chickenroost.entity.mods.allthemodium.AChickenUnobtainiumEntity;
import net.tarantel.chickenroost.entity.mods.allthemodium.AChickenVibraniumEntity;
import net.tarantel.chickenroost.entity.mods.botania.*;
import net.tarantel.chickenroost.entity.mods.mekanism.AChickenMekanismBioFuelEntity;
import net.tarantel.chickenroost.entity.mods.mekanism.AChickenOsmiumEntity;
import net.tarantel.chickenroost.entity.mods.random.*;
import net.tarantel.chickenroost.entity.mods.silentgems.AChickenRubyEntity;
import net.tarantel.chickenroost.entity.mods.silentgems.AChickenSapphireEntity;
import net.tarantel.chickenroost.entity.mods.tct.*;
import net.tarantel.chickenroost.entity.mods.thermal.*;
import net.tarantel.chickenroost.entity.vanilla.*;
import net.tarantel.chickenroost.entity.wip.*;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModEntities {
	//public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ChickenRoostMod.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE,
			ChickenRoostMod.MODID);


	public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerMob(String name, EntityType.EntityFactory<T> entity,
																						   float width, float height, int primaryEggColor, int secondaryEggColor) {
		DeferredHolder<EntityType<?>, EntityType<T>> entityType = ENTITIES.register(name,
				() -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name));

		return entityType;
	}


	/*public static final DeferredHolder<EntityType<?>, EntityType<AChickenCobbleEntity>> A_CHICKEN_COBBLE = registerMob("c_cobble", AChickenCobbleEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);*/
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenCobbleEntity>> A_CHICKEN_COBBLE = registerMob("c_cobble",AChickenCobbleEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);

	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBreezeEntity>> A_CHICKEN_BREEZE = registerMob("c_breeze",AChickenBreezeEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenFlintEntity>> A_CHICKEN_FLINT = registerMob("c_flint",AChickenFlintEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenSandEntity>> A_CHICKEN_SAND = registerMob("c_sand",AChickenSandEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenGravelEntity>> A_CHICKEN_GRAVEL = registerMob("c_gravel",AChickenGravelEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenGranitEntity>> A_CHICKEN_GRANIT = registerMob("c_granit",AChickenGranitEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenAndersiteEntity>> A_CHICKEN_ANDERSITE = registerMob("c_andesite",AChickenAndersiteEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenCopperEntity>> A_CHICKEN_COPPER = registerMob("c_copper",AChickenCopperEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenIronEntity>> A_CHICKEN_IRON = registerMob("c_iron",AChickenIronEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenRedstoneEntity>> A_CHICKEN_REDSTONE = registerMob("c_redstone",AChickenRedstoneEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenLapisEntity>> A_CHICKEN_LAPIS = registerMob("c_lapis",AChickenLapisEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenDiamondEntity>> A_CHICKEN_DIAMOND = registerMob("c_diamond",AChickenDiamondEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenObsidianEntity>> A_CHICKEN_OBSIDIAN = registerMob("c_obsidian",AChickenObsidianEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenGoldEntity>> A_CHICKEN_GOLD = registerMob("c_gold",AChickenGoldEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenSlimeEntity>> A_CHICKEN_SLIME = registerMob("c_slime",AChickenSlimeEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBirchwoodEntity>> A_CHICKEN_BIRCHWOOD = registerMob("c_birchwood",AChickenBirchwoodEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenOakwoodEntity>> A_CHICKEN_OAKWOOD = registerMob("c_oakwood",AChickenOakwoodEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenOsmiumEntity>> A_CHICKEN_OSMIUM = registerMob("c_osmium",AChickenOsmiumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenTinEntity>> A_CHICKEN_MEKANISM_TIN = registerMob("c_tin",AChickenTinEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBronzeEntity>> A_CHICKEN_MEKANISM_BRONZE = registerMob("c_bronze",AChickenBronzeEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenSteelEntity>> A_CHICKEN_MEKANISM_STEEL = registerMob("c_steel",AChickenSteelEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenUraniumEntity>> A_CHICKEN_MEKANISM_URANIUM = registerMob("c_uranium",AChickenUraniumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenLeadEntity>> A_CHICKEN_MEKANISM_LEAD = registerMob("c_lead",AChickenLeadEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenMekanismBioFuelEntity>> A_CHICKEN_MEKANISM_BIO_FUEL = registerMob("c_biofuel",AChickenMekanismBioFuelEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenAESiliconEntity>> A_CHICKEN_AE_SILICON = registerMob("c_silicon",AChickenAESiliconEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenAECertusQuartzEntity>> A_CHICKEN_AE_CERTUS_QUARTZ = registerMob("c_certusquartz",AChickenAECertusQuartzEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenAEFluixCrystalEntity>> A_CHICKEN_AE_FLUIX_CRYSTAL = registerMob("c_fluixcrystal",AChickenAEFluixCrystalEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenAEChargedCertusEntity>> A_CHICKEN_AE_CHARGED_CERTUS = registerMob("c_chargedcertus",AChickenAEChargedCertusEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBotaniaManasteelEntity>> A_CHICKEN_BOTANIA_MANASTEEL = registerMob("c_manasteel",AChickenBotaniaManasteelEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBotaniaTerrasteelEntity>> A_CHICKEN_BOTANIA_TERRASTEEL = registerMob("c_terrasteel",AChickenBotaniaTerrasteelEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBotaniaElementiumEntity>> A_CHICKEN_BOTANIA_ELEMENTIUM = registerMob("c_elementium",AChickenBotaniaElementiumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBotaniaLivingrockEntity>> A_CHICKEN_BOTANIA_LIVINGROCK = registerMob("c_livingrock",AChickenBotaniaLivingrockEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBotaniaLivingwoodEntity>> A_CHICKEN_BOTANIA_LIVINGWOOD = registerMob("c_livingwood",AChickenBotaniaLivingwoodEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenCrimstonStemEntity>> A_CHICKEN_CRIMSTON_STEM = registerMob("c_crimstonstem",AChickenCrimstonStemEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenWarpedStemEntity>> A_CHICKEN_WARPED_STEM = registerMob("c_warpedstem",AChickenWarpedStemEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenSprucewoodEntity>> A_CHICKEN_SPRUCEWOOD = registerMob("c_sprucewood",AChickenSprucewoodEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenGlassEntity>> A_CHICKEN_GLASS = registerMob("c_glass",AChickenGlassEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenWoolEntity>> A_CHICKEN_WOOL = registerMob("c_wool",AChickenWoolEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenSoulSandEntity>> A_CHICKEN_SOUL_SAND = registerMob("c_soulsand",AChickenSoulSandEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenNetherrackEntity>> A_CHICKEN_NETHERRACK = registerMob("c_netherrack",AChickenNetherrackEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenSoulSoilEntity>> A_CHICKEN_SOUL_SOIL = registerMob("c_soulsoil",AChickenSoulSoilEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBasaltEntity>> A_CHICKEN_BASALT = registerMob("c_basalt",AChickenBasaltEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenInkEntity>> A_CHICKEN_INK = registerMob("c_ink",AChickenInkEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenPaperEntity>> A_CHICKEN_PAPER = registerMob("c_paper",AChickenPaperEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenClayEntity>> A_CHICKEN_CLAY = registerMob("c_clay",AChickenClayEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenQuartzEntity>> A_CHICKEN_QUARTZ = registerMob("c_quartz",AChickenQuartzEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenAmethystShardEntity>> A_CHICKEN_AMETHYST_SHARD = registerMob("c_amethystshard",AChickenAmethystShardEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenEmeraldEntity>> A_CHICKEN_EMERALD = registerMob("c_emerald",AChickenEmeraldEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenTNTEntity>> A_CHICKEN_TNT = registerMob("c_tnt",AChickenTNTEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenDioriteEntity>> A_CHICKEN_DIORITE = registerMob("c_diorite",AChickenDioriteEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenStoneEntity>> A_CHICKEN_STONE = registerMob("c_stone",AChickenStoneEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenNetherStarEntity>> A_CHICKEN_NETHER_STAR = registerMob("c_netherstar",AChickenNetherStarEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenNetherWartEntity>> A_CHICKEN_NETHER_WART = registerMob("c_netherwart",AChickenNetherWartEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenEnderEyeEntity>> A_CHICKEN_ENDER_EYE = registerMob("c_endereye",AChickenEnderEyeEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenGlowstoneEntity>> A_CHICKEN_GLOWSTONE = registerMob("c_glowstone",AChickenGlowstoneEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBlazeRodEntity>> A_CHICKEN_BLAZE_ROD = registerMob("c_blazerod",AChickenBlazeRodEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenSugarEntity>> A_CHICKEN_SUGAR = registerMob("c_sugar",AChickenSugarEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBoneMealEntity>> A_CHICKEN_BONE_MEAL = registerMob("c_bonemeal",AChickenBoneMealEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenEnderPearlEntity>> A_CHICKEN_ENDER_PEARL = registerMob("c_enderpearl",AChickenEnderPearlEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBoneEntity>> A_CHICKEN_BONE = registerMob("c_bone",AChickenBoneEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenDarkOakEntity>> A_CHICKEN_DARK_OAK = registerMob("c_darkoak",AChickenDarkOakEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenAcaciaWoodEntity>> A_CHICKEN_ACACIA_WOOD = registerMob("c_acaciawood",AChickenAcaciaWoodEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenJungleWoodEntity>> A_CHICKEN_JUNGLE_WOOD = registerMob("c_junglewood",AChickenJungleWoodEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenNautilusShellEntity>> A_CHICKEN_NAUTILUS_SHELL = registerMob("c_nautilusshell",AChickenNautilusShellEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenHoneycombEntity>> A_CHICKEN_HONEYCOMB = registerMob("c_honeycomb",AChickenHoneycombEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenRabbitHideEntity>> A_CHICKEN_RABBIT_HIDE = registerMob("c_rabbithide",AChickenRabbitHideEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenPrismarineShardEntity>> A_CHICKEN_PRISMARINE_SHARD = registerMob("c_prismarineshard",AChickenPrismarineShardEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenNetherBrickEntity>> A_CHICKEN_NETHER_BRICK = registerMob("c_netherbrick",AChickenNetherBrickEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenChorusFruitEntity>> A_CHICKEN_CHORUS_FRUIT = registerMob("c_chorusfruit",AChickenChorusFruitEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenCoalEntity>> A_CHICKEN_COAL = registerMob("c_coal",AChickenCoalEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenCharCoalEntity>> A_CHICKEN_CHAR_COAL = registerMob("c_charcoal",AChickenCharCoalEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenLeatherEntity>> A_CHICKEN_LEATHER = registerMob("c_leather",AChickenLeatherEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenStringEntity>> A_CHICKEN_STRING = registerMob("c_string",AChickenStringEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenFeatherEntity>> A_CHICKEN_FEATHER = registerMob("c_feather",AChickenFeatherEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickensnowEntity>> A_CHICKENSNOW = registerMob("c_snow",AChickensnowEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenappleEntity>> A_CHICKENAPPLE = registerMob("c_apple",AChickenappleEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenspongeEntity>> A_CHICKENSPONGE = registerMob("c_sponge",AChickenspongeEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenmelonEntity>> A_CHICKENMELON = registerMob("c_melon",AChickenmelonEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenmagmacreamEntity>> A_CHICKENMAGMACREAM = registerMob("c_magmacream",AChickenmagmacreamEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenblazepowderEntity>> A_CHICKENBLAZEPOWDER = registerMob("c_blazepowder",AChickenblazepowderEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenglowberriesEntity>> A_CHICKENGLOWBERRIES = registerMob("c_glowberries",AChickenglowberriesEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickensweetberriesEntity>> A_CHICKENSWEETBERRIES = registerMob("c_sweetberries",AChickensweetberriesEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickentintedglassEntity>> A_CHICKENTINTEDGLASS = registerMob("c_tintedglass",AChickentintedglassEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickennetheriteEntity>> A_CHICKENNETHERITE = registerMob("c_netherite",AChickennetheriteEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenbeetrootEntity>> A_CHICKENBEETROOT = registerMob("c_beetroot",AChickenbeetrootEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenspidereyeEntity>> A_CHICKENSPIDEREYE = registerMob("c_spidereye",AChickenspidereyeEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickencarrotEntity>> A_CHICKENCARROT = registerMob("c_carrot",AChickencarrotEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenrottenEntity>> A_CHICKENROTTEN = registerMob("c_rotten",AChickenrottenEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenghasttearEntity>> A_CHICKENGHASTTEAR = registerMob("c_ghasttear",AChickenghasttearEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenAluminiumEntity>> A_CHICKEN_ALUMINIUM = registerMob("c_aluminium",AChickenAluminiumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenZincEntity>> A_CHICKEN_ZINC = registerMob("c_zinc",AChickenZincEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenSilverEntity>> A_CHICKEN_SILVER = registerMob("c_silver",AChickenSilverEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenNickelEntity>> A_CHICKEN_NICKEL = registerMob("c_nickel",AChickenNickelEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenAdamantiumEntity>> A_CHICKEN_ADAMANTIUM = registerMob("c_adamantium",AChickenAdamantiumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBrassEntity>> A_CHICKEN_BRASS = registerMob("c_brass",AChickenBrassEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenChromeEntity>> A_CHICKEN_CHROME = registerMob("c_chrome",AChickenChromeEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenElectrumEntity>> A_CHICKEN_ELECTRUM = registerMob("c_electrum",AChickenElectrumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenInvarEntity>> A_CHICKEN_INVAR = registerMob("c_invar",AChickenInvarEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenIridiumEntity>> A_CHICKEN_IRIDIUM = registerMob("c_iridium",AChickenIridiumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenPlatinumEntity>> A_CHICKEN_PLATINUM = registerMob("c_platinum",AChickenPlatinumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenRefinedironEntity>> A_CHICKEN_REFINEDIRON = registerMob("c_refinediron", AChickenRefinedironEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenTitaniumEntity>> A_CHICKEN_TITANIUM = registerMob("c_titanium",AChickenTitaniumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenTungstenEntity>> A_CHICKEN_TUNGSTEN = registerMob("c_tungsten",AChickenTungstenEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenTungstensteelEntity>> A_CHICKEN_TUNGSTENSTEEL = registerMob("c_tungstensteel",AChickenTungstensteelEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenYelloriumEntity>> A_CHICKEN_YELLORIUM = registerMob("c_yellorium",AChickenYelloriumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBlutoniumEntity>> A_CHICKEN_BLUTONIUM = registerMob("c_blutonium",AChickenBlutoniumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenAllthemodiumEntity>> A_CHICKEN_ALLTHEMODIUM = registerMob("c_allthemodium",AChickenAllthemodiumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenVibraniumEntity>> A_CHICKEN_VIBRANIUM = registerMob("c_vibranium",AChickenVibraniumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenUnobtainiumEntity>> A_CHICKEN_UNOBTAINIUM = registerMob("c_unobtainium",AChickenUnobtainiumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenEndstoneEntity>> A_CHICKEN_ENDSTONE = registerMob("c_endstone",AChickenEndstoneEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenCobaldEntity>> A_CHICKEN_COBALD = registerMob("c_cobald",AChickenCobaldEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenHepatizonEntity>> A_CHICKEN_HEPATIZON = registerMob("c_hepatizon",AChickenHepatizonEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenKnightSlimeEntity>> A_CHICKEN_KNIGHT_SLIME = registerMob("c_knightslime",AChickenKnightSlimeEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenManyullynEntity>> A_CHICKEN_MANYULLYN = registerMob("c_manyullyn",AChickenManyullynEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenPigIronEntity>> A_CHICKEN_PIG_IRON = registerMob("c_pigiron",AChickenPigIronEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenQueenSlimeEntity>> A_CHICKEN_QUEEN_SLIME = registerMob("c_queenslime",AChickenQueenSlimeEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenRoseGoldEntity>> A_CHICKEN_ROSE_GOLD = registerMob("c_rosegold",AChickenRoseGoldEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenSlimesteelEntity>> A_CHICKEN_SLIMESTEEL = registerMob("c_slimesteel",AChickenSlimesteelEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenAmethystBronzeEntity>> A_CHICKEN_AMETHYST_BRONZE = registerMob("c_amethystbronze",AChickenAmethystBronzeEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenQuartzEnrichedIronEntity>> A_CHICKEN_QUARTZ_ENRICHED_IRON = registerMob("c_quartzenrichediron",AChickenQuartzEnrichedIronEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenApatiteEntity>> A_CHICKEN_APATITE = registerMob("c_apatite",AChickenApatiteEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBasalzEntity>> A_CHICKEN_BASALZ = registerMob("c_basalz",AChickenBasalzEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBitumenEntity>> A_CHICKEN_BITUMEN = registerMob("c_bitumen",AChickenBitumenEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBlitzEntity>> A_CHICKEN_BLITZ = registerMob("c_blitz",AChickenBlitzEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenBlizzEntity>> A_CHICKEN_BLIZZ = registerMob("c_blizz",AChickenBlizzEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenCinnabarEntity>> A_CHICKEN_CINNABAR = registerMob("c_cinnabar",AChickenCinnabarEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenCokeEntity>> A_CHICKEN_COKE = registerMob("c_coke",AChickenCokeEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenConstantanEntity>> A_CHICKEN_CONSTANTAN = registerMob("c_constantan",AChickenConstantanEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenEnderiumEntity>> A_CHICKEN_ENDERIUM = registerMob("c_enderium",AChickenEnderiumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenLumiumEntity>> A_CHICKEN_LUMIUM = registerMob("c_lumium",AChickenLumiumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenNiterEntity>> A_CHICKEN_NITER = registerMob("c_niter",AChickenNiterEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenSapphireEntity>> A_CHICKEN_SAPPHIRE = registerMob("c_sapphire",AChickenSapphireEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenRubyEntity>> A_CHICKEN_RUBY = registerMob("c_ruby",AChickenRubyEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenSignalumEntity>> A_CHICKEN_SIGNALUM = registerMob("c_signalum",AChickenSignalumEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenSulfurEntity>> A_CHICKEN_SULFUR = registerMob("c_sulfur",AChickenSulfurEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<AChickenTarEntity>> A_CHICKEN_TAR = registerMob("c_tar",AChickenTarEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);
	public static final DeferredHolder<EntityType<?>, EntityType<GhostChickenEntity>> GHOST_CHICKEN = registerMob("c_ghostchicken",GhostChickenEntity::new,
			0.4f, 0.7f, 0x302219, 0xACACAC);

	/*private static <T extends Entity> Supplier<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}*/

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			AChickenCobbleEntity.init();
			AChickenBreezeEntity.init();
			AChickenFlintEntity.init();
			AChickenSandEntity.init();
			AChickenGravelEntity.init();
			AChickenGranitEntity.init();
			AChickenAndersiteEntity.init();
			AChickenCopperEntity.init();
			AChickenIronEntity.init();
			AChickenRedstoneEntity.init();
			AChickenLapisEntity.init();
			AChickenDiamondEntity.init();
			AChickenObsidianEntity.init();
			AChickenGoldEntity.init();
			AChickenSlimeEntity.init();
			AChickenBirchwoodEntity.init();
			AChickenOakwoodEntity.init();
			AChickenOsmiumEntity.init();
			AChickenTinEntity.init();
			AChickenBronzeEntity.init();
			AChickenSteelEntity.init();
			AChickenUraniumEntity.init();
			AChickenLeadEntity.init();
			AChickenMekanismBioFuelEntity.init();
			AChickenAESiliconEntity.init();
			AChickenAECertusQuartzEntity.init();
			AChickenAEFluixCrystalEntity.init();
			AChickenAEChargedCertusEntity.init();
			AChickenBotaniaManasteelEntity.init();
			AChickenBotaniaTerrasteelEntity.init();
			AChickenBotaniaElementiumEntity.init();
			AChickenBotaniaLivingrockEntity.init();
			AChickenBotaniaLivingwoodEntity.init();
			AChickenCrimstonStemEntity.init();
			AChickenWarpedStemEntity.init();
			AChickenSprucewoodEntity.init();
			AChickenGlassEntity.init();
			AChickenWoolEntity.init();
			AChickenSoulSandEntity.init();
			AChickenNetherrackEntity.init();
			AChickenSoulSoilEntity.init();
			AChickenBasaltEntity.init();
			AChickenInkEntity.init();
			AChickenPaperEntity.init();
			AChickenClayEntity.init();
			AChickenQuartzEntity.init();
			AChickenAmethystShardEntity.init();
			AChickenEmeraldEntity.init();
			AChickenTNTEntity.init();
			AChickenDioriteEntity.init();
			AChickenStoneEntity.init();
			AChickenNetherStarEntity.init();
			AChickenNetherWartEntity.init();
			AChickenEnderEyeEntity.init();
			AChickenGlowstoneEntity.init();
			AChickenBlazeRodEntity.init();
			AChickenSugarEntity.init();
			AChickenBoneMealEntity.init();
			AChickenEnderPearlEntity.init();
			AChickenBoneEntity.init();
			AChickenDarkOakEntity.init();
			AChickenAcaciaWoodEntity.init();
			AChickenJungleWoodEntity.init();
			AChickenNautilusShellEntity.init();
			AChickenHoneycombEntity.init();
			AChickenRabbitHideEntity.init();
			AChickenPrismarineShardEntity.init();
			AChickenNetherBrickEntity.init();
			AChickenChorusFruitEntity.init();
			AChickenCoalEntity.init();
			AChickenCharCoalEntity.init();
			AChickenLeatherEntity.init();
			AChickenStringEntity.init();
			AChickenFeatherEntity.init();
			AChickensnowEntity.init();
			AChickenappleEntity.init();
			AChickenspongeEntity.init();
			AChickenmelonEntity.init();
			AChickenmagmacreamEntity.init();
			AChickenblazepowderEntity.init();
			AChickenglowberriesEntity.init();
			AChickensweetberriesEntity.init();
			AChickentintedglassEntity.init();
			AChickennetheriteEntity.init();
			AChickenbeetrootEntity.init();
			AChickenspidereyeEntity.init();
			AChickencarrotEntity.init();
			AChickenrottenEntity.init();
			AChickenghasttearEntity.init();
			AChickenAluminiumEntity.init();
			AChickenZincEntity.init();
			AChickenSilverEntity.init();
			AChickenNickelEntity.init();
			AChickenAdamantiumEntity.init();
			AChickenBrassEntity.init();
			AChickenChromeEntity.init();
			AChickenElectrumEntity.init();
			AChickenInvarEntity.init();
			AChickenIridiumEntity.init();
			AChickenPlatinumEntity.init();
			AChickenRefinedironEntity.init();
			AChickenTitaniumEntity.init();
			AChickenTungstenEntity.init();
			AChickenTungstensteelEntity.init();
			AChickenYelloriumEntity.init();
			AChickenBlutoniumEntity.init();
			AChickenAllthemodiumEntity.init();
			AChickenVibraniumEntity.init();
			AChickenUnobtainiumEntity.init();
			AChickenEndstoneEntity.init();
			AChickenCobaldEntity.init();
			AChickenHepatizonEntity.init();
			AChickenKnightSlimeEntity.init();
			AChickenManyullynEntity.init();
			AChickenPigIronEntity.init();
			AChickenQueenSlimeEntity.init();
			AChickenRoseGoldEntity.init();
			AChickenSlimesteelEntity.init();
			AChickenAmethystBronzeEntity.init();
			AChickenQuartzEnrichedIronEntity.init();
			AChickenApatiteEntity.init();
			AChickenBasalzEntity.init();
			AChickenBitumenEntity.init();
			AChickenBlitzEntity.init();
			AChickenBlizzEntity.init();
			AChickenCinnabarEntity.init();
			AChickenCokeEntity.init();
			AChickenConstantanEntity.init();
			AChickenEnderiumEntity.init();
			AChickenLumiumEntity.init();
			AChickenNiterEntity.init();
			AChickenSapphireEntity.init();
			AChickenRubyEntity.init();
			AChickenSignalumEntity.init();
			AChickenSulfurEntity.init();
			AChickenTarEntity.init();
			GhostChickenEntity.init();
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(A_CHICKEN_COBBLE.get(), AChickenCobbleEntity.createAttributes().build());
		event.put(A_CHICKEN_BREEZE.get(), AChickenBreezeEntity.createAttributes().build());
		event.put(A_CHICKEN_FLINT.get(), AChickenFlintEntity.createAttributes().build());
		event.put(A_CHICKEN_SAND.get(), AChickenSandEntity.createAttributes().build());
		event.put(A_CHICKEN_GRAVEL.get(), AChickenGravelEntity.createAttributes().build());
		event.put(A_CHICKEN_GRANIT.get(), AChickenGranitEntity.createAttributes().build());
		event.put(A_CHICKEN_ANDERSITE.get(), AChickenAndersiteEntity.createAttributes().build());
		event.put(A_CHICKEN_COPPER.get(), AChickenCopperEntity.createAttributes().build());
		event.put(A_CHICKEN_IRON.get(), AChickenIronEntity.createAttributes().build());
		event.put(A_CHICKEN_REDSTONE.get(), AChickenRedstoneEntity.createAttributes().build());
		event.put(A_CHICKEN_LAPIS.get(), AChickenLapisEntity.createAttributes().build());
		event.put(A_CHICKEN_DIAMOND.get(), AChickenDiamondEntity.createAttributes().build());
		event.put(A_CHICKEN_OBSIDIAN.get(), AChickenObsidianEntity.createAttributes().build());
		event.put(A_CHICKEN_GOLD.get(), AChickenGoldEntity.createAttributes().build());
		event.put(A_CHICKEN_SLIME.get(), AChickenSlimeEntity.createAttributes().build());
		event.put(A_CHICKEN_BIRCHWOOD.get(), AChickenBirchwoodEntity.createAttributes().build());
		event.put(A_CHICKEN_OAKWOOD.get(), AChickenOakwoodEntity.createAttributes().build());
		event.put(A_CHICKEN_OSMIUM.get(), AChickenOsmiumEntity.createAttributes().build());
		event.put(A_CHICKEN_MEKANISM_TIN.get(), AChickenTinEntity.createAttributes().build());
		event.put(A_CHICKEN_MEKANISM_BRONZE.get(), AChickenBronzeEntity.createAttributes().build());
		event.put(A_CHICKEN_MEKANISM_STEEL.get(), AChickenSteelEntity.createAttributes().build());
		event.put(A_CHICKEN_MEKANISM_URANIUM.get(), AChickenUraniumEntity.createAttributes().build());
		event.put(A_CHICKEN_MEKANISM_LEAD.get(), AChickenLeadEntity.createAttributes().build());
		event.put(A_CHICKEN_MEKANISM_BIO_FUEL.get(), AChickenMekanismBioFuelEntity.createAttributes().build());
		event.put(A_CHICKEN_AE_SILICON.get(), AChickenAESiliconEntity.createAttributes().build());
		event.put(A_CHICKEN_AE_CERTUS_QUARTZ.get(), AChickenAECertusQuartzEntity.createAttributes().build());
		event.put(A_CHICKEN_AE_FLUIX_CRYSTAL.get(), AChickenAEFluixCrystalEntity.createAttributes().build());
		event.put(A_CHICKEN_AE_CHARGED_CERTUS.get(), AChickenAEChargedCertusEntity.createAttributes().build());
		event.put(A_CHICKEN_BOTANIA_MANASTEEL.get(), AChickenBotaniaManasteelEntity.createAttributes().build());
		event.put(A_CHICKEN_BOTANIA_TERRASTEEL.get(), AChickenBotaniaTerrasteelEntity.createAttributes().build());
		event.put(A_CHICKEN_BOTANIA_ELEMENTIUM.get(), AChickenBotaniaElementiumEntity.createAttributes().build());
		event.put(A_CHICKEN_BOTANIA_LIVINGROCK.get(), AChickenBotaniaLivingrockEntity.createAttributes().build());
		event.put(A_CHICKEN_BOTANIA_LIVINGWOOD.get(), AChickenBotaniaLivingwoodEntity.createAttributes().build());
		event.put(A_CHICKEN_CRIMSTON_STEM.get(), AChickenCrimstonStemEntity.createAttributes().build());
		event.put(A_CHICKEN_WARPED_STEM.get(), AChickenWarpedStemEntity.createAttributes().build());
		event.put(A_CHICKEN_SPRUCEWOOD.get(), AChickenSprucewoodEntity.createAttributes().build());
		event.put(A_CHICKEN_GLASS.get(), AChickenGlassEntity.createAttributes().build());
		event.put(A_CHICKEN_WOOL.get(), AChickenWoolEntity.createAttributes().build());
		event.put(A_CHICKEN_SOUL_SAND.get(), AChickenSoulSandEntity.createAttributes().build());
		event.put(A_CHICKEN_NETHERRACK.get(), AChickenNetherrackEntity.createAttributes().build());
		event.put(A_CHICKEN_SOUL_SOIL.get(), AChickenSoulSoilEntity.createAttributes().build());
		event.put(A_CHICKEN_BASALT.get(), AChickenBasaltEntity.createAttributes().build());
		event.put(A_CHICKEN_INK.get(), AChickenInkEntity.createAttributes().build());
		event.put(A_CHICKEN_PAPER.get(), AChickenPaperEntity.createAttributes().build());
		event.put(A_CHICKEN_CLAY.get(), AChickenClayEntity.createAttributes().build());
		event.put(A_CHICKEN_QUARTZ.get(), AChickenQuartzEntity.createAttributes().build());
		event.put(A_CHICKEN_AMETHYST_SHARD.get(), AChickenAmethystShardEntity.createAttributes().build());
		event.put(A_CHICKEN_EMERALD.get(), AChickenEmeraldEntity.createAttributes().build());
		event.put(A_CHICKEN_TNT.get(), AChickenTNTEntity.createAttributes().build());
		event.put(A_CHICKEN_DIORITE.get(), AChickenDioriteEntity.createAttributes().build());
		event.put(A_CHICKEN_STONE.get(), AChickenStoneEntity.createAttributes().build());
		event.put(A_CHICKEN_NETHER_STAR.get(), AChickenNetherStarEntity.createAttributes().build());
		event.put(A_CHICKEN_NETHER_WART.get(), AChickenNetherWartEntity.createAttributes().build());
		event.put(A_CHICKEN_ENDER_EYE.get(), AChickenEnderEyeEntity.createAttributes().build());
		event.put(A_CHICKEN_GLOWSTONE.get(), AChickenGlowstoneEntity.createAttributes().build());
		event.put(A_CHICKEN_BLAZE_ROD.get(), AChickenBlazeRodEntity.createAttributes().build());
		event.put(A_CHICKEN_SUGAR.get(), AChickenSugarEntity.createAttributes().build());
		event.put(A_CHICKEN_BONE_MEAL.get(), AChickenBoneMealEntity.createAttributes().build());
		event.put(A_CHICKEN_ENDER_PEARL.get(), AChickenEnderPearlEntity.createAttributes().build());
		event.put(A_CHICKEN_BONE.get(), AChickenBoneEntity.createAttributes().build());
		event.put(A_CHICKEN_DARK_OAK.get(), AChickenDarkOakEntity.createAttributes().build());
		event.put(A_CHICKEN_ACACIA_WOOD.get(), AChickenAcaciaWoodEntity.createAttributes().build());
		event.put(A_CHICKEN_JUNGLE_WOOD.get(), AChickenJungleWoodEntity.createAttributes().build());
		event.put(A_CHICKEN_NAUTILUS_SHELL.get(), AChickenNautilusShellEntity.createAttributes().build());
		event.put(A_CHICKEN_HONEYCOMB.get(), AChickenHoneycombEntity.createAttributes().build());
		event.put(A_CHICKEN_RABBIT_HIDE.get(), AChickenRabbitHideEntity.createAttributes().build());
		event.put(A_CHICKEN_PRISMARINE_SHARD.get(), AChickenPrismarineShardEntity.createAttributes().build());
		event.put(A_CHICKEN_NETHER_BRICK.get(), AChickenNetherBrickEntity.createAttributes().build());
		event.put(A_CHICKEN_CHORUS_FRUIT.get(), AChickenChorusFruitEntity.createAttributes().build());
		event.put(A_CHICKEN_COAL.get(), AChickenCoalEntity.createAttributes().build());
		event.put(A_CHICKEN_CHAR_COAL.get(), AChickenCharCoalEntity.createAttributes().build());
		event.put(A_CHICKEN_LEATHER.get(), AChickenLeatherEntity.createAttributes().build());
		event.put(A_CHICKEN_STRING.get(), AChickenStringEntity.createAttributes().build());
		event.put(A_CHICKEN_FEATHER.get(), AChickenFeatherEntity.createAttributes().build());
		event.put(A_CHICKENSNOW.get(), AChickensnowEntity.createAttributes().build());
		event.put(A_CHICKENAPPLE.get(), AChickenappleEntity.createAttributes().build());
		event.put(A_CHICKENSPONGE.get(), AChickenspongeEntity.createAttributes().build());
		event.put(A_CHICKENMELON.get(), AChickenmelonEntity.createAttributes().build());
		event.put(A_CHICKENMAGMACREAM.get(), AChickenmagmacreamEntity.createAttributes().build());
		event.put(A_CHICKENBLAZEPOWDER.get(), AChickenblazepowderEntity.createAttributes().build());
		event.put(A_CHICKENGLOWBERRIES.get(), AChickenglowberriesEntity.createAttributes().build());
		event.put(A_CHICKENSWEETBERRIES.get(), AChickensweetberriesEntity.createAttributes().build());
		event.put(A_CHICKENTINTEDGLASS.get(), AChickentintedglassEntity.createAttributes().build());
		event.put(A_CHICKENNETHERITE.get(), AChickennetheriteEntity.createAttributes().build());
		event.put(A_CHICKENBEETROOT.get(), AChickenbeetrootEntity.createAttributes().build());
		event.put(A_CHICKENSPIDEREYE.get(), AChickenspidereyeEntity.createAttributes().build());
		event.put(A_CHICKENCARROT.get(), AChickencarrotEntity.createAttributes().build());
		event.put(A_CHICKENROTTEN.get(), AChickenrottenEntity.createAttributes().build());
		event.put(A_CHICKENGHASTTEAR.get(), AChickenghasttearEntity.createAttributes().build());
		event.put(A_CHICKEN_ALUMINIUM.get(), AChickenAluminiumEntity.createAttributes().build());
		event.put(A_CHICKEN_ZINC.get(), AChickenZincEntity.createAttributes().build());
		event.put(A_CHICKEN_SILVER.get(), AChickenSilverEntity.createAttributes().build());
		event.put(A_CHICKEN_NICKEL.get(), AChickenNickelEntity.createAttributes().build());
		event.put(A_CHICKEN_ADAMANTIUM.get(), AChickenAdamantiumEntity.createAttributes().build());
		event.put(A_CHICKEN_BRASS.get(), AChickenBrassEntity.createAttributes().build());
		event.put(A_CHICKEN_CHROME.get(), AChickenChromeEntity.createAttributes().build());
		event.put(A_CHICKEN_ELECTRUM.get(), AChickenElectrumEntity.createAttributes().build());
		event.put(A_CHICKEN_INVAR.get(), AChickenInvarEntity.createAttributes().build());
		event.put(A_CHICKEN_IRIDIUM.get(), AChickenIridiumEntity.createAttributes().build());
		event.put(A_CHICKEN_PLATINUM.get(), AChickenPlatinumEntity.createAttributes().build());
		event.put(A_CHICKEN_REFINEDIRON.get(), AChickenRefinedironEntity.createAttributes().build());
		event.put(A_CHICKEN_TITANIUM.get(), AChickenTitaniumEntity.createAttributes().build());
		event.put(A_CHICKEN_TUNGSTEN.get(), AChickenTungstenEntity.createAttributes().build());
		event.put(A_CHICKEN_TUNGSTENSTEEL.get(), AChickenTungstensteelEntity.createAttributes().build());
		event.put(A_CHICKEN_YELLORIUM.get(), AChickenYelloriumEntity.createAttributes().build());
		event.put(A_CHICKEN_BLUTONIUM.get(), AChickenBlutoniumEntity.createAttributes().build());
		event.put(A_CHICKEN_ALLTHEMODIUM.get(), AChickenAllthemodiumEntity.createAttributes().build());
		event.put(A_CHICKEN_VIBRANIUM.get(), AChickenVibraniumEntity.createAttributes().build());
		event.put(A_CHICKEN_UNOBTAINIUM.get(), AChickenUnobtainiumEntity.createAttributes().build());
		event.put(A_CHICKEN_ENDSTONE.get(), AChickenEndstoneEntity.createAttributes().build());
		event.put(A_CHICKEN_COBALD.get(), AChickenCobaldEntity.createAttributes().build());
		event.put(A_CHICKEN_HEPATIZON.get(), AChickenHepatizonEntity.createAttributes().build());
		event.put(A_CHICKEN_KNIGHT_SLIME.get(), AChickenKnightSlimeEntity.createAttributes().build());
		event.put(A_CHICKEN_MANYULLYN.get(), AChickenManyullynEntity.createAttributes().build());
		event.put(A_CHICKEN_PIG_IRON.get(), AChickenPigIronEntity.createAttributes().build());
		event.put(A_CHICKEN_QUEEN_SLIME.get(), AChickenQueenSlimeEntity.createAttributes().build());
		event.put(A_CHICKEN_ROSE_GOLD.get(), AChickenRoseGoldEntity.createAttributes().build());
		event.put(A_CHICKEN_SLIMESTEEL.get(), AChickenSlimesteelEntity.createAttributes().build());
		event.put(A_CHICKEN_AMETHYST_BRONZE.get(), AChickenAmethystBronzeEntity.createAttributes().build());
		event.put(A_CHICKEN_QUARTZ_ENRICHED_IRON.get(), AChickenQuartzEnrichedIronEntity.createAttributes().build());
		event.put(A_CHICKEN_APATITE.get(), AChickenApatiteEntity.createAttributes().build());
		event.put(A_CHICKEN_BASALZ.get(), AChickenBasalzEntity.createAttributes().build());
		event.put(A_CHICKEN_BITUMEN.get(), AChickenBitumenEntity.createAttributes().build());
		event.put(A_CHICKEN_BLITZ.get(), AChickenBlitzEntity.createAttributes().build());
		event.put(A_CHICKEN_BLIZZ.get(), AChickenBlizzEntity.createAttributes().build());
		event.put(A_CHICKEN_CINNABAR.get(), AChickenCinnabarEntity.createAttributes().build());
		event.put(A_CHICKEN_COKE.get(), AChickenCokeEntity.createAttributes().build());
		event.put(A_CHICKEN_CONSTANTAN.get(), AChickenConstantanEntity.createAttributes().build());
		event.put(A_CHICKEN_ENDERIUM.get(), AChickenEnderiumEntity.createAttributes().build());
		event.put(A_CHICKEN_LUMIUM.get(), AChickenLumiumEntity.createAttributes().build());
		event.put(A_CHICKEN_NITER.get(), AChickenNiterEntity.createAttributes().build());
		event.put(A_CHICKEN_SAPPHIRE.get(), AChickenSapphireEntity.createAttributes().build());
		event.put(A_CHICKEN_RUBY.get(), AChickenRubyEntity.createAttributes().build());
		event.put(A_CHICKEN_SIGNALUM.get(), AChickenSignalumEntity.createAttributes().build());
		event.put(A_CHICKEN_SULFUR.get(), AChickenSulfurEntity.createAttributes().build());
		event.put(A_CHICKEN_TAR.get(), AChickenTarEntity.createAttributes().build());
		event.put(GHOST_CHICKEN.get(), GhostChickenEntity.createAttributes().build());
	}
}