package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.block.blocks.model.AnimatedTrainerModel;
import net.tarantel.chickenroost.block.tile.Trainer_Tile;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import javax.annotation.Nullable;

public class AnimatedTrainerRenderer extends GeoBlockRenderer<Trainer_Tile> {
    public AnimatedTrainerRenderer(BlockEntityRendererProvider.Context context) {
        super(new AnimatedTrainerModel());
        this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {

            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, Trainer_Tile animatable) {
                ItemStack itemStack = animatable.getRenderStack();
                return switch (bone.getName()) {
                    case "bone" -> new ItemStack(itemStack.getItem());
                    default -> null;
                };
            }


            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack,
                                                                  Trainer_Tile animatable) {
                return switch (bone.getName()) {
                    default -> ItemDisplayContext.FIXED;
                };
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
                                              Trainer_Tile animatable, MultiBufferSource bufferSource, float partialTick, int packedLight,
                                              int packedOverlay) {
                poseStack.mulPose(Axis.XP.rotationDegrees(0));
                poseStack.mulPose(Axis.YP.rotationDegrees(0));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack.translate(0.0D, 0.4D, 0.0D);
                poseStack.scale(1.0f, 1.0f, 1.0f);
                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight,
                        packedOverlay);
            }
        });

    }
}