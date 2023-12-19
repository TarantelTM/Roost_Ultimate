package net.tarantel.chickenroost.block.tile;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;


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
}