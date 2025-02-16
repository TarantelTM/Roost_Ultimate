package net.tarantel.chickenroost.api.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.tarantel.chickenroost.api.rei.displays.*;
import net.tarantel.chickenroost.recipes.*;

public class REICategoryIdentifiers {
    public static final CategoryIdentifier<EggREIDisplay> EGG = CategoryIdentifier.of(ThrowEggRecipe.Type.ID);
    public static final CategoryIdentifier<BreederREIDisplay> BREEDING = CategoryIdentifier.of(Breeder_Recipe.Type.ID);
    public static final CategoryIdentifier<SoulBreederREIDisplay> SOULBREEDING = CategoryIdentifier.of(Soul_Breeder_Recipe.Type.ID);
    public static final CategoryIdentifier<SoulExtractionREIDisplay> SOULEXTRACTION = CategoryIdentifier.of(Soul_Extractor_Recipe.Type.ID);
    public static final CategoryIdentifier<RoostREIDisplayV1> ROOST = CategoryIdentifier.of(Roost_Recipe.Type.ID);
    public static final CategoryIdentifier<RoostREIDisplayV1> TRAINER = CategoryIdentifier.of(Trainer_Recipe.Type.ID);

    private REICategoryIdentifiers() {

    }
}