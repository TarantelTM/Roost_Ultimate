
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenLumiumEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenLumiumRenderer extends MobRenderer<AChickenLumiumEntity, Modelchicken<AChickenLumiumEntity>> {
	public AChickenLumiumRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenLumiumEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/lumium_chicken.png");
	}
}
