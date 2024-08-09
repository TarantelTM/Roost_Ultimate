package net.tarantel.chickenroost.item.base;

import net.minecraft.world.level.block.Block;

public class ChickenSeedBase extends ChickenBlockItem{

    public static int currentmaxxpp;

    public ChickenSeedBase(Block block, Properties properties, int currentmaxxp) {
        super(block, properties, currentmaxxp);
        currentmaxxpp = currentmaxxp;
    }
}
