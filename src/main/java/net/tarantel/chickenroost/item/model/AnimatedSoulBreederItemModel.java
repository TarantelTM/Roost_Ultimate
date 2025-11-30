package net.tarantel.chickenroost.item.model;
import software.bernie.geckolib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedSoulBreederBlockItem;


public class AnimatedSoulBreederItemModel extends GeoModel<AnimatedSoulBreederBlockItem> {
    @Override
    public ResourceLocation getModelResource(AnimatedSoulBreederBlockItem animatable) {
        return ChickenRoostMod.ownresource("geo/soul_breeder.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AnimatedSoulBreederBlockItem animatable) {
        return ChickenRoostMod.ownresource("textures/block/soul_breeder.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedSoulBreederBlockItem animatable) {
        return ChickenRoostMod.ownresource("animations/soul_breeder.animation.json");
    }
}