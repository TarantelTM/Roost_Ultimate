package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.tarantel.chickenroost.recipes.Breeder_Recipe;

public class EmiBreederRecipe extends BasicEmiRecipe {

    public EmiBreederRecipe(Breeder_Recipe recipe) {
        super(EmiPlugin.BREEDER_CATEGORY, recipe.getId(), 100, 20);
        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        this.catalysts.add(EmiIngredient.of(recipe.getIngredients().get(1)));
        this.catalysts.add(EmiIngredient.of(recipe.getIngredients().get(2)));
        this.outputs.add(EmiStack.of(recipe.getResultEmi()));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 58, 0);
        widgets.addSlot(inputs.get(0), 20, 0);
        widgets.addSlot(catalysts.get(0), 0, 0);
        widgets.addSlot(catalysts.get(1), 40, 0);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 58, 0,
                500, true, false, false);
        widgets.addSlot(outputs.get(0), 82, 0).recipeContext(this).getRecipe();
    }
}