package net.tarantel.chickenroost.api.rei.displays;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.crafting.RecipeHolder;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.recipes.BreederRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BreederREIDisplay extends BasicDisplay {

    public BreederREIDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public static final CategoryIdentifier<BreederREIDisplay> ID = CategoryIdentifier.of(ChickenRoostMod.MODID, "basic_breeding");

    public BreederREIDisplay(RecipeHolder<BreederRecipe> recipe){
        super(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryIngredients.of(recipe.value().getResultItem(null)))));
    }

    public BreederREIDisplay(List<EntryIngredient> input, List<EntryIngredient> output, Optional<ResourceLocation> location) {
        super(input, output, location);
    }

    private static List<EntryIngredient> getInputList(BreederRecipe recipe) {
        if(recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(1)));
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(2)));
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {

        return ID;
    }
}