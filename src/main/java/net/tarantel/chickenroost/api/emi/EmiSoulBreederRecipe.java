package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.core.RegistryAccess;
import net.tarantel.chickenroost.recipes.Soul_Breeder_Recipe;

public class EmiSoulBreederRecipe extends BasicEmiRecipe {

    public EmiSoulBreederRecipe(Soul_Breeder_Recipe recipe) {
        super(EmiPlugin.SOUL_BREEDER_CATEGORY, recipe.getId(), 100, 20);
        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        this.catalysts.add(EmiIngredient.of(recipe.getIngredients().get(1)));
        this.outputs.add(EmiStack.of(recipe.getResultItem(RegistryAccess.EMPTY)));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 50, 0);
        widgets.addSlot(inputs.get(0), 0, 0);
        widgets.addSlot(catalysts.get(0), 20, 0);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 50, 0,
                500, true, false, false);
        widgets.addSlot(outputs.get(0), 80, 0).recipeContext(this).getRecipe();
    }
}