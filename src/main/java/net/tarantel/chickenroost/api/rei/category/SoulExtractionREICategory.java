package net.tarantel.chickenroost.api.rei.category;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.tarantel.chickenroost.api.rei.REIPlugin;
import net.tarantel.chickenroost.api.rei.displays.SoulExtractionREIDisplay;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import org.jetbrains.annotations.NotNull;

public class SoulExtractionREICategory implements DisplayCategory<SoulExtractionREIDisplay> {
   private final EntryStack<ItemStack> blaster = EntryStacks.of(new ItemStack((ItemLike)ModBlocks.SOUL_EXTRACTOR.get()));
   public static final CategoryIdentifier<SoulExtractionREIDisplay> SOULEXTRACTOR = CategoryIdentifier.of("chicken_roost", "soul_extraction");

   public CategoryIdentifier<? extends SoulExtractionREIDisplay> getCategoryIdentifier() {
      return SOULEXTRACTOR;
   }

   @NotNull
   public Renderer getIcon() {
      return this.blaster;
   }

   public Component getTitle() {
      return Component.literal("Soul Extractor");
   }

   public List<Widget> setupDisplay(SoulExtractionREIDisplay display, Rectangle bounds) {
      Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
      List<Widget> widgets = Lists.newArrayList();
      widgets.add(Widgets.createRecipeBase(bounds));
      widgets.add(REIPlugin.createAnimatedArrow(startPoint.x + 60, startPoint.y + 5));
      widgets.add(Widgets.createSlotBackground(new Point(startPoint.x + 90, startPoint.y + 5)));
      widgets.add(Widgets.createSlot(new Point(startPoint.x + 24 - 20, startPoint.y + 5)).entries((Collection)display.getInputEntries().get(0)).markInput());
      widgets.add(
         Widgets.createSlot(new Point(startPoint.x + 90, startPoint.y + 5))
            .entries((Collection)display.getOutputEntries().get(0))
            .disableBackground()
            .markOutput()
      );
      return widgets;
   }

   public int getDisplayHeight() {
      return 25;
   }
}
