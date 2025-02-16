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
import net.tarantel.chickenroost.api.rei.category.*;
import net.tarantel.chickenroost.api.rei.displays.*;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.*;
import net.tarantel.chickenroost.screen.*;


@REIPluginClient
public class REIPlugin implements REIClientPlugin {

    public static final int SLOT_SIZE = 19;
    public REIPlugin() {
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new EggREICategory());
        registry.addWorkstations(REICategoryIdentifiers.EGG, EntryStacks.of(ModItems.CHICKEN_STICK.get()));
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

    }

    @Override
    public void registerDisplays(DisplayRegistry helper) {
        helper.registerRecipeFiller(ThrowEggRecipe.class, ThrowEggRecipe.Type.INSTANCE, EggREIDisplay::new);
        helper.registerRecipeFiller(Breeder_Recipe.class, Breeder_Recipe.Type.INSTANCE, BreederREIDisplay::new);
        helper.registerRecipeFiller(Roost_Recipe.class, Roost_Recipe.Type.INSTANCE, RoostREIDisplayV1::new);
        helper.registerRecipeFiller(Soul_Breeder_Recipe.class, Soul_Breeder_Recipe.Type.INSTANCE, SoulBreederREIDisplay::new);
        helper.registerRecipeFiller(Soul_Extractor_Recipe.class, Soul_Extractor_Recipe.Type.INSTANCE, SoulExtractionREIDisplay::new);
        helper.registerRecipeFiller(Trainer_Recipe.class, Trainer_Recipe.Type.INSTANCE, TrainerREIDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(53, 30, 40, 10), Breeder_Screen.class, BreederREIDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), Roost_Screen.class, RoostREIDisplayV1.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), Soul_Breeder_Screen.class, SoulBreederREIDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), Soul_Extractor_Screen.class, SoulExtractionREIDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), Trainer_Screen.class, TrainerREIDisplay.ID);
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

    public static Slot createOutputSlotTrainer(BasicDisplay display, int index, int x, int y) {
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