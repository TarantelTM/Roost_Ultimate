package net.tarantel.chickenroost.item;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;


public class ChickenEssenceTier6Item extends Item {
    public ChickenEssenceTier6Item(Settings settings) {
        super(settings.maxCount(64).rarity(Rarity.RARE)
                .food((new FoodComponent.Builder()).hunger(9).saturationModifier(0.8f).alwaysEdible()

                        .build()));
    }
}
