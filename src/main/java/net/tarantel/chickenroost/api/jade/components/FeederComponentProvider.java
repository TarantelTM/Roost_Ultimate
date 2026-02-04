package net.tarantel.chickenroost.api.jade.components;

import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tarantel.chickenroost.api.jade.JadePlugin;
import net.tarantel.chickenroost.block.tile.FeederTile;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.JadeIds;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.IElement.Align;

public enum FeederComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
   INSTANCE;

   public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
      CompoundTag data = accessor.getServerData();
      if (accessor.getBlockEntity() instanceof FeederTile breederBlockEntity) {
         ListTag furnaceItems = data.getList("furnace", 10);
         NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);

         for (int i = 0; i < furnaceItems.size(); i++) {
            inventory.set(i, ItemStack.parseOptional(accessor.getLevel().registryAccess(), furnaceItems.getCompound(i)));
         }

         boolean autooutput = data.getBoolean("roundrobin");
         FeederTile.StackSendMode sendMode = FeederTile.StackSendMode.byId(data.getInt("sendmode"));
         IElementHelper helper2 = IElementHelper.get();
         tooltip.add(
            helper2.text(
                  Component.translatable("roost_chicken.interface.roundrobin.name.jade")
                     .withStyle(ChatFormatting.WHITE)
                     .append(
                        Component.translatable(autooutput ? "roost_chicken.interface.output.on" : "roost_chicken.interface.output.off")
                           .withStyle(autooutput ? ChatFormatting.GREEN : ChatFormatting.RED)
                     )
               )
               .align(Align.LEFT)
         );

         tooltip.add(
            helper2.text(
                  Component.translatable("roost_chicken.interface.sendmode.name.jade")
                     .withStyle(ChatFormatting.WHITE)
                     .append(Component.translatable("roost_chicken.interface.sendmode." + sendMode.name().toLowerCase()).withStyle(switch (sendMode) {
                        case SINGLE -> ChatFormatting.GRAY;
                        case HALF -> ChatFormatting.YELLOW;
                        case FULL -> ChatFormatting.GREEN;
                     }))
               )
               .align(Align.LEFT)
         );
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
      FeederTile breeder = (FeederTile)accessor.getBlockEntity();
      if (breeder instanceof FeederTile) {
         data.putBoolean("roundrobin", breeder.isRoundRobinEnabled());
         data.putInt("sendmode", breeder.getStackSendMode().id());
         data.putInt("range", breeder.getFeedRange());
      }
   }

   public ResourceLocation getUid() {
      return JadePlugin.FEEDER;
   }
}
