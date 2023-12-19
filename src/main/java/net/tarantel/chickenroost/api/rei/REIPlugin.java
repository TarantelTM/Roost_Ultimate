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
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.tarantel.chickenroost.api.rei.category.*;
import net.tarantel.chickenroost.api.rei.displays.*;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.*;
import net.tarantel.chickenroost.screen.*;

import java.util.List;

@REIPluginClient
public class REIPlugin implements REIClientPlugin {

    public static final int SLOT_SIZE = 19;
    public REIPlugin() {
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new BreederREICategory());
        registry.addWorkstations(REICategoryIdentifiers.BREEDING, EntryStacks.of(ModBlocks.BREEDER.get()));
        registry.add(new RoostREICategoryV1());
        registry.addWorkstations(REICategoryIdentifiers.ROOST, EntryStacks.of(ModBlocks.ROOST.get()));
        registry.add(new SoulBreederREICategory());
        registry.addWorkstations(REICategoryIdentifiers.SOULBREEDING, EntryStacks.of(ModBlocks.SOUL_BREEDER.get()));
        registry.add(new SoulExtractionREICategory());
        registry.addWorkstations(REICategoryIdentifiers.SOULEXTRACTION, EntryStacks.of(ModBlocks.SOUL_EXTRACTOR.get()));
        registry.add(new TrainerREICategory());
        registry.addWorkstations(REICategoryIdentifiers.TRAINER, EntryStacks.of(ModBlocks.TRAINER.get()));
        //helper.addWorkstations(REICategoryIdentifiers.SOULBREEDING, EntryStacks.of(ModBlocks.ROOST.get()));
        //helper.addWorkstations(REICategoryIdentifiers.SOULEXTRACTION, EntryStacks.of(ModBlocks.ROOST.get()));
       // helper.addWorkstations(REICategoryIdentifiers.TRAINER, EntryStacks.of(ModBlocks.ROOST.get()));

    }

    @Override
    public void registerDisplays(DisplayRegistry helper) {
        helper.registerRecipeFiller(Breeder_Recipe.class, Breeder_Recipe.Type.INSTANCE, BreederREIDisplay::new);
        helper.registerRecipeFiller(Roost_Recipe.class, Roost_Recipe.Type.INSTANCE, RoostREIDisplayV1::new);
        helper.registerRecipeFiller(Soul_Breeder_Recipe.class, Soul_Breeder_Recipe.Type.INSTANCE, SoulBreederREIDisplay::new);
        helper.registerRecipeFiller(Soul_Extractor_Recipe.class, Soul_Extractor_Recipe.Type.INSTANCE, SoulExtractionREIDisplay::new);
        helper.registerRecipeFiller(Trainer_Recipe.class, Trainer_Recipe.Type.INSTANCE, TrainerREIDisplay::new);
        //helper.registerFiller(Soul_Breeder_Recipe.class, RoostREIDisplayV1::new);
       // helper.registerFiller(Soul_Extractor_Recipe.class, RoostREIDisplayV1::new);
       // helper.registerFiller(Trainer_Recipe.class, RoostREIDisplayV1::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(53, 30, 40, 10), Breeder_Screen.class, BreederREIDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), Roost_Screen.class, RoostREIDisplayV1.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), Soul_Breeder_Screen.class, SoulBreederREIDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), Soul_Extractor_Screen.class, SoulExtractionREIDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), Trainer_Screen.class, TrainerREIDisplay.ID);
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
    public static Slot createOutputSlotTrainer(BasicDisplay display, int index, int x, int y) {
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