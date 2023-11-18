package net.tarantel.chickenroost.client.model;


import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelchicken<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier("chicken_roost", "modelchicken"), "modelchicken");
	public final ModelPart body;
	public final ModelPart head;
	public final ModelPart leg0;
	public final ModelPart leg1;
	public final ModelPart wing0;
	public final ModelPart wing1;

	public Modelchicken(ModelPart root) {
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.leg0 = root.getChild("leg0");
		this.leg1 = root.getChild("leg1");
		this.wing0 = root.getChild("wing0");
		this.wing1 = root.getChild("wing1");
	}

	public static TexturedModelData createBodyLayer() {
		ModelData meshdefinition = new ModelData();
		ModelPartData partdefinition = meshdefinition.getRoot();
		ModelPartData body = partdefinition.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.0F, 0.0F));
		ModelPartData body_r1 = body.addChild("body_r1",
				ModelPartBuilder.create().uv(0, 9).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new Dilation(0.0F)),
				ModelTransform.of(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
		ModelPartData head = partdefinition.addChild("head",
				ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, new Dilation(0.0F)),
				ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		ModelPartData comb = head.addChild("comb",
				ModelPartBuilder.create().uv(14, 4).cuboid(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)),
				ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData beak = head.addChild("beak",
				ModelPartBuilder.create().uv(14, 0).cuboid(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)),
				ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData leg0 = partdefinition.addChild("leg0",
				ModelPartBuilder.create().uv(26, 0).cuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)),
				ModelTransform.pivot(-2.0F, 19.0F, 1.0F));
		ModelPartData leg1 = partdefinition.addChild("leg1",
				ModelPartBuilder.create().uv(26, 0).cuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)),
				ModelTransform.pivot(1.0F, 19.0F, 1.0F));
		ModelPartData wing0 = partdefinition.addChild("wing0",
				ModelPartBuilder.create().uv(24, 13).cuboid(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new Dilation(0.0F)),
				ModelTransform.pivot(-3.0F, 13.0F, 0.0F));
		ModelPartData wing1 = partdefinition.addChild("wing1",
				ModelPartBuilder.create().uv(24, 13).cuboid(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new Dilation(0.0F)),
				ModelTransform.pivot(3.0F, 13.0F, 0.0F));
		return TexturedModelData.of(meshdefinition, 64, 32);
	}


	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

		this.head.yaw = headYaw / (180F / (float) Math.PI);
		this.head.pitch = headPitch / (180F / (float) Math.PI);
		this.leg0.pitch = MathHelper.cos(limbAngle * 1.0F) * 1.0F * limbDistance;
		this.leg1.pitch = MathHelper.cos(limbAngle * 1.0F) * -1.0F * limbDistance;
		this.wing1.roll = MathHelper.cos(limbAngle * 0.6662F) * limbDistance;
		this.wing0.roll = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * limbDistance;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

		body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		leg0.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		leg1.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		wing0.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		wing1.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}
