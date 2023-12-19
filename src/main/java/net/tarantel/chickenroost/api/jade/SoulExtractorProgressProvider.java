package net.tarantel.chickenroost.api.jade;

import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.tarantel.chickenroost.block.tile.Soul_Extractor_Tile;
import org.jetbrains.annotations.NotNull;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.view.ClientViewGroup;
import snownee.jade.api.view.IClientExtensionProvider;
import snownee.jade.api.view.IServerExtensionProvider;
import snownee.jade.api.view.ProgressView;
import snownee.jade.api.view.ViewGroup;

public enum SoulExtractorProgressProvider implements IServerExtensionProvider<Soul_Extractor_Tile, CompoundTag>,
        IClientExtensionProvider<CompoundTag, ProgressView>, IServerDataProvider<BlockAccessor>, IBlockComponentProvider {

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
    public List<ViewGroup<CompoundTag>> getGroups(Accessor<?> accessor, Soul_Extractor_Tile target) {
        Level world = accessor.getLevel();
        float period = (float) target.getScaledProgress();
        var progress1 = ProgressView.create(period / 200);
        period = 200;
        var group = new ViewGroup<>(List.of(progress1));
        return List.of(group);
    }

    @Override
    public ResourceLocation getUid() {
        return JadePlugin.SOULEXTRACTOR_PROGRESS;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, @NotNull BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("soul_extractor.progress")) {
            IElement icon = IElementHelper.get().item(new ItemStack(Items.CLOCK), 0.5f).size(new Vec2(10, 10)).translate(new Vec2(0, -1));
            icon.message(null);
            tooltip.add(icon);
            tooltip.append(Component.translatable("myfsafamod.fuel", accessor.getServerData().getInt("soul_extractor.progress")));
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
        Soul_Extractor_Tile furnace = (Soul_Extractor_Tile) accessor.getBlockEntity();
        data.putInt("soul_extractor.progress", furnace.progress);
    }
}