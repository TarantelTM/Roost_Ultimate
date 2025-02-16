package net.tarantel.chickenroost.item.base;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.util.Config;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ModItemNameBlockItem_5 extends ChickenSeedBase {
    public static int currentmaxxpp;
    private final Block block;
    public ModItemNameBlockItem_5(Block p_41579_, Item.Properties p_41580_, int currentmaxxp) {
        super(p_41579_, p_41580_, currentmaxxp);
        currentmaxxpp = currentmaxxp;
        this.block = p_41579_;
    }


    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {

        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.nullToEmpty((("\u00A7a") + "XP: " + "\u00A79" + (((int) Config.food_xp_tier_5.get())))));
        list.add(Component.nullToEmpty("\u00A71 Roost Ultimate"));
    }
    public static final String BLOCK_ENTITY_TAG = "BlockEntityTag";
    public static final String BLOCK_STATE_TAG = "BlockStateTag";

    @Override
    public InteractionResult useOn(UseOnContext p_40581_) {
        InteractionResult interactionresult = this.place(new BlockPlaceContext(p_40581_));
        if (!interactionresult.consumesAction() && this.isEdible()) {
            InteractionResult interactionresult1 = this.use(p_40581_.getLevel(), p_40581_.getPlayer(), p_40581_.getHand()).getResult();
            return interactionresult1 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : interactionresult1;
        } else {
            return interactionresult;
        }
    }
    @Override
    public InteractionResult place(BlockPlaceContext p_40577_) {
        if (!this.getBlock().isEnabled(p_40577_.getLevel().enabledFeatures())) {
            return InteractionResult.FAIL;
        } else if (!p_40577_.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext blockplacecontext = this.updatePlacementContext(p_40577_);
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
                        blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemstack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                        }
                    }

                    SoundType soundtype = blockstate1.getSoundType(level, blockpos, p_40577_.getPlayer());
                    level.playSound(player, blockpos, this.getPlaceSound(blockstate1, level, blockpos, p_40577_.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    level.gameEvent(GameEvent.BLOCK_PLACE, blockpos, GameEvent.Context.of(player, blockstate1));
                    if (player == null || !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
    }

    /** @deprecated */
    @Deprecated
    @Override
    protected SoundEvent getPlaceSound(BlockState p_40588_) {
        return p_40588_.getSoundType().getPlaceSound();
    }
    @Override
    protected SoundEvent getPlaceSound(BlockState state, Level world, BlockPos pos, Player entity) {
        return state.getSoundType(world, pos, entity).getPlaceSound();
    }
    @Override
    @Nullable
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext p_40609_) {
        return p_40609_;
    }
    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos p_40597_, Level p_40598_, @Nullable Player p_40599_, ItemStack p_40600_, BlockState p_40601_) {
        return updateCustomBlockEntityTag(p_40598_, p_40599_, p_40597_, p_40600_);
    }
    @Override
    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext p_40613_) {
        BlockState blockstate = this.getBlock().getStateForPlacement(p_40613_);
        return blockstate != null && this.canPlace(p_40613_, blockstate) ? blockstate : null;
    }


    private BlockState updateBlockStateFromTag(BlockPos p_40603_, Level p_40604_, ItemStack p_40605_, BlockState p_40606_) {
        BlockState blockstate = p_40606_;
        CompoundTag compoundtag = p_40605_.getTag();
        if (compoundtag != null) {
            CompoundTag compoundtag1 = compoundtag.getCompound("BlockStateTag");
            StateDefinition<Block, BlockState> statedefinition = p_40606_.getBlock().getStateDefinition();

            for(String s : compoundtag1.getAllKeys()) {
                Property<?> property = statedefinition.getProperty(s);
                if (property != null) {
                    String s1 = compoundtag1.get(s).getAsString();
                    blockstate = updateState(blockstate, property, s1);
                }
            }
        }

        if (blockstate != p_40606_) {
            p_40604_.setBlock(p_40603_, blockstate, 2);
        }

        return blockstate;
    }

    private static <T extends Comparable<T>> BlockState updateState(BlockState p_40594_, Property<T> p_40595_, String p_40596_) {
        return (BlockState)p_40595_.getValue(p_40596_).map((p_40592_) -> (BlockState)p_40594_.setValue(p_40595_, p_40592_)).orElse(p_40594_);
    }
    @Override
    protected boolean canPlace(BlockPlaceContext p_40611_, BlockState p_40612_) {
        Player player = p_40611_.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return (!this.mustSurvive() || p_40612_.canSurvive(p_40611_.getLevel(), p_40611_.getClickedPos())) && p_40611_.getLevel().isUnobstructed(p_40612_, p_40611_.getClickedPos(), collisioncontext);
    }
    @Override
    protected boolean mustSurvive() {
        return true;
    }
    @Override
    protected boolean placeBlock(BlockPlaceContext p_40578_, BlockState p_40579_) {
        return p_40578_.getLevel().setBlock(p_40578_.getClickedPos(), p_40579_, 11);
    }

    public static boolean updateCustomBlockEntityTag(Level p_40583_, @Nullable Player p_40584_, BlockPos p_40585_, ItemStack p_40586_) {
        MinecraftServer minecraftserver = p_40583_.getServer();
        if (minecraftserver == null) {
            return false;
        } else {
            CompoundTag compoundtag = getBlockEntityData(p_40586_);
            if (compoundtag != null) {
                BlockEntity blockentity = p_40583_.getBlockEntity(p_40585_);
                if (blockentity != null) {
                    if (!p_40583_.isClientSide && blockentity.onlyOpCanSetNbt() && (p_40584_ == null || !p_40584_.canUseGameMasterBlocks())) {
                        return false;
                    }

                    CompoundTag compoundtag1 = blockentity.saveWithoutMetadata();
                    CompoundTag compoundtag2 = compoundtag1.copy();
                    compoundtag1.merge(compoundtag);
                    if (!compoundtag1.equals(compoundtag2)) {
                        blockentity.load(compoundtag1);
                        blockentity.setChanged();
                        return true;
                    }
                }
            }

            return false;
        }
    }
    @Override
    public Block getBlock() {
        return this.getBlockRaw() == null ? null : (Block) ForgeRegistries.BLOCKS.getDelegateOrThrow(this.getBlockRaw()).get();
    }

    private Block getBlockRaw() {
        return this.block;
    }
    @Override
    public void registerBlocks(Map<Block, Item> p_40607_, Item p_40608_) {
        p_40607_.put(this.getBlock(), p_40608_);
    }
    @Override
    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
        blockToItemMap.remove(this.getBlock());
    }
    @Override
    public boolean canFitInsideContainerItems() {
        return !(this.block instanceof ShulkerBoxBlock);
    }


    @Nullable
    public static CompoundTag getBlockEntityData(ItemStack p_186337_) {
        return p_186337_.getTagElement("BlockEntityTag");
    }

    public static void setBlockEntityData(ItemStack p_186339_, BlockEntityType<?> p_186340_, CompoundTag p_186341_) {
        if (p_186341_.isEmpty()) {
            p_186339_.removeTagKey("BlockEntityTag");
        } else {
            BlockEntity.addEntityType(p_186341_, p_186340_);
            p_186339_.addTagElement("BlockEntityTag", p_186341_);
        }

    }
    @Override
    public FeatureFlagSet requiredFeatures() {
        return this.getBlock().requiredFeatures();
    }
}