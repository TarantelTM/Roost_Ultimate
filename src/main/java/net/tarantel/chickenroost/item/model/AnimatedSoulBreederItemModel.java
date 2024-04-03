package net.tarantel.chickenroost.item.model;

import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedSoulBreederBlockItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class AnimatedSoulBreederItemModel extends AnimatedGeoModel<AnimatedSoulBreederBlockItem> {
    @Override
    public ResourceLocation getModelLocation(AnimatedSoulBreederBlockItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/soul_breeder.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AnimatedSoulBreederBlockItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/block/soul_breeder.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AnimatedSoulBreederBlockItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/soul_breeder.animation.json");
    }
}