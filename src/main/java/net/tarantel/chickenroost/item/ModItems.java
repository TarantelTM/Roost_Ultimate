package net.tarantel.chickenroost.item;


import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
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
import net.tarantel.chickenroost.block.blocks.BreederBlock;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.pipe.PipeBlock;
import net.tarantel.chickenroost.util.ChickenData;
import net.tarantel.chickenroost.util.GsonChickenReader;
import net.tarantel.chickenroost.util.ModDataComponents;
//import net.tarantel.crlib.util.UniversalFluidItem;
import net.tarantel.crlib.util.UniversalFluidItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModItems {



	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("chicken_roost");
	public static final DeferredRegister.Items ITEMSS = DeferredRegister.createItems("chicken_roost");
	public static final DeferredRegister.Items CHICKENITEMS = DeferredRegister.createItems("chicken_roost");

	/*public static final DeferredItem<Item> XMAS_HAT =
			ITEMS.registerItem("xmashat", Item::new);*/
	/*public static final DeferredItem<Item> INGOT_ELECTRUM = ITEMS.register("ingot_electrum", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_electrum"));
	public static final DeferredItem<Item> INGOT_SILVER = ITEMS.register("ingot_silver", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_silver"));
	public static final DeferredItem<Item> INGOT_ZINC = ITEMS.register("ingot_zinc", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_zinc"));

	public static final DeferredItem<Item> INGOT_BRONZE = ITEMS.register("ingot_bronze", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_bronze"));
	public static final DeferredItem<Item> INGOT_LEAD = ITEMS.register("ingot_lead", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_lead"));
	public static final DeferredItem<Item> INGOT_STEEL = ITEMS.register("ingot_steel", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_steel"));
	public static final DeferredItem<Item> INGOT_TIN = ITEMS.register("ingot_tin", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_tin"));
	public static final DeferredItem<Item> INGOT_URANIUM = ITEMS.register("ingot_uranium", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_uranium"));


	public static final DeferredItem<Item> INGOT_ALUMINUM = ITEMS.register("ingot_aluminum", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_aluminum"));
	public static final DeferredItem<Item> INGOT_CHROME = ITEMS.register("ingot_chrome", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_chrome"));

	public static final DeferredItem<Item> INGOT_INVAR = ITEMS.register("ingot_invar", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_invar"));
	public static final DeferredItem<Item> INGOT_IRIDIUM = ITEMS.register("ingot_iridium", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_iridium"));

	public static final DeferredItem<Item> INGOT_NICKEL = ITEMS.register("ingot_nickel", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_nickel"));
	public static final DeferredItem<Item> INGOT_PLATINUM = ITEMS.register("ingot_platinum", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_platinum"));

	public static final DeferredItem<Item> INGOT_TITANUM = ITEMS.register("ingot_titanum", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_titanum"));
	public static final DeferredItem<Item> INGOT_TUNGSTEN = ITEMS.register("ingot_tungsten", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_tungsten"));
	public static final DeferredItem<Item> INGOT_TUNGSTENSTEEL = ITEMS.register("ingot_tungstensteel", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_tungstensteel"));


	public static final DeferredItem<Item> INGOT_ENDERIUM = ITEMS.register("ingot_enderium", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_enderium"));
	public static final DeferredItem<Item> INGOT_ADAMANTIUM = ITEMS.register("ingot_adamantium", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_adamantium"));
	public static final DeferredItem<Item> INGOT_LUMIUM = ITEMS.register("ingot_lumium", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_lumium"));
	public static final DeferredItem<Item> INGOT_SIGNALUM = ITEMS.register("ingot_signalum", props -> new AnimatedIngotItem(props.stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_signalum"));*/


	public static final DeferredItem<Item> WATER_EGG = ITEMS.registerItem("water_egg", props -> new UniversalFluidItem(props.stacksTo(64), () -> Fluids.WATER, 1000));
	public static final DeferredItem<Item> LAVA_EGG = ITEMS.registerItem("lava_egg", props -> new UniversalFluidItem(props.stacksTo(64), () -> Fluids.LAVA, 1000));

	/*public static final DeferredItem<Item> STONE_ESSENCE = ITEMS.register("stone_essence", props -> new Item(props.stacksTo(64).rarity(Rarity.COMMON)));
	public static final DeferredItem<Item> WOOD_ESSENCE = ITEMS.register("wood_essence", props -> new Item(props.stacksTo(64).rarity(Rarity.COMMON)));*/

	public static final DeferredItem<Item> XMAS_HAT =
			ITEMS.registerItem("xmashat", Item::new);

	public static final DeferredItem<Item> STONE_ESSENCE =
			ITEMS.registerItem("stone_essence", props -> new Item(base(props)));

	public static final DeferredItem<Item> WOOD_ESSENCE =
			ITEMS.registerItem("wood_essence", props -> new Item(base(props)));


	public static final DeferredItem<Item> RED_EGG = ITEMS.registerItem("red_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_red"),
			base(props)));
	public static final DeferredItem<Item> BLUE_EGG = ITEMS.registerItem("blue_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_blue"),
                        base(props)));
	public static final DeferredItem<Item> YELLOW_EGG = ITEMS.registerItem("yellow_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_yellow"),
                        base(props)));
	public static final DeferredItem<Item> WHITE_EGG = ITEMS.registerItem("white_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_white"),
                        base(props)));
	public static final DeferredItem<Item> ORANGE_EGG = ITEMS.registerItem("orange_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_orange"),
                        base(props)));
	public static final DeferredItem<Item> MAGENTA_EGG = ITEMS.registerItem("magenta_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_magenta"),
                        base(props)));
	public static final DeferredItem<Item> LIGHT_BLUE_EGG = ITEMS.registerItem("light_blue_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_light_blue"),
                        base(props)));
	public static final DeferredItem<Item> LIME_EGG = ITEMS.registerItem("lime_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_lime"),
                        base(props)));
	public static final DeferredItem<Item> PINK_EGG = ITEMS.registerItem("pink_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_pink"),
                        base(props)));
	public static final DeferredItem<Item> GRAY_EGG = ITEMS.registerItem("gray_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_gray"),
                        base(props)));
	public static final DeferredItem<Item> LIGHT_GRAY_EGG = ITEMS.registerItem("light_gray_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_light_gray"),
                        base(props)));
	public static final DeferredItem<Item> CYAN_EGG = ITEMS.registerItem("cyan_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_cyan"),
                        base(props)));
	public static final DeferredItem<Item> PURPLE_EGG = ITEMS.registerItem("purple_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_purple"),
                        base(props)));
	public static final DeferredItem<Item> BROWN_EGG = ITEMS.registerItem("brown_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_brown"),
                        base(props)));
	public static final DeferredItem<Item> GREEN_EGG = ITEMS.registerItem("green_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_green"),
                        base(props)));
	public static final DeferredItem<Item> BLACK_EGG = ITEMS.registerItem("black_egg",
                props -> new RoostEgg(
                        Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "c_black"),
                        base(props)));

	public static final DeferredItem<Item> CHICKEN_STICK = ITEMS.registerItem("chicken_stick", props -> new AnimatedChickenStick(props));

	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_1 = ITEMS.registerItem("chicken_essence_tier_1", props -> new EssenceSoul(essence(props)));
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_2 = ITEMS.registerItem("chicken_essence_tier_2", props -> new EssenceSoul(essence(props)));
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_3 = ITEMS.registerItem("chicken_essence_tier_3", props -> new EssenceSoul(essence(props)));
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_4 = ITEMS.registerItem("chicken_essence_tier_4", props -> new EssenceSoul(essence(props)));
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_5 = ITEMS.registerItem("chicken_essence_tier_5", props -> new EssenceSoul(essence(props)));
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_6 = ITEMS.registerItem("chicken_essence_tier_6", props -> new EssenceSoul(essence(props)));
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_7 = ITEMS.registerItem("chicken_essence_tier_7", props -> new EssenceSoul(essence(props)));
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_8 = ITEMS.registerItem("chicken_essence_tier_8", props -> new EssenceSoul(essence(props)));
	public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_9 = ITEMS.registerItem("chicken_essence_tier_9", props -> new EssenceSoul(essence(props)));

	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_1 = ITEMS.registerItem("chicken_food_tier_1", props -> new CropBlockItem(ModBlocks.SEED_CROP_1, props, 0));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_2 = ITEMS.registerItem("chicken_food_tier_2", props -> new CropBlockItem(ModBlocks.SEED_CROP_2, props, 1));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_3 = ITEMS.registerItem("chicken_food_tier_3", props -> new CropBlockItem(ModBlocks.SEED_CROP_3, props, 2));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_4 = ITEMS.registerItem("chicken_food_tier_4", props -> new CropBlockItem(ModBlocks.SEED_CROP_4, props, 3));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_5 = ITEMS.registerItem("chicken_food_tier_5", props -> new CropBlockItem(ModBlocks.SEED_CROP_5, props, 4));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_6 = ITEMS.registerItem("chicken_food_tier_6", props -> new CropBlockItem(ModBlocks.SEED_CROP_6, props, 5));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_7 = ITEMS.registerItem("chicken_food_tier_7", props -> new CropBlockItem(ModBlocks.SEED_CROP_7, props, 6));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_8 = ITEMS.registerItem("chicken_food_tier_8", props -> new CropBlockItem(ModBlocks.SEED_CROP_8, props, 7));
	public static final DeferredItem<Item> CHICKEN_FOOD_TIER_9 = ITEMS.registerItem("chicken_food_tier_9", props -> new CropBlockItem(ModBlocks.SEED_CROP_9, props, 8));


	public static final DeferredHolder<Item, BlockItem> BREEDER = ITEMSS.registerItem("breeder", props -> new BlockItem(ModBlocks.BREEDER.get(),
			props));
	public static final DeferredHolder<Item, BlockItem> EXTRACTOR = ITEMSS.registerItem("soul_extractor", props -> new BlockItem(ModBlocks.SOUL_EXTRACTOR.get(),
			props));
	public static final DeferredHolder<Item, BlockItem> ROOST = ITEMSS.registerItem("roost", props -> new BlockItem(ModBlocks.ROOST.get(),
			props));
	public static final DeferredHolder<Item, BlockItem> FEEDER = ITEMSS.registerItem("feeder", props -> new BlockItem(ModBlocks.FEEDER.get(),
			props));
	public static final DeferredHolder<Item, BlockItem> COLLECTOR = ITEMSS.registerItem("collector", props -> new BlockItem(ModBlocks.COLLECTOR.get(),
			props));
	public static final DeferredHolder<Item, BlockItem> SLIME_BLOCK = ITEMSS.registerItem("slime_block", props -> new BlockItem(ModBlocks.SLIMEBLOCK.get(),
			props));

	public static final DeferredHolder<Item, BlockItem> TRAINER = ITEMSS.registerItem("trainer", props -> new AnimatedTrainerBlockItem(ModBlocks.TRAINER.get(),
			props));

	/*public static final DeferredHolder<Item, BlockItem> CHICKENSTORAGE = ITEMSS.register("chickenstorage",
			() -> new BlockItem(ModBlocks.CHICKENSTORAGE.get(),
					new Item.Properties().rarity(Rarity.EPIC)){

				@Override
				public void appendHoverText(@NotNull ItemStack itemstack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> list, @NotNull TooltipFlag flag) {
					super.appendHoverText(itemstack, context, tooltipDisplay, list, flag);
					list.accept(Component.nullToEmpty("Very Big Storage"));
					list.accept(Component.nullToEmpty("For Chickens and Seeds"));
					list.accept(Component.nullToEmpty("Works only with AE2, RS, Simple Storage Network"));
					list.accept(Component.nullToEmpty("or other Mods who access Block Inventories via Interfaces"));
					list.accept(Component.nullToEmpty("Its harder than Obsidian and got a 10x explosion Resist"));
					if(itemstack.getComponents().has(ModDataComponents.STORAGEAMOUNT.value())) {
						list.accept(Component.nullToEmpty(("\u00A7e") + "Stored Items: " + ("\u00A7a") + String.format("%,d", itemstack.get(ModDataComponents.STORAGEAMOUNT))));
					}
				}
			});*/

	public static final DeferredHolder<Item, BlockItem> PIPE_TIER1 = ITEMSS.registerItem("pipe_tier1", props -> new BlockItem(ModBlocks.PIPE_TIER1.get(),
			props){

				@Override
				public void appendHoverText(@NotNull ItemStack itemstack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> list, @NotNull TooltipFlag flag) {
					super.appendHoverText(itemstack, context, tooltipDisplay, list, flag);
					list.accept(Component.translatable("pipe.chicken_roost.transfer.info.tier1"));
					list.accept(Component.translatable("pipe.chicken_roost.transfer.pipe.tier1"));
					list.accept(Component.translatable("pipe.chicken_roost.transfer.pipe.modeinfo"));
				}
			});

	public static final DeferredHolder<Item, BlockItem> PIPE_TIER2 = ITEMSS.registerItem("pipe_tier2", props -> new BlockItem(ModBlocks.PIPE_TIER2.get(),
			props){

				@Override
				public void appendHoverText(@NotNull ItemStack itemstack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> list, @NotNull TooltipFlag flag) {
					super.appendHoverText(itemstack, context, tooltipDisplay, list, flag);
					list.accept(Component.translatable("pipe.chicken_roost.transfer.info.tier2"));
					list.accept(Component.translatable("pipe.chicken_roost.transfer.pipe.tier2"));
					list.accept(Component.translatable("pipe.chicken_roost.transfer.pipe.modeinfo"));
				}
			});

	public static final DeferredHolder<Item, BlockItem> PIPE_TIER3 = ITEMSS.registerItem("pipe_tier3", props -> new BlockItem(ModBlocks.PIPE_TIER3.get(),
			props){

				@Override
				public void appendHoverText(@NotNull ItemStack itemstack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> list, @NotNull TooltipFlag flag) {
					super.appendHoverText(itemstack, context, tooltipDisplay, list, flag);
					list.accept(Component.translatable("pipe.chicken_roost.transfer.info.tier3"));
					list.accept(Component.translatable("pipe.chicken_roost.transfer.pipe.tier3"));
					list.accept(Component.translatable("pipe.chicken_roost.transfer.pipe.modeinfo"));
				}
			});

	public static final DeferredHolder<Item, BlockItem> PIPE_TIER4 = ITEMSS.registerItem("pipe_tier4", props -> new BlockItem(ModBlocks.PIPE_TIER4.get(),
			props){

				@Override
				public void appendHoverText(@NotNull ItemStack itemstack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> list, @NotNull TooltipFlag flag) {
					super.appendHoverText(itemstack, context, tooltipDisplay, list, flag);
					list.accept(Component.translatable("pipe.chicken_roost.transfer.info.tier4"));
					list.accept(Component.translatable("pipe.chicken_roost.transfer.pipe.tier4"));
					list.accept(Component.translatable("pipe.chicken_roost.transfer.pipe.modeinfo"));
				}
			});

	private static final List<DeferredItem<? extends Item>> DYNAMIC_CHICKEN_ITEMS = new ArrayList<>();


	public static void readthis() {

		List<ChickenData> readItems = ChickenRoostMod.chickens;
        assert readItems != null;
        if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){

				String id = etherItem.getId();
				String itemtexture = etherItem.getItemtexture();
				String mobtexture = etherItem.getMobtexture();
				String dropitem = etherItem.getDropitem();
				float eggtime = etherItem.getEggtime();
                int tierr = etherItem.getTier();

				/*Identifier idkey = Identifier.fromNamespaceAndPath(
						ChickenRoostMod.MODID,
						etherItem.getId()
				);
				ResourceKey<Item> key = ResourceKey.create(
						Registries.ITEM,
						idkey
				);*/

				extrachickens(id, itemtexture, tierr);

			}
		}
	}

	public static void extrachickens(String idd, String texturee, int tierr) {
		DeferredItem<Item> deferred = switch (tierr) {
            case 2 -> CHICKENITEMS.registerItem(idd, props -> new AnimatedChicken(chicken(props), texturee,1));
			case 3 -> CHICKENITEMS.registerItem(idd, props -> new AnimatedChicken(chicken(props), texturee,2));
			case 4 -> CHICKENITEMS.registerItem(idd, props -> new AnimatedChicken(chicken(props), texturee,3));
			case 5 -> CHICKENITEMS.registerItem(idd, props -> new AnimatedChicken(chicken(props), texturee,4));
			case 6 -> CHICKENITEMS.registerItem(idd, props -> new AnimatedChicken(chicken(props), texturee,5));
			case 7 -> CHICKENITEMS.registerItem(idd, props -> new AnimatedChicken(chicken(props), texturee,6));
			case 8 -> CHICKENITEMS.registerItem(idd, props -> new AnimatedChicken(chicken(props), texturee,7));
			case 9 -> CHICKENITEMS.registerItem(idd, props -> new AnimatedChicken(chicken(props), texturee,8));
			default -> CHICKENITEMS.registerItem(idd, props -> new AnimatedChicken(chicken(props), texturee,0));
		};

		DYNAMIC_CHICKEN_ITEMS.add(deferred);

	}
	private static Item.Properties base(Item.Properties props) {
		return props
				.stacksTo(64)
				.rarity(Rarity.COMMON);
	}

	private static Item.Properties chicken(Item.Properties props) {
		return base(props)
				.fireResistant()
				.component(ModDataComponents.CHICKENLEVEL.value(), 0)
				.component(ModDataComponents.CHICKENXP.value(), 0)
				.component(ModDataComponents.MAXLEVEL.value(), false);
	}

	private static Item.Properties essence(Item.Properties props) {
		return base(props)
				.food(new FoodProperties.Builder()
						.nutrition(4)
						.saturationModifier(0.3f)
						.alwaysEdible()
						.build());
	}
	private static Item.Properties propss() {
		return new Item.Properties()
				.stacksTo(64)
				.fireResistant()
				.rarity(Rarity.COMMON)
				.component(ModDataComponents.CHICKENLEVEL.value(), 0)
				.component(ModDataComponents.CHICKENXP.value(), 0)
				.component(ModDataComponents.MAXLEVEL.value(), false);
	}


	public static void register(IEventBus eventBus) {
		readthis();
		ITEMS.register(eventBus);
		ITEMSS.register(eventBus);
		CHICKENITEMS.register(eventBus);

	}
}
