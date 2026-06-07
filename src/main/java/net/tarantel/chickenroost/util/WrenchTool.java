package net.tarantel.chickenroost.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class WrenchTool {
   public static void execute(LevelAccessor world, int x, int y, int z) {
      if (world.getBlockState(new BlockPos(x, y, z)).is(BlockTags.create(ResourceLocation.parse("c:all")))) {
         world.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), 3);
      }
   }
}
