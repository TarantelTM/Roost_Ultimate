package net.tarantel.chickenroost.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.tarantel.chickenroost.block.tile.Feeder_Tile;

import java.util.function.Supplier;

public class SetFeederRoostActivePayload {
    private final BlockPos feederPos;
    private final BlockPos roostPos;
    private final boolean active;

    public SetFeederRoostActivePayload(BlockPos feederPos, BlockPos roostPos, boolean active) {
        this.feederPos = feederPos;
        this.roostPos = roostPos;
        this.active = active;
    }

    public SetFeederRoostActivePayload(FriendlyByteBuf buf) {
        this.feederPos = buf.readBlockPos();
        this.roostPos = buf.readBlockPos();
        this.active = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(feederPos);
        buf.writeBlockPos(roostPos);
        buf.writeBoolean(active);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerLevel level = ctx.get().getSender().serverLevel();
            BlockEntity be = level.getBlockEntity(feederPos);
            if (be instanceof Feeder_Tile feeder) {
                if (active) feeder.addActiveRoost(roostPos);   // ✅ dein Setter
                else feeder.removeActiveRoost(roostPos);       // ✅ dein Setter
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
