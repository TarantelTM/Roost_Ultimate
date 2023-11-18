package net.tarantel.chickenroost.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;

public class DataReceiver {
    public static final Identifier CHICKEN_SYNC = new Identifier(ChickenRoostMod.MODID, "chicken_sync");

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(CHICKEN_SYNC, ChickenSyncS2CPacket::receive);
    }
}
