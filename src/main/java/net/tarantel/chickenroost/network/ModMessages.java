package net.tarantel.chickenroost.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.tarantel.chickenroost.ChickenRoostMod;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation("chicken_roost:messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;


        net.messageBuilder(RoostItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RoostItemStackSyncS2CPacket::new)
                .encoder(RoostItemStackSyncS2CPacket::toBytes)
                .consumerMainThread(RoostItemStackSyncS2CPacket::handle)
                .add();

        net.messageBuilder(BreederItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BreederItemStackSyncS2CPacket::new)
                .encoder(BreederItemStackSyncS2CPacket::toBytes)
                .consumerMainThread(BreederItemStackSyncS2CPacket::handle)
                .add();

        net.messageBuilder(ExtractorStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ExtractorStackSyncS2CPacket::new)
                .encoder(ExtractorStackSyncS2CPacket::toBytes)
                .consumerMainThread(ExtractorStackSyncS2CPacket::handle)
                .add();
        net.messageBuilder(SetBreederAutoOutputPayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetBreederAutoOutputPayload::new)
                .encoder(SetBreederAutoOutputPayload::toBytes)
                .consumerMainThread(SetBreederAutoOutputPayload::handle)
                .add();

        net.messageBuilder(SetRoostAutoOutputPayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetRoostAutoOutputPayload::new)
                .encoder(SetRoostAutoOutputPayload::toBytes)
                .consumerMainThread(SetRoostAutoOutputPayload::handle)
                .add();

        net.messageBuilder(SetRoostNamePayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetRoostNamePayload::new)
                .encoder(SetRoostNamePayload::toBytes)
                .consumerMainThread(SetRoostNamePayload::handle)
                .add();

        net.messageBuilder(SetBreederNamePayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetBreederNamePayload::new)
                .encoder(SetBreederNamePayload::toBytes)
                .consumerMainThread(SetBreederNamePayload::handle)
                .add();

        net.messageBuilder(SetTrainerNamePayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetTrainerNamePayload::new)
                .encoder(SetTrainerNamePayload::toBytes)
                .consumerMainThread(SetTrainerNamePayload::handle)
                .add();

        net.messageBuilder(SetExtractorNamePayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetExtractorNamePayload::new)
                .encoder(SetExtractorNamePayload::toBytes)
                .consumerMainThread(SetExtractorNamePayload::handle)
                .add();

        net.messageBuilder(SetCollectorRangePayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetCollectorRangePayload::new)
                .encoder(SetCollectorRangePayload::toBytes)
                .consumerMainThread(SetCollectorRangePayload::handle)
                .add();

        net.messageBuilder(SetCollectorRoostActivePayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetCollectorRoostActivePayload::new)
                .encoder(SetCollectorRoostActivePayload::toBytes)
                .consumerMainThread(SetCollectorRoostActivePayload::handle)
                .add();

        net.messageBuilder(SetExtractorAutoOutputPayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetExtractorAutoOutputPayload::new)
                .encoder(SetExtractorAutoOutputPayload::toBytes)
                .consumerMainThread(SetExtractorAutoOutputPayload::handle)
                .add();

        net.messageBuilder(SetFeederRangePayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetFeederRangePayload::new)
                .encoder(SetFeederRangePayload::toBytes)
                .consumerMainThread(SetFeederRangePayload::handle)
                .add();

        net.messageBuilder(SetFeederRoostActivePayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetFeederRoostActivePayload::new)
                .encoder(SetFeederRoostActivePayload::toBytes)
                .consumerMainThread(SetFeederRoostActivePayload::handle)
                .add();

        net.messageBuilder(SetFeederRoostSeedPayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetFeederRoostSeedPayload::new)
                .encoder(SetFeederRoostSeedPayload::toBytes)
                .consumerMainThread(SetFeederRoostSeedPayload::handle)
                .add();

        net.messageBuilder(SetFeederRoundRobinPayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetFeederRoundRobinPayload::new)
                .encoder(SetFeederRoundRobinPayload::toBytes)
                .consumerMainThread(SetFeederRoundRobinPayload::handle)
                .add();

        net.messageBuilder(SetFeederStackModePayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetFeederStackModePayload::new)
                .encoder(SetFeederStackModePayload::toBytes)
                .consumerMainThread(SetFeederStackModePayload::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}