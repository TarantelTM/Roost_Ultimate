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
                .named(new ResourceLocation(ChickenRoostMod.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;


        net.messageBuilder(RoostItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RoostItemStackSyncS2CPacket::new)
                .encoder(RoostItemStackSyncS2CPacket::toBytes)
                .consumer(RoostItemStackSyncS2CPacket::handle)
                .add();

        net.messageBuilder(BreederItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BreederItemStackSyncS2CPacket::new)
                .encoder(BreederItemStackSyncS2CPacket::toBytes)
                .consumer(BreederItemStackSyncS2CPacket::handle)
                .add();

        net.messageBuilder(ExtractorStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ExtractorStackSyncS2CPacket::new)
                .encoder(ExtractorStackSyncS2CPacket::toBytes)
                .consumer(ExtractorStackSyncS2CPacket::handle)
                .add();
        net.messageBuilder(SoulBreederStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SoulBreederStackSyncS2CPacket::new)
                .encoder(SoulBreederStackSyncS2CPacket::toBytes)
                .consumer(SoulBreederStackSyncS2CPacket::handle)
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