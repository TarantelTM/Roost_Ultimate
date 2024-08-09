
package net.tarantel.chickenroost.client.renderer;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.ChickenRoostMod;

import net.tarantel.chickenroost.entity.mods.aetwo.AChickenAEChargedCertusEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenAEChargedCertusRenderer extends MobRenderer<AChickenAEChargedCertusEntity, Modelchicken<AChickenAEChargedCertusEntity>> {
	public AChickenAEChargedCertusRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenAEChargedCertusEntity entity) {
		return ChickenRoostMod.ownresource("textures/entities/quartz_enriched_iron_chicken.png");
	}
}
