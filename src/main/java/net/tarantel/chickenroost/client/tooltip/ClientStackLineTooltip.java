//package net.tarantel.chickenroost.client.tooltip;
//
//import net.minecraft.client.gui.Font;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.item.ItemStack;
//
//public class ClientStackLineTooltip implements ClientTooltipComponent {
//
//    private final ItemStack stack;
//    private final Component name;
//
//    public ClientStackLineTooltip(StackLineTooltip data) {
//        this.stack = data.stack().copy();
//        this.stack.setCount(1);
//        this.name = this.stack.getHoverName();
//    }
//
//
//    @Override
//    public int getHeight(Font font) {
//        return 18;
//    }
//
//    @Override
//    public int getWidth(Font font) {
//        return 18 + 4 + font.width(name);
//    }
//
//    @Override
//    public void renderImage(Font font, int x, int y, int width, int height, GuiGraphics gfx) {
//        gfx.renderItem(stack, x, y);
//        gfx.renderItemDecorations(font, stack, x, y);
//        gfx.drawString(font, name, x + 18 + 4, y + 5, 0xA0A0A0, false);
//    }
//}
//