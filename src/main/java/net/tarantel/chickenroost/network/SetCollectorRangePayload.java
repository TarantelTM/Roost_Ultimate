package net.tarantel.chickenroost.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.tarantel.chickenroost.block.tile.Collector_Tile;

import java.util.function.Supplier;

public class SetCollectorRangePayload {
    private final BlockPos collectorPos;
    private final int range;

    public SetCollectorRangePayload(BlockPos collectorPos, int range) {
        this.collectorPos = collectorPos;
        this.range = range;
    }

    public SetCollectorRangePayload(FriendlyByteBuf buf) {
        this.collectorPos = buf.readBlockPos();
        this.range = buf.readVarInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(collectorPos);
        buf.writeVarInt(range);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerLevel level = ctx.get().getSender().serverLevel();
            BlockEntity be = level.getBlockEntity(collectorPos);
            if (be instanceof Collector_Tile collector) {
                collector.setCollectRange(range); // ✅ dein Setter
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
