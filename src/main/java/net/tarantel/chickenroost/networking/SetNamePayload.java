package net.tarantel.chickenroost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record SetNamePayload(BlockPos pos, String name) implements CustomPacketPayload {

    public static final Type<SetNamePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("chicken_roost", "set_roost_name"));

    public static final StreamCodec<ByteBuf, SetNamePayload> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, SetNamePayload::pos,
                    ByteBufCodecs.stringUtf8(64), SetNamePayload::name,
                    SetNamePayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
