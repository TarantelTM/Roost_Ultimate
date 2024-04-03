package net.tarantel.chickenroost.item.model;

import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChickenStick;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnimatedChickenStickModel extends AnimatedGeoModel<AnimatedChickenStick> {
    @Override
    public ResourceLocation getModelLocation(AnimatedChickenStick item) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/chicken_stick.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AnimatedChickenStick item) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/item/chicken_stick.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AnimatedChickenStick item) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/chicken_stick.animation.json");
    }
}