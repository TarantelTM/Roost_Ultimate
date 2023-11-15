package net.tarantel.chickenroost.item.model;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedIngotItem;
import net.tarantel.chickenroost.item.base.ChickenBook;

public class ChickenBookModel extends GeoModel<ChickenBook> {
    @Override
    public ResourceLocation getModelResource(ChickenBook animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/book.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(ChickenBook animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/item/book.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChickenBook animatable) {
        return null;
    }
}