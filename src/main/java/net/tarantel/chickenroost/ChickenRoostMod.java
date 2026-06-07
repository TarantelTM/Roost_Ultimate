package net.tarantel.chickenroost;

import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent.GatherComponents;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import net.tarantel.chickenroost.client.ClientRoostCache;
import net.tarantel.chickenroost.client.tooltip.StackLineTooltip;
import net.tarantel.chickenroost.datagen.ChickenRecipeGenerator;
import net.tarantel.chickenroost.datagen.ChickenTagGenerator;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.handler.ModHandlers;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.networking.ModNetworking;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.RoostRecipe;
import net.tarantel.chickenroost.util.ChickenData;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.CustomConfigReader;
import net.tarantel.chickenroost.util.GsonChickenReader;
import net.tarantel.chickenroost.util.ModDataComponents;
import net.tarantel.chickenroost.util.ModEntitySpawnMob;
import net.tarantel.chickenroost.util.ModEntitySpawnMonster;
import net.tarantel.chickenroost.util.RoostConfig;
import net.tarantel.chickenroost.util.ServerBiomeCache;
import net.tarantel.chickenroost.util.ServerBiomeReloadListener;
import org.slf4j.Logger;

@Mod("chicken_roost")
public class ChickenRoostMod {
   public static final String MODID = "chicken_roost";
   public static final Logger LOGGER = LogUtils.getLogger();
   public static RoostConfig CONFIG;
   public static List<ChickenData> chickens;
   @Deprecated
   public static final Random RANDOM = new Random();

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
      modContainer.registerConfig(Type.SERVER, Config.SPEC);
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

   private static int findInsertIndex(List<Either<FormattedText, TooltipComponent>> elements, String translationKey) {
      for (int i = 0; i < elements.size(); i++) {
         Either<FormattedText, TooltipComponent> el = elements.get(i);
         if (el.left().isPresent()) {
            String text = ((FormattedText)el.left().get()).getString();
            if (text.contains(translationKey)) {
               return i + 1;
            }
         }
      }

      return elements.size();
   }

   @SubscribeEvent
   public void onGatherTooltip(GatherComponents e) {
      ItemStack stack = e.getItemStack();
      if (!stack.isEmpty()) {
         List<RoostRecipe> roostRecipes = ClientRoostCache.getRecipes(stack);
         if (!roostRecipes.isEmpty()) {
            List<Either<FormattedText, TooltipComponent>> elements = e.getTooltipElements();
            int insertAt = findInsertIndex(elements, "XP");
            elements.add(insertAt++, Either.left(Component.translatable("roost_chicken.roostinfo.title")));

            for (RoostRecipe recipe : roostRecipes) {
               ItemStack out = recipe.getResultItem(null).copy();
               out.setCount(1);
               elements.add(insertAt++, Either.right(new StackLineTooltip(out)));
            }
         }
      }
   }

   public void registerCapabilities(RegisterCapabilitiesEvent event) {
      ModBlockEntities.registerCapabilities(event);
      if (ModList.get().isLoaded("computercraft")) {
         CCA.registercctweaked(event);
      }
   }

   public void addCreative(BuildCreativeModeTabContentsEvent event) {
      if (event.getTab() == ModCreativeModeTabs.TAB_CHICKEN_ROOST_TAB.get()) {
         for (int i = 0; i < ModItems.ITEMS.getEntries().size(); i++) {
            event.accept(((Item)((DeferredHolder)ModItems.ITEMS.getEntries().stream().toList().get(i)).get()).asItem());
         }

         for (int i = 0; i < ModItems.CHICKENITEMS.getEntries().size(); i++) {
            event.accept(((Item)((DeferredHolder)ModItems.CHICKENITEMS.getEntries().stream().toList().get(i)).get()).asItem());
         }

         event.accept((ItemLike)ModBlocks.BREEDER.get());
         event.accept((ItemLike)ModItems.TRAINER.get());
         event.accept((ItemLike)ModBlocks.SOUL_EXTRACTOR.get());
         event.accept((ItemLike)ModBlocks.ROOST.get());
         event.accept((ItemLike)ModItems.CHICKENSTORAGE.get());
         event.accept((ItemLike)ModBlocks.SLIMEBLOCK.get());
         event.accept((ItemLike)ModBlocks.COLLECTOR.get());
         event.accept((ItemLike)ModBlocks.FEEDER.get());
         event.accept((ItemLike)ModBlocks.PIPE_TIER1.get());
         event.accept((ItemLike)ModBlocks.PIPE_TIER2.get());
         event.accept((ItemLike)ModBlocks.PIPE_TIER3.get());
         event.accept((ItemLike)ModBlocks.PIPE_TIER4.get());
      }
   }

   public static ResourceLocation ownresource(String path) {
      return ResourceLocation.fromNamespaceAndPath("chicken_roost", path);
   }

   public static ResourceLocation commonsource(String path) {
      return ResourceLocation.fromNamespaceAndPath("c", path);
   }

   @EventBusSubscriber(modid = "chicken_roost", bus = Bus.MOD)
   public static class ModEventBusEvents {
      @SubscribeEvent
      public static void sendImc(InterModEnqueueEvent evt) {
         evt.enqueueWork(() -> {
            ChickenRecipeGenerator.generate(ChickenRoostMod.chickens);
            ChickenTagGenerator.onServerStarting();
            ModEntities.initChickenConfig();
         });
      }
   }
}
