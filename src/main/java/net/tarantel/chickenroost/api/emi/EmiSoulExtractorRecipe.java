package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.tarantel.chickenroost.recipes.Soul_Extractor_Recipe;

public class EmiSoulExtractorRecipe extends BasicEmiRecipe {

    public EmiSoulExtractorRecipe(Soul_Extractor_Recipe recipe) {
        super(EmiPlugin.SOUL_EXTRACTOR_CATEGORY, recipe.getId(), 100, 60);
        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        this.outputs.add(EmiStack.of(recipe.getResultEmi()));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 50, 20);
        widgets.addSlot(inputs.get(0), 0, +20);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 50, 20,
                500, true, false, false);
        widgets.addSlot(outputs.get(0), 80, 20).recipeContext(this).getRecipe();
    }
}