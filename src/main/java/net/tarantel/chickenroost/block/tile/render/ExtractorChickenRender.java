package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.tarantel.chickenroost.block.blocks.RoostBlock;
import net.tarantel.chickenroost.block.tile.SoulExtractorTile;
import org.jspecify.annotations.Nullable;

public class ExtractorChickenRender
        implements BlockEntityRenderer<SoulExtractorTile, BlockEntityRenderState> {

    public ExtractorChickenRender(BlockEntityRendererProvider.Context context) {}

    @Override
    public BlockEntityRenderState createRenderState() {
        return new BlockEntityRenderState();
    }

    @Override
    public void extractRenderState(
            SoulExtractorTile tile,
            BlockEntityRenderState state,
            float partialTick,
            Vec3 cameraPos,
            net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay damage
    ) {
        BlockEntityRenderState.extractBase(tile, state, damage);
    }

    @Override
    public void submit(
            BlockEntityRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            CameraRenderState camera
    ) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        SoulExtractorTile tile =
                (SoulExtractorTile) mc.level.getBlockEntity(state.blockPos);
        if (tile == null) return;

        ItemStack stack = tile.getRenderStack();
        if (stack.isEmpty()) return;

        ItemStackRenderState itemState = new ItemStackRenderState();
        mc.getItemModelResolver().updateForTopItem(
                itemState,
                stack,
                ItemDisplayContext.FIXED,
                mc.level,
                null,
                0
        );

        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);

        switch (state.blockState.getValue(RoostBlock.FACING)) {
            case NORTH -> poseStack.translate(0.5, 0.35, 0.6);
            case EAST -> {
                poseStack.translate(0.4, 0.35, 0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees(-90));
            }
            case SOUTH -> {
                poseStack.translate(0.5, 0.35, 0.4);
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
            }
            case WEST -> {
                poseStack.translate(0.6, 0.35, 0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
            }
        }

        itemState.submit(
                poseStack,
                collector,
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0
        );

        poseStack.popPose();
    }
}
