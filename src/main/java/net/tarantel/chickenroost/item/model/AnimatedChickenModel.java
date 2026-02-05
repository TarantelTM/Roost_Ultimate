package net.tarantel.chickenroost.item.model;


import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChicken;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedChickenModel extends GeoModel<AnimatedChicken> {
    @Override
    public ResourceLocation getModelResource(AnimatedChicken animatable) {
        return new ResourceLocation("chicken_roost:geo/renderchicken.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(AnimatedChicken animatable) {
        return new ResourceLocation("chicken_roost:textures/block/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedChicken animatable) {
        return new ResourceLocation("chicken_roost:animations/renderchicken.animation.json");
    }
}