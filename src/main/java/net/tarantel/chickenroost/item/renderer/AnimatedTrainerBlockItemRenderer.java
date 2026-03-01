package net.tarantel.chickenroost.item.renderer;

import net.tarantel.chickenroost.item.base.AnimatedTrainerBlockItem;
import net.tarantel.chickenroost.item.model.AnimatedTrainerBlockItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AnimatedTrainerBlockItemRenderer extends GeoItemRenderer<AnimatedTrainerBlockItem> {
   public AnimatedTrainerBlockItemRenderer() {
      super(new AnimatedTrainerBlockItemModel());
   }
}
