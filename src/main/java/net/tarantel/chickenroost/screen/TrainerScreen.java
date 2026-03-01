package net.tarantel.chickenroost.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.handler.TrainerHandler;
import net.tarantel.chickenroost.networking.SetAutoOutputPayload;
import net.tarantel.chickenroost.networking.SetNamePayload;
import net.tarantel.chickenroost.networking.SetTrainerLevelPayload;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

public class TrainerScreen extends AbstractContainerScreen<TrainerHandler> {
   private boolean colorblindMode = (Boolean)Config.trainer_cb.get();
   private static final ResourceLocation GUI = ChickenRoostMod.ownresource("textures/screens/trainer.png");
   private static final ResourceLocation ARROW = ChickenRoostMod.ownresource("textures/screens/newarrow.png");
   private static final ResourceLocation ARROW_VANILLA = ChickenRoostMod.ownresource("textures/screens/arrow.png");
   private static final ResourceLocation GUI_VANILLA = ChickenRoostMod.ownresource("textures/screens/trainer_vanilla.png");
   private static final ResourceLocation ARROWBACK_VANILLA = ChickenRoostMod.ownresource("textures/screens/arrowback.png");
   private EditBox nameField;
   private String enteredName = "TRAINER";
   private Button nameButton;
   private Button output;
   private EditBox levelField;
   private int enteredLevel = 128;
   private MultiLineTextWidget infotext;

   public TrainerScreen(TrainerHandler menu, Inventory inventory, Component component) {
      super(menu, inventory, component);
      this.imageWidth = 176;
      this.imageHeight = 166;
   }

   protected void init() {
      super.init();
      this.output = Button.builder(this.makeOutputText(), button -> {
         boolean newValue = !((TrainerHandler)this.menu).blockEntity.isAutoOutputEnabled();
         ((TrainerHandler)this.menu).blockEntity.setAutoOutputEnabled(newValue);
         button.setMessage(this.makeOutputText());
         PacketDistributor.sendToServer(new SetAutoOutputPayload(((TrainerHandler)this.menu).blockEntity.getBlockPos(), newValue), new CustomPacketPayload[0]);
      }).pos(this.leftPos, this.topPos - 30).size(70, 13).build();
      this.output.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.output.info")));
      this.addRenderableWidget(this.output);
      int levelX = this.leftPos + 87;
      int levelY = this.topPos - 27;
      this.levelField = new EditBox(this.font, levelX, levelY, 30, 20, Component.translatable("roost_chicken.interface.level"));
      this.levelField.setMaxLength(4);
      this.levelField.setFilter(s -> s.isEmpty() || s.matches("\\d+"));
      this.levelField.setValue(String.valueOf(((TrainerHandler)this.menu).blockEntity.getAutoOutputLevel()));
      this.addRenderableWidget(this.levelField);
      this.levelField.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.level.info")));
      int x = this.leftPos;
      int y = this.topPos - 17;
      Button b = Button.builder(Component.literal("V"), button -> {
         if (this.colorblindMode) {
            Config.trainer_cb.set(false);
            Config.trainer_cb.save();
         } else {
            Config.trainer_cb.set(true);
            Config.trainer_cb.save();
         }
      }).pos(this.leftPos, this.topPos - 17).size(13, 13).build();
      b.setTooltip(Tooltip.create(Component.translatable("roost_chicken.interface.uiswitch.info")));
      this.addRenderableWidget(b);
      int nameBtnX = this.leftPos + 14;
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
                  String currentname = ((TrainerHandler)this.menu).blockEntity.getCustomName();
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

   protected void containerTick() {
      super.containerTick();
      if (!this.levelField.isFocused()) {
         this.levelField.setValue(String.valueOf(((TrainerHandler)this.menu).blockEntity.getAutoOutputLevel()));
      }
   }

   private Component makeOutputText() {
      boolean enabled = ((TrainerHandler)this.menu).blockEntity.isAutoOutputEnabled();
      Component state = Component.translatable(enabled ? "roost_chicken.interface.output.on" : "roost_chicken.interface.output.off")
         .withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED);
      return Component.translatable("roost_chicken.interface.output.name", new Object[]{state});
   }

   private int getScaledProgress() {
      boolean colorblindMode = (Boolean)Config.trainer_cb.get();
      int arrowWidth = 54;
      return ((TrainerHandler)this.menu).getScaledProgress(arrowWidth);
   }

   protected void renderBg(@NotNull GuiGraphics ms, float partialTicks, int gx, int gy) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      if (this.colorblindMode) {
         RenderSystem.setShaderTexture(0, GUI_VANILLA);
         ms.blit(GUI_VANILLA, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
         ms.blit(ARROW, this.leftPos + 54, this.topPos + 31, 0.0F, 0.0F, this.getScaledProgress(), 33, 54, 33);
      } else {
         RenderSystem.setShaderTexture(0, GUI);
         ms.blit(GUI, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
         ms.blit(ARROW, this.leftPos + 54, this.topPos + 31, 0.0F, 0.0F, this.getScaledProgress(), 33, 54, 33);
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableBlend();
      this.colorblindMode = (Boolean)Config.trainer_cb.get();
   }

   public void render(@NotNull GuiGraphics ms, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(ms, mouseX, mouseY, partialTicks);
      super.render(ms, mouseX, mouseY, partialTicks);
      this.renderTooltip(ms, mouseX, mouseY);
   }

   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      Component label = Component.translatable("roost_chicken.interface.level");
      int x = 122;
      int y = -21;
      float scale = 1.5F;
      guiGraphics.pose().pushPose();
      guiGraphics.pose().scale(scale, scale, 1.0F);
      guiGraphics.drawString(this.font, label, (int)(x / scale), (int)(y / scale), 16777215, false);
      guiGraphics.pose().popPose();
   }

   public boolean isNameFieldFocused() {
      return this.nameField != null && this.nameField.isFocused();
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         if (this.nameField != null && this.nameField.isFocused()) {
            this.nameField.setFocused(false);
            return true;
         }

         if (this.levelField != null && this.levelField.isFocused()) {
            this.levelField.setFocused(false);
            return true;
         }
      }

      if (keyCode == 257 || keyCode == 257 || keyCode == 335) {
         if (this.nameField != null && this.nameField.isFocused()) {
            this.enteredName = this.nameField.getValue().trim();
            if (!this.enteredName.isEmpty()) {
               PacketDistributor.sendToServer(
                  new SetNamePayload(((TrainerHandler)this.menu).getBlockEntity().getBlockPos(), this.enteredName), new CustomPacketPayload[0]
               );
            }

            this.nameField.setFocused(false);
            return true;
         }

         if (this.levelField != null && this.levelField.isFocused()) {
            String txt = this.levelField.getValue().trim();
            int lvl = txt.isEmpty() ? 0 : Integer.parseInt(txt);
            ((TrainerHandler)this.menu).blockEntity.setAutoOutputLevelClient(lvl);
            PacketDistributor.sendToServer(
               new SetTrainerLevelPayload(((TrainerHandler)this.menu).getBlockEntity().getBlockPos(), lvl), new CustomPacketPayload[0]
            );
            this.levelField.setFocused(false);
            return true;
         }
      }

      if (this.nameField != null && this.nameField.isFocused()) {
         return this.nameField.keyPressed(keyCode, scanCode, modifiers) ? true : true;
      } else if (this.levelField == null || !this.levelField.isFocused()) {
         return super.keyPressed(keyCode, scanCode, modifiers);
      } else {
         return this.levelField.keyPressed(keyCode, scanCode, modifiers) ? true : true;
      }
   }

   public boolean charTyped(char codePoint, int modifiers) {
      if (this.nameField != null && this.nameField.isFocused()) {
         return this.nameField.charTyped(codePoint, modifiers) ? true : true;
      } else if (this.levelField == null || !this.levelField.isFocused()) {
         return super.charTyped(codePoint, modifiers);
      } else {
         return this.levelField.charTyped(codePoint, modifiers) ? true : true;
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.nameField != null && this.nameField.isFocused() && !this.nameField.isMouseOver(mouseX, mouseY)) {
         this.nameField.setFocused(false);
         return true;
      } else if (this.levelField != null && this.levelField.isFocused() && !this.levelField.isMouseOver(mouseX, mouseY)) {
         this.levelField.setFocused(false);
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }
}
