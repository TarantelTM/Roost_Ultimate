package net.tarantel.chickenroost.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.tarantel.chickenroost.block.tile.Feeder_Tile;

import java.util.function.Supplier;

public class SetFeederRoostSeedPayload {
    private final BlockPos feederPos;
    private final BlockPos roostPos;
    private final String itemId; // "minecraft:wheat_seeds" oder "" zum Löschen

    public SetFeederRoostSeedPayload(BlockPos feederPos, BlockPos roostPos, String itemId) {
        this.feederPos = feederPos;
        this.roostPos = roostPos;
        this.itemId = itemId == null ? "" : itemId;
    }

    public SetFeederRoostSeedPayload(FriendlyByteBuf buf) {
        this.feederPos = buf.readBlockPos();
        this.roostPos = buf.readBlockPos();
        this.itemId = buf.readUtf(256);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(feederPos);
        buf.writeBlockPos(roostPos);
        buf.writeUtf(itemId, 256);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerLevel level = ctx.get().getSender().serverLevel();
            BlockEntity be = level.getBlockEntity(feederPos);
            if (!(be instanceof Feeder_Tile feeder)) return;

            Item item = null;
            if (!itemId.isEmpty()) {
                ResourceLocation rl = ResourceLocation.tryParse(itemId);
                if (rl != null) {
                    item = BuiltInRegistries.ITEM.get(rl);
                    // Falls ungültig: BuiltInRegistries.ITEM.get(rl) kann AIR liefern – optional prüfen:
                    if (item == net.minecraft.world.item.Items.AIR) item = null;
                }
            }

            feeder.setPreferredSeed(roostPos, item); // ✅ dein Setter
        });
        ctx.get().setPacketHandled(true);
    }
}
