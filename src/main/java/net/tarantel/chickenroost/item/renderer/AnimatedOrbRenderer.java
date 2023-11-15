package net.tarantel.chickenroost.item.renderer;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.tarantel.chickenroost.item.base.AnimatedChicken_1;
import net.tarantel.chickenroost.item.base.AnimatedOrbItem;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_1;
import net.tarantel.chickenroost.item.model.AnimatedOrbModel;

public class AnimatedOrbRenderer extends GeoItemRenderer<AnimatedOrbItem> {
    public AnimatedOrbRenderer() {
        super(new AnimatedOrbModel());
    }
}

