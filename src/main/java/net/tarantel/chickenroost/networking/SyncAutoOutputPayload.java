package net.tarantel.chickenroost.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record SyncAutoOutputPayload(BlockPos pos, boolean enabled) implements CustomPacketPayload {
   public static final Type<SyncAutoOutputPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("chicken_roost", "sync_roost_auto_output"));
   public static final StreamCodec<FriendlyByteBuf, SyncAutoOutputPayload> STREAM_CODEC = StreamCodec.composite(
      BlockPos.STREAM_CODEC, SyncAutoOutputPayload::pos, ByteBufCodecs.BOOL, SyncAutoOutputPayload::enabled, SyncAutoOutputPayload::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
