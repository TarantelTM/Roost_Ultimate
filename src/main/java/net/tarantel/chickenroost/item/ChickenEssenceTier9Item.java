
package net.tarantel.chickenroost.item;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;


public class ChickenEssenceTier9Item extends Item {
	public ChickenEssenceTier9Item(Settings settings) {
		super(settings.maxCount(64).rarity(Rarity.EPIC)
				.food((new FoodComponent.Builder()).hunger(12).saturationModifier(2f).alwaysEdible().build()));
	}

}
