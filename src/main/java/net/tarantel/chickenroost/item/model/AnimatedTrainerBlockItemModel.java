package net.tarantel.chickenroost.item.model;

import software.bernie.geckolib.model.GeoModel;
import net.minecraft.resources.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedTrainerBlockItem;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class AnimatedTrainerBlockItemModel extends GeoModel<AnimatedTrainerBlockItem> {
    @Override
    public Identifier getModelResource(GeoRenderState animatable) {
        return ChickenRoostMod.ownresource("trainer");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState animatable) {
        return ChickenRoostMod.ownresource("textures/block/trainer.png");
    }

    @Override
    public Identifier getAnimationResource(AnimatedTrainerBlockItem animatable) {
        return ChickenRoostMod.ownresource("trainer");
    }
}