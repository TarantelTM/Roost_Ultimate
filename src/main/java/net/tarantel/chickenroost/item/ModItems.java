package net.tarantel.chickenroost.item;


import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.*;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.util.ChickenData;
import net.tarantel.chickenroost.util.GsonChickenReader;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModItems {



	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("chicken_roost");
	public static final DeferredRegister.Items ITEMSS = DeferredRegister.createItems("chicken_roost");
	public static final DeferredRegister.Items CHICKENITEMS = DeferredRegister.createItems("chicken_roost");


	public static final DeferredItem<Item> INGOT_ELECTRUM = ITEMS.register("ingot_electrum", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_electrum"));
	public static final DeferredItem<Item> INGOT_SILVER = ITEMS.register("ingot_silver", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_silver"));
	public static final DeferredItem<Item> INGOT_ZINC = ITEMS.register("ingot_zinc", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_zinc"));

	public static final DeferredItem<Item> INGOT_BRONZE = ITEMS.register("ingot_bronze", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_bronze"));
	public static final DeferredItem<Item> INGOT_LEAD = ITEMS.register("ingot_lead", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_lead"));
	public static final DeferredItem<Item> INGOT_STEEL = ITEMS.register("ingot_steel", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_steel"));
	public static final DeferredItem<Item> INGOT_TIN = ITEMS.register("ingot_tin", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_tin"));
	public static final DeferredItem<Item> INGOT_URANIUM = ITEMS.register("ingot_uranium", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_uranium"));


	public static final DeferredItem<Item> INGOT_ALUMINUM = ITEMS.register("ingot_aluminum", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_aluminum"));
	public static final DeferredItem<Item> INGOT_CHROME = ITEMS.register("ingot_chrome", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_chrome"));

	public static final DeferredItem<Item> INGOT_INVAR = ITEMS.register("ingot_invar", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_invar"));
	public static final DeferredItem<Item> INGOT_IRIDIUM = ITEMS.register("ingot_iridium", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_iridium"));

	public static final DeferredItem<Item> INGOT_NICKEL = ITEMS.register("ingot_nickel", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_nickel"));
	public static final DeferredItem<Item> INGOT_PLATINUM = ITEMS.register("ingot_platinum", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_platinum"));

	public static final DeferredItem<Item> INGOT_TITANUM = ITEMS.register("ingot_titanum", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_titanum"));
	public static final DeferredItem<Item> INGOT_TUNGSTEN = ITEMS.register("ingot_tungsten", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_tungsten"));
	public static final DeferredItem<Item> INGOT_TUNGSTENSTEEL = ITEMS.register("ingot_tungstensteel", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_tungstensteel"));


	public static final DeferredItem<Item> INGOT_ENDERIUM = ITEMS.register("ingot_enderium", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_enderium"));
	public static final DeferredItem<Item> INGOT_ADAMANTIUM = ITEMS.register("ingot_adamantium", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_adamantium"));
	public static final DeferredItem<Item> INGOT_LUMIUM = ITEMS.register("ingot_lumium", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_lumium"));
	public static final DeferredItem<Item> INGOT_SIGNALUM = ITEMS.register("ingot_signalum", () -> new AnimatedIngotItem(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_signalum"));


	public static final DeferredItem<Item> WATER_EGG = ITEMS.register("water_egg", () -> new UniversalFluidItem(new Item.Properties().stacksTo(64), Fluids.WATER, 1000));
	public static final DeferredItem<Item> LAVA_EGG = ITEMS.register("lava_egg", () -> new UniversalFluidItem(new Item.Properties().stacksTo(64), Fluids.LAVA, 1000));

	public static final DeferredItem<Item> STONE_ESSENCE = ITEMS.register("stone_essence", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> WOOD_ESSENCE = ITEMS.register("wood_essence", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> CHICKENCHICKEN = CHICKENITEMS.register("c_vanilla", () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "saltchicken", 0));


	public static final DeferredItem<Item> RED_EGG = ITEMS.register("red_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_red"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> BLUE_EGG = ITEMS.register("blue_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_blue"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> YELLOW_EGG = ITEMS.register("yellow_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_yellow"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> WHITE_EGG = ITEMS.register("white_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_white"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> ORANGE_EGG = ITEMS.register("orange_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_orange"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> MAGENTA_EGG = ITEMS.register("magenta_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_magenta"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> LIGHT_BLUE_EGG = ITEMS.register("light_blue_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_light_blue"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> LIME_EGG = ITEMS.register("lime_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_lime"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> PINK_EGG = ITEMS.register("pink_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_pink"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> GRAY_EGG = ITEMS.register("gray_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_gray"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> LIGHT_GRAY_EGG = ITEMS.register("light_gray_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_light_gray"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> CYAN_EGG = ITEMS.register("cyan_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_cyan"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> PURPLE_EGG = ITEMS.register("purple_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_purple"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> BROWN_EGG = ITEMS.register("brown_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_brown"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> GREEN_EGG = ITEMS.register("green_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_green"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> BLACK_EGG = ITEMS.register("black_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_black"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> E_CHICKEN_LAVA = CHICKENITEMS.register("c_lava", () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "lavachicken", 1));
	public static final DeferredItem<Item> E_CHICKEN_WATER = CHICKENITEMS.register("c_water", () -> new AnimatedChicken(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).component(ModDataComponents.CHICKENLEVEL.value(), 0).component(ModDataComponents.CHICKENXP.value(), 0), "waterchicken", 1));

	public static final DeferredItem<Item> CHICKEN_SCANNER = ITEMS.register("chicken_scanner", ChickenScannerItem::new);
	public static final DeferredItem<Item> CHICKEN_STICK = ITEMS.register("chicken_stick", () -> new AnimatedChickenStick(new Item.Properties()));

	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_1 = ITEMS.register("chicken_essence_tier_1", EssenceSoul::new);
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_2 = ITEMS.register("chicken_essence_tier_2", EssenceSoul::new);
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_3 = ITEMS.register("chicken_essence_tier_3", EssenceSoul::new);
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_4 = ITEMS.register("chicken_essence_tier_4", EssenceSoul::new);
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_5 = ITEMS.register("chicken_essence_tier_5", EssenceSoul::new);
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_6 = ITEMS.register("chicken_essence_tier_6", EssenceSoul::new);
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_7 = ITEMS.register("chicken_essence_tier_7", EssenceSoul::new);
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_8 = ITEMS.register("chicken_essence_tier_8", EssenceSoul::new);
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_9 = ITEMS.register("chicken_essence_tier_9", EssenceSoul::new);

	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_1 = ITEMS.register("chicken_food_tier_1", () -> new CropBlockItem(ModBlocks.SEED_CROP_1.get(), new Item.Properties(), 0));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_2 = ITEMS.register("chicken_food_tier_2", () -> new CropBlockItem(ModBlocks.SEED_CROP_2.get(), new Item.Properties(), 1));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_3 = ITEMS.register("chicken_food_tier_3", () -> new CropBlockItem(ModBlocks.SEED_CROP_3.get(), new Item.Properties(), 2));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_4 = ITEMS.register("chicken_food_tier_4", () -> new CropBlockItem(ModBlocks.SEED_CROP_4.get(), new Item.Properties(), 3));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_5 = ITEMS.register("chicken_food_tier_5", () -> new CropBlockItem(ModBlocks.SEED_CROP_5.get(), new Item.Properties(), 4));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_6 = ITEMS.register("chicken_food_tier_6", () -> new CropBlockItem(ModBlocks.SEED_CROP_6.get(), new Item.Properties(), 5));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_7 = ITEMS.register("chicken_food_tier_7", () -> new CropBlockItem(ModBlocks.SEED_CROP_7.get(), new Item.Properties(), 6));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_8 = ITEMS.register("chicken_food_tier_8", () -> new CropBlockItem(ModBlocks.SEED_CROP_8.get(), new Item.Properties(), 7));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_9 = ITEMS.register("chicken_food_tier_9", () -> new CropBlockItem(ModBlocks.SEED_CROP_9.get(), new Item.Properties(), 8));



	public static final DeferredHolder<Item, BlockItem> SOUL_BREEDER = ITEMSS.register("soul_breeder",
			() -> new AnimatedSoulBreederBlockItem(ModBlocks.SOUL_BREEDER.get(),
					new Item.Properties()));


	public static final DeferredHolder<Item, BlockItem> TRAINER = ITEMSS.register("trainer",
			() -> new AnimatedTrainerBlockItem(ModBlocks.TRAINER.get(),
					new Item.Properties()));

	public static final DeferredHolder<Item, BlockItem> CHICKENSTORAGE = ITEMSS.register("chickenstorage",
			() -> new BlockItem(ModBlocks.CHICKENSTORAGE.get(),
					new Item.Properties().rarity(Rarity.EPIC)){

				@Override
				public void appendHoverText(@NotNull ItemStack itemstack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
					super.appendHoverText(itemstack, context, list, flag);
					list.add(Component.nullToEmpty("Very Big Storage"));
					list.add(Component.nullToEmpty("For Chickens and Seeds"));
					list.add(Component.nullToEmpty("Works only with AE2, RS, Simple Storage Network"));
					list.add(Component.nullToEmpty("or other Mods who access Block Inventories via Interfaces"));
					list.add(Component.nullToEmpty("Its harder than Obsidian and got a 10x explosion Resist"));
					if(itemstack.getComponents().has(ModDataComponents.STORAGEAMOUNT.value())) {
						list.add(Component.nullToEmpty(("\u00A7e") + "Stored Items: " + ("\u00A7a") + String.format("%,d", itemstack.get(ModDataComponents.STORAGEAMOUNT))));
					}
				}
			});

	private static final List<DeferredItem<? extends Item>> DYNAMIC_CHICKEN_ITEMS = new ArrayList<>();


	public static void readthis() {

		List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
        assert readItems != null;
        if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){

				String id = etherItem.getId();
				String itemtexture = etherItem.getItemtexture();
                int tierr = etherItem.getTier();

				extrachickens(id, itemtexture, tierr);
			}
		}
	}

	public static void extrachickens(String idd, String texturee, int tierr) {
		DeferredItem<Item> deferred = switch (tierr) {
            case 2 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee,1));
			case 3 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee,2));
			case 4 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee,3));
			case 5 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee,4));
			case 6 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee,5));
			case 7 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee,6));
			case 8 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee,7));
			case 9 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee,8));
			default -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee,0));
		};

		DYNAMIC_CHICKEN_ITEMS.add(deferred);

	}

	private static Item.Properties props() {
		return new Item.Properties()
				.stacksTo(64)
				.fireResistant()
				.rarity(Rarity.COMMON)
				.component(ModDataComponents.CHICKENLEVEL.value(), 0)
				.component(ModDataComponents.CHICKENXP.value(), 0);
	}


	public static void register(IEventBus eventBus) {
		readthis();
		ITEMS.register(eventBus);
		ITEMSS.register(eventBus);
		CHICKENITEMS.register(eventBus);

	}
}
