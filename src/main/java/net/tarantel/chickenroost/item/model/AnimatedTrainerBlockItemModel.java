package net.tarantel.chickenroost.item.model;

import software.bernie.geckolib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedSoulBreederBlockItem;
import net.tarantel.chickenroost.item.base.AnimatedTrainerBlockItem;

public class AnimatedTrainerBlockItemModel extends GeoModel<AnimatedTrainerBlockItem> {
    @Override
    public ResourceLocation getModelResource(AnimatedTrainerBlockItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/trainer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AnimatedTrainerBlockItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/block/trainer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedTrainerBlockItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/trainer.animation.json");
    }
}