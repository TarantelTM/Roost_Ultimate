package net.tarantel.chickenroost.item.model;

import software.bernie.geckolib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChicken_8;

public class AnimatedChickenModel_8 extends GeoModel<AnimatedChicken_8> {
    @Override
    public ResourceLocation getModelResource(AnimatedChicken_8 animatable) {
        return ChickenRoostMod.ownresource("geo/renderchicken.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(AnimatedChicken_8 animatable) {
        return ChickenRoostMod.ownresource("textures/block/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedChicken_8 animatable) {
        return ChickenRoostMod.ownresource("animations/renderchicken.animation.json");
    }
}