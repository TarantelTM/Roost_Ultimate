
package net.tarantel.chickenroost.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.util.GsonChickenReader;
import net.tarantel.chickenroost.util.ChickenData;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		List<ChickenData> readItems = ChickenRoostMod.chickens;
		assert readItems != null;
		if(!readItems.isEmpty()){
			for(ChickenData etherItem : readItems){

				if (!etherItem.getId().equals("c_vanilla")) {
					String id = etherItem.getId();
					String mobtexture = etherItem.getMobtexture();

					ResourceLocation resourceLocation = new ResourceLocation(ChickenRoostMod.MODID, id);
					EntityType entityType = EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
					event.registerEntityRenderer(entityType, context -> new BaseChickenRenderer(context, mobtexture));
				}
			}
		}
	}
}