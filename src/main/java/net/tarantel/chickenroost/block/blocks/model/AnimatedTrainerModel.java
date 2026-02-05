package net.tarantel.chickenroost.block.blocks.model;


import net.minecraft.resources.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.tile.TrainerTile;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class AnimatedTrainerModel extends DefaultedBlockGeoModel<TrainerTile> {

    public AnimatedTrainerModel() {
        super(Identifier.fromNamespaceAndPath(ChickenRoostMod.MODID, "trainer"));
    }

    @Override
    public Identifier getModelResource(GeoRenderState animatable) {
        return ChickenRoostMod.ownresource("trainer");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState animatable) {
        return ChickenRoostMod.ownresource("textures/block/trainer.png");
    }

    @Override
    public Identifier getAnimationResource(TrainerTile animatable) {
        return ChickenRoostMod.ownresource("trainer");
    }


    @Override
    public void addAdditionalStateData(
            TrainerTile animatable,
            Object relatedObject,
            GeoRenderState renderState
    ) {
        // Optional: Item fürs Rendering speichern
        renderState.addGeckolibData(
                software.bernie.geckolib.constant.DataTickets.ITEM,
                animatable.getRenderStack().getItem()
        );
    }

}