package net.tarantel.chickenroost.item.model;

import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedTrainerBlockItem;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedTrainerBlockItemModel extends GeoModel<AnimatedTrainerBlockItem> {
   public ResourceLocation getModelResource(AnimatedTrainerBlockItem animatable) {
      return ChickenRoostMod.ownresource("geo/trainer.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedTrainerBlockItem animatable) {
      return ChickenRoostMod.ownresource("textures/block/trainer.png");
   }

   public ResourceLocation getAnimationResource(AnimatedTrainerBlockItem animatable) {
      return ChickenRoostMod.ownresource("animations/trainer.animation.json");
   }
}
