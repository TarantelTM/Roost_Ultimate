package net.tarantel.chickenroost.block.tile.render;

        import com.mojang.blaze3d.vertex.PoseStack;
        import com.mojang.math.Axis;
        import net.minecraft.client.renderer.MultiBufferSource;
        import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
        import net.minecraft.world.item.ItemDisplayContext;
        import net.minecraft.world.item.ItemStack;
        import net.tarantel.chickenroost.block.tile.Soul_Breeder_Tile;
        import net.tarantel.chickenroost.client.model.AnimatedSoulBreederModel;
        import software.bernie.geckolib.cache.object.GeoBone;
        import software.bernie.geckolib.renderer.GeoBlockRenderer;
        import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

        import javax.annotation.Nullable;

public class AnimatedSoulRenderExtra extends GeoBlockRenderer<Soul_Breeder_Tile> {
    public AnimatedSoulRenderExtra(BlockEntityRendererProvider.Context context) {
        super(new AnimatedSoulBreederModel());
        this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {

            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, Soul_Breeder_Tile animatable) {
                ItemStack itemStack2 = animatable.getRenderStackInput();
                return switch (bone.getName()) {
                    case "bone" -> new ItemStack(itemStack2.getItem());
                    case "bone2" -> new ItemStack(itemStack2.getItem());
                    case "bone4" -> new ItemStack(itemStack2.getItem());
                    case "bone3" -> new ItemStack(itemStack2.getItem());
                    default -> null;
                };
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack,
                                                                  Soul_Breeder_Tile animatable) {
                return switch (bone.getName()) {
                    default -> ItemDisplayContext.FIXED;
                };
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
                                              Soul_Breeder_Tile animatable, MultiBufferSource bufferSource, float partialTick, int packedLight,
                                              int packedOverlay) {
                poseStack.mulPose(Axis.XP.rotationDegrees(0));
                poseStack.mulPose(Axis.YP.rotationDegrees(0));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack.translate(0.0D, 0.02D, 0.0D);
                poseStack.scale(0.3f, 0.3f, 0.3f);
                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight,
                        packedOverlay);
            }
        });

    }


}