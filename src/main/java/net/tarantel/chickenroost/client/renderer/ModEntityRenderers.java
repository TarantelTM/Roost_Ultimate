
package net.tarantel.chickenroost.client.renderer;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.util.ChickenData;
import net.tarantel.chickenroost.util.GsonChickenReader;

import java.util.List;

@EventBusSubscriber(modid = ChickenRoostMod.MODID, value = Dist.CLIENT)
public class ModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
		assert readItems != null;
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){

				if (!etherItem.getId().equals("c_vanilla")) {
					String id = etherItem.getId();
					String mobtexture = etherItem.getMobtexture();

					Identifier identifier = Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, id);
					EntityType entityType = EntityType.byString(identifier.toString()).orElse(EntityType.CHICKEN);
					event.registerEntityRenderer(entityType, context -> new BaseChickenRenderer(context, mobtexture));
				}
			}
		}
	}
}