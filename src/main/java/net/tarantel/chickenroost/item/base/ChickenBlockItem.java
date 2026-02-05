package net.tarantel.chickenroost.item.base;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;

public class ChickenBlockItem extends BlockItem {

    private final Block block;

    public ChickenBlockItem(Block block, Properties properties, int currentmaxxp) {
        super(block, properties);
        this.block = block;
    }

    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        InteractionResult interactionresult = this.place(new BlockPlaceContext(context));
        if (!interactionresult.consumesAction() && context.getItemInHand().has(DataComponents.FOOD)) {
            InteractionResult interactionresult1 = super.use(context.getLevel(), Objects.requireNonNull(context.getPlayer()), context.getHand()).getResult();
            return interactionresult1 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : interactionresult1;
        } else {
            return interactionresult;
        }
    }

    public @NotNull InteractionResult place(BlockPlaceContext context) {
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
                    level.playSound(player, blockpos, this.getPlaceSound(blockstate1, level, blockpos, Objects.requireNonNull(context.getPlayer())), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    level.gameEvent(GameEvent.BLOCK_PLACE, blockpos, Context.of(player, blockstate1));
                    itemstack.consume(1, player);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
    }

    protected @NotNull SoundEvent getPlaceSound(BlockState state) {
        return state.getSoundType().getPlaceSound();
    }

    protected @NotNull SoundEvent getPlaceSound(BlockState p_state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player entity) {
        return p_state.getSoundType(world, pos, entity).getPlaceSound();
    }

    @Nullable
    public BlockPlaceContext updatePlacementContext(@NotNull BlockPlaceContext context) {
        return context;
    }

    private static void updateBlockEntityComponents(Level level, BlockPos poa, ItemStack stack) {
        BlockEntity blockentity = level.getBlockEntity(poa);
        if (blockentity != null) {
            blockentity.applyComponentsFromItemStack(stack);
            blockentity.setChanged();
        }

    }

    protected boolean updateCustomBlockEntityTag(@NotNull BlockPos pos, @NotNull Level level, @Nullable Player player, @NotNull ItemStack stack, @NotNull BlockState state) {
        return updateCustomBlockEntityTag(level, player, pos, stack);
    }

    @Nullable
    protected BlockState getPlacementState(@NotNull BlockPlaceContext context) {
        BlockState blockstate = this.getBlock().getStateForPlacement(context);
        return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
    }

    private BlockState updateBlockStateFromTag(BlockPos pos, Level level, ItemStack stack, BlockState state) {
        BlockItemStateProperties blockitemstateproperties = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
        if (blockitemstateproperties.isEmpty()) {
            return state;
        } else {
            BlockState blockstate = blockitemstateproperties.apply(state);
            if (blockstate != state) {
                level.setBlock(pos, blockstate, 2);
            }

            return blockstate;
        }
    }

    protected boolean canPlace(BlockPlaceContext context, @NotNull BlockState state) {
        Player player = context.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return (!this.mustSurvive() || state.canSurvive(context.getLevel(), context.getClickedPos())) && context.getLevel().isUnobstructed(state, context.getClickedPos(), collisioncontext);
    }

    public static boolean updateCustomBlockEntityTag(Level level, @Nullable Player player, @NotNull BlockPos pos, @NotNull ItemStack stack) {
        MinecraftServer minecraftserver = level.getServer();
        if (minecraftserver == null) {
            return false;
        } else {
            CustomData customdata = stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
            if (!customdata.isEmpty()) {
                BlockEntity blockentity = level.getBlockEntity(pos);
                if (blockentity != null) {
                    if (!level.isClientSide && blockentity.onlyOpCanSetNbt() && (player == null || !player.canUseGameMasterBlocks())) {
                        return false;
                    }

                    return customdata.loadInto(blockentity, level.registryAccess());
                }
            }

            return false;
        }
    }

    public @NotNull String getDescriptionId() {
        return this.getBlock().getDescriptionId();
    }

    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        this.getBlock().appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public @NotNull Block getBlock() {
        return this.block;
    }

    public void registerBlocks(Map<Block, Item> blockToItemMap, @NotNull Item item) {
        blockToItemMap.put(this.getBlock(), item);
    }

    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, @NotNull Item itemIn) {
        blockToItemMap.remove(this.getBlock());
    }

    public boolean canFitInsideContainerItems() {
        return !(this.getBlock() instanceof ShulkerBoxBlock);
    }

    public @NotNull FeatureFlagSet requiredFeatures() {
        return this.getBlock().requiredFeatures();
    }
}
