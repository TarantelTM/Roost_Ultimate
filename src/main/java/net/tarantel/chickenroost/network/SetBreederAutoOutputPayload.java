package net.tarantel.chickenroost.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.tarantel.chickenroost.block.tile.Breeder_Tile;

import java.util.function.Supplier;

public class SetBreederAutoOutputPayload {

    private final BlockPos pos;
    private final boolean enabled;

    /* ---------- CLIENT → SERVER ---------- */
    public SetBreederAutoOutputPayload(BlockPos pos, boolean enabled) {
        this.pos = pos;
        this.enabled = enabled;
    }

    /* ---------- DECODE ---------- */
    public SetBreederAutoOutputPayload(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.enabled = buf.readBoolean();
    }

    /* ---------- ENCODE ---------- */
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeBoolean(enabled);
    }

    /* ---------- HANDLE ---------- */
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerLevel level = ctx.get().getSender().serverLevel();
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof Breeder_Tile breeder) {
                breeder.setAutoOutputEnabled(enabled);
                breeder.setChanged();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
