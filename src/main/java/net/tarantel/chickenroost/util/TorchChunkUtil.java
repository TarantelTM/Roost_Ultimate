package net.tarantel.chickenroost.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.spawn.IChickenTorchChunkData;
import net.tarantel.chickenroost.spawn.ModCapabilities;

public final class TorchChunkUtil {

    private static final int RADIUS_CHUNKS = 4; // 64 Blöcke

    public static boolean isTorchNearby(ServerLevel level, BlockPos pos) {
        int cx = pos.getX() >> 4;
        int cz = pos.getZ() >> 4;

        for (int dx = -RADIUS_CHUNKS; dx <= RADIUS_CHUNKS; dx++) {
            for (int dz = -RADIUS_CHUNKS; dz <= RADIUS_CHUNKS; dz++) {

                LevelChunk chunk = level.getChunkSource().getChunkNow(cx + dx, cz + dz);
                if (chunk == null) continue;

                boolean has = chunk.getCapability(ModCapabilities.TORCH_DATA)
                        .map(data -> {
                            if (!data.scanned()) {
                                boolean found = scanChunkForTorch(chunk, level);
                                data.setHasTorch(found);
                                data.setScanned(true);
                            }
                            return data.hasTorch();
                        })
                        .orElse(false);

                if (has) return true;
            }
        }
        return false;
    }


    private static boolean scanChunkForTorch(LevelChunk chunk, ServerLevel level) {
        int minY = level.getMinBuildHeight();
        int maxY = chunk.getHighestSectionPosition() + 15;

        int startX = chunk.getPos().getMinBlockX();
        int startZ = chunk.getPos().getMinBlockZ();

        BlockPos.MutableBlockPos mp = new BlockPos.MutableBlockPos();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = minY; y <= maxY; y++) {
                    mp.set(startX + x, y, startZ + z);
                    BlockState state = chunk.getBlockState(mp);
                    if (state.is(ModBlocks.TORCH.get()) || state.is(ModBlocks.WALL_TORCH.get())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private TorchChunkUtil() {}
}