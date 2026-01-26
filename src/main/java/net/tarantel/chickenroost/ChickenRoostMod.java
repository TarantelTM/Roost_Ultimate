package net.tarantel.chickenroost;

import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.tarantel.chickenroost.api.cc.ChickenBreederPeripheral;
import net.tarantel.chickenroost.api.cc.ChickenRoostPeripheral;
import net.tarantel.chickenroost.api.cc.ChickenSoulExtractorPeripheral;
import net.tarantel.chickenroost.api.cc.ChickenTrainerPeripheral;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import net.tarantel.chickenroost.client.ClientRoostCache;
import net.tarantel.chickenroost.client.tooltip.ClientStackLineTooltip;
import net.tarantel.chickenroost.client.tooltip.StackLineTooltip;
import net.tarantel.chickenroost.datagen.ChickenRecipeGenerator;
import net.tarantel.chickenroost.datagen.ChickenTagGenerator;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.handler.ModHandlers;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.networking.ModNetworking;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.RoostRecipe;
import net.tarantel.chickenroost.util.*;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;



@Mod(ChickenRoostMod.MODID)
public class ChickenRoostMod {
    public static final String MODID = "chicken_roost";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static RoostConfig CONFIG;
    public static List<ChickenData> chickens;

    public ChickenRoostMod(IEventBus bus, ModContainer modContainer) throws FileNotFoundException {
        bus.addListener(ModNetworking::onRegisterPayloadHandlers);
        chickens = GsonChickenReader.readItemsFromFile();
        CONFIG = CustomConfigReader.load();
        ModCreativeModeTabs.register(bus);
        ModDataComponents.register(bus);
        ModEntities.register(bus);
        ModItems.register(bus);
        ModEntitySpawnMonster.SERIALIZER.register(bus);
        ModEntitySpawnMob.SERIALIZER.register(bus);
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
        ModBlocks.BLOCKS.register(bus);
        ModBlockEntities.register(bus);
        bus.addListener(this::registerCapabilities);
        ModHandlers.register(bus);
        ModRecipes.RECIPE_SERIALIZERS.register(bus);
        ModRecipes.RECIPE_TYPES.register(bus);
        NeoForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);

    }
    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        ServerBiomeCache.rebuild(event.getServer().overworld());
    }

    @SubscribeEvent
    public void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new ServerBiomeReloadListener());
    }

    private static int findInsertIndex(
            List<Either<FormattedText, TooltipComponent>> elements,
            String translationKey
    ) {
        for (int i = 0; i < elements.size(); i++) {
            var el = elements.get(i);
            if (el.left().isPresent()) {
                var text = el.left().get().getString();
                if (text.contains(translationKey)) {
                    return i + 1;
                }
            }
        }
        return elements.size();
    }


    @SubscribeEvent
    public void onGatherTooltip(RenderTooltipEvent.GatherComponents e) {
        var stack = e.getItemStack();
        if (stack.isEmpty()) return;

        var roostRecipes = ClientRoostCache.getRecipes(stack);
        if (roostRecipes.isEmpty()) return;

        var elements = e.getTooltipElements();

        int insertAt = findInsertIndex(elements, "XP");

        elements.add(insertAt++, Either.left(
                Component.translatable("roost_chicken.roostinfo.title")
        ));

        for (RoostRecipe recipe : roostRecipes) {
            ItemStack out = recipe.getResultItem(null).copy();
            out.setCount(1);

            elements.add(insertAt++, Either.right(
                    new StackLineTooltip(out)
            ));
        }
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        ModBlockEntities.registerCapabilities(event);
        event.registerBlockEntity(
                PeripheralCapability.get(),
                ModBlockEntities.TRAINER.get(),
                (be, ctx) -> new ChickenTrainerPeripheral(be)
        );
        event.registerBlockEntity(
                PeripheralCapability.get(),
                ModBlockEntities.ROOST.get(),
                (be, ctx) -> new ChickenRoostPeripheral(be)
        );
        event.registerBlockEntity(
                PeripheralCapability.get(),
                ModBlockEntities.BREEDER.get(),
                (be, ctx) -> new ChickenBreederPeripheral(be)
        );
        event.registerBlockEntity(
                PeripheralCapability.get(),
                ModBlockEntities.SOUL_EXTRACTOR.get(),
                (be, ctx) -> new ChickenSoulExtractorPeripheral(be)
        );
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
            event.accept(ModBlocks.SLIMEBLOCK.get());
            event.accept(ModBlocks.COLLECTOR.get());
            event.accept(ModBlocks.FEEDER.get());
            event.accept(ModBlocks.PIPE_TIER1.get());
            event.accept(ModBlocks.PIPE_TIER2.get());
            event.accept(ModBlocks.PIPE_TIER3.get());
            event.accept(ModBlocks.PIPE_TIER4.get());
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
        public static void sendImc(InterModEnqueueEvent evt) {
            evt.enqueueWork(() -> {
                ChickenRecipeGenerator.generate(chickens);
                ChickenTagGenerator.onServerStarting();
                ModEntities.initChickenConfig();
            });
        }
    }
}
