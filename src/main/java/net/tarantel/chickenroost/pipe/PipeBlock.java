package net.tarantel.chickenroost.pipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import net.tarantel.chickenroost.block.tile.TrainerTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class PipeBlock extends BaseEntityBlock {

    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    private final PipeTier tier;
    private final Supplier<Item> chickenStick;

    public PipeBlock(Properties properties, PipeTier tier, Supplier<Item> chickenStick) {
        super(properties);
        this.tier = tier;
        this.chickenStick = chickenStick;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(UP, false).setValue(DOWN, false)
                .setValue(NORTH, false).setValue(SOUTH, false)
                .setValue(EAST, false).setValue(WEST, false)
        );
    }


    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        dropFromTile(level, pos);
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        // Wird von destroyBlock(...) benutzt
        if (level instanceof Level l && !l.isClientSide()) {
            dropFromTile(l, pos);
        }
        super.destroy(level, pos, state);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state,
                              @Nullable BlockEntity be, ItemStack tool) {
        dropFromTile(level, pos);
        super.playerDestroy(level, player, pos, state, be, tool);
    }

    private void dropFromTile(Level level, BlockPos pos) {
        if (level.isClientSide()) return;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof PipeBlockEntity tile)) return;

        // 🔥 zentraler Schutz gegen Mehrfach-Drops
        if (tile.hasDropped()) return;
        tile.markDropped();

        // Der Block weiß sein Tier → droppt sich selbst
        Containers.dropItemStack(
                level,
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                new ItemStack(this)
        );
    }


    public PipeTier getTier() {
        return tier;
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return MapCodec.unit(this);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PipeBlockEntity(pos, state);
    }

    private boolean canConnectTo(Level level, BlockPos selfPos, Direction dir) {
        BlockPos npos = selfPos.relative(dir);
        BlockState neighborState = level.getBlockState(npos);

        if (neighborState.getBlock() instanceof PipeBlock other) {
            return other.getTier() == this.tier;
        }

        PipeBlockEntity selfPipe = getPipeBE(level, selfPos);
        if (selfPipe == null) return false;

        PipeBlockEntity.PipeMode mode = selfPipe.getMode();

        if (mode != PipeBlockEntity.PipeMode.INPUT &&
                mode != PipeBlockEntity.PipeMode.OUTPUT) {
            return false;
        }

        return level.getCapability(
                Capabilities.Item.BLOCK,
                npos,
                neighborState,
                level.getBlockEntity(npos),
                dir.getOpposite()
        ) != null;
    }



    private BlockState computeConnections(Level level, BlockPos pos, BlockState state) {
        return state
                .setValue(UP, canConnectTo(level, pos, Direction.UP))
                .setValue(DOWN, canConnectTo(level, pos, Direction.DOWN))
                .setValue(NORTH, canConnectTo(level, pos, Direction.NORTH))
                .setValue(SOUTH, canConnectTo(level, pos, Direction.SOUTH))
                .setValue(EAST, canConnectTo(level, pos, Direction.EAST))
                .setValue(WEST, canConnectTo(level, pos, Direction.WEST));
    }


    @Override
    public BlockState getStateForPlacement(net.minecraft.world.item.context.BlockPlaceContext ctx) {
        BlockState placed = this.defaultBlockState();
        if (ctx.getLevel() instanceof Level level) {
            return computeConnections(level, ctx.getClickedPos(), placed);
        }
        return placed;
    }

    @Override
    public BlockState updateShape(BlockState blockState, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (level instanceof Level levell) {
            invalidateCache(levell, blockPos);
            return computeConnections(levell, blockPos, blockState);
        }
        return blockState;
    }


    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                            Player player, BlockHitResult hit) {
        return InteractionResult.PASS;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos,
                        BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);

        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof PipeBlockEntity pipe) {
                pipe.invalidateNetwork(level);
            }
        }
    }


    @Override
    protected void affectNeighborsAfterRemoval(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, boolean movedByPiston) {
        super.affectNeighborsAfterRemoval(blockState, serverLevel, blockPos, movedByPiston);

        if (!serverLevel.isClientSide()) {
            BlockEntity be = serverLevel.getBlockEntity(blockPos);
            if (be instanceof PipeBlockEntity pipe) {
                pipe.invalidateNetwork(serverLevel);
            }

        }
    }




    @Override
    protected InteractionResult  useItemOn(ItemStack stack, BlockState state, Level level,
                                              BlockPos pos, Player player, InteractionHand hand,
                                              BlockHitResult hit) {

        if (stack.getItem() != chickenStick.get()) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof PipeBlockEntity pipe) {

            pipe.cycleMode();
            pipe.invalidateNetwork(level);

            BlockState newState = computeConnections(level, pos, state);
            level.setBlock(pos, newState, 3);
            Component modeComponent = Component.translatable(
                    "roost_chicken.interface.pipemode." + pipe.getMode().name().toLowerCase()
            ).withStyle(switch (pipe.getMode()) {
                case NONE -> ChatFormatting.GRAY;
                case INPUT -> ChatFormatting.YELLOW;
                case OUTPUT -> ChatFormatting.GREEN;
            });
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.displayClientMessage(
                        Component.translatable(
                                "roost_chicken.pipesystem.mode.show",
                                modeComponent
                        ),
                        true
                );
            }
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public net.minecraft.world.phys.shapes.VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
                                                               net.minecraft.world.phys.shapes.CollisionContext ctx) {
        return box(4, 4, 4, 12, 12, 12);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModBlockEntities.PIPE.get()
                ? (lvl, p, st, be) -> PipeBlockEntity.tick(lvl, p, st, (PipeBlockEntity) be)
                : null;
    }


    private boolean hasItemHandler(Level level, BlockPos pos, Direction side) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be == null) return false;

        return level.getCapability(
                Capabilities.Item.BLOCK,
                pos,
                level.getBlockState(pos),
                be,
                side
        ) != null;
    }

    @Nullable
    private PipeBlockEntity getPipeBE(Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        return be instanceof PipeBlockEntity pipe ? pipe : null;
    }

    private void invalidateCache(Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof PipeBlockEntity pipe) {
            pipe.invalidateOutputCache();
        }
    }




}
