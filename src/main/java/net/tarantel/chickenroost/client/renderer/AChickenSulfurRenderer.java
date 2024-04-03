
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenSulfurEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenSulfurRenderer extends MobRenderer<AChickenSulfurEntity, Modelchicken<AChickenSulfurEntity>> {
	public AChickenSulfurRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenSulfurEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/sulfur_chicken.png");
	}
}
