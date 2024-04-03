package net.tarantel.chickenroost.item.model;


import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.ChickenBook;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChickenBookModel extends AnimatedGeoModel<ChickenBook> {
    @Override
    public ResourceLocation getModelLocation(ChickenBook animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/book.geo.json");
    }


    @Override
    public ResourceLocation getTextureLocation(ChickenBook animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/item/book.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ChickenBook animatable) {
        return null;
    }
}