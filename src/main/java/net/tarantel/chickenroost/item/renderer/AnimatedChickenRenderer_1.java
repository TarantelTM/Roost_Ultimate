package net.tarantel.chickenroost.item.renderer;


import net.tarantel.chickenroost.item.base.AnimatedChicken_1;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_1;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AnimatedChickenRenderer_1 extends GeoItemRenderer<AnimatedChicken_1> {
    public AnimatedChickenRenderer_1() {
        super(new AnimatedChickenModel_1());
    }
}

