package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ChickenRoostMod.MODID);


    public static final Supplier<BlockEntityType<Soul_Breeder_Tile>> SOUL_BREEDER =
            BLOCK_ENTITIES.register("soul_breeder", () ->
                    BlockEntityType.Builder.of(Soul_Breeder_Tile::new,
                            ModBlocks.SOUL_BREEDER.get()).build(null));

    public static final Supplier<BlockEntityType<Breeder_Tile>> BREEDER =
            BLOCK_ENTITIES.register("breeder", () ->
                    BlockEntityType.Builder.of(Breeder_Tile::new,
                            ModBlocks.BREEDER.get()).build(null));

    public static final Supplier<BlockEntityType<ChickenStorage_Tile>> CHICKENSTORAGE =
            BLOCK_ENTITIES.register("chickenstorage", () ->
                    BlockEntityType.Builder.of(ChickenStorage_Tile::new,
                            ModBlocks.CHICKENSTORAGE.get()).build(null));


    public static final Supplier<BlockEntityType<Soul_Extractor_Tile>> SOUL_EXTRACTOR =
            BLOCK_ENTITIES.register("soul_extractor", () ->
                    BlockEntityType.Builder.of(Soul_Extractor_Tile::new,
                            ModBlocks.SOUL_EXTRACTOR.get()).build(null));
    public static final Supplier<BlockEntityType<Roost_Tile>> ROOST =
            BLOCK_ENTITIES.register("roost", () ->
                    BlockEntityType.Builder.of(Roost_Tile::new,
                            ModBlocks.ROOST.get()).build(null));

    public static final Supplier<BlockEntityType<Trainer_Tile>> TRAINER =
            BLOCK_ENTITIES.register("trainer", () ->
                    BlockEntityType.Builder.of(Trainer_Tile::new,
                            ModBlocks.TRAINER.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }



    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                CHICKENSTORAGE.get(), ChickenStorage_Tile::getItemHandlerCapability);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                SOUL_BREEDER.get(), Soul_Breeder_Tile::getItemHandlerCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                BREEDER.get(), Breeder_Tile::getItemHandlerCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                SOUL_EXTRACTOR.get(), Soul_Extractor_Tile::getItemHandlerCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ROOST.get(), Roost_Tile::getItemHandlerCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                TRAINER.get(), Trainer_Tile::getItemHandlerCapability);

    }
}