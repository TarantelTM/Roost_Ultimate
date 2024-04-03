package net.tarantel.chickenroost.block.model;


import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.tile.Soul_Breeder_Tile;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class AnimatedSoulBreederModel extends AnimatedGeoModel<Soul_Breeder_Tile> {
    @Override
    public ResourceLocation getModelLocation(Soul_Breeder_Tile animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/soul_breeder.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Soul_Breeder_Tile animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/block/soul_breeder.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Soul_Breeder_Tile animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/soul_breeder.animation.json");
    }
}