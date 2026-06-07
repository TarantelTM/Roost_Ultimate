package net.tarantel.chickenroost.item.model;

import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedIngotItem;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedIngotModel extends GeoModel<AnimatedIngotItem> {
   public ResourceLocation getModelResource(AnimatedIngotItem animatable) {
      return ChickenRoostMod.ownresource("geo/ingot.geo.json");
   }

   public ResourceLocation getTextureResource(AnimatedIngotItem animatable) {
      return ChickenRoostMod.ownresource("textures/item/" + animatable.getLocalpath() + ".png");
   }

   public ResourceLocation getAnimationResource(AnimatedIngotItem animatable) {
      return null;
   }
}
