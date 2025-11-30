package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.block.tile.SoulBreederTile;
import net.tarantel.chickenroost.block.blocks.model.AnimatedSoulBreederModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import javax.annotation.Nullable;

public class AnimatedSoulBreederRenderer extends GeoBlockRenderer<SoulBreederTile> {
    public AnimatedSoulBreederRenderer(BlockEntityRendererProvider.Context context) {
        super(new AnimatedSoulBreederModel());
        this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {

            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, SoulBreederTile animatable) {
                ItemStack itemStack = animatable.getRenderStack();
                return switch (bone.getName()) {
                    case "bone9" -> new ItemStack(itemStack.getItem());
                    default -> null;
                };
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack,
                                                                  SoulBreederTile animatable) {
                return switch (bone.getName()) {
                    default -> ItemDisplayContext.FIXED;
                };
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
                                              SoulBreederTile animatable, MultiBufferSource bufferSource, float partialTick, int packedLight,
                                              int packedOverlay) {
                poseStack.mulPose(Axis.XP.rotationDegrees(0));
                poseStack.mulPose(Axis.YP.rotationDegrees(0));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack.translate(0.0D, 0.2D, 0.0D);
                poseStack.scale(1.0f, 1.0f, 1.0f);
                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight,
                        packedOverlay);
            }
        });
        this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {

            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, SoulBreederTile animatable) {
                ItemStack itemStack2 = animatable.getRenderStackInput();
                if(itemStack2.getCount() == 1) {
                    return switch (bone.getName()) {
                        case "bone7" -> new ItemStack(itemStack2.getItem());
                        default -> null;
                    };
                }
                if(itemStack2.getCount() == 2) {
                    return switch (bone.getName()) {
                        case "bone7", "bone6" -> new ItemStack(itemStack2.getItem());
                        default -> null;
                    };
                }
                if(itemStack2.getCount() == 3) {
                    return switch (bone.getName()) {
                        case "bone7", "bone6", "bone5" -> new ItemStack(itemStack2.getItem());
                        default -> null;
                    };
                }
                if(itemStack2.getCount() == 4) {
                    return switch (bone.getName()) {
                        case "bone7", "bone6", "bone8", "bone5" -> new ItemStack(itemStack2.getItem());
                        default -> null;
                    };
                }
                if(itemStack2.getCount() == 5) {
                    return switch (bone.getName()) {
                        case "bone7", "bone6", "bone5", "bone8", "bone36" -> new ItemStack(itemStack2.getItem());
                        default -> null;
                    };
                }
                if(itemStack2.getCount() == 6) {
                    return switch (bone.getName()) {
                        case "bone7", "bone6", "bone5", "bone8", "bone36", "bone26" -> new ItemStack(itemStack2.getItem());
                        default -> null;
                    };
                }
                if(itemStack2.getCount() == 7) {
                    return switch (bone.getName()) {
                        case "bone7", "bone6", "bone5", "bone36", "bone26", "bone8", "bone23" -> new ItemStack(itemStack2.getItem());
                        default -> null;
                    };
                }
                if(itemStack2.getCount() == 8) {
                    return switch (bone.getName()) {
                        case "bone7", "bone6", "bone8", "bone36", "bone26", "bone23", "bone5", "bone14" -> new ItemStack(itemStack2.getItem());
                        default -> null;
                    };
                }
                else{
                    return switch (bone.getName()) {
                        case "bone7", "bone6", "bone8", "bone36", "bone26", "bone23", "bone14", "bone5" -> new ItemStack(itemStack2.getItem());
                        default -> null;
                    };
                }

            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack,
                                                                  SoulBreederTile animatable) {
                bone.getName();
                return ItemDisplayContext.FIXED;
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
                                              SoulBreederTile animatable, MultiBufferSource bufferSource, float partialTick, int packedLight,
                                              int packedOverlay) {
                poseStack.mulPose(Axis.XP.rotationDegrees(0));
                poseStack.mulPose(Axis.YP.rotationDegrees(0));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack.translate(0.0D, 0.2D, 0.0D);
                poseStack.scale(0.3f, 0.3f, 0.3f);

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight,
                        packedOverlay);
            }
        });
    }

}