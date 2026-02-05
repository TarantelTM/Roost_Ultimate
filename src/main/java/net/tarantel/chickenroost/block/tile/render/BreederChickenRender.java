package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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
import net.tarantel.chickenroost.block.blocks.BreederBlock;
import net.tarantel.chickenroost.block.tile.BreederTile;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class BreederChickenRender
        implements BlockEntityRenderer<@NotNull BreederTile, @NotNull BlockEntityRenderState> {

    public BreederChickenRender(BlockEntityRendererProvider.Context context) {}

    @Override
    public BlockEntityRenderState createRenderState() {
        return new BlockEntityRenderState();
    }

    @Override
    public void extractRenderState(
            BreederTile tile,
            BlockEntityRenderState state,
            float partialTick,
            @NotNull Vec3 cameraPos,
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

        BreederTile tile = (BreederTile) mc.level.getBlockEntity(state.blockPos);
        if (tile == null) return;

        renderChicken(
                tile.getRenderStack2(),
                state,
                poseStack,
                collector,
                mc,
                0.7f,
                ChickenSlot.LEFT
        );

        renderChicken(
                tile.getRenderStack1(),
                state,
                poseStack,
                collector,
                mc,
                0.7f,
                ChickenSlot.RIGHT
        );

        renderSeed(
                tile.getRenderStack3(),
                state,
                poseStack,
                collector,
                mc,
                0.5f
        );
    }


    private void renderChicken(
            ItemStack stack,
            BlockEntityRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            Minecraft mc,
            float scale,
            ChickenSlot slot
    ) {
        if (stack.isEmpty()) return;

        ItemStackRenderState itemState = new ItemStackRenderState();
        mc.getItemModelResolver().updateForTopItem(
                itemState, stack, ItemDisplayContext.FIXED, mc.level, null, 0
        );

        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);

        switch (state.blockState.getValue(BreederBlock.FACING)) {
            case NORTH -> poseStack.translate(
                    slot == ChickenSlot.LEFT ? 0.37f : 1.06f,
                    0.6f,
                    0.88f
            );
            case EAST -> poseStack.translate(
                    0.55f,
                    0.6f,
                    slot == ChickenSlot.LEFT ? 0.366f : 1.06f
            );
            case SOUTH -> poseStack.translate(
                    slot == ChickenSlot.LEFT ? 1.062f : 0.362f,
                    0.6f,
                    0.465f
            );
            case WEST -> poseStack.translate(
                    0.963f,
                    0.6f,
                    slot == ChickenSlot.LEFT ? 1.06f : 0.365f
            );
        }

        switch (state.blockState.getValue(BreederBlock.FACING)) {
            case EAST  -> poseStack.mulPose(Axis.YP.rotationDegrees(-90));
            case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(-180));
            case WEST  -> poseStack.mulPose(Axis.YP.rotationDegrees(90));
            default -> {}
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

    private enum ChickenSlot {
        LEFT, RIGHT
    }

    private void renderSeed(
            ItemStack stack,
            BlockEntityRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            Minecraft mc,
            float scale
    ) {
        if (stack.isEmpty()) return;

        ItemStackRenderState itemState = new ItemStackRenderState();
        mc.getItemModelResolver().updateForTopItem(
                itemState, stack, ItemDisplayContext.FIXED, mc.level, null, 0
        );

        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        switch (state.blockState.getValue(BreederBlock.FACING)) {
            case NORTH -> poseStack.translate(1.0f, 0.5f, -0.4f);
            case EAST -> {
                poseStack.translate(1.5f, 1.0f, -0.4f);
                poseStack.mulPose(Axis.ZP.rotationDegrees(90));
            }
            case SOUTH -> {
                poseStack.translate(1.0f, 1.5f, -0.4f);
                poseStack.mulPose(Axis.YP.rotationDegrees(-180));
            }
            case WEST -> poseStack.translate(0.5f, 1.0f, -0.4f);
        }

        // 🔥 HIER: freie Extra-Rotation möglich
        // poseStack.mulPose(Axis.YP.rotationDegrees(time));

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
