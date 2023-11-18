package net.tarantel.chickenroost.api.rei.displays;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.util.Identifier;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.recipemanager.Roost_Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RoostREIDisplayV1 extends BasicDisplay {

    public static final CategoryIdentifier<RoostREIDisplayV1> ID = CategoryIdentifier.of(ChickenRoostMod.MODID, "roost");
    public RoostREIDisplayV1(Roost_Recipe recipe) {
        this(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.newOutput())), Optional.ofNullable(recipe.getId()));
    }


    public RoostREIDisplayV1(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }
    @Override
    public @NotNull CategoryIdentifier<?> getCategoryIdentifier() {
        return ID;
    }
}