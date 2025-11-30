package net.tarantel.chickenroost.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.handler.RoostHandler;
import net.tarantel.chickenroost.networking.SetRoostNamePayload;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;
import net.minecraft.client.gui.components.EditBox;
import com.mojang.blaze3d.platform.InputConstants;


public class RoostScreen extends AbstractContainerScreen<RoostHandler> {
    public RoostScreen(RoostHandler menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private EditBox nameField;
    private String enteredName = "ROOST";
    private Button nameButton;

    private static final ResourceLocation GUI = ChickenRoostMod.ownresource("textures/screens/roostgui.png");
    private static final ResourceLocation ARROW = ChickenRoostMod.ownresource("textures/screens/newarrow.png");

    @Override
    protected void init() {
        super.init();

        int x = this.leftPos;
        int y = this.topPos - 17;


        int nameBtnX = this.leftPos + 20 + 2;
        int nameBtnY = this.topPos - 17;
        this.nameButton = Button.builder(Component.literal("Name"), btn -> {

            int fieldX = nameBtnX + 40 + 2;
            int fieldY = this.topPos - 21;
            if (this.nameField == null) {
                this.nameField = new EditBox(this.font, fieldX, fieldY, 60, 20, Component.literal("Name").withColor(16777215));
                this.nameField.setMaxLength(32);
                String currentname = this.menu.blockEntity.getCustomName();
                this.nameField.setValue(currentname);
                this.nameField.setFocused(true);
                this.addRenderableWidget(this.nameField);
            } else {
                this.nameField.setFocused(true);
            }
        }).pos(nameBtnX, nameBtnY).size(40, 13).build();
        this.nameButton.setTooltip(Tooltip.create(Component.literal("Set Name")));
        this.addRenderableWidget(this.nameButton);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.nameField != null && this.nameField.isFocused()) {

            if (keyCode == InputConstants.KEY_ESCAPE) {
                return super.keyPressed(keyCode, scanCode, modifiers);
            }


            if (keyCode == InputConstants.KEY_RETURN || keyCode == 257 || keyCode == 335) {
                this.enteredName = this.nameField.getValue().trim();
                if (!this.enteredName.isEmpty()) {
                     PacketDistributor.sendToServer(
                         new SetRoostNamePayload(this.menu.getBlockEntity().getBlockPos(), this.enteredName)
                     );
                }
                this.nameField.setFocused(false);
                return true;
            }


            if (this.nameField.keyPressed(keyCode, scanCode, modifiers)) {
                return true;
            }


            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (this.nameField != null && this.nameField.isFocused()) {

            if (this.nameField.charTyped(codePoint, modifiers)) {
                return true;
            }
            return true;
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics ms, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, GUI);
        ms.blit(GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        ms.blit(ARROW, this.leftPos + 63, this.topPos + 11, 0, 0, menu.getScaledProgress(), 33, 54, 33);
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