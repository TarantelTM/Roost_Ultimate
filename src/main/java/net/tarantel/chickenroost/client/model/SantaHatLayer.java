//package net.tarantel.chickenroost.client.model;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.math.Axis;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.model.geom.ModelLayerLocation;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.SubmitNodeCollector;
//import net.minecraft.client.renderer.entity.RenderLayerParent;
//import net.minecraft.client.renderer.entity.layers.RenderLayer;
//import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
//import net.minecraft.client.renderer.item.ItemStackRenderState;
//import net.minecraft.client.renderer.rendertype.RenderTypes;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//import net.minecraft.resources.Identifier;
//import net.minecraft.world.inventory.InventoryMenu;
//import net.minecraft.world.item.ItemDisplayContext;
//import net.minecraft.world.item.ItemStack;
//import net.tarantel.chickenroost.entity.BaseChickenEntity;
//
//public class SantaHatLayer extends RenderLayer<LivingEntityRenderState, Modelchicken> {
//
//    private static final ModelLayerLocation HAT_MODEL =
//            new ModelLayerLocation(Identifier.fromNamespaceAndPath("chicken_roost", "xmashat"), "inventory");
//
//
//    public SantaHatLayer(RenderLayerParent<T, Modelchicken<T>> parent) {
//        super(parent);
//    }
//
//    /*@Override
//    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, T t, float v, float v1) {
//
//    }*/
//
//    @Override
//    public void submit(
//            PoseStack poseStack,
//            SubmitNodeCollector collector,
//            int packedLight,
//            LivingEntityRenderState state,
//            float partialTick,
//            float ageInTicks
//    ) {
//        if (state.isInvisible) return;
//
//        poseStack.pushPose();
//
//        // Kopf-Transform vom Parent-Model
//        this.getParentModel().head.translateAndRotate(poseStack);
//
//        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
//        poseStack.translate(-0.21D, 0.35D, -0.2D);
//        poseStack.scale(0.43F, 0.43F, 0.43F);
//
//        collector.submitItem(
//                poseStack,
//                ItemDisplayContext.HEAD,   // oder FIXED/THIRD_PERSON etc.
//                packedLight,
//                OverlayTexture.NO_OVERLAY,
//                -1,
//                null,                      // tint array (oder int[] für multiple tints)
//                quadsList,                 // baked quads
//                RenderType.entityCutout(InventoryMenu.BLOCK_ATLAS),
//                ItemStackRenderState.FoilType.NONE
//        );
//
//        poseStack.popPose();
//    }
//}
//