package net.tarantel.chickenroost.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.handler.BreederHandler;
import net.tarantel.chickenroost.networking.SetAutoOutputPayload;
import net.tarantel.chickenroost.networking.SetNamePayload;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

public class BreederScreen extends AbstractContainerScreen<BreederHandler> {
   private boolean colorblindMode = (Boolean)Config.breeder_cb.get();
   private static final ResourceLocation GUI = ChickenRoostMod.ownresource("textures/screens/breeder.png");
   private static final ResourceLocation ARROW = ChickenRoostMod.ownresource("textures/screens/newarrow.png");
   private static final ResourceLocation ARROW_VANILLA = ChickenRoostMod.ownresource("textures/screens/arrow.png");
   private static final ResourceLocation GUI_VANILLA = ChickenRoostMod.ownresource("textures/screens/breeder_vanilla.png");
   private static final ResourceLocation ARROWBACK_VANILLA = ChickenRoostMod.ownresource("textures/screens/arrowback.png");
   private EditBox nameField;
   private String enteredName = "BREEDER";
   private Button nameButton;
   private Button output;

   public BreederScreen(BreederHandler menu, Inventory inventory, Component component) {
      super(menu, inventory, component);
      this.imageWidth = 176;
      this.imageHeight = 166;
   }

   protected void containerTick() {
      super.containerTick();
      if (this.output != null) {
         this.output.setMessage(this.makeOutputText());
      }
   }

   protected void init() {
      super.init();
      int x = this.leftPos;
      int y = this.topPos - 17;
      this.output = Button.builder(this.makeOutputText(), button -> {
         boolean newValue = !((BreederHandler)this.menu).blockEntity.isAutoOutputEnabled();
         ((BreederHandler)this.menu).blockEntity.setAutoOutputEnabled(newValue);
         button.setMessage(this.makeOutputText());
         PacketDistributor.sendToServer(new SetAutoOutputPayload(((BreederHandler)this.menu).blockEntity.getBlockPos(), newValue), new CustomPacketPayload[0]);
      }).pos(this.leftPos + 13, this.topPos - 17).size(70, 13).build();
      this.addRenderableWidget(this.output);
      this.output.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.output.info")));
      Button b = Button.builder(Component.literal("V"), button -> {
         if (this.colorblindMode) {
            Config.breeder_cb.set(false);
            Config.breeder_cb.save();
         } else {
            Config.breeder_cb.set(true);
            Config.breeder_cb.save();
         }
      }).pos(this.leftPos, this.topPos - 17).size(13, 13).build();
      b.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.uiswitch.info")));
      this.addRenderableWidget(b);
      int nameBtnX = this.leftPos + 20 + 2 + 61;
      int nameBtnY = this.topPos - 17;
      this.nameButton = Button.builder(
            Component.translatable("roost_chicken.interface.name"),
            btn -> {
               int fieldX = nameBtnX + 40 + 2;
               int fieldY = this.topPos - 21;
               if (this.nameField == null) {
                  this.nameField = new EditBox(
                     this.font, fieldX, fieldY, 60, 20, Component.translatable("roost_chicken.interface.name").withStyle(style -> style.withColor(16777215))
                  );
                  this.nameField.setMaxLength(32);
                  String currentname = ((BreederHandler)this.menu).blockEntity.getCustomName();
                  this.nameField.setValue(currentname);
                  this.nameField.setFocused(true);
                  this.addRenderableWidget(this.nameField);
               } else {
                  this.nameField.setFocused(true);
               }
            }
         )
         .pos(nameBtnX, nameBtnY)
         .size(40, 13)
         .build();
      this.nameButton.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.setname")));
      this.addRenderableWidget(this.nameButton);
   }

   private Component makeOutputText() {
      boolean enabled = ((BreederHandler)this.menu).blockEntity.isAutoOutputEnabled();
      Component state = Component.translatable(enabled ? "roost_chicken.interface.output.on" : "roost_chicken.interface.output.off")
         .withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED);
      return Component.translatable("roost_chicken.interface.output.name", new Object[]{state});
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.nameField != null && this.nameField.isFocused() && !this.nameField.isMouseOver(mouseX, mouseY)) {
         this.nameField.setFocused(false);
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.nameField == null || !this.nameField.isFocused()) {
         return super.keyPressed(keyCode, scanCode, modifiers);
      } else if (keyCode == 256) {
         this.nameField.setFocused(false);
         return true;
      } else if (keyCode == 257 || keyCode == 257 || keyCode == 335) {
         this.enteredName = this.nameField.getValue().trim();
         if (!this.enteredName.isEmpty()) {
            PacketDistributor.sendToServer(
               new SetNamePayload(((BreederHandler)this.menu).getBlockEntity().getBlockPos(), this.enteredName), new CustomPacketPayload[0]
            );
         }

         this.nameField.setFocused(false);
         return true;
      } else {
         return this.nameField.keyPressed(keyCode, scanCode, modifiers) ? true : true;
      }
   }

   public boolean charTyped(char codePoint, int modifiers) {
      if (this.nameField == null || !this.nameField.isFocused()) {
         return super.charTyped(codePoint, modifiers);
      } else {
         return this.nameField.charTyped(codePoint, modifiers) ? true : true;
      }
   }

   private int getScaledProgress() {
      boolean colorblindMode = (Boolean)Config.breeder_cb.get();
      int arrowWidth = 54;
      return ((BreederHandler)this.menu).getScaledProgress(arrowWidth);
   }

   protected void renderBg(@NotNull GuiGraphics ms, float partialTicks, int gx, int gy) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      if (this.colorblindMode) {
         RenderSystem.setShaderTexture(0, GUI_VANILLA);
         ms.blit(GUI_VANILLA, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
         ms.blit(ARROW, this.leftPos + 46, this.topPos + 35, 0.0F, 0.0F, this.getScaledProgress(), 33, 54, 33);
      } else {
         RenderSystem.setShaderTexture(0, GUI);
         ms.blit(GUI, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
         ms.blit(ARROW, this.leftPos + 46, this.topPos + 35, 0.0F, 0.0F, this.getScaledProgress(), 33, 54, 33);
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableBlend();
      this.colorblindMode = (Boolean)Config.breeder_cb.get();
   }

   public void render(@NotNull GuiGraphics ms, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(ms, mouseX, mouseY, partialTicks);
      super.render(ms, mouseX, mouseY, partialTicks);
      this.renderTooltip(ms, mouseX, mouseY);
   }

   protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
   }

   public boolean isNameFieldFocused() {
      return this.nameField != null && this.nameField.isFocused();
   }
}
