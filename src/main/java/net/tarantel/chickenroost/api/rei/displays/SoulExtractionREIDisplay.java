package net.tarantel.chickenroost.api.rei.displays;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;

import net.tarantel.chickenroost.api.rei.REICategoryIdentifiers;
import net.tarantel.chickenroost.api.rei.REIRecipeDisplay;
import net.tarantel.chickenroost.recipemanager.SoulExtractionRecipe;
import org.jetbrains.annotations.NotNull;

public class SoulExtractionREIDisplay extends REIRecipeDisplay<SoulExtractionRecipe> {
    /*public SoulExtractionREIDisplay(SoulExtractionRecipe recipe) {
        super(recipe);
    }*/

    @Override
    public @NotNull CategoryIdentifier<?> getCategoryIdentifier() {
        return REICategoryIdentifiers.SOULEXTRACTION;
    }
}