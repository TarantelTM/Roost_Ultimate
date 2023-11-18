
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.tarantel.chickenroost.entity;

import net.tarantel.chickenroost.ChickenRoostMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.tarantel.chickenroost.entity.mods.aetwo.AChickenAECertusQuartzEntity;
import net.tarantel.chickenroost.entity.mods.aetwo.AChickenAEChargedCertusEntity;
import net.tarantel.chickenroost.entity.mods.aetwo.AChickenAEFluixCrystalEntity;
import net.tarantel.chickenroost.entity.mods.aetwo.AChickenAESiliconEntity;
import net.tarantel.chickenroost.entity.mods.allthemodium.AChickenAllthemodiumEntity;
import net.tarantel.chickenroost.entity.mods.allthemodium.AChickenUnobtainiumEntity;
import net.tarantel.chickenroost.entity.mods.allthemodium.AChickenVibraniumEntity;
import net.tarantel.chickenroost.entity.mods.botania.*;
import net.tarantel.chickenroost.entity.mods.mekanism.*;
import net.tarantel.chickenroost.entity.mods.random.*;
import net.tarantel.chickenroost.entity.mods.silentgems.AChickenRubyEntity;
import net.tarantel.chickenroost.entity.mods.silentgems.AChickenSapphireEntity;
import net.tarantel.chickenroost.entity.mods.tct.*;
import net.tarantel.chickenroost.entity.mods.thermal.*;
import net.tarantel.chickenroost.entity.vanilla.*;
import net.tarantel.chickenroost.entity.wip.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ChickenRoostMod.MODID);
	public static final RegistryObject<EntityType<AChickenCobbleEntity>> A_CHICKEN_COBBLE = register("c_cobble",
			EntityType.Builder.<AChickenCobbleEntity>of(AChickenCobbleEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenCobbleEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenFlintEntity>> A_CHICKEN_FLINT = register("c_flint",
			EntityType.Builder.<AChickenFlintEntity>of(AChickenFlintEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenFlintEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenSandEntity>> A_CHICKEN_SAND = register("c_sand",
			EntityType.Builder.<AChickenSandEntity>of(AChickenSandEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenSandEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenGravelEntity>> A_CHICKEN_GRAVEL = register("c_gravel",
			EntityType.Builder.<AChickenGravelEntity>of(AChickenGravelEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenGravelEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenGranitEntity>> A_CHICKEN_GRANIT = register("c_granit",
			EntityType.Builder.<AChickenGranitEntity>of(AChickenGranitEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenGranitEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenAndersiteEntity>> A_CHICKEN_ANDERSITE = register("c_andesite",
			EntityType.Builder.<AChickenAndersiteEntity>of(AChickenAndersiteEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenAndersiteEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenCopperEntity>> A_CHICKEN_COPPER = register("c_copper",
			EntityType.Builder.<AChickenCopperEntity>of(AChickenCopperEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenCopperEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenIronEntity>> A_CHICKEN_IRON = register("c_iron",
			EntityType.Builder.<AChickenIronEntity>of(AChickenIronEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenIronEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenRedstoneEntity>> A_CHICKEN_REDSTONE = register("c_redstone",
			EntityType.Builder.<AChickenRedstoneEntity>of(AChickenRedstoneEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenRedstoneEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenLapisEntity>> A_CHICKEN_LAPIS = register("c_lapis",
			EntityType.Builder.<AChickenLapisEntity>of(AChickenLapisEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenLapisEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenDiamondEntity>> A_CHICKEN_DIAMOND = register("c_diamond",
			EntityType.Builder.<AChickenDiamondEntity>of(AChickenDiamondEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenDiamondEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenObsidianEntity>> A_CHICKEN_OBSIDIAN = register("c_obsidian",
			EntityType.Builder.<AChickenObsidianEntity>of(AChickenObsidianEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenObsidianEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenGoldEntity>> A_CHICKEN_GOLD = register("c_gold",
			EntityType.Builder.<AChickenGoldEntity>of(AChickenGoldEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenGoldEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenSlimeEntity>> A_CHICKEN_SLIME = register("c_slime",
			EntityType.Builder.<AChickenSlimeEntity>of(AChickenSlimeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenSlimeEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBirchwoodEntity>> A_CHICKEN_BIRCHWOOD = register("c_birchwood",
			EntityType.Builder.<AChickenBirchwoodEntity>of(AChickenBirchwoodEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenBirchwoodEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenOakwoodEntity>> A_CHICKEN_OAKWOOD = register("c_oakwood",
			EntityType.Builder.<AChickenOakwoodEntity>of(AChickenOakwoodEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenOakwoodEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenOsmiumEntity>> A_CHICKEN_OSMIUM = register("c_osmium",
			EntityType.Builder.<AChickenOsmiumEntity>of(AChickenOsmiumEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenOsmiumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenTinEntity>> A_CHICKEN_MEKANISM_TIN = register("c_tin",
			EntityType.Builder.<AChickenTinEntity>of(AChickenTinEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenTinEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBronzeEntity>> A_CHICKEN_MEKANISM_BRONZE = register("c_bronze",
			EntityType.Builder.<AChickenBronzeEntity>of(AChickenBronzeEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenBronzeEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenSteelEntity>> A_CHICKEN_MEKANISM_STEEL = register("c_steel",
			EntityType.Builder.<AChickenSteelEntity>of(AChickenSteelEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenSteelEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenUraniumEntity>> A_CHICKEN_MEKANISM_URANIUM = register("c_uranium",
			EntityType.Builder.<AChickenUraniumEntity>of(AChickenUraniumEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenUraniumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenLeadEntity>> A_CHICKEN_MEKANISM_LEAD = register("c_lead",
			EntityType.Builder.<AChickenLeadEntity>of(AChickenLeadEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenLeadEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenMekanismBioFuelEntity>> A_CHICKEN_MEKANISM_BIO_FUEL = register(
			"c_biofuel",
			EntityType.Builder.<AChickenMekanismBioFuelEntity>of(AChickenMekanismBioFuelEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenMekanismBioFuelEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenAESiliconEntity>> A_CHICKEN_AE_SILICON = register("c_silicon",
			EntityType.Builder.<AChickenAESiliconEntity>of(AChickenAESiliconEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenAESiliconEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenAECertusQuartzEntity>> A_CHICKEN_AE_CERTUS_QUARTZ = register("c_certusquartz",
			EntityType.Builder.<AChickenAECertusQuartzEntity>of(AChickenAECertusQuartzEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenAECertusQuartzEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenAEFluixCrystalEntity>> A_CHICKEN_AE_FLUIX_CRYSTAL = register("c_fluixcrystal",
			EntityType.Builder.<AChickenAEFluixCrystalEntity>of(AChickenAEFluixCrystalEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenAEFluixCrystalEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenAEChargedCertusEntity>> A_CHICKEN_AE_CHARGED_CERTUS = register(
			"c_chargedcertus",
			EntityType.Builder.<AChickenAEChargedCertusEntity>of(AChickenAEChargedCertusEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenAEChargedCertusEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBotaniaManasteelEntity>> A_CHICKEN_BOTANIA_MANASTEEL = register(
			"c_manasteel",
			EntityType.Builder.<AChickenBotaniaManasteelEntity>of(AChickenBotaniaManasteelEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenBotaniaManasteelEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBotaniaTerrasteelEntity>> A_CHICKEN_BOTANIA_TERRASTEEL = register(
			"c_terrasteel",
			EntityType.Builder.<AChickenBotaniaTerrasteelEntity>of(AChickenBotaniaTerrasteelEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenBotaniaTerrasteelEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBotaniaElementiumEntity>> A_CHICKEN_BOTANIA_ELEMENTIUM = register(
			"c_elementium",
			EntityType.Builder.<AChickenBotaniaElementiumEntity>of(AChickenBotaniaElementiumEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenBotaniaElementiumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBotaniaLivingrockEntity>> A_CHICKEN_BOTANIA_LIVINGROCK = register(
			"c_livingrock",
			EntityType.Builder.<AChickenBotaniaLivingrockEntity>of(AChickenBotaniaLivingrockEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenBotaniaLivingrockEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBotaniaLivingwoodEntity>> A_CHICKEN_BOTANIA_LIVINGWOOD = register(
			"c_livingwood",
			EntityType.Builder.<AChickenBotaniaLivingwoodEntity>of(AChickenBotaniaLivingwoodEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenBotaniaLivingwoodEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenCrimstonStemEntity>> A_CHICKEN_CRIMSTON_STEM = register("c_crimstonstem",
			EntityType.Builder.<AChickenCrimstonStemEntity>of(AChickenCrimstonStemEntity::new, MobCategory.MONSTER)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenCrimstonStemEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenWarpedStemEntity>> A_CHICKEN_WARPED_STEM = register("c_warpedstem",
			EntityType.Builder.<AChickenWarpedStemEntity>of(AChickenWarpedStemEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenWarpedStemEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenSprucewoodEntity>> A_CHICKEN_SPRUCEWOOD = register("c_sprucewood",
			EntityType.Builder.<AChickenSprucewoodEntity>of(AChickenSprucewoodEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenSprucewoodEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenGlassEntity>> A_CHICKEN_GLASS = register("c_glass",
			EntityType.Builder.<AChickenGlassEntity>of(AChickenGlassEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenGlassEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenWoolEntity>> A_CHICKEN_WOOL = register("c_wool",
			EntityType.Builder.<AChickenWoolEntity>of(AChickenWoolEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenWoolEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenSoulSandEntity>> A_CHICKEN_SOUL_SAND = register("c_soulsand",
			EntityType.Builder.<AChickenSoulSandEntity>of(AChickenSoulSandEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenSoulSandEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenNetherrackEntity>> A_CHICKEN_NETHERRACK = register("c_netherrack",
			EntityType.Builder.<AChickenNetherrackEntity>of(AChickenNetherrackEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenNetherrackEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenSoulSoilEntity>> A_CHICKEN_SOUL_SOIL = register("c_soulsoil",
			EntityType.Builder.<AChickenSoulSoilEntity>of(AChickenSoulSoilEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenSoulSoilEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBasaltEntity>> A_CHICKEN_BASALT = register("c_basalt",
			EntityType.Builder.<AChickenBasaltEntity>of(AChickenBasaltEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenBasaltEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenInkEntity>> A_CHICKEN_INK = register("c_ink",
			EntityType.Builder.<AChickenInkEntity>of(AChickenInkEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenInkEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenPaperEntity>> A_CHICKEN_PAPER = register("c_paper",
			EntityType.Builder.<AChickenPaperEntity>of(AChickenPaperEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenPaperEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenClayEntity>> A_CHICKEN_CLAY = register("c_clay",
			EntityType.Builder.<AChickenClayEntity>of(AChickenClayEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenClayEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenQuartzEntity>> A_CHICKEN_QUARTZ = register("c_quartz",
			EntityType.Builder.<AChickenQuartzEntity>of(AChickenQuartzEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenQuartzEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenAmethystShardEntity>> A_CHICKEN_AMETHYST_SHARD = register("c_amethystshard",
			EntityType.Builder.<AChickenAmethystShardEntity>of(AChickenAmethystShardEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenAmethystShardEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenEmeraldEntity>> A_CHICKEN_EMERALD = register("c_emerald",
			EntityType.Builder.<AChickenEmeraldEntity>of(AChickenEmeraldEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenEmeraldEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenTNTEntity>> A_CHICKEN_TNT = register("c_tnt",
			EntityType.Builder.<AChickenTNTEntity>of(AChickenTNTEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenTNTEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenDioriteEntity>> A_CHICKEN_DIORITE = register("c_diorite",
			EntityType.Builder.<AChickenDioriteEntity>of(AChickenDioriteEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenDioriteEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenStoneEntity>> A_CHICKEN_STONE = register("c_stone",
			EntityType.Builder.<AChickenStoneEntity>of(AChickenStoneEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenStoneEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenNetherStarEntity>> A_CHICKEN_NETHER_STAR = register("c_netherstar",
			EntityType.Builder.<AChickenNetherStarEntity>of(AChickenNetherStarEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenNetherStarEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenNetherWartEntity>> A_CHICKEN_NETHER_WART = register("c_netherwart",
			EntityType.Builder.<AChickenNetherWartEntity>of(AChickenNetherWartEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenNetherWartEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenEnderEyeEntity>> A_CHICKEN_ENDER_EYE = register("c_endereye",
			EntityType.Builder.<AChickenEnderEyeEntity>of(AChickenEnderEyeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenEnderEyeEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenGlowstoneEntity>> A_CHICKEN_GLOWSTONE = register("c_glowstone",
			EntityType.Builder.<AChickenGlowstoneEntity>of(AChickenGlowstoneEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenGlowstoneEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBlazeRodEntity>> A_CHICKEN_BLAZE_ROD = register("c_blazerod",
			EntityType.Builder.<AChickenBlazeRodEntity>of(AChickenBlazeRodEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenBlazeRodEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenSugarEntity>> A_CHICKEN_SUGAR = register("c_sugar",
			EntityType.Builder.<AChickenSugarEntity>of(AChickenSugarEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenSugarEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBoneMealEntity>> A_CHICKEN_BONE_MEAL = register("c_bonemeal",
			EntityType.Builder.<AChickenBoneMealEntity>of(AChickenBoneMealEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenBoneMealEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenEnderPearlEntity>> A_CHICKEN_ENDER_PEARL = register("c_enderpearl",
			EntityType.Builder.<AChickenEnderPearlEntity>of(AChickenEnderPearlEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenEnderPearlEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBoneEntity>> A_CHICKEN_BONE = register("c_bone",
			EntityType.Builder.<AChickenBoneEntity>of(AChickenBoneEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenBoneEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenDarkOakEntity>> A_CHICKEN_DARK_OAK = register("c_darkoak",
			EntityType.Builder.<AChickenDarkOakEntity>of(AChickenDarkOakEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenDarkOakEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenAcaciaWoodEntity>> A_CHICKEN_ACACIA_WOOD = register("c_acaciawood",
			EntityType.Builder.<AChickenAcaciaWoodEntity>of(AChickenAcaciaWoodEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenAcaciaWoodEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenJungleWoodEntity>> A_CHICKEN_JUNGLE_WOOD = register("c_junglewood",
			EntityType.Builder.<AChickenJungleWoodEntity>of(AChickenJungleWoodEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenJungleWoodEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenNautilusShellEntity>> A_CHICKEN_NAUTILUS_SHELL = register("c_nautilusshell",
			EntityType.Builder.<AChickenNautilusShellEntity>of(AChickenNautilusShellEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenNautilusShellEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenHoneycombEntity>> A_CHICKEN_HONEYCOMB = register("c_honeycomb",
			EntityType.Builder.<AChickenHoneycombEntity>of(AChickenHoneycombEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenHoneycombEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenRabbitHideEntity>> A_CHICKEN_RABBIT_HIDE = register("c_rabbithide",
			EntityType.Builder.<AChickenRabbitHideEntity>of(AChickenRabbitHideEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenRabbitHideEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenPrismarineShardEntity>> A_CHICKEN_PRISMARINE_SHARD = register("c_prismarineshard",
			EntityType.Builder.<AChickenPrismarineShardEntity>of(AChickenPrismarineShardEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenPrismarineShardEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenNetherBrickEntity>> A_CHICKEN_NETHER_BRICK = register("c_netherbrick",
			EntityType.Builder.<AChickenNetherBrickEntity>of(AChickenNetherBrickEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenNetherBrickEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenChorusFruitEntity>> A_CHICKEN_CHORUS_FRUIT = register("c_chorusfruit",
			EntityType.Builder.<AChickenChorusFruitEntity>of(AChickenChorusFruitEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenChorusFruitEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenCoalEntity>> A_CHICKEN_COAL = register("c_coal",
			EntityType.Builder.<AChickenCoalEntity>of(AChickenCoalEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenCoalEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenCharCoalEntity>> A_CHICKEN_CHAR_COAL = register("c_charcoal",
			EntityType.Builder.<AChickenCharCoalEntity>of(AChickenCharCoalEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenCharCoalEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenLeatherEntity>> A_CHICKEN_LEATHER = register("c_leather",
			EntityType.Builder.<AChickenLeatherEntity>of(AChickenLeatherEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenLeatherEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenStringEntity>> A_CHICKEN_STRING = register("c_string",
			EntityType.Builder.<AChickenStringEntity>of(AChickenStringEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenStringEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenFeatherEntity>> A_CHICKEN_FEATHER = register("c_feather",
			EntityType.Builder.<AChickenFeatherEntity>of(AChickenFeatherEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenFeatherEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickensnowEntity>> A_CHICKENSNOW = register("c_snow",
			EntityType.Builder.<AChickensnowEntity>of(AChickensnowEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickensnowEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenappleEntity>> A_CHICKENAPPLE = register("c_apple",
			EntityType.Builder.<AChickenappleEntity>of(AChickenappleEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenappleEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenspongeEntity>> A_CHICKENSPONGE = register("c_sponge",
			EntityType.Builder.<AChickenspongeEntity>of(AChickenspongeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenspongeEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenmelonEntity>> A_CHICKENMELON = register("c_melon",
			EntityType.Builder.<AChickenmelonEntity>of(AChickenmelonEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenmelonEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenmagmacreamEntity>> A_CHICKENMAGMACREAM = register("c_magmacream",
			EntityType.Builder.<AChickenmagmacreamEntity>of(AChickenmagmacreamEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenmagmacreamEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenblazepowderEntity>> A_CHICKENBLAZEPOWDER = register("c_blazepowder",
			EntityType.Builder.<AChickenblazepowderEntity>of(AChickenblazepowderEntity::new, MobCategory.MONSTER)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenblazepowderEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenglowberriesEntity>> A_CHICKENGLOWBERRIES = register("c_glowberries",
			EntityType.Builder.<AChickenglowberriesEntity>of(AChickenglowberriesEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenglowberriesEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickensweetberriesEntity>> A_CHICKENSWEETBERRIES = register("c_sweetberries",
			EntityType.Builder.<AChickensweetberriesEntity>of(AChickensweetberriesEntity::new, MobCategory.CREATURE)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickensweetberriesEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickentintedglassEntity>> A_CHICKENTINTEDGLASS = register("c_tintedglass",
			EntityType.Builder.<AChickentintedglassEntity>of(AChickentintedglassEntity::new, MobCategory.MONSTER)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickentintedglassEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickennetheriteEntity>> A_CHICKENNETHERITE = register("c_netherite",
			EntityType.Builder.<AChickennetheriteEntity>of(AChickennetheriteEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickennetheriteEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenbeetrootEntity>> A_CHICKENBEETROOT = register("c_beetroot",
			EntityType.Builder.<AChickenbeetrootEntity>of(AChickenbeetrootEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenbeetrootEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenspidereyeEntity>> A_CHICKENSPIDEREYE = register("c_spidereye",
			EntityType.Builder.<AChickenspidereyeEntity>of(AChickenspidereyeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenspidereyeEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickencarrotEntity>> A_CHICKENCARROT = register("c_carrot",
			EntityType.Builder.<AChickencarrotEntity>of(AChickencarrotEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickencarrotEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenrottenEntity>> A_CHICKENROTTEN = register("c_rotten",
			EntityType.Builder.<AChickenrottenEntity>of(AChickenrottenEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenrottenEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenghasttearEntity>> A_CHICKENGHASTTEAR = register("c_ghasttear",
			EntityType.Builder.<AChickenghasttearEntity>of(AChickenghasttearEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenghasttearEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenAluminiumEntity>> A_CHICKEN_ALUMINIUM = register("c_aluminium",
			EntityType.Builder.<AChickenAluminiumEntity>of(AChickenAluminiumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenAluminiumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenZincEntity>> A_CHICKEN_ZINC = register("c_zinc",
			EntityType.Builder.<AChickenZincEntity>of(AChickenZincEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenZincEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenSilverEntity>> A_CHICKEN_SILVER = register("c_silver",
			EntityType.Builder.<AChickenSilverEntity>of(AChickenSilverEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenSilverEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenNickelEntity>> A_CHICKEN_NICKEL = register("c_nickel",
			EntityType.Builder.<AChickenNickelEntity>of(AChickenNickelEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenNickelEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenAdamantiumEntity>> A_CHICKEN_ADAMANTIUM = register("c_adamantium",
			EntityType.Builder.<AChickenAdamantiumEntity>of(AChickenAdamantiumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenAdamantiumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBrassEntity>> A_CHICKEN_BRASS = register("c_brass",
			EntityType.Builder.<AChickenBrassEntity>of(AChickenBrassEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenBrassEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenChromeEntity>> A_CHICKEN_CHROME = register("c_chrome",
			EntityType.Builder.<AChickenChromeEntity>of(AChickenChromeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenChromeEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenElectrumEntity>> A_CHICKEN_ELECTRUM = register("c_electrum",
			EntityType.Builder.<AChickenElectrumEntity>of(AChickenElectrumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenElectrumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenInvarEntity>> A_CHICKEN_INVAR = register("c_invar",
			EntityType.Builder.<AChickenInvarEntity>of(AChickenInvarEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenInvarEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenIridiumEntity>> A_CHICKEN_IRIDIUM = register("c_iridium",
			EntityType.Builder.<AChickenIridiumEntity>of(AChickenIridiumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenIridiumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenPlatinumEntity>> A_CHICKEN_PLATINUM = register("c_platinum",
			EntityType.Builder.<AChickenPlatinumEntity>of(AChickenPlatinumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenPlatinumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenRefinedironEntity>> A_CHICKEN_REFINEDIRON = register("c_refinediron",
			EntityType.Builder.<AChickenRefinedironEntity>of(AChickenRefinedironEntity::new, MobCategory.MONSTER)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenRefinedironEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenTitaniumEntity>> A_CHICKEN_TITANIUM = register("c_titanium",
			EntityType.Builder.<AChickenTitaniumEntity>of(AChickenTitaniumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenTitaniumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenTungstenEntity>> A_CHICKEN_TUNGSTEN = register("c_tungsten",
			EntityType.Builder.<AChickenTungstenEntity>of(AChickenTungstenEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenTungstenEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenTungstensteelEntity>> A_CHICKEN_TUNGSTENSTEEL = register("c_tungstensteel",
			EntityType.Builder.<AChickenTungstensteelEntity>of(AChickenTungstensteelEntity::new, MobCategory.MONSTER)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenTungstensteelEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenYelloriumEntity>> A_CHICKEN_YELLORIUM = register("c_yellorium",
			EntityType.Builder.<AChickenYelloriumEntity>of(AChickenYelloriumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenYelloriumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBlutoniumEntity>> A_CHICKEN_BLUTONIUM = register("c_blutonium",
			EntityType.Builder.<AChickenBlutoniumEntity>of(AChickenBlutoniumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenBlutoniumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenAllthemodiumEntity>> A_CHICKEN_ALLTHEMODIUM = register("c_allthemodium",
			EntityType.Builder.<AChickenAllthemodiumEntity>of(AChickenAllthemodiumEntity::new, MobCategory.MONSTER)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenAllthemodiumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenVibraniumEntity>> A_CHICKEN_VIBRANIUM = register("c_vibranium",
			EntityType.Builder.<AChickenVibraniumEntity>of(AChickenVibraniumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenVibraniumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenUnobtainiumEntity>> A_CHICKEN_UNOBTAINIUM = register("c_unobtainium",
			EntityType.Builder.<AChickenUnobtainiumEntity>of(AChickenUnobtainiumEntity::new, MobCategory.MONSTER)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenUnobtainiumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenEndstoneEntity>> A_CHICKEN_ENDSTONE = register("c_endstone",
			EntityType.Builder.<AChickenEndstoneEntity>of(AChickenEndstoneEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenEndstoneEntity::new)
					.sized(0.4f, 0.7f));

	public static final RegistryObject<EntityType<AChickenCobaldEntity>> A_CHICKEN_COBALD = register("c_cobald",
			EntityType.Builder.<AChickenCobaldEntity>of(AChickenCobaldEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenCobaldEntity::new)
					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenHepatizonEntity>> A_CHICKEN_HEPATIZON = register("c_hepatizon",
			EntityType.Builder.<AChickenHepatizonEntity>of(AChickenHepatizonEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenHepatizonEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenKnightSlimeEntity>> A_CHICKEN_KNIGHT_SLIME = register("c_knightslime",
			EntityType.Builder.<AChickenKnightSlimeEntity>of(AChickenKnightSlimeEntity::new, MobCategory.MONSTER)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenKnightSlimeEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenManyullynEntity>> A_CHICKEN_MANYULLYN = register("c_manyullyn",
			EntityType.Builder.<AChickenManyullynEntity>of(AChickenManyullynEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenManyullynEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenPigIronEntity>> A_CHICKEN_PIG_IRON = register("c_pigiron",
			EntityType.Builder.<AChickenPigIronEntity>of(AChickenPigIronEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenPigIronEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenQueenSlimeEntity>> A_CHICKEN_QUEEN_SLIME = register("c_queenslime",
			EntityType.Builder.<AChickenQueenSlimeEntity>of(AChickenQueenSlimeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenQueenSlimeEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenRoseGoldEntity>> A_CHICKEN_ROSE_GOLD = register("c_rosegold",
			EntityType.Builder.<AChickenRoseGoldEntity>of(AChickenRoseGoldEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenRoseGoldEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenSlimesteelEntity>> A_CHICKEN_SLIMESTEEL = register("c_slimesteel",
			EntityType.Builder.<AChickenSlimesteelEntity>of(AChickenSlimesteelEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenSlimesteelEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenAmethystBronzeEntity>> A_CHICKEN_AMETHYST_BRONZE = register("c_amethystbronze",
			EntityType.Builder.<AChickenAmethystBronzeEntity>of(AChickenAmethystBronzeEntity::new, MobCategory.MONSTER)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenAmethystBronzeEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenQuartzEnrichedIronEntity>> A_CHICKEN_QUARTZ_ENRICHED_IRON = register(
			"c_quartzenrichediron",
			EntityType.Builder.<AChickenQuartzEnrichedIronEntity>of(AChickenQuartzEnrichedIronEntity::new, MobCategory.MONSTER)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(AChickenQuartzEnrichedIronEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenApatiteEntity>> A_CHICKEN_APATITE = register("c_apatite",
			EntityType.Builder.<AChickenApatiteEntity>of(AChickenApatiteEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenApatiteEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBasalzEntity>> A_CHICKEN_BASALZ = register("c_basalz",
			EntityType.Builder.<AChickenBasalzEntity>of(AChickenBasalzEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenBasalzEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBitumenEntity>> A_CHICKEN_BITUMEN = register("c_bitumen",
			EntityType.Builder.<AChickenBitumenEntity>of(AChickenBitumenEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenBitumenEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBlitzEntity>> A_CHICKEN_BLITZ = register("c_blitz",
			EntityType.Builder.<AChickenBlitzEntity>of(AChickenBlitzEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenBlitzEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenBlizzEntity>> A_CHICKEN_BLIZZ = register("c_blizz",
			EntityType.Builder.<AChickenBlizzEntity>of(AChickenBlizzEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenBlizzEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenCinnabarEntity>> A_CHICKEN_CINNABAR = register("c_cinnabar",
			EntityType.Builder.<AChickenCinnabarEntity>of(AChickenCinnabarEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenCinnabarEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenCokeEntity>> A_CHICKEN_COKE = register("c_coke",
			EntityType.Builder.<AChickenCokeEntity>of(AChickenCokeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenCokeEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenConstantanEntity>> A_CHICKEN_CONSTANTAN = register("c_constantan",
			EntityType.Builder.<AChickenConstantanEntity>of(AChickenConstantanEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenConstantanEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenEnderiumEntity>> A_CHICKEN_ENDERIUM = register("c_enderium",
			EntityType.Builder.<AChickenEnderiumEntity>of(AChickenEnderiumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenEnderiumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenLumiumEntity>> A_CHICKEN_LUMIUM = register("c_lumium",
			EntityType.Builder.<AChickenLumiumEntity>of(AChickenLumiumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenLumiumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenNiterEntity>> A_CHICKEN_NITER = register("c_niter",
			EntityType.Builder.<AChickenNiterEntity>of(AChickenNiterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenNiterEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenSapphireEntity>> A_CHICKEN_SAPPHIRE = register("c_sapphire",
			EntityType.Builder.<AChickenSapphireEntity>of(AChickenSapphireEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenSapphireEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenRubyEntity>> A_CHICKEN_RUBY = register("c_ruby",
			EntityType.Builder.<AChickenRubyEntity>of(AChickenRubyEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenRubyEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenSignalumEntity>> A_CHICKEN_SIGNALUM = register("c_signalum",
			EntityType.Builder.<AChickenSignalumEntity>of(AChickenSignalumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenSignalumEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenSulfurEntity>> A_CHICKEN_SULFUR = register("c_sulfur",
			EntityType.Builder.<AChickenSulfurEntity>of(AChickenSulfurEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenSulfurEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<AChickenTarEntity>> A_CHICKEN_TAR = register("c_tar",
			EntityType.Builder.<AChickenTarEntity>of(AChickenTarEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AChickenTarEntity::new)

					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<GhostChickenEntity>> GHOST_CHICKEN = register("c_ghostchicken",
			EntityType.Builder.<GhostChickenEntity>of(GhostChickenEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(GhostChickenEntity::new)

					.sized(0.4f, 0.7f));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			AChickenCobbleEntity.init();
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
