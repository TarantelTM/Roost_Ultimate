package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.world.item.crafting.Ingredient;
import net.tarantel.chickenroost.recipes.TrainerRecipe;

public class EmiTrainerRecipe extends BasicEmiRecipe {
   public EmiTrainerRecipe(TrainerRecipe recipe) {
      super(EmiRoostPlugin.TRAINER_CATEGORY, recipe.getId(), 100, 20);
      this.inputs.add(EmiIngredient.of((Ingredient)recipe.getIngredients().getFirst()));
      this.outputs.add(EmiStack.of(recipe.getResultEmi()));
   }

   public void addWidgets(WidgetHolder widgets) {
      widgets.addTexture(EmiTexture.EMPTY_ARROW, 50, 0);
      widgets.addSlot((EmiIngredient)this.inputs.getFirst(), 80, 0);
      widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 50, 0, 500, true, false, false);
      widgets.addSlot((EmiIngredient)this.outputs.getFirst(), 20, 0).recipeContext(this).getRecipe();
   }
}
