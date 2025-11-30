package net.tarantel.chickenroost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SetRoostNamePayload(BlockPos pos, String name) implements CustomPacketPayload {

    public static final Type<SetRoostNamePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("chicken_roost", "set_roost_name"));

    public static final StreamCodec<ByteBuf, SetRoostNamePayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, SetRoostNamePayload::pos,
            ByteBufCodecs.STRING_UTF8, SetRoostNamePayload::name,
            SetRoostNamePayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}