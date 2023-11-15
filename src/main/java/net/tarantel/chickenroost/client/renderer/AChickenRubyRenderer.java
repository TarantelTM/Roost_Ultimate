
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.mods.silentgems.AChickenRubyEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenRubyRenderer extends MobRenderer<AChickenRubyEntity, Modelchicken<AChickenRubyEntity>> {
	public AChickenRubyRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenRubyEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/ruby_chicken.png");
	}
}
