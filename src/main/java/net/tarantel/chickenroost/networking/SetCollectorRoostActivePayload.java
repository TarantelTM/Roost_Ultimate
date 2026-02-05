package net.tarantel.chickenroost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;


public record SetCollectorRoostActivePayload(BlockPos collectorPos, BlockPos roostPos, boolean active)
        implements CustomPacketPayload {

    public static final Type<SetCollectorRoostActivePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("chicken_roost", "collector_roost_active"));

    public static final StreamCodec<ByteBuf, SetCollectorRoostActivePayload> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, SetCollectorRoostActivePayload::collectorPos,
                    BlockPos.STREAM_CODEC, SetCollectorRoostActivePayload::roostPos,
                    ByteBufCodecs.BOOL, SetCollectorRoostActivePayload::active,
                    SetCollectorRoostActivePayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
