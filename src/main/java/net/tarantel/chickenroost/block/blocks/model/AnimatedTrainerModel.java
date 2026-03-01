package net.tarantel.chickenroost.block.blocks.model;

import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.tile.TrainerTile;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedTrainerModel extends GeoModel<TrainerTile> {
   public ResourceLocation getModelResource(TrainerTile animatable) {
      return ChickenRoostMod.ownresource("geo/trainer.geo.json");
   }

   public ResourceLocation getTextureResource(TrainerTile animatable) {
      return ChickenRoostMod.ownresource("textures/block/trainer.png");
   }

   public ResourceLocation getAnimationResource(TrainerTile animatable) {
      return ChickenRoostMod.ownresource("animations/trainer.animation.json");
   }
}
