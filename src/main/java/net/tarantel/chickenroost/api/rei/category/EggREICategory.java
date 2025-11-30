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
import net.tarantel.chickenroost.api.rei.displays.EggREIDisplay;
import net.tarantel.chickenroost.item.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.tarantel.chickenroost.api.rei.REIPlugin.*;

public class EggREICategory implements DisplayCategory<EggREIDisplay> {
    private final EntryStack<ItemStack> blaster = EntryStacks.of(new ItemStack(ModItems.CHICKEN_STICK.get()));

    public static final CategoryIdentifier<EggREIDisplay> EGG =
            CategoryIdentifier.of(ChickenRoostMod.MODID, "throwegg");

    @Override
    public CategoryIdentifier<? extends EggREIDisplay> getCategoryIdentifier() {
        return EGG;
    }

    @Override
    public @NotNull Renderer getIcon() {
        return blaster;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Egg Throwing");
    }

    @Override
    public List<Widget> setupDisplay(EggREIDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(createAnimatedArrow(startPoint.x + 30, startPoint.y + 5));
        widgets.add(Widgets.createSlotBackground(new Point(startPoint.x + 55, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 15 - 5, startPoint.y + 5))
                .entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 55, startPoint.y + 5))
                .entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 25;
    }

    @Override
    public int getDisplayWidth(EggREIDisplay display) {
        return 70;
    }
}