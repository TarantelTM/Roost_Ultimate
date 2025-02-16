package net.tarantel.chickenroost;

import cech12.bucketlib.api.BucketLibApi;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
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
import net.tarantel.chickenroost.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.GeckoLib;

import java.io.FileNotFoundException;
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
	public ChickenRoostMod() throws FileNotFoundException {
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		GeckoLib.initialize();
		ModEntities.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
		ModItems.register(FMLJavaModLoadingContext.get().getModEventBus());
		ModCreativeModeTabs.register(bus);
		ModEntitySpawn.SERIALIZER.register(bus);
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC);
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
	public void addCreative(BuildCreativeModeTabContentsEvent event)  {
		if(event.getTab() == ModCreativeModeTabs.TAB_CHICKEN_ROOST_TAB.get()){
				for (int i = 0; i < ModItems.ITEMS.getEntries().size(); i++) {
					event.accept(ModItems.ITEMS.getEntries().stream().toList().get(i).get().asItem());
				}
			event.accept(ModBlocks.BREEDER.get());
			event.accept(ModItems.TRAINER.get());
			event.accept(ModBlocks.SOUL_EXTRACTOR.get());
			event.accept(ModBlocks.ROOST.get());
			event.accept(ModItems.SOUL_BREEDER.get());
			event.accept(ModItems.CHICKENSTORAGE.get());
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
		public static void sendImc(InterModEnqueueEvent evt) {
			//register your bucket at the BucketLib mod to activate all features for your bucket
			BucketLibApi.registerBucket(ModItems.LAVA_EGG.getId());
			BucketLibApi.registerBucket(ModItems.WATER_EGG.getId());
			ModEntities.initChickenConfig();
		}
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

        }
    }
}
