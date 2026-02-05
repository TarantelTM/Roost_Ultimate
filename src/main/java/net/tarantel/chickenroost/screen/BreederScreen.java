package net.tarantel.chickenroost.screen;

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
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.handler.BreederHandler;
import net.tarantel.chickenroost.networking.SetAutoOutputPayload;
import net.tarantel.chickenroost.networking.SetNamePayload;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

public class BreederScreen extends AbstractContainerScreen<BreederHandler> {

    private static final Identifier GUI =
            ChickenRoostMod.ownresource("textures/screens/breeder.png");
    private static final Identifier GUI_VANILLA =
            ChickenRoostMod.ownresource("textures/screens/breeder_vanilla.png");
    private static final Identifier ARROW =
            ChickenRoostMod.ownresource("textures/screens/newarrow.png");

    private EditBox nameField;
    private Button outputButton;

    private boolean colorblindMode = Config.breeder_cb.get();

    public BreederScreen(BreederHandler menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    /* ------------------------------------------------------------ */

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        /* ---------- Auto Output Toggle ---------- */
        this.outputButton = Button.builder(
                        makeOutputText(),
                        btn -> {
                            boolean newValue = !menu.getBlockEntity().isAutoOutputEnabled();

                            // ✅ SEND PAYLOAD (server authoritative)
                            ClientPacketDistributor.sendToServer(
                                    new SetAutoOutputPayload(
                                            menu.getBlockEntity().getBlockPos(),
                                            newValue
                                    )
                            );

                            // Optimistic UI update (actual state comes back via SyncAutoOutputPayload)
                            btn.setMessage(makeOutputText());
                        }
                )
                .pos(this.leftPos + 13, this.topPos - 17)
                .size(70, 13)
                .tooltip(Tooltip.create(
                        Component.translatable("roost_chicken.interface.output.info")))
                .build();

        this.addRenderableWidget(this.outputButton);

        /* ---------- Colorblind Toggle ---------- */
        this.addRenderableWidget(
                Button.builder(Component.literal("V"), btn -> {
                            colorblindMode = !colorblindMode;
                            Config.breeder_cb.set(colorblindMode);
                            Config.breeder_cb.save();
                        })
                        .pos(this.leftPos, this.topPos - 17)
                        .size(13, 13)
                        .tooltip(Tooltip.create(
                                Component.translatable("roost_chicken.interface.uiswitch.info")))
                        .build()
        );

        /* ---------- Name Button ---------- */
        int nameBtnX = this.leftPos + 20 + 2 + 61;
        int nameBtnY = this.topPos - 17;

        this.addRenderableWidget(
                Button.builder(
                                Component.translatable("roost_chicken.interface.name"),
                                btn -> openNameField(nameBtnX)
                        )
                        .pos(nameBtnX, nameBtnY)
                        .size(40, 13)
                        .tooltip(Tooltip.create(
                                Component.translatable("roost_chicken.interface.setname")))
                        .build()
        );
    }

    /* ------------------------------------------------------------ */

    private void openNameField(int nameBtnX) {
        if (this.nameField != null) {
            this.nameField.setFocused(true);
            return;
        }

        this.nameField = new EditBox(
                this.font,
                nameBtnX + 42,
                this.topPos - 21,
                60,
                20,
                Component.translatable("roost_chicken.interface.name")
        );

        this.nameField.setMaxLength(32);
        this.nameField.setValue(menu.getBlockEntity().getCustomName());
        this.nameField.setFocused(true);

        this.addRenderableWidget(this.nameField);
    }

    /* ------------------------------------------------------------ */

    private Component makeOutputText() {
        boolean enabled = menu.getBlockEntity().isAutoOutputEnabled();

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

    /* ------------------------------------------------------------ */

    @Override
    protected void containerTick() {
        super.containerTick();
        if (outputButton != null) {
            outputButton.setMessage(makeOutputText());
        }
    }

    /* ------------------------------------------------------------ */

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean dbl) {
        if (nameField != null && nameField.isFocused()) {
            if (!nameField.isMouseOver(event.x(), event.y())) {
                nameField.setFocused(false);
                return true;
            }
        }
        return super.mouseClicked(event, dbl);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (nameField != null && nameField.isFocused()) {

            if (event.isEscape()) {
                nameField.setFocused(false);
                return true;
            }

            if (event.isConfirmation()) {
                String name = nameField.getValue().trim();
                if (!name.isEmpty()) {
                    ClientPacketDistributor.sendToServer(
                            new SetNamePayload(
                                    menu.getBlockEntity().getBlockPos(),
                                    name
                            )
                    );
                }
                nameField.setFocused(false);
                return true;
            }

            nameField.keyPressed(event);
            return true;
        }

        return super.keyPressed(event);
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        if (nameField != null && nameField.isFocused()) {
            nameField.charTyped(event);
            return true;
        }
        return super.charTyped(event);
    }

    /* ------------------------------------------------------------ */

    @Override
    protected void renderBg(@NotNull GuiGraphics g, float partial, int mx, int my) {
        Identifier bg = colorblindMode ? GUI_VANILLA : GUI;

        g.blit(RenderPipelines.GUI_TEXTURED,
                bg,
                leftPos,
                topPos,
                0, 0,
                imageWidth,
                imageHeight,
                imageWidth,
                imageHeight
        );

        g.blit(RenderPipelines.GUI_TEXTURED,
                ARROW,
                leftPos + 46,
                topPos + 35,
                0, 0,
                menu.getScaledProgress(54),
                33,
                54,
                33
        );
    }

    @Override
    public void render(@NotNull GuiGraphics g, int mx, int my, float partial) {
        renderBackground(g, mx, my, partial);
        super.render(g, mx, my, partial);
        renderTooltip(g, mx, my);
    }
}
