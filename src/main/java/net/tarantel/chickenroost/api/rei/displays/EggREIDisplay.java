package net.tarantel.chickenroost.api.rei.displays;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.recipes.ThrowEggRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EggREIDisplay extends BasicDisplay {

    public EggREIDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public static final CategoryIdentifier<EggREIDisplay> ID = CategoryIdentifier.of(ChickenRoostMod.MODID, "throwegg");


    public EggREIDisplay(RecipeHolder<ThrowEggRecipe> recipe){
        super(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryIngredients.of(recipe.value().getResultItem(null)))));
    }

    public EggREIDisplay(List<EntryIngredient> input, List<EntryIngredient> output, Optional<ResourceLocation> location) {
        super(input, output, location);
    }

    private static List<EntryIngredient> getInputList(ThrowEggRecipe recipe) {
        if(recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {

        return ID;
    }
}