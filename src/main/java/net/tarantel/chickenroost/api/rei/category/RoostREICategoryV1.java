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
import net.tarantel.chickenroost.api.rei.displays.RoostREIDisplayV1;

import net.tarantel.chickenroost.block.blocks.ModBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.tarantel.chickenroost.api.rei.REIPlugin.*;

public class RoostREICategoryV1 implements DisplayCategory<RoostREIDisplayV1> {
    private final EntryStack<ItemStack> blaster = EntryStacks.of(new ItemStack(ModBlocks.ROOST.get()));
    public static final CategoryIdentifier<RoostREIDisplayV1> ROOST =
            CategoryIdentifier.of(ChickenRoostMod.MODID, "roost_output");
    @Override
    public CategoryIdentifier<? extends RoostREIDisplayV1> getCategoryIdentifier() {
        return ROOST;
    }

    @Override
    public @NotNull Renderer getIcon() {
        return blaster;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Roost");
    }

    @Override
    public List<Widget> setupDisplay(RoostREIDisplayV1 display, Rectangle bounds) {
        // Setup background
        Point startPoint = new Point(bounds.getCenterX() - 36, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));

        // Add arrow
        widgets.add(createAnimatedArrow(startPoint.x + 27, startPoint.y + 4));

        //Add output slot
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets.add(createOutputSlot(display, 0, startPoint.x + 61, startPoint.y + 5));

        // Add input slots
        int x = startPoint.x-14;
        int y = startPoint.y - 5;
        widgets.add(createInputSlot(display, 0, x, y));
        widgets.add(createInputSlot(display, 1, x+SLOT_SIZE, y));
        return widgets;
    }

}