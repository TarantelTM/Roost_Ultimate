package net.tarantel.chickenroost.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.entity.BaseChickenEntity;

public class SantaHatLayer<T extends BaseChickenEntity>
        extends RenderLayer<T, Modelchicken<T>> {

    private static final ModelResourceLocation HAT_MODEL =
            new ModelResourceLocation(new ResourceLocation("chicken_roost", "xmashat"), "inventory");


    public SantaHatLayer(RenderLayerParent<T, Modelchicken<T>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       T entity, float limbSwing, float limbSwingAmount, float partialTick,
                       float ageInTicks, float netHeadYaw, float headPitch) {

        poseStack.pushPose();

        // an den Kopf hängen
        this.getParentModel().head.translateAndRotate(poseStack);

        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        // Position/Größe anpassen
        poseStack.translate(-0.21D, 0.35D, -0.2D);
        poseStack.scale(0.43F, 0.43F, 0.43F);

        Minecraft mc = Minecraft.getInstance();
        BakedModel baked = mc.getModelManager().getModel(HAT_MODEL);

        var vc = buffer.getBuffer(RenderType.entityCutout(InventoryMenu.BLOCK_ATLAS));

        mc.getItemRenderer().renderModelLists(
                baked,
                ItemStack.EMPTY,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                vc
        );

        poseStack.popPose();
    }
}
