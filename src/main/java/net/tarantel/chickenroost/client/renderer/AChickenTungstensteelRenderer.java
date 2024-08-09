
package net.tarantel.chickenroost.client.renderer;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.ChickenRoostMod;

import net.tarantel.chickenroost.entity.wip.AChickenTungstensteelEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenTungstensteelRenderer extends MobRenderer<AChickenTungstensteelEntity, Modelchicken<AChickenTungstensteelEntity>> {
	public AChickenTungstensteelRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenTungstensteelEntity entity) {
		return ChickenRoostMod.ownresource("textures/entities/tungstensteel_chicken.png");
	}
}
