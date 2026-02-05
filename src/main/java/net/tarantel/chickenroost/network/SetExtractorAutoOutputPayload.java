package net.tarantel.chickenroost.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.tarantel.chickenroost.block.tile.Soul_Extractor_Tile;

import java.util.function.Supplier;

public class SetExtractorAutoOutputPayload {

    private final BlockPos pos;
    private final boolean enabled;

    public SetExtractorAutoOutputPayload(BlockPos pos, boolean enabled) {
        this.pos = pos;
        this.enabled = enabled;
    }

    public SetExtractorAutoOutputPayload(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.enabled = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeBoolean(enabled);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerLevel level = ctx.get().getSender().serverLevel();
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof Soul_Extractor_Tile tile) {
                tile.setAutoOutputEnabled(enabled);
                tile.setChanged();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
