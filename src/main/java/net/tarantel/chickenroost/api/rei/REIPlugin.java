package net.tarantel.chickenroost.api.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Arrow;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.tarantel.chickenroost.api.rei.category.BreederREICategory;
import net.tarantel.chickenroost.api.rei.category.RoostREICategoryV1;
import net.tarantel.chickenroost.api.rei.displays.BreederREIDisplay;
import net.tarantel.chickenroost.api.rei.displays.RoostREIDisplayV1;
import net.tarantel.chickenroost.block.ModBlocks;
import net.tarantel.chickenroost.client.screen.breeder_screen;
import net.tarantel.chickenroost.client.screen.roost_screen;
import net.tarantel.chickenroost.recipemanager.BasicBreedingRecipe;
import net.tarantel.chickenroost.recipemanager.Roost_Recipe;

import java.util.List;

@Environment(EnvType.CLIENT)
public class REIPlugin implements REIClientPlugin {

    public static final int SLOT_SIZE = 19;
    public REIPlugin() {
    }

    @Override
    public void registerCategories(CategoryRegistry helper) {
        helper.add(List.of(
                new BreederREICategory(),
                new RoostREICategoryV1()
        ));
        helper.addWorkstations(REICategoryIdentifiers.BREEDING, EntryStacks.of(ModBlocks.BREEDER));
        helper.addWorkstations(REICategoryIdentifiers.ROOST, EntryStacks.of(ModBlocks.ROOST));
    }

    @Override
    public void registerDisplays(DisplayRegistry helper) {
        helper.registerFiller(BasicBreedingRecipe.class, BreederREIDisplay::new);
        helper.registerFiller(Roost_Recipe.class, RoostREIDisplayV1::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(53, 30, 40, 10), breeder_screen.class, BreederREIDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), roost_screen.class, RoostREIDisplayV1.ID);
    }
    
    public static Slot createInputSlot(BasicDisplay display, int index, int x, int y) {
        if (index >= display.getInputEntries().size()) {
            return Widgets.createSlot(new Point(x, y));
        }
        EntryIngredient ingredient = display.getInputEntries().get(index);
        return Widgets.createSlot(new Point(x, y)).entries(ingredient).markInput();
    }

    
    public static Slot createOutputSlot(BasicDisplay display, int index, int x, int y) {
        if (index >= display.getOutputEntries().size()) {
            return Widgets.createSlot(new Point(x, y));
        }
        EntryIngredient outputIngredient = display.getOutputEntries().get(index);
        return Widgets.createSlot(new Point(x, y)).entries(outputIngredient).disableBackground().markOutput();
    }

   
    public static Arrow createAnimatedArrow(int x, int y) {
        return Widgets.createArrow(new Point(x, y)).animationDurationTicks(20*3);
    }
}
