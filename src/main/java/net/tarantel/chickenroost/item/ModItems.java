package net.tarantel.chickenroost.item;

import mekanism.common.Mekanism;
import mekanism.common.registries.MekanismFluids;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.util.GsonChickenReader;
import net.tarantel.chickenroost.util.ChickenData;
import net.tarantel.crlib.util.UniversalFluidItem;

import java.text.DecimalFormat;
import java.util.List;

public class ModItems {


	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ChickenRoostMod.MODID);
	public static final DeferredRegister<Item> ITEMSS = DeferredRegister.create(ForgeRegistries.ITEMS, ChickenRoostMod.MODID);

	public static final RegistryObject<Item> XMAS_HAT = ITEMS.register("xmashat", () -> new ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, new Item.Properties()));
	///Ingots
	public static final RegistryObject<Item> INGOT_ELECTRUM = ITEMS.register("ingot_electrum", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_SILVER = ITEMS.register("ingot_silver", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_ZINC = ITEMS.register("ingot_zinc", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> INGOT_BRONZE = ITEMS.register("ingot_bronze", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_LEAD = ITEMS.register("ingot_lead", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_STEEL = ITEMS.register("ingot_steel", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_TIN = ITEMS.register("ingot_tin", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_URANIUM = ITEMS.register("ingot_uranium", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> INGOT_ALUMINUM = ITEMS.register("ingot_aluminum", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_CHROME = ITEMS.register("ingot_chrome", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> INGOT_INVAR = ITEMS.register("ingot_invar", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_IRIDIUM = ITEMS.register("ingot_iridium", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> INGOT_NICKEL = ITEMS.register("ingot_nickel", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_PLATINUM = ITEMS.register("ingot_platinum", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> INGOT_TITANUM = ITEMS.register("ingot_titanum", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_TUNGSTEN = ITEMS.register("ingot_tungsten", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_TUNGSTENSTEEL = ITEMS.register("ingot_tungstensteel", () -> new Item(new Item.Properties()));


	public static final RegistryObject<Item> INGOT_ENDERIUM = ITEMS.register("ingot_enderium", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_ADAMANTIUM = ITEMS.register("ingot_adamantium", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_LUMIUM = ITEMS.register("ingot_lumium", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_SIGNALUM = ITEMS.register("ingot_signalum", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> CHICKEN_TORCH = ITEMS.register("chicken_torch", () -> new StandingAndWallBlockItem(ModBlocks.TORCH.get(),ModBlocks.WALL_TORCH.get(), new Item.Properties(), Direction.DOWN){


		@Override
		public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
			super.appendHoverText(stack, level, tooltip, flag);

			// Add static tooltip lines
			tooltip.add(Component.literal("Prevents spawning of Chickens for 64 Blocks in any Direction"));

		}
	});


	public static final RegistryObject<Item> WATER_EGG = ITEMS.register("water_egg", () -> new UniversalFluidItem(new Item.Properties().stacksTo(64), () -> Fluids.WATER, 1000));
	public static final RegistryObject<Item> LAVA_EGG = ITEMS.register("lava_egg", () -> new UniversalFluidItem(new Item.Properties().stacksTo(64), () -> Fluids.LAVA, 1000));


	public static final RegistryObject<Item> STONE_ESSENCE = ITEMS.register("stone_essence", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> WOOD_ESSENCE = ITEMS.register("wood_essence", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	//region Color Eggs
	public static final RegistryObject<Item> RED_EGG = ITEMS.register("red_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_red"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> BLUE_EGG = ITEMS.register("blue_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_lapis"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> YELLOW_EGG = ITEMS.register("yellow_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_yellow"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> WHITE_EGG = ITEMS.register("white_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_white"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> ORANGE_EGG = ITEMS.register("orange_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_orange"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> MAGENTA_EGG = ITEMS.register("magenta_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_magenta"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> LIGHT_BLUE_EGG = ITEMS.register("light_blue_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_light_blue"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> LIME_EGG = ITEMS.register("lime_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_lime"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> PINK_EGG = ITEMS.register("pink_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_pink"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> GRAY_EGG = ITEMS.register("gray_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_gray"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> LIGHT_GRAY_EGG = ITEMS.register("light_gray_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_light_gray"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> CYAN_EGG = ITEMS.register("cyan_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_cyan"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> PURPLE_EGG = ITEMS.register("purple_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_purple"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> BROWN_EGG = ITEMS.register("brown_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_brown"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> GREEN_EGG = ITEMS.register("green_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_green"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> BLACK_EGG = ITEMS.register("black_egg", () -> new RoostEgg(new ResourceLocation("chicken_roost:c_ink"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));

	public static final RegistryObject<Item> CHICKEN_STICK = ITEMS.register("chicken_stick", () -> new AnimatedChickenStick(new Item.Properties()));
	//endregion

	//region Essence
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_1 = ITEMS.register("chicken_essence_tier_1", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_2 = ITEMS.register("chicken_essence_tier_2", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_3 = ITEMS.register("chicken_essence_tier_3", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_4 = ITEMS.register("chicken_essence_tier_4", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_5 = ITEMS.register("chicken_essence_tier_5", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_6 = ITEMS.register("chicken_essence_tier_6", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_7 = ITEMS.register("chicken_essence_tier_7", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_8 = ITEMS.register("chicken_essence_tier_8", () -> new Essence_Soul());
	public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_9 = ITEMS.register("chicken_essence_tier_9", () -> new Essence_Soul());
	//endregion


	//region SEEDS
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_1 = ITEMS.register("chicken_food_tier_1", () -> new CropBlockItem(ModBlocks.SEED_CROP_1.get(), new Item.Properties(), 0));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_2 = ITEMS.register("chicken_food_tier_2", () -> new CropBlockItem(ModBlocks.SEED_CROP_2.get(), new Item.Properties(), 1));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_3 = ITEMS.register("chicken_food_tier_3", () -> new CropBlockItem(ModBlocks.SEED_CROP_3.get(), new Item.Properties(), 2));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_4 = ITEMS.register("chicken_food_tier_4", () -> new CropBlockItem(ModBlocks.SEED_CROP_4.get(), new Item.Properties(), 3));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_5 = ITEMS.register("chicken_food_tier_5", () -> new CropBlockItem(ModBlocks.SEED_CROP_5.get(), new Item.Properties(), 4));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_6 = ITEMS.register("chicken_food_tier_6", () -> new CropBlockItem(ModBlocks.SEED_CROP_6.get(), new Item.Properties(), 5));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_7 = ITEMS.register("chicken_food_tier_7", () -> new CropBlockItem(ModBlocks.SEED_CROP_7.get(), new Item.Properties(), 6));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_8 = ITEMS.register("chicken_food_tier_8", () -> new CropBlockItem(ModBlocks.SEED_CROP_8.get(), new Item.Properties(), 7));
	public static final RegistryObject<Item> CHICKEN_FOOD_TIER_9 = ITEMS.register("chicken_food_tier_9", () -> new CropBlockItem(ModBlocks.SEED_CROP_9.get(), new Item.Properties(), 8));
	//endregion


	public static final RegistryObject<BlockItem> TRAINER = ITEMSS.register("trainer",
			() -> new AnimatedTrainerBlockItem(ModBlocks.TRAINER.get(),
					new Item.Properties()));
	public static final RegistryObject<Item> CHICKENSTORAGE = ITEMSS.register("chickenstorage",
			() -> new BlockItem(ModBlocks.CHICKENSTORAGE.get(), new Item.Properties().rarity(Rarity.EPIC)) {

				private static final DecimalFormat STORAGE_AMOUNT_FORMAT = new DecimalFormat("#,###,###");

				@Override
				public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
					super.appendHoverText(stack, level, tooltip, flag);

					// Add static tooltip lines
					tooltip.add(Component.literal("Very Big Storage"));
					tooltip.add(Component.literal("For Chickens and Seeds"));
					tooltip.add(Component.literal("Works only with AE2, RS, Simple Storage Network"));
					tooltip.add(Component.literal("or other Mods who access Block Inventories via Interfaces"));
					tooltip.add(Component.literal("Its harder than Obsidian and got a 10x explosion Resist"));

					// Add dynamic tooltip line if storage amount is present
					if (stack.hasTag() && stack.getTag().contains("StorageAmount")) {
						int storageAmount = stack.getTag().getInt("StorageAmount");
						tooltip.add(Component.literal("\u00A7e Stored Items: \u00A7a" + STORAGE_AMOUNT_FORMAT.format(storageAmount)));
					}
				}
			});

	public static void readthis() {

		List<ChickenData> readItems = ChickenRoostMod.chickens;
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){

				String id = etherItem.getId();
				String itemtexture = etherItem.getItemtexture();
				String mobtexture = etherItem.getMobtexture();
				String dropitem = etherItem.getDropitem();
				float eggtime = etherItem.getEggtime();
				int tierr = etherItem.getTier();

				extrachickens(id, itemtexture, tierr);
			}
		}
	}

	private static RegistryObject<Item> extrachickens(String idd, String texturee, int tierr) {
		return switch (tierr) {
			case 1 -> ITEMS.register(idd, () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee, 0));
			case 2 -> ITEMS.register(idd, () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,1));
			case 3 -> ITEMS.register(idd, () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,2));
			case 4 -> ITEMS.register(idd, () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,3));
			case 5 -> ITEMS.register(idd, () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,4));
			case 6 -> ITEMS.register(idd, () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,5));
			case 7 -> ITEMS.register(idd, () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,6));
			case 8 -> ITEMS.register(idd, () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,7));
			case 9 -> ITEMS.register(idd, () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,8));
			default -> ITEMS.register(idd, () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,0));
		};
	}

	public static void register(IEventBus eventBus) {
		readthis();
		ITEMS.register(eventBus);
		ITEMSS.register(eventBus);
	}
}
