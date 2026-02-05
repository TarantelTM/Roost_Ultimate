package net.tarantel.chickenroost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;


public record SetCollectorRangePayload(BlockPos collectorPos, int range) implements CustomPacketPayload {

    public static final Type<SetCollectorRangePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("chicken_roost", "collector_range"));

    public static final StreamCodec<ByteBuf, SetCollectorRangePayload> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, SetCollectorRangePayload::collectorPos,
                    ByteBufCodecs.VAR_INT, SetCollectorRangePayload::range,
                    SetCollectorRangePayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
