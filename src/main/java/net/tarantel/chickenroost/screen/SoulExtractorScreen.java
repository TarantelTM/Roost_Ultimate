package net.tarantel.chickenroost.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.handler.SoulExtractorHandler;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

public class SoulExtractorScreen extends AbstractContainerScreen<SoulExtractorHandler> {


    public SoulExtractorScreen(SoulExtractorHandler menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageWidth = 176;
        this.imageHeight = 166;


    }

    private static final ResourceLocation GUI = ChickenRoostMod.ownresource("textures/screens/soulextractorgui.png");
    private static final ResourceLocation ARROW = ChickenRoostMod.ownresource("textures/screens/newarrow.png");
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
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        ms.blit(ARROW, this.leftPos + 54, this.topPos + 31, 0, 0, menu.getScaledProgress(), 33, 54, 33);
        RenderSystem.disableBlend();
    }
    @Override
    public void render(@NotNull GuiGraphics ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms, mouseX, mouseY, partialTicks);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {

    }
}