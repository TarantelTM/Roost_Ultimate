package net.tarantel.chickenroost.block;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.tarantel.chickenroost.item.ModItems;

public class CropBlock_7 extends CropBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 6);

    public CropBlock_7(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.CHICKEN_FOOD_TIER_7;
    }

    @Override
    public int getMaxAge() {
        return 6;
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}