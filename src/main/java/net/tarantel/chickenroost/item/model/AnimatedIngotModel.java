package net.tarantel.chickenroost.item.model;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChicken_1;
import net.tarantel.chickenroost.item.base.AnimatedIngotItem;

public class AnimatedIngotModel extends GeoModel<AnimatedIngotItem> {
    @Override
    public ResourceLocation getModelResource(AnimatedIngotItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/ingot.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(AnimatedIngotItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/item/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedIngotItem animatable) {
        return null;
    }
}