package net.tarantel.chickenroost.item.model;

import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedOrbItem;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedOrbModel extends GeoModel<AnimatedOrbItem> {
   public ResourceLocation getModelResource(AnimatedOrbItem animatable) {
      return ChickenRoostMod.ownresource("geo/orb.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedOrbItem animatable) {
      return ChickenRoostMod.ownresource("textures/item/" + animatable.getLocalpath() + ".png");
   }

   public ResourceLocation getAnimationResource(AnimatedOrbItem animatable) {
      return ChickenRoostMod.ownresource("animations/orb.animation.json");
   }
}
