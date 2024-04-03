
package net.tarantel.chickenroost.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.client.model.Modelchicken;
import net.tarantel.chickenroost.entity.AChickenTinEntity;

public class AChickenMekanismTinRenderer extends MobRenderer<AChickenTinEntity, Modelchicken<AChickenTinEntity>> {
	public AChickenMekanismTinRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenTinEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/tin_chicken.png");
	}
}
