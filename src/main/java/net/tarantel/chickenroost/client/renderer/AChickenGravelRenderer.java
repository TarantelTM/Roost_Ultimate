
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenGravelEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenGravelRenderer extends MobRenderer<AChickenGravelEntity, Modelchicken<AChickenGravelEntity>> {
	public AChickenGravelRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenGravelEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/graychicken.png");
	}
}
