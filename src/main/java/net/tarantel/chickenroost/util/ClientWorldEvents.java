package net.tarantel.chickenroost.util;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent.Load;

@EventBusSubscriber(Dist.CLIENT)
public class ClientWorldEvents {
   @SubscribeEvent
   public static void onWorldLoad(Load event) {
      if (event.getLevel().isClientSide()) {
         ClientBiomeCache.reloadFromDisk();
      }
   }
}
