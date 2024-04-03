package net.tarantel.chickenroost.item.renderer;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import net.tarantel.chickenroost.item.base.AnimatedChicken_3;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_3;

public class AnimatedChickenRenderer_3 extends GeoItemRenderer<AnimatedChicken_3> {
    public AnimatedChickenRenderer_3() {
        super(new AnimatedChickenModel_3());
    }
}
