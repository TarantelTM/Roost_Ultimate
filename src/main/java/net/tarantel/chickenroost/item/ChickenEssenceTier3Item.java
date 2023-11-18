
package net.tarantel.chickenroost.item;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;



public class ChickenEssenceTier3Item extends Item {
	public ChickenEssenceTier3Item(Settings settings) {
		super(settings.maxCount(64).rarity(Rarity.UNCOMMON)
				.food((new FoodComponent.Builder()).hunger(6).saturationModifier(0.5f).alwaysEdible().build()));
	}
}
