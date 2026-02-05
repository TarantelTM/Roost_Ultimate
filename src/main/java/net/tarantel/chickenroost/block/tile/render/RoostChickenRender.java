package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.block.blocks.RoostBlock;
import net.tarantel.chickenroost.block.tile.RoostTile;
import org.jetbrains.annotations.NotNull;

public class RoostChickenRender implements BlockEntityRenderer<RoostTile> {
    public RoostChickenRender(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(RoostTile pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pBlockEntity.getRenderStack();
        pPoseStack.pushPose();
        pPoseStack.scale(1.0f, 1.0f, 1.0f);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(0));

        switch (pBlockEntity.getBlockState().getValue(RoostBlock.FACING)) {
            case NORTH -> {
                pPoseStack.translate(0.5f, 0.5f, 0.6f);
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(0));
                pPoseStack.mulPose(Axis.YP.rotationDegrees(0));
            }
            case EAST -> {
                pPoseStack.translate(0.4f, 0.5f, 0.5f);
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(0));
                pPoseStack.mulPose(Axis.YP.rotationDegrees(-90));
            }
            case SOUTH -> {
                pPoseStack.translate(0.5f, 0.5f, 0.4f);
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(0));
                pPoseStack.mulPose(Axis.YP.rotationDegrees(-180));
            }
            case WEST -> {
                pPoseStack.translate(0.6f, 0.5f, 0.5f);
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(0));
                pPoseStack.mulPose(Axis.XP.rotationDegrees(0));
                pPoseStack.mulPose(Axis.YP.rotationDegrees(+90));
            }
        }

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, pPackedLight,
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 0);
        pPoseStack.popPose();
    }
}