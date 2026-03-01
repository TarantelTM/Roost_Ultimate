package net.tarantel.chickenroost.item.model;

import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChickenStick;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedChickenStickModel extends GeoModel<AnimatedChickenStick> {
   public ResourceLocation getModelResource(AnimatedChickenStick animatable) {
      return ChickenRoostMod.ownresource("geo/chicken_stick.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedChickenStick animatable) {
      return ChickenRoostMod.ownresource("textures/item/chicken_stick.png");
   }

   public ResourceLocation getAnimationResource(AnimatedChickenStick animatable) {
      return ChickenRoostMod.ownresource("animations/chicken_stick.animation.json");
   }
}
