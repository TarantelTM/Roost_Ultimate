
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenTitaniumEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenTitaniumRenderer extends MobRenderer<AChickenTitaniumEntity, Modelchicken<AChickenTitaniumEntity>> {
	public AChickenTitaniumRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenTitaniumEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/titanium_chicken.png");
	}
}
