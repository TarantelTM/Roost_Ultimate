
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenJungleWoodEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenJungleWoodRenderer extends MobRenderer<AChickenJungleWoodEntity, Modelchicken<AChickenJungleWoodEntity>> {
	public AChickenJungleWoodRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenJungleWoodEntity entity) {
		return new ResourceLocation("chicken_roost:textures/pulsating_iron_chicken.png");
	}
}
