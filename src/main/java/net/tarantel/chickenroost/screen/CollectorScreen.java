package net.tarantel.chickenroost.screen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.api.ICollectorTarget;
import net.tarantel.chickenroost.block.tile.BreederTile;
import net.tarantel.chickenroost.block.tile.CollectorTile;
import net.tarantel.chickenroost.block.tile.RoostTile;
import net.tarantel.chickenroost.block.tile.SoulExtractorTile;
import net.tarantel.chickenroost.handler.CollectorHandler;
import net.tarantel.chickenroost.networking.SetCollectorRangePayload;
import net.tarantel.chickenroost.networking.SetCollectorRoostActivePayload;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

public class CollectorScreen extends AbstractContainerScreen<CollectorHandler> {
   private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/container/generic_54.png");
   private static final ResourceLocation SUB = ChickenRoostMod.ownresource("textures/screens/collectorsubmenu.png");
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

   public CollectorScreen(CollectorHandler menu, Inventory inv, Component title) {
      super(menu, inv, title);
      this.imageWidth = 176;
      this.imageHeight = 222;
   }

   protected void init() {
      super.init();
      this.addRenderableWidget(Button.builder(Component.translatable("roost_chicken.interface.config"), b -> {
         this.showRoostMenu = !this.showRoostMenu;
         if (this.showRoostMenu) {
            if (((CollectorHandler)this.menu).getBlockEntity() instanceof CollectorTile ct) {
               this.searchRange = Math.max(1, Math.min((Integer)Config.collectorrange.get(), ct.getCollectRange()));
               this.activeRoostsClient.clear();
               this.activeRoostsClient.addAll(ct.getActiveRoosts());
            }

            this.scanNearbyRoosts();
            this.clampScroll();
         }
      }).pos(this.leftPos + 5, this.topPos + 3).size(40, 13).build());
   }

   private void scanNearbyRoosts() {
      this.foundRoosts.clear();
      Minecraft mc = this.minecraft;
      if (mc != null && mc.level != null) {
         if (((CollectorHandler)this.menu).getBlockEntity() instanceof CollectorTile ct) {
            BlockPos center = ct.getBlockPos();
            int r = Math.max(1, Math.min((Integer)Config.collectorrange.get(), this.searchRange));
            this.foundRoosts.addAll(findRoosts(mc.level, center, r));
            this.foundRoosts.sort(Comparator.comparingDouble(p -> p.distToCenterSqr(center.getX() + 0.5, center.getY() + 0.5, center.getZ() + 0.5)));
         }
      }
   }

   private static List<BlockPos> findRoosts(Level level, BlockPos center, int r) {
      int minX = center.getX() - r;
      int maxX = center.getX() + r;
      int minY = center.getY() - r;
      int maxY = center.getY() + r;
      int minZ = center.getZ() - r;
      int maxZ = center.getZ() + r;
      return BlockPos.betweenClosedStream(minX, minY, minZ, maxX, maxY, maxZ).filter(p -> !p.equals(center)).filter(p -> {
         BlockEntity be = level.getBlockEntity(p);
         return be instanceof RoostTile || be instanceof BreederTile || be instanceof SoulExtractorTile;
      }).<BlockPos>map(BlockPos::immutable).collect(Collectors.toList());
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

      if (((CollectorHandler)this.menu).getBlockEntity() instanceof CollectorTile ct) {
         PacketDistributor.sendToServer(new SetCollectorRoostActivePayload(ct.getBlockPos(), pos, active), new CustomPacketPayload[0]);
      }
   }

   private void changeRange(int delta) {
      if (((CollectorHandler)this.menu).getBlockEntity() instanceof CollectorTile ct) {
         int nr = Math.max(1, Math.min((Integer)Config.collectorrange.get(), this.searchRange + delta));
         if (nr != this.searchRange) {
            this.searchRange = nr;
            this.scanNearbyRoosts();
            this.clampScroll();
            PacketDistributor.sendToServer(new SetCollectorRangePayload(ct.getBlockPos(), this.searchRange), new CustomPacketPayload[0]);
         }
      }
   }

   protected void renderBg(@NotNull GuiGraphics g, float partialTick, int mouseX, int mouseY) {
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      g.blit(GUI, i, j, 0, 0, this.imageWidth, 125);
      g.blit(GUI, i, j + 108 + 17, 0, 126, this.imageWidth, 96);
   }

   protected void renderLabels(@NotNull GuiGraphics g, int mouseX, int mouseY) {
   }

   public void render(@NotNull GuiGraphics g, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(g, mouseX, mouseY, partialTick);
      super.render(g, mouseX, mouseY, partialTick);
      if (this.showRoostMenu) {
         this.renderRoostOverlay(g, mouseX, mouseY, partialTick);
      } else {
         this.renderTooltip(g, mouseX, mouseY);
      }
   }

   private void renderRoostOverlay(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
      g.pose().pushPose();
      g.pose().translate(0.0F, 0.0F, 400.0F);
      int px = this.leftPos + (this.imageWidth - 160) / 2;
      int py = this.topPos + (this.imageHeight - 140) / 2;
      g.blit(SUB, this.leftPos + 6, this.topPos + 39, 0.0F, 0.0F, 164, 144, 164, 144);
      g.drawString(this.font, Component.translatable("roost_chicken.interface.nearbyroosts", new Object[]{this.searchRange}), px + 6, py + 6, 16777215, false);
      int minusX1 = px + 160 - 28;
      int minusX2 = minusX1 + 8;
      int plusX1 = px + 160 - 14;
      int plusX2 = plusX1 + 8;
      int headY1 = py + 4;
      int headY2 = headY1 + 10;
      g.drawString(this.font, Component.literal("-"), minusX1 + 1, py + 5, 16777215, false);
      g.drawString(this.font, Component.literal("+"), plusX1 + 1, py + 5, 16777215, false);
      int listX = px + 6;
      int listY = py + 18 + 4;
      int listW = 140;
      int listH = 112;
      g.enableScissor(listX, listY, listX + listW, listY + listH);
      int visibleRows = Math.max(1, listH / 12);
      int total = this.foundRoosts.size();
      this.clampScroll();
      int y = listY;

      for (int i = 0; i < visibleRows; i++) {
         int idx = this.listTopIndex + i;
         if (idx >= total) {
            break;
         }

         BlockPos p = this.foundRoosts.get(idx);
         boolean active = this.activeRoostsClient.contains(p);
         String checkbox = active ? "[x]" : "[ ]";
         String label = "(" + p.getX() + "," + p.getY() + "," + p.getZ() + ")";
         if ((this.minecraft != null ? this.minecraft.level.getBlockEntity(p) : null) instanceof ICollectorTarget target) {
            try {
               String nm = target.getCustomName();
               if (nm != null && !nm.isEmpty()) {
                  label = nm + " ";
               }
            } catch (Throwable var29) {
            }
         }

         g.drawString(this.font, Component.literal(checkbox), listX, y, 16777215, false);
         g.drawString(this.font, Component.literal(label), listX + 14, y, 13421772, false);
         y += 12;
      }

      g.disableScissor();
      int sbX1 = listX + listW + 2;
      int sbX2 = sbX1 + 6;
      int sbY2 = listY + listH;
      g.fill(sbX1, listY, sbX2, sbY2, 1711276032);
      if (total > visibleRows) {
         int thumbH = Math.max(10, (int)((float)visibleRows / total * listH));
         int maxTop = total - visibleRows;
         float progress = maxTop > 0 ? (float)this.listTopIndex / maxTop : 0.0F;
         int thumbY = listY + (int)((listH - thumbH) * progress);
         g.fill(sbX1 + 1, thumbY, sbX2 - 1, thumbY + thumbH, -5592406);
      }

      g.pose().popPose();
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
      if (this.showRoostMenu) {
         int px = this.leftPos + (this.imageWidth - 160) / 2;
         int py = this.topPos + (this.imageHeight - 140) / 2;
         int listX = px + 6;
         int listY = py + 18 + 4;
         int listW = 140;
         int listH = 112;
         if (mouseX >= listX && mouseX <= listX + listW && mouseY >= listY && mouseY <= listY + listH) {
            int visibleRows = Math.max(1, listH / 12);
            int total = this.foundRoosts.size();
            if (total > visibleRows) {
               this.listTopIndex = this.listTopIndex - (int)Math.signum(deltaY);
               this.clampScroll();
               return true;
            }
         }
      }

      return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (!this.showRoostMenu) {
         return super.mouseClicked(mouseX, mouseY, button);
      } else {
         CollectorScreen.Geo g = this.geom();
         boolean handled = this.handleHeaderButtons(mouseX, mouseY, g)
            || this.handleScrollbar(mouseX, mouseY, g)
            || this.handleList(mouseX, mouseY, button, g)
            || inside(g.panelX1 - 2, g.panelY1 - 2, g.panelX2 + 2, g.panelY2 + 2, mouseX, mouseY);
         return handled || super.mouseClicked(mouseX, mouseY, button);
      }
   }

   private CollectorScreen.Geo geom() {
      int px = this.leftPos + (this.imageWidth - 160) / 2;
      int py = this.topPos + (this.imageHeight - 140) / 2;
      int minusX1 = px + 160 - 28;
      int minusX2 = minusX1 + 8;
      int plusX1 = px + 160 - 14;
      int plusX2 = plusX1 + 8;
      int headY1 = py + 4;
      int headY2 = headY1 + 10;
      int listX = px + 6;
      int listY = py + 18 + 4;
      int listW = 140;
      int listH = 112;
      int sbX1 = listX + 140 + 2;
      int sbX2 = sbX1 + 6;
      return new CollectorScreen.Geo(px, py, px + 160, py + 140, minusX1, minusX2, plusX1, plusX2, headY1, headY2, listX, listY, 140, 112, sbX1, sbX2);
   }

   private boolean handleHeaderButtons(double mx, double my, CollectorScreen.Geo g) {
      if (inside(g.minusX1, g.headY1, g.minusX2, g.headY2, mx, my)) {
         this.changeRange(-1);
         return true;
      } else if (inside(g.plusX1, g.headY1, g.plusX2, g.headY2, mx, my)) {
         this.changeRange(1);
         return true;
      } else {
         return false;
      }
   }

   private boolean handleScrollbar(double mx, double my, CollectorScreen.Geo g) {
      if (!inside(g.sbX1, g.listY, g.sbX2, g.listY + g.listH, mx, my)) {
         return false;
      } else {
         int visibleRows = Math.max(1, g.listH / 12);
         int total = this.foundRoosts.size();
         if (total <= visibleRows) {
            return false;
         } else {
            this.draggingScrollbar = true;
            this.dragStartMouseY = (int)my;
            this.dragStartTopIndex = this.listTopIndex;
            return true;
         }
      }
   }

   private boolean handleList(double mx, double my, int button, CollectorScreen.Geo g) {
      if (!inside(g.listX, g.listY, g.listX + g.listW, g.listY + g.listH, mx, my)) {
         return false;
      } else {
         int relY = (int)my - g.listY;
         int row = relY / 12;
         int idx = this.listTopIndex + row;
         if (idx >= 0 && idx < this.foundRoosts.size()) {
            int cbX1 = g.listX;
            int cbX2 = g.listX + 14;
            int rowY1 = g.listY + row * 12;
            int rowY2 = rowY1 + 12;
            if (inside(cbX1, rowY1, cbX2, rowY2, mx, my)) {
               this.toggleRoost(this.foundRoosts.get(idx));
               return true;
            }
         }

         return true;
      }
   }

   private static boolean inside(int x1, int y1, int x2, int y2, double mx, double my) {
      return mx >= x1 && mx <= x2 && my >= y1 && my <= y2;
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (this.showRoostMenu && this.draggingScrollbar) {
         int listH = 112;
         int visibleRows = Math.max(1, listH / 12);
         int total = this.foundRoosts.size();
         int maxTop = Math.max(0, total - visibleRows);
         if (total > visibleRows) {
            int dy = (int)mouseY - this.dragStartMouseY;
            int thumbH = Math.max(10, (int)((float)visibleRows / total * listH));
            int scrollSpace = listH - thumbH;
            if (scrollSpace < 1) {
               scrollSpace = 1;
            }

            float frac = (float)dy / scrollSpace;
            int deltaRows = Math.round(frac * maxTop);
            this.listTopIndex = this.dragStartTopIndex + deltaRows;
            this.clampScroll();
         }

         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (this.draggingScrollbar) {
         this.draggingScrollbar = false;
         return true;
      } else {
         return super.mouseReleased(mouseX, mouseY, button);
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
      int listH = 112;
      return Math.max(1, listH / 12);
   }

   private record Geo(
      int panelX1,
      int panelY1,
      int panelX2,
      int panelY2,
      int minusX1,
      int minusX2,
      int plusX1,
      int plusX2,
      int headY1,
      int headY2,
      int listX,
      int listY,
      int listW,
      int listH,
      int sbX1,
      int sbX2
   ) {
   }
}
