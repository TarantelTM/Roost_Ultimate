package net.tarantel.chickenroost;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.handler.ModHandlers;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.networking.ModNetworking;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.util.*;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;



@Mod(ChickenRoostMod.MODID)
public class ChickenRoostMod {
    public static final String MODID = "chicken_roost";
    public static final Logger LOGGER = LogUtils.getLogger();
    public ChickenRoostMod(IEventBus bus, ModContainer modContainer) throws FileNotFoundException {
        bus.addListener(ModNetworking::onRegisterPayloadHandlers);
        GsonChickenReader.readItemsFromFile();
        ModCreativeModeTabs.register(bus);
        ModDataComponents.register(bus);
        ModEntities.register(bus);
        ModItems.register(bus);
        ModEntitySpawnMonster.SERIALIZER.register(bus);
        ModEntitySpawnMob.SERIALIZER.register(bus);
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.ClientSpec);
        ModBlocks.BLOCKS.register(bus);
        ModBlockEntities.register(bus);
        bus.addListener(this::registerCapabilities);
        ModHandlers.register(bus);
        ModRecipes.RECIPE_SERIALIZERS.register(bus);
        ModRecipes.RECIPE_TYPES.register(bus);
        NeoForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);

    }


    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        ModBlockEntities.registerCapabilities(event);
    }


    @Deprecated
    public static final Random RANDOM = new Random();

    public void addCreative(BuildCreativeModeTabContentsEvent event)  {
        if(event.getTab() == ModCreativeModeTabs.TAB_CHICKEN_ROOST_TAB.get()){
            for (int i = 0; i < ModItems.ITEMS.getEntries().size(); i++) {
                event.accept(ModItems.ITEMS.getEntries().stream().toList().get(i).get().asItem());
            }
            for (int i = 0; i < ModItems.CHICKENITEMS.getEntries().size(); i++) {
                event.accept(ModItems.CHICKENITEMS.getEntries().stream().toList().get(i).get().asItem());
            }
            event.accept(ModBlocks.BREEDER.get());
            event.accept(ModItems.TRAINER.get());
            event.accept(ModBlocks.SOUL_EXTRACTOR.get());
            event.accept(ModBlocks.ROOST.get());
            event.accept(ModItems.CHICKENSTORAGE.get());
            event.accept(ModItems.SOUL_BREEDER.get());
            event.accept(ModBlocks.SLIMEBLOCK.get());
            event.accept(ModBlocks.COLLECTOR.get());
            event.accept(ModBlocks.FEEDER.get());
        }

    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        LOGGER.info("Player: " + event.getEntity().getScoreboardName());
        if (FMLEnvironment.dist.isClient()) {
            ClientBiomeCache.initialize(event.getEntity().level());
        }
    }


    public static ResourceLocation ownresource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, path);
    }
    public static ResourceLocation commonsource(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }

    @EventBusSubscriber(modid = ChickenRoostMod.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {

        @SubscribeEvent
        public static void sendImc(RegisterCapabilitiesEvent evt) {
            ModEntities.initChickenConfig();

        }
    }
}
