package net.tarantel.chickenroost.block.blocks.model;


import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.tile.Soul_Breeder_Tile;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedSoulBreederModel extends GeoModel<Soul_Breeder_Tile> {
    @Override
    public ResourceLocation getModelResource(Soul_Breeder_Tile animatable) {
        return ChickenRoostMod.ownresource("geo/soul_breeder.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Soul_Breeder_Tile animatable) {
        return ChickenRoostMod.ownresource("textures/block/soul_breeder.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Soul_Breeder_Tile animatable) {
        return ChickenRoostMod.ownresource("animations/soul_breeder.animation.json");
    }
}