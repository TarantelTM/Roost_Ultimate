package net.tarantel.chickenroost.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.*;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;

import java.util.List;

public final class ModNetworking {

    private ModNetworking() {}

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(SetCollectorRoostActivePayload.TYPE, SetCollectorRoostActivePayload.STREAM_CODEC, ModNetworking::handleSetCollectorRoostActive);
        registrar.playToServer(SetCollectorRangePayload.TYPE, SetCollectorRangePayload.STREAM_CODEC, ModNetworking::handleSetCollectorRange);
        registrar.playToServer(SetFeederRangePayload.TYPE, SetFeederRangePayload.STREAM_CODEC, ModNetworking::handleSetFeederRange);
        registrar.playToServer(SetFeederRoostActivePayload.TYPE, SetFeederRoostActivePayload.STREAM_CODEC, ModNetworking::handleSetFeederRoostActive);
        registrar.playToServer(SetFeederRoostSeedPayload.TYPE, SetFeederRoostSeedPayload.STREAM_CODEC, ModNetworking::handleSetFeederRoostSeed);
        registrar.playToServer(SetFeederStackModePayload.TYPE, SetFeederStackModePayload.STREAM_CODEC, ModNetworking::handleSetFeederStackMode);
        registrar.playToServer(SetFeederRoundRobinPayload.TYPE, SetFeederRoundRobinPayload.STREAM_CODEC, ModNetworking::handleSetFeederRoundRobin);
        registrar.playToServer(SetAutoOutputPayload.TYPE, SetAutoOutputPayload.STREAM_CODEC, ModNetworking::handleSetAutoOutput);
        registrar.playToServer(SetNamePayload.TYPE, SetNamePayload.STREAM_CODEC, ModNetworking::handleSetName);
        registrar.playToServer(SetTrainerLevelPayload.TYPE, SetTrainerLevelPayload.STREAM_CODEC, ModNetworking::handleSetTrainerLevel);

        registrar.playToClient(
                SyncFeederActiveTargetsPayload.TYPE,
                SyncFeederActiveTargetsPayload.STREAM_CODEC,
                SyncFeederActiveTargetsPayload::handle
        );

        registrar.playToClient(SyncAutoOutputPayload.TYPE, SyncAutoOutputPayload.STREAM_CODEC, ModNetworking::handleSyncAutoOutput);
        registrar.playToClient(SyncTrainerLevelPayload.TYPE, SyncTrainerLevelPayload.STREAM_CODEC, ModNetworking::handleSyncTrainerLevel);
    }

    /* -------------------- CLIENTBOUND -------------------- */

    public static void sendFeederTargets(ServerPlayer player, FeederTile feeder) {
        PacketDistributor.sendToPlayer(
                player,
                new SyncFeederActiveTargetsPayload(
                        feeder.getBlockPos(),
                        List.copyOf(feeder.getActiveRoosts())
                )
        );
    }

    private static void handleSyncAutoOutput(SyncAutoOutputPayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.player() == null) return;

            BlockEntity be = ctx.player().level().getBlockEntity(msg.pos());
            if (be instanceof ICollectorTarget roost) {
                roost.setAutoOutputClient(msg.enabled());
            }
        });
    }

    private static void handleSyncTrainerLevel(SyncTrainerLevelPayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.player() == null) return;

            BlockEntity be = ctx.player().level().getBlockEntity(msg.pos());
            if (be instanceof TrainerTile trainer) {
                trainer.setAutoOutputLevelClient(msg.level());
            }
        });
    }

    /* -------------------- SERVERBOUND -------------------- */

    private static void handleSetCollectorRoostActive(SetCollectorRoostActivePayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;
            ServerLevel level = player.connection.getPlayer().level();

            if (!level.hasChunkAt(msg.collectorPos())) return;
            BlockEntity be = level.getBlockEntity(msg.collectorPos());

            if (be instanceof CollectorTile ct) {
                ct.setRoostActive(msg.roostPos(), msg.active());
            }
        });
    }

    private static void handleSetCollectorRange(SetCollectorRangePayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;
            ServerLevel level = player.connection.getPlayer().level();

            if (!level.hasChunkAt(msg.collectorPos())) return;
            BlockEntity be = level.getBlockEntity(msg.collectorPos());

            if (be instanceof CollectorTile ct) {
                ct.setCollectRange(msg.range());
            }
        });
    }

    private static void handleSetFeederRange(SetFeederRangePayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;
            ServerLevel level = player.connection.getPlayer().level();

            if (!level.hasChunkAt(msg.feederPos())) return;
            BlockEntity be = level.getBlockEntity(msg.feederPos());

            if (be instanceof FeederTile feeder) {
                feeder.setFeedRange(msg.range());
            }
        });
    }

    /*private static void handleSetFeederRoostActive(SetFeederRoostActivePayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;
            ServerLevel level = player.connection.getPlayer().level();

            if (!level.hasChunkAt(msg.feederPos())) return;
            BlockEntity be = level.getBlockEntity(msg.feederPos());

            if (be instanceof FeederTile feeder) {
                if (msg.active()) feeder.addActiveRoost(msg.roostPos());
                else feeder.removeActiveRoost(msg.roostPos());
            }
        });
    }*/

    public static void handleSetFeederRoostActive(
            SetFeederRoostActivePayload payload,
            IPayloadContext ctx
    ) {
        ctx.enqueueWork(() -> {

            // 🔒 Sicherheitscheck
            if (!(ctx.player() instanceof ServerPlayer player)) return;

            ServerLevel level = player.connection.getPlayer().level();
            BlockEntity be = level.getBlockEntity(payload.feederPos());

            if (!(be instanceof FeederTile feeder)) return;

            BlockPos roostPos = payload.roostPos();

            // 🔒 Reichweiten-Check (optional, aber empfohlen)
            if (player.distanceToSqr(
                    feeder.getBlockPos().getX() + 0.5,
                    feeder.getBlockPos().getY() + 0.5,
                    feeder.getBlockPos().getZ() + 0.5
            ) > 64) return;

            // ✅ SERVER-WAHRHEIT ändern
            if (payload.active()) {
                feeder.addActiveRoost(roostPos);
            } else {
                feeder.removeActiveRoost(roostPos);
            }

            // 🔄 ALLE CLIENTS synchronisieren
            feeder.onTargetsChanged();
        });
    }

    private static void handleSetFeederStackMode(SetFeederStackModePayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;
            ServerLevel level = player.connection.getPlayer().level();

            if (!level.hasChunkAt(msg.feederPos())) return;
            BlockEntity be = level.getBlockEntity(msg.feederPos());

            if (be instanceof FeederTile feeder) {
                feeder.setStackSendModeById(msg.mode());
            }
        });
    }

    private static void handleSetFeederRoundRobin(SetFeederRoundRobinPayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;
            ServerLevel level = player.connection.getPlayer().level();

            if (!level.hasChunkAt(msg.feederPos())) return;
            BlockEntity be = level.getBlockEntity(msg.feederPos());

            if (be instanceof FeederTile feeder) {
                feeder.setRoundRobinEnabled(msg.enabled());
            }
        });
    }

    private static void handleSetAutoOutput(SetAutoOutputPayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;
            ServerLevel level = player.connection.getPlayer().level();

            if (!level.hasChunkAt(msg.pos())) return;
            BlockEntity be = level.getBlockEntity(msg.pos());

            if (be instanceof ICollectorTarget roost) {
                roost.setAutoOutputFromGui(msg.enabled());
            }
        });
    }

    private static void handleSetName(SetNamePayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;
            ServerLevel level = player.connection.getPlayer().level();

            if (!level.hasChunkAt(msg.pos())) return;
            BlockEntity be = level.getBlockEntity(msg.pos());

            if (be instanceof ICollectorTarget roost) {
                roost.setCustomName(msg.name());
            }
        });
    }

    private static void handleSetTrainerLevel(SetTrainerLevelPayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;
            ServerLevel level = player.connection.getPlayer().level();

            if (!level.hasChunkAt(msg.pos())) return;
            BlockEntity be = level.getBlockEntity(msg.pos());

            if (be instanceof TrainerTile trainer) {
                trainer.setAutoOutputLevel(msg.level());
            }
        });
    }

    private static void handleSetFeederRoostSeed(SetFeederRoostSeedPayload msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;
            ServerLevel level = player.connection.getPlayer().level();

            if (!level.hasChunkAt(msg.feederPos())) return;
            BlockEntity be = level.getBlockEntity(msg.feederPos());
            if (!(be instanceof FeederTile feeder)) return;

            msg.itemId().ifPresentOrElse(id -> {
                BuiltInRegistries.ITEM.getOptional(id)
                        .filter(ModNetworking::isAllowedSeed)
                        .ifPresentOrElse(
                                item -> feeder.setPreferredSeed(msg.roostPos(), item),
                                () -> feeder.setPreferredSeed(msg.roostPos(), null)
                        );
            }, () -> feeder.setPreferredSeed(msg.roostPos(), null));
        });
    }

    private static boolean isAllowedSeed(Item item) {
        return item instanceof ChickenSeedBase;
    }
}
