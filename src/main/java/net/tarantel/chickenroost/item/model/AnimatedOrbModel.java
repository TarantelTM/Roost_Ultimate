package net.tarantel.chickenroost.item.model;

import software.bernie.geckolib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedOrbItem;

public class AnimatedOrbModel extends GeoModel<AnimatedOrbItem> {
    @Override
    public ResourceLocation getModelResource(AnimatedOrbItem animatable) {
        return ChickenRoostMod.ownresource("geo/orb.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(AnimatedOrbItem animatable) {
        return ChickenRoostMod.ownresource("textures/item/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedOrbItem animatable) {
        return ChickenRoostMod.ownresource("animations/orb.animation.json");
    }
}