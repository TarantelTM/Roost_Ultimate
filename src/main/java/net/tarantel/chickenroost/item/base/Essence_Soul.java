
package net.tarantel.chickenroost.item.base;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class Essence_Soul extends SoulBase {
	public Essence_Soul() {
		super(new Properties().stacksTo(64).rarity(Rarity.COMMON)
				.food((new FoodProperties.Builder()).nutrition(4).saturationModifier(0.3f).alwaysEdible().build()), 0);
	}
}
