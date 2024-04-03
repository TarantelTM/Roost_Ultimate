package net.tarantel.chickenroost.item.renderer;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import net.tarantel.chickenroost.item.base.AnimatedChicken_2;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_2;

public class AnimatedChickenRenderer_2 extends GeoItemRenderer<AnimatedChicken_2> {
    public AnimatedChickenRenderer_2() {
        super(new AnimatedChickenModel_2());
    }
}
