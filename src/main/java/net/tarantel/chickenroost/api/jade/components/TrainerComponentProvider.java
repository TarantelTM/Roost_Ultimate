package net.tarantel.chickenroost.api.jade.components;

import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.tarantel.chickenroost.api.jade.JadePlugin;
import net.tarantel.chickenroost.block.tile.TrainerTile;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.util.ModDataComponents;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.JadeIds;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.IElement.Align;

public enum TrainerComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
   INSTANCE;

   public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
      CompoundTag data = accessor.getServerData();
      if (accessor.getBlockEntity() instanceof TrainerTile breederBlockEntity) {
         BlockState breederBlockState = breederBlockEntity.getBlockState();
         if (breederBlockState == null) {
            return;
         }

         int progress = data.getInt("progress");
         ListTag furnaceItems = data.getList("furnace", 10);
         NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);

         for (int i = 0; i < furnaceItems.size(); i++) {
            inventory.set(i, ItemStack.parseOptional(accessor.getLevel().registryAccess(), furnaceItems.getCompound(i)));
         }

         int total = data.getInt("total");
         int level = data.getInt("level");
         int xp = data.getInt("xp");
         int maxlevel = data.getInt("maxlevel");
         int maxxp = data.getInt("maxxp");
         String CustomName = data.getString("customname");
         boolean autooutput = data.getBoolean("autooutput");
         IElementHelper helper = IElementHelper.get();
         IElementHelper helper2 = IElementHelper.get();
         if (!((ItemStack)inventory.get(1)).isEmpty()) {
            tooltip.add(helper.item((ItemStack)inventory.get(1)).align(Align.LEFT));
         }

         tooltip.append(helper.spacer(4, 0).align(Align.LEFT));
         tooltip.append(helper.progress((float)progress / total).translate(new Vec2(-2.0F, 0.0F)).align(Align.LEFT));
         if (!((ItemStack)inventory.get(0)).isEmpty()) {
            tooltip.append(helper.item((ItemStack)inventory.get(0)).align(Align.LEFT));
         }

         tooltip.add(helper.text(Component.literal("LvL: " + level + "/" + maxlevel)).align(Align.LEFT));
         tooltip.add(helper.text(Component.literal("XP: " + xp + "/" + maxxp)).align(Align.LEFT));
         tooltip.add(
            helper2.text(Component.translatable("roost_chicken.interface.level.jade", new Object[]{data.getInt("maxlevel")}).withStyle(ChatFormatting.WHITE))
               .align(Align.LEFT)
         );
         tooltip.add(
            helper2.text(
                  Component.translatable("roost_chicken.interface.output.name.jade")
                     .withStyle(ChatFormatting.WHITE)
                     .append(
                        Component.translatable(autooutput ? "roost_chicken.interface.output.on" : "roost_chicken.interface.output.off")
                           .withStyle(autooutput ? ChatFormatting.GREEN : ChatFormatting.RED)
                     )
               )
               .align(Align.LEFT)
         );
         tooltip.add(helper2.text(Component.literal("§o§1Roost Ultimate")).align(Align.LEFT));
         tooltip.remove(JadeIds.UNIVERSAL_ITEM_STORAGE);
         tooltip.remove(JadeIds.CORE_MOD_NAME);
         tooltip.remove(JadeIds.UNIVERSAL_ITEM_STORAGE_ITEMS_PER_LINE);
         tooltip.remove(JadeIds.CORE_OBJECT_NAME);
         Component translation = Component.translatable("block.chicken_roost.trainer");
         tooltip.add(0, helper.text(Component.empty().append(translation).append(" [" + CustomName + "]").withStyle(style -> style.withBold(true))));
      }
   }

   public int getDefaultPriority() {
      return 10000;
   }

   public void appendServerData(CompoundTag data, BlockAccessor accessor) {
      TrainerTile breeder = (TrainerTile)accessor.getBlockEntity();
      if (breeder instanceof TrainerTile) {
         TrainerTile furnace = breeder;
         ListTag items = new ListTag();

         for (int i = 0; i < 2; i++) {
            items.add(furnace.itemHandler.getStackInSlot(i).saveOptional(accessor.getLevel().registryAccess()));
         }

         data.putString("customname", breeder.getCustomName());
         data.putBoolean("autooutput", breeder.isAutoOutputEnabled());
         data.putInt("maxlevel", breeder.getAutoOutputLevel());
         if (!breeder.itemHandler.getStackInSlot(0).isEmpty()) {
            ChickenItemBase ChickenItem = (ChickenItemBase)breeder.itemHandler.getStackInSlot(0).getItem();
            ItemStack MyChicken = breeder.itemHandler.getStackInSlot(0);
            data.putInt("level", (Integer)MyChicken.get((DataComponentType)ModDataComponents.CHICKENLEVEL.value()));
            data.putInt("xp", (Integer)MyChicken.get((DataComponentType)ModDataComponents.CHICKENXP.value()));
            data.putInt("maxlevel", breeder.LevelList[ChickenItem.currentchickena(ChickenItem.getDefaultInstance())]);
            data.putInt("maxxp", breeder.XPList[ChickenItem.currentchickena(ChickenItem.getDefaultInstance())]);
            data.put("furnace", items);
            data.putInt("progress", breeder.progress);
            data.putInt("total", breeder.maxProgress);
         } else {
            data.put("furnace", items);
            data.putInt("progress", breeder.progress);
            data.putInt("total", breeder.maxProgress);
         }
      }
   }

   public ResourceLocation getUid() {
      return JadePlugin.TRAINER_PROGRESS;
   }
}
