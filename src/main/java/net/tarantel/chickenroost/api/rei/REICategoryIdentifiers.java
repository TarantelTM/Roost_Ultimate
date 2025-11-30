package net.tarantel.chickenroost.api.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.tarantel.chickenroost.api.rei.displays.*;
import net.tarantel.chickenroost.recipes.*;

public class REICategoryIdentifiers {
    public static final CategoryIdentifier<EggREIDisplay> EGG = CategoryIdentifier.of(ThrowEggRecipe.Type.ID);
    public static final CategoryIdentifier<BreederREIDisplay> BREEDING = CategoryIdentifier.of(BreederRecipe.Type.ID);
    public static final CategoryIdentifier<SoulBreederREIDisplay> SOULBREEDING = CategoryIdentifier.of(SoulBreederRecipe.Type.ID);
    public static final CategoryIdentifier<SoulExtractionREIDisplay> SOULEXTRACTION = CategoryIdentifier.of(SoulExtractorRecipe.Type.ID);
    public static final CategoryIdentifier<RoostREIDisplay> ROOST = CategoryIdentifier.of(RoostRecipe.Type.ID);
    public static final CategoryIdentifier<TrainerREIDisplay> TRAINER = CategoryIdentifier.of(TrainerRecipe.Type.ID);

    private REICategoryIdentifiers() {

    }
}