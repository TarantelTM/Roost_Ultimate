
package net.tarantel.chickenroost.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.client.model.Modelchicken;
import net.tarantel.chickenroost.entity.AChickenBronzeEntity;

public class AChickenMekanismBronzeRenderer extends MobRenderer<AChickenBronzeEntity, Modelchicken<AChickenBronzeEntity>> {
	public AChickenMekanismBronzeRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenBronzeEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/bronze_chicken.png");
	}
}
