
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenmagmacreamEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenmagmacreamRenderer extends MobRenderer<AChickenmagmacreamEntity, Modelchicken<AChickenmagmacreamEntity>> {
	public AChickenmagmacreamRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenmagmacreamEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/magmachicken.png");
	}
}
