package net.tarantel.chickenroost.client.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ClientStackLineTooltip implements ClientTooltipComponent {

    private final ItemStack stack;
    private final Component name;

    public ClientStackLineTooltip(StackLineTooltip data) {
        this.stack = data.stack().copy();
        this.stack.setCount(1);
        this.name = this.stack.getHoverName();
    }

    @Override
    public int getHeight() {
        return 18 + 2; // kleiner Abstand
    }

    @Override
    public int getWidth(Font font) {
        return 18 + 6 + font.width(name);
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics gfx) {
        int iconY = y + 1;

        gfx.renderItem(stack, x, iconY);
        gfx.renderItemDecorations(font, stack, x, iconY);
        gfx.drawString(font, name, x + 18 + 6, iconY + 5, 0xA0A0A0, false);
    }
}
