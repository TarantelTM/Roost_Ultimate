package net.tarantel.chickenroost.block.blocks.model;


import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.tile.SoulBreederTile;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedSoulBreederModel extends GeoModel<SoulBreederTile> {
    @Override
    public ResourceLocation getModelResource(SoulBreederTile animatable) {
        return ChickenRoostMod.ownresource("geo/soul_breeder.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SoulBreederTile animatable) {
        return ChickenRoostMod.ownresource("textures/block/soul_breeder.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SoulBreederTile animatable) {
        return ChickenRoostMod.ownresource("animations/soul_breeder.animation.json");
    }
}