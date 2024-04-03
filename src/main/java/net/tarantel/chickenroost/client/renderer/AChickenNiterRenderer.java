
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenNiterEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenNiterRenderer extends MobRenderer<AChickenNiterEntity, Modelchicken<AChickenNiterEntity>> {
	public AChickenNiterRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenNiterEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/vinteum_chicken.png");
	}
}
