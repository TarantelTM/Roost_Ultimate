package net.tarantel.chickenroost.item.base;

public class SoulBase extends RoostUltimateItem {

    public int currentchickena;
    public SoulBase(Properties properties, int currentchicken) {
        super(properties);
        this.currentchickena = currentchicken;
    }
}
