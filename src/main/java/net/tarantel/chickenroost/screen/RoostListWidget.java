//package net.tarantel.chickenroost.screen;
//
//import mezz.jei.library.gui.widgets.AbstractScrollWidget;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.chat.Component;
//
//public class RoostListWidget extends AbstractScrollWidget {
//
//    private final FeederScreen screen;
//
//    public RoostListWidget(int x, int y, int w, int h, FeederScreen screen) {
//        super(x, y, w, h, Component.empty());
//        this.screen = screen;
//    }
//
//    @Override
//    protected int getInnerHeight() {
//        return screen.getRoostCount() * 22;
//    }
//
//    @Override
//    protected void renderContents(GuiGraphics g, int mouseX, int mouseY, float delta) {
//        int y = getY() - scrollAmount();
//
//        for (BlockPos pos : screen.getVisibleRoosts()) {
//            screen.renderRoostRow(g, pos, getX(), y);
//            y += 22;
//        }
//    }
//
//    @Override
//    protected int getVisibleAmount() {
//        return 0;
//    }
//
//    @Override
//    protected int getHiddenAmount() {
//        return 0;
//    }
//
//    @Override
//    protected void drawContents(GuiGraphics guiGraphics, double v, double v1, float v2) {
//
//    }
//
//    @Override
//    protected float calculateScrollAmount(double v) {
//        return 0;
//    }
//}
//