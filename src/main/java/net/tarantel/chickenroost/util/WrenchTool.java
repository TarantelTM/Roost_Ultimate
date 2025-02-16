package net.tarantel.chickenroost.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.tarantel.chickenroost.ChickenRoostMod;

public class WrenchTool {
    public static void execute(LevelAccessor world, int x, int y, int z) {
        BlockState oldblock = Blocks.AIR.defaultBlockState();
        if ((world.getBlockState(new BlockPos(x, y, z))).is(BlockTags.create(new ResourceLocation(ChickenRoostMod.MODID,"blocks/all")))) {
            oldblock = (world.getBlockState(new BlockPos(x, y, z)));
            world.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), 3);
        }
    }
}

