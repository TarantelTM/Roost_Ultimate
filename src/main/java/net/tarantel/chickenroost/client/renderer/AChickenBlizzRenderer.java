
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenBlizzEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenBlizzRenderer extends MobRenderer<AChickenBlizzEntity, Modelchicken<AChickenBlizzEntity>> {
	public AChickenBlizzRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenBlizzEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/blizz_rod_chicken.png");
	}
}
