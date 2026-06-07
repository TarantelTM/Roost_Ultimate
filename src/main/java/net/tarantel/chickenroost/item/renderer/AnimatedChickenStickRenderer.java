package net.tarantel.chickenroost.item.renderer;

import net.tarantel.chickenroost.item.base.AnimatedChickenStick;
import net.tarantel.chickenroost.item.model.AnimatedChickenStickModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AnimatedChickenStickRenderer extends GeoItemRenderer<AnimatedChickenStick> {
   public AnimatedChickenStickRenderer() {
      super(new AnimatedChickenStickModel());
   }
}
