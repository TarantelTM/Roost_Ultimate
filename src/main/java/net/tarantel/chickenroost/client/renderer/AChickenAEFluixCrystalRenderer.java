
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenAEFluixCrystalEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenAEFluixCrystalRenderer extends MobRenderer<AChickenAEFluixCrystalEntity, Modelchicken<AChickenAEFluixCrystalEntity>> {
	public AChickenAEFluixCrystalRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenAEFluixCrystalEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/ludicrite_chicken.png");
	}
}
