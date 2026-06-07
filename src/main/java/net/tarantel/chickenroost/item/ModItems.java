package net.tarantel.chickenroost.item;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.base.AnimatedChicken;
import net.tarantel.chickenroost.item.base.AnimatedChickenStick;
import net.tarantel.chickenroost.item.base.AnimatedIngotItem;
import net.tarantel.chickenroost.item.base.AnimatedTrainerBlockItem;
import net.tarantel.chickenroost.item.base.CropBlockItem;
import net.tarantel.chickenroost.item.base.EssenceSoul;
import net.tarantel.chickenroost.util.ChickenData;
import net.tarantel.chickenroost.util.ModDataComponents;
import net.tarantel.crlib.util.UniversalFluidItem;
import org.jetbrains.annotations.NotNull;

public class ModItems {
   public static final Items ITEMS = DeferredRegister.createItems("chicken_roost");
   public static final Items ITEMSS = DeferredRegister.createItems("chicken_roost");
   public static final Items CHICKENITEMS = DeferredRegister.createItems("chicken_roost");
   public static final DeferredItem<Item> XMAS_HAT = ITEMS.register("xmashat", () -> new Item(new Properties()));
   public static final DeferredItem<Item> INGOT_ELECTRUM = ITEMS.register(
      "ingot_electrum", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_electrum")
   );
   public static final DeferredItem<Item> INGOT_SILVER = ITEMS.register(
      "ingot_silver", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_silver")
   );
   public static final DeferredItem<Item> INGOT_ZINC = ITEMS.register(
      "ingot_zinc", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_zinc")
   );
   public static final DeferredItem<Item> INGOT_BRONZE = ITEMS.register(
      "ingot_bronze", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_bronze")
   );
   public static final DeferredItem<Item> INGOT_LEAD = ITEMS.register(
      "ingot_lead", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_lead")
   );
   public static final DeferredItem<Item> INGOT_STEEL = ITEMS.register(
      "ingot_steel", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_steel")
   );
   public static final DeferredItem<Item> INGOT_TIN = ITEMS.register(
      "ingot_tin", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_tin")
   );
   public static final DeferredItem<Item> INGOT_URANIUM = ITEMS.register(
      "ingot_uranium", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_uranium")
   );
   public static final DeferredItem<Item> INGOT_ALUMINUM = ITEMS.register(
      "ingot_aluminum", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_aluminum")
   );
   public static final DeferredItem<Item> INGOT_CHROME = ITEMS.register(
      "ingot_chrome", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_chrome")
   );
   public static final DeferredItem<Item> INGOT_INVAR = ITEMS.register(
      "ingot_invar", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_invar")
   );
   public static final DeferredItem<Item> INGOT_IRIDIUM = ITEMS.register(
      "ingot_iridium", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_iridium")
   );
   public static final DeferredItem<Item> INGOT_NICKEL = ITEMS.register(
      "ingot_nickel", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_nickel")
   );
   public static final DeferredItem<Item> INGOT_PLATINUM = ITEMS.register(
      "ingot_platinum", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_platinum")
   );
   public static final DeferredItem<Item> INGOT_TITANUM = ITEMS.register(
      "ingot_titanum", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_titanum")
   );
   public static final DeferredItem<Item> INGOT_TUNGSTEN = ITEMS.register(
      "ingot_tungsten", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_tungsten")
   );
   public static final DeferredItem<Item> INGOT_TUNGSTENSTEEL = ITEMS.register(
      "ingot_tungstensteel", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_tungstensteel")
   );
   public static final DeferredItem<Item> INGOT_ENDERIUM = ITEMS.register(
      "ingot_enderium", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_enderium")
   );
   public static final DeferredItem<Item> INGOT_ADAMANTIUM = ITEMS.register(
      "ingot_adamantium", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_adamantium")
   );
   public static final DeferredItem<Item> INGOT_LUMIUM = ITEMS.register(
      "ingot_lumium", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_lumium")
   );
   public static final DeferredItem<Item> INGOT_SIGNALUM = ITEMS.register(
      "ingot_signalum", () -> new AnimatedIngotItem(new Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), "ingot_signalum")
   );
   public static final DeferredItem<Item> WATER_EGG = ITEMS.register(
      "water_egg", () -> new UniversalFluidItem(new Properties().stacksTo(64), () -> Fluids.WATER, 1000)
   );
   public static final DeferredItem<Item> LAVA_EGG = ITEMS.register(
      "lava_egg", () -> new UniversalFluidItem(new Properties().stacksTo(64), () -> Fluids.LAVA, 1000)
   );
   public static final DeferredItem<Item> STONE_ESSENCE = ITEMS.register("stone_essence", () -> new Item(new Properties().stacksTo(64).rarity(Rarity.COMMON)));
   public static final DeferredItem<Item> WOOD_ESSENCE = ITEMS.register("wood_essence", () -> new Item(new Properties().stacksTo(64).rarity(Rarity.COMMON)));
   public static final DeferredItem<Item> RED_EGG = ITEMS.register(
      "red_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_red"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> BLUE_EGG = ITEMS.register(
      "blue_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_blue"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> YELLOW_EGG = ITEMS.register(
      "yellow_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_yellow"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> WHITE_EGG = ITEMS.register(
      "white_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_white"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> ORANGE_EGG = ITEMS.register(
      "orange_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_orange"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> MAGENTA_EGG = ITEMS.register(
      "magenta_egg",
      () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_magenta"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> LIGHT_BLUE_EGG = ITEMS.register(
      "light_blue_egg",
      () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_light_blue"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> LIME_EGG = ITEMS.register(
      "lime_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_lime"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> PINK_EGG = ITEMS.register(
      "pink_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_pink"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> GRAY_EGG = ITEMS.register(
      "gray_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_gray"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> LIGHT_GRAY_EGG = ITEMS.register(
      "light_gray_egg",
      () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_light_gray"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> CYAN_EGG = ITEMS.register(
      "cyan_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_cyan"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> PURPLE_EGG = ITEMS.register(
      "purple_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_purple"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> BROWN_EGG = ITEMS.register(
      "brown_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_brown"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> GREEN_EGG = ITEMS.register(
      "green_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_green"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> BLACK_EGG = ITEMS.register(
      "black_egg", () -> new RoostEgg(ResourceLocation.fromNamespaceAndPath("chicken_roost", "c_black"), new Properties().stacksTo(64).rarity(Rarity.COMMON))
   );
   public static final DeferredItem<Item> CHICKEN_STICK = ITEMS.register("chicken_stick", () -> new AnimatedChickenStick(new Properties()));
   public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_1 = ITEMS.register("chicken_essence_tier_1", EssenceSoul::new);
   public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_2 = ITEMS.register("chicken_essence_tier_2", EssenceSoul::new);
   public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_3 = ITEMS.register("chicken_essence_tier_3", EssenceSoul::new);
   public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_4 = ITEMS.register("chicken_essence_tier_4", EssenceSoul::new);
   public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_5 = ITEMS.register("chicken_essence_tier_5", EssenceSoul::new);
   public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_6 = ITEMS.register("chicken_essence_tier_6", EssenceSoul::new);
   public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_7 = ITEMS.register("chicken_essence_tier_7", EssenceSoul::new);
   public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_8 = ITEMS.register("chicken_essence_tier_8", EssenceSoul::new);
   public static final DeferredItem<Item> CHICKEN_ESSENCE_TIER_9 = ITEMS.register("chicken_essence_tier_9", EssenceSoul::new);
   public static final DeferredItem<Item> CHICKEN_FOOD_TIER_1 = ITEMS.register(
      "chicken_food_tier_1", () -> new CropBlockItem((Block)ModBlocks.SEED_CROP_1.get(), new Properties(), 0)
   );
   public static final DeferredItem<Item> CHICKEN_FOOD_TIER_2 = ITEMS.register(
      "chicken_food_tier_2", () -> new CropBlockItem((Block)ModBlocks.SEED_CROP_2.get(), new Properties(), 1)
   );
   public static final DeferredItem<Item> CHICKEN_FOOD_TIER_3 = ITEMS.register(
      "chicken_food_tier_3", () -> new CropBlockItem((Block)ModBlocks.SEED_CROP_3.get(), new Properties(), 2)
   );
   public static final DeferredItem<Item> CHICKEN_FOOD_TIER_4 = ITEMS.register(
      "chicken_food_tier_4", () -> new CropBlockItem((Block)ModBlocks.SEED_CROP_4.get(), new Properties(), 3)
   );
   public static final DeferredItem<Item> CHICKEN_FOOD_TIER_5 = ITEMS.register(
      "chicken_food_tier_5", () -> new CropBlockItem((Block)ModBlocks.SEED_CROP_5.get(), new Properties(), 4)
   );
   public static final DeferredItem<Item> CHICKEN_FOOD_TIER_6 = ITEMS.register(
      "chicken_food_tier_6", () -> new CropBlockItem((Block)ModBlocks.SEED_CROP_6.get(), new Properties(), 5)
   );
   public static final DeferredItem<Item> CHICKEN_FOOD_TIER_7 = ITEMS.register(
      "chicken_food_tier_7", () -> new CropBlockItem((Block)ModBlocks.SEED_CROP_7.get(), new Properties(), 6)
   );
   public static final DeferredItem<Item> CHICKEN_FOOD_TIER_8 = ITEMS.register(
      "chicken_food_tier_8", () -> new CropBlockItem((Block)ModBlocks.SEED_CROP_8.get(), new Properties(), 7)
   );
   public static final DeferredItem<Item> CHICKEN_FOOD_TIER_9 = ITEMS.register(
      "chicken_food_tier_9", () -> new CropBlockItem((Block)ModBlocks.SEED_CROP_9.get(), new Properties(), 8)
   );
   public static final DeferredHolder<Item, BlockItem> TRAINER = ITEMSS.register(
      "trainer", () -> new AnimatedTrainerBlockItem((Block)ModBlocks.TRAINER.get(), new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> CHICKENSTORAGE = ITEMSS.register(
      "chickenstorage", () -> new BlockItem((Block)ModBlocks.CHICKENSTORAGE.get(), new Properties().rarity(Rarity.EPIC)) {
         public void appendHoverText(@NotNull ItemStack itemstack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
            super.appendHoverText(itemstack, context, list, flag);
            list.add(Component.nullToEmpty("Very Big Storage"));
            list.add(Component.nullToEmpty("For Chickens and Seeds"));
            list.add(Component.nullToEmpty("Works only with AE2, RS, Simple Storage Network"));
            list.add(Component.nullToEmpty("or other Mods who access Block Inventories via Interfaces"));
            list.add(Component.nullToEmpty("Its harder than Obsidian and got a 10x explosion Resist"));
            if (itemstack.getComponents().has((DataComponentType)ModDataComponents.STORAGEAMOUNT.value())) {
               list.add(Component.nullToEmpty("§eStored Items: §a" + String.format("%,d", itemstack.get(ModDataComponents.STORAGEAMOUNT))));
            }
         }
      }
   );
   private static final List<DeferredItem<? extends Item>> DYNAMIC_CHICKEN_ITEMS = new ArrayList<>();

   public static void readthis() {
      List<ChickenData> readItems = ChickenRoostMod.chickens;

      assert readItems != null;

      if (!readItems.isEmpty()) {
         for (ChickenData etherItem : readItems) {
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

   public static void extrachickens(String idd, String texturee, int tierr) {
      DeferredItem<Item> deferred = switch (tierr) {
         case 2 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee, 1));
         case 3 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee, 2));
         case 4 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee, 3));
         case 5 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee, 4));
         case 6 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee, 5));
         case 7 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee, 6));
         case 8 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee, 7));
         case 9 -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee, 8));
         default -> CHICKENITEMS.register(idd, () -> new AnimatedChicken(props(), texturee, 0));
      };
      DYNAMIC_CHICKEN_ITEMS.add(deferred);
   }

   private static Properties props() {
      return new Properties()
         .stacksTo(64)
         .fireResistant()
         .rarity(Rarity.COMMON)
         .component((DataComponentType)ModDataComponents.CHICKENLEVEL.value(), 0)
         .component((DataComponentType)ModDataComponents.CHICKENXP.value(), 0)
         .component((DataComponentType)ModDataComponents.MAXLEVEL.value(), false);
   }

   public static void register(IEventBus eventBus) {
      readthis();
      ITEMS.register(eventBus);
      ITEMSS.register(eventBus);
      CHICKENITEMS.register(eventBus);
   }
}
