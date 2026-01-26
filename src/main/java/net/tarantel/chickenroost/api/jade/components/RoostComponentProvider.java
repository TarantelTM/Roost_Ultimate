package net.tarantel.chickenroost.api.jade.components;

import net.minecraft.ChatFormatting;
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
import net.tarantel.chickenroost.block.tile.RoostTile;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.util.ModDataComponents;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum RoostComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private RoostComponentProvider(){

    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        BlockEntity blockEntity = accessor.getBlockEntity();
        if (blockEntity instanceof RoostTile breederBlockEntity) {
            BlockState breederBlockState = breederBlockEntity.getBlockState();
            if (breederBlockState == null)
                return;
            int progress = data.getInt("progress");
            ListTag furnaceItems = data.getList("furnace", 10);
            NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
            for(int i = 0; i < furnaceItems.size(); ++i) {
                inventory.set(i, ItemStack.parseOptional(accessor.getLevel().registryAccess(), furnaceItems.getCompound(i)));
            }
            int total = data.getInt("total");
            int level = data.getInt("level");
            int xp = data.getInt("xp");
            int maxlevel = data.getInt("maxlevel");
            int maxxp = data.getInt("maxxp");
            boolean autooutput = data.getBoolean("autooutput");
            String CustomName = data.getString("customname");
            IElementHelper helper = IElementHelper.get();
            IElementHelper helper2 = IElementHelper.get();
            tooltip.add(helper.item((ItemStack)inventory.get(0)).align(IElement.Align.LEFT));
            tooltip.append(helper.item((ItemStack)inventory.get(1)).align(IElement.Align.LEFT));
            tooltip.append(helper.spacer(4, 0).align(IElement.Align.LEFT));
            tooltip.append(helper.progress((float)progress / (float)total).translate(new Vec2(-2.0F, 0.0F)).align(IElement.Align.LEFT));
            tooltip.append(helper.item((ItemStack)inventory.get(2)).align(IElement.Align.LEFT));
            tooltip.add(helper.text(Component.literal("LvL: " + level + "/" + maxlevel)).align(IElement.Align.LEFT));
            tooltip.add(helper.text(Component.literal("XP: " + xp + "/" + maxxp)).align(IElement.Align.LEFT));
            tooltip.add(
                    helper2.text(
                            Component.translatable("roost_chicken.interface.output.name.jade")
                                    .withStyle(ChatFormatting.WHITE)
                                    .append(
                                            Component.translatable(
                                                    autooutput
                                                            ? "roost_chicken.interface.output.on"
                                                            : "roost_chicken.interface.output.off"
                                            ).withStyle(autooutput ? ChatFormatting.GREEN : ChatFormatting.RED)
                                    )
                    ).align(IElement.Align.LEFT)
            );

            tooltip.add(helper2.text(Component.literal("\u00A7o" + "\u00A71" + "Roost Ultimate")).align(IElement.Align.LEFT));
            tooltip.remove(JadeIds.UNIVERSAL_ITEM_STORAGE);
            tooltip.remove(JadeIds.CORE_MOD_NAME);
            tooltip.remove(JadeIds.UNIVERSAL_ITEM_STORAGE_ITEMS_PER_LINE);
            tooltip.remove(JadeIds.CORE_OBJECT_NAME);
            Component translation = Component.translatable("block.chicken_roost.roost");

            tooltip.add(0, helper.text(
                    Component.empty()
                            .append(translation)
                            .append(" [" + CustomName + "]")
                            .withStyle(style -> style.withBold(true))
            ));



        }
    }
    @Override
    public int getDefaultPriority() {
        return TooltipPosition.TAIL;
    }
    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        RoostTile breeder = (RoostTile) accessor.getBlockEntity();
        if (breeder instanceof RoostTile furnace) {

            data.putString("customname", breeder.getCustomName());
            ListTag items = new ListTag();

            for (int i = 0; i < 3; ++i) {
                items.add(furnace.itemHandler.getStackInSlot(i).saveOptional(accessor.getLevel().registryAccess()));
            }
            data.putBoolean("autooutput", breeder.isAutoOutputEnabled());
            if (!breeder.itemHandler.getStackInSlot(1).isEmpty()) {


            @Nullable ChickenItemBase ChickenItem = (ChickenItemBase) breeder.itemHandler.getStackInSlot(1).getItem();
            @Nullable ItemStack MyChicken = breeder.itemHandler.getStackInSlot(1);
            data.putInt("level", MyChicken.get(ModDataComponents.CHICKENLEVEL.value()));
            data.putInt("xp", MyChicken.get(ModDataComponents.CHICKENXP.value()));
            data.putInt("maxlevel", breeder.LevelList[ChickenItem.currentchickena(ChickenItem.getDefaultInstance())]);
            data.putInt("maxxp", breeder.XPList[ChickenItem.currentchickena(ChickenItem.getDefaultInstance())]);
                data.put("furnace", items);
                data.putInt("progress", breeder.progress);
                data.putInt("total", breeder.maxProgress);
        }
            else {
                data.put("furnace", items);
                data.putInt("progress", breeder.progress);
                data.putInt("total", breeder.maxProgress);
            }


        }
    }

    @Override
    public ResourceLocation getUid() {
        return JadePlugin.ROOST_PROGRESS;
    }
}
