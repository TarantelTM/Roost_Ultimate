package net.tarantel.chickenroost.item.renderer;
import mod.azure.azurelib.renderer.GeoItemRenderer;
import net.tarantel.chickenroost.item.base.AnimatedChickenStick;
import net.tarantel.chickenroost.item.model.AnimatedChickenStickModel;

public class AnimatedChickenStickRenderer extends GeoItemRenderer<AnimatedChickenStick> {
    public AnimatedChickenStickRenderer() {
        super(new AnimatedChickenStickModel());
    }
}