package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.tarantel.chickenroost.block.blocks.Breeder_Block;
import net.tarantel.chickenroost.block.tile.Breeder_Tile;

public class BreederChickenRender implements BlockEntityRenderer<Breeder_Tile> {
    public BreederChickenRender(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(Breeder_Tile pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Level level = Minecraft.getInstance().level;
        ItemStack itemStack1 = pBlockEntity.getRenderStack1();
        ItemStack itemStack2 = pBlockEntity.getRenderStack2();
        ItemStack itemStack3 = pBlockEntity.getRenderStack3();
        PoseStack poseStack1 = pPoseStack;
        PoseStack poseStack2 = pPoseStack;
        PoseStack poseStack3 = pPoseStack;

        poseStack2.pushPose();

        poseStack2.scale(0.7f, 0.7f, 0.7f);
        poseStack2.mulPose(Axis.XP.rotationDegrees(0));

        switch (pBlockEntity.getBlockState().getValue(Breeder_Block.FACING)) {
            case NORTH -> {
                poseStack2.translate(0.37f, 0.6f, 0.88f);
                poseStack2.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack2.mulPose(Axis.YP.rotationDegrees(0));
            }
            case EAST -> {
                poseStack2.translate(0.55f, 0.6f, 0.366f);
                poseStack2.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack2.mulPose(Axis.YP.rotationDegrees(-90));
            }
            case SOUTH -> {
                poseStack2.translate(1.062f, 0.6f, 0.465f);
                poseStack2.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack2.mulPose(Axis.YP.rotationDegrees(-180));
            }
            case WEST -> {
                poseStack2.translate(0.963f, 0.6f, 1.06f);
                poseStack2.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack2.mulPose(Axis.XP.rotationDegrees(0));
                poseStack2.mulPose(Axis.YP.rotationDegrees(+90));
            }
        }

        itemRenderer.renderStatic(itemStack2, ItemDisplayContext.FIXED, pPackedLight,
                OverlayTexture.NO_OVERLAY, poseStack2, pBufferSource, pBlockEntity.getLevel(), 0);
        poseStack2.popPose();

        poseStack1.pushPose();
        poseStack1.scale(0.7f, 0.7f, 0.7f);
        poseStack1.mulPose(Axis.XP.rotationDegrees(0));

        switch (pBlockEntity.getBlockState().getValue(Breeder_Block.FACING)) {
            case NORTH -> {
                poseStack1.translate(1.06f, 0.6f, 0.88f);
                poseStack1.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack1.mulPose(Axis.YP.rotationDegrees(0));
            }
            case EAST -> {
                poseStack1.translate(0.55f, 0.6f, 1.06f);
                poseStack1.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack1.mulPose(Axis.YP.rotationDegrees(-90));
            }
            case SOUTH -> {
                poseStack1.translate(0.362f, 0.6f, 0.465f);
                poseStack1.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack1.mulPose(Axis.YP.rotationDegrees(-180));
            }
            case WEST -> {
                poseStack1.translate(0.963f, 0.6f, 0.365f);
                poseStack1.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack1.mulPose(Axis.XP.rotationDegrees(0));
                poseStack1.mulPose(Axis.YP.rotationDegrees(+90));
            }
        }

        itemRenderer.renderStatic(itemStack1, ItemDisplayContext.FIXED, pPackedLight,
                OverlayTexture.NO_OVERLAY, poseStack1, pBufferSource, pBlockEntity.getLevel(), 0);
        poseStack1.popPose();

        poseStack3.pushPose();
        poseStack3.scale(0.5f, 0.5f, 0.5f);
        poseStack3.mulPose(Axis.XP.rotationDegrees(90));

        switch (pBlockEntity.getBlockState().getValue(Breeder_Block.FACING)) {
            case NORTH -> {
                poseStack3.translate(1.0f, 0.5f, -0.4f);
                poseStack3.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack3.mulPose(Axis.YP.rotationDegrees(0));
            }
            case EAST -> {
                poseStack3.translate(1.5f, 1.0f, -0.4f);
                poseStack3.mulPose(Axis.ZP.rotationDegrees(90));
                poseStack3.mulPose(Axis.YP.rotationDegrees(0));
            }
            case SOUTH -> {
                poseStack3.translate(1.0f, 1.5f, -0.4f);
                poseStack3.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack3.mulPose(Axis.YP.rotationDegrees(-180));
            }
            case WEST -> {
                poseStack3.translate(0.5f, 1.0f, -0.4f);
                poseStack3.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack3.mulPose(Axis.XP.rotationDegrees(0));
                poseStack3.mulPose(Axis.YP.rotationDegrees(0));
            }
        }

        itemRenderer.renderStatic(itemStack3, ItemDisplayContext.FIXED, pPackedLight,
                OverlayTexture.NO_OVERLAY, poseStack3, pBufferSource, pBlockEntity.getLevel(), 0);
        poseStack3.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}