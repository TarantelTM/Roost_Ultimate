package net.tarantel.chickenroost.item.renderer;

import net.tarantel.chickenroost.item.base.AnimatedIngotItem;
import net.tarantel.chickenroost.item.model.AnimatedIngotModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AnimatedIngotRenderer extends GeoItemRenderer<AnimatedIngotItem> {
   public AnimatedIngotRenderer() {
      super(new AnimatedIngotModel());
   }
}
