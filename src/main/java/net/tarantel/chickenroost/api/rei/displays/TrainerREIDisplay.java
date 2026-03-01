package net.tarantel.chickenroost.api.rei.displays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.tarantel.chickenroost.recipes.TrainerRecipe;

public class TrainerREIDisplay extends BasicDisplay {
   public static final CategoryIdentifier<TrainerREIDisplay> ID = CategoryIdentifier.of("chicken_roost", "trainer_output");

   public TrainerREIDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
      super(inputs, outputs);
   }

   public TrainerREIDisplay(RecipeHolder<TrainerRecipe> recipe) {
      super(getInputList((TrainerRecipe)recipe.value()), List.of(EntryIngredient.of(EntryIngredients.of(((TrainerRecipe)recipe.value()).getResultItem(null)))));
   }

   public TrainerREIDisplay(List<EntryIngredient> input, List<EntryIngredient> output, Optional<ResourceLocation> location) {
      super(input, output, location);
   }

   private static List<EntryIngredient> getInputList(TrainerRecipe recipe) {
      if (recipe == null) {
         return Collections.emptyList();
      } else {
         List<EntryIngredient> list = new ArrayList<>();
         list.add(EntryIngredients.ofIngredient((Ingredient)recipe.getIngredients().get(0)));
         return list;
      }
   }

   public CategoryIdentifier<?> getCategoryIdentifier() {
      return ID;
   }
}
