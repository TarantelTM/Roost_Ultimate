package net.tarantel.chickenroost.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import java.time.LocalDate;
import java.time.Month;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.client.model.Modelchicken;
import net.tarantel.chickenroost.client.model.SantaHatLayer;
import net.tarantel.chickenroost.entity.BaseChickenEntity;
import org.jetbrains.annotations.NotNull;

public class BaseChickenRenderer extends MobRenderer<BaseChickenEntity, Modelchicken<BaseChickenEntity>> {
   public String texture;

   public BaseChickenRenderer(Context context, String texture) {
      super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5F);
      this.texture = texture;
      if (LocalDate.now().getMonth().equals(Month.DECEMBER)) {
         this.addLayer(new SantaHatLayer(this));
      }
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull BaseChickenEntity entity) {
      return ResourceLocation.parse("chicken_roost:textures/entities/" + this.texture + ".png");
   }

   protected void scale(BaseChickenEntity chicken, @NotNull PoseStack poseStack, float partialTickTime) {
      if (chicken.isBaby()) {
         float scale = 0.5F;
         poseStack.scale(scale, scale, scale);
         this.shadowRadius = 0.15F;
      } else {
         super.scale(chicken, poseStack, partialTickTime);
      }
   }
}
