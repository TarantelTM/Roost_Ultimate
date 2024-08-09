
package net.tarantel.chickenroost.client.renderer;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.ChickenRoostMod;

import net.tarantel.chickenroost.entity.mods.mekanism.AChickenMekanismBioFuelEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenMekanismBioFuelRenderer extends MobRenderer<AChickenMekanismBioFuelEntity, Modelchicken<AChickenMekanismBioFuelEntity>> {
	public AChickenMekanismBioFuelRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenMekanismBioFuelEntity entity) {
		return ChickenRoostMod.ownresource("textures/entities/enderium_chicken.png");
	}
}
