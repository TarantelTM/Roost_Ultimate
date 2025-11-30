package net.tarantel.chickenroost.api.jade.components;


import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.phys.Vec2;
import net.tarantel.chickenroost.api.jade.JadePlugin;
import net.tarantel.chickenroost.block.tile.BreederTile;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum BreederComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private BreederComponentProvider(){

    }
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        BlockEntity blockEntity = accessor.getBlockEntity();
        if (blockEntity instanceof BreederTile breederBlockEntity) {
            BlockState breederBlockState = breederBlockEntity.getBlockState();
            if (breederBlockState == null)
                return;
            int progress = data.getInt("progress");
            ListTag furnaceItems = data.getList("furnace", 10);
            NonNullList<ItemStack> inventory = NonNullList.withSize(11, ItemStack.EMPTY);
            for(int i = 0; i < furnaceItems.size(); ++i) {
                inventory.set(i, ItemStack.parseOptional(accessor.getLevel().registryAccess(), furnaceItems.getCompound(i)));
            }
            int total = data.getInt("total");
            IElementHelper helper = IElementHelper.get();
            IElementHelper helper2 = IElementHelper.get();
            tooltip.add(helper.item((ItemStack)inventory.get(1)).align(IElement.Align.LEFT));
            tooltip.append(helper.item((ItemStack)inventory.get(2)).align(IElement.Align.LEFT));
            tooltip.append(helper.item((ItemStack)inventory.get(0)).align(IElement.Align.LEFT));
            tooltip.append(helper.spacer(4, 0).align(IElement.Align.LEFT));
            tooltip.append(helper.progress((float)progress / (float)total).translate(new Vec2(-2.0F, 0.0F)).align(IElement.Align.LEFT));
            tooltip.append(helper.item((ItemStack)inventory.get(3)).align(IElement.Align.LEFT));
            tooltip.append(helper.item((ItemStack)inventory.get(4)).align(IElement.Align.LEFT));
            tooltip.append(helper.item((ItemStack)inventory.get(5)).align(IElement.Align.LEFT));
            tooltip.append(helper.item((ItemStack)inventory.get(6)).align(IElement.Align.LEFT));

            tooltip.add(helper2.text(Component.literal("\u00A7o" + "\u00A71" + "Roost Ultimate")).align(IElement.Align.LEFT));
            tooltip.append(helper2.spacer(11, 0).align(IElement.Align.LEFT));
            tooltip.append(helper2.item((ItemStack)inventory.get(7)).align(IElement.Align.LEFT));
            tooltip.append(helper2.item((ItemStack)inventory.get(8)).align(IElement.Align.LEFT));
            tooltip.append(helper2.item((ItemStack)inventory.get(9)).align(IElement.Align.LEFT));
            tooltip.append(helper2.item((ItemStack)inventory.get(10)).align(IElement.Align.LEFT));
            tooltip.remove(JadeIds.UNIVERSAL_ITEM_STORAGE);
            tooltip.remove(JadeIds.CORE_MOD_NAME);
            tooltip.remove(JadeIds.UNIVERSAL_ITEM_STORAGE_ITEMS_PER_LINE);


        }
    }
    @Override
    public int getDefaultPriority() {
        return TooltipPosition.TAIL;
    }
    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        BreederTile breeder = (BreederTile) accessor.getBlockEntity();
        if (breeder instanceof BreederTile furnace) {

            ListTag items = new ListTag();

            for (int i = 0; i < 11; ++i) {
                items.add(furnace.itemHandler.getStackInSlot(i).saveOptional(accessor.getLevel().registryAccess()));
            }

            data.put("furnace", items);

            data.putInt("progress", breeder.progress);
            data.putInt("total", breeder.maxProgress);

        }
    }

    @Override
    public ResourceLocation getUid() {
        return JadePlugin.BREEDER_PROGRESS;
    }
}