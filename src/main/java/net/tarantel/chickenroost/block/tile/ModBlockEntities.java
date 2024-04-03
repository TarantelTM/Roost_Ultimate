package net.tarantel.chickenroost.block.tile;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILES  =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ChickenRoostMod.MODID);


    public static final RegistryObject<BlockEntityType<Soul_Breeder_Tile>> SOUL_BREEDER =
            TILES .register("soul_breeder", () ->
                    BlockEntityType.Builder.of(Soul_Breeder_Tile::new,
                            ModBlocks.SOUL_BREEDER.get()).build(null));

    public static final RegistryObject<BlockEntityType<Breeder_Tile>> BREEDER =
            TILES .register("breeder", () ->
                    BlockEntityType.Builder.of(Breeder_Tile::new,
                            ModBlocks.BREEDER.get()).build(null));


    public static final RegistryObject<BlockEntityType<Soul_Extractor_Tile>> SOUL_EXTRACTOR =
            TILES .register("soul_extractor", () ->
                    BlockEntityType.Builder.of(Soul_Extractor_Tile::new,
                            ModBlocks.SOUL_EXTRACTOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<Roost_Tile>> ROOST =
            TILES .register("roost", () ->
                    BlockEntityType.Builder.of(Roost_Tile::new,
                            ModBlocks.ROOST.get()).build(null));

    /*public static final RegistryObject<BlockEntityType<Trainer_Tile>> TRAINER =
            TILES .register("trainer", () ->
                    BlockEntityType.Builder.of(Trainer_Tile::new,
                            ModBlocks.TRAINER.get()).build(null));
*/


    public static final RegistryObject<BlockEntityType<Trainer_Tile>> TRAINER =
            TILES.register("trainer", () ->
                    BlockEntityType.Builder.of(Trainer_Tile::new,
                            ModBlocks.TRAINER.get()).build(null));


    public static void register(IEventBus eventBus) {
        TILES .register(eventBus);
    }
}