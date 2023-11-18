
package net.tarantel.chickenroost.item;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;


public class Chickenessence1Item extends Item  {
	public Chickenessence1Item(Settings settings) {
		super(settings.maxCount(64).rarity(Rarity.COMMON)
				.food((new FoodComponent.Builder()).hunger(4).saturationModifier(0.3f).alwaysEdible()

						.build()));
	}

}
