package net.tarantel.chickenroost.api.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.tarantel.chickenroost.api.rei.displays.BreederREIDisplay;
import net.tarantel.chickenroost.api.rei.displays.EggREIDisplay;
import net.tarantel.chickenroost.api.rei.displays.RoostREIDisplay;
import net.tarantel.chickenroost.api.rei.displays.SoulExtractionREIDisplay;
import net.tarantel.chickenroost.api.rei.displays.TrainerREIDisplay;
import net.tarantel.chickenroost.recipes.RoostRecipe;

public class REICategoryIdentifiers {
   public static final CategoryIdentifier<EggREIDisplay> EGG = CategoryIdentifier.of("throwegg");
   public static final CategoryIdentifier<BreederREIDisplay> BREEDING = CategoryIdentifier.of("basic_breeding");
   public static final CategoryIdentifier<SoulExtractionREIDisplay> SOULEXTRACTION = CategoryIdentifier.of("soul_extraction");
   public static final CategoryIdentifier<RoostREIDisplay> ROOST = CategoryIdentifier.of(RoostRecipe.Type.ID);
   public static final CategoryIdentifier<TrainerREIDisplay> TRAINER = CategoryIdentifier.of("trainer_output");

   private REICategoryIdentifiers() {
   }
}
