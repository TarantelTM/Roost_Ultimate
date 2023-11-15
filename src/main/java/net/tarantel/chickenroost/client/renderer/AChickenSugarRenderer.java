
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.vanilla.AChickenSugarEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenSugarRenderer extends MobRenderer<AChickenSugarEntity, Modelchicken<AChickenSugarEntity>> {
	public AChickenSugarRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenSugarEntity entity) {
		return new ResourceLocation("chicken_roost:textures/enori_crystal_chicken.png");
	}
}
