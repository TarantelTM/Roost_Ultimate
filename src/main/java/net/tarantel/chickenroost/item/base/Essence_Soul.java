
package net.tarantel.chickenroost.item.base;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.tarantel.chickenroost.util.ModTabs;

public class Essence_Soul extends Item {
	public Essence_Soul() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModTabs.TAB_CHICKEN_ROOST)
				.food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.3f).alwaysEat().build()));
	}
}
