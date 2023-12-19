
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.mods.aetwo.AChickenAESiliconEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenAESiliconRenderer extends MobRenderer<AChickenAESiliconEntity, Modelchicken<AChickenAESiliconEntity>> {
	public AChickenAESiliconRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenAESiliconEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/silicon_chicken.png");
	}
}
