
package net.tarantel.chickenroost.client.renderer;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.ChickenRoostMod;

import net.tarantel.chickenroost.entity.mods.botania.AChickenBotaniaLivingrockEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenBotaniaLivingrockRenderer extends MobRenderer<AChickenBotaniaLivingrockEntity, Modelchicken<AChickenBotaniaLivingrockEntity>> {
	public AChickenBotaniaLivingrockRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenBotaniaLivingrockEntity entity) {
		return ChickenRoostMod.ownresource("textures/entities/moonstone_chicken.png");
	}
}
