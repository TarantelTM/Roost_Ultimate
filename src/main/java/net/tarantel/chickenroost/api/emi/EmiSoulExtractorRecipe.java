package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.tarantel.chickenroost.recipes.SoulExtractorRecipe;

public class EmiSoulExtractorRecipe extends BasicEmiRecipe {

    public EmiSoulExtractorRecipe(SoulExtractorRecipe recipe) {
        super(EmiRoostPlugin.SOUL_EXTRACTOR_CATEGORY, recipe.getId(), 100, 20);
        this.inputs.add(EmiIngredient.of(recipe.getIngredients().getFirst()));
        this.outputs.add(EmiStack.of(recipe.getResultEmi()));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 50, 0);
        widgets.addSlot(inputs.getFirst(), 0, 0);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 50, 0,
                500, true, false, false);
        widgets.addSlot(outputs.getFirst(), 80, 0).recipeContext(this).getRecipe();
    }
}