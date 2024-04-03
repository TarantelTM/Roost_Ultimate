
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenTNTEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenTNTRenderer extends MobRenderer<AChickenTNTEntity, Modelchicken<AChickenTNTEntity>> {
	public AChickenTNTRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenTNTEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/gunpowderchicken.png");
	}
}
