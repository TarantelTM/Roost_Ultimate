package net.tarantel.chickenroost.util;


import cech12.bucketlib.api.BucketLibTags;
import cech12.bucketlib.api.item.UniversalBucketItem;
import cech12.bucketlib.util.BucketLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.ItemHandlerHelper;

public class CustomWorldInteractionUtil {
    /**
     * A utility class for handling custom world interactions related to specific game mechanics.
     * This class provides methods for interacting with entities, cauldrons, and managing
     * bucket interactions. It is designed to facilitate various world interaction tasks
     * programmatically.
     *
     * This class cannot be instantiated.
     */
    private CustomWorldInteractionUtil() {
    }

    /**
     * Attempts to milk a living entity if it is milkable. If successful, the player's hand will be updated
     * with a filled milk bucket. The method ensures to preserve the player's instabuild ability state.
     *
     * @param itemStack The current item stack held by the player.
     * @param entity The living entity to interact with for milking.
     * @param player The player attempting to milk the entity.
     * @param interactionHand The hand the player is using for the interaction.
     * @return An {@link InteractionResult} indicating the outcome of the interaction.
     */
    public static InteractionResult tryMilkLivingEntity(ItemStack itemStack, LivingEntity entity, Player player, InteractionHand interactionHand) {
        if (!entity.getType().is(BucketLibTags.EntityTypes.MILKABLE)) {
            return InteractionResult.PASS;
        } else {
            player.setItemInHand(interactionHand, new ItemStack(Items.BUCKET));
            boolean previousInstabuildValue = player.getAbilities().instabuild;
            player.getAbilities().instabuild = false;
            InteractionResult result = player.interactOn(entity, interactionHand);
            player.getAbilities().instabuild = previousInstabuildValue;
            if (result.consumesAction()) {
                itemStack = ItemUtils.createFilledResult(itemStack.copy(), player, BucketLibUtil.addMilk(ItemHandlerHelper.copyStackWithSize(itemStack, 1)));
            }

            player.setItemInHand(interactionHand, itemStack);
            return result;
        }
    }

    /**
     * Attempts to pick up the contents of a cauldron with an appropriate container item.
     * If the operation is performed on the client-side, it returns an immediate pass result.
     * On the server-side, it checks if the cauldron's contents are compatible with the player's item
     * and performs the interaction if possible.
     *
     * @param level          The level representing the world where the interaction occurs.
     * @param player         The player attempting to perform the interaction.
     * @param interactionHand The hand used by the player for the interaction.
     * @param blockHitResult The result of the player's ray trace targeting the cauldron.
     * @return An InteractionResultHolder containing the interaction result and an ItemStack.
     */
    public static InteractionResultHolder<ItemStack> tryPickupFromCauldron(Level level, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (level.isClientSide()) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos hitBlockPos = blockHitResult.getBlockPos();
            BlockState hitBlockState = level.getBlockState(hitBlockPos);
            Block hitBlock = hitBlockState.getBlock();
            if (hitBlock instanceof AbstractCauldronBlock) {
                AbstractCauldronBlock cauldronBlock = (AbstractCauldronBlock)hitBlock;
                Item var10 = itemstack.getItem();

                if (var10 instanceof UniversalBucketItem) {
                    UniversalBucketItem bucketItem = (UniversalBucketItem)var10;
                    if (BucketLibUtil.isEmpty(itemstack) && (cauldronBlock == Blocks.LAVA_CAULDRON && bucketItem.canHoldFluid(Fluids.LAVA) || cauldronBlock == Blocks.WATER_CAULDRON && bucketItem.canHoldFluid(Fluids.WATER) || cauldronBlock == Blocks.POWDER_SNOW_CAULDRON && bucketItem.canHoldBlock(Blocks.POWDER_SNOW))) {
                        ItemStack stack = new ItemStack(Items.BUCKET);
                        player.setItemInHand(interactionHand, stack);
                        boolean previousInstabuildValue = player.getAbilities().instabuild;
                        player.getAbilities().instabuild = false;
                        InteractionResult interactionResult = hitBlockState.use(level, player, interactionHand, blockHitResult);
                        player.getAbilities().instabuild = previousInstabuildValue;
                        ItemStack resultItemStack = player.getItemInHand(interactionHand);
                        player.setItemInHand(interactionHand, itemstack);
                        if (interactionResult.consumesAction()) {
                            if (resultItemStack.getItem() == Items.POWDER_SNOW_BUCKET) {
                                return new InteractionResultHolder(interactionResult, ItemUtils.createFilledResult(itemstack, player, BucketLibUtil.addBlock(ItemHandlerHelper.copyStackWithSize(itemstack, 1), Blocks.POWDER_SNOW)));
                            }
                            FluidStack resultFluidStack = (FluidStack) FluidUtil.getFluidContained(resultItemStack).orElse(FluidStack.EMPTY);
                            return new InteractionResultHolder(interactionResult, ItemUtils.createFilledResult(itemstack, player, BucketLibUtil.addFluid(ItemHandlerHelper.copyStackWithSize(itemstack, 1), resultFluidStack.getFluid())));
                        }
                    }
                }
            }

            return InteractionResultHolder.pass(itemstack);
        }
    }
    /**
     * Retrieves the ItemStack currently held by the player in the specified hand.
     *
     * @param player The player whose held item is being retrieved.
     * @param hand The hand (main hand or offhand) from which the held item is to be retrieved.
     * @return The ItemStack held by the player in the specified hand.
     */
    public static ItemStack thisbucket(Player player, InteractionHand hand){
        return player.getItemInHand(hand);
    }
    /**
     * Retrieves the bucket item held by the player using the specified interaction hand.
     *
     * @param player The player from whom the bucket item is to be retrieved.
     * @param hand The interaction hand used to hold the bucket item (e.g., main hand or off hand).
     * @return The bucket item held by the player in the specified interaction hand.
     */
    public static Item getBucket(Player player, InteractionHand hand) {
        return thisbucket(player, hand).getItem();
    }
    /**
     * Attempts to place the contents of a player's held item into a cauldron within the game world.
     * Handles interactions with cauldrons and updates the player's inventory accordingly.
     *
     * @param level The level or world in which the interaction is taking place.
     * @param player The player performing the interaction.
     * @param interactionHand The hand the player is using to interact (main or off-hand).
     * @param blockHitResult The result of the raytracing that determines the block the player is interacting with.
     * @return An {@code InteractionResultHolder} containing the result of the interaction and an updated {@code ItemStack}.
     */
    public static InteractionResultHolder<ItemStack> tryPlaceIntoCauldron(Level level, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        //System.out.println("tryPlaceIntoCauldron 1");
        ItemStack itemstack = player.getItemInHand(interactionHand);
        BlockPos hitBlockPos = blockHitResult.getBlockPos();
        BlockState hitBlockState = level.getBlockState(hitBlockPos);
        Block hitBlock = hitBlockState.getBlock();
        if (hitBlock instanceof AbstractCauldronBlock cauldronBlock) {
           // System.out.println("tryPlaceIntoCauldron 2");
            if (itemstack.getItem() instanceof UniversalBucketItem && cauldronBlock == Blocks.CAULDRON && !BucketLibUtil.containsEntityType(itemstack)) {
                //System.out.println("tryPlaceIntoCauldron 3");
                Fluid bucketFluid = BucketLibUtil.getFluid(itemstack);
                Block bucketBlock = BucketLibUtil.getBlock(itemstack);
                ServerLevel serverLevel = level instanceof ServerLevel ? (ServerLevel)level : null;
                ItemStack stack;
                boolean previousInstabuildValue;
                InteractionResult interactionResult;
                if (bucketFluid != Fluids.LAVA && bucketFluid != Fluids.WATER) {
                   // System.out.println("tryPlaceIntoCauldron 4");
                    if (bucketBlock == Blocks.POWDER_SNOW) {
                        //System.out.println("tryPlaceIntoCauldron 5");
                        stack = new ItemStack(Items.POWDER_SNOW_BUCKET);
                        //player.setItemInHand(interactionHand, stack);
                        previousInstabuildValue = player.getAbilities().instabuild;
                        player.getAbilities().instabuild = false;
                        interactionResult = hitBlockState.use(level, player, interactionHand, blockHitResult);
                        player.getAbilities().instabuild = previousInstabuildValue;
                        //player.setItemInHand(interactionHand, itemstack);
                        if (interactionResult.consumesAction()) {
                           // System.out.println("tryPlaceIntoCauldron 6");
                            itemstack.shrink(1);
                            return new InteractionResultHolder(interactionResult, BucketLibUtil.createEmptyResult(itemstack, player, itemstack, interactionHand, true));
                        }
                    }
                } else {
                   // System.out.println("tryPlaceIntoCauldron 7");
                    //stack = new ItemStack(getBucket(player, interactionHand));
                    stack = new ItemStack(bucketFluid.getBucket());
                    //player.setItemInHand(interactionHand, stack);
                    previousInstabuildValue = player.getAbilities().instabuild;
                    player.getAbilities().instabuild = false;
                    interactionResult = hitBlockState.use( level, player, interactionHand, blockHitResult);
                    player.getAbilities().instabuild = previousInstabuildValue;
                    //player.setItemInHand(interactionHand, itemstack);
                    if (interactionResult.consumesAction()) {
                        //System.out.println("tryPlaceIntoCauldron 8");
                        itemstack.shrink(1);
                       // System.out.println("want to shrink 2");
                        return new InteractionResultHolder(interactionResult, BucketLibUtil.createEmptyResult(itemstack, player, BucketLibUtil.removeFluid(itemstack), interactionHand, false));
                    }
                }
            }
        }
        //System.out.println("tryPlaceIntoCauldron 9");
        return InteractionResultHolder.pass(itemstack);
    }
}
