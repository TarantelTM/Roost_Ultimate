package net.tarantel.chickenroost.item.model;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import software.bernie.geckolib.model.GeoModel;
import net.tarantel.chickenroost.item.base.AnimatedChickenStick;

public class AnimatedChickenStickModel extends GeoModel<AnimatedChickenStick> {
    @Override
    public ResourceLocation getModelResource(AnimatedChickenStick animatable) {
        return ChickenRoostMod.ownresource("geo/chicken_stick.geo.json");
    }


    @Override
    public ResourceLocation getTextureResource(AnimatedChickenStick animatable) {
        return ChickenRoostMod.ownresource("textures/item/chicken_stick.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedChickenStick animatable) {
        return ChickenRoostMod.ownresource("animations/chicken_stick.animation.json");
    }
}