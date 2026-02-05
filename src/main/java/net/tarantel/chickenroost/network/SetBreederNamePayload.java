package net.tarantel.chickenroost.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.tarantel.chickenroost.block.tile.Breeder_Tile;


import java.util.function.Supplier;

public class SetBreederNamePayload {

    private final BlockPos pos;
    private final String name;

    public SetBreederNamePayload(BlockPos pos, String name) {
        this.pos = pos;
        this.name = name;
    }

    public SetBreederNamePayload(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.name = buf.readUtf(64);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUtf(name);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerLevel level = ctx.get().getSender().serverLevel();
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof Breeder_Tile roost) {
                roost.setCustomName(name);
                roost.setChanged();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
