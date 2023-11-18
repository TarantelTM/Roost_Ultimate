package net.tarantel.chickenroost.api.rei.displays;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.tarantel.chickenroost.api.rei.REICategoryIdentifiers;
import net.tarantel.chickenroost.api.rei.REIRecipeDisplay;
import net.tarantel.chickenroost.recipemanager.SoulBreedingRecipe;
import org.jetbrains.annotations.NotNull;

public class SoulBreederREIDisplay extends REIRecipeDisplay<SoulBreedingRecipe> {
    /*public SoulBreederREIDisplay(SoulBreedingRecipe recipe) {
        super(recipe);
    }*/

    @Override
    public @NotNull CategoryIdentifier<?> getCategoryIdentifier() {
        return REICategoryIdentifiers.SOULBREEDING;
    }
}