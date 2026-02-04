package net.tarantel.chickenroost.item.base;

import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class EssenceSoul extends SoulBase {
   public EssenceSoul() {
      super(new Properties().stacksTo(64).rarity(Rarity.COMMON).food(new Builder().nutrition(4).saturationModifier(0.3F).alwaysEdible().build()), 0);
   }
}
