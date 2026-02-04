package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.block.blocks.model.AnimatedTrainerModel;
import net.tarantel.chickenroost.block.tile.TrainerTile;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class AnimatedTrainerRenderer extends GeoBlockRenderer<TrainerTile> {
   public AnimatedTrainerRenderer(Context context) {
      super(new AnimatedTrainerModel());
      this.addRenderLayer(
         new BlockAndItemGeoLayer<TrainerTile>(this) {
            @Nullable
            protected ItemStack getStackForBone(GeoBone bone, TrainerTile animatable) {
               ItemStack itemStack = animatable.getRenderStack();
               String var4 = bone.getName();
               byte var5 = -1;
               switch (var4.hashCode()) {
                  case 3029700:
                     if (var4.equals("bone")) {
                        var5 = 0;
                     }
                  default:
                     return switch (var5) {
                        case 0 -> new ItemStack(itemStack.getItem());
                        default -> null;
                     };
               }
            }

            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, TrainerTile animatable) {
               String var4 = bone.getName();
               byte var5 = -1;
               var4.hashCode();
               switch (var5) {
                  default:
                     return ItemDisplayContext.FIXED;
               }
            }

            protected void renderStackForBone(
               PoseStack poseStack,
               GeoBone bone,
               ItemStack stack,
               TrainerTile animatable,
               MultiBufferSource bufferSource,
               float partialTick,
               int packedLight,
               int packedOverlay
            ) {
               poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
               poseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
               poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
               poseStack.translate(0.0, 0.4, 0.0);
               poseStack.scale(1.0F, 1.0F, 1.0F);
               super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
         }
      );
   }
}
