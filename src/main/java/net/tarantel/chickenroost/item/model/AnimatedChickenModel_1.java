package net.tarantel.chickenroost.item.model;


import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.*;
import net.tarantel.chickenroost.item.base.AnimatedChicken_1;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedChickenModel_1 extends GeoModel<AnimatedChicken_1> {
    @Override
    public ResourceLocation getModelResource(AnimatedChicken_1 animatable) {
        return ChickenRoostMod.ownresource("geo/renderchicken.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(AnimatedChicken_1 animatable) {
        return ChickenRoostMod.ownresource("textures/block/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedChicken_1 animatable) {
        return ChickenRoostMod.ownresource("animations/renderchicken.animation.json");
    }
}