package net.tarantel.chickenroost.item.base;

import net.minecraft.world.item.Item;

public class ChickenItemBase extends RoostUltimateItem {

    public int currentchickena;
    public ChickenItemBase(Item.Properties properties, int currentchicken) {
        super(properties);
        this.currentchickena = currentchicken;
    }
}
