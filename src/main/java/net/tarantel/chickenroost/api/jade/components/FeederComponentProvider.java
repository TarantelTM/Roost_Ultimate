package net.tarantel.chickenroost.api.jade.components;

import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.tarantel.chickenroost.api.jade.JadePlugin;
import net.tarantel.chickenroost.block.tile.FeederTile;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum FeederComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private FeederComponentProvider(){

    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        BlockEntity blockEntity = accessor.getBlockEntity();
        if (blockEntity instanceof FeederTile breederBlockEntity) {
            ListTag furnaceItems = data.getList("furnace", 10);
            NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
            for(int i = 0; i < furnaceItems.size(); ++i) {
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
                                            Component.translatable(
                                                    autooutput
                                                            ? "roost_chicken.interface.output.on"
                                                            : "roost_chicken.interface.output.off"
                                            ).withStyle(autooutput ? ChatFormatting.GREEN : ChatFormatting.RED)
                                    )
                    ).align(IElement.Align.LEFT)
            );
            tooltip.add(
                    helper2.text(
                            Component.translatable("roost_chicken.interface.sendmode.name.jade")
                                    .withStyle(ChatFormatting.WHITE)
                                    .append(
                                            Component.translatable(
                                                    "roost_chicken.interface.sendmode." + sendMode.name().toLowerCase()
                                            ).withStyle(switch (sendMode) {
                                                case SINGLE -> ChatFormatting.GRAY;
                                                case HALF -> ChatFormatting.YELLOW;
                                                case FULL -> ChatFormatting.GREEN;
                                            })
                                    )
                    ).align(IElement.Align.LEFT)
            );
            tooltip.add(
                    helper2.text(
                            Component.translatable("roost_chicken.interface.range.jade", data.getInt("range"))
                                    .withStyle(ChatFormatting.WHITE)
                    ).align(IElement.Align.LEFT)
            );
            tooltip.add(helper2.text(Component.literal("\u00A7o" + "\u00A71" + "Roost Ultimate")).align(IElement.Align.LEFT));
            tooltip.remove(JadeIds.CORE_MOD_NAME);
        }
    }
    @Override
    public int getDefaultPriority() {
        return TooltipPosition.TAIL;
    }
    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        FeederTile breeder = (FeederTile) accessor.getBlockEntity();
        if (breeder instanceof FeederTile furnace) {
            data.putBoolean("roundrobin", breeder.isRoundRobinEnabled());
            data.putInt("sendmode", breeder.getStackSendMode().id());
            data.putInt("range", breeder.getFeedRange());

        }
    }

    @Override
    public ResourceLocation getUid() {
        return JadePlugin.FEEDER;
    }
}
