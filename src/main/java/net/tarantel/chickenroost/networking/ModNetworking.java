package net.tarantel.chickenroost.networking;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.CollectorTile;
import net.tarantel.chickenroost.block.tile.FeederTile;
import net.tarantel.chickenroost.block.tile.TrainerTile;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;

public final class ModNetworking {
   private ModNetworking() {
   }

   @SubscribeEvent
   public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("1");
      registrar.playToServer(SetCollectorRoostActivePayload.TYPE, SetCollectorRoostActivePayload.STREAM_CODEC, ModNetworking::handleSetCollectorRoostActive);
      registrar.playToServer(SetCollectorRangePayload.TYPE, SetCollectorRangePayload.STREAM_CODEC, ModNetworking::handleSetCollectorRange);
      registrar.playToServer(SetFeederRangePayload.TYPE, SetFeederRangePayload.STREAM_CODEC, ModNetworking::handleSetFeederRange);
      registrar.playToServer(SetFeederRoostActivePayload.TYPE, SetFeederRoostActivePayload.STREAM_CODEC, ModNetworking::handleSetFeederRoostActive);
      registrar.playToServer(SetFeederRoostSeedPayload.TYPE, SetFeederRoostSeedPayload.STREAM_CODEC, ModNetworking::handleSetFeederRoostSeed);
      registrar.playToServer(SetFeederStackModePayload.TYPE, SetFeederStackModePayload.STREAM_CODEC, ModNetworking::handleSetFeederStackMode);
      registrar.playToServer(SetAutoOutputPayload.TYPE, SetAutoOutputPayload.STREAM_CODEC, ModNetworking::handleSetAutoOutput);
      registrar.playToServer(SetNamePayload.TYPE, SetNamePayload.STREAM_CODEC, ModNetworking::handleSetName);
      registrar.playToServer(SetFeederRoundRobinPayload.TYPE, SetFeederRoundRobinPayload.STREAM_CODEC, ModNetworking::handleSetFeederRoundRobin);
      registrar.playToClient(SyncAutoOutputPayload.TYPE, SyncAutoOutputPayload.STREAM_CODEC, ModNetworking::handleSyncAutoOutput);
      registrar.playToServer(SetTrainerLevelPayload.TYPE, SetTrainerLevelPayload.STREAM_CODEC, ModNetworking::handleSetTrainerLevel);
      registrar.playToClient(SyncTrainerLevelPayload.TYPE, SyncTrainerLevelPayload.STREAM_CODEC, ModNetworking::handleSyncTrainerLevel);
   }

   public static void handleSyncTrainerLevel(SyncTrainerLevelPayload msg, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         if (ctx.player().level().isClientSide()) {
            if (ctx.player().level().getBlockEntity(msg.pos()) instanceof TrainerTile trainer) {
               trainer.setAutoOutputLevelClient(msg.level());
            }
         }
      });
   }

   public static void handleSetTrainerLevel(SetTrainerLevelPayload msg, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Player player = ctx.player();
         if (player != null) {
            Level level = player.level();
            if (level.getBlockEntity(msg.pos()) instanceof TrainerTile trainer) {
               trainer.setAutoOutputLevel(msg.level());
            }
         }
      });
   }

   private static void handleSyncAutoOutput(SyncAutoOutputPayload msg, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         if (ctx.player().level().isClientSide()) {
            if (ctx.player().level().getBlockEntity(msg.pos()) instanceof ICollectorTarget roost) {
               roost.setAutoOutputClient(msg.enabled());
            }
         }
      });
   }

   private static void handleSetFeederStackMode(SetFeederStackModePayload msg, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         ServerPlayer player = (ServerPlayer)ctx.player();
         ServerLevel level = player.serverLevel();
         if (level.getBlockEntity(msg.feederPos()) instanceof FeederTile feeder) {
            feeder.setStackSendModeById(msg.mode());
         }
      });
   }

   private static void handleSetFeederRoundRobin(SetFeederRoundRobinPayload msg, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         if (ctx.player() instanceof ServerPlayer sp) {
            if (sp.level() instanceof ServerLevel level) {
               if (level.getBlockEntity(msg.feederPos()) instanceof FeederTile feeder) {
                  feeder.setRoundRobinEnabled(msg.enabled());
               }
            }
         }
      });
   }

   private static void handleSetAutoOutput(SetAutoOutputPayload msg, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         ServerPlayer player = (ServerPlayer)ctx.player();
         ServerLevel level = player.serverLevel();
         if (level.getBlockEntity(msg.pos()) instanceof ICollectorTarget roost) {
            roost.setAutoOutputFromGui(msg.enabled());
         }
      });
   }

   private static void handleSetCollectorRoostActive(SetCollectorRoostActivePayload msg, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         ServerPlayer player = (ServerPlayer)ctx.player();
         ServerLevel level = player.serverLevel();
         if (level.getBlockEntity(msg.collectorPos()) instanceof CollectorTile ct) {
            ct.setRoostActive(msg.roostPos(), msg.active());
         }
      });
   }

   private static void handleSetCollectorRange(SetCollectorRangePayload msg, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         ServerPlayer player = (ServerPlayer)ctx.player();
         ServerLevel level = player.serverLevel();
         if (level.getBlockEntity(msg.collectorPos()) instanceof CollectorTile ct) {
            ct.setCollectRange(msg.range());
         }
      });
   }

   private static void handleSetFeederRange(SetFeederRangePayload msg, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         ServerPlayer player = (ServerPlayer)ctx.player();
         ServerLevel level = player.serverLevel();
         if (level.getBlockEntity(msg.feederPos()) instanceof FeederTile feeder) {
            feeder.setFeedRange(msg.range());
         }
      });
   }

   private static void handleSetFeederRoostActive(SetFeederRoostActivePayload msg, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         ServerPlayer player = (ServerPlayer)ctx.player();
         ServerLevel level = player.serverLevel();
         if (level.getBlockEntity(msg.feederPos()) instanceof FeederTile feeder) {
            if (msg.active()) {
               feeder.addActiveRoost(msg.roostPos());
            } else {
               feeder.removeActiveRoost(msg.roostPos());
            }
         }
      });
   }

   private static void handleSetName(SetNamePayload msg, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         ServerPlayer player = (ServerPlayer)ctx.player();
         ServerLevel level = player.serverLevel();
         if (level.getBlockEntity(msg.pos()) instanceof ICollectorTarget roost) {
            roost.setCustomName(msg.name());
         }
      });
   }

   private static void handleSetFeederRoostSeed(SetFeederRoostSeedPayload msg, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         ServerPlayer player = (ServerPlayer)ctx.player();
         ServerLevel level = player.serverLevel();
         if (level.getBlockEntity(msg.feederPos()) instanceof FeederTile feeder) {
            if (msg.itemId() != null && !msg.itemId().isEmpty()) {
               Item item = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.parse(msg.itemId()));
               if (!isAllowedGhostItemServer(item)) {
                  feeder.setPreferredSeed(msg.roostPos(), null);
               } else {
                  feeder.setPreferredSeed(msg.roostPos(), item);
               }
            } else {
               feeder.setPreferredSeed(msg.roostPos(), null);
            }
         }
      });
   }

   private static boolean isAllowedGhostItemServer(Item item) {
      return item instanceof ChickenSeedBase;
   }
}
