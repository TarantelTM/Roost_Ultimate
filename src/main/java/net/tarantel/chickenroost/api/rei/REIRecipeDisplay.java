package net.tarantel.chickenroost.api.rei;

import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@REIPluginClient
public abstract class REIRecipeDisplay<T extends Recipe<CraftingInput>> implements Display {
    protected final T recipe;
    protected List<EntryIngredient> inputs;
    protected EntryIngredient outputs;

    public REIRecipeDisplay(T recipe) {
        this.recipe = recipe;
        this.inputs = EntryIngredients.ofIngredients(recipe.getIngredients());
        this.outputs = EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess()));
    }

    @Override
    public @NotNull List<EntryIngredient> getInputEntries() {
        return this.inputs;
    }


    @Override
    public @NotNull List<EntryIngredient> getOutputEntries() {
        return Collections.singletonList(this.outputs);
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.empty();
    }
}