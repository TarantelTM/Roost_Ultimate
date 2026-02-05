package net.tarantel.chickenroost.api.rei.category;

import com.google.common.collect.Lists;
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
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.api.rei.displays.BreederREIDisplay;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.tarantel.chickenroost.api.rei.REIPlugin.*;


public class BreederREICategory implements DisplayCategory<BreederREIDisplay> {
    private final EntryStack<ItemStack> blaster = EntryStacks.of(new ItemStack(ModBlocks.BREEDER.get()));

    public static final CategoryIdentifier<BreederREIDisplay> BREEDER =
            CategoryIdentifier.of(ChickenRoostMod.MODID, "basic_breeding");

    @Override
    public CategoryIdentifier<? extends BreederREIDisplay> getCategoryIdentifier() {
        return BREEDER;
    }

    @Override
    public @NotNull Renderer getIcon() {
        return blaster;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Breeder");
    }

    /*@Override
    public List<Widget> setupDisplay(BreederREIDisplay display, Rectangle bounds) {
        // Setup background
        Point startPoint = new Point(bounds.getCenterX() - 36, bounds.getCenterY() - 10);
        List<Widget> widgets = Lists.newArrayList();
        bounds.setSize(130, 20);
        widgets.add(Widgets.createRecipeBase(bounds));

        // Add arrow
        widgets.add(createAnimatedArrow(startPoint.x + 4, startPoint.y + 4));

        //Add output slot
        widgets.add(Widgets.createSlotBackground(new Point(startPoint.x + 61, startPoint.y + 0)));
        widgets.add(createOutputSlot(display, 0, startPoint.x + 61, startPoint.y + 0));

        // Add input slots
        int x = startPoint.x-14;
        int y = startPoint.y;
        widgets.add(createInputSlot(display, 0, x, y));
        widgets.add(createInputSlot(display, 1, x+SLOT_SIZE, y));
        widgets.add(createInputSlot(display, 2, x+SLOT_SIZE+SLOT_SIZE, y));
        return widgets;
    }*/

    @Override
    public List<Widget> setupDisplay(BreederREIDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(createAnimatedArrow(startPoint.x + 60, startPoint.y + 5));
        widgets.add(Widgets.createSlotBackground(new Point(startPoint.x + 90, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4 - 20, startPoint.y + 5))
                .entries(display.getInputEntries().get(1)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 24 - 20, startPoint.y + 5))
                .entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 44 - 20, startPoint.y + 5))
                .entries(display.getInputEntries().get(2)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 90, startPoint.y + 5))
                .entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 25;
    }
}