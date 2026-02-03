package net.tarantel.chickenroost.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
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
import net.tarantel.chickenroost.networking.SetFeederRangePayload;
import net.tarantel.chickenroost.networking.SetFeederRoostActivePayload;
import net.tarantel.chickenroost.networking.SetFeederRoostSeedPayload;
import net.tarantel.chickenroost.networking.SetFeederRoundRobinPayload;
import net.tarantel.chickenroost.networking.SetFeederStackModePayload;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
   private static final int MAX_VISIBLE = 16;
   private static final int CYCLE_TICKS = 160;
   private int stackModeClient = 0;
   private Button stackModeButton;

   public FeederScreen(FeederHandler handler, Inventory inv, Component title) {
      super(handler, inv, title);
      this.imageWidth = 176;
      this.imageHeight = 222;
   }

   private int overlayX() {
      return this.leftPos + (this.imageWidth - 164) / 2;
   }

   private int overlayY() {
      return this.topPos - 40 + (this.imageHeight - 120) / 2;
   }

   private Component roundRobinLabel() {
      return Component.literal("R: " + (this.roundRobinClient ? "ON" : "OFF"));
   }

   private Component stackModeLabel() {
      return switch (this.stackModeClient) {
         case 1 -> Component.translatable("roost_chicken.interface.stackmode.half");
         case 2 -> Component.translatable("roost_chicken.interface.stackmode.full");
         default -> Component.translatable("roost_chicken.interface.stackmode.single");
      };
   }

   protected void containerTick() {
      super.containerTick();
      if (((FeederHandler)this.menu).getBlockEntity() instanceof FeederTile ct) {
         int serverMode = ct.getStackSendModeId();
         if (!this.stackModePending && serverMode != this.stackModeClient) {
            this.stackModeClient = serverMode;
            this.stackModeButton.setMessage(this.stackModeLabel());
         } else if (this.stackModePending && serverMode == this.stackModeClient) {
            this.stackModePending = false;
         }

         boolean rr = ct.isRoundRobinEnabled();
         if (!this.roundRobinPending && rr != this.roundRobinClient) {
            this.roundRobinClient = rr;
            this.roundRobinButton.setMessage(this.roundRobinLabel());
         } else if (this.roundRobinPending && rr == this.roundRobinClient) {
            this.roundRobinPending = false;
         }
      }
   }

   protected void init() {
      super.init();
      if (((FeederHandler)this.menu).getBlockEntity() instanceof FeederTile ctInit) {
         this.roundRobinClient = ctInit.isRoundRobinEnabled();
         this.stackModeClient = ctInit.getStackSendModeId();
      }

      this.stackModeButton = (Button)this.addRenderableWidget(Button.builder(this.stackModeLabel(), btn -> {
         this.stackModeClient = (this.stackModeClient + 1) % 3;
         this.stackModePending = true;
         btn.setMessage(this.stackModeLabel());
         if (((FeederHandler)this.menu).getBlockEntity() instanceof FeederTile ct2) {
            PacketDistributor.sendToServer(new SetFeederStackModePayload(ct2.getBlockPos(), this.stackModeClient), new CustomPacketPayload[0]);
         }
      }).bounds(this.leftPos + 110, this.topPos + 4, 50, 12).build());
      this.stackModeButton.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.stackmode.info")));
      int btnX = this.leftPos + 70;
      int btnY = this.topPos + 4;
      this.roundRobinButton = (Button)this.addRenderableWidget(Button.builder(this.roundRobinLabel(), btn -> {
         this.roundRobinClient = !this.roundRobinClient;
         this.roundRobinPending = true;
         btn.setMessage(this.roundRobinLabel());
         if (((FeederHandler)this.menu).getBlockEntity() instanceof FeederTile ctx) {
            PacketDistributor.sendToServer(new SetFeederRoundRobinPayload(ctx.getBlockPos(), this.roundRobinClient), new CustomPacketPayload[0]);
         }
      }).bounds(btnX, btnY, 36, 12).build());
      this.roundRobinButton.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.roundrobin.info")));
      this.addRenderableWidget(Button.builder(Component.translatable("roost_chicken.interface.config"), btn -> {
         this.showRoostMenu = !this.showRoostMenu;
         ((FeederHandler)this.menu).setUiBlockFeederSlots(this.showRoostMenu);
         if (this.showRoostMenu) {
            this.scanNearbyRoosts();
            this.clampScroll();
         }
      }).bounds(this.leftPos + 4, this.topPos + 4, 35, 12).build());
      this.addRenderableWidget(
         Button.builder(Component.literal("-"), btn -> this.changeRange(-1)).bounds(this.leftPos + 20 + 18 + 5, this.topPos + 4, 12, 12).build()
      );
      this.addRenderableWidget(
         Button.builder(Component.literal("+"), btn -> this.changeRange(1)).bounds(this.leftPos + 34 + 18 + 5, this.topPos + 4, 12, 12).build()
      );
      if (((FeederHandler)this.menu).getBlockEntity() instanceof FeederTile ct) {
         this.searchRange = ct.getFeedRange();
         this.activeRoostsClient.clear();
         this.activeRoostsClient.addAll(ct.getActiveRoosts());
         this.stackModeClient = ct.getStackSendModeId();
      }
   }

   public void removed() {
      super.removed();
      if (this.menu != null) {
         ((FeederHandler)this.menu).setUiBlockFeederSlots(false);
      }
   }

   private void scanNearbyRoosts() {
      this.foundRoosts.clear();
      if (((FeederHandler)this.menu).getBlockEntity() instanceof FeederTile ct) {
         int r = this.searchRange;
         BlockPos center = ct.getBlockPos();

         for (BlockPos p : BlockPos.betweenClosed(center.offset(-r, -r, -r), center.offset(r, r, r))) {
            assert this.minecraft != null;

            assert this.minecraft.level != null;

            BlockEntity roostBe = this.minecraft.level.getBlockEntity(p);
            if (roostBe instanceof RoostTile || roostBe instanceof BreederTile || roostBe instanceof TrainerTile) {
               this.foundRoosts.add(p.immutable());
            }
         }

         this.preferredSeedsClient.clear();

         for (BlockPos rp : ct.getActiveRoosts()) {
            Item it = ct.getPreferredSeed(rp);
            if (it != null) {
               this.preferredSeedsClient.put(rp, it);
            }
         }
      }
   }

   private void toggleRoost(BlockPos pos) {
      boolean active = this.activeRoostsClient.contains(pos);
      active = !active;
      if (active) {
         this.activeRoostsClient.add(pos);
      } else {
         this.activeRoostsClient.remove(pos);
      }

      if (((FeederHandler)this.menu).getBlockEntity() instanceof FeederTile ct) {
         PacketDistributor.sendToServer(new SetFeederRoostActivePayload(ct.getBlockPos(), pos, active), new CustomPacketPayload[0]);
      }
   }

   private void changeRange(int delta) {
      if (((FeederHandler)this.menu).getBlockEntity() instanceof FeederTile ct) {
         int nr = Math.max(5, Math.min((Integer)Config.feederrange.get(), this.searchRange + delta));
         if (nr != this.searchRange) {
            this.searchRange = nr;
            this.scanNearbyRoosts();
            this.clampScroll();
            PacketDistributor.sendToServer(new SetFeederRangePayload(ct.getBlockPos(), this.searchRange), new CustomPacketPayload[0]);
         }
      }
   }

   private void setRoostSeed(BlockPos roostPos, @Nullable ItemStack cursor) {
      if (((FeederHandler)this.menu).getBlockEntity() instanceof FeederTile ct) {
         String id = "";
         if (cursor != null && !cursor.isEmpty() && this.isAllowedGhostItem(cursor.getItem())) {
            ResourceLocation key = BuiltInRegistries.ITEM.getKey(cursor.getItem());
            id = key.toString();
         }

         if (id.isEmpty()) {
            this.preferredSeedsClient.remove(roostPos);
         } else {
            this.preferredSeedsClient.put(roostPos, cursor.getItem());
         }

         PacketDistributor.sendToServer(new SetFeederRoostSeedPayload(ct.getBlockPos(), roostPos, id), new CustomPacketPayload[0]);
      }
   }

   private boolean isAllowedGhostItem(Item item) {
      return item instanceof ChickenSeedBase;
   }

   protected void renderBg(@NotNull GuiGraphics g, float partialTick, int mouseX, int mouseY) {
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      g.blit(GUI, i, j, 0, 0, this.imageWidth, 125);
      g.blit(GUI, i, j + 108 + 17, 0, 126, this.imageWidth, 96);
      if (this.showRoostMenu) {
         this.renderRoostOverlay(g, this.overlayX(), this.overlayY());
      }
   }

   public void render(@NotNull GuiGraphics g, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(g, mouseX, mouseY, partialTick);
      super.render(g, mouseX, mouseY, partialTick);
      this.renderTooltip(g, mouseX, mouseY);
   }

   private void renderRoostOverlay(GuiGraphics g, int x, int y) {
      g.blit(OVERLAY, x, y, 0.0F, 0.0F, 164, 120, 164, 120);
      g.drawString(this.font, Component.translatable("roost_chicken.interface.range", new Object[]{this.searchRange}), x + 164 - 60, y + 4, 11184810, false);
      int listX = x + 6;
      int listY = y + 18 + 4;
      int listH = 92;
      int visibleRows = this.getVisibleRows();
      int total = this.foundRoosts.size();
      this.clampScroll();
      int lineY = listY;

      for (int row = 0; row < visibleRows && row + this.listTopIndex < total; row++) {
         BlockPos pos = this.foundRoosts.get(row + this.listTopIndex);
         boolean active = this.activeRoostsClient.contains(pos);
         g.fill(listX, lineY, listX + 14, lineY + 10, -14671840);
         if (active) {
            g.drawString(this.font, Component.literal("x"), listX + 4, lineY, 65280, false);
         }

         String label = this.buildScrollingNameWithCoords(pos);
         g.drawString(this.font, Component.literal(label), listX + 14 + 4, lineY + 3, 16777215, false);
         int slotSize = 16;
         int slotX = x - 20 + 164 - 6 - slotSize;
         int slotY = lineY - 1;
         g.fill(slotX - 1, slotY - 1, slotX + slotSize + 1, slotY + slotSize + 1, -16777216);
         g.fill(slotX, slotY, slotX + slotSize, slotY + slotSize, -12829636);
         Item it = this.preferredSeedsClient.get(pos);
         if (it != null) {
            g.renderItem(new ItemStack(it), slotX, slotY);
         }

         lineY += 20;
      }

      int scrollbarX = x + 164 - 6 - 6;
      g.fill(scrollbarX, listY, scrollbarX + 6, listY + 92, -2145378272);
      if (total > 0) {
         int maxTop = Math.max(0, total - visibleRows);
         float visRatio = Math.min(1.0F, (float)visibleRows / total);
         int thumbH = Math.max(8, Math.min(92, Math.round(visRatio * 92.0F)));
         float posx = maxTop == 0 ? 0.0F : Math.max(0.0F, Math.min(1.0F, (float)this.listTopIndex / maxTop));
         int thumbY = listY + Math.round(posx * (92 - thumbH));
         if (total > visibleRows) {
            g.fill(scrollbarX, thumbY, scrollbarX + 6, thumbY + thumbH, -6250336);
         }
      }
   }

   private String buildScrollingNameWithCoords(BlockPos pos) {
      Level level = this.minecraft != null ? this.minecraft.level : null;
      if (level == null) {
         return "";
      } else if (level.getBlockEntity(pos) instanceof ICollectorTarget target) {
         String name = target.getCustomName();
         if (isBlank(name)) {
            return "";
         } else {
            String label = scrollOrClamp(name, level.getGameTime());
            return label + " ";
         }
      } else {
         return "";
      }
   }

   private static boolean isBlank(String s) {
      return s == null || s.isEmpty();
   }

   private static String scrollOrClamp(String text, long gameTime) {
      if (text.length() <= 16) {
         return text;
      } else {
         int scrollWidth = text.length() - 16;
         long period = 320L;
         long t = gameTime % period;
         int forward = (int)Math.min(t, 160L);
         int backward = (int)Math.max(0L, 160L - (t - 160L));
         int triangle = t < 160L ? forward : backward;
         int offset = Math.min(scrollWidth, triangle);
         int end = Math.min(text.length(), offset + 16);
         return text.substring(offset, end);
      }
   }

   protected void renderLabels(@NotNull GuiGraphics g, int mouseX, int mouseY) {
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (!this.showRoostMenu) {
         return super.mouseClicked(mouseX, mouseY, button);
      } else {
         int x = this.overlayX();
         int y = this.overlayY();
         int listX = x + 6;
         int listY = y + 18 + 4;
         int listH = 92;
         int visibleRows = this.getVisibleRows();
         int total = this.foundRoosts.size();
         int lineY = listY;

         for (int row = 0; row < visibleRows && row + this.listTopIndex < total; row++) {
            BlockPos pos = this.foundRoosts.get(row + this.listTopIndex);
            int cbX2 = listX + 14;
            int cbY2 = lineY + 10;
            if (inside(listX, lineY, cbX2, cbY2, mouseX, mouseY)) {
               this.toggleRoost(pos);
               return true;
            }

            int slotSize = 16;
            int slotX1 = x - 20 + 164 - 6 - 16;
            int slotX2 = slotX1 + 16;
            int slotY1 = lineY - 1;
            int slotY2 = slotY1 + 16;
            if (inside(slotX1, slotY1, slotX2, slotY2, mouseX, mouseY)) {
               return this.handleGhostSlotClick(pos, button);
            }

            lineY += 20;
         }

         int scrollbarX1 = x + 164 - 6 - 6;
         int scrollbarX2 = scrollbarX1 + 6;
         int scrollbarY2 = listY + 92;
         if (inside(scrollbarX1, listY, scrollbarX2, scrollbarY2, mouseX, mouseY)) {
            this.draggingScrollbar = true;
            this.dragStartMouseY = (int)mouseY;
            this.dragStartTopIndex = this.listTopIndex;
            return true;
         } else {
            return super.mouseClicked(mouseX, mouseY, button);
         }
      }
   }

   private static boolean inside(int x1, int y1, int x2, int y2, double mx, double my) {
      return mx >= x1 && mx <= x2 && my >= y1 && my <= y2;
   }

   private boolean handleGhostSlotClick(BlockPos pos, int button) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player == null) {
         return true;
      } else {
         ItemStack cursor = mc.player.containerMenu.getCarried();
         if (button == 1 || cursor.isEmpty()) {
            this.setRoostSeed(pos, ItemStack.EMPTY);
            return true;
         } else if (!this.isAllowedGhostItem(cursor.getItem())) {
            this.safeClickSound();
            return true;
         } else {
            this.setRoostSeed(pos, cursor);
            return true;
         }
      }
   }

   private void safeClickSound() {
      try {
         Minecraft mc = Minecraft.getInstance();
         if (mc.player != null) {
            mc.player.playNotifySound((SoundEvent)SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 0.25F, 0.6F);
         }
      } catch (Throwable var2) {
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (this.showRoostMenu && this.draggingScrollbar) {
         int x = this.overlayX();
         int y = this.overlayY();
         int deltaRows = this.getDeltaRows((int)mouseY);
         this.listTopIndex = this.dragStartTopIndex + deltaRows;
         this.clampScroll();
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
      }
   }

   private int getDeltaRows(int mouseY) {
      int listH = 92;
      int total = this.foundRoosts.size();
      int visibleRows = this.getVisibleRows();
      int maxTop = Math.max(0, total - visibleRows);
      int dy = mouseY - this.dragStartMouseY;
      int thumbH = Math.max(8, Math.round((float)visibleRows / total * 92.0F));
      int scrollSpace = Math.max(1, 92 - thumbH);
      float frac = (float)dy / scrollSpace;
      return Math.round(frac * maxTop);
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (this.draggingScrollbar) {
         this.draggingScrollbar = false;
         return true;
      } else {
         return super.mouseReleased(mouseX, mouseY, button);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      return this.handleWheelScroll(mouseX, mouseY, delta);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
      double delta = Math.abs(deltaY) > 0.0 ? deltaY : deltaX;
      return this.handleWheelScroll(mouseX, mouseY, delta);
   }

   private boolean handleWheelScroll(double mouseX, double mouseY, double delta) {
      if (!this.showRoostMenu) {
         return false;
      } else {
         int x = this.overlayX();
         int y = this.overlayY();
         int listY = y + 18 + 4;
         if (!(mouseX < x) && !(mouseX > x + 164) && !(mouseY < y) && !(mouseY > y + 120)) {
            int step = hasShiftDown() ? 3 : 1;
            if (delta < 0.0) {
               this.listTopIndex += step;
            } else {
               this.listTopIndex -= step;
            }

            this.clampScroll();
            return true;
         } else {
            return false;
         }
      }
   }

   private void clampScroll() {
      int visibleRows = this.getVisibleRows();
      int total = this.foundRoosts.size();
      int maxTop = Math.max(0, total - visibleRows);
      if (this.listTopIndex < 0) {
         this.listTopIndex = 0;
      }

      if (this.listTopIndex > maxTop) {
         this.listTopIndex = maxTop;
      }
   }

   private int getVisibleRows() {
      int listH = 92;
      return Math.max(1, listH / 20);
   }
}
