//
//package net.tarantel.chickenroost.client.renderer;
//
//import net.tarantel.chickenroost.entity.AChickenTemplateEntity;
//import net.tarantel.chickenroost.client.model.Modelchicken;
//
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.client.renderer.entity.MobRenderer;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//
//public class AChickenTemplateRenderer extends MobRenderer<AChickenTemplateEntity, Modelchicken<AChickenTemplateEntity>> {
//	public AChickenTemplateRenderer(EntityRendererProvider.Context context) {
//		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
//	}
//
//	@Override
//	public ResourceLocation getTextureLocation(AChickenTemplateEntity entity) {
//		return new ResourceLocation("chicken_roost:textures/entities/vinteum_chicken.png");
//	}
//}
