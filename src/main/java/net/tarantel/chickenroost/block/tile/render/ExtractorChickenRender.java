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
import net.tarantel.chickenroost.block.blocks.RoostBlock;
import net.tarantel.chickenroost.block.tile.SoulExtractorTile;
import org.jetbrains.annotations.NotNull;

public class ExtractorChickenRender implements BlockEntityRenderer<SoulExtractorTile> {
   public ExtractorChickenRender(Context context) {
   }

   public void render(
      SoulExtractorTile pBlockEntity, float pPartialTick, PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay
   ) {
      ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
      ItemStack itemStack = pBlockEntity.getRenderStack();
      pPoseStack.pushPose();
      pPoseStack.scale(1.0F, 1.0F, 1.0F);
      pPoseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
      switch ((Direction)pBlockEntity.getBlockState().getValue(RoostBlock.FACING)) {
         case NORTH:
            pPoseStack.translate(0.5F, 0.35F, 0.6F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
            break;
         case EAST:
            pPoseStack.translate(0.4F, 0.35F, 0.5F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
            break;
         case SOUTH:
            pPoseStack.translate(0.5F, 0.35F, 0.4F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
            break;
         case WEST:
            pPoseStack.translate(0.6F, 0.35F, 0.5F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
      }

      itemRenderer.renderStatic(
         itemStack, ItemDisplayContext.FIXED, pPackedLight, OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 0
      );
      pPoseStack.popPose();
   }
}
