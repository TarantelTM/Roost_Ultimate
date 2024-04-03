package net.tarantel.chickenroost.item.model;


import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChicken_1;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class AnimatedChickenModel_1 extends AnimatedGeoModel<AnimatedChicken_1> {

    @Override
    public ResourceLocation getModelLocation(AnimatedChicken_1 item) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/renderchicken.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AnimatedChicken_1 item) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/block/" + item.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AnimatedChicken_1 item) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/renderchicken.animation.json");
    }
}