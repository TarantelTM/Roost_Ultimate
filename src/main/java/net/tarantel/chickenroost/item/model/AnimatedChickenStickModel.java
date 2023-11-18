package net.tarantel.chickenroost.item.model;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChickenStick;
import software.bernie.geckolib.model.GeoModel;
public class AnimatedChickenStickModel extends GeoModel<AnimatedChickenStick> {
    @Override
    public ResourceLocation getModelResource(AnimatedChickenStick animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/chicken_stick.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AnimatedChickenStick animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/item/chicken_stick.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedChickenStick animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/chicken_stick.animation.json");
    }
}