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

   public int getHeight() {
      return 18;
   }

   public int getWidth(Font font) {
      return 22 + font.width(this.name);
   }

   public void renderImage(Font font, int x, int y, GuiGraphics gfx) {
      gfx.renderItem(this.stack, x, y);
      gfx.renderItemDecorations(font, this.stack, x, y);
      gfx.drawString(font, this.name, x + 18 + 4, y + 5, 10526880, false);
   }
}
