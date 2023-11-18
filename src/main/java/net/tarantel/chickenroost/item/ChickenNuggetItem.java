
package net.tarantel.chickenroost.item;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;



public class ChickenNuggetItem extends Item {
	public ChickenNuggetItem(Settings settings) {
		super(settings.maxCount(64).rarity(Rarity.EPIC)
				.food((new FoodComponent.Builder()).hunger(1).saturationModifier(0f).alwaysEdible().meat().build()));
	}
}
