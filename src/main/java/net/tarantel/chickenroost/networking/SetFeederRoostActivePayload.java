package net.tarantel.chickenroost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SetFeederRoostActivePayload(BlockPos feederPos, BlockPos roostPos, boolean active) implements CustomPacketPayload {
   public static final Type<SetFeederRoostActivePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("chicken_roost", "feeder_roost_active"));
   public static final StreamCodec<ByteBuf, SetFeederRoostActivePayload> STREAM_CODEC = StreamCodec.composite(
      BlockPos.STREAM_CODEC,
      SetFeederRoostActivePayload::feederPos,
      BlockPos.STREAM_CODEC,
      SetFeederRoostActivePayload::roostPos,
      ByteBufCodecs.BOOL,
      SetFeederRoostActivePayload::active,
      SetFeederRoostActivePayload::new
   );

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
