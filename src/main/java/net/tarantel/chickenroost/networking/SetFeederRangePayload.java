package net.tarantel.chickenroost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SetFeederRangePayload(BlockPos feederPos, int range) implements CustomPacketPayload {
   public static final Type<SetFeederRangePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("chicken_roost", "feeder_range"));
   public static final StreamCodec<ByteBuf, SetFeederRangePayload> STREAM_CODEC = StreamCodec.composite(
      BlockPos.STREAM_CODEC, SetFeederRangePayload::feederPos, ByteBufCodecs.VAR_INT, SetFeederRangePayload::range, SetFeederRangePayload::new
   );

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
