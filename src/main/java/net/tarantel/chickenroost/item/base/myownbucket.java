package net.tarantel.chickenroost.item.base;


import cech12.bucketlib.api.item.UniversalBucketItem;
import cech12.bucketlib.util.BucketLibUtil;
import cech12.bucketlib.util.RegistryUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.util.CustomWorldInteractionUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Iterator;

public class myownbucket extends UniversalBucketItem {


    public myownbucket(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return  64;
    }

    /*@Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        if(stack.getItem() == ModItems.WATER_EGG.get()){
            stack.getOrCreateTag().put
        }
    }*/
    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand interactionHand) {
        //System.out.println("1");
        ItemStack itemstack = player.getItemInHand(interactionHand);
        boolean isEmpty = BucketLibUtil.isEmpty(itemstack);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, isEmpty ? net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY : net.minecraft.world.level.ClipContext.Fluid.NONE);
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            //System.out.println("2");
            BlockPos hitBlockPos = blockHitResult.getBlockPos();
            BlockState hitBlockState = level.getBlockState(hitBlockPos);
            Direction hitDirection = blockHitResult.getDirection();
            BlockPos relativeBlockPos = hitBlockPos.relative(hitDirection);
            ServerLevel serverLevel = level instanceof ServerLevel ? (ServerLevel)level : null;
            InteractionResultHolder caldronInteractionResult;
            RegistryUtil.BucketBlock bucketBlock;
            ItemStack fakeStack;
            if (isEmpty) {
                //System.out.println("3");
                caldronInteractionResult = CustomWorldInteractionUtil.tryPickupFromCauldron(level, player, interactionHand, blockHitResult);
                if (caldronInteractionResult.getResult().consumesAction()) {
                    //System.out.println("4");
                    return caldronInteractionResult;
                }

                FluidActionResult fluidActionResult = FluidUtil.tryPickUpFluid(itemstack, player, level, hitBlockPos, hitDirection);
                if (fluidActionResult.isSuccess()) {
                    itemstack.shrink(1);
                    //System.out.println("5");
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }

                bucketBlock = RegistryUtil.getBucketBlock(hitBlockState.getBlock());
                if (bucketBlock != null && this.canHoldBlock(bucketBlock.block())) {
                    //System.out.println("6");
                    fakeStack = new ItemStack(Items.BUCKET);
                    player.getItemInHand(interactionHand).shrink(1);
                    InteractionResultHolder<ItemStack> interactionResult = fakeStack.use(level, player, interactionHand);
                    player.getItemInHand(interactionHand).shrink(1);

                    if (interactionResult.getResult().consumesAction()) {
                        itemstack.shrink(1);
                        //System.out.println("7");
                        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                    }
                }
            } else {
                //System.out.println("8");
                caldronInteractionResult = CustomWorldInteractionUtil.tryPlaceIntoCauldron(level, player, interactionHand, blockHitResult);
                if (caldronInteractionResult.getResult().consumesAction()) {
                    //System.out.println("9");
                    return caldronInteractionResult;
                }

                if (BucketLibUtil.containsFluid(itemstack)) {
                    FluidStack fluidStack = (FluidStack)FluidUtil.getFluidHandler(itemstack).map((fluidHandler) -> fluidHandler.getFluidInTank(0)).orElse(FluidStack.EMPTY);

                    Iterator var17 = Arrays.asList(hitBlockPos, relativeBlockPos).iterator();
                    //System.out.println("10");
                    while(var17.hasNext()) {
                        BlockPos pos = (BlockPos)var17.next();
                        FluidActionResult fluidActionResult = FluidUtil.tryPlaceFluid(player, level, interactionHand, pos, BucketLibUtil.removeEntityType(itemstack, false), fluidStack);
                        if (fluidActionResult.isSuccess()) {
                            //System.out.println("11");
                            if (BucketLibUtil.containsEntityType(itemstack)) {
                                //System.out.println("12");
                                this.spawnEntityFromBucket(player, level, itemstack, pos, false);
                            }

                            itemstack.shrink(1);
                            //System.out.println("13");
                            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                        }
                    }
                } else {
                    //System.out.println("14");
                    if (BucketLibUtil.containsEntityType(itemstack)) {
                        //System.out.println("15");
                        ItemStack emptyBucket = this.spawnEntityFromBucket(player, level, itemstack, relativeBlockPos, true);

                        return InteractionResultHolder.sidedSuccess(ItemStack.EMPTY, level.isClientSide());
                    }

                    if (BucketLibUtil.containsBlock(itemstack)) {
                        //System.out.println("16");
                        Block block = BucketLibUtil.getBlock(itemstack);
                        bucketBlock = RegistryUtil.getBucketBlock(block);
                        if (block != null && bucketBlock != null) {
                            //System.out.println("17");
                            fakeStack = new ItemStack(bucketBlock.bucketItem());
                            //player.getItemInHand(interactionHand).shrink(1);
                            InteractionResult interactionResult = fakeStack.useOn(new UseOnContext(player, interactionHand, blockHitResult));
                            //player.getItemInHand(interactionHand).shrink(1);
                            if (interactionResult.consumesAction()) {
                                //System.out.println("18");
                                return new InteractionResultHolder(interactionResult, ItemStack.EMPTY);
                            }
                        }
                    }
                }
            }
        }
        //System.out.println("19");
        return BucketLibUtil.containsMilk(itemstack) ? ItemUtils.startUsingInstantly(level, player, interactionHand) : InteractionResultHolder.pass(itemstack);
    }





    @Override
    public void inventoryTick(@Nonnull ItemStack itemStack, Level level, Entity entity, int position, boolean selected) {

    }






    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack itemStack, @Nonnull Level level, @Nonnull LivingEntity player) {
        if (!level.isClientSide) {
            player.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
        }

        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, new ItemStack(Items.MILK_BUCKET));
            serverPlayer.awardStat(Stats.ITEM_USED.get(Items.MILK_BUCKET));
        }

        return BucketLibUtil.notCreative(player) ? BucketLibUtil.removeMilk(itemStack) : itemStack;
    }

}
