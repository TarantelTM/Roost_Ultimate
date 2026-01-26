package net.tarantel.chickenroost.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.BreederTile;
import net.tarantel.chickenroost.block.tile.FeederTile;
import net.tarantel.chickenroost.block.tile.RoostTile;
import net.tarantel.chickenroost.block.tile.TrainerTile;
import net.tarantel.chickenroost.handler.FeederHandler;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import net.tarantel.chickenroost.networking.*;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FeederScreen extends AbstractContainerScreen<FeederHandler> {

    private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/container/generic_54.png");
    private static final ResourceLocation OVERLAY = ResourceLocation.fromNamespaceAndPath("chicken_roost", "textures/screens/feedersubmenu.png");

    private boolean showRoostMenu = false;
    private int searchRange = 10;

    private final List<BlockPos> foundRoosts = new ArrayList<>();
    private final Set<BlockPos> activeRoostsClient = new HashSet<>();
    private final Map<BlockPos, Item> preferredSeedsClient = new HashMap<>();


    private int listTopIndex = 0;
    private boolean draggingScrollbar = false;
    private int dragStartMouseY = 0;
    private int dragStartTopIndex = 0;
    private boolean roundRobinClient = false;
    private Button roundRobinButton;

    private boolean roundRobinPending = false;
    private boolean stackModePending = false;

    private static final int PANEL_W = 164;
    private static final int PANEL_H = 120;
    private static final int HEADER_H = 18;
    private static final int LINE_H = 20;
    private static final int PADDING = 6;
    private static final int SCROLLBAR_W = 6;
    private static final int CHECKBOX_W = 14;

    public FeederScreen(FeederHandler handler, Inventory inv, Component title) {
        super(handler, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 222;
    }
    private static final int MAX_VISIBLE = 16;
    private static final int CYCLE_TICKS = 160;


    private int overlayX() { return this.leftPos + (this.imageWidth - PANEL_W) / 2; }
    private int overlayY() { return this.topPos - 40 + (this.imageHeight - PANEL_H) / 2; }
    private Component roundRobinLabel() {
        return Component.literal("R: " + (this.roundRobinClient ? "ON" : "OFF"));
    }


    private int stackModeClient = 0;
    private Button stackModeButton;

    private Component stackModeLabel() {
        return switch (this.stackModeClient) {
            case 1 -> Component.translatable("roost_chicken.interface.stackmode.half");
            case 2 -> Component.translatable("roost_chicken.interface.stackmode.full");
            default -> Component.translatable("roost_chicken.interface.stackmode.single");
        };
    }

    @Override
    protected void containerTick() {
        super.containerTick();

        BlockEntity be = this.menu.getBlockEntity();
        if (!(be instanceof FeederTile ct)) return;


        int serverMode = ct.getStackSendModeId();
        if (!stackModePending && serverMode != this.stackModeClient) {
            this.stackModeClient = serverMode;
            this.stackModeButton.setMessage(stackModeLabel());
        } else if (stackModePending && serverMode == this.stackModeClient) {
            stackModePending = false;
        }


        boolean rr = ct.isRoundRobinEnabled();
        if (!roundRobinPending && rr != this.roundRobinClient) {
            this.roundRobinClient = rr;
            this.roundRobinButton.setMessage(roundRobinLabel());
        } else if (roundRobinPending && rr == this.roundRobinClient) {
            roundRobinPending = false;
        }
    }



    @Override
    protected void init() {
        super.init();
        BlockEntity beInit = this.menu.getBlockEntity();
        if (beInit instanceof FeederTile ctInit) {
            this.roundRobinClient = ctInit.isRoundRobinEnabled();
            this.stackModeClient = ctInit.getStackSendModeId();
        }
        this.stackModeButton = this.addRenderableWidget(
                Button.builder(stackModeLabel(), btn -> {
                    this.stackModeClient = (this.stackModeClient + 1) % 3;
                    this.stackModePending = true;
                    btn.setMessage(stackModeLabel());

                    BlockEntity be2 = this.menu.getBlockEntity();
                    if (be2 instanceof FeederTile ct2) {
                        net.neoforged.neoforge.network.PacketDistributor.sendToServer(
                                new SetFeederStackModePayload(ct2.getBlockPos(), this.stackModeClient)
                        );
                    }
                }).bounds(this.leftPos + 110, this.topPos + 4, 50, 12).build()
        );
        this.stackModeButton.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.stackmode.info")));
        int btnX = this.leftPos + 70;
        int btnY = this.topPos + 4;

        this.roundRobinButton = this.addRenderableWidget(
                Button.builder(roundRobinLabel(), btn -> {
                    this.roundRobinClient = !this.roundRobinClient;
                    this.roundRobinPending = true;
                    btn.setMessage(roundRobinLabel());

                    BlockEntity be = this.menu.getBlockEntity();
                    if (be instanceof FeederTile ct) {
                        PacketDistributor.sendToServer(
                                new SetFeederRoundRobinPayload(ct.getBlockPos(), this.roundRobinClient)
                        );
                    }
                }).bounds(btnX, btnY, 36, 12).build()
        );
        this.roundRobinButton.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.roundrobin.info")));
        addRenderableWidget(Button.builder(Component.translatable("roost_chicken.interface.config"), btn -> {
            this.showRoostMenu = !this.showRoostMenu;


            this.menu.setUiBlockFeederSlots(this.showRoostMenu);

            if (this.showRoostMenu) {
                scanNearbyRoosts();
                clampScroll();
            }
        }).bounds(this.leftPos + 4, this.topPos + 4, 35, 12).build());

        addRenderableWidget(Button.builder(Component.literal("-"), btn -> changeRange(-1))
                .bounds(this.leftPos + 20 + 18 + 5, this.topPos + 4, 12, 12).build());
        addRenderableWidget(Button.builder(Component.literal("+"), btn -> changeRange(+1))
                .bounds(this.leftPos + 34 + 18+ 5, this.topPos + 4, 12, 12).build());


        BlockEntity be = this.menu.getBlockEntity();
        if (be instanceof FeederTile ct) {
            this.searchRange = ct.getFeedRange();
            this.activeRoostsClient.clear();
            this.activeRoostsClient.addAll(ct.getActiveRoosts());
            this.stackModeClient = ct.getStackSendModeId();
        }
    }

    @Override
    public void removed() {
        super.removed();
        if (this.menu != null) {
            this.menu.setUiBlockFeederSlots(false);
        }
    }


    private void scanNearbyRoosts() {
        this.foundRoosts.clear();
        BlockEntity be = this.menu.getBlockEntity();
        if (!(be instanceof FeederTile ct)) return;

        int r = this.searchRange;
        BlockPos center = ct.getBlockPos();
        for (BlockPos p : BlockPos.betweenClosed(center.offset(-r, -r, -r), center.offset(+r, +r, +r))) {
            assert this.minecraft != null;
            assert this.minecraft.level != null;
            var roostBe = this.minecraft.level.getBlockEntity(p);
            if (roostBe instanceof RoostTile
                    || roostBe instanceof BreederTile
                    || roostBe instanceof TrainerTile) {

                this.foundRoosts.add(p.immutable());
            }
        }

        this.preferredSeedsClient.clear();
        for (BlockPos rp : ct.getActiveRoosts()) {
            Item it = ct.getPreferredSeed(rp);
            if (it != null) this.preferredSeedsClient.put(rp, it);
        }
    }

    private void toggleRoost(BlockPos pos) {
        boolean active = this.activeRoostsClient.contains(pos);
        active = !active;
        if (active) this.activeRoostsClient.add(pos);
        else this.activeRoostsClient.remove(pos);

        BlockEntity be = this.menu.getBlockEntity();
        if (be instanceof FeederTile ct) {
            PacketDistributor.sendToServer(new SetFeederRoostActivePayload(ct.getBlockPos(), pos, active));
        }
    }

    private void changeRange(int delta) {
        BlockEntity be = this.menu.getBlockEntity();
        if (!(be instanceof FeederTile ct)) return;

        int nr = Math.max(5, Math.min(Config.feederrange.get(), this.searchRange + delta));
        if (nr == this.searchRange) return;

        this.searchRange = nr;
        scanNearbyRoosts();
        clampScroll();

        PacketDistributor.sendToServer(new SetFeederRangePayload(ct.getBlockPos(), this.searchRange));
    }


    private void setRoostSeed(BlockPos roostPos, @Nullable ItemStack cursor) {
        BlockEntity be = this.menu.getBlockEntity();
        if (!(be instanceof FeederTile ct)) return;

        String id = "";
        if (cursor != null && !cursor.isEmpty() && isAllowedGhostItem(cursor.getItem())) {
            var key = BuiltInRegistries.ITEM.getKey(cursor.getItem());
            id = key.toString();
        }

        if (id.isEmpty()) this.preferredSeedsClient.remove(roostPos);
        else this.preferredSeedsClient.put(roostPos, cursor.getItem());

        PacketDistributor.sendToServer(new SetFeederRoostSeedPayload(ct.getBlockPos(), roostPos, id));
    }

    private boolean isAllowedGhostItem(Item item) {
        return item instanceof ChickenSeedBase;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        g.blit(GUI, i, j, 0, 0, this.imageWidth, 6 * 18 + 17);
        g.blit(GUI, i, j + 6 * 18 + 17, 0, 126, this.imageWidth, 96);

        if (this.showRoostMenu) {
            renderRoostOverlay(g, overlayX(), overlayY());
        }
    }

    @Override
    public void render(@NotNull GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);
        this.renderTooltip(g, mouseX, mouseY);
    }

    private void renderRoostOverlay(GuiGraphics g, int x, int y) {
        g.blit(OVERLAY, x, y, 0, 0, PANEL_W, PANEL_H, PANEL_W, PANEL_H);


        g.drawString(this.font, Component.translatable(
                "roost_chicken.interface.range",
                this.searchRange
        ), x + PANEL_W - 60, y + 4, 0xAAAAAA, false);


        final int listX = x + PADDING;
        final int listY = y + HEADER_H + 4;
        final int listH = PANEL_H - (HEADER_H + 4) - PADDING;

        int visibleRows = getVisibleRows();
        int total = this.foundRoosts.size();
        clampScroll();

        int lineY = listY;

        for (int row = 0; row < visibleRows && (row + this.listTopIndex) < total; row++) {
            BlockPos pos = this.foundRoosts.get(row + this.listTopIndex);
            boolean active = this.activeRoostsClient.contains(pos);


            g.fill(listX, lineY, listX + CHECKBOX_W, lineY + 10, 0xFF202020);
            if (active) g.drawString(this.font, Component.literal("x"), listX + 4, lineY, 0x00FF00, false);


            String label = buildScrollingNameWithCoords(pos);

            g.drawString(this.font, Component.literal(label), listX + CHECKBOX_W + 4, lineY + 3, 0xFFFFFF, false);


            int slotSize = 16;
            int slotX = x - 20 + PANEL_W - PADDING - slotSize;
            int slotY = lineY - 1;
            g.fill(slotX - 1, slotY - 1, slotX + slotSize + 1, slotY + slotSize + 1, 0xFF000000);
            g.fill(slotX, slotY, slotX + slotSize, slotY + slotSize, 0xFF3C3C3C);

            Item it = this.preferredSeedsClient.get(pos);
            if (it != null) g.renderItem(new ItemStack(it), slotX, slotY);

            lineY += LINE_H;
        }


        int scrollbarX = x + PANEL_W - PADDING - SCROLLBAR_W;
        g.fill(scrollbarX, listY, scrollbarX + SCROLLBAR_W, listY + listH, 0x80202020);

        if (total > 0) {

            int maxTop = Math.max(0, total - visibleRows);


            float visRatio = Math.min(1f, visibleRows / (float) total);


            int thumbH = Math.max(8, Math.min(listH, Math.round(visRatio * listH)));


            float pos = (maxTop == 0)
                    ? 0f
                    : Math.max(0f, Math.min(1f, this.listTopIndex / (float) maxTop));


            int thumbY = listY + Math.round(pos * (listH - thumbH));


            if (total > visibleRows) {
                g.fill(scrollbarX, thumbY, scrollbarX + SCROLLBAR_W, thumbY + thumbH, 0xFFA0A0A0);
            }
        }
    }


    private String buildScrollingNameWithCoords(BlockPos pos) {
        Level level = (minecraft != null) ? minecraft.level : null;
        if (level == null) return "";

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof ICollectorTarget target)) return "";

        String name = target.getCustomName();
        if (isBlank(name)) return "";

        String label = scrollOrClamp(name, level.getGameTime());
        return label + " ";
    }



    private static boolean isBlank(String s) {
        return s == null || s.isEmpty();
    }


    private static String scrollOrClamp(String text, long gameTime) {
        if (text.length() <= FeederScreen.MAX_VISIBLE) return text;

        int scrollWidth = text.length() - FeederScreen.MAX_VISIBLE;
        long period = CYCLE_TICKS * 2L;
        long t = gameTime % period;

        int forward = (int) Math.min(t, CYCLE_TICKS);
        int backward = (int) Math.max(0, CYCLE_TICKS - (t - CYCLE_TICKS));
        int triangle = (t < CYCLE_TICKS) ? forward : backward;

        int offset = Math.min(scrollWidth, triangle);
        int end = Math.min(text.length(), offset + FeederScreen.MAX_VISIBLE);
        return text.substring(offset, end);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics g, int mouseX, int mouseY) {

    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.showRoostMenu) {
            return super.mouseClicked(mouseX, mouseY, button);
        }

        final int x = overlayX();
        final int y = overlayY();


        final int listX = x + PADDING;
        final int listY = y + HEADER_H + 4;
        final int listH = PANEL_H - (HEADER_H + 4) - PADDING;

        final int visibleRows = getVisibleRows();
        final int total = this.foundRoosts.size();

        int lineY = listY;
        for (int row = 0; row < visibleRows && (row + this.listTopIndex) < total; row++) {
            final BlockPos pos = this.foundRoosts.get(row + this.listTopIndex);


            final int cbX2 = listX + CHECKBOX_W;
            final int cbY1 = lineY;
            final int cbY2 = cbY1 + 10;

            if (inside(listX, cbY1, cbX2, cbY2, mouseX, mouseY)) {
                toggleRoost(pos);
                return true;
            }


            final int slotSize = 16;
            final int slotX1 = x - 20 + PANEL_W - PADDING - slotSize;
            final int slotX2 = slotX1 + slotSize;
            final int slotY1 = lineY - 1;
            final int slotY2 = slotY1 + slotSize;

            if (inside(slotX1, slotY1, slotX2, slotY2, mouseX, mouseY)) {
                return handleGhostSlotClick(pos, button);
            }

            lineY += LINE_H;
        }


        final int scrollbarX1 = x + PANEL_W - PADDING - SCROLLBAR_W;
        final int scrollbarX2 = scrollbarX1 + SCROLLBAR_W;
        final int scrollbarY2 = listY + listH;

        if (inside(scrollbarX1, listY, scrollbarX2, scrollbarY2, mouseX, mouseY)) {
            this.draggingScrollbar = true;
            this.dragStartMouseY = (int) mouseY;
            this.dragStartTopIndex = this.listTopIndex;
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }



    private static boolean inside(int x1, int y1, int x2, int y2, double mx, double my) {
        return mx >= x1 && mx <= x2 && my >= y1 && my <= y2;
    }

    private boolean handleGhostSlotClick(BlockPos pos, int button) {
        final var mc = Minecraft.getInstance();
        if (mc.player == null) return true;

        final ItemStack cursor = mc.player.containerMenu.getCarried();

        if (button == 1 || cursor.isEmpty()) {
            setRoostSeed(pos, ItemStack.EMPTY);
            return true;
        }

        if (!isAllowedGhostItem(cursor.getItem())) {
            safeClickSound();
            return true;
        }

        setRoostSeed(pos, cursor);
        return true;
    }


    private void safeClickSound() {
        try {
            final var mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.playNotifySound(SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 0.25f, 0.6f);
            }
        } catch (Throwable ignored) {

        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.showRoostMenu && this.draggingScrollbar) {
            final int x = overlayX();
            final int y = overlayY();
            int deltaRows = getDeltaRows((int) mouseY);
            this.listTopIndex = this.dragStartTopIndex + deltaRows;
            clampScroll();
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private int getDeltaRows(int mouseY) {
        final int listH = PANEL_H - (HEADER_H + 4) - PADDING;

        int total = this.foundRoosts.size();
        int visibleRows = getVisibleRows();
        int maxTop = Math.max(0, total - visibleRows);

        int dy = mouseY - this.dragStartMouseY;
        int thumbH = Math.max(8, Math.round((visibleRows / (float) total) * listH));
        int scrollSpace = Math.max(1, listH - thumbH);


        float frac = dy / (float) scrollSpace;
        return Math.round(frac * maxTop);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.draggingScrollbar) {
            this.draggingScrollbar = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }


    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        return handleWheelScroll(mouseX, mouseY, delta);
    }
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        double delta = Math.abs(deltaY) > 0.0 ? deltaY : deltaX;
        return handleWheelScroll(mouseX, mouseY, delta);
    }
    private boolean handleWheelScroll(double mouseX, double mouseY, double delta) {
        if (!this.showRoostMenu) return false;

        final int x = overlayX();
        final int y = overlayY();
        final int listY = y + HEADER_H + 4;


        if (mouseX < x || mouseX > x + PANEL_W || mouseY < y || mouseY > y + PANEL_H) {
            return false;
        }

        int step = hasShiftDown() ? 3 : 1;
        if (delta < 0) this.listTopIndex += step;
        else this.listTopIndex -= step;
        clampScroll();
        return true;
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
