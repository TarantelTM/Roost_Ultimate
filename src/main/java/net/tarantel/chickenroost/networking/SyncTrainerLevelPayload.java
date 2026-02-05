package net.tarantel.chickenroost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record SyncTrainerLevelPayload(BlockPos pos, int level)
        implements CustomPacketPayload {

    public static final Type<SyncTrainerLevelPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("chicken_roost", "sync_trainer_level"));

    public static final StreamCodec<ByteBuf, SyncTrainerLevelPayload> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, SyncTrainerLevelPayload::pos,
                    ByteBufCodecs.VAR_INT, SyncTrainerLevelPayload::level,
                    SyncTrainerLevelPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
