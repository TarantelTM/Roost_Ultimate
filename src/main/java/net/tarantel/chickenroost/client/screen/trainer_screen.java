package net.tarantel.chickenroost.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tarantel.chickenroost.handler.trainer_handler;

public class trainer_screen extends HandledScreen<trainer_handler> {

    private static final Identifier TEXTURE =
            new Identifier("chicken_roost:textures/screens/newtrainerguinew.png");
    private static final Identifier ARROW =
            new Identifier("chicken_roost:textures/screens/arrow.png");
    private static final Identifier ARROWBACK =
            new Identifier("chicken_roost:textures/screens/arrowback.png");

    public trainer_screen(trainer_handler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
    }


    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        context.drawTexture(ARROWBACK,  x + 70, y + 47, 39, 10, 0, 0, 39, 10, 39, 10);
        context.drawTexture(ARROW,  x + 70, y + 47, handler.getScaledProgress(), 10, 0, 0, handler.getScaledProgress(), 10, 39, 10);
        //drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
        //RenderSystem.setShaderTexture(0, ARROWBACK);
        //drawTexture(matrices,  x + 70, y + 47, 39, 10, 0, 0, 39, 10, 39, 10);
        //RenderSystem.setShaderTexture(0, ARROW);
       // drawTexture(matrices,  x + 70, y + 47, handler.getScaledProgress(), 10, 0, 0, handler.getScaledProgress(), 10, 39, 10);
        RenderSystem.disableBlend();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
