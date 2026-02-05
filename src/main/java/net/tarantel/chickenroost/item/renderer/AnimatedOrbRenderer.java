package net.tarantel.chickenroost.item.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.tarantel.chickenroost.item.base.AnimatedOrbItem;
import net.tarantel.chickenroost.item.model.AnimatedOrbModel;

public class AnimatedOrbRenderer extends GeoItemRenderer<AnimatedOrbItem> {
    public AnimatedOrbRenderer() {
        super(new AnimatedOrbModel());
    }
}

