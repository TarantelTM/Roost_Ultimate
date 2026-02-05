package net.tarantel.chickenroost.block.tile.render;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.Map;

public class TrainerRenderState extends BlockEntityRenderState implements GeoRenderState {
    private final Map<DataTicket<?>, Object> geckolibData = new Reference2ObjectOpenHashMap<>();

    @Override
    public Map<DataTicket<?>, Object> getDataMap() {
        return geckolibData;
    }
}