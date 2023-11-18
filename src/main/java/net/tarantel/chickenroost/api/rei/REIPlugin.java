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
                //new CombinerREICategory(),
                //new UpgraderREICategory(),
                //new LightningChannelerREICategory()
        ));
        helper.addWorkstations(REICategoryIdentifiers.BREEDING, EntryStacks.of(ModBlocks.BREEDER));
        helper.addWorkstations(REICategoryIdentifiers.ROOST, EntryStacks.of(ModBlocks.ROOST));
        //helper.addWorkstations(ThommasREICategoryIdentifiers.COMBINER, EntryStacks.of(ModBlocks.COMBINER));
        //helper.addWorkstations(ThommasREICategoryIdentifiers.UPGRADER, EntryStacks.of(ModBlocks.UPGRADER));
        //helper.addWorkstations(ThommasREICategoryIdentifiers.LIGHTNING_CHANNELER, EntryStacks.of(ModBlocks.LIGHTNING_CHANNELER_BLOCK));
    }

    @Override
    public void registerDisplays(DisplayRegistry helper) {
        helper.registerFiller(BasicBreedingRecipe.class, BreederREIDisplay::new);
        helper.registerFiller(Roost_Recipe.class, RoostREIDisplayV1::new);
        //helper.registerFiller(UpgraderRecipe.class, UpgraderREIDisplay::new);
        //helper.registerFiller(CombinerRecipe.class, CombinerREIDisplay::new);
        //helper.registerFiller(LightningChannelerRecipe.class, LightningChannelerREIDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(53, 30, 40, 10), breeder_screen.class, BreederREIDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), roost_screen.class, RoostREIDisplayV1.ID);
        //registry.registerContainerClickArea(new Rectangle(58, 41, 27, 5), AtomizerScreen.class, AtomizerRecipeDisplay.ID);
        //registry.registerContainerClickArea(new Rectangle(90, 41, 27, 5), LiquifierScreen.class, LiquifierRecipeDisplay.ID);
        //registry.registerContainerClickArea(new Rectangle(75, 41, 27, 5), CompactorScreen.class, CompactorRecipeDisplay.ID);
        //registry.registerContainerClickArea(new Rectangle(64, 86, 27, 5), CombinerScreen.class, CombinerRecipeDisplay.ID);
        //registry.registerContainerClickArea(new Rectangle(74, 41, 27, 5), FissionControllerScreen.class, FissionRecipeDisplay.ID);
        //registry.registerContainerClickArea(new Rectangle(91, 41, 27, 5), FusionControllerScreen.class, FusionRecipeDisplay.ID);
    }
    /**
     * Creates an input slot at a specified point with the specified ingredient index.
     *
     * @param display the display to get recipe data from.
     * @param index the index of the input ingredient to display.
     * @param x the x-coordinate of the top left corner of the slot.
     * @param y the y-coordinate of the top left corner of the slot.
     * @return a widget object to be added to display widget list.
     */
    public static Slot createInputSlot(BasicDisplay display, int index, int x, int y) {
        if (index >= display.getInputEntries().size()) {
            return Widgets.createSlot(new Point(x, y));
        }
        EntryIngredient ingredient = display.getInputEntries().get(index);
        return Widgets.createSlot(new Point(x, y)).entries(ingredient).markInput();
    }

    /**
     * Creates an output slot at a specified point with the specified ingredient index.
     *
     * @param display the display to get recipe data from.
     * @param index the index of the output ingredient to display.
     * @param x the x-coordinate of the top left corner of the slot.
     * @param y the y-coordinate of the top left corner of the slot.
     * @return a widget object to be added to display widget list.
     */
    public static Slot createOutputSlot(BasicDisplay display, int index, int x, int y) {
        if (index >= display.getOutputEntries().size()) {
            return Widgets.createSlot(new Point(x, y));
        }
        EntryIngredient outputIngredient = display.getOutputEntries().get(index);
        return Widgets.createSlot(new Point(x, y)).entries(outputIngredient).disableBackground().markOutput();
    }

    /**
     * Creates an animated progress bar starting at point x,y.
     *
     * @param x the x-coordinate of the starting point.
     * @param y the y-coordinate of the starting point.
     * @return arrow widget object to be added to display widget list.
     */
    public static Arrow createAnimatedArrow(int x, int y) {
        return Widgets.createArrow(new Point(x, y)).animationDurationTicks(20*3);
    }
}