package net.tarantel.chickenroost.item.renderer;


import net.tarantel.chickenroost.item.base.AnimatedChicken;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AnimatedChickenRenderer extends GeoItemRenderer<AnimatedChicken> {
    public AnimatedChickenRenderer() {
        super(new AnimatedChickenModel());
    }
}

