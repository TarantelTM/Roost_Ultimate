package net.tarantel.chickenroost.block.tile.render;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.model.BakedGeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import net.tarantel.chickenroost.block.tile.TrainerTile;
import net.tarantel.chickenroost.block.blocks.model.AnimatedTrainerModel;
import software.bernie.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer;

import java.util.List;

public class AnimatedTrainerRenderer<R extends BlockEntityRenderState & GeoRenderState>
        extends GeoBlockRenderer<TrainerTile, R> {

    public AnimatedTrainerRenderer() {
        super(new AnimatedTrainerModel());

        this.withRenderLayer(renderer ->
                new TrainerItemLayer<>(renderer)
        );
    }

    @Override
    public void extractRenderState(
            TrainerTile tile,
            R renderState,
            float partialTick,
            Vec3 cameraPos,
            ModelFeatureRenderer.CrumblingOverlay damageOverlay
    ) {
        // 🔹 Vanilla + GeckoLib interner State
        super.extractRenderState(tile, renderState, partialTick, cameraPos, damageOverlay);

        // 🔥 CLIENTSEITIG Animation anwenden
        if (tile.getLevel() != null && tile.getLevel().isClientSide()) {
            tile.applyClientAnimation();
        }
    }


}