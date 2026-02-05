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
import net.minecraft.world.phys.Vec3;
import net.tarantel.chickenroost.block.blocks.RoostBlock;
import net.tarantel.chickenroost.block.tile.RoostTile;
import org.jetbrains.annotations.Nullable;

public class RoostChickenRender
        implements BlockEntityRenderer<RoostTile, RoostRenderState> {

    public RoostChickenRender(BlockEntityRendererProvider.Context context) {}

    // ------------------------------------------------------------------
    // 1️⃣ Create render state
    // ------------------------------------------------------------------
    @Override
    public RoostRenderState createRenderState() {
        return new RoostRenderState();
    }

    // ------------------------------------------------------------------
    // 2️⃣ Extract ALL data from the BlockEntity (SAFE STAGE)
    // ------------------------------------------------------------------
    @Override
    public void extractRenderState(
            RoostTile tile,
            RoostRenderState state,
            float partialTick,
            Vec3 cameraPos,
            @Nullable net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay damage
    ) {
        BlockEntityRenderState.extractBase(tile, state, damage);

        state.blockState = tile.getBlockState();
        state.blockPos   = tile.getBlockPos();

        // IMPORTANT: copy, never keep reference
        state.renderStack = tile.getRenderStack().copy();
    }

    // ------------------------------------------------------------------
    // 3️⃣ Render ONLY from render state (NO WORLD ACCESS)
    // ------------------------------------------------------------------
    @Override
    public void submit(
            RoostRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            CameraRenderState camera
    ) {
        if (state.renderStack.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();

        ItemStackRenderState itemState = new ItemStackRenderState();
        mc.getItemModelResolver().updateForTopItem(
                itemState,
                state.renderStack,
                ItemDisplayContext.FIXED,
                mc.level,
                null,
                0
        );

        poseStack.pushPose();

        switch (state.blockState.getValue(RoostBlock.FACING)) {
            case NORTH -> poseStack.translate(0.5, 0.5, 0.6);
            case EAST -> {
                poseStack.translate(0.4, 0.5, 0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees(-90));
            }
            case SOUTH -> {
                poseStack.translate(0.5, 0.5, 0.4);
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
            }
            case WEST -> {
                poseStack.translate(0.6, 0.5, 0.5);
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
