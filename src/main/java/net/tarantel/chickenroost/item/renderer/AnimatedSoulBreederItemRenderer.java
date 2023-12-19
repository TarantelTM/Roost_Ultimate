package net.tarantel.chickenroost.item.renderer;


import software.bernie.geckolib.renderer.GeoItemRenderer;
import net.tarantel.chickenroost.item.base.AnimatedSoulBreederBlockItem;
import net.tarantel.chickenroost.item.model.AnimatedSoulBreederItemModel;

public class AnimatedSoulBreederItemRenderer extends GeoItemRenderer<AnimatedSoulBreederBlockItem> {
    public AnimatedSoulBreederItemRenderer() {
        super(new AnimatedSoulBreederItemModel());
    }
}