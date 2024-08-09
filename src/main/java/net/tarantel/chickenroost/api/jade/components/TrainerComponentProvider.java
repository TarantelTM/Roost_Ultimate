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
import net.tarantel.chickenroost.block.tile.Trainer_Tile;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum TrainerComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private TrainerComponentProvider(){

    }
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        BlockEntity blockEntity = accessor.getBlockEntity();
        if (blockEntity instanceof Trainer_Tile breederBlockEntity) {
            BlockState breederBlockState = breederBlockEntity.getBlockState();
            if (breederBlockState == null)
                return;
            int progress = data.getInt("progress");
            ListTag furnaceItems = data.getList("furnace", 10);
            NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);
            for (int i = 0; i < furnaceItems.size(); ++i) {
                inventory.set(i, ItemStack.parseOptional(accessor.getLevel().registryAccess(), furnaceItems.getCompound(i)));
            }
            int total = data.getInt("total");
            int level = data.getInt("level");
            int xp = data.getInt("xp");
            int maxlevel = data.getInt("maxlevel");
            int maxxp = data.getInt("maxxp");
            IElementHelper helper = IElementHelper.get();
            IElementHelper helper2 = IElementHelper.get();
            if (!inventory.get(1).isEmpty()){
                tooltip.add(helper.item((ItemStack) inventory.get(1)).align(IElement.Align.LEFT));
            }
            //tooltip.append(helper.item((ItemStack)inventory.get(1)).align(IElement.Align.LEFT));
            tooltip.append(helper.spacer(4, 0).align(IElement.Align.LEFT));
            tooltip.append(helper.progress((float)progress / (float)total).translate(new Vec2(-2.0F, 0.0F)).align(IElement.Align.LEFT));
            if (!inventory.get(0).isEmpty()) {
                tooltip.append(helper.item((ItemStack) inventory.get(0)).align(IElement.Align.LEFT));
            }
            tooltip.add(helper.text(Component.literal("LvL: " + level + "/" + maxlevel)).align(IElement.Align.LEFT));
            tooltip.add(helper.text(Component.literal("XP: " + xp + "/" + maxxp)).align(IElement.Align.LEFT));
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
        Trainer_Tile breeder = (Trainer_Tile) accessor.getBlockEntity();
        if (breeder instanceof Trainer_Tile furnace) {

            ListTag items = new ListTag();

            for (int i = 0; i < 2; ++i) {
                items.add(furnace.itemHandler.getStackInSlot(i).saveOptional(accessor.getLevel().registryAccess()));
            }

            if (!breeder.itemHandler.getStackInSlot(1).isEmpty()) {


                @Nullable ChickenItemBase ChickenItem = (ChickenItemBase) breeder.itemHandler.getStackInSlot(0).getItem();
                @Nullable ItemStack MyChicken = breeder.itemHandler.getStackInSlot(0);
                //ChickenSeedBase FoodItem = (ChickenSeedBase) breeder.itemHandler.getStackInSlot(1).getItem();
                data.putInt("level", MyChicken.get(ModDataComponents.CHICKENLEVEL.value()));
                data.putInt("xp", MyChicken.get(ModDataComponents.CHICKENXP.value()));
                data.putInt("maxlevel", breeder.LevelList[ChickenItem.currentchickena]);
                data.putInt("maxxp", breeder.XPList[ChickenItem.currentchickena]);
                data.put("furnace", items);
                data.putInt("progress", breeder.getScaledProgress());
                data.putInt("total", breeder.maxProgress);
            }
            else {
                data.put("furnace", items);
                data.putInt("progress", breeder.getScaledProgress());
                data.putInt("total", breeder.maxProgress);
            }


        }
    }

    @Override
    public ResourceLocation getUid() {
        return JadePlugin.TRAINER_PROGRESS;
    }
}