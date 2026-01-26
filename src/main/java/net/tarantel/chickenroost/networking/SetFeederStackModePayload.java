package net.tarantel.chickenroost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SetFeederStackModePayload(BlockPos feederPos, int mode) implements CustomPacketPayload {


    public static final Type<SetFeederStackModePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("chicken_roost", "feeder_stack_mode"));

    public static final StreamCodec<ByteBuf, SetFeederStackModePayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, SetFeederStackModePayload::feederPos,
            ByteBufCodecs.VAR_INT, SetFeederStackModePayload::mode,
            SetFeederStackModePayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
