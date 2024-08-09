
package net.tarantel.chickenroost.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.client.model.Modelchicken;
import net.tarantel.chickenroost.entity.vanilla.AChickenAcaciaWoodEntity;
import net.tarantel.chickenroost.entity.vanilla.AChickenBreezeEntity;

public class AChickenBreezeRenderer extends MobRenderer<AChickenBreezeEntity, Modelchicken<AChickenBreezeEntity>> {
	public AChickenBreezeRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenBreezeEntity entity) {
		return ChickenRoostMod.ownresource("textures/breeze.png");
	}
}
