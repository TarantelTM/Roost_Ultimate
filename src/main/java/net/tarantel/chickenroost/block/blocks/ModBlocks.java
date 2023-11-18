package net.tarantel.chickenroost.block.blocks;


import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.crops.*;
import net.tarantel.chickenroost.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChickenRoostMod.MODID);


    public static final RegistryObject<Soul_Breeder_Block> SOUL_BREEDER = BLOCKS.register("soul_breeder",
            () -> new Soul_Breeder_Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(1f).noOcclusion()));
    public static final RegistryObject<Block> BREEDER = registerBlock("breeder",
            () -> new Breeder_Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(1f).noOcclusion()));

    public static final RegistryObject<Block> SOUL_EXTRACTOR = registerBlock("soul_extractor",
            () -> new Soul_Extractor_Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(1f).noOcclusion()));
    public static final RegistryObject<Block> ROOST = registerBlock("roost",
            () -> new Roost_Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(1f).noOcclusion()));
    public static final RegistryObject<Block> TRAINER = BLOCKS.register("trainer",
            () -> new Trainer_Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(1f).noOcclusion()));

    public static final RegistryObject<Block> SEED_CROP_1 = registerBlock("seed_crop_1",
            () -> new Crop_Block_1(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> SEED_CROP_2 = registerBlock("seed_crop_2",
            () -> new Crop_Block_2(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> SEED_CROP_3 = registerBlock("seed_crop_3",
            () -> new Crop_Block_3(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> SEED_CROP_4 = registerBlock("seed_crop_4",
            () -> new Crop_Block_4(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> SEED_CROP_5 = registerBlock("seed_crop_5",
            () -> new Crop_Block_5(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> SEED_CROP_6 = registerBlock("seed_crop_6",
            () -> new Crop_Block_6(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> SEED_CROP_7 = registerBlock("seed_crop_7",
            () -> new Crop_Block_7(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> SEED_CROP_8 = registerBlock("seed_crop_8",
            () -> new Crop_Block_8(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> SEED_CROP_9 = registerBlock("seed_crop_9",
            () -> new Crop_Block_9(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}