package net.tarantel.chickenroost.item.model;

import software.bernie.geckolib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChicken_1;
import net.tarantel.chickenroost.item.base.AnimatedIngotItem;

public class AnimatedIngotModel extends GeoModel<AnimatedIngotItem> {
    @Override
    public ResourceLocation getModelResource(AnimatedIngotItem animatable) {
        return ChickenRoostMod.ownresource("geo/ingot.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(AnimatedIngotItem animatable) {
        return ChickenRoostMod.ownresource("textures/item/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedIngotItem animatable) {
        return null;
    }
}