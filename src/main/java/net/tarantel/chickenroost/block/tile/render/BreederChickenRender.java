package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.block.blocks.BreederBlock;
import net.tarantel.chickenroost.block.tile.BreederTile;
import org.jetbrains.annotations.NotNull;

public class BreederChickenRender implements BlockEntityRenderer<BreederTile> {
   public BreederChickenRender(Context context) {
   }

   public void render(
      BreederTile pBlockEntity,
      float pPartialTick,
      @NotNull PoseStack pPoseStack,
      @NotNull MultiBufferSource pBufferSource,
      int pPackedLight,
      int pPackedOverlay
   ) {
      ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
      ItemStack itemStack1 = pBlockEntity.getRenderStack1();
      ItemStack itemStack2 = pBlockEntity.getRenderStack2();
      ItemStack itemStack3 = pBlockEntity.getRenderStack3();
      pPoseStack.pushPose();
      pPoseStack.scale(0.7F, 0.7F, 0.7F);
      pPoseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
      switch ((Direction)pBlockEntity.getBlockState().getValue(BreederBlock.FACING)) {
         case NORTH:
            pPoseStack.translate(0.37F, 0.6F, 0.88F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
            break;
         case EAST:
            pPoseStack.translate(0.55F, 0.6F, 0.366F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
            break;
         case SOUTH:
            pPoseStack.translate(1.062F, 0.6F, 0.465F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
            break;
         case WEST:
            pPoseStack.translate(0.963F, 0.6F, 1.06F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
      }

      itemRenderer.renderStatic(
         itemStack2, ItemDisplayContext.FIXED, pPackedLight, OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 0
      );
      pPoseStack.popPose();
      pPoseStack.pushPose();
      pPoseStack.scale(0.7F, 0.7F, 0.7F);
      pPoseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
      switch ((Direction)pBlockEntity.getBlockState().getValue(BreederBlock.FACING)) {
         case NORTH:
            pPoseStack.translate(1.06F, 0.6F, 0.88F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
            break;
         case EAST:
            pPoseStack.translate(0.55F, 0.6F, 1.06F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
            break;
         case SOUTH:
            pPoseStack.translate(0.362F, 0.6F, 0.465F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
            break;
         case WEST:
            pPoseStack.translate(0.963F, 0.6F, 0.365F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
      }

      itemRenderer.renderStatic(
         itemStack1, ItemDisplayContext.FIXED, pPackedLight, OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 0
      );
      pPoseStack.popPose();
      pPoseStack.pushPose();
      pPoseStack.scale(0.5F, 0.5F, 0.5F);
      pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
      switch ((Direction)pBlockEntity.getBlockState().getValue(BreederBlock.FACING)) {
         case NORTH:
            pPoseStack.translate(1.0F, 0.5F, -0.4F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
            break;
         case EAST:
            pPoseStack.translate(1.5F, 1.0F, -0.4F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
            break;
         case SOUTH:
            pPoseStack.translate(1.0F, 1.5F, -0.4F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
            break;
         case WEST:
            pPoseStack.translate(0.5F, 1.0F, -0.4F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
      }

      itemRenderer.renderStatic(
         itemStack3, ItemDisplayContext.FIXED, pPackedLight, OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 0
      );
      pPoseStack.popPose();
   }
}
