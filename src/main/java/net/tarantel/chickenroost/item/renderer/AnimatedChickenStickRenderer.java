package net.tarantel.chickenroost.item.renderer;
import net.tarantel.chickenroost.item.model.AnimatedChickenStickModel;
import net.tarantel.chickenroost.item.base.AnimatedChickenStick;
import software.bernie.geckolib.renderer.GeoItemRenderer;
public class AnimatedChickenStickRenderer extends GeoItemRenderer<AnimatedChickenStick> {
    public AnimatedChickenStickRenderer() {
        super(new AnimatedChickenStickModel());
    }
}