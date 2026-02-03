package net.tarantel.chickenroost.api.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Arrow;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.world.level.ItemLike;
import net.tarantel.chickenroost.api.rei.category.BreederREICategory;
import net.tarantel.chickenroost.api.rei.category.EggREICategory;
import net.tarantel.chickenroost.api.rei.category.RoostREICategory;
import net.tarantel.chickenroost.api.rei.category.SoulExtractionREICategory;
import net.tarantel.chickenroost.api.rei.category.TrainerREICategory;
import net.tarantel.chickenroost.api.rei.displays.BreederREIDisplay;
import net.tarantel.chickenroost.api.rei.displays.EggREIDisplay;
import net.tarantel.chickenroost.api.rei.displays.RoostREIDisplay;
import net.tarantel.chickenroost.api.rei.displays.SoulExtractionREIDisplay;
import net.tarantel.chickenroost.api.rei.displays.TrainerREIDisplay;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.recipes.BreederRecipe;
import net.tarantel.chickenroost.recipes.RoostRecipe;
import net.tarantel.chickenroost.recipes.SoulExtractorRecipe;
import net.tarantel.chickenroost.recipes.ThrowEggRecipe;
import net.tarantel.chickenroost.recipes.TrainerRecipe;
import net.tarantel.chickenroost.screen.BreederScreen;
import net.tarantel.chickenroost.screen.RoostScreen;
import net.tarantel.chickenroost.screen.SoulExtractorScreen;
import net.tarantel.chickenroost.screen.TrainerScreen;

@REIPluginClient
public class REIPlugin implements REIClientPlugin {
   public void registerCategories(CategoryRegistry registry) {
      registry.add(new EggREICategory());
      registry.addWorkstations(REICategoryIdentifiers.EGG, new EntryStack[]{EntryStacks.of((ItemLike)ModItems.CHICKEN_STICK.get())});
      registry.add(new BreederREICategory());
      registry.addWorkstations(REICategoryIdentifiers.BREEDING, new EntryStack[]{EntryStacks.of((ItemLike)ModBlocks.BREEDER.get())});
      registry.add(new RoostREICategory());
      registry.addWorkstations(REICategoryIdentifiers.ROOST, new EntryStack[]{EntryStacks.of((ItemLike)ModBlocks.ROOST.get())});
      registry.add(new SoulExtractionREICategory());
      registry.addWorkstations(REICategoryIdentifiers.SOULEXTRACTION, new EntryStack[]{EntryStacks.of((ItemLike)ModBlocks.SOUL_EXTRACTOR.get())});
      registry.add(new TrainerREICategory());
      registry.addWorkstations(REICategoryIdentifiers.TRAINER, new EntryStack[]{EntryStacks.of((ItemLike)ModBlocks.TRAINER.get())});
   }

   public void registerDisplays(DisplayRegistry helper) {
      helper.registerRecipeFiller(ThrowEggRecipe.class, ThrowEggRecipe.Type.INSTANCE, EggREIDisplay::new);
      helper.registerRecipeFiller(BreederRecipe.class, BreederRecipe.Type.INSTANCE, BreederREIDisplay::new);
      helper.registerRecipeFiller(RoostRecipe.class, RoostRecipe.Type.INSTANCE, RoostREIDisplay::new);
      helper.registerRecipeFiller(SoulExtractorRecipe.class, SoulExtractorRecipe.Type.INSTANCE, SoulExtractionREIDisplay::new);
      helper.registerRecipeFiller(TrainerRecipe.class, TrainerRecipe.Type.INSTANCE, TrainerREIDisplay::new);
   }

   public void registerScreens(ScreenRegistry registry) {
      registry.registerContainerClickArea(new Rectangle(53, 41, 40, 10), BreederScreen.class, new CategoryIdentifier[]{BreederREIDisplay.ID});
      registry.registerContainerClickArea(new Rectangle(59, 25, 40, 10), RoostScreen.class, new CategoryIdentifier[]{RoostREIDisplay.ID});
      registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), SoulExtractorScreen.class, new CategoryIdentifier[]{SoulExtractionREIDisplay.ID});
      registry.registerContainerClickArea(new Rectangle(59, 41, 40, 10), TrainerScreen.class, new CategoryIdentifier[]{TrainerREIDisplay.ID});
   }

   public static Arrow createAnimatedArrow(int x, int y) {
      return Widgets.createArrow(new Point(x, y)).animationDurationTicks(60.0);
   }
}
