package net.tarantel.chickenroost.item.renderer;


import net.tarantel.chickenroost.item.base.AnimatedChicken_2;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_2;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AnimatedChickenRenderer_2 extends GeoItemRenderer<AnimatedChicken_2> {
    public AnimatedChickenRenderer_2() {
        super(new AnimatedChickenModel_2());
    }
}
