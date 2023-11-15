package net.tarantel.chickenroost.block.blocks.model;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.tile.Soul_Breeder_Tile;
import net.tarantel.chickenroost.block.tile.Trainer_Tile;

public class AnimatedTrainerModel extends GeoModel<Trainer_Tile> {
    @Override
    public ResourceLocation getModelResource(Trainer_Tile animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/trainer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Trainer_Tile animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/block/trainer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Trainer_Tile animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/trainer.animation.json");
    }
}