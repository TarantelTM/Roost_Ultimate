package net.tarantel.chickenroost.block.blocks;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.block.blocks.crops.*;
import net.tarantel.chickenroost.item.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks("chicken_roost");


    public static final DeferredBlock<Block> COLLECTOR = registerBlock("collector",
            () -> new CollectorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .strength(1f).noOcclusion()));

    public static final DeferredBlock<Block> FEEDER = registerBlock("feeder",
            () -> new FeederBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .strength(1f).noOcclusion()));


    public static final DeferredBlock<ChickenStorageBlock> CHICKENSTORAGE = BLOCKS.register("chickenstorage",
            () -> new ChickenStorageBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .strength(70.0F, 120000.0F).noOcclusion()));

    public static final DeferredBlock<SoulBreederBlock> SOUL_BREEDER = BLOCKS.register("soul_breeder",
            () -> new SoulBreederBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .strength(1f).noOcclusion()));
    public static final DeferredBlock<Block> BREEDER = registerBlock("breeder",
            () -> new BreederBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .strength(1f).noOcclusion()));

    public static final DeferredBlock<Block> SOUL_EXTRACTOR = registerBlock("soul_extractor",
            () -> new SoulExtractorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .strength(1f).noOcclusion()));
    public static final DeferredBlock<Block> ROOST = registerBlock("roost",
            () -> new RoostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .strength(1f).noOcclusion()));
    public static final DeferredBlock<Block> TRAINER = BLOCKS.register("trainer",
            () -> new TrainerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .strength(1f).noOcclusion()));

    public static final DeferredBlock<SlimeBlock> SLIMEBLOCK = registerBlock("slime_block",
            () -> new SlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion().lightLevel(p_152607_ -> 15)){
        @Override
        public void updateEntityAfterFallOn(@NotNull BlockGetter level, @NotNull Entity entity) {
                    if (entity.isSuppressingBounce()) {
                        super.updateEntityAfterFallOn(level, entity);
                    } else {
                        this.bounceUp(entity);
                    }
        }

        private void bounceUp(Entity entity) {
            Vec3 vec3 = entity.getDeltaMovement();
            if (vec3.y < 0.0) {
                double d0 = entity instanceof LivingEntity ? 10.0 : 0.8;
                entity.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
            }
        }
    });

    public static final DeferredBlock<Block> SEED_CROP_1 = BLOCKS.register("seed_crop_1",
            () -> new CropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(), 0));
    public static final DeferredBlock<Block> SEED_CROP_2 = BLOCKS.register("seed_crop_2",
            () -> new CropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(),1));
    public static final DeferredBlock<Block> SEED_CROP_3 = BLOCKS.register("seed_crop_3",
            () -> new CropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(),2));
    public static final DeferredBlock<Block> SEED_CROP_4 = BLOCKS.register("seed_crop_4",
            () -> new CropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(),3));
    public static final DeferredBlock<Block> SEED_CROP_5 = BLOCKS.register("seed_crop_5",
            () -> new CropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(),4));
    public static final DeferredBlock<Block> SEED_CROP_6 = BLOCKS.register("seed_crop_6",
            () -> new CropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(),5));
    public static final DeferredBlock<Block> SEED_CROP_7 = BLOCKS.register("seed_crop_7",
            () -> new CropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(),6));
    public static final DeferredBlock<Block> SEED_CROP_8 = BLOCKS.register("seed_crop_8",
            () -> new CropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(),7));
    public static final DeferredBlock<Block> SEED_CROP_9 = BLOCKS.register("seed_crop_9",
            () -> new CropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noOcclusion().noCollission(),8));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredItem<Item> registerBlockItem(String name, DeferredBlock<T> block) {
        return ModItems.ITEMSS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}