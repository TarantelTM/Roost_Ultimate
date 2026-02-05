package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.tarantel.chickenroost.block.tile.TrainerTile;

public class TrainerChickenOverlayRenderer
        implements BlockEntityRenderer<TrainerTile, BlockEntityRenderState> {

    public TrainerChickenOverlayRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public BlockEntityRenderState createRenderState() {
        return new BlockEntityRenderState();
    }

    @Override
    public void extractRenderState(
            TrainerTile tile,
            BlockEntityRenderState state,
            float partialTick,
            Vec3 cameraPos,
            ModelFeatureRenderer.CrumblingOverlay damage
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

        TrainerTile tile =
                (TrainerTile) mc.level.getBlockEntity(state.blockPos);
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
        poseStack.scale(0.75f, 0.75f, 0.75f);
        poseStack.translate(0.5, 0.35, 0.5);

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
