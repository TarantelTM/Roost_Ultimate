package net.tarantel.chickenroost.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ServerBiomeReloadListener implements PreparableReloadListener {

    @Override
    public CompletableFuture<Void> reload(
            SharedState state,
            Executor backgroundExecutor,
            PreparationBarrier barrier,
            Executor gameExecutor
    ) {
        return CompletableFuture
                // Background-Phase (keine MC-Zugriffe!)
                .runAsync(() -> {
                    // z.B. JSON parsen, Daten vorbereiten
                }, backgroundExecutor)

                // Synchronisation mit Vanilla
                .thenCompose(barrier::wait)

                // Game-Thread-Phase
                .thenRunAsync(() -> {
                    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                    if (server != null) {
                        ServerBiomeCache.rebuild(server.overworld());
                    }
                }, gameExecutor);
    }
}
