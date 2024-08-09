
package net.tarantel.chickenroost.client.renderer;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.ChickenRoostMod;

import net.tarantel.chickenroost.entity.mods.thermal.AChickenBasalzEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenBasalzRenderer extends MobRenderer<AChickenBasalzEntity, Modelchicken<AChickenBasalzEntity>> {
	public AChickenBasalzRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenBasalzEntity entity) {
		return ChickenRoostMod.ownresource("textures/entities/basalz_rod_chicken.png");
	}
}
