package net.tarantel.chickenroost;


import com.mojang.datafixers.util.Either;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
//import net.tarantel.chickenroost.api.jei.JEIPlugin;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.*;
//import net.tarantel.chickenroost.client.ClientRoostCache;
//import net.tarantel.chickenroost.client.tooltip.StackLineTooltip;
import net.tarantel.chickenroost.datagen.ChickenRecipeGenerator;
import net.tarantel.chickenroost.datagen.ChickenTagGenerator;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.handler.ModHandlers;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.networking.ModNetworking;
import net.tarantel.chickenroost.recipes.*;
import net.tarantel.chickenroost.util.*;


import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.Set;


@Mod(ChickenRoostMod.MODID)
public class ChickenRoostMod {
    public static final String MODID = "chicken_roost";
    public static RoostConfig CONFIG;
    public static List<ChickenData> chickens;

    public ChickenRoostMod(IEventBus bus, ModContainer modContainer) {
        bus.addListener(ModNetworking::register);
        chickens = GsonChickenReader.readItemsFromFile();
        CONFIG = CustomConfigReader.load();

        ModDataComponents.register(bus);
        ModEntities.register(bus);
        ModItems.register(bus);
        ModEntitySpawnMonster.SERIALIZER.register(bus);
        ModEntitySpawnMob.SERIALIZER.register(bus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModBlocks.register(bus);
        ModBlockEntities.register(bus);
        bus.addListener(this::registerCapabilities);
        ModHandlers.register(bus);
        ModRecipes.RECIPE_SERIALIZERS.register(bus);
        ModRecipes.RECIPE_TYPES.register(bus);
        NeoForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);
        ChickenRecipeGenerator.generate(chickens);
        ChickenTagGenerator.onServerStarting();
        ModCreativeModeTabs.register(bus);
        /*if(JEIUtils.isJEIAvailable()) {
            NeoForge.EVENT_BUS.addListener(false, OnDatapackSyncEvent.class, e -> e.sendRecipes(
                    BreederRecipe.Type.INSTANCE,
                    RoostRecipe.Type.INSTANCE,
                    TrainerRecipe.Type.INSTANCE,
                    ThrowEggRecipe.Type.INSTANCE,
                    SoulExtractorRecipe.Type.INSTANCE
            ));
        }*/
        //bus.addListener(this::onGatherTooltip);

    }
    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        ServerBiomeCache.rebuild(event.getServer().overworld());
    }

    @SubscribeEvent
    public void onAddServerReloadListeners(AddServerReloadListenersEvent event) {
        event.addListener(
                 Identifier.fromNamespaceAndPath("chicken_roost", "server_biome_cache"),
                new ServerBiomeReloadListener()
        );
    }

    private static int findInsertIndex(
            List<Either<FormattedText, TooltipComponent>> elements,
            String needle
    ) {
        for (int i = 0; i < elements.size(); i++) {
            var el = elements.get(i);
            if (el.left().isPresent()) {
                if (el.left().get().getString().contains(needle)) {
                    return i + 1;
                }
            }
        }
        return elements.size();
    }


   /* @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientGameEvents {
        @SubscribeEvent
        public static void onRecipesReceived(RecipesReceivedEvent event) {
            //if(JEIUtils.isJEIAvailable()) {
                //JEIPlugin.recipeMap = event.getRecipeMap();
            //    System.out.println("RecipeMap: Filled");
           // }
        }
    }*/

    /*@SubscribeEvent
    public void onGatherTooltip(RenderTooltipEvent.GatherComponents e) {
        ItemStack stack = e.getItemStack();
        if (stack.isEmpty()) return;

        var roostRecipes = ClientRoostCache.getRecipes(stack);
        if (roostRecipes.isEmpty()) return;

        List<Either<FormattedText, TooltipComponent>> elements = e.getTooltipElements();

        int insertAt = findInsertIndex(elements, "XP");

        elements.add(insertAt++,
                Either.left(Component.translatable("roost_chicken.roostinfo.title"))
        );

        for (RoostRecipe recipe : roostRecipes) {
            ItemStack out = recipe.getResultItem(null).copy();
            out.setCount(1);

            elements.add(insertAt++,
                    Either.right(new StackLineTooltip(out))
            );
        }
    }*/




    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                ModBlockEntities.BREEDER.get(),
                (be, side) -> {
                    BreederTile tile = (BreederTile) be;

                    // GUI / intern
                    if (side == null) {
                        return tile.inventory;
                    }

                    // Automation
                    return new FilteredItemHandler(
                            tile.inventory,
                            slot -> slot >= 0 && slot <= 2,   // INSERT: Inputs
                            slot -> slot >= 3 && slot <= 11   // EXTRACT: Outputs
                    );
                }
        );

        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                ModBlockEntities.TRAINER.get(),
                (be, side) -> {
                    TrainerTile tile = (TrainerTile) be;

                    if (side == null) {
                        return tile.inventory;
                    }

                    return new FilteredItemHandler(
                            tile.inventory,
                            slot -> slot == 0 || slot == 1,   // INSERT: Chicken + Seeds
                            slot -> slot == 0                 // EXTRACT: Chicken
                    );
                }
        );

        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                ModBlockEntities.SOUL_EXTRACTOR.get(),
                (be, side) -> {
                    SoulExtractorTile tile = (SoulExtractorTile) be;

                    if (side == null) {
                        return tile.inventory;
                    }

                    return new FilteredItemHandler(
                            tile.inventory,
                            slot -> slot == 0,   // INSERT: Chicken
                            slot -> slot == 1    // EXTRACT: Souls
                    );
                }
        );

        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                ModBlockEntities.ROOST.get(),
                (be, side) -> {
                    RoostTile tile = (RoostTile) be;

                    if (side == null) {
                        return tile.inventory;
                    }

                    return new FilteredItemHandler(
                            tile.inventory,
                            slot -> slot == 0 || slot == 1,   // INSERT: Seeds + Chicken
                            slot -> slot == 2                 // EXTRACT: Output
                    );
                }
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
            //event.accept(ModBlocks.TRAINER.get());
            event.accept(ModBlocks.SOUL_EXTRACTOR.get());
            event.accept(ModBlocks.ROOST.get());
            //event.accept(ModItems.CHICKENSTORAGE.get());
            event.accept(ModBlocks.SLIMEBLOCK.get());
            event.accept(ModBlocks.COLLECTOR.get());
            event.accept(ModBlocks.FEEDER.get());
            event.accept(ModBlocks.PIPE_TIER1.get());
            event.accept(ModBlocks.PIPE_TIER2.get());
            event.accept(ModBlocks.PIPE_TIER3.get());
            event.accept(ModBlocks.PIPE_TIER4.get());
        }

    }


    public static Identifier ownresource(String path) {
        return Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, path);
    }
    public static Identifier commonsource(String path) {
        return Identifier.fromNamespaceAndPath("c", path);
    }

    @EventBusSubscriber(
            modid = ChickenRoostMod.MODID,
            value = Dist.DEDICATED_SERVER
    )
    public static class ModEventBusEvents {

        @SubscribeEvent
        public static void sendImc(InterModEnqueueEvent evt) {
            evt.enqueueWork(() -> {
                ModEntities.initChickenConfig();
            });
        }
    }
}
