package net.tarantel.chickenroost.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class WrenchTool {
    public static void execute(WorldAccess world, int x, int y, int z) {
        BlockState oldblock = Blocks.AIR.getDefaultState();
        if ((world.getBlockState(new BlockPos(x, y, z))).isIn(BlockTagManager.Blocks.ROOSTBLOCKS)) {
            oldblock = (world.getBlockState(new BlockPos(x, y, z)));
            world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 3);
            if (world instanceof World _level && !_level.isClient()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, (new ItemStack(oldblock.getBlock())));
                entityToSpawn.setPickupDelay(10);
                _level.spawnEntity(entityToSpawn);
            }
        }
    }
}