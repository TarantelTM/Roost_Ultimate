package net.tarantel.chickenroost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record SetTrainerLevelPayload(BlockPos pos, int level)
        implements CustomPacketPayload {

    public static final Type<SetTrainerLevelPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("chicken_roost", "set_trainer_level"));

    public static final StreamCodec<ByteBuf, SetTrainerLevelPayload> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, SetTrainerLevelPayload::pos,
                    ByteBufCodecs.VAR_INT, SetTrainerLevelPayload::level,
                    SetTrainerLevelPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
