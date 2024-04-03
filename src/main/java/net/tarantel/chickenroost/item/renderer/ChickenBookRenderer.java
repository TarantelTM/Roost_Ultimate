package net.tarantel.chickenroost.item.renderer;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import net.tarantel.chickenroost.item.base.ChickenBook;
import net.tarantel.chickenroost.item.model.ChickenBookModel;

public class ChickenBookRenderer extends GeoItemRenderer<ChickenBook> {
    public ChickenBookRenderer() {
        super(new ChickenBookModel());
    }
}

