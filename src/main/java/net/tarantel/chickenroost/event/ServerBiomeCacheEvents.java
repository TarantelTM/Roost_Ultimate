package net.tarantel.chickenroost.event;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tarantel.chickenroost.util.ServerBiomeCache;
import net.tarantel.chickenroost.util.ServerBiomeReloadListener;

@Mod.EventBusSubscriber
public class ServerBiomeCacheEvents {

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        ServerBiomeCache.rebuild(event.getServer().overworld());
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new ServerBiomeReloadListener());
    }
}
