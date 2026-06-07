package net.tarantel.chickenroost.api.jade.components;

import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.api.jade.JadePlugin;
import net.tarantel.chickenroost.block.tile.CollectorTile;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.JadeIds;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.IElement.Align;

public enum CollectorComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
   INSTANCE;

   public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
      CompoundTag data = accessor.getServerData();
      if (accessor.getBlockEntity() instanceof CollectorTile breederBlockEntity) {
         ListTag furnaceItems = data.getList("furnace", 10);
         NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);

         for (int i = 0; i < furnaceItems.size(); i++) {
            inventory.set(i, ItemStack.parseOptional(accessor.getLevel().registryAccess(), furnaceItems.getCompound(i)));
         }

         IElementHelper helper2 = IElementHelper.get();
         tooltip.add(
            helper2.text(Component.translatable("roost_chicken.interface.range.jade", new Object[]{data.getInt("range")}).withStyle(ChatFormatting.WHITE))
               .align(Align.LEFT)
         );
         tooltip.add(helper2.text(Component.literal("§o§1Roost Ultimate")).align(Align.LEFT));
         tooltip.remove(JadeIds.CORE_MOD_NAME);
      }
   }

   public int getDefaultPriority() {
      return 10000;
   }

   public void appendServerData(CompoundTag data, BlockAccessor accessor) {
      CollectorTile breeder = (CollectorTile)accessor.getBlockEntity();
      if (breeder instanceof CollectorTile) {
         data.putInt("range", breeder.getCollectRange());
      }
   }

   public ResourceLocation getUid() {
      return JadePlugin.COLLECTOR;
   }
}
