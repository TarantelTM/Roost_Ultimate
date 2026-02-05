package net.tarantel.chickenroost.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.tarantel.chickenroost.block.tile.Feeder_Tile;

import java.util.function.Supplier;

public class SetFeederRangePayload {
    private final BlockPos feederPos;
    private final int range;

    public SetFeederRangePayload(BlockPos feederPos, int range) {
        this.feederPos = feederPos;
        this.range = range;
    }

    public SetFeederRangePayload(FriendlyByteBuf buf) {
        this.feederPos = buf.readBlockPos();
        this.range = buf.readVarInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(feederPos);
        buf.writeVarInt(range);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerLevel level = ctx.get().getSender().serverLevel();
            BlockEntity be = level.getBlockEntity(feederPos);
            if (be instanceof Feeder_Tile feeder) {
                feeder.setFeedRange(range); // ✅ dein Setter
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
