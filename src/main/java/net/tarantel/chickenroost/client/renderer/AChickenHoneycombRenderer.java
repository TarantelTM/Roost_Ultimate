
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenHoneycombEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenHoneycombRenderer extends MobRenderer<AChickenHoneycombEntity, Modelchicken<AChickenHoneycombEntity>> {
	public AChickenHoneycombRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenHoneycombEntity entity) {
		return new ResourceLocation("chicken_roost:textures/yellowchicken.png");
	}
}
