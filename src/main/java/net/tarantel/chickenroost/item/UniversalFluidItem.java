package net.tarantel.chickenroost.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;


import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;


public class UniversalFluidItem extends Item {
    private final Fluid fluid;

    private final int amount;

    public UniversalFluidItem(Properties props, Fluid fluid, int amount) {
        super(props);
        this.fluid = fluid;
        this.amount = amount;
    }

    public int getAmount() { return this.amount; }

    public Fluid getFluid() {
        return this.fluid;
    }

    public int getAmountPerItem() {
        return this.amount;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Direction side = context.getClickedFace();
        ItemStack stack = player != null ? player.getItemInHand(context.getHand()) : context.getItemInHand();


        BlockHitResult hit = raycast(level, player);
        if (hit != null) side = hit.getDirection();

        BlockState clickedState = level.getBlockState(clickedPos);


        if (isAnyCauldron(clickedState) && side == Direction.UP) {
            if (handleCauldronFill(level, clickedPos)) {
                consumeOne(stack, player);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }


        BlockPos placePos = clickedState.canBeReplaced() ? clickedPos : clickedPos.relative(side);
        BlockState placeState = level.getBlockState(placePos);


        if (isAnyCauldron(placeState) && side == Direction.UP) {
            if (handleCauldronFill(level, placePos)) {
                consumeOne(stack, player);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }


        boolean requireFull = this.amount >= 1000;
        Direction handlerSideForPlace = placePos.equals(clickedPos) ? side : side.getOpposite();
        if (tryFillAnyHandler(level, clickedPos, side, requireFull)
                || tryFillAnyHandler(level, clickedPos, null, requireFull)
                || tryFillAnyHandler(level, placePos, handlerSideForPlace, requireFull)
                || tryFillAnyHandler(level, placePos, null, requireFull)) {
            consumeOne(stack, player);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }


        if (placeFluidInWorld(level, placePos, this.fluid)) {
            consumeOne(stack, player);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hit = raycast(level, player);
        if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hit.getBlockPos();
            Direction face = hit.getDirection();
            boolean requireFull = this.amount >= 1000;
            if (tryFillAnyHandler(level, pos, face, requireFull) || tryFillAnyHandler(level, pos, null, requireFull)) {
                consumeOne(stack, player);
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
            }
        }
        return InteractionResultHolder.pass(stack);
    }


    private BlockHitResult raycast(Level level, Player player) {
        if (player == null) return null;
        Vec3 eye = player.getEyePosition(1.0F);
        Vec3 look = player.getViewVector(1.0F);
        double reach = player.blockInteractionRange();
        Vec3 reachVec = eye.add(look.scale(reach));
        HitResult hit = level.clip(new ClipContext(
                eye,
                reachVec,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        ));
        return hit instanceof BlockHitResult bhr ? bhr : null;
    }

    private boolean isAnyCauldron(BlockState state) {
        Block b = state.getBlock();
        return b instanceof CauldronBlock || b == Blocks.WATER_CAULDRON || b == Blocks.LAVA_CAULDRON;
    }


    private boolean handleCauldronFill(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        if (this.fluid == Fluids.WATER) {
            int current;
            if (block == Blocks.WATER_CAULDRON) {
                current = state.getValue(LayeredCauldronBlock.LEVEL);
            } else if (block instanceof CauldronBlock) {
                current = 0;
            } else {
                return false;
            }

            int addLevels = this.amount / 333;
            if (addLevels <= 0) return false;
            int newLevels = Math.min(3, current + Math.min(3, addLevels));
            if (newLevels == current) return false;

            BlockState newState = Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, newLevels);
            if (!level.isClientSide) {
                level.setBlock(pos, newState, Block.UPDATE_ALL);
                level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return true;
        }

        if (this.fluid == Fluids.LAVA) {
            if (this.amount < 1000) return false;
            if (block instanceof CauldronBlock && block != Blocks.WATER_CAULDRON && block != Blocks.LAVA_CAULDRON) {
                if (!level.isClientSide) {
                    level.setBlock(pos, Blocks.LAVA_CAULDRON.defaultBlockState(), Block.UPDATE_ALL);
                    level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
                    level.playSound(null, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return true;
            }
        }
        return false;
    }

    private void consumeOne(ItemStack stack, Player player) {
        if (player == null) return;
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }


    public boolean tryFillAnyHandler(Level level, BlockPos pos, Direction side, boolean requireFull) {
        if (tryFill(level, pos, side, requireFull, true)) return true;
        return tryFill(level, pos, side, requireFull, false);
    }

    private boolean tryFill(Level level, BlockPos pos, Direction side, boolean requireFull, boolean blockEntityCap) {
        IFluidHandler handler = level.getCapability(
                Capabilities.FluidHandler.BLOCK,
                pos,
                side
        );
        if (handler == null) return false;

        FluidStack toInsert = new FluidStack(this.fluid, this.amount);
        int canFill = handler.fill(toInsert, IFluidHandler.FluidAction.SIMULATE);
        if (requireFull && canFill < this.amount) return false;
        if (canFill <= 0) return false;

        int filled = handler.fill(toInsert, IFluidHandler.FluidAction.EXECUTE);
        return requireFull ? (filled == this.amount) : (filled > 0);
    }

    private boolean placeFluidInWorld(Level level, BlockPos pos, Fluid fluid) {
        BlockState state = level.getBlockState(pos);
        if (!state.canBeReplaced() && !state.isAir()) {
            return false;
        }

        BlockState fluidState;
        if (fluid == Fluids.WATER) {
            fluidState = Blocks.WATER.defaultBlockState();
        } else if (fluid == Fluids.LAVA) {
            fluidState = Blocks.LAVA.defaultBlockState();
        } else {
            return false;
        }

        if (!level.isClientSide) {
            level.setBlock(pos, fluidState, Block.UPDATE_ALL);
            level.gameEvent(null, GameEvent.BLOCK_PLACE, pos);
            if (fluid == Fluids.WATER) {
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
        return true;
    }
}