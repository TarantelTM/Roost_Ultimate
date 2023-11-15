package net.tarantel.chickenroost.item.renderer;


import mod.azure.azurelib.renderer.GeoItemRenderer;
import net.tarantel.chickenroost.item.base.AnimatedSoulBreederBlockItem;
import net.tarantel.chickenroost.item.model.AnimatedSoulBreederItemModel;

public class AnimatedSoulBreederItemRenderer extends GeoItemRenderer<AnimatedSoulBreederBlockItem> {
    public AnimatedSoulBreederItemRenderer() {
        super(new AnimatedSoulBreederItemModel());
    }
}