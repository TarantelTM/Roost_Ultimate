package net.tarantel.chickenroost.item.model;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedOrbItem;

public class AnimatedOrbModel extends GeoModel<AnimatedOrbItem> {
    @Override
    public ResourceLocation getModelResource(AnimatedOrbItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/orb.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(AnimatedOrbItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/item/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedOrbItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/orb.animation.json");
    }
}