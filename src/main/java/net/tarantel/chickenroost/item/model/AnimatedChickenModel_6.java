package net.tarantel.chickenroost.item.model;

import software.bernie.geckolib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChicken_6;

public class AnimatedChickenModel_6 extends GeoModel<AnimatedChicken_6> {
    @Override
    public ResourceLocation getModelResource(AnimatedChicken_6 animatable) {
        return ChickenRoostMod.ownresource("geo/renderchicken.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(AnimatedChicken_6 animatable) {
        return ChickenRoostMod.ownresource("textures/block/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedChicken_6 animatable) {
        return ChickenRoostMod.ownresource("animations/renderchicken.animation.json");
    }
}