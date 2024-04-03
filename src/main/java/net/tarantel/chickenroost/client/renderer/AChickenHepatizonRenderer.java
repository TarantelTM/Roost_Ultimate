
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenHepatizonEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenHepatizonRenderer extends MobRenderer<AChickenHepatizonEntity, Modelchicken<AChickenHepatizonEntity>> {
	public AChickenHepatizonRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenHepatizonEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/tanzanite_chicken.png");
	}
}
