package net.tarantel.chickenroost.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SetAutoOutputPayload(BlockPos pos, boolean enabled) implements CustomPacketPayload {

    public static final Type<SetAutoOutputPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("chicken_roost", "set_roost_auto_output"));

    public static final StreamCodec<FriendlyByteBuf, SetAutoOutputPayload> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, SetAutoOutputPayload::pos,
                    ByteBufCodecs.BOOL, SetAutoOutputPayload::enabled,
                    SetAutoOutputPayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
