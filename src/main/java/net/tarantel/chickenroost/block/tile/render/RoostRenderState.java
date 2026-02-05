package net.tarantel.chickenroost.block.tile.render;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.item.ItemStack;

public class RoostRenderState extends BlockEntityRenderState {

    /** The item to render on the roost */
    public ItemStack renderStack = ItemStack.EMPTY;
}
