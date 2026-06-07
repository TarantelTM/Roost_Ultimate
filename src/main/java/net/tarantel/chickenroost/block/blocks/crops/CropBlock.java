package net.tarantel.chickenroost.block.blocks.crops;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.tarantel.chickenroost.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class CropBlock extends net.minecraft.world.level.block.CropBlock {
   public static final int MAX_AGE = 7;
   public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
   private final int tier;

   public CropBlock(Properties pProperties, int tier) {
      super(pProperties);
      this.tier = tier;
   }

   @NotNull
   protected ItemLike getBaseSeedId() {
      return switch (this.tier) {
         case 1 -> (Item)ModItems.CHICKEN_FOOD_TIER_2.get();
         case 2 -> (Item)ModItems.CHICKEN_FOOD_TIER_3.get();
         case 3 -> (Item)ModItems.CHICKEN_FOOD_TIER_4.get();
         case 4 -> (Item)ModItems.CHICKEN_FOOD_TIER_5.get();
         case 5 -> (Item)ModItems.CHICKEN_FOOD_TIER_6.get();
         case 6 -> (Item)ModItems.CHICKEN_FOOD_TIER_7.get();
         case 7 -> (Item)ModItems.CHICKEN_FOOD_TIER_8.get();
         case 8 -> (Item)ModItems.CHICKEN_FOOD_TIER_9.get();
         default -> (Item)ModItems.CHICKEN_FOOD_TIER_1.get();
      };
   }

   @NotNull
   public IntegerProperty getAgeProperty() {
      return AGE;
   }

   public int getMaxAge() {
      return 7;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
      pBuilder.add(new Property[]{AGE});
   }
}
