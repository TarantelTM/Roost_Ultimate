package net.tarantel.chickenroost.item.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.item.model.*;


public class AnimatedChickenRenderer extends GeoItemRenderer<AnimatedChicken> {
    public AnimatedChickenRenderer() {
        super(new AnimatedChickenModel());
    }
}

