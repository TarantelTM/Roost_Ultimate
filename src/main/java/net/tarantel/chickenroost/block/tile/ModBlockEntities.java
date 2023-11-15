package net.tarantel.chickenroost.block.tile;

import net.neoforged.bus.api.IEventBus;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ChickenRoostMod.MODID);


    public static final RegistryObject<BlockEntityType<Soul_Breeder_Tile>> SOUL_BREEDER =
            BLOCK_ENTITIES.register("soul_breeder", () ->
                    BlockEntityType.Builder.of(Soul_Breeder_Tile::new,
                            ModBlocks.SOUL_BREEDER.get()).build(null));

    public static final RegistryObject<BlockEntityType<Breeder_Tile>> BREEDER =
            BLOCK_ENTITIES.register("breeder", () ->
                    BlockEntityType.Builder.of(Breeder_Tile::new,
                            ModBlocks.BREEDER.get()).build(null));


    public static final RegistryObject<BlockEntityType<Soul_Extractor_Tile>> SOUL_EXTRACTOR =
            BLOCK_ENTITIES.register("soul_extractor", () ->
                    BlockEntityType.Builder.of(Soul_Extractor_Tile::new,
                            ModBlocks.SOUL_EXTRACTOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<Roost_Tile>> ROOST =
            BLOCK_ENTITIES.register("roost", () ->
                    BlockEntityType.Builder.of(Roost_Tile::new,
                            ModBlocks.ROOST.get()).build(null));

    public static final RegistryObject<BlockEntityType<Trainer_Tile>> TRAINER =
            BLOCK_ENTITIES.register("trainer", () ->
                    BlockEntityType.Builder.of(Trainer_Tile::new,
                            ModBlocks.TRAINER.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}