package net.tarantel.chickenroost.item.renderer;

import net.tarantel.chickenroost.item.base.AnimatedSoulBreederBlockItem;
import net.tarantel.chickenroost.item.model.AnimatedSoulBreederItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AnimatedSoulBreederItemRenderer extends GeoItemRenderer<AnimatedSoulBreederBlockItem> {
    public AnimatedSoulBreederItemRenderer() {
        super(new AnimatedSoulBreederItemModel());
    }
}