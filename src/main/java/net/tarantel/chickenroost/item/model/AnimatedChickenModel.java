package net.tarantel.chickenroost.item.model;


import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.tarantel.chickenroost.*;
import net.tarantel.chickenroost.item.base.AnimatedChicken;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class AnimatedChickenModel extends GeoModel<AnimatedChicken> {


    public AnimatedChickenModel() {
        //super();
    }


    @Override
    public Identifier getModelResource(GeoRenderState animatable) {
        return ChickenRoostMod.ownresource("renderchicken");
    }


    @Override
    public Identifier getTextureResource(GeoRenderState animatable) {
        Item item = animatable.getGeckolibData(DataTickets.ITEM);

        if (item instanceof AnimatedChicken chicken) {
            return ChickenRoostMod.ownresource(
                    "textures/block/" + chicken.getLocalpath() + ".png"
            );
        }

        // Fallback (sollte eigentlich nie passieren)
        return ChickenRoostMod.ownresource("textures/block/default.png");
    }


    @Override
    public Identifier getAnimationResource(AnimatedChicken animatable) {
        return ChickenRoostMod.ownresource("renderchicken");
    }


}