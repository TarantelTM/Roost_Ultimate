package net.tarantel.chickenroost.item.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;
import net.tarantel.chickenroost.item.base.AnimatedChicken_9;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_9;

public class AnimatedChickenRenderer_9 extends GeoItemRenderer<AnimatedChicken_9> {
    public AnimatedChickenRenderer_9() {
        super(new AnimatedChickenModel_9());
    }
}
