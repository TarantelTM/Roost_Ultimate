package net.tarantel.chickenroost;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import net.tarantel.chickenroost.block.tile.render.*;
import net.tarantel.chickenroost.client.tooltip.ClientStackLineTooltip;
import net.tarantel.chickenroost.client.tooltip.StackLineTooltip;
import net.tarantel.chickenroost.handler.ModHandlers;
import net.tarantel.chickenroost.screen.*;
import net.tarantel.chickenroost.util.Config;

@SuppressWarnings("removal")
@Mod(value = "chicken_roost", dist = Dist.CLIENT)
public class ChickenRoostModClient {

    public ChickenRoostModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        container.registerConfig(ModConfig.Type.CLIENT, Config.ClientSpec);
    }

    @EventBusSubscriber(modid = "chicken_roost", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.BREEDER.get(),
                    BreederChickenRender::new);
            event.registerBlockEntityRenderer(ModBlockEntities.ROOST.get(),
                    RoostChickenRender::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SOUL_EXTRACTOR.get(),
                    ExtractorChickenRender::new);
            event.registerBlockEntityRenderer(ModBlockEntities.TRAINER.get(),
                    AnimatedTrainerRenderer::new);
        }

        @SubscribeEvent
        public static void onClientSetup(RegisterMenuScreensEvent event) {
            event.register(ModHandlers.BREEDER_MENU.get(), BreederScreen::new);
            event.register(ModHandlers.SOUL_EXTRACTOR_MENU.get(), SoulExtractorScreen::new);
            event.register(ModHandlers.ROOST_MENU_V1.get(), RoostScreen::new);
            event.register(ModHandlers.TRAINER.get(), TrainerScreen::new);
            event.register(ModHandlers.COLLECTOR_MENU.get(), CollectorScreen::new);
            event.register(ModHandlers.FEEDER_MENU.get(), FeederScreen::new);
        }

        @SubscribeEvent
        public static void registerTooltipFactories(RegisterClientTooltipComponentFactoriesEvent e) {
            e.register(StackLineTooltip.class, ClientStackLineTooltip::new);
        }
    }



}