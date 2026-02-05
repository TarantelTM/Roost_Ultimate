package net.tarantel.chickenroost.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.*;
import net.tarantel.chickenroost.handler.FeederHandler;
import net.tarantel.chickenroost.networking.SetFeederRoostActivePayload;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FeederScreen extends AbstractContainerScreen<FeederHandler> {

    private static final Identifier GUI =
            Identifier.withDefaultNamespace("textures/gui/container/generic_54.png");

    private static final int PANEL_W = 164;
    private static final int PANEL_H = 120;
    private static final int LINE_H = 20;
    private static final int HEADER_H = 18;
    private static final int PADDING = 6;

    private boolean showMenu = false;
    private int listTop = 0;

    private final List<BlockPos> allTargets = new ArrayList<>();
    private final List<Button> rowButtons = new ArrayList<>();
    private List<BlockPos> lastVisible = List.of();

    public FeederScreen(FeederHandler menu, Inventory inv, Component title) {
        super(menu, inv, title);
        //imageWidth = 176;
        //imageHeight = 222;
    }

    @Override
    protected void init() {
        super.init();
        int slotX = overlayX() + PANEL_W - 20;
        int baseY = overlayY() + HEADER_H + 4;

        menu.initPreferredSlots(slotX, baseY, LINE_H);

        addRenderableWidget(Button.builder(Component.literal("Config"), b -> {
            showMenu = !showMenu;
            menu.setUiBlockFeederSlots(showMenu);
            updateVisibleTargets();
        }).bounds(leftPos + 4, topPos + 4, 40, 12).build());

        rebuildRowButtons();
    }

    /* ================= UPDATE ================= */

    private void updateVisibleTargets() {
        scanTargets();

        int end = Math.min(allTargets.size(), listTop + getVisibleRows());
        List<BlockPos> now = allTargets.subList(listTop, end);

        if (!now.equals(lastVisible)) {
            int slotX = PANEL_W - 20;
            int baseY = HEADER_H + 4;

            menu.setPreferredSlotBasePosition(slotX, baseY, LINE_H);
            menu.setVisibleTargets(now);

            lastVisible = List.copyOf(now);
        }

        updateButtonsOnly();
    }

    private void updateButtonsOnly() {
        int x = overlayX() + PADDING;
        int y = overlayY() + HEADER_H + 4;

        for (int i = 0; i < rowButtons.size(); i++) {
            int idx = listTop + i;
            Button btn = rowButtons.get(i);

            if (idx >= allTargets.size()) {
                btn.visible = false;
                continue;
            }

            BlockPos pos = allTargets.get(idx);
            boolean active = menu.getBlockEntity().isRoostActive(pos);

            btn.visible = true;
            btn.setMessage(Component.literal(active ? "ON" : "OFF"));
            btn.setX(x);
            btn.setY(y + i * LINE_H + 2);
        }
    }


    /* ================= RENDER ================= */

    @Override
    protected void renderBg(GuiGraphics g, float p, int mx, int my) {
        int i = (width - imageWidth) / 2;
        int j = (height - imageHeight) / 2;

        g.blit(RenderPipelines.GUI_TEXTURED, GUI, i, j, 0, 0, imageWidth, 114, 256, 256);
        g.blit(RenderPipelines.GUI_TEXTURED, GUI, i, j + 114, 0, 126, imageWidth, 96, 256, 256);

        if (showMenu) {
            int x = overlayX();
            int y = overlayY();
            g.fill(x, y, x + PANEL_W, y + PANEL_H, 0xFFC6C6C6);
            renderFilterSlotBackgrounds(g);
        }
    }

    /* ================= SLOT BACKGROUNDS ================= */

    private void renderFilterSlotBackgrounds(GuiGraphics g) {
        int slotX = overlayX() + PANEL_W - 20;
        int baseY = overlayY() + HEADER_H + 4;

        for (int i = 0; i < getVisibleRows(); i++) {
            int idx = listTop + i;
            if (idx >= allTargets.size()) break;

            int y = baseY + i * LINE_H;

            g.fill(slotX - 1, y - 1, slotX + 17, y + 17, 0xFF202020);
            g.fill(slotX, y, slotX + 16, y + 16, 0xFF8B8B8B);
        }
    }

    /* ================= HELPERS ================= */

    private int overlayX() {
        return leftPos + (imageWidth - PANEL_W) / 2;
    }

    private int overlayY() {
        return topPos - 40 + (imageHeight - PANEL_H) / 2;
    }

    private int getVisibleRows() {
        return (PANEL_H - HEADER_H - 8) / LINE_H;
    }

    private void scanTargets() {
        allTargets.clear();
        Level level = minecraft.level;
        if (level == null) return;

        FeederTile ft = menu.getBlockEntity();
        BlockPos center = ft.getBlockPos();
        int r = ft.getFeedRange();

        for (BlockPos p : BlockPos.betweenClosed(
                center.offset(-r, -r, -r),
                center.offset(r, r, r))) {

            BlockEntity be = level.getBlockEntity(p);
            if (be instanceof RoostTile || be instanceof BreederTile || be instanceof TrainerTile) {
                allTargets.add(p.immutable());
            }
        }
    }

    private void rebuildRowButtons() {
        rowButtons.clear();
        for (int i = 0; i < getVisibleRows(); i++) {
            final int row = i;
            rowButtons.add(addRenderableWidget(
                    Button.builder(Component.literal(""), b -> toggleRow(row))
                            .bounds(0, 0, 28, 14)
                            .build()
            ));
        }
    }

    private void toggleRow(int row) {
        int idx = listTop + row;
        if (idx >= allTargets.size()) return;

        BlockPos pos = allTargets.get(idx);
        FeederTile ft = menu.getBlockEntity();

        boolean wasActive = ft.isRoostActive(pos);
        Set<BlockPos> clientSet = ft.getClientActiveRoosts();

        if (wasActive) clientSet.remove(pos);
        else clientSet.add(pos);

        updateVisibleTargets();

        ClientPacketDistributor.sendToServer(
                new SetFeederRoostActivePayload(ft.getBlockPos(), pos, !wasActive)
        );
    }

    public void refreshButtons() {
        if (!showMenu) return;

        // KEINE Slots neu bauen
        // KEINE VisibleTargets ändern
        // NUR Button-Texte / Sichtbarkeit neu setzen
        updateButtonsOnly();
    }

}
