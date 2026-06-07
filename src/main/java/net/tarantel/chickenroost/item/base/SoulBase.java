package net.tarantel.chickenroost.item.base;

import net.minecraft.world.item.Item.Properties;

public class SoulBase extends RoostUltimateItem {
   public int currentchickena;

   public SoulBase(Properties properties, int currentchicken) {
      super(properties);
      this.currentchickena = currentchicken;
   }
}
