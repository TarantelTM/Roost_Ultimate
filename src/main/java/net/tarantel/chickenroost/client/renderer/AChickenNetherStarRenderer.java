
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenNetherStarEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenNetherStarRenderer extends MobRenderer<AChickenNetherStarEntity, Modelchicken<AChickenNetherStarEntity>> {
	public AChickenNetherStarRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenNetherStarEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/energetic_alloy_chicken.png");
	}
}
