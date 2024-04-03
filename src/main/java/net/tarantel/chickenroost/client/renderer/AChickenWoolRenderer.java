
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenWoolEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenWoolRenderer extends MobRenderer<AChickenWoolEntity, Modelchicken<AChickenWoolEntity>> {
	public AChickenWoolRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenWoolEntity entity) {
		return new ResourceLocation("chicken_roost:textures/enori_crystal_chicken.png");
	}
}
