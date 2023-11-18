package net.tarantel.chickenroost.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.client.model.Modelchicken;


public class Model_c_apple  extends MobEntityRenderer<ChickenBaseEntity, Modelchicken<ChickenBaseEntity>> {
    public Model_c_apple(EntityRendererFactory.Context context) {
        super(context, new Modelchicken(context.getPart(Modelchicken.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(ChickenBaseEntity entity) {
        return new Identifier(ChickenRoostMod.MODID,"textures/entities/redchicken.png");
    }
}