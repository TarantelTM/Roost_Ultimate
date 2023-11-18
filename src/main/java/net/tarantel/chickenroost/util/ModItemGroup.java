package net.tarantel.chickenroost.util;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.ModBlocks;
import net.tarantel.chickenroost.item.ModItems;

public class ModItemGroup {
    public static final ItemGroup BLOCKS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ChickenRoostMod.MODID, "roost"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.roost"))
                    .icon(() -> new ItemStack(ModBlocks.ROOST)).entries((displayContext, entries) -> {
                        entries.add(ModItems.CHICKEN_STICK);
                        entries.add(ModItems.C_COBBLE);
                        entries.add(ModItems.C_OAKWOOD);
                        entries.add(ModItems.C_ANDESITE);
                        entries.add(ModItems.C_SAND);
                        entries.add(ModItems.C_VANILLA);
                        entries.add(ModItems.C_GRAVEL);
                        entries.add(ModItems.C_DARKOAKWOOD);
                        entries.add(ModItems.C_GRANIT);
                        entries.add(ModItems.C_BIRCHWOOD);
                        entries.add(ModItems.C_SPRUCEWOOD);
                        entries.add(ModItems.C_HONEYCOMB);
                        entries.add(ModItems.C_FEATHER);
                        entries.add(ModItems.C_WOOL);
                        entries.add(ModItems.C_ACACIAWOOD);
                        entries.add(ModItems.C_STONE);
                        entries.add(ModItems.C_DIORITE);
                        entries.add(ModItems.C_JUNGLEWOOD);
                        entries.add(ModItems.C_MELON);
                        entries.add(ModItems.C_WARPEDSTEM);
                        entries.add(ModItems.C_NETHERRACK);
                        entries.add(ModItems.C_SNOW);
                        entries.add(ModItems.C_GLASS);
                        entries.add(ModItems.C_SUGAR);
                        entries.add(ModItems.C_CRIMSONSTEM);
                        entries.add(ModItems.C_FLINT);
                        entries.add(ModItems.C_APPLE);
                        entries.add(ModItems.C_BONE);
                        entries.add(ModItems.C_COAL);
                        entries.add(ModItems.C_CARROT);
                        entries.add(ModItems.C_INK);
                        entries.add(ModItems.C_BEETROOT);
                        entries.add(ModItems.C_SWEETBERRIES);
                        entries.add(ModItems.C_GLOWBERRIES);
                        entries.add(ModItems.C_SOULSOIL);
                        entries.add(ModItems.C_STRING);
                        entries.add(ModItems.C_BASALT);
                        entries.add(ModItems.C_COPPER);
                        entries.add(ModItems.C_CLAY);
                        entries.add(ModItems.C_SOULSAND);
                        entries.add(ModItems.C_SPONGE);
                        entries.add(ModItems.C_LEATHER);
                        entries.add(ModItems.C_NETHERWART);
                        entries.add(ModItems.C_REDSTONE);
                        entries.add(ModItems.C_LAPIS);
                        entries.add(ModItems.C_OBSIDIAN);
                        entries.add(ModItems.C_MAGMACREAM);
                        entries.add(ModItems.C_IRON);
                        entries.add(ModItems.C_ROTTEN);
                        entries.add(ModItems.C_SLIME);
                        entries.add(ModItems.C_CHORUSFRUIT);
                        entries.add(ModItems.C_GLOWSTONE);
                        entries.add(ModItems.C_ENDSTONE);
                        entries.add(ModItems.C_GOLD);
                        entries.add(ModItems.C_BLAZEROD);
                        entries.add(ModItems.C_NETHERQUARTZ);
                        entries.add(ModItems.C_TNT);
                        entries.add(ModItems.C_ENDERPEARL);
                        entries.add(ModItems.C_EMERALD);
                        entries.add(ModItems.C_GHASTTEAR);
                        entries.add(ModItems.C_DIAMOND);
                        entries.add(ModItems.C_NETHERITE);
                        entries.add(ModItems.C_NETHERSTAR);
                        entries.add(ModItems.CHICKEN_ESSENCE_TIER_1);
                        entries.add(ModItems.CHICKEN_ESSENCE_TIER_2);
                        entries.add(ModItems.CHICKEN_ESSENCE_TIER_3);
                        entries.add(ModItems.CHICKEN_ESSENCE_TIER_4);
                        entries.add(ModItems.CHICKEN_ESSENCE_TIER_5);
                        entries.add(ModItems.CHICKEN_ESSENCE_TIER_6);
                        entries.add(ModItems.CHICKEN_ESSENCE_TIER_7);
                        entries.add(ModItems.CHICKEN_ESSENCE_TIER_8);
                        entries.add(ModItems.CHICKEN_ESSENCE_TIER_9);
                        entries.add(ModItems.CHICKEN_FOOD_TIER_1);
                        entries.add(ModItems.CHICKEN_FOOD_TIER_2);
                        entries.add(ModItems.CHICKEN_FOOD_TIER_3);
                        entries.add(ModItems.CHICKEN_FOOD_TIER_4);
                        entries.add(ModItems.CHICKEN_FOOD_TIER_5);
                        entries.add(ModItems.CHICKEN_FOOD_TIER_6);
                        entries.add(ModItems.CHICKEN_FOOD_TIER_7);
                        entries.add(ModItems.CHICKEN_FOOD_TIER_8);
                        entries.add(ModItems.CHICKEN_FOOD_TIER_9);
                        entries.add(ModBlocks.BREEDER);
                        entries.add(ModBlocks.TRAINER);
                        entries.add(ModBlocks.ROOST);



                    }).build());


    public static void registerItemGroups() {
        ChickenRoostMod.LOGGER.info("Registering Item Groups for " + ChickenRoostMod.MODID);
    }
}
