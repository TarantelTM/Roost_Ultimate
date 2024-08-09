package net.tarantel.chickenroost.item.model;

import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.ChickenBook;
import software.bernie.geckolib.model.GeoModel;

public class ChickenBookModel extends GeoModel<ChickenBook> {
    @Override
    public ResourceLocation getModelResource(ChickenBook animatable) {
        return ChickenRoostMod.ownresource("geo/book.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(ChickenBook animatable) {
        return ChickenRoostMod.ownresource("textures/item/book.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChickenBook animatable) {
        return null;
    }
}