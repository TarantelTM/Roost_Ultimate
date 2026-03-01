package net.tarantel.chickenroost.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.PreparableReloadListener.PreparationBarrier;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class ServerBiomeReloadListener implements PreparableReloadListener {
   public CompletableFuture<Void> reload(
      PreparationBarrier barrier,
      ResourceManager manager,
      ProfilerFiller prepProfiler,
      ProfilerFiller reloadProfiler,
      Executor backgroundExecutor,
      Executor gameExecutor
   ) {
      return CompletableFuture.runAsync(() -> {}, backgroundExecutor).thenCompose(barrier::wait).thenRunAsync(() -> {
         MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
         if (server != null) {
            ServerBiomeCache.rebuild(server.overworld());
         }
      }, gameExecutor);
   }
}
