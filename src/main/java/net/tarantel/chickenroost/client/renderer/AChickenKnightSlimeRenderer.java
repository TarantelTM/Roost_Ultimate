
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenKnightSlimeEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenKnightSlimeRenderer extends MobRenderer<AChickenKnightSlimeEntity, Modelchicken<AChickenKnightSlimeEntity>> {
	public AChickenKnightSlimeRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenKnightSlimeEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/knight_slime_chicken.png");
	}
}
