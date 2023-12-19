package net.tarantel.chickenroost.item.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;
import net.tarantel.chickenroost.item.base.AnimatedChicken_4;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_4;

public class AnimatedChickenRenderer_4 extends GeoItemRenderer<AnimatedChicken_4> {
    public AnimatedChickenRenderer_4() {
        super(new AnimatedChickenModel_4());
    }
}
