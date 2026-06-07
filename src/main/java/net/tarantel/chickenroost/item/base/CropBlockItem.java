package net.tarantel.chickenroost.item.base;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.block.Block;
import net.tarantel.chickenroost.util.Config;
import org.jetbrains.annotations.NotNull;

public class CropBlockItem extends ChickenSeedBase {
   private final int currentmaxxpp;

   public CropBlockItem(Block p_41579_, Properties p_41580_, int currentmaxxp) {
      super(p_41579_, p_41580_, currentmaxxp);
      this.currentmaxxpp = currentmaxxp;
   }

   @NotNull
   @Override
   public String getDescriptionId() {
      return this.getOrCreateDescriptionId();
   }

   @Override
   public void appendHoverText(@NotNull ItemStack itemstack, @NotNull TooltipContext world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
      int maxLevel = switch (this.currentmaxxpp) {
         case 1 -> Config.food_xp_tier_2.get();
         case 2 -> Config.food_xp_tier_3.get();
         case 3 -> Config.food_xp_tier_4.get();
         case 4 -> Config.food_xp_tier_5.get();
         case 5 -> Config.food_xp_tier_6.get();
         case 6 -> Config.food_xp_tier_7.get();
         case 7 -> Config.food_xp_tier_8.get();
         case 8 -> Config.food_xp_tier_9.get();
         default -> Config.food_xp_tier_1.get();
      };
      super.appendHoverText(itemstack, world, list, flag);
      list.add(Component.nullToEmpty("§aXP: §9" + maxLevel));
      list.add(Component.nullToEmpty("§1 Roost Ultimate"));
   }

   @Override
   public int getCurrentMaxXp() {
      return this.currentmaxxpp;
   }
}
