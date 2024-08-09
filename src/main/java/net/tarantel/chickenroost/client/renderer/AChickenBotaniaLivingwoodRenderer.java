
package net.tarantel.chickenroost.client.renderer;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.entity.mods.botania.AChickenBotaniaLivingwoodEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenBotaniaLivingwoodRenderer extends MobRenderer<AChickenBotaniaLivingwoodEntity, Modelchicken<AChickenBotaniaLivingwoodEntity>> {
	public AChickenBotaniaLivingwoodRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenBotaniaLivingwoodEntity entity) {
		return ChickenRoostMod.ownresource("textures/entities/magical_wood_chicken.png");
	}
}
