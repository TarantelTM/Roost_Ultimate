package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.tarantel.chickenroost.recipes.Soul_Breeder_Recipe;

public class EmiSoulBreederRecipe extends BasicEmiRecipe {

    public EmiSoulBreederRecipe(Soul_Breeder_Recipe recipe) {
        super(EmiPlugin.SOUL_BREEDER_CATEGORY, recipe.getId(), 100, 60);
        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(1)));
        this.outputs.add(EmiStack.of(recipe.getResultEmi()));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 50, 20);
        widgets.addSlot(inputs.get(0), 0, +20);
        widgets.addSlot(inputs.get(1), 20, +20);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 50, 20,
                500, true, false, false);
        widgets.addSlot(outputs.get(0), 80, 20).recipeContext(this).getRecipe();
    }
}