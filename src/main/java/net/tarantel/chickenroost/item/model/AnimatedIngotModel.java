package net.tarantel.chickenroost.item.model;

import net.tarantel.chickenroost.item.base.AnimatedChicken;
import software.bernie.geckolib.model.GeoModel;
import net.minecraft.resources.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedIngotItem;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class AnimatedIngotModel extends GeoModel<AnimatedIngotItem> {

    public AnimatedIngotItem chicken;
    @Override
    public Identifier getModelResource(GeoRenderState animatable) {
        return ChickenRoostMod.ownresource("geo/ingot.geo.json");
    }


    @Override
    public Identifier getTextureResource(GeoRenderState animatable) {
        return ChickenRoostMod.ownresource("textures/item/" + chicken.getLocalpath() + ".png");
    }

    @Override
    public Identifier getAnimationResource(AnimatedIngotItem animatable) {
        chicken = animatable;
        return null;
    }
}