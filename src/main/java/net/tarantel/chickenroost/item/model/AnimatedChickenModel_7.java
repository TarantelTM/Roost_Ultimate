package net.tarantel.chickenroost.item.model;

import software.bernie.geckolib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChicken_7;

public class AnimatedChickenModel_7 extends GeoModel<AnimatedChicken_7> {
    @Override
    public ResourceLocation getModelResource(AnimatedChicken_7 animatable) {
        return ChickenRoostMod.ownresource("geo/renderchicken.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(AnimatedChicken_7 animatable) {
        return ChickenRoostMod.ownresource("textures/block/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedChicken_7 animatable) {
        return ChickenRoostMod.ownresource("animations/renderchicken.animation.json");
    }
}