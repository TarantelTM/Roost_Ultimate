package net.tarantel.chickenroost.item.model;

import software.bernie.geckolib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.item.base.AnimatedTrainerBlockItem;

public class AnimatedTrainerBlockItemModel extends GeoModel<AnimatedTrainerBlockItem> {
    @Override
    public ResourceLocation getModelResource(AnimatedTrainerBlockItem animatable) {
        return new ResourceLocation("chicken_roost:geo/trainer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AnimatedTrainerBlockItem animatable) {
        return new ResourceLocation("chicken_roost:textures/block/trainer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedTrainerBlockItem animatable) {
        return new ResourceLocation("chicken_roost:animations/trainer.animation.json");
    }
}