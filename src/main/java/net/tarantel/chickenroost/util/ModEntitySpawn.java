package net.tarantel.chickenroost.util;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.dimension.DimensionType;
import net.tarantel.chickenroost.entity.ChickenBaseEntity;
import net.tarantel.chickenroost.entity.ModEntities;

public class ModEntitySpawn {
    public static boolean isValidNetherRack(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return !world.getBlockState(pos.down()).isOf(Blocks.NETHER_WART_BLOCK);
    }
    public static boolean canSpawnRack(EntityType<ChickenBaseEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockPos.Mutable mutable = pos.mutableCopy();

        return world.getBlockState(mutable).isAir();
    }
    public static boolean isSpawnDark(ServerWorldAccess world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.SKY, pos) > random.nextInt(15)) {
            return true;
        } else {
            DimensionType dimensionType = world.getDimension();
            int i = dimensionType.monsterSpawnBlockLightLimit();
            if (i <= 15 && world.getLightLevel(LightType.BLOCK, pos) >= 15) {
                return true;
            } else {
                int j = world.toServerWorld().isThundering() ? world.getLightLevel(pos, 0) : world.getLightLevel(pos);
                return j <= dimensionType.monsterSpawnLightTest().get(random);

            }
        }
    }


    public static boolean canSpawnIgnoreLightLevel(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random){
        return true;
    }

    public static boolean canMobSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockPos blockPos = pos.down();
        return spawnReason == SpawnReason.SPAWNER || world.getBlockState(blockPos).allowsSpawning(world, blockPos, type);
    }
    public static boolean canSpawnInDark(EntityType<? extends AnimalEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return isSpawnDark(world, pos, random) && canMobSpawn(type, world, spawnReason, pos, random);
    }
    public static void addEntitySpawn() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.FLOWER_FOREST), SpawnGroup.CREATURE,
                ModEntities.C_OAKWOOD, SpawnConfig.c_weight_oakwood.get(), SpawnConfig.c_min_oakwood.get(), SpawnConfig.c_max_oakwood.get());

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.STONY_PEAKS, BiomeKeys.STONY_SHORE, BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.PLAINS), SpawnGroup.CREATURE,
                ModEntities.C_STONE, SpawnConfig.c_weight_stone.get(), SpawnConfig.c_min_stone.get(), SpawnConfig.c_max_stone.get());

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA), SpawnGroup.CREATURE,
                ModEntities.C_SAND, SpawnConfig.c_weight_sand.get(), SpawnConfig.c_min_sand.get(), SpawnConfig.c_max_sand.get());

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.STONY_PEAKS, BiomeKeys.STONY_SHORE, BiomeKeys.DRIPSTONE_CAVES), SpawnGroup.CREATURE,
                ModEntities.C_COBBLE, SpawnConfig.c_weight_cobble.get(), SpawnConfig.c_min_cobble.get(), SpawnConfig.c_max_cobble.get());

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.CRIMSON_FOREST, BiomeKeys.DEEP_DARK, BiomeKeys.WARPED_FOREST), SpawnGroup.MONSTER,
                ModEntities.C_BONE, SpawnConfig.c_weight_bone.get(), SpawnConfig.c_min_bone.get(), SpawnConfig.c_max_bone.get());

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.END_BARRENS, BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS, BiomeKeys.SMALL_END_ISLANDS, BiomeKeys.THE_END), SpawnGroup.MONSTER,
                ModEntities.C_ENDSTONE, SpawnConfig.c_weight_endstone.get(), SpawnConfig.c_max_endstone.get(), SpawnConfig.c_max_endstone.get());

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES, BiomeKeys.CRIMSON_FOREST, BiomeKeys.WARPED_FOREST), SpawnGroup.MONSTER,
                ModEntities.C_NETHERRACK, SpawnConfig.c_weight_netherrack.get(), SpawnConfig.c_max_netherrack.get(), SpawnConfig.c_max_netherrack.get());




        SpawnRestriction.register(ModEntities.C_BONE, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawn::isValidNetherRack);

        SpawnRestriction.register(ModEntities.C_STONE, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

        SpawnRestriction.register(ModEntities.C_SAND, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

        SpawnRestriction.register(ModEntities.C_COBBLE, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

        SpawnRestriction.register(ModEntities.C_OAKWOOD, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

        SpawnRestriction.register(ModEntities.C_NETHERRACK, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawn::isValidNetherRack);

        SpawnRestriction.register(ModEntities.C_ENDSTONE, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawn::canSpawnIgnoreLightLevel);
    }
}
