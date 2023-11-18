package net.tarantel.chickenroost;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import net.tarantel.chickenroost.block.tile.render.*;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.handler.ModHandlers;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.network.ModMessages;
import net.tarantel.chickenroost.particle.ModParticles;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.screen.*;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModEntitySpawn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.GeckoLib;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("ALL")
@Mod("chicken_roost")
public class ChickenRoostMod {
	public static final Logger LOGGER = LogManager.getLogger(ChickenRoostMod.class);
	public static final String MODID = "chicken_roost";
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID = 0;
	public ChickenRoostMod() {
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		GeckoLib.initialize();
		ModCreativeModeTabs.register(bus);
		ModItems.ITEMS.register(bus);
		ModEntities.REGISTRY.register(bus);
		ModEntitySpawn.SERIALIZER.register(bus);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC, "roostultimate_common_1.0.2.toml");
		ModBlocks.BLOCKS.register(bus);
		ModBlockEntities.register(bus);
        ModHandlers.register(bus);

		ModParticles.register(bus);
		ModRecipes.RECIPE_SERIALIZERS.register(bus);
		ModRecipes.RECIPE_TYPES.register(bus);
		bus.addListener(this::addCreative);
		bus.addListener(this::commonSetup);

		MinecraftForge.EVENT_BUS.register(this);
	}
	private void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			ModMessages.register();
		});
	}

	@Deprecated
	public static final Random RANDOM = new Random();

	private void addCreative(BuildCreativeModeTabContentsEvent event) /*throws NoSuchMethodException*/ {
		if(event.getTab() == ModCreativeModeTabs.TAB_CHICKEN_ROOST_TAB.get()){
			//Mod
			event.accept(ModItems.CHICKEN_BOOK.get());
			event.accept(ModBlocks.BREEDER.get());
			event.accept(ModBlocks.SOUL_EXTRACTOR.get());
			//event.accept(ModBlocks.SOUL_BREEDER.get());
			event.accept(ModItems.TRAINER.get());
			event.accept(ModBlocks.ROOST.get());
			event.accept(ModItems.SOUL_BREEDER.get());
			event.accept(ModItems.CHICKEN_SCANNER.get());
			event.accept(ModItems.CHICKEN_STICK.get());
			event.accept(ModItems.CHICKEN_FOOD_TIER_1.get());
			event.accept(ModItems.CHICKEN_FOOD_TIER_2.get());
			event.accept(ModItems.CHICKEN_FOOD_TIER_3.get());
			event.accept(ModItems.CHICKEN_FOOD_TIER_4.get());
			event.accept(ModItems.CHICKEN_FOOD_TIER_5.get());
			event.accept(ModItems.CHICKEN_FOOD_TIER_6.get());
			event.accept(ModItems.CHICKEN_FOOD_TIER_7.get());
			event.accept(ModItems.CHICKEN_FOOD_TIER_8.get());
			event.accept(ModItems.CHICKEN_FOOD_TIER_9.get());
			event.accept(ModItems.CHICKEN_ESSENCE_TIER_1.get());
			event.accept(ModItems.CHICKEN_ESSENCE_TIER_2.get());
			event.accept(ModItems.CHICKEN_ESSENCE_TIER_3.get());
			event.accept(ModItems.CHICKEN_ESSENCE_TIER_4.get());
			event.accept(ModItems.CHICKEN_ESSENCE_TIER_5.get());
			event.accept(ModItems.CHICKEN_ESSENCE_TIER_6.get());
			event.accept(ModItems.CHICKEN_ESSENCE_TIER_7.get());
			event.accept(ModItems.CHICKEN_ESSENCE_TIER_8.get());
			event.accept(ModItems.CHICKEN_ESSENCE_TIER_9.get());
			//VANILLA
			event.accept(ModItems.E_CHICKEN_OAKWOOD.get());
			event.accept(ModItems.E_CHICKEN_ANDESITE.get());
			event.accept(ModItems.E_CHICKEN_SAND.get());
			event.accept(ModItems.CHICKENCHICKEN.get());
			event.accept(ModItems.E_CHICKEN_GRAVEL.get());
			event.accept(ModItems.E_CHICKEN_COBBLE.get());
			event.accept(ModItems.E_CHICKEN_DARK_OAK.get());
			event.accept(ModItems.E_CHICKEN_GRANIT.get());
			event.accept(ModItems.E_CHICKEN_BIRCHWOOD.get());
			event.accept(ModItems.E_CHICKEN_SPRUCEWOOD.get());
			event.accept(ModItems.E_CHICKEN_HONEYCOMB.get());
			event.accept(ModItems.ECHICKENFEATHER.get());
			event.accept(ModItems.E_CHICKEN_WOOL.get());
			event.accept(ModItems.E_CHICKEN_ACACIAWOOD.get());
			event.accept(ModItems.E_CHICKEN_STONE.get());
			event.accept(ModItems.E_CHICKEN_DIORITE.get());
			event.accept(ModItems.E_CHICKEN_JUNGLEWOOD.get());
			event.accept(ModItems.E_CHICKEN_MELON.get());
			event.accept(ModItems.E_CHICKEN_WARPED_STEM.get());
			event.accept(ModItems.E_CHICKEN_NETHERRACK.get());
			event.accept(ModItems.ECHICKENSNOW.get());
			event.accept(ModItems.E_CHICKEN_GLASS.get());
			event.accept(ModItems.E_CHICKEN_SUGAR.get());
			event.accept(ModItems.E_CHICKEN_CRIMSTON_STEM.get());
			event.accept(ModItems.E_CHICKEN_FLINT.get());
			event.accept(ModItems.ECHICKENAPPLE.get());
			event.accept(ModItems.E_CHICKEN_BONE.get());
			event.accept(ModItems.E_CHICKEN_BONE_MEAL.get());
			event.accept(ModItems.E_CHICKEN_CHAR_COAL.get());
			event.accept(ModItems.E_CHICKENCARROT.get());
			event.accept(ModItems.E_CHICKEN_SWEETBERRIES.get());
			event.accept(ModItems.E_CHICKEN_PAPER.get());
			event.accept(ModItems.E_CHICKENGLOWBERRIES.get());
			event.accept(ModItems.E_CHICKEN_COAL.get());
			event.accept(ModItems.E_CHICKENBEETROOT.get());
			event.accept(ModItems.E_CHICKEN_INK.get());
			event.accept(ModItems.E_CHICKEN_SOUL_SOIL.get());
			event.accept(ModItems.E_CHICKEN_STRING.get());
			event.accept(ModItems.E_CHICKEN_BASALT.get());
			event.accept(ModItems.E_CHICKEN_COPPER.get());
			event.accept(ModItems.E_CHICKEN_RABBIT_HIDE.get());
			event.accept(ModItems.E_CHICKEN_CLAY.get());
			event.accept(ModItems.E_CHICKEN_SPIDEREYE.get());
			event.accept(ModItems.E_CHICKEN_SOUL_SAND.get());
			event.accept(ModItems.ECHICKENSPONGE.get());
			event.accept(ModItems.E_CHICKEN_LEATHER.get());
			event.accept(ModItems.E_CHICKEN_NETHER_WART.get());

			event.accept(ModItems.E_CHICKEN_REDSTONE.get());
			event.accept(ModItems.E_CHICKEN_LAPIS.get());
			event.accept(ModItems.E_CHICKEN_OBSIDIAN.get());
			event.accept(ModItems.E_CHICKEN_MAGMACREAM.get());
			event.accept(ModItems.E_CHICKEN_IRON.get());
			event.accept(ModItems.E_CHICKEN_ROTTEN.get());
			event.accept(ModItems.E_CHICKEN_SLIME.get());
			event.accept(ModItems.E_CHICKEN_GOLD.get());
			event.accept(ModItems.E_CHICKENBLAZEPOWDER.get());
			event.accept(ModItems.E_CHICKEN_QUARTZ.get());
			event.accept(ModItems.E_CHICKEN_CHORUS_FRUIT.get());
			event.accept(ModItems.E_CHICKEN_GLOWSTONE.get());
			event.accept(ModItems.E_CHICKEN_ENDSTONE.get());
			event.accept(ModItems.E_CHICKEN_ENDER_PEARL.get());
			event.accept(ModItems.E_CHICKEN_TNT.get());
			event.accept(ModItems.E_CHICKEN_BLAZE_ROD.get());
			event.accept(ModItems.E_CHICKEN_ENDER_EYE.get());
			event.accept(ModItems.E_CHICKEN_EMERALD.get());
			event.accept(ModItems.E_CHICKEN_GHASTTEAR.get());
			event.accept(ModItems.E_CHICKEN_NETHERITE.get());
			event.accept(ModItems.E_CHICKEN_DIAMOND.get());
			event.accept(ModItems.E_CHICKEN_NETHER_STAR.get());


			//BASIC MOD MATS
			event.accept(ModItems.E_CHICKEN_MEKANISM_LEAD.get());
			event.accept(ModItems.E_CHICKEN_CHROME.get());
			event.accept(ModItems.E_CHICKEN_SILVER.get());
			event.accept(ModItems.ECHICKENENDERIUM.get());
			event.accept(ModItems.ECHICKENLUMIUM.get());
			event.accept(ModItems.E_CHICKEN_SIGNALUM.get());
			event.accept(ModItems.E_CHICKEN_MEKANISM_STEEL.get());
			event.accept(ModItems.E_CHICKEN_TUNGSTEN.get());
			event.accept(ModItems.E_CHICKEN_MEKANISM_URANIUM.get());
			event.accept(ModItems.E_CHICKEN_ZINC.get());
			event.accept(ModItems.E_CHICKEN_MEKANISM_BRONZE.get());
			event.accept(ModItems.E_CHICKEN_ALUMINIUM.get());
			event.accept(ModItems.E_CHICKEN_MEKANISM_TIN.get());
			event.accept(ModItems.E_CHICKEN_NICKEL.get());


			event.accept(ModItems.E_CHICKEN_IRIDIUM.get());
			event.accept(ModItems.E_CHICKEN_ADAMANTIUM.get());
			event.accept(ModItems.E_CHICKEN_PLATINUM.get());
			event.accept(ModItems.E_CHICKEN_TITANIUM.get());
			event.accept(ModItems.E_CHICKEN_TUNGSTENSTEEL.get());
			event.accept(ModItems.E_CHICKEN_ELECTRUM.get());
			event.accept(ModItems.E_CHICKEN_INVAR.get());

			if(ModList.get().isLoaded("techreborn")){
				event.accept(ModItems.E_CHICKEN_REFINED_IRON.get());

			}



			if(ModList.get().isLoaded("bigreactors")){
				event.accept(ModItems.E_CHICKEN_YELLORIUM.get());

			}

			if(ModList.get().isLoaded("bigreactors") || ModList.get().isLoaded("biggerreactors")){
				event.accept(ModItems.E_CHICKEN_BLUTONIUM.get());

			}
			if(ModList.get().isLoaded("create")){
				event.accept(ModItems.E_CHICKEN_BRASS.get());

			}
			if(ModList.get().isLoaded("silentgems")){
				event.accept(ModItems.ECHICKENSAPPHIRE.get());
				event.accept(ModItems.ECHICKENRUBY.get());
			}
			if(ModList.get().isLoaded("refinedstorage")){
				event.accept(ModItems.ECHICKENQUARTZENRICHEDIRON.get());
			}
			if(ModList.get().isLoaded("ae2")){
				event.accept(ModItems.E_CHICKEN_CERTUSQ.get());
				event.accept(ModItems.E_CHICKEN_AE_SILICON.get());
			}
			if(ModList.get().isLoaded("thermal_foundation")){
				event.accept(ModItems.ECHICKENBLIZZ.get());
				event.accept(ModItems.ECHICKENBLITZ.get());
				event.accept(ModItems.ECHICKENCONSTANTAN.get());
				event.accept(ModItems.ECHICKENBITUMEN.get());
				event.accept(ModItems.ECHICKENCINNABAR.get());
				event.accept(ModItems.ECHICKENCOKE.get());
				event.accept(ModItems.ECHICKENNITER.get());
				event.accept(ModItems.ECHICKENBASALZ.get());
				event.accept(ModItems.ECHICKENAPATITE.get());
				event.accept(ModItems.E_CHICKEN_TAR.get());
				event.accept(ModItems.E_CHICKEN_SULFUR.get());

			}
			if(ModList.get().isLoaded("mekanism")){
				event.accept(ModItems.E_OSMIUM_CHICKEN.get());
				event.accept(ModItems.E_CHICKEN_MEKANISM_BIO_FUEL.get());
			}
			if(ModList.get().isLoaded("allthemodium")){
				event.accept(ModItems.E_CHICKEN_ALLTHEMODIUM.get());
				event.accept(ModItems.E_CHICKEN_UNOBTAINIUM.get());
				event.accept(ModItems.E_CHICKEN_VIBRANIUM.get());
			}
			if(ModList.get().isLoaded("tconstruct")){
				event.accept(ModItems.ECHICKENCOBALD.get());
				event.accept(ModItems.ECHICKENMANYULLYN.get());
				event.accept(ModItems.ECHICKENAMETHYSTBRONZE.get());
				event.accept(ModItems.ECHICKENKNIGHTSLIME.get());
				event.accept(ModItems.ECHICKENSLIMESTEEL.get());
				event.accept(ModItems.ECHICKENPIGIRON.get());
				event.accept(ModItems.ECHICKENROSEGOLD.get());
				event.accept(ModItems.ECHICKENHEPATIZON.get());

			}
			if(ModList.get().isLoaded("botania")){
				event.accept(ModItems.E_CHICKEN_BOTANIA_ELEMENTIUM.get());
				event.accept(ModItems.E_CHICKEN_BOTANIA_TERRASTEEL.get());
				event.accept(ModItems.E_CHICKEN_BOTANIA_MANASTEEL.get());
				event.accept(ModItems.E_CHICKEN_BOTANIA_LIVINGWOOD.get());
				event.accept(ModItems.E_CHICKEN_BOTANIA_LIVINGROCK.get());
			}
		}

	}
	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
			BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}
	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
		@SubscribeEvent
		public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
			event.registerBlockEntityRenderer(ModBlockEntities.BREEDER.get(),
					BreederChickenRender::new);
			event.registerBlockEntityRenderer(ModBlockEntities.ROOST.get(),
					RoostChickenRender::new);
			event.registerBlockEntityRenderer(ModBlockEntities.SOUL_EXTRACTOR.get(),
					ExtractorChickenRender::new);
			event.registerBlockEntityRenderer(ModBlockEntities.SOUL_BREEDER.get(),
					AnimatedSoulBreederRenderer::new);
			event.registerBlockEntityRenderer(ModBlockEntities.TRAINER.get(),
					AnimatedTrainerRenderer::new);

		}
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
			MenuScreens.register(ModHandlers.NEW_SOUL_BREEDER_MENU.get(), Soul_Breeder_Screen::new);
			MenuScreens.register(ModHandlers.BREEDER_MENU.get(), Breeder_Screen::new);
			MenuScreens.register(ModHandlers.SOUL_EXTRACTOR_MENU.get(), Soul_Extractor_Screen::new);
			MenuScreens.register(ModHandlers.ROOST_MENU_V1.get(), Roost_Screen::new);
			MenuScreens.register(ModHandlers.TRAINER.get(), Trainer_Screen::new);
			MenuScreens.register(ModHandlers.BOOK.get(), Chicken_Book_Screen::new);
			//BlockEntityRenderers.register(ModBlockEntities.SOUL_BREEDER.get(), AnimatedSoulBreederRenderer::new);
			//Minecraft.getInstance().particleEngine.register(ModParticles.SOUL_PARTICLES.get(),
			//		SoulParticles.Provider::new);

        }
		/*@SubscribeEvent
		public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
			Minecraft.getInstance().particleEngine.register(ModParticles.SOUL_PARTICLES.get(),
					SoulParticles.Provider::new);
		}*/
		//@SubscribeEvent
		/*public static void registerFactories(RegisterParticleProvidersEvent evt) {
			evt.registerSprite(ModParticles.SOUL_PARTICLES, ParticleProvide);
			Minecraft.getInstance().particleEngine.register(ModParticles.SOUL_PARTICLES.get(),
					SoulParticles.Provider::new);
		}*/
		/*@SubscribeEvent
		public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
			Minecraft.getInstance().particleEngine.register(ModParticles.SOUL_PARTICLES.get(),
					SoulParticles.Provider::new);
		}*/
    }
}
