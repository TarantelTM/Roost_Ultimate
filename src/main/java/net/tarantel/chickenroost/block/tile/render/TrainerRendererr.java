package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.tarantel.chickenroost.block.Breeder_Block;
import net.tarantel.chickenroost.block.model.AnimatedTrainerModel;
import net.tarantel.chickenroost.block.tile.Trainer_Tile;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class TrainerRendererr extends GeoBlockRenderer<Trainer_Tile> {
    public TrainerRendererr(BlockEntityRendererProvider.Context renderManager) {
        super(renderManager, new AnimatedTrainerModel());
        //super(new AnimatedSoulBreederModel());


    }
    @Override
    public RenderType getRenderType(Trainer_Tile animatable, float partialTick, PoseStack poseStack,
                                    MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
    @Override
    public void render(Trainer_Tile tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        //Soul_Breeder_Tile newtile = (Soul_Breeder_Tile) tile;
        ItemStack itemStack = tile.getRenderStack();
        poseStack.pushPose();
        //poseStack.mulPose(Vector3f.XP.rotationDegrees(0));
        //poseStack.mulPose(Vector3f.YP.rotationDegrees(0));
        //poseStack.mulPose(Vector3f.ZP.rotationDegrees(0));
        //poseStack.translate(0.5f, 1.5f, 0.6f);
        //stack.translate(0.34D, 0.1D, 1.0D);
        poseStack.scale(1.0f, 1.0f, 1.0f);


        switch (tile.getBlockState().getValue(Breeder_Block.FACING)) {
            case NORTH -> {
                poseStack.translate(0.5f, 0.47f, 0.5f);
                poseStack.mulPose(new Quaternion(0, 0, 0, true));
            }
            case EAST -> {
                poseStack.translate(0.5f, 0.47f, 0.5f);
                poseStack.mulPose(new Quaternion(0, -90, 0, true));
            }
            case SOUTH -> {
                poseStack.translate(0.5f, 0.47f, 0.5f);
                poseStack.mulPose(new Quaternion(0, -180, 0, true));
            }
            case WEST -> {
                poseStack.translate(0.5f, 0.47f, 0.5f);
                poseStack.mulPose(new Quaternion(0, +90, 0, true));
            }
        }
            /*Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemTransforms.TransformType.FIXED,
                    packedLightIn, packedOverlayIn, stack, this.rtb, 0);*/
            /*itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.FIXED, packedLightIn,
                    OverlayTexture.NO_OVERLAY, stack, (MultiBufferSource) bufferIn, 0);*/

        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.FIXED, packedLight,
                OverlayTexture.NO_OVERLAY, poseStack, bufferSource, 0);
        poseStack.popPose();


        super.render(tile, partialTick, poseStack, bufferSource, packedLight);
    }
    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}