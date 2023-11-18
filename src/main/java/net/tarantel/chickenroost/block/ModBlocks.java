package net.tarantel.chickenroost.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.util.ModItemGroup;

public class ModBlocks {


    ////CROPS
    public static final Block CROPBLOCK_1 = registerBlockWithoutItem("cropblock_1",
            new CropBlock_1(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block CROPBLOCK_2 = registerBlockWithoutItem("cropblock_2",
            new CropBlock_2(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block CROPBLOCK_3 = registerBlockWithoutItem("cropblock_3",
            new CropBlock_3(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block CROPBLOCK_4 = registerBlockWithoutItem("cropblock_4",
            new CropBlock_4(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block CROPBLOCK_5 = registerBlockWithoutItem("cropblock_5",
            new CropBlock_5(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block CROPBLOCK_6 = registerBlockWithoutItem("cropblock_6",
            new CropBlock_6(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block CROPBLOCK_7 = registerBlockWithoutItem("cropblock_7",
            new CropBlock_7(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block CROPBLOCK_8 = registerBlockWithoutItem("cropblock_8",
            new CropBlock_8(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block CROPBLOCK_9 = registerBlockWithoutItem("cropblock_9",
            new CropBlock_9(FabricBlockSettings.copyOf(Blocks.WHEAT)));

    ////MACHINES

    public static final Block BREEDER = registerBlock("breeder",
            new breeder_block(FabricBlockSettings.copyOf(Blocks.BIRCH_WOOD).strength(1f).requiresTool().hardness(1f).nonOpaque()));
    public static final Block TRAINER = registerBlock("trainer",
            new trainer_block(FabricBlockSettings.copyOf(Blocks.BIRCH_WOOD).strength(1f).requiresTool().hardness(1f).nonOpaque()));

    public static final Block ROOST = registerBlock("roost__v1",
            new roost_block(FabricBlockSettings.copyOf(Blocks.BIRCH_WOOD).strength(1f).requiresTool().hardness(1f).nonOpaque()));


    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(ChickenRoostMod.MODID, name), block);
    }
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ChickenRoostMod.MODID, name), block);
    }




    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(ChickenRoostMod.MODID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    /*private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        Item item = Registry.register(Registries.ITEM, new Identifier(ChickenRoostMod.MODID, name),
                new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(group.).register(entries -> entries.add(item));
        return item;
    }*/
    /*private static Item registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, new Identifier(ChickenRoostMod.MODID, name),
                new BlockItem(block, new FabricItemSettings()));
        return item;
    }*/
    public static void registerModBlocks(){
        ChickenRoostMod.LOGGER.debug("Registering Blocks - " + ChickenRoostMod.MODID);
    }
}
