package net.tarantel.chickenroost.api.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.tarantel.chickenroost.api.rei.displays.*;
import net.tarantel.chickenroost.recipemanager.*;

public class REICategoryIdentifiers {
    public static final CategoryIdentifier<BreederREIDisplay> BREEDING = CategoryIdentifier.of(BasicBreedingRecipe.Type.ID);
    public static final CategoryIdentifier<SoulBreederREIDisplay> SOULBREEDING = CategoryIdentifier.of(SoulBreedingRecipe.Type.ID);
    public static final CategoryIdentifier<SoulExtractionREIDisplay> SOULEXTRACTION = CategoryIdentifier.of(SoulExtractionRecipe.Type.ID);
    public static final CategoryIdentifier<RoostREIDisplayV1> ROOST = CategoryIdentifier.of(Roost_Recipe.Type.ID);

    private REICategoryIdentifiers() {

    }
}