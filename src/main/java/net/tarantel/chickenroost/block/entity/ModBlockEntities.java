package net.tarantel.chickenroost.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<breeder_entity> BREEDER;
    public static BlockEntityType<trainer_entity> TRAINER;
    public static BlockEntityType<roost_entity> ROOST;

    public static void registerBlockEntities() {
        BREEDER = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ChickenRoostMod.MODID, "breeder"),
                FabricBlockEntityTypeBuilder.create(breeder_entity::new,
                        ModBlocks.BREEDER).build(null));

        TRAINER = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ChickenRoostMod.MODID, "trainer"),
                FabricBlockEntityTypeBuilder.create(trainer_entity::new,
                        ModBlocks.TRAINER).build(null));

        ROOST = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ChickenRoostMod.MODID, "roost__v1"),
                FabricBlockEntityTypeBuilder.create(roost_entity::new,
                        ModBlocks.ROOST).build(null));
    }
}
