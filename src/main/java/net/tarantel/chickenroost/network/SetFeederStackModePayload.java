package net.tarantel.chickenroost.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.tarantel.chickenroost.block.tile.Feeder_Tile;

import java.util.function.Supplier;

public class SetFeederStackModePayload {
    private final BlockPos feederPos;
    private final int mode; // 0=SINGLE, 1=HALF, 2=FULL

    public SetFeederStackModePayload(BlockPos feederPos, int mode) {
        this.feederPos = feederPos;
        this.mode = mode;
    }

    public SetFeederStackModePayload(FriendlyByteBuf buf) {
        this.feederPos = buf.readBlockPos();
        this.mode = buf.readVarInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(feederPos);
        buf.writeVarInt(mode);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerLevel level = ctx.get().getSender().serverLevel();
            BlockEntity be = level.getBlockEntity(feederPos);
            if (be instanceof Feeder_Tile feeder) {
                feeder.setStackSendModeById(mode); // ✅ dein Setter
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
