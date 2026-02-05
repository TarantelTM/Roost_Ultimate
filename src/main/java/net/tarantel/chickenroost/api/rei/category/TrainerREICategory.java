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
import net.tarantel.chickenroost.api.rei.displays.TrainerREIDisplay;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.tarantel.chickenroost.api.rei.REIPlugin.*;

public class TrainerREICategory implements DisplayCategory<TrainerREIDisplay> {
    private final EntryStack<ItemStack> blaster = EntryStacks.of(new ItemStack(ModBlocks.TRAINER.get()));

    public static final CategoryIdentifier<TrainerREIDisplay> TRAINER =
            CategoryIdentifier.of(ChickenRoostMod.MODID, "trainer_output");

    @Override
    public CategoryIdentifier<? extends TrainerREIDisplay> getCategoryIdentifier() {
        return TRAINER;
    }

    @Override
    public @NotNull Renderer getIcon() {
        return blaster;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Trainer");
    }



    @Override
    public List<Widget> setupDisplay(TrainerREIDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(createAnimatedArrow(startPoint.x + 60, startPoint.y + 5));
        widgets.add(Widgets.createSlotBackground(new Point(startPoint.x + 90, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4 - 20, startPoint.y + 5))
                .entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 90, startPoint.y + 5))
                .entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 25;
    }
}