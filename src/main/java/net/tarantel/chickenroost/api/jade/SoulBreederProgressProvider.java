package net.tarantel.chickenroost.api.jade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;
import net.tarantel.chickenroost.block.tile.Soul_Breeder_Tile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.view.*;

import java.util.List;

public enum SoulBreederProgressProvider implements IServerExtensionProvider<Soul_Breeder_Tile, CompoundTag>,
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



    @Override
    public @Nullable List<ViewGroup<CompoundTag>> getGroups(ServerPlayer serverPlayer, ServerLevel serverLevel, Soul_Breeder_Tile tile, boolean b) {
        //Level world = accessor.getLevel();
        float period = (float) tile.getScaledProgress();
        var progress1 = ProgressView.create(period / 200);
        period = 200;
        var group = new ViewGroup<>(List.of(progress1));
        return List.of(group);
    }

    @Override
    public ResourceLocation getUid() {
        return JadePlugin.SOULBREEDER_PROGRESS;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, @NotNull BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("soul_breeder.progress")) {
            IElement icon = IElementHelper.get().item(new ItemStack(Items.CLOCK), 0.5f).size(new Vec2(10, 10)).translate(new Vec2(0, -1));
            icon.message(null);
            tooltip.add(icon);
            tooltip.append(Component.translatable("soulbreeder.fuel", accessor.getServerData().getInt("soul_breeder.progress")));
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
        Soul_Breeder_Tile furnace = (Soul_Breeder_Tile) accessor.getBlockEntity();
        data.putInt("soul_breeder.progress", furnace.progress);
    }
}