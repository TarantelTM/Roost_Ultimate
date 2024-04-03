
package net.tarantel.chickenroost.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.client.model.Modelchicken;
import net.tarantel.chickenroost.entity.AChickenUraniumEntity;

public class AChickenMekanismUraniumRenderer extends MobRenderer<AChickenUraniumEntity, Modelchicken<AChickenUraniumEntity>> {
	public AChickenMekanismUraniumRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenUraniumEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/uranium_chicken.png");
	}
}
