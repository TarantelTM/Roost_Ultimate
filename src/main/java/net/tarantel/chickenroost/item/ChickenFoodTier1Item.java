
package net.tarantel.chickenroost.item;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;


public class ChickenFoodTier1Item extends Item {
	public ChickenFoodTier1Item(Item.Settings settings) {
		super(settings.maxCount(64).rarity(Rarity.COMMON));
	}

}
