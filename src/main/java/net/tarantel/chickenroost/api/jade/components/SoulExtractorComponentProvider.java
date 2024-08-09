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
import net.tarantel.chickenroost.block.tile.Roost_Tile;
import net.tarantel.chickenroost.block.tile.Soul_Extractor_Tile;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum SoulExtractorComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private SoulExtractorComponentProvider(){

    }
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        BlockEntity blockEntity = accessor.getBlockEntity();
        if (blockEntity instanceof Soul_Extractor_Tile breederBlockEntity) {
            BlockState breederBlockState = breederBlockEntity.getBlockState();
            if (breederBlockState == null)
                return;
            int progress = data.getInt("progress");
            ListTag furnaceItems = data.getList("furnace", 10);
            NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);
            for(int i = 0; i < furnaceItems.size(); ++i) {
                inventory.set(i, ItemStack.parseOptional(accessor.getLevel().registryAccess(), furnaceItems.getCompound(i)));
            }
            int total = data.getInt("total");
            IElementHelper helper = IElementHelper.get();
            IElementHelper helper2 = IElementHelper.get();
            tooltip.add(helper.item((ItemStack)inventory.get(0)).align(IElement.Align.LEFT));
            tooltip.append(helper.spacer(4, 0).align(IElement.Align.LEFT));
            tooltip.append(helper.progress((float)progress / (float)total).translate(new Vec2(-2.0F, 0.0F)).align(IElement.Align.LEFT));
            tooltip.append(helper.item((ItemStack)inventory.get(1)).align(IElement.Align.LEFT));
            tooltip.add(helper2.text(Component.literal("\u00A7o" + "\u00A71" + "Roost Ultimate")).align(IElement.Align.LEFT));
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
        Soul_Extractor_Tile breeder = (Soul_Extractor_Tile) accessor.getBlockEntity();
        if (breeder instanceof Soul_Extractor_Tile furnace) {

            ListTag items = new ListTag();

            for (int i = 0; i < 2; ++i) {
                items.add(furnace.itemHandler.getStackInSlot(i).saveOptional(accessor.getLevel().registryAccess()));
            }

            data.put("furnace", items);

            data.putInt("progress", breeder.getScaledProgress());
            data.putInt("total", breeder.maxProgress);

        }
    }

    @Override
    public ResourceLocation getUid() {
        return JadePlugin.SOULEXTRACTOR_PROGRESS;
    }
}