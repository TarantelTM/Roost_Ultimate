package net.tarantel.chickenroost.item.model;


import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChicken_1;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedChickenModel_1 extends GeoModel<AnimatedChicken_1> {
    @Override
    public ResourceLocation getModelResource(AnimatedChicken_1 animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/renderchicken.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(AnimatedChicken_1 animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/block/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedChicken_1 animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/renderchicken.animation.json");
    }
}