
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.vanilla.AChickenbeetrootEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenbeetrootRenderer extends MobRenderer<AChickenbeetrootEntity, Modelchicken<AChickenbeetrootEntity>> {
	public AChickenbeetrootRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenbeetrootEntity entity) {
		return new ResourceLocation("chicken_roost:textures/redstone_alloy_chicken.png");
	}
}
