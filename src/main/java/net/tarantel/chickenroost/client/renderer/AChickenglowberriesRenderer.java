
package net.tarantel.chickenroost.client.renderer;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.ChickenRoostMod;

import net.tarantel.chickenroost.entity.vanilla.AChickenglowberriesEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenglowberriesRenderer extends MobRenderer<AChickenglowberriesEntity, Modelchicken<AChickenglowberriesEntity>> {
	public AChickenglowberriesRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenglowberriesEntity entity) {
		return ChickenRoostMod.ownresource("textures/glowstonechicken.png");
	}
}
