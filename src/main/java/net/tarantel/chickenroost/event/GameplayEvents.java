package net.tarantel.chickenroost.event;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.spawn.IChickenTorchChunkData;
import net.tarantel.chickenroost.spawn.ModCapabilities;
import net.tarantel.chickenroost.spawn.TorchChunkCapabilityProvider;
import net.tarantel.chickenroost.util.TorchChunkUtil;

@Mod.EventBusSubscriber(
        modid = ChickenRoostMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class GameplayEvents {

    @SubscribeEvent
    public static void attachChunkCaps(AttachCapabilitiesEvent<LevelChunk> event) {
        event.addCapability(
                new ResourceLocation("chicken_roost", "torch_data"),
                new TorchChunkCapabilityProvider()
        );
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!(event.getChunk() instanceof LevelChunk chunk)) return;

        chunk.getCapability(ModCapabilities.TORCH_DATA).ifPresent(data -> {
            data.setScanned(false);     // 🔑 wird später lazy gescannt
            // data.setHasTorch(false); // optional; lieber nicht überschreiben
        });
    }
    private static boolean isChickenTorch(BlockState state) {
        return state.is(ModBlocks.TORCH.get())
                || state.is(ModBlocks.WALL_TORCH.get());
    }
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        if (!isChickenTorch(event.getPlacedBlock())) return;

        LevelChunk chunk = level.getChunkAt(event.getPos());

        chunk.getCapability(ModCapabilities.TORCH_DATA)
                .ifPresent(data -> data.setHasTorch(true));
    }


    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        if (!isChickenTorch(event.getState())) return;

        LevelChunk chunk = level.getChunkAt(event.getPos());

        chunk.getCapability(ModCapabilities.TORCH_DATA)
                .ifPresent(data -> {
                    boolean found = scanChunkForTorch(chunk, level);
                    data.setHasTorch(found);
                });
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



}