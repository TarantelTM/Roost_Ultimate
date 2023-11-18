
package net.tarantel.chickenroost.item;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;


public class ChickenScannerItem extends Item {
	public ChickenScannerItem(Settings settings) {
		super(settings.maxCount(1).rarity(Rarity.COMMON));
	}
}
