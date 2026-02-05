package net.tarantel.chickenroost.screen;

import net.minecraft.world.level.Level;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.Breeder_Tile;
import net.tarantel.chickenroost.block.tile.Roost_Tile;
import net.tarantel.chickenroost.block.tile.Soul_Extractor_Tile;
import net.tarantel.chickenroost.network.ModMessages;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.tarantel.chickenroost.handler.Collector_Handler;
import net.tarantel.chickenroost.block.tile.Collector_Tile;
import net.tarantel.chickenroost.network.SetCollectorRangePayload;
import net.tarantel.chickenroost.network.SetCollectorRoostActivePayload;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Collector_Screen extends AbstractContainerScreen<Collector_Handler> {

    private static final ResourceLocation GUI =
            new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");
    private static final ResourceLocation SUB =
            new ResourceLocation("chicken_roost", "textures/screens/collectorsubmenu.png");

    private boolean showRoostMenu = false;
    private int searchRange = 10;
    private final List<BlockPos> foundRoosts = new ArrayList<>();
    private final Set<BlockPos> activeRoostsClient = new HashSet<>();


    private int listTopIndex = 0;
    private boolean draggingScrollbar = false;
    private int dragStartMouseY = 0;
    private int dragStartTopIndex = 0;


    private static final int PANEL_W = 160;
    private static final int PANEL_H = 140;
    private static final int HEADER_H = 18;
    private static final int LINE_H = 12;
    private static final int PADDING = 6;
    private static final int SCROLLBAR_W = 6;
    private static final int CHECKBOX_W = 14;

    public Collector_Screen(Collector_Handler menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 222;
    }

    @Override
    protected void init() {
        super.init();


        this.addRenderableWidget(
                Button.builder(Component.translatable("roost_chicken.interface.config"), b -> {
                    this.showRoostMenu = !this.showRoostMenu;
                    if (this.showRoostMenu) {

                        BlockEntity be = this.menu.getBlockEntity();
                        if (be instanceof Collector_Tile ct) {
                            this.searchRange = Math.max(1, Math.min(Config.ServerConfig.collectorrange.get(), ct.getCollectRange()));
                            this.activeRoostsClient.clear();
                            this.activeRoostsClient.addAll(ct.getActiveRoosts());
                        }
                        scanNearbyRoosts();
                        clampScroll();
                    }
                }).pos(this.leftPos + 5, this.topPos + 3).size(40, 13).build()
        );
    }


    private void scanNearbyRoosts() {
        this.foundRoosts.clear();

        final Minecraft mc = this.minecraft;
        if (mc == null || mc.level == null) return;

        final BlockEntity be = this.menu.getBlockEntity();
        if (!(be instanceof Collector_Tile ct)) return;

        final BlockPos center = ct.getBlockPos();
        final int r = Math.max(1, Math.min(Config.ServerConfig.collectorrange.get(), this.searchRange));

        this.foundRoosts.addAll(findRoosts(mc.level, center, r));

        this.foundRoosts.sort(Comparator.comparingDouble(
                p -> p.distToCenterSqr(center.getX() + 0.5, center.getY() + 0.5, center.getZ() + 0.5)
        ));
    }

    private static List<BlockPos> findRoosts(Level level, BlockPos center, int r) {
        final int minX = center.getX() - r, maxX = center.getX() + r;
        final int minY = center.getY() - r, maxY = center.getY() + r;
        final int minZ = center.getZ() - r, maxZ = center.getZ() + r;


        return BlockPos.betweenClosedStream(minX, minY, minZ, maxX, maxY, maxZ)
                .filter(p -> !p.equals(center))
                .filter(p -> {
                    BlockEntity be = level.getBlockEntity(p);
                    return be instanceof Roost_Tile
                            || be instanceof Breeder_Tile
                            || be instanceof Soul_Extractor_Tile;
                })
                .map(BlockPos::immutable)
                .collect(Collectors.toList());
    }


    private void toggleRoost(BlockPos pos) {
        boolean active;
        if (this.activeRoostsClient.contains(pos)) {
            this.activeRoostsClient.remove(pos);
            active = false;
        } else {
            this.activeRoostsClient.add(pos);
            active = true;
        }
        BlockEntity be = this.menu.getBlockEntity();
        if (be instanceof Collector_Tile ct) {
            ModMessages.sendToServer(
                    new SetCollectorRoostActivePayload(ct.getBlockPos(), pos, active)
            );
        }
    }


    private void changeRange(int delta) {
        BlockEntity be = this.menu.getBlockEntity();
        if (!(be instanceof Collector_Tile ct)) return;

        int nr = Math.max(1, Math.min(Config.ServerConfig.collectorrange.get(), this.searchRange + delta));
        if (nr == this.searchRange) return;

        this.searchRange = nr;
        scanNearbyRoosts();
        clampScroll();

        ModMessages.sendToServer(
                new SetCollectorRangePayload(ct.getBlockPos(), this.searchRange)
        );
    }


    @Override
    protected void renderBg(@NotNull GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        g.blit(GUI, i, j, 0, 0, this.imageWidth, 6 * 18 + 17);
        g.blit(GUI, i, j + 6 * 18 + 17, 0, 126, this.imageWidth, 96);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics g, int mouseX, int mouseY) {

    }


    @Override
    public void render(@NotNull GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g);
        super.render(g, mouseX, mouseY, partialTick);

        if (this.showRoostMenu) {
            renderRoostOverlay(g, mouseX, mouseY, partialTick);

        } else {
            this.renderTooltip(g, mouseX, mouseY);
        }
    }

    private void renderRoostOverlay(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        g.pose().pushPose();
        g.pose().translate(0, 0, 400);

        int px = this.leftPos + (this.imageWidth - PANEL_W) / 2;
        int py = this.topPos + (this.imageHeight - PANEL_H) / 2;

        g.blit(SUB, this.leftPos + 6, this.topPos + 39, 0, 0, 164, 144, 164, 144);



        g.drawString(this.font, Component.translatable(
                        "roost_chicken.interface.nearbyroosts",
                        this.searchRange
                ),
                px + PADDING, py + 6, 0xFFFFFF, false);

        int minusX1 = px + PANEL_W - 28, minusX2 = minusX1 + 8;
        int plusX1 = px + PANEL_W - 14, plusX2 = plusX1 + 8;
        int headY1 = py + 4, headY2 = headY1 + 10;
        g.drawString(this.font, Component.literal("-"), minusX1 + 1, py + 5, 0xFFFFFF, false);
        g.drawString(this.font, Component.literal("+"), plusX1 + 1, py + 5, 0xFFFFFF, false);


        int listX = px + PADDING;
        int listY = py + HEADER_H + 4;
        int listW = PANEL_W - PADDING * 2 - SCROLLBAR_W - 2;
        int listH = PANEL_H - (HEADER_H + 4) - PADDING;


        g.enableScissor(listX, listY, listX + listW, listY + listH);


        int visibleRows = Math.max(1, listH / LINE_H);
        int total = this.foundRoosts.size();
        clampScroll();

        int y = listY;
        for (int i = 0; i < visibleRows; i++) {
            int idx = listTopIndex + i;
            if (idx >= total) break;

            BlockPos p = this.foundRoosts.get(idx);
            boolean active = this.activeRoostsClient.contains(p);
            String checkbox = active ? "[x]" : "[ ]";

            String label = "(" + p.getX() + "," + p.getY() + "," + p.getZ() + ")";
            var be = this.minecraft != null ? this.minecraft.level.getBlockEntity(p) : null;
            if (be instanceof ICollectorTarget target) {
                try {
                    String nm = target.getCustomName();
                    if (nm != null && !nm.isEmpty()) label = nm + " ";
                } catch (Throwable ignored) {}
            }


            g.drawString(this.font, Component.literal(checkbox), listX, y, 0xFFFFFF, false);
            g.drawString(this.font, Component.literal(label), listX + CHECKBOX_W, y, 0xCCCCCC, false);

            y += LINE_H;
        }

        g.disableScissor();


        int sbX1 = listX + listW + 2;
        int sbX2 = sbX1 + SCROLLBAR_W;
        int sbY2 = listY + listH;


        g.fill(sbX1, listY, sbX2, sbY2, 0x66000000);


        if (total > visibleRows) {
            int thumbH = Math.max(10, (int) ((visibleRows / (float) total) * listH));
            int maxTop = total - visibleRows;
            float progress = (maxTop > 0) ? (listTopIndex / (float) maxTop) : 0f;
            int thumbY = listY + (int) ((listH - thumbH) * progress);


            g.fill(sbX1 + 1, thumbY, sbX2 - 1, thumbY + thumbH, 0xFFAAAAAA);
        }

        g.pose().popPose();
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (this.showRoostMenu) {
            int px = this.leftPos + (this.imageWidth - PANEL_W) / 2;
            int py = this.topPos + (this.imageHeight - PANEL_H) / 2;

            int listX = px + PADDING;
            int listY = py + HEADER_H + 4;
            int listW = PANEL_W - PADDING * 2 - SCROLLBAR_W - 2;
            int listH = PANEL_H - (HEADER_H + 4) - PADDING;

            if (mouseX >= listX && mouseX <= listX + listW
                    && mouseY >= listY && mouseY <= listY + listH) {

                int visibleRows = Math.max(1, listH / LINE_H);
                int total = this.foundRoosts.size();

                if (total > visibleRows) {
                    // delta > 0 = hoch, delta < 0 = runter (je nach OS/Mouse kann invertiert sein)
                    this.listTopIndex -= (int) Math.signum(delta);
                    clampScroll();
                    return true;
                }
            }
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.showRoostMenu) {
            return super.mouseClicked(mouseX, mouseY, button);
        }

        final Geo g = geom();

        boolean handled =
                handleHeaderButtons(mouseX, mouseY, g)
                        || handleScrollbar(mouseX, mouseY, g)
                        || handleList(mouseX, mouseY, button, g)
                        || inside(g.panelX1 - 2, g.panelY1 - 2, g.panelX2 + 2, g.panelY2 + 2, mouseX, mouseY);

        return handled || super.mouseClicked(mouseX, mouseY, button);
    }



    private record Geo(
            int panelX1, int panelY1, int panelX2, int panelY2,
            int minusX1, int minusX2, int plusX1, int plusX2, int headY1, int headY2,
            int listX, int listY, int listW, int listH,
            int sbX1, int sbX2
    ) {}

    private Geo geom() {
        final int px = this.leftPos + (this.imageWidth - PANEL_W) / 2;
        final int py = this.topPos + (this.imageHeight - PANEL_H) / 2;

        final int minusX1 = px + PANEL_W - 28, minusX2 = minusX1 + 8;
        final int plusX1  = px + PANEL_W - 14, plusX2  = plusX1 + 8;
        final int headY1 = py + 4, headY2 = headY1 + 10;

        final int listX = px + PADDING;
        final int listY = py + HEADER_H + 4;
        final int listW = PANEL_W - PADDING * 2 - SCROLLBAR_W - 2;
        final int listH = PANEL_H - (HEADER_H + 4) - PADDING;

        final int sbX1 = listX + listW + 2;
        final int sbX2 = sbX1 + SCROLLBAR_W;

        return new Geo(px, py, px + PANEL_W, py + PANEL_H,
                minusX1, minusX2, plusX1, plusX2, headY1, headY2,
                listX, listY, listW, listH, sbX1, sbX2);
    }



    private boolean handleHeaderButtons(double mx, double my, Geo g) {
        if (inside(g.minusX1, g.headY1, g.minusX2, g.headY2, mx, my)) {
            changeRange(-1);
            return true;
        }
        if (inside(g.plusX1, g.headY1, g.plusX2, g.headY2, mx, my)) {
            changeRange(+1);
            return true;
        }
        return false;
    }

    private boolean handleScrollbar(double mx, double my, Geo g) {
        if (!inside(g.sbX1, g.listY, g.sbX2, g.listY + g.listH, mx, my)) return false;

        final int visibleRows = Math.max(1, g.listH / LINE_H);
        final int total = this.foundRoosts.size();
        if (total <= visibleRows) return false;

        this.draggingScrollbar = true;
        this.dragStartMouseY = (int) my;
        this.dragStartTopIndex = this.listTopIndex;
        return true;
    }

    private boolean handleList(double mx, double my, int button, Geo g) {
        if (!inside(g.listX, g.listY, g.listX + g.listW, g.listY + g.listH, mx, my)) return false;

        final int relY = (int) my - g.listY;
        final int row = relY / LINE_H;
        final int idx = listTopIndex + row;

        if (idx >= 0 && idx < this.foundRoosts.size()) {
            final int cbX1 = g.listX;
            final int cbX2 = g.listX + CHECKBOX_W;
            final int rowY1 = g.listY + row * LINE_H;
            final int rowY2 = rowY1 + LINE_H;

            if (inside(cbX1, rowY1, cbX2, rowY2, mx, my)) {
                toggleRoost(this.foundRoosts.get(idx));
                return true;
            }
        }
        return true;
    }

    /* ---------- utils ---------- */

    private static boolean inside(int x1, int y1, int x2, int y2, double mx, double my) {
        return mx >= x1 && mx <= x2 && my >= y1 && my <= y2;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.showRoostMenu && this.draggingScrollbar) {
            int listH = PANEL_H - (HEADER_H + 4) - PADDING;

            int visibleRows = Math.max(1, listH / LINE_H);
            int total = this.foundRoosts.size();
            int maxTop = Math.max(0, total - visibleRows);

            if (total > visibleRows) {
                int dy = (int) mouseY - this.dragStartMouseY;

                int thumbH = Math.max(10, (int) ((visibleRows / (float) total) * listH));
                int scrollSpace = listH - thumbH;
                if (scrollSpace < 1) scrollSpace = 1;

                float frac = dy / (float) scrollSpace;
                int deltaRows = Math.round(frac * maxTop);
                this.listTopIndex = this.dragStartTopIndex + deltaRows;
                clampScroll();
            }
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.draggingScrollbar) {
            this.draggingScrollbar = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }


    private void clampScroll() {
        int visibleRows = getVisibleRows();
        int total = this.foundRoosts.size();
        int maxTop = Math.max(0, total - visibleRows);
        if (this.listTopIndex < 0) this.listTopIndex = 0;
        if (this.listTopIndex > maxTop) this.listTopIndex = maxTop;
    }

    private int getVisibleRows() {
        int listH = PANEL_H - (HEADER_H + 4) - PADDING;
        return Math.max(1, listH / LINE_H);
    }
}
