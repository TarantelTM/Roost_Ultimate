package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.tarantel.chickenroost.block.tile.TrainerTile;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.model.BakedGeoModel;
import software.bernie.geckolib.cache.model.GeoBone;
import software.bernie.geckolib.constant.DataTickets;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.base.PerBoneRender;
import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer;
import software.bernie.geckolib.util.RenderUtil;

import java.util.List;
import java.util.function.BiConsumer;

public class TrainerItemLayer<T extends GeoAnimatable, R extends GeoRenderState>
        extends BlockAndItemGeoLayer<T, Void, R> {

    public TrainerItemLayer(GeoRenderer<T, Void, R> renderer) {
        super(renderer);
    }

    @Override
    protected List<RenderData<R>> getRelevantBones(R renderState, BakedGeoModel model) {
        Item item = renderState.getOrDefaultGeckolibData(DataTickets.ITEM, Items.AIR);
        if (item == Items.AIR) return List.of();

        return List.of(
                new RenderData<>(
                        "bone",
                        ItemDisplayContext.FIXED,
                        (bone, state) -> Either.left(new ItemStack(item))
                )
        );
    }

    @Override
    public void addRenderData(T animatable, Void unused, R renderState, float partialTick) {
        // nichts nötig
    }

    // 🔥 HIER ist der entscheidende Punkt
    @Override
    protected void submitItemStackRender(
            PoseStack poseStack,
            GeoBone bone,
            ItemStack stack,
            ItemDisplayContext displayContext,
            R renderState,
            SubmitNodeCollector renderTasks,
            CameraRenderState cameraState,
            int packedLight,
            int packedOverlay,
            int renderColor
    ) {
        // 🧠 Bone-Transformation ist hier BEREITS aktiv!

        poseStack.pushPose();

        // ➕ FEINJUSTIERUNG (ohne Model anfassen!)
        poseStack.mulPose(Axis.XP.rotationDegrees(0));
        poseStack.mulPose(Axis.YP.rotationDegrees(0));
        poseStack.mulPose(Axis.ZP.rotationDegrees(0));
        poseStack.translate(0.0D, 0.5D, 0.0D);
        poseStack.scale(1.0f, 1.0f, 1.0f);

        super.submitItemStackRender(
                poseStack,
                bone,
                stack,
                displayContext,
                renderState,
                renderTasks,
                cameraState,
                packedLight,
                packedOverlay,
                renderColor
        );

        poseStack.popPose();
    }
}