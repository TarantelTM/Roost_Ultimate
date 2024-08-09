package net.tarantel.chickenroost.block.blocks.model;


import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.tile.Trainer_Tile;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedTrainerModel extends GeoModel<Trainer_Tile> {
    @Override
    public ResourceLocation getModelResource(Trainer_Tile animatable) {
        return ChickenRoostMod.ownresource("geo/trainer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Trainer_Tile animatable) {
        return ChickenRoostMod.ownresource("textures/block/trainer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Trainer_Tile animatable) {
        return ChickenRoostMod.ownresource("animations/trainer.animation.json");
    }
}