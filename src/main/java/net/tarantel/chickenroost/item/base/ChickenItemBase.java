package net.tarantel.chickenroost.item.base;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;

public class ChickenItemBase extends RoostUltimateItem {
   private final int tierIndex;

   public ChickenItemBase(Properties properties, int tierIndex) {
      super(properties);
      this.tierIndex = tierIndex;
   }

   public int currentchickena(ItemStack defaultInstance) {
      return this.tierIndex;
   }
}
