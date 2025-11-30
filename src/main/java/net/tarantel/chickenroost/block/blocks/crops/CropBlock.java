package net.tarantel.chickenroost.block.blocks.crops;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
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

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return switch (tier) {
            case 1 -> ModItems.CHICKEN_FOOD_TIER_2.get();
            case 2 -> ModItems.CHICKEN_FOOD_TIER_3.get();
            case 3 -> ModItems.CHICKEN_FOOD_TIER_4.get();
            case 4 -> ModItems.CHICKEN_FOOD_TIER_5.get();
            case 5 -> ModItems.CHICKEN_FOOD_TIER_6.get();
            case 6 -> ModItems.CHICKEN_FOOD_TIER_7.get();
            case 7 -> ModItems.CHICKEN_FOOD_TIER_8.get();
            case 8 -> ModItems.CHICKEN_FOOD_TIER_9.get();
            default -> ModItems.CHICKEN_FOOD_TIER_1.get();
        };
    }

    @Override
    public @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

}