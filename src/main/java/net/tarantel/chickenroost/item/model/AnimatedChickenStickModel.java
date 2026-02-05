package net.tarantel.chickenroost.item.model;

import net.minecraft.resources.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;
import software.bernie.geckolib.model.GeoModel;
import net.tarantel.chickenroost.item.base.AnimatedChickenStick;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class AnimatedChickenStickModel extends GeoModel<AnimatedChickenStick> {


    public AnimatedChickenStickModel() {
        //super();
    }

    @Override
    public Identifier getModelResource(GeoRenderState animatable) {
        return ChickenRoostMod.ownresource("chicken_stick");
    }


    @Override
    public Identifier getTextureResource(GeoRenderState animatable) {
        return ChickenRoostMod.ownresource("textures/item/chicken_stick.png");
    }

    @Override
    public Identifier getAnimationResource(AnimatedChickenStick animatable) {
        return ChickenRoostMod.ownresource("chicken_stick");
    }
}