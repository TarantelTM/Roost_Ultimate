package net.tarantel.chickenroost.api.jade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;
import net.tarantel.chickenroost.block.tile.Roost_Tile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.view.*;

import java.util.List;

public enum RoostComponentProvider implements IServerExtensionProvider<Roost_Tile, CompoundTag>,
        IClientExtensionProvider<CompoundTag, ProgressView>, IServerDataProvider<BlockAccessor> , IBlockComponentProvider {

    INSTANCE;

    @Override
    public List<ClientViewGroup<ProgressView>> getClientGroups(Accessor<?> accessor, List<ViewGroup<CompoundTag>> groups) {
        return ClientViewGroup.map(groups, ProgressView::read, (group, clientGroup) -> {
            var view = clientGroup.views.get(0);
            view.style.color(0xFF008000);
            view.text = Component.literal("Progress");
        });
    }


    private int otherprogress;
    private int othermaxprogress;



    @Override
    public @Nullable List<ViewGroup<CompoundTag>> getGroups(ServerPlayer serverPlayer, ServerLevel serverLevel, Roost_Tile tile, boolean b) {
        //Level world = accessor.getLevel();
        float period = (float) tile.getScaledProgress();
        var progress1 = ProgressView.create(period / 200);
        period = 200;
        var group = new ViewGroup<>(List.of(progress1));
        return List.of(group);
    }

    @Override
    public ResourceLocation getUid() {
        return JadePlugin.ROOST_PROGRESS;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, @NotNull BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("roost.progress")) {
            IElement icon = IElementHelper.get().item(new ItemStack(Items.CLOCK), 0.5f).size(new Vec2(10, 10)).translate(new Vec2(0, -1));
            icon.message(null);
            tooltip.add(icon);
            tooltip.append(Component.translatable("roost.fuel", accessor.getServerData().getInt("roost.progress")));
        }

        Component test1 = Component.literal("1");
        Component test2 = Component.literal("2");
        Component test3 = Component.literal("3");
        tooltip.add(IElementHelper.get().text(test2).align(IElement.Align.RIGHT));
        tooltip.add(IElementHelper.get().text(test1).align(IElement.Align.LEFT));
        tooltip.append(IElementHelper.get().text(test1).align(IElement.Align.RIGHT));
        tooltip.append(IElementHelper.get().text(test2).align(IElement.Align.RIGHT));
        tooltip.append(IElementHelper.get().text(test3).align(IElement.Align.RIGHT));
        tooltip.append(IElementHelper.get().text(test2).align(IElement.Align.LEFT));
        tooltip.append(IElementHelper.get().text(test3).align(IElement.Align.LEFT));
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        Roost_Tile furnace = (Roost_Tile) accessor.getBlockEntity();
        data.putInt("roost.progress", furnace.progress);
    }
}
