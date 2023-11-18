
package net.tarantel.chickenroost.item;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;


public class ChickenEssenceTier5Item extends Item {
	public ChickenEssenceTier5Item(Settings settings) {
		super(settings.maxCount(64).rarity(Rarity.RARE)
				.food((new FoodComponent.Builder()).hunger(8).saturationModifier(0.7f).alwaysEdible()

						.build()));
	}
}
