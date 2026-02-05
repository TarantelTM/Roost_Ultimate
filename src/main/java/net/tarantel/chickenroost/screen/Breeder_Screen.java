package net.tarantel.chickenroost.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.handler.Breeder_Handler;
import net.tarantel.chickenroost.handler.Roost_Handler;
import net.tarantel.chickenroost.network.ModMessages;
import net.tarantel.chickenroost.network.SetBreederAutoOutputPayload;
import net.tarantel.chickenroost.network.SetBreederNamePayload;
import net.tarantel.chickenroost.network.SetRoostNamePayload;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

public class Breeder_Screen extends AbstractContainerScreen<Breeder_Handler> {

    public Breeder_Screen(Breeder_Handler menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private static final ResourceLocation GUI = new ResourceLocation("chicken_roost:textures/screens/breeder.png");

    private static final ResourceLocation ARROW = new ResourceLocation("chicken_roost:textures/screens/newarrow.png");
    private static final ResourceLocation ARROW_VANILLA = new ResourceLocation("chicken_roost:textures/screens/arrow.png");
    private static final ResourceLocation GUI_VANILLA = new ResourceLocation("chicken_roost:textures/screens/breeder_vanilla.png");
    private static final ResourceLocation ARROWBACK_VANILLA = new ResourceLocation("chicken_roost:textures/screens/arrowback.png");

    private boolean colorblindMode;
    private boolean configInitialized = false;

    private EditBox nameField;
    private String enteredName = "BREEDER";
    private Button nameButton;
    @Override
    protected void init() {
        super.init();
        Button output;
        int x = this.leftPos;
        int y = this.topPos - 17;
        output = Button.builder(
                        makeOutputText(),
                        button -> {
                            // aktuellen Wert vom Client holen
                            boolean enabled = !this.menu.blockEntity.isAutoOutputEnabled();

                            // 1) direkt Client-BlockEntity updaten, damit GUI sofort den neuen Wert kennt
                            this.menu.blockEntity.setAutoOutputEnabled(enabled);

                            // 2) Text vom Button sofort ändern
                            button.setMessage(makeOutputText());

                            // 3) Server informieren
                            ModMessages.sendToServer(
                                    new SetBreederAutoOutputPayload(this.menu.blockEntity.getBlockPos(), enabled)
                            );
                        }
                )
                .pos(this.leftPos + 13, this.topPos - 17)
                .size(70, 13)
                .build();

        this.addRenderableWidget(output);
        output.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.output.info")));


        Button b = Button.builder(Component.literal("V"), button -> {
            if(colorblindMode){
                Config.ClientConfig.breeder_cb.set(false);
                Config.ClientConfig.breeder_cb.save();
            }
            else {
                Config.ClientConfig.breeder_cb.set(true);
                Config.ClientConfig.breeder_cb.save();
            }
        }).pos(this.leftPos, this.topPos - 17).size(13, 13).build();
        b.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.uiswitch.info")));

        this.addRenderableWidget(b);

        int nameBtnX = this.leftPos + 20 + 2 + 61;
        int nameBtnY = this.topPos - 17;
        this.nameButton = Button.builder(Component.translatable("roost_chicken.interface.name"), btn -> {

            int fieldX = nameBtnX + 40 + 2;
            int fieldY = this.topPos - 21;
            if (this.nameField == null) {
                this.nameField = new EditBox(
                        this.font,
                        fieldX, fieldY,
                        60, 20,
                        Component.translatable("roost_chicken.interface.name")
                                .withStyle(style -> style.withColor(0xFFFFFF))
                );
                this.nameField.setMaxLength(32);
                String currentname = this.menu.blockEntity.getCustomName();
                this.nameField.setValue(currentname);
                this.nameField.setFocused(true);
                this.addRenderableWidget(this.nameField);
            } else {
                this.nameField.setFocused(true);
            }
        }).pos(nameBtnX, nameBtnY).size(40, 13).build();
        this.nameButton.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.setname")));
        this.addRenderableWidget(this.nameButton);
    }

    private Component makeOutputText() {
        boolean enabled = this.menu.blockEntity.isAutoOutputEnabled();

        Component state = Component.translatable(
                enabled
                        ? "roost_chicken.interface.output.on"
                        : "roost_chicken.interface.output.off"
        ).withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED);

        return Component.translatable(
                "roost_chicken.interface.output.name",
                state
        );
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
                    ModMessages.sendToServer(
                            new SetBreederNamePayload(this.menu.blockEntity.getBlockPos(), this.enteredName)
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
        if (colorblindMode) {
            RenderSystem.setShaderTexture(0, GUI_VANILLA);
            ms.blit(GUI_VANILLA, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
            //RenderSystem.setShaderTexture(0, ARROW);
            ms.blit(ARROW, this.leftPos + 46, this.topPos + 35, 0, 0, menu.getScaledProgress(), 33, 54, 33);

        } else {
            RenderSystem.setShaderTexture(0, GUI);
            ms.blit(GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
            RenderSystem.setShaderTexture(0, ARROW);
            ms.blit(ARROW, this.leftPos + 46, this.topPos + 35, 0, 0, menu.getScaledProgress(), 33, 54, 33);
        }

        RenderSystem.disableBlend();
        colorblindMode = Config.ClientConfig.breeder_cb.get();
    }


    @Override
    public void render(@NotNull GuiGraphics ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
        if (!configInitialized) {
            this.colorblindMode = Config.ClientConfig.breeder_cb.get();
            this.configInitialized = true;
        }
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

}
