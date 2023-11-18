
package net.tarantel.chickenroost.item;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;


public class ChickenEssenceTier7Item extends Item {
	public ChickenEssenceTier7Item(Settings settings) {
		super(settings.maxCount(64).rarity(Rarity.RARE)
				.food((new FoodComponent.Builder()).hunger(10).saturationModifier(0.9f).alwaysEdible()

						.build()));
	}
}
