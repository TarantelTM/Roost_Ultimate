package net.tarantel.chickenroost.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.tarantel.chickenroost.handler.Breeder_Handler;
import org.jetbrains.annotations.NotNull;

public class Breeder_Screen extends AbstractContainerScreen<Breeder_Handler> {
    public Breeder_Screen(Breeder_Handler menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }
    private static final ResourceLocation GUI = new ResourceLocation("chicken_roost:textures/screens/breedergui8.png");
    private static final ResourceLocation ARROWBACK = new ResourceLocation("chicken_roost:textures/screens/arrowback.png");
    private static final ResourceLocation ARROW = new ResourceLocation("chicken_roost:textures/screens/arrow.png");
    @Override
    protected void init() {
        super.init();
    }



    @Override
    protected void renderBg(@NotNull GuiGraphics ms, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, GUI);
        ms.blit(GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        //RenderSystem.setShaderTexture(0, new ResourceLocation("chicken_roost:textures/screens/arrowback.png"));
        ms.blit(ARROWBACK, this.leftPos + 53, this.topPos + 30, 0, 0, 40, 10, 40, 10);
        //RenderSystem.setShaderTexture(0, new ResourceLocation("chicken_roost:textures/screens/arrow.png"));
        ms.blit(ARROW, this.leftPos + 53, this.topPos + 30, 0, 0, menu.getScaledProgress(), 10, 40, 10);
        RenderSystem.disableBlend();
    }
    @Override
    public void render(@NotNull GuiGraphics  ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms, mouseX, mouseY, partialTicks);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }

}