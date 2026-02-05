package net.tarantel.chickenroost;

import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ChunkEvent;
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
import net.tarantel.chickenroost.client.ClientRoostCache;
import net.tarantel.chickenroost.client.tooltip.ClientStackLineTooltip;
import net.tarantel.chickenroost.client.tooltip.StackLineTooltip;
import net.tarantel.chickenroost.datagen.ChickenRecipeGenerator;
import net.tarantel.chickenroost.datagen.ChickenTagGenerator;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.handler.ModHandlers;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.network.ModMessages;
import net.tarantel.chickenroost.recipes.ModRecipes;
import net.tarantel.chickenroost.recipes.Roost_Recipe;
import net.tarantel.chickenroost.screen.*;
import net.tarantel.chickenroost.spawn.*;
import net.tarantel.chickenroost.util.*;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("ALL")
@Mod("chicken_roost")
public class ChickenRoostMod {
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final String MODID = "chicken_roost";
	private static final String PROTOCOL_VERSION = "1";
	public static RoostConfig CONFIG;
	public static List<ChickenData> chickens;

	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);


	private static int messageID = 0;
	public ChickenRoostMod() {

		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		GeckoLib.initialize();
		chickens = GsonChickenReader.readItemsFromFile();
		CONFIG = CustomConfigReader.load();

		ChickenTagGenerator.onServerStarting();
		ModEntities.register(bus);
		ModItems.register(bus);
		ModCreativeModeTabs.register(bus);
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.ServerConfig.SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.ClientConfig.ClientSpec);
		ModBlocks.BLOCKS.register(bus);
		ModBlockEntities.register(bus);
        ModHandlers.register(bus);
		ModRecipes.RECIPE_SERIALIZERS.register(bus);
		ModRecipes.RECIPE_TYPES.register(bus);
		bus.addListener(this::addCreative);
		bus.addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.addListener(
				(AddReloadListenerEvent e) ->
						e.addListener(new BiomeModifierReloadListener())
		);
		MinecraftForge.EVENT_BUS.register(ChickenRoostSpawnManager.class);


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
			event.accept(ModItems.CHICKENSTORAGE.get());
			event.accept(ModBlocks.FEEDER.get());
			event.accept(ModBlocks.COLLECTOR.get());
		}

	}
	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
			BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}
	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class SideEvents{
		@SubscribeEvent
		public static void sendImc(InterModEnqueueEvent evt) {

			evt.enqueueWork(() -> {
				ChickenRecipeGenerator.generate(chickens);
				ModEntities.initChickenConfig();
			});
		}


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


	/*@SubscribeEvent
	public void onGatherTooltip(RenderTooltipEvent.GatherComponents e) {
		var stack = e.getItemStack();
		if (stack.isEmpty()) return;

		var roostRecipes = ClientRoostCache.getRecipes(stack);
		if (roostRecipes.isEmpty()) return;

		var elements = e.getTooltipElements();

		// z.B. NACH "Biome" Abschnitt
		int insertAt = findInsertIndex(elements, "XP");
		// oder einfach:
		// int insertAt = Math.min(3, elements.size());

		// Überschrift
		elements.add(insertAt++, Either.left(
				Component.translatable("roost_chicken.roostinfo.title")
		));
		System.out.println("onGatherTooltip: Item Added to Tooltip");

		// Outputs
		for (Roost_Recipe recipe : roostRecipes) {
			ItemStack out = recipe.getResultItem(null).copy();
			out.setCount(1);

			elements.add(insertAt++, Either.right(
					new StackLineTooltip(out)
			));
		}
	}*/

	public static ResourceLocation ownresource(String path) {
		return new ResourceLocation(ChickenRoostMod.MODID, path);
	}
	public static ResourceLocation commonsource(String path) {
		return new ResourceLocation("c", path);
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
			event.registerBlockEntityRenderer(ModBlockEntities.TRAINER.get(),
					AnimatedTrainerRenderer::new);

		}
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
			event.enqueueWork(() -> {
				MenuScreens.register(ModHandlers.SOUL_EXTRACTOR_MENU.get(), Soul_Extractor_Screen::new);
				MenuScreens.register(ModHandlers.ROOST_MENU_V1.get(), Roost_Screen::new);
				MenuScreens.register(ModHandlers.TRAINER.get(), Trainer_Screen::new);
				MenuScreens.register(ModHandlers.BREEDER_MENU.get(), Breeder_Screen::new);
				MenuScreens.register(ModHandlers.FEEDER_MENU.get(), Feeder_Screen::new);
				MenuScreens.register(ModHandlers.COLLECTOR_MENU.get(), Collector_Screen::new);

				ItemBlockRenderTypes.setRenderLayer(ModBlocks.TORCH.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.WALL_TORCH.get(), RenderType.cutout());
			});


        }

		/*@SubscribeEvent
		public static void registerTooltipFactories(RegisterClientTooltipComponentFactoriesEvent e) {
			System.out.println(">>> REGISTER TOOLTIP FACTORY <<<");
			e.register(StackLineTooltip.class, ClientStackLineTooltip::new);
		}*/
    }
}
