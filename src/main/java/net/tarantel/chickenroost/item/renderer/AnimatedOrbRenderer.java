package net.tarantel.chickenroost.item.renderer;

import net.tarantel.chickenroost.item.base.AnimatedOrbItem;
import net.tarantel.chickenroost.item.model.AnimatedOrbModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AnimatedOrbRenderer extends GeoItemRenderer<AnimatedOrbItem> {
   public AnimatedOrbRenderer() {
      super(new AnimatedOrbModel());
   }
}
