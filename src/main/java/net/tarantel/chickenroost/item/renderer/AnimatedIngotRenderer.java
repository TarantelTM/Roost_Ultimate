package net.tarantel.chickenroost.item.renderer;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.item.model.*;

public class AnimatedIngotRenderer extends GeoItemRenderer<AnimatedIngotItem> {
    public AnimatedIngotRenderer() {
        super(new AnimatedIngotModel());
    }
}

