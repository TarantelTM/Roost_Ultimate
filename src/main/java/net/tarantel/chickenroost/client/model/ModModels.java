
package net.tarantel.chickenroost.client.model;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.tarantel.chickenroost.ChickenRoostMod;

@EventBusSubscriber(modid = ChickenRoostMod.MODID, value = Dist.CLIENT)
public class ModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modelchicken.LAYER_LOCATION, Modelchicken::createBodyLayer);
	}
}
