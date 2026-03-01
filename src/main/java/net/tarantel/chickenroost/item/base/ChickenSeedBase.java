package net.tarantel.chickenroost.item.base;

import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public class ChickenSeedBase extends ChickenBlockItem {
   private final int currentmaxxpp;

   public ChickenSeedBase(Block block, Properties properties, int currentmaxxp) {
      super(block, properties, currentmaxxp);
      this.currentmaxxpp = currentmaxxp;
   }

   public int getCurrentMaxXp() {
      return this.currentmaxxpp;
   }
}
