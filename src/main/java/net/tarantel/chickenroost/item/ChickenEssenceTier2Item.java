
package net.tarantel.chickenroost.item;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;



public class ChickenEssenceTier2Item extends Item {
	public ChickenEssenceTier2Item(Settings settings) {
		super(settings.maxCount(64).rarity(Rarity.COMMON)
				.food((new FoodComponent.Builder()).hunger(5).saturationModifier(0.4f).alwaysEdible().build()));
	}
}
