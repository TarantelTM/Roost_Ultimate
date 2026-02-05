package net.tarantel.chickenroost.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.handler.SoulExtractorHandler;
import net.tarantel.chickenroost.networking.*;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

public class SoulExtractorScreen extends AbstractContainerScreen<SoulExtractorHandler> {


    public SoulExtractorScreen(SoulExtractorHandler menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageWidth = 176;
        this.imageHeight = 166;


    }
    private boolean colorblindMode = Config.extractor_cb.get();
    private static final Identifier GUI = ChickenRoostMod.ownresource("textures/screens/soulextractorgui.png");
    private static final Identifier ARROW = ChickenRoostMod.ownresource("textures/screens/newarrow.png");
    private static final Identifier ARROW_VANILLA = ChickenRoostMod.ownresource("textures/screens/arrow.png");
    private static final Identifier GUI_VANILLA = ChickenRoostMod.ownresource("textures/screens/soulextractorgui_vanilla.png");
    private static final Identifier ARROWBACK_VANILLA = ChickenRoostMod.ownresource("textures/screens/arrowback.png");
    private EditBox nameField;
    private String enteredName = "EXTRACTOR";
    private Button nameButton;
    private Button output;


    @Override
    protected void containerTick() {
        super.containerTick();

        if (output != null) {
            output.setMessage(makeOutputText());
        }
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        int x = this.leftPos;
        int y = this.topPos - 17;
        this.output = Button.builder(
                        makeOutputText(),
                        button -> {
                            boolean newValue = !this.menu.blockEntity.isAutoOutputEnabled();
                            this.menu.blockEntity.setAutoOutputEnabled(newValue);
                            button.setMessage(makeOutputText());
                            Minecraft.getInstance()
                                    .getConnection()
                                    .send(new SetAutoOutputPayload(this.menu.blockEntity.getBlockPos(), newValue)
                            );
                        }
                )
                .pos(this.leftPos + 13, this.topPos - 17)
                .size(70, 13)
                .build();

        this.addRenderableWidget(this.output);
        this.output.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.output.info")));

        Button b = Button.builder(Component.literal("V"), button -> {
            if(colorblindMode){
                Config.extractor_cb.set(false);
                Config.extractor_cb.save();
            }
            else {
                Config.extractor_cb.set(true);
                Config.extractor_cb.save();
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
    public boolean keyPressed(KeyEvent event) {

        if (this.nameField != null && this.nameField.isFocused()) {

            if (event.isEscape()) {
                this.nameField.setFocused(false);
                return true;
            }

            if (event.isConfirmation()) {
                this.enteredName = this.nameField.getValue().trim();
                if (!this.enteredName.isEmpty()) {
                    Minecraft.getInstance()
                            .getConnection()
                            .send(new SetNamePayload(
                                    this.menu.getBlockEntity().getBlockPos(),
                                    this.enteredName
                            ));
                }
                this.nameField.setFocused(false);
                return true;
            }

            this.nameField.keyPressed(event);
            return true;
        }

        return super.keyPressed(event);
    }


    @Override
    public boolean charTyped(CharacterEvent codePoint) {
        if (this.nameField != null && this.nameField.isFocused()) {
            this.nameField.charTyped(codePoint);
            return true;
        }
        return super.charTyped(codePoint);
    }

    private int getScaledProgress() {
        boolean colorblindMode = Config.extractor_cb.get();
        int arrowWidth = 54;
        return this.menu.getScaledProgress(arrowWidth);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics g, float partialTicks, int mouseX, int mouseY) {

        Identifier bg = colorblindMode ? GUI_VANILLA : GUI;

        // Hintergrund
        g.blit(
                RenderPipelines.GUI_TEXTURED,
                bg,
                this.leftPos,
                this.topPos,
                0, 0,
                this.imageWidth,
                this.imageHeight,
                this.imageWidth,
                this.imageHeight
        );

        // Fortschrittspfeil
        g.blit(
                RenderPipelines.GUI_TEXTURED,
                ARROW,
                this.leftPos + 54,
                this.topPos + 31,
                0, 0,
                getScaledProgress(),
                33,
                54,
                33
        );

        // Mode ggf. aktualisieren
        colorblindMode = Config.extractor_cb.get();
    }


    @Override
    public void render(@NotNull GuiGraphics ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms, mouseX, mouseY, partialTicks);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }
    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {

    }


    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        double mouseX = event.x();
        double mouseY = event.y();

        if (this.nameField != null && this.nameField.isFocused()) {
            if (!this.nameField.isMouseOver(mouseX, mouseY)) {
                this.nameField.setFocused(false);
                return true;
            }
        }

        return super.mouseClicked(event, isDoubleClick);
    }

    public boolean isNameFieldFocused() {
        return this.nameField != null && this.nameField.isFocused();
    }

}