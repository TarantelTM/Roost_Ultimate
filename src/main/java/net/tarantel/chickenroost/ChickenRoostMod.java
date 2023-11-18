package net.tarantel.chickenroost;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraftforge.fml.config.ModConfig;
import net.tarantel.chickenroost.block.ModBlocks;
import net.tarantel.chickenroost.block.entity.ModBlockEntities;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.handler.ModScreenHandlers;
import net.tarantel.chickenroost.handler.breeder_handler;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipemanager.ModRecipes;
import net.tarantel.chickenroost.util.Config;
import net.tarantel.chickenroost.util.ModEntitySpawn;
import net.tarantel.chickenroost.util.ModItemGroup;
import net.tarantel.chickenroost.util.SpawnConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ChickenRoostMod  implements ModInitializer {

	public static final String MODID = "chicken_roost";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	private static final String PROTOCOL_VERSION = "1";
	private static int messageID = 0;
	public static ScreenHandlerType<breeder_handler> BREEDER_FABRIC_HANDLER;
	//public static final Item EGG_C_COBBLE = new SpawnEggItem(ModEntities.C_COBBLE, 0xc4c4c4, 0xadadad, new FabricItemSettings().group(ModItemGroup.EGGS));


	@Override
	public void onInitialize() {


		ModItemGroup.registerItemGroups();
		//BREEDER_FABRIC_HANDLER = Registry.register(Registries.SCREEN_HANDLER, id("breeder_handler"), new ScreenHandlerType<>(breeder_handler::new));
		/*BREEDER_FABRIC_HANDLER =
				Registry.register(Registries.SCREEN_HANDLER, new Identifier(ChickenRoostMod.MODID, "breeder"), new ScreenHandlerType<>(breeder_handler::new, FeatureFlags.DEFAULT_ENABLED_FEATURES));*/
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerAllScreenHandlers();
		ModRecipes.registerRecipes();
		//GeckoLib.initialize();
		ModEntities.load();
		ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.COMMON, Config.SPEC, "roostultimate_common.toml");
		ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.COMMON, SpawnConfig.SPEC, "roostultimate_spawnconfig.toml");
		//ModLoadingContext.registerConfig(ChickenRoostMod.MODID, ModConfig.Type.COMMON, Config.SPEC, "roostultimate_common.toml");
		//ModLoadingContext.registerConfig(ChickenRoostMod.MODID, ModConfig.Type.COMMON, SpawnConfig.SPEC, "roostultimate_spawnconfig.toml");

		ModEntitySpawn.addEntitySpawn();
	}
	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}
