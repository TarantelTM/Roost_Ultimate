package net.tarantel.chickenroost;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import net.tarantel.chickenroost.block.tile.render.*;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.handler.ModHandlers;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.screen.*;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModDataComponents;
import net.tarantel.chickenroost.util.ModEntitySpawn;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.util.Random;



@Mod(ChickenRoostMod.MODID)
public class ChickenRoostMod {
    //region

    public static final String MODID = "chicken_roost";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String PROTOCOL_VERSION = "1";
    //endregion
    public ChickenRoostMod(IEventBus bus, ModContainer modContainer) throws FileNotFoundException {
        bus.addListener(this::commonSetup);
        ModCreativeModeTabs.register(bus);
        ModDataComponents.register(bus);
        ModItems.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModEntitySpawn.SERIALIZER.register(bus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC, "roostultimate/common.toml");
        ModBlocks.BLOCKS.register(bus);
        ModBlockEntities.register(bus);
        bus.addListener(this::registerCapabilities);

        ModHandlers.register(bus);
        ModRecipes.RECIPE_SERIALIZERS.register(bus);
        ModRecipes.RECIPE_TYPES.register(bus);
        bus.addListener(this::addCreative);
        NeoForge.EVENT_BUS.register(this);
    }


    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        ModBlockEntities.registerCapabilities(event);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {


    }

    @Deprecated
    public static final Random RANDOM = new Random();
    private static int messageID = 0;
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    public void addCreative(BuildCreativeModeTabContentsEvent event)  {
        if(event.getTab() == ModCreativeModeTabs.TAB_CHICKEN_ROOST_TAB.get()){
            for (int i = 0; i < ModItems.ITEMS.getEntries().size(); i++) {
                event.accept(ModItems.ITEMS.getEntries().stream().toList().get(i).get().asItem());
            }
            event.accept(ModBlocks.BREEDER.get());
            event.accept(ModItems.TRAINER.get());
            event.accept(ModBlocks.SOUL_EXTRACTOR.get());
            event.accept(ModBlocks.ROOST.get());
            event.accept(ModItems.CHICKENSTORAGE.get());
            event.accept(ModItems.SOUL_BREEDER.get());

        }

    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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
        public static void onClientSetup(RegisterMenuScreensEvent event) {
            event.register(ModHandlers.SOUL_BREEDER_MENU.get(), Soul_Breeder_Screen::new);
            event.register(ModHandlers.BREEDER_MENU.get(), Breeder_Screen::new);
            event.register(ModHandlers.SOUL_EXTRACTOR_MENU.get(), Soul_Extractor_Screen::new);
            event.register(ModHandlers.ROOST_MENU_V1.get(), Roost_Screen::new);
            event.register(ModHandlers.TRAINER.get(), Trainer_Screen::new);
            event.register(ModHandlers.BOOK.get(), Chicken_Book_Screen::new);

        }
    }

    public static ResourceLocation ownresource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, path);
    }

    public static ResourceLocation commonsource(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }

}
