package net.tarantel.chickenroost.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.6.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class NewModelChicken<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier("chicken_roost", "newmodelchicken"), "main");
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart left_wing;
    private final ModelPart right_wing;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public NewModelChicken(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.left_wing = root.getChild("left_wing");
        this.right_wing = root.getChild("right_wing");
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
    }

    public static TexturedModelData createBodyLayer() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData head = partdefinition.addChild("head",
                ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -15.0F, -6.0F, 4.0F, 6.0F, 3.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData bill = head.addChild("bill",
                ModelPartBuilder.create().uv(14, 0).cuboid(-2.0F, -13.0F, -8.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData chin = bill.addChild("chin",
                ModelPartBuilder.create().uv(14, 4).cuboid(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new
                        Dilation(0.0F)),
                ModelTransform.pivot(0.0F, -9.0F, -4.0F));

        ModelPartData accesories = head.addChild("accesories",
                ModelPartBuilder.create().uv(24, 3).cuboid(0.0F, -20.0F, -6.0F, 0.0F, 10.0F, 12.0F, new
                        Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = partdefinition.addChild("body",
                ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 45.0F, 30.0F));

        ModelPartData body_r1 = body.addChild("body_r1",
                ModelPartBuilder.create().uv(0, 9).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -29.0F, -30.0F, 1.5708F, 0.0F, 0.0F));

        ModelPartData left_wing = partdefinition.addChild("left_wing",
                ModelPartBuilder.create().uv(18, 5).cuboid(3.0F, -11.0F, -3.0F, 1.0F, 4.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData right_wing = partdefinition.addChild("right_wing",
                ModelPartBuilder.create().uv(18, 5).cuboid(-4.0F, -11.0F, -3.0F, 1.0F, 4.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData left_leg = partdefinition.addChild("left_leg",
                ModelPartBuilder.create().uv(26, 0).cuboid(0.0F, -5.0F, -2.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData right_leg = partdefinition.addChild("right_leg",
                ModelPartBuilder.create().uv(26, 0).mirrored().cuboid(-3.0F, -5.0F, -2.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 64, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw / (180F / (float) Math.PI);
        this.head.pitch = headPitch / (180F / (float) Math.PI);
        this.right_leg.pitch = MathHelper.cos(limbAngle * 1.0F) * 1.0F * limbDistance;
        this.left_leg.pitch = MathHelper.cos(limbAngle * 1.0F) * -1.0F * limbDistance;
        this.left_wing.roll = MathHelper.cos(limbAngle * 0.6662F) * limbDistance;
        this.right_wing.roll = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * limbDistance;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        left_wing.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        right_wing.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        left_leg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        right_leg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}