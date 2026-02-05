package net.tarantel.chickenroost.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.entity.BaseChickenEntity;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.spawn.IChickenTorchChunkData;
import net.tarantel.chickenroost.spawn.ModCapabilities;

import java.util.Set;

@Mod.EventBusSubscriber
public class SpawnLimiter {

    // 🔹 Erlaubte Spawn-Blöcke
    private static final Set<Block> ALLOWED_BLOCKS = Set.of(
            Blocks.GRASS_BLOCK,
            Blocks.DIRT,
            Blocks.COARSE_DIRT,
            Blocks.PODZOL,
            Blocks.MOSS_BLOCK,
            Blocks.SAND,
            Blocks.ANDESITE,
            Blocks.GRANITE,
            Blocks.NETHER_GOLD_ORE,
            Blocks.NETHERRACK,
            Blocks.NETHER_QUARTZ_ORE,
            Blocks.NETHER_WART,
            Blocks.END_STONE,
            Blocks.ICE,
            Blocks.SNOW,
            Blocks.SNOW_BLOCK,
            Blocks.POWDER_SNOW,
            Blocks.STONE,
            Blocks.ALLIUM,
            Blocks.AMETHYST_BLOCK
    );


    @SubscribeEvent
    public static void onFinalizeSpawn(MobSpawnEvent.FinalizeSpawn event) {
        if (!(event.getEntity() instanceof BaseChickenEntity mob)) return;
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        MobSpawnType type = event.getSpawnType();
        if (type != MobSpawnType.NATURAL && type != MobSpawnType.CHUNK_GENERATION) {
            return;
        }

        // 🔹 Block unter dem Mob prüfen
        BlockPos pos = mob.blockPosition();
        BlockPos belowPos = pos.below();

        BlockState belowState = level.getBlockState(belowPos);
        BlockState currentState = level.getBlockState(pos);

        // ❌ Kein Spawn in der Luft / auf nicht-soliden Blöcken
        if (!belowState.isSolid()) {
            event.setSpawnCancelled(true);
            return;
        }

        // ❌ Kein Spawn in Flüssigkeiten
        if (currentState.getFluidState().isSource() || belowState.getFluidState().isSource()) {
            event.setSpawnCancelled(true);
            return;
        }

        // ❌ Spawn nur auf erlaubten Blöcken
        if (!ALLOWED_BLOCKS.contains(belowState.getBlock())) {
            event.setSpawnCancelled(true);
            return;
        }

        if (TorchChunkUtil.isTorchNearby(level, pos)) {
            event.setSpawnCancelled(true);
            return;
        }

        int max = 15;

        int nearby = level.getEntitiesOfClass(
                BaseChickenEntity.class,
                mob.getBoundingBox().inflate(64, 256, 64)
        ).size();

        if (nearby >= max) {
            event.setSpawnCancelled(true);
        }
    }
}