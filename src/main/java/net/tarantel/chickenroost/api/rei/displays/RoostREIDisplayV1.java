package net.tarantel.chickenroost.api.rei.displays;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;

import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.recipes.Roost_Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RoostREIDisplayV1 extends BasicDisplay {

    public RoostREIDisplayV1(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public static final CategoryIdentifier<RoostREIDisplayV1> ID = CategoryIdentifier.of(ChickenRoostMod.MODID, "roost_output");


    public RoostREIDisplayV1(Roost_Recipe recipe){
        super(getInputList(recipe), List.of(EntryIngredient.of(EntryIngredients.of(recipe.getResultItem(null)))));
    }

    public RoostREIDisplayV1(List<EntryIngredient> input, List<EntryIngredient> output, Optional<ResourceLocation> location) {
        super(input, output, location);
    }

    private static List<EntryIngredient> getInputList(Roost_Recipe recipe) {
        if(recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(1)));
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {

        return ID;
    }
}