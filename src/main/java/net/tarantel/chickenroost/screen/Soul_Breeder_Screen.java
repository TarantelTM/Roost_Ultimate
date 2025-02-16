package net.tarantel.chickenroost.screen;

import net.minecraft.client.gui.GuiGraphics;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Inventory;
import net.tarantel.chickenroost.handler.SoulBreeder_Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


@SuppressWarnings("ALL")
public class Soul_Breeder_Screen extends AbstractContainerScreen<SoulBreeder_Handler> {
	private final static HashMap<String, Object> guistate = SoulBreeder_Handler.guistate;
	private final Level world;
	private final int x, y, z;
	//private final Player entity;
    
    public Soul_Breeder_Screen(SoulBreeder_Handler menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
		this.world = menu.level;
		this.x = menu.x;
		this.y = menu.y;
		this.z = menu.z;
		//this.entity = menu.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static final ResourceLocation GUI = new ResourceLocation("chicken_roost:textures/screens/newsoulbreedergui.png");
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
		//RenderSystem.setShaderTexture(0, new ResourceLocation("chicken_roost:textures/screens/arrow.png"));
		ms.blit(ARROW, this.leftPos + 59, this.topPos + 41, 0, 0, menu.getScaledProgress(), 10, 40, 10);

		/*RenderSystem.setShaderTexture(0, TEXTURE2);
		this.blit(ms, x + 211, y + 85, 0, 0, menu.getScaledProgress(), 10, 40, 10);*/
		//renderProgressArrow(ms, x, y);
		RenderSystem.disableBlend();


        
    }

    /*private void renderProgressArrow(PoseStack pPoseStack, int x, int y) {
        if(menu.isCrafting()) {
            blit(TEXTURE2, x + 211, y + 85, 0, 0, menu.getScaledProgress(), 10);
        }
    }*/

	@Override
	public void render(@NotNull GuiGraphics ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
	}

}