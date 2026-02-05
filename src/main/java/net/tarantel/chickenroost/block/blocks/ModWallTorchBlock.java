package net.tarantel.chickenroost.block.blocks;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallTorchBlock;

import java.util.function.Supplier;

public class ModWallTorchBlock extends WallTorchBlock {

    private final Supplier<Block> standingTorch;

    public ModWallTorchBlock(Properties props, ParticleOptions particle, Supplier<Block> standingTorch) {
        super(props, particle);
        this.standingTorch = standingTorch;
    }

    @Override
    public String getDescriptionId() {
        return this.standingTorch.get().getDescriptionId();
    }
}