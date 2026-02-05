package net.tarantel.chickenroost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.tarantel.chickenroost.block.tile.FeederTile;
import net.tarantel.chickenroost.screen.FeederScreen;


import java.util.ArrayList;
import java.util.List;

public record SyncFeederActiveTargetsPayload(
        BlockPos feederPos,
        List<BlockPos> activeTargets
) implements CustomPacketPayload {


    public static final StreamCodec<ByteBuf, List<BlockPos>> BLOCK_POS_LIST_CODEC =
            StreamCodec.of(
                    // ENCODE
                    (buf, list) -> {
                        buf.writeInt(list.size());
                        for (BlockPos pos : list) {
                            BlockPos.STREAM_CODEC.encode(buf, pos);
                        }
                    },
                    // DECODE
                    buf -> {
                        int size = buf.readInt();
                        List<BlockPos> list = new ArrayList<>(size);
                        for (int i = 0; i < size; i++) {
                            list.add(BlockPos.STREAM_CODEC.decode(buf));
                        }
                        return list;
                    }
            );

    public static final Type<SyncFeederActiveTargetsPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("chicken_roost", "sync_feeder_targets"));

    public static final StreamCodec<ByteBuf, SyncFeederActiveTargetsPayload> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,
                    SyncFeederActiveTargetsPayload::feederPos,
                    BLOCK_POS_LIST_CODEC,
                    SyncFeederActiveTargetsPayload::activeTargets,
                    SyncFeederActiveTargetsPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // ===== CLIENT HANDLER =====
    public static void handle(SyncFeederActiveTargetsPayload payload, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null) return;

            BlockEntity be = mc.level.getBlockEntity(payload.feederPos());
            if (be instanceof FeederTile feeder) {

                // 🔄 Client-State vollständig ersetzen
                feeder.setClientActiveRoosts(payload.activeTargets());

                // 🔄 Screen neu zeichnen
                if (mc.screen instanceof FeederScreen screen) {
                    screen.refreshButtons();
                }
            }
        });
    }

}
