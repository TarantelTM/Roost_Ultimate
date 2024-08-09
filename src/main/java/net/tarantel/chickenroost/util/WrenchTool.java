package net.tarantel.chickenroost.util;

import net.minecraft.core.BlockPos;
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
        if ((world.getBlockState(new BlockPos(x, y, z))).is(BlockTags.create(ChickenRoostMod.ownresource("blocks/all")))) {
            oldblock = (world.getBlockState(new BlockPos(x, y, z)));
            world.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), 3);
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, (new ItemStack(oldblock.getBlock())));
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(entityToSpawn);
            }
        }
    }
}

