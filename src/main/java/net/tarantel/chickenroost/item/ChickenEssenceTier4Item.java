
package net.tarantel.chickenroost.item;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;


public class ChickenEssenceTier4Item extends Item {
	public ChickenEssenceTier4Item(Settings settings) {
		super(settings.maxCount(64).rarity(Rarity.UNCOMMON)
				.food((new FoodComponent.Builder()).hunger(7).saturationModifier(0.6f).alwaysEdible()

						.build()));
	}
}
