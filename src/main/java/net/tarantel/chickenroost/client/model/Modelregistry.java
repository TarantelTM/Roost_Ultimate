package net.tarantel.chickenroost.client.model;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

public class Modelregistry {

    public static void load(){
        EntityModelLayerRegistry.registerModelLayer(Modelchicken.LAYER_LOCATION, Modelchicken::createBodyLayer);
    }
}
