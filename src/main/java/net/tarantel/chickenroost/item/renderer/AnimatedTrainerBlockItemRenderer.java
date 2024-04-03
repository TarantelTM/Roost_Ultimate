package net.tarantel.chickenroost.item.renderer;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import net.tarantel.chickenroost.item.base.AnimatedTrainerBlockItem;
import net.tarantel.chickenroost.item.model.AnimatedTrainerBlockItemModel;

public class AnimatedTrainerBlockItemRenderer extends GeoItemRenderer<AnimatedTrainerBlockItem> {
    public AnimatedTrainerBlockItemRenderer() {
        super(new AnimatedTrainerBlockItemModel());
    }
}