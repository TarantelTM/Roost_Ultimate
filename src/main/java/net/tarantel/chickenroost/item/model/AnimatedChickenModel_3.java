package net.tarantel.chickenroost.item.model;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChicken_3;

public class AnimatedChickenModel_3 extends GeoModel<AnimatedChicken_3> {
    @Override
    public ResourceLocation getModelResource(AnimatedChicken_3 animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/renderchicken.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(AnimatedChicken_3 animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/block/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedChicken_3 animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/renderchicken.animation.json");
    }
}