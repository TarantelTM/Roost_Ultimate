package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.pipe.PipeBlockEntity;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ChickenRoostMod.MODID);

    public static final Supplier<BlockEntityType<CollectorTile>> COLLECTOR =
            BLOCK_ENTITIES.register("collector", () ->
                    BlockEntityType.Builder.of(CollectorTile::new,
                            ModBlocks.COLLECTOR.get()).build(null));

    public static final Supplier<BlockEntityType<FeederTile>> FEEDER =
            BLOCK_ENTITIES.register("feeder", () ->
                    BlockEntityType.Builder.of(FeederTile::new,
                            ModBlocks.FEEDER.get()).build(null));


    public static final Supplier<BlockEntityType<BreederTile>> BREEDER =
            BLOCK_ENTITIES.register("breeder", () ->
                    BlockEntityType.Builder.of(BreederTile::new,
                            ModBlocks.BREEDER.get()).build(null));

    public static final Supplier<BlockEntityType<ChickenStorageTile>> CHICKENSTORAGE =
            BLOCK_ENTITIES.register("chickenstorage", () ->
                    BlockEntityType.Builder.of(ChickenStorageTile::new,
                            ModBlocks.CHICKENSTORAGE.get()).build(null));


    public static final Supplier<BlockEntityType<SoulExtractorTile>> SOUL_EXTRACTOR =
            BLOCK_ENTITIES.register("soul_extractor", () ->
                    BlockEntityType.Builder.of(SoulExtractorTile::new,
                            ModBlocks.SOUL_EXTRACTOR.get()).build(null));

    public static final Supplier<BlockEntityType<RoostTile>> ROOST =
            BLOCK_ENTITIES.register("roost", () ->
                    BlockEntityType.Builder.of(RoostTile::new,
                            ModBlocks.ROOST.get()).build(null));

    public static final Supplier<BlockEntityType<TrainerTile>> TRAINER =
            BLOCK_ENTITIES.register("trainer", () ->
                    BlockEntityType.Builder.of(TrainerTile::new,
                            ModBlocks.TRAINER.get()).build(null));


    public static final Supplier<BlockEntityType<PipeBlockEntity>> PIPE =
            BLOCK_ENTITIES.register("pipe",
                    () -> BlockEntityType.Builder.of(
                            PipeBlockEntity::new,
                            ModBlocks.PIPE_TIER1.get(),
                            ModBlocks.PIPE_TIER2.get(),
                            ModBlocks.PIPE_TIER3.get(),
                            ModBlocks.PIPE_TIER4.get()
                    ).build(null)
            );


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }



    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                CHICKENSTORAGE.get(), ChickenStorageTile::getItemHandlerCapability);


        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                BREEDER.get(), BreederTile::getItemHandlerCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                SOUL_EXTRACTOR.get(), SoulExtractorTile::getItemHandlerCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ROOST.get(), RoostTile::getItemHandlerCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                COLLECTOR.get(), CollectorTile::getItemHandlerCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                TRAINER.get(), TrainerTile::getItemHandlerCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                FEEDER.get(), FeederTile::getItemHandlerCapability);



    }
}