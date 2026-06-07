package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.world.item.crafting.Ingredient;
import net.tarantel.chickenroost.recipes.ThrowEggRecipe;

public class EmiEggRecipe extends BasicEmiRecipe {
   public EmiEggRecipe(ThrowEggRecipe recipe) {
      super(EmiRoostPlugin.EGG_CATEGORY, recipe.getId(), 60, 20);
      this.inputs.add(EmiIngredient.of((Ingredient)recipe.getIngredients().getFirst()));
      this.outputs.add(EmiStack.of(recipe.getResultEmi()));
   }

   public void addWidgets(WidgetHolder widgets) {
      widgets.addTexture(EmiTexture.EMPTY_ARROW, 18, 0);
      widgets.addSlot((EmiIngredient)this.inputs.getFirst(), 0, 0);
      widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 18, 0, 500, true, false, false);
      widgets.addSlot((EmiIngredient)this.outputs.getFirst(), 43, 0).recipeContext(this).getRecipe();
   }
}
