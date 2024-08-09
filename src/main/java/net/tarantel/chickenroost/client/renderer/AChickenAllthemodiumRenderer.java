
package net.tarantel.chickenroost.client.renderer;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.ChickenRoostMod;

import net.tarantel.chickenroost.entity.mods.allthemodium.AChickenAllthemodiumEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenAllthemodiumRenderer extends MobRenderer<AChickenAllthemodiumEntity, Modelchicken<AChickenAllthemodiumEntity>> {
	public AChickenAllthemodiumRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenAllthemodiumEntity entity) {
		return ChickenRoostMod.ownresource("textures/entities/energetic_alloy_chicken.png");
	}
}
