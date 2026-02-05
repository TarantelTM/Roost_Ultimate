package net.tarantel.chickenroost.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.ChickenRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;

import net.tarantel.chickenroost.client.model.Modelchicken;
import net.tarantel.chickenroost.entity.BaseChickenEntity;
import org.jetbrains.annotations.NotNull;

public class BaseChickenRenderer extends MobRenderer<BaseChickenEntity, ChickenRenderState, Modelchicken<BaseChickenEntity>> {
    public String texture;

    public BaseChickenRenderer(EntityRendererProvider.Context context, String texture) {
        super(context, new Modelchicken<>(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
        this.texture = texture;

        /*if (LocalDate.now().getMonth().equals(Month.DECEMBER)) {
            this.addLayer(new SantaHatLayer<>(this));
        }*/

    }

    @Override
    public @NotNull Identifier getTextureLocation(ChickenRenderState entity) {
        return Identifier.parse("chicken_roost:textures/entities/" + this.texture + ".png");
    }

    @Override
    public void submit(ChickenRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (renderState.isBaby) {
            float scale = 0.5F;
            poseStack.scale(scale, scale, scale);
            this.shadowRadius = 0.15F;
        } else {
            super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
        }
    }

    @Override
    public ChickenRenderState createRenderState() {
        return new ChickenRenderState();
    }
}