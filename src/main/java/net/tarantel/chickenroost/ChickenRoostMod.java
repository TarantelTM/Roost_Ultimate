package net.tarantel.chickenroost;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tarantel.chickenroost.block.ModBlocks;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import net.tarantel.chickenroost.block.tile.render.*;
import net.tarantel.chickenroost.handlers.ModHandlers;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.screen.*;
import net.tarantel.chickenroost.util.ModEntities;
import net.tarantel.chickenroost.util.ModTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod("chicken_roost")
public class ChickenRoostMod {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "chicken_roost";
	private static final String PROTOCOL_VERSION = "1";
	private static int messageID = 0;
	public ChickenRoostMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		GeckoLib.initialize();
		ModTabs.load();
		ModBlocks.BLOCKS.register(bus);
		ModItems.ITEMS.register(bus);
		ModEntities.REGISTRY.register(bus);
		ModBlockEntities.TILES.register(bus);
		ModHandlers.register(bus);
		ModRecipes.RECIPE_SERIALIZERS.register(bus);
		ModRecipes.RECIPE_TYPES.register(bus);
		bus.addListener(this::setup);
        bus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC, "common_config.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SpawnConfig.SPEC, "spawn_config.toml");

	}

	private void clientSetup(final FMLClientSetupEvent event) {

    }
	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
			event.registerBlockEntityRenderer(ModBlockEntities.BREEDER.get(),
					BreederChickenRender::new);
			event.registerBlockEntityRenderer(ModBlockEntities.ROOST.get(),
					RoostChickenRender::new);
			event.registerBlockEntityRenderer(ModBlockEntities.SOUL_EXTRACTOR.get(),
					ExtractorChickenRender::new);
			/*event.registerBlockEntityRenderer(ModBlockEntities.SOUL_BREEDER.get(),
					AnimatedSoulBreederRenderer::new);*/
			/*event.registerBlockEntityRenderer(ModBlockEntities.TRAINER.get(),
					AnimatedTrainerRenderer::new);*/

			event.registerBlockEntityRenderer(ModBlockEntities.TRAINER.get(), TrainerRendererr::new);
			event.registerBlockEntityRenderer(ModBlockEntities.SOUL_BREEDER.get(), SoulBreederRendererr::new);

		}
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			MenuScreens.register(ModHandlers.NEW_SOUL_BREEDER_MENU.get(), Soul_Breeder_Screen::new);
			MenuScreens.register(ModHandlers.BREEDER_MENU.get(), Breeder_Screen::new);
			MenuScreens.register(ModHandlers.SOUL_EXTRACTOR_MENU.get(), Soul_Extractor_Screen::new);
			MenuScreens.register(ModHandlers.ROOST_MENU_V1.get(), Roost_Screen::new);
			MenuScreens.register(ModHandlers.TRAINER.get(), Trainer_Screen::new);
			MenuScreens.register(ModHandlers.BOOK.get(), Chicken_Book_Screen::new);



		}

		@SubscribeEvent
		public static void registerRenderers(final FMLClientSetupEvent event) {
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.TRAINER.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.SOUL_BREEDER.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.SEED_CROP_1.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.SEED_CROP_2.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.SEED_CROP_3.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.SEED_CROP_4.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.SEED_CROP_5.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.SEED_CROP_6.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.SEED_CROP_7.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.SEED_CROP_8.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.SEED_CROP_9.get(), RenderType.cutout());


		}
	}
    private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
		});
    }
}
