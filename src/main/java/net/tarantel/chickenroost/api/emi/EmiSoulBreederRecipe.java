package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.tarantel.chickenroost.recipes.SoulBreederRecipe;

public class EmiSoulBreederRecipe extends BasicEmiRecipe {

    public EmiSoulBreederRecipe(SoulBreederRecipe recipe) {
        super(EmiRoostPlugin.SOUL_BREEDER_CATEGORY, recipe.getId(), 100, 20);
        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(1)));
        this.outputs.add(EmiStack.of(recipe.getResultEmi()));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 50, 0);
        widgets.addSlot(inputs.get(0), 0, 0);
        widgets.addSlot(inputs.get(1), 20, 0);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 50, 0,
                500, true, false, false);
        widgets.addSlot(outputs.getFirst(), 80, 0).recipeContext(this).getRecipe();
    }
}