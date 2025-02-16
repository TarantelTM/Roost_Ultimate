
package net.tarantel.chickenroost.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.client.model.Modelchicken;
import net.tarantel.chickenroost.entity.BaseChickenEntity;

public class BaseChickenRenderer extends MobRenderer<BaseChickenEntity, Modelchicken<BaseChickenEntity>> {
    public String texture;
    public BaseChickenRenderer(EntityRendererProvider.Context context, String texture) {
        super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
        this.texture = texture;
    }

    @Override
    public ResourceLocation getTextureLocation(BaseChickenEntity entity) {
        return new ResourceLocation("chicken_roost:textures/entities/" + this.texture + ".png");
    }
}
