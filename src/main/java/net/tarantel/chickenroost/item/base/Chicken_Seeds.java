
package net.tarantel.chickenroost.item.base;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class Chicken_Seeds extends Item {
	public Chicken_Seeds() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

}
