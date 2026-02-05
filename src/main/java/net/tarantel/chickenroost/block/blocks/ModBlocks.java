package net.tarantel.chickenroost.block.blocks;


import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
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
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.crops.CropBlock;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.pipe.PipeBlock;
import net.tarantel.chickenroost.pipe.PipeTier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(ChickenRoostMod.MODID);


    public static final DeferredBlock<Block> COLLECTOR =
            BLOCKS.registerBlock("collector", props ->
                    new CollectorBlock(props.strength(1.0F).sound(SoundType.METAL).noOcclusion()));


    public static final DeferredBlock<Block> FEEDER = BLOCKS.registerBlock("feeder", props ->
            new FeederBlock(props.strength(1.0F).sound(SoundType.METAL).noOcclusion()));


    public static final DeferredBlock<@NotNull ChickenStorageBlock> CHICKENSTORAGE = BLOCKS.registerBlock("chickenstorage", props ->
            new ChickenStorageBlock(props.strength(1.0F).sound(SoundType.METAL).noOcclusion()));

    public static final DeferredBlock<@NotNull Block> BREEDER = BLOCKS.registerBlock("breeder", props ->
            new BreederBlock(props.strength(1.0F).sound(SoundType.METAL).noOcclusion()));

    public static final DeferredBlock<@NotNull PipeBlock> PIPE_TIER1 =
            BLOCKS.registerBlock("pipe_tier1", props ->
                    new PipeBlock(props.strength(1.0F).sound(SoundType.METAL).strength(1f).noOcclusion(),
                            PipeTier.TIER1,
                            ModItems.CHICKEN_STICK));

    public static final DeferredBlock<@NotNull PipeBlock> PIPE_TIER2 =
            BLOCKS.registerBlock("pipe_tier2", props ->
                    new PipeBlock(props.strength(1.0F).sound(SoundType.METAL).strength(1f).noOcclusion(),
                            PipeTier.TIER2,
                            ModItems.CHICKEN_STICK));

    public static final DeferredBlock<@NotNull PipeBlock> PIPE_TIER3 =
            BLOCKS.registerBlock("pipe_tier3", props ->
                    new PipeBlock(props.strength(1.0F).sound(SoundType.METAL).strength(1f).noOcclusion(),
                            PipeTier.TIER3,
                            ModItems.CHICKEN_STICK));

    public static final DeferredBlock<@NotNull PipeBlock> PIPE_TIER4 =
            BLOCKS.registerBlock("pipe_tier4", props ->
                    new PipeBlock(props.strength(1.0F).sound(SoundType.METAL).noOcclusion(),
                            PipeTier.TIER4,
                            ModItems.CHICKEN_STICK));

    public static final DeferredBlock<Block> SOUL_EXTRACTOR = BLOCKS.registerBlock("soul_extractor", props ->
            new SoulExtractorBlock(props.strength(1.0F).sound(SoundType.METAL)));
    public static final DeferredBlock<Block> ROOST = BLOCKS.registerBlock("roost", props ->
            new RoostBlock(props.strength(1.0F).sound(SoundType.METAL).noOcclusion()));
    public static final DeferredBlock<Block> TRAINER = BLOCKS.registerBlock("trainer", props ->
            new TrainerBlock(props.strength(1.0F).sound(SoundType.METAL).noOcclusion()));

    public static final DeferredBlock<SlimeBlock> SLIMEBLOCK = BLOCKS.registerBlock("slime_block", props ->
            new SlimeBlock(props.mapColor(MapColor.GRASS).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion().lightLevel(p_152607_ -> 15)){
        @Override
        public void updateEntityMovementAfterFallOn(@NotNull BlockGetter level, @NotNull Entity entity) {
                    if (entity.isSuppressingBounce()) {
                        super.updateEntityMovementAfterFallOn(level, entity);
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

    public static final DeferredBlock<Block> SEED_CROP_1 = BLOCKS.registerBlock("seed_crop_1", props ->
            new CropBlock(props.noOcclusion().noCollision(), 0));
    public static final DeferredBlock<Block> SEED_CROP_2 = BLOCKS.registerBlock("seed_crop_2", props ->
                    new CropBlock(props.noOcclusion().noCollision(),1));
    public static final DeferredBlock<Block> SEED_CROP_3 = BLOCKS.registerBlock("seed_crop_3", props ->
                    new CropBlock(props.noOcclusion().noCollision(),2));
    public static final DeferredBlock<Block> SEED_CROP_4 = BLOCKS.registerBlock("seed_crop_4", props ->
                    new CropBlock(props.noOcclusion().noCollision(),3));
    public static final DeferredBlock<Block> SEED_CROP_5 = BLOCKS.registerBlock("seed_crop_5", props ->
                    new CropBlock(props.noOcclusion().noCollision(),4));
    public static final DeferredBlock<Block> SEED_CROP_6 = BLOCKS.registerBlock("seed_crop_6", props ->
                    new CropBlock(props.noOcclusion().noCollision(),5));
    public static final DeferredBlock<Block> SEED_CROP_7 = BLOCKS.registerBlock("seed_crop_7", props ->
                    new CropBlock(props.noOcclusion().noCollision(),6));
    public static final DeferredBlock<Block> SEED_CROP_8 = BLOCKS.registerBlock("seed_crop_8", props ->
                    new CropBlock(props.noOcclusion().noCollision(),7));
    public static final DeferredBlock<Block> SEED_CROP_9 = BLOCKS.registerBlock("seed_crop_9", props ->
                    new CropBlock(props.noOcclusion().noCollision(),8));


    /*private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }*/

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    /*private static <T extends Block> DeferredItem<Item> registerBlockItem(String name, DeferredBlock<T> block) {
        return ModItems.ITEMSS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }*/
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerItem(name, (properties) -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}