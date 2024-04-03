package net.tarantel.chickenroost.block.model;


import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.tile.Trainer_Tile;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnimatedTrainerModel extends AnimatedGeoModel<Trainer_Tile> {
    @Override
    public ResourceLocation getModelLocation(Trainer_Tile animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/trainer.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Trainer_Tile animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/block/trainer.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Trainer_Tile animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/trainer.animation.json");
    }
}