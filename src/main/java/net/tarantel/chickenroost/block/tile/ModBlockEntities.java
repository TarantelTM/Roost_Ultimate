package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.pipe.PipeBlockEntity;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ChickenRoostMod.MODID);




    public static final Supplier<BlockEntityType<CollectorTile>> COLLECTOR =
            BLOCK_ENTITIES.register("collector", () -> new BlockEntityType<>(
                    CollectorTile::new,
                            ModBlocks.COLLECTOR.get()));

    public static final Supplier<BlockEntityType<FeederTile>> FEEDER =
            BLOCK_ENTITIES.register("feeder", () -> new BlockEntityType<>(
                    FeederTile::new,
                            ModBlocks.FEEDER.get()));


    public static final Supplier<BlockEntityType<BreederTile>> BREEDER =
            BLOCK_ENTITIES.register("breeder", () -> new BlockEntityType<>(
                    BreederTile::new,
                            ModBlocks.BREEDER.get()));

    public static final Supplier<BlockEntityType<ChickenStorageTile>> CHICKENSTORAGE =
            BLOCK_ENTITIES.register("chickenstorage", () -> new BlockEntityType<>(
                    ChickenStorageTile::new,
                            ModBlocks.CHICKENSTORAGE.get()));


    public static final Supplier<BlockEntityType<SoulExtractorTile>> SOUL_EXTRACTOR =
            BLOCK_ENTITIES.register("soul_extractor", () -> new BlockEntityType<>(
                    SoulExtractorTile::new,
                            ModBlocks.SOUL_EXTRACTOR.get()));

    public static final Supplier<BlockEntityType<RoostTile>> ROOST =
            BLOCK_ENTITIES.register("roost", () -> new BlockEntityType<>(
                    RoostTile::new,
                            ModBlocks.ROOST.get()));

    public static final Supplier<BlockEntityType<TrainerTile>> TRAINER =
            BLOCK_ENTITIES.register("trainer", () -> new BlockEntityType<>(
                    TrainerTile::new,
                            ModBlocks.TRAINER.get()));


    public static final Supplier<BlockEntityType<PipeBlockEntity>> PIPE =
            BLOCK_ENTITIES.register("pipe", () -> new BlockEntityType<>(
                            PipeBlockEntity::new,
                            ModBlocks.PIPE_TIER1.get(),
                            ModBlocks.PIPE_TIER2.get(),
                            ModBlocks.PIPE_TIER3.get(),
                            ModBlocks.PIPE_TIER4.get()

            ));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }



    public static void registerCapabilities(RegisterCapabilitiesEvent event) {


        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                ModBlockEntities.BREEDER.get(),
                (be, side) -> ((BreederTile) be).inventory
        );
        /*event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                CHICKENSTORAGE.get(),
                ChickenStorageTile::getItemHandlerCapability
        );*/

        /*event.registerBlock(
                Capabilities.Item.BLOCK,
                (level, pos, state, be, side) ->

                ROOST,
                BREEDER,
                TRAINER,
                SOUL_EXTRACTOR
    );*/

        /*event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                SOUL_EXTRACTOR.get(),
                SoulExtractorTile::getItemHandlerCapability
        );

        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                ROOST.get(),
                RoostTile::getItemHandlerCapability
        );*/

        /*event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                COLLECTOR.get(),
                CollectorTile::getItemHandlerCapability
        );*/

        /*event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                TRAINER.get(),
                TrainerTile::getItemHandlerCapability
        );*/

       /* event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                FEEDER.get(),
                FeederTile::getItemHandlerCapability
        );*/
    }
}