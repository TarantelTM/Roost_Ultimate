package net.tarantel.chickenroost.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.ChickenRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class Modelchicken<T extends Entity> extends EntityModel<@NotNull ChickenRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("chicken_roost", "modelchicken"), "main");
	/*public final ModelPart body;
	public final ModelPart head;
	public final ModelPart leg0;
	public final ModelPart leg1;
	public final ModelPart wing0;
	public final ModelPart wing1;*/

	private final ModelPart head;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;
	private final ModelPart rightWing;
	private final ModelPart leftWing;

	public Modelchicken(ModelPart root) {
		super(root);
		/*this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.leg0 = root.getChild("leg0");
		this.leg1 = root.getChild("leg1");
		this.wing0 = root.getChild("wing0");
		this.wing1 = root.getChild("wing1");*/
		this.head = root.getChild("head");
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
		this.rightWing = root.getChild("right_wing");
		this.leftWing = root.getChild("left_wing");
	}

	public static LayerDefinition createBodyLayer() {
		/*MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));
		PartDefinition body_r1 = body.addOrReplaceChild("body_r1",
				CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 15.0F, -4.0F));
		PartDefinition comb = head.addOrReplaceChild("comb",
				CubeListBuilder.create().texOffs(14, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition beak = head.addOrReplaceChild("beak",
				CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition leg0 = partdefinition.addOrReplaceChild("leg0",
				CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, 19.0F, 1.0F));
		PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1",
				CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(1.0F, 19.0F, 1.0F));
		PartDefinition wing0 = partdefinition.addOrReplaceChild("wing0",
				CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-3.0F, 13.0F, 0.0F));
		PartDefinition wing1 = partdefinition.addOrReplaceChild("wing1",
				CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offset(3.0F, 13.0F, 0.0F));*/

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition partdefinition1 = partdefinition.addOrReplaceChild(
				"head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F), PartPose.offset(0.0F, 15.0F, -4.0F)
		);
		partdefinition1.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F), PartPose.ZERO);
		partdefinition1.addOrReplaceChild("red_thing", CubeListBuilder.create().texOffs(14, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F), PartPose.ZERO);
		partdefinition.addOrReplaceChild(
				"body",
				CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F),
				PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
		);
		CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
		partdefinition.addOrReplaceChild("right_leg", cubelistbuilder, PartPose.offset(-2.0F, 19.0F, 1.0F));
		partdefinition.addOrReplaceChild("left_leg", cubelistbuilder, PartPose.offset(1.0F, 19.0F, 1.0F));
		partdefinition.addOrReplaceChild(
				"right_wing", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.offset(-4.0F, 13.0F, 0.0F)
		);
		partdefinition.addOrReplaceChild(
				"left_wing", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.offset(4.0F, 13.0F, 0.0F)
		);
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	/*@Override
	public void renderToBuffer(@NotNull PoseStack var1, @NotNull VertexConsumer var2, int var3, int var4) {
		body.render(var1, var2, var3, var4, -1);
		head.render(var1, var2, var3, var4, -1);
		leg0.render(var1, var2, var3, var4, -1);
		leg1.render(var1, var2, var3, var4, -1);
		wing0.render(var1, var2, var3, var4, -1);
		wing1.render(var1, var2, var3, var4, -1);
	}*/

	/*@Override
	public void setupAnim(ChickenRenderState state) {
		//this.resetPose();

		//this.head.xRot = state.headPitch * ((float) Math.PI / 180F);
		//this.head.yRot = state.headYaw() * ((float) Math.PI / 180F);

		//this.leg0.xRot = Mth.cos(state.walkAnimation() * 0.6662F) * 1.4F * state.walkSpeed();
		//this.leg1.xRot = Mth.cos(state.walkAnimation() * 0.6662F + (float)Math.PI) * 1.4F * state.walkSpeed();


		super.setupAnim(state);
		float f = (Mth.sin(state.flap) + 1.0F) * state.flapSpeed;
		this.head.xRot = state.xRot * (float) (Math.PI / 180.0);
		this.head.yRot = state.yRot * (float) (Math.PI / 180.0);
		float f1 = state.walkAnimationSpeed;
		float f2 = state.walkAnimationPos;
		this.leg0.xRot = Mth.cos(f2 * 0.6662F) * 1.4F * f1;
		this.leg1.xRot = Mth.cos(f2 * 0.6662F + (float) Math.PI) * 1.4F * f1;
		//this.rightWing.zRot = f;
		//this.leftWing.zRot = -f;
	}*/
	@Override
	public void setupAnim(ChickenRenderState state) {
		super.setupAnim(state);
		float f = (Mth.sin(state.flap) + 1.0F) * state.flapSpeed;
		this.head.xRot = state.xRot * (float) (Math.PI / 180.0);
		this.head.yRot = state.yRot * (float) (Math.PI / 180.0);
		float f1 = state.walkAnimationSpeed;
		float f2 = state.walkAnimationPos;
		this.rightLeg.xRot = Mth.cos(f2 * 0.6662F) * 1.4F * f1;
		this.leftLeg.xRot = Mth.cos(f2 * 0.6662F + (float) Math.PI) * 1.4F * f1;
		this.rightWing.zRot = f;
		this.leftWing.zRot = -f;
	}
}
