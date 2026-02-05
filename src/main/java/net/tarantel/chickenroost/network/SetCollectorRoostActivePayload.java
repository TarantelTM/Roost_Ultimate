package net.tarantel.chickenroost.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.tarantel.chickenroost.block.tile.Collector_Tile;

import java.util.function.Supplier;

public class SetCollectorRoostActivePayload {
    private final BlockPos collectorPos;
    private final BlockPos roostPos;
    private final boolean active;

    public SetCollectorRoostActivePayload(BlockPos collectorPos, BlockPos roostPos, boolean active) {
        this.collectorPos = collectorPos;
        this.roostPos = roostPos;
        this.active = active;
    }

    public SetCollectorRoostActivePayload(FriendlyByteBuf buf) {
        this.collectorPos = buf.readBlockPos();
        this.roostPos = buf.readBlockPos();
        this.active = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(collectorPos);
        buf.writeBlockPos(roostPos);
        buf.writeBoolean(active);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerLevel level = ctx.get().getSender().serverLevel();
            BlockEntity be = level.getBlockEntity(collectorPos);
            if (be instanceof Collector_Tile collector) {
                collector.setRoostActive(roostPos, active); // ✅ dein Setter
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
