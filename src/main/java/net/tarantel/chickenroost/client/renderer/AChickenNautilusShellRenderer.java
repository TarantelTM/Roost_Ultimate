
package net.tarantel.chickenroost.client.renderer;

import net.tarantel.chickenroost.entity.AChickenNautilusShellEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenNautilusShellRenderer extends MobRenderer<AChickenNautilusShellEntity, Modelchicken<AChickenNautilusShellEntity>> {
	public AChickenNautilusShellRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenNautilusShellEntity entity) {
		return new ResourceLocation("chicken_roost:textures/entities/conductive_iron_chicken.png");
	}
}
