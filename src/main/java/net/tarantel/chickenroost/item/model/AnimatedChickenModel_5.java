package net.tarantel.chickenroost.item.model;

import software.bernie.geckolib3.model.AnimatedGeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChicken_5;

public class AnimatedChickenModel_5 extends AnimatedGeoModel<AnimatedChicken_5> {
    @Override
    public ResourceLocation getModelLocation(AnimatedChicken_5 animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/renderchicken.geo.json");
    }


    @Override
    public ResourceLocation getTextureLocation(AnimatedChicken_5 animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/block/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AnimatedChicken_5 animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/renderchicken.animation.json");
    }
}