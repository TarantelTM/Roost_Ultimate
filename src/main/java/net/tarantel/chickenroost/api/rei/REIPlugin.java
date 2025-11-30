package net.tarantel.chickenroost.api.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Arrow;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
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


    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new EggREICategory());
        registry.addWorkstations(REICategoryIdentifiers.EGG, EntryStacks.of(ModItems.CHICKEN_STICK.get()));
        registry.add(new BreederREICategory());
        registry.addWorkstations(REICategoryIdentifiers.BREEDING, EntryStacks.of(ModBlocks.BREEDER.get()));
        registry.add(new RoostREICategory());
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
        helper.registerRecipeFiller(BreederRecipe.class, BreederRecipe.Type.INSTANCE, BreederREIDisplay::new);
        helper.registerRecipeFiller(RoostRecipe.class, RoostRecipe.Type.INSTANCE, RoostREIDisplay::new);
        helper.registerRecipeFiller(SoulBreederRecipe.class, SoulBreederRecipe.Type.INSTANCE, SoulBreederREIDisplay::new);
        helper.registerRecipeFiller(SoulExtractorRecipe.class, SoulExtractorRecipe.Type.INSTANCE, SoulExtractionREIDisplay::new);
        helper.registerRecipeFiller(TrainerRecipe.class, TrainerRecipe.Type.INSTANCE, TrainerREIDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(53, 30, 40, 10), BreederScreen.class, BreederREIDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), RoostScreen.class, RoostREIDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), SoulBreederScreen.class, SoulBreederREIDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), SoulExtractorScreen.class, SoulExtractionREIDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), TrainerScreen.class, TrainerREIDisplay.ID);
    }


    public static Arrow createAnimatedArrow(int x, int y) {
        return Widgets.createArrow(new Point(x, y)).animationDurationTicks(20*3);
    }
}