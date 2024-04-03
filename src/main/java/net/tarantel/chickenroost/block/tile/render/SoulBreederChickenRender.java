//package net.tarantel.chickenroost.block.tile.render;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.math.Quaternion;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.LightTexture;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.block.model.ItemTransforms;
//import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
//import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
//import net.minecraft.client.renderer.entity.ItemRenderer;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.LightLayer;
//import net.tarantel.chickenroost.block.Soul_Breeder_Block;
//import net.tarantel.chickenroost.block.tile.Soul_Breeder_Tile;
//
//public class SoulBreederChickenRender implements BlockEntityRenderer<Soul_Breeder_Tile> {
//    public SoulBreederChickenRender(BlockEntityRendererProvider.Context context) {
//
//    }
//
//    @Override
//    public void render(Soul_Breeder_Tile pBlockEntity, float pPartialTick, PoseStack pPoseStack,
//                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
//        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
//        ItemStack itemStack = pBlockEntity.getRenderStack();
//        pPoseStack.pushPose();
//        //pPoseStack.translate(0.5f, 0.65f, 0.5f);
//        pPoseStack.scale(1.0f, 1.0f, 1.0f);
//        //pPoseStack.mulPose(Axis.XP.rotationDegrees(0));
//
//        switch (pBlockEntity.getBlockState().getValue(Soul_Breeder_Block.FACING)) {
//            case NORTH -> {
//                pPoseStack.translate(0.5f, 0.5f, 0.6f);
//                pPoseStack.mulPose(new Quaternion(0, 0, 0, true));
//            }
//            case EAST -> {
//                pPoseStack.translate(0.4f, 0.5f, 0.5f);
//                pPoseStack.mulPose(new Quaternion(0, -90, 0, true));
//            }
//            case SOUTH -> {
//                pPoseStack.translate(0.5f, 0.5f, 0.4f);
//                pPoseStack.mulPose(new Quaternion(0, -180, 0, true));
//            }
//            case WEST -> {
//                pPoseStack.translate(0.6f, 0.5f, 0.5f);
//                pPoseStack.mulPose(new Quaternion(0, +90, 0, true));
//            }
//        }
//
//        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.FIXED, pPackedLight,
//                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 0);
//        pPoseStack.popPose();
//    }
//
//    private int getLightLevel(Level level, BlockPos pos) {
//        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
//        int sLight = level.getBrightness(LightLayer.SKY, pos);
//        return LightTexture.pack(bLight, sLight);
//    }
//}