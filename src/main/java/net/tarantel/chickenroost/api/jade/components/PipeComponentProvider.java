//package net.tarantel.chickenroost.api.jade.components;
//
//import net.minecraft.ChatFormatting;
//import net.minecraft.core.NonNullList;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.nbt.ListTag;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.Identifier;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.tarantel.chickenroost.api.jade.JadePlugin;
//import net.tarantel.chickenroost.pipe.PipeBlockEntity;
//import snownee.jade.api.*;
//import snownee.jade.api.config.IPluginConfig;
//import snownee.jade.api.ui.IElement;
//import snownee.jade.api.ui.IElementHelper;
//
//public enum PipeComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
//    INSTANCE;
//
//    private PipeComponentProvider(){
//
//    }
//
//    @Override
//    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
//        CompoundTag data = accessor.getServerData();
//        BlockEntity blockEntity = accessor.getBlockEntity();
//        if (blockEntity instanceof PipeBlockEntity breederBlockEntity) {
//            ListTag furnaceItems = data.getList("furnace", 10);
//            NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
//            for(int i = 0; i < furnaceItems.size(); ++i) {
//                inventory.set(i, ItemStack.parseOptional(accessor.getLevel().registryAccess(), furnaceItems.getCompound(i)));
//            }
//            PipeBlockEntity.PipeMode pipeMode = PipeBlockEntity.PipeMode.byId(data.getInt("sendmode"));
//            IElementHelper helper2 = IElementHelper.get();
//            tooltip.add(
//                    helper2.text(
//                            Component.translatable("roost_chicken.interface.pipemode.name.jade")
//                                    .withStyle(ChatFormatting.WHITE)
//                                    .append(
//                                            Component.translatable(
//                                                    "roost_chicken.interface.pipemode." + pipeMode.name().toLowerCase()
//                                            ).withStyle(switch (pipeMode) {
//                                                case NONE -> ChatFormatting.GRAY;
//                                                case INPUT -> ChatFormatting.YELLOW;
//                                                case OUTPUT -> ChatFormatting.GREEN;
//                                            })
//                                    )
//                    ).align(IElement.Align.LEFT)
//            );
//            tooltip.add(helper2.text(Component.literal("\u00A7o" + "\u00A71" + "Roost Ultimate")).align(IElement.Align.LEFT));
//            tooltip.remove(JadeIds.CORE_MOD_NAME);
//        }
//    }
//    @Override
//    public int getDefaultPriority() {
//        return TooltipPosition.TAIL;
//    }
//    @Override
//    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
//        PipeBlockEntity breeder = (PipeBlockEntity) accessor.getBlockEntity();
//        if (breeder instanceof PipeBlockEntity furnace) {
//            data.putInt("sendmode", breeder.getMode().id());
//
//        }
//    }
//
//    @Override
//    public Identifier getUid() {
//        return JadePlugin.PIPE;
//    }
//}
//