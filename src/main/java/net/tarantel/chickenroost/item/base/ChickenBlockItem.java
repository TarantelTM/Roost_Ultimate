package net.tarantel.chickenroost.item.base;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.*;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class ChickenBlockItem extends BlockItem {
    /** @deprecated */
    @Deprecated
    private final Block block;

    public ChickenBlockItem(Block block, Properties properties, int currentmaxxp) {
        super(block, properties);
        this.block = block;
    }

    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        InteractionResult interactionresult = this.place(new BlockPlaceContext(context));
        if (!interactionresult.consumesAction() && context.getItemInHand().isEdible()) {
            InteractionResult interactionresult1 = super.use(context.getLevel(), Objects.requireNonNull(context.getPlayer()), context.getHand()).getResult();
            return interactionresult1 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : interactionresult1;
        } else {
            return interactionresult;
        }
    }

    public InteractionResult place(BlockPlaceContext context) {
        if (!this.getBlock().isEnabled(context.getLevel().enabledFeatures())) {
            return InteractionResult.FAIL;
        } else if (!context.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext blockplacecontext = this.updatePlacementContext(context);
            if (blockplacecontext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState blockstate = this.getPlacementState(blockplacecontext);
                if (blockstate == null) {
                    return InteractionResult.FAIL;
                } else if (!this.placeBlock(blockplacecontext, blockstate)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos blockpos = blockplacecontext.getClickedPos();
                    Level level = blockplacecontext.getLevel();
                    Player player = blockplacecontext.getPlayer();
                    ItemStack itemstack = blockplacecontext.getItemInHand();
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    if (blockstate1.is(blockstate.getBlock())) {
                        blockstate1 = this.updateBlockStateFromTag(blockpos, level, itemstack, blockstate1);
                        this.updateCustomBlockEntityTag(blockpos, level, player, itemstack, blockstate1);
                        updateBlockEntityComponents(level, blockpos, itemstack);
                        blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemstack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                        }
                    }

                    SoundType soundtype = blockstate1.getSoundType(level, blockpos, context.getPlayer());
                    level.playSound(player, blockpos, this.getPlaceSound(blockstate1, level, blockpos, context.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    level.gameEvent(GameEvent.BLOCK_PLACE, blockpos, Context.of(player, blockstate1));
                    itemstack.shrink(1);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
    }

    /** @deprecated */
    @Deprecated
    protected SoundEvent getPlaceSound(BlockState state) {
        return state.getSoundType().getPlaceSound();
    }

    protected SoundEvent getPlaceSound(BlockState p_state, Level world, BlockPos pos, Player entity) {
        return p_state.getSoundType(world, pos, entity).getPlaceSound();
    }

    @Nullable
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        return context;
    }

    private static void updateBlockEntityComponents(Level level, BlockPos poa, ItemStack stack) {
        BlockEntity blockentity = level.getBlockEntity(poa);
        if (blockentity != null) {
            blockentity.saveToItem(stack);
            blockentity.setChanged();
        }

    }

    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        return updateCustomBlockEntityTag(level, player, pos, stack);
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockstate = this.getBlock().getStateForPlacement(context);
        return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
    }

    private BlockState updateBlockStateFromTag(BlockPos pos, Level level, ItemStack stack, BlockState state) {
        // Check if the ItemStack has NBT data
        if (!stack.hasTag()) {
            return state;
        }

        // Get the NBT data from the ItemStack
        CompoundTag nbt = stack.getTag();
        if (nbt == null || !nbt.contains("BlockStateTag")) {
            return state;
        }

        // Get the BlockState properties from the NBT data
        CompoundTag blockStateTag = nbt.getCompound("BlockStateTag");
        BlockState blockstate = this.getBlock().defaultBlockState();

        // Update the block state if it has changed
        if (blockstate != state) {
            level.setBlock(pos, blockstate, 2);
        }

        return blockstate;
    }

    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        Player player = context.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return (!this.mustSurvive() || state.canSurvive(context.getLevel(), context.getClickedPos())) && context.getLevel().isUnobstructed(state, context.getClickedPos(), collisioncontext);
    }

    protected boolean mustSurvive() {
        return true;
    }

    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        return context.getLevel().setBlock(context.getClickedPos(), state, 11);
    }

    public static boolean updateCustomBlockEntityTag(Level level, @Nullable Player player, BlockPos pos, ItemStack stack) {
        MinecraftServer minecraftserver = level.getServer();
        if (minecraftserver == null) {
            return false;
        } else {
            CompoundTag customdata = stack.getTagElement("BlockEntityTag");
            if (customdata != null && !customdata.isEmpty()) {
                BlockEntity blockentity = level.getBlockEntity(pos);
                if (blockentity != null) {
                    if (!level.isClientSide && blockentity.onlyOpCanSetNbt() && (player == null || !player.canUseGameMasterBlocks())) {
                        return false;
                    }

                    blockentity.load(customdata);
                    return true;
                }
            }

            return false;
        }
    }

    public String getDescriptionId() {
        return this.getBlock().getDescriptionId();
    }

    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        this.getBlock().appendHoverText(itemstack, world, list, flag);
    }

    public Block getBlock() {
        return this.block;
    }

    public void registerBlocks(Map<Block, Item> blockToItemMap, Item item) {
        blockToItemMap.put(this.getBlock(), item);
    }

    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
        blockToItemMap.remove(this.getBlock());
    }

    public boolean canFitInsideContainerItems() {
        return !(this.getBlock() instanceof ShulkerBoxBlock);
    }

    public void onDestroyed(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        CompoundTag containerTag = stack.getTagElement("Items"); // Assuming "Items" is the key for container contents
        if (containerTag != null && !containerTag.isEmpty()) {
            NonNullList<ItemStack> items = NonNullList.withSize(containerTag.getInt("Size"), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(containerTag, items); // Extract items from the tag
            ItemUtils.onContainerDestroyed(itemEntity, (Stream<ItemStack>) items); // Pass extracted items to the method
        }
    }

    public static void setBlockEntityData(ItemStack stack, BlockEntityType<?> blockEntityType, CompoundTag blockEntityData) {
        blockEntityData.remove("id"); // Remove the "id" tag as it's not needed
        if (blockEntityData.isEmpty()) {
            stack.removeTagKey("BlockEntityTag"); // Remove the BlockEntityTag if the data is empty
        } else {
            blockEntityData.putString("id", ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(blockEntityType).toString()); // Add the block entity type ID
            stack.getOrCreateTag().put("BlockEntityTag", blockEntityData); // Set the BlockEntityTag
        }
    }

    public FeatureFlagSet requiredFeatures() {
        return this.getBlock().requiredFeatures();
    }
}
