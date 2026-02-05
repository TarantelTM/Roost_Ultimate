package net.tarantel.chickenroost.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SetTrainerLevelPayload(BlockPos pos, int level) implements CustomPacketPayload {

    public static final Type<SetTrainerLevelPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("chicken_roost", "set_trainer_level"));

    public static final StreamCodec<FriendlyByteBuf, SetTrainerLevelPayload> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, SetTrainerLevelPayload::pos,
                    ByteBufCodecs.INT, SetTrainerLevelPayload::level,
                    SetTrainerLevelPayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
