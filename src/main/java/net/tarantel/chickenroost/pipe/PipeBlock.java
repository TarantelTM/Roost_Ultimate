package net.tarantel.chickenroost.pipe;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PipeBlock extends BaseEntityBlock {
   public static final BooleanProperty UP = BooleanProperty.create("up");
   public static final BooleanProperty DOWN = BooleanProperty.create("down");
   public static final BooleanProperty NORTH = BooleanProperty.create("north");
   public static final BooleanProperty SOUTH = BooleanProperty.create("south");
   public static final BooleanProperty EAST = BooleanProperty.create("east");
   public static final BooleanProperty WEST = BooleanProperty.create("west");
   private final PipeTier tier;
   private final Supplier<Item> chickenStick;

   public PipeBlock(Properties props, PipeTier tier, Supplier<Item> chickenStick) {
      super(props);
      this.tier = tier;
      this.chickenStick = chickenStick;
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(UP, false))
                        .setValue(DOWN, false))
                     .setValue(NORTH, false))
                  .setValue(SOUTH, false))
               .setValue(EAST, false))
            .setValue(WEST, false)
      );
   }

   public PipeTier getTier() {
      return this.tier;
   }

   @NotNull
   protected MapCodec<? extends BaseEntityBlock> codec() {
      return MapCodec.unit(this);
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new PipeBlockEntity(pos, state);
   }

   private boolean canConnectTo(Level level, BlockPos selfPos, Direction dir) {
      BlockPos npos = selfPos.relative(dir);
      BlockState neighborState = level.getBlockState(npos);
      if (neighborState.getBlock() instanceof PipeBlock other) {
         return other.getTier() == this.tier;
      } else {
         PipeBlockEntity selfPipe = this.getPipeBE(level, selfPos);
         if (selfPipe == null) {
            return false;
         } else {
            PipeBlockEntity.PipeMode mode = selfPipe.getMode();
            return mode != PipeBlockEntity.PipeMode.INPUT && mode != PipeBlockEntity.PipeMode.OUTPUT
               ? false
               : level.getCapability(ItemHandler.BLOCK, npos, neighborState, level.getBlockEntity(npos), dir.getOpposite()) != null;
         }
      }
   }

   private BlockState computeConnections(Level level, BlockPos pos, BlockState state) {
      return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)state.setValue(UP, this.canConnectTo(level, pos, Direction.UP)))
                     .setValue(DOWN, this.canConnectTo(level, pos, Direction.DOWN)))
                  .setValue(NORTH, this.canConnectTo(level, pos, Direction.NORTH)))
               .setValue(SOUTH, this.canConnectTo(level, pos, Direction.SOUTH)))
            .setValue(EAST, this.canConnectTo(level, pos, Direction.EAST)))
         .setValue(WEST, this.canConnectTo(level, pos, Direction.WEST));
   }

   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
      BlockState placed = this.defaultBlockState();
      Level var4 = ctx.getLevel();
      return var4 instanceof Level ? this.computeConnections(var4, ctx.getClickedPos(), placed) : placed;
   }

   public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState, LevelAccessor levelAccessor, BlockPos pos, BlockPos neighborPos) {
      if (levelAccessor instanceof Level level) {
         this.invalidateCache(level, pos);
         return this.computeConnections(level, pos, state);
      } else {
         return state;
      }
   }

   public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      return InteractionResult.PASS;
   }

   public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
      super.onPlace(state, level, pos, oldState, movedByPiston);
      if (!level.isClientSide && level.getBlockEntity(pos) instanceof PipeBlockEntity pipe) {
         pipe.invalidateNetwork(level);
      }
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
      super.onRemove(state, level, pos, newState, movedByPiston);
      if (!level.isClientSide && level.getBlockEntity(pos) instanceof PipeBlockEntity pipe) {
         pipe.invalidateNetwork(level);
      }
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
   ) {
      if (stack.getItem() != this.chickenStick.get()) {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      } else if (level.isClientSide) {
         return ItemInteractionResult.SUCCESS;
      } else if (level.getBlockEntity(pos) instanceof PipeBlockEntity pipe) {
         pipe.cycleMode();
         pipe.invalidateNetwork(level);
         BlockState newState = this.computeConnections(level, pos, state);
         level.setBlock(pos, newState, 3);

         Component modeComponent = Component.translatable("roost_chicken.interface.pipemode." + pipe.getMode().name().toLowerCase())
            .withStyle(switch (pipe.getMode()) {
               case NONE -> ChatFormatting.GRAY;
               case INPUT -> ChatFormatting.YELLOW;
               case OUTPUT -> ChatFormatting.GREEN;
            });
         if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.displayClientMessage(Component.translatable("roost_chicken.pipesystem.mode.show", new Object[]{modeComponent}), true);
         }

         return ItemInteractionResult.CONSUME;
      } else {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{UP, DOWN, NORTH, SOUTH, EAST, WEST});
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
      return box(4.0, 4.0, 4.0, 12.0, 12.0, 12.0);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return type == ModBlockEntities.PIPE.get() ? (lvl, p, st, be) -> PipeBlockEntity.tick(lvl, p, st, (PipeBlockEntity)be) : null;
   }

   private boolean hasItemHandler(Level level, BlockPos pos, Direction side) {
      BlockEntity be = level.getBlockEntity(pos);
      return be == null ? false : level.getCapability(ItemHandler.BLOCK, pos, level.getBlockState(pos), be, side) != null;
   }

   @Nullable
   private PipeBlockEntity getPipeBE(Level level, BlockPos pos) {
      return level.getBlockEntity(pos) instanceof PipeBlockEntity pipe ? pipe : null;
   }

   private void invalidateCache(Level level, BlockPos pos) {
      if (level.getBlockEntity(pos) instanceof PipeBlockEntity pipe) {
         pipe.invalidateOutputCache();
      }
   }
}
