package net.tarantel.chickenroost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;


public record SetFeederRoostSeedPayload(
        BlockPos feederPos,
        BlockPos roostPos,
        Optional<Identifier> itemId
) implements CustomPacketPayload {

    public static final Type<SetFeederRoostSeedPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("chicken_roost", "feeder_roost_seed"));

    public static final StreamCodec<ByteBuf, SetFeederRoostSeedPayload> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, SetFeederRoostSeedPayload::feederPos,
                    BlockPos.STREAM_CODEC, SetFeederRoostSeedPayload::roostPos,
                    Identifier.STREAM_CODEC.apply(ByteBufCodecs::optional),
                    SetFeederRoostSeedPayload::itemId,
                    SetFeederRoostSeedPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
