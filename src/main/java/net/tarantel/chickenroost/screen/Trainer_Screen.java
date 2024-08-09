package net.tarantel.chickenroost.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.handler.Trainer_Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Trainer_Screen extends AbstractContainerScreen<Trainer_Handler> {
    private final static HashMap<String, Object> guistate = Trainer_Handler.guistate;
    private final Level world;
    private final int x, y, z;

    public Trainer_Screen(Trainer_Handler menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.world = menu.level;
        this.x = menu.x;
        this.y = menu.y;
        this.z = menu.z;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private static final ResourceLocation GUI = ChickenRoostMod.ownresource("textures/screens/newsoulextractorgui.png");
    private static final ResourceLocation ARROW = ChickenRoostMod.ownresource("textures/screens/arrow.png");
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
        RenderSystem.setShaderTexture(0, ChickenRoostMod.ownresource("textures/screens/arrow.png"));
        ms.blit(ARROW, this.leftPos + 59, this.topPos + 41, 0, 0, menu.getScaledProgress(), 10, 40, 10);
        RenderSystem.disableBlend();
    }
    @Override
    public void render(@NotNull GuiGraphics ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms, mouseX, mouseY, partialTicks);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }
}