package net.tarantel.chickenroost.item.model;


import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedTrainerBlockItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnimatedTrainerBlockItemModel extends AnimatedGeoModel<AnimatedTrainerBlockItem> {
    @Override
    public ResourceLocation getModelLocation(AnimatedTrainerBlockItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/trainer.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AnimatedTrainerBlockItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/block/trainer.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AnimatedTrainerBlockItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/trainer.animation.json");
    }
}