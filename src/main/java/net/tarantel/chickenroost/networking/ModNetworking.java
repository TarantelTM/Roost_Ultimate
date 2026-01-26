package net.tarantel.chickenroost.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.*;
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

        registrar.playToServer(SetFeederStackModePayload.TYPE, SetFeederStackModePayload.STREAM_CODEC,
                ModNetworking::handleSetFeederStackMode);

        registrar.playToServer(
                SetAutoOutputPayload.TYPE,
                SetAutoOutputPayload.STREAM_CODEC,
                ModNetworking::handleSetAutoOutput
        );

        registrar.playToServer(
                SetNamePayload.TYPE,
                SetNamePayload.STREAM_CODEC,
                ModNetworking::handleSetName
        );

        registrar.playToServer(
                SetFeederRoundRobinPayload.TYPE,
                SetFeederRoundRobinPayload.STREAM_CODEC,
                ModNetworking::handleSetFeederRoundRobin
        );

        registrar.playToClient(
                SyncAutoOutputPayload.TYPE,
                SyncAutoOutputPayload.STREAM_CODEC,
                ModNetworking::handleSyncAutoOutput
        );

        registrar.playToServer(
                SetTrainerLevelPayload.TYPE,
                SetTrainerLevelPayload.STREAM_CODEC,
                ModNetworking::handleSetTrainerLevel
        );

        registrar.playToClient(
                SyncTrainerLevelPayload.TYPE,
                SyncTrainerLevelPayload.STREAM_CODEC,
                ModNetworking::handleSyncTrainerLevel
        );
    }

    public static void handleSyncTrainerLevel(
            final SyncTrainerLevelPayload msg,
            final IPayloadContext ctx
    ) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player().level().isClientSide())) return;

            BlockEntity be = ctx.player().level().getBlockEntity(msg.pos());
            if (be instanceof TrainerTile trainer) {
                trainer.setAutoOutputLevelClient(msg.level());
            }
        });
    }


    public static void handleSetTrainerLevel(SetTrainerLevelPayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            var player = ctx.player();
            if (player == null) return;

            var level = player.level();
            var be = level.getBlockEntity(msg.pos());
            if (be instanceof TrainerTile trainer) {
                trainer.setAutoOutputLevel(msg.level());
            }
        });
    }

    private static void handleSyncAutoOutput(
            final SyncAutoOutputPayload msg,
            final IPayloadContext ctx
    ) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player().level().isClientSide())) return;

            BlockEntity be = ctx.player().level().getBlockEntity(msg.pos());
            if (be instanceof ICollectorTarget roost) {
                roost.setAutoOutputClient(msg.enabled());
            }
        });
    }


    private static void handleSetFeederStackMode(final SetFeederStackModePayload msg, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();
            ServerLevel level = player.serverLevel();
            BlockEntity be = level.getBlockEntity(msg.feederPos());
            if (be instanceof FeederTile feeder) {
                feeder.setStackSendModeById(msg.mode());
            }
        });
    }

    private static void handleSetFeederRoundRobin(final SetFeederRoundRobinPayload msg, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer sp)) return;
            if (!(sp.level() instanceof ServerLevel level)) return;

            BlockEntity be = level.getBlockEntity(msg.feederPos());
            if (!(be instanceof FeederTile feeder)) return;

            feeder.setRoundRobinEnabled(msg.enabled());
        });
    }


    private static void handleSetAutoOutput(final SetAutoOutputPayload msg, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();
            ServerLevel level = player.serverLevel();
            BlockEntity be = level.getBlockEntity(msg.pos());
            if (be instanceof ICollectorTarget roost) {
                roost.setAutoOutputFromGui(msg.enabled());

            }
        });
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

    private static void handleSetName(final SetNamePayload msg, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();

            ServerLevel level = player.serverLevel();
            BlockEntity be = level.getBlockEntity(msg.pos());
            if (be instanceof ICollectorTarget roost) {
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