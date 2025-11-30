package net.tarantel.chickenroost.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.tarantel.chickenroost.block.tile.CollectorTile;
import net.tarantel.chickenroost.block.tile.FeederTile;
import net.tarantel.chickenroost.block.tile.RoostTile;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;

public final class ModNetworking {
    private ModNetworking() {}

    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                SetCollectorRoostActivePayload.TYPE,
                SetCollectorRoostActivePayload.STREAM_CODEC,
                ModNetworking::handleSetCollectorRoostActive
        );
        registrar.playToServer(
                SetCollectorRangePayload.TYPE,
                SetCollectorRangePayload.STREAM_CODEC,
                ModNetworking::handleSetCollectorRange
        );

        registrar.playToServer(SetFeederRangePayload.TYPE, SetFeederRangePayload.STREAM_CODEC,
                ModNetworking::handleSetFeederRange);

        registrar.playToServer(SetFeederRoostActivePayload.TYPE, SetFeederRoostActivePayload.STREAM_CODEC,
                ModNetworking::handleSetFeederRoostActive);


        registrar.playToServer(SetFeederRoostSeedPayload.TYPE, SetFeederRoostSeedPayload.STREAM_CODEC,
                ModNetworking::handleSetFeederRoostSeed);


        registrar.playToServer(
                SetRoostNamePayload.TYPE,
                SetRoostNamePayload.STREAM_CODEC,
                ModNetworking::handleSetRoostName
        );
    }

    private static void handleSetCollectorRoostActive(final SetCollectorRoostActivePayload msg, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();
            ServerLevel level = player.serverLevel();
            BlockEntity be = level.getBlockEntity(msg.collectorPos());
            if (be instanceof CollectorTile ct) {
                ct.setRoostActive(msg.roostPos(), msg.active());
            }
        });
    }

    private static void handleSetCollectorRange(final SetCollectorRangePayload msg, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();
            ServerLevel level = player.serverLevel();
            BlockEntity be = level.getBlockEntity(msg.collectorPos());
            if (be instanceof CollectorTile ct) {
                ct.setCollectRange(msg.range());
            }
        });
    }

    private static void handleSetFeederRange(final SetFeederRangePayload msg, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();
            ServerLevel level = player.serverLevel();
            BlockEntity be = level.getBlockEntity(msg.feederPos());
            if (be instanceof FeederTile feeder) {
                feeder.setFeedRange(msg.range());
            }
        });
    }

    private static void handleSetFeederRoostActive(final SetFeederRoostActivePayload msg, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();
            ServerLevel level = player.serverLevel();
            BlockEntity be = level.getBlockEntity(msg.feederPos());
            if (be instanceof FeederTile feeder) {
                if (msg.active()) feeder.addActiveRoost(msg.roostPos());
                else feeder.removeActiveRoost(msg.roostPos());
            }
        });
    }

    private static void handleSetRoostName(final SetRoostNamePayload msg, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();

            ServerLevel level = player.serverLevel();
            BlockEntity be = level.getBlockEntity(msg.pos());
            if (be instanceof RoostTile roost) {
                roost.setCustomName(msg.name());
            }
        });
    }



    private static void handleSetFeederRoostSeed(final SetFeederRoostSeedPayload msg, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();

            ServerLevel level = player.serverLevel();
            BlockEntity be = level.getBlockEntity(msg.feederPos());
            if (!(be instanceof FeederTile feeder)) return;


            if (msg.itemId() == null || msg.itemId().isEmpty()) {
                feeder.setPreferredSeed(msg.roostPos(), null);
                return;
            }

            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(msg.itemId()));
            if (!isAllowedGhostItemServer(item)) {

                feeder.setPreferredSeed(msg.roostPos(), null);
                return;
            }

            feeder.setPreferredSeed(msg.roostPos(), item);
        });
    }
    private static boolean isAllowedGhostItemServer(Item item) {
        return item instanceof ChickenSeedBase;
    }
}