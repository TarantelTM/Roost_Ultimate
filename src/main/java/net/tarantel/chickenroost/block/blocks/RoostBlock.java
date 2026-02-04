package net.tarantel.chickenroost.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.tarantel.chickenroost.block.tile.ModBlockEntities;
import net.tarantel.chickenroost.block.tile.RoostTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RoostBlock extends BaseEntityBlock {
   public static final MapCodec<RoostBlock> CODEC = simpleCodec(RoostBlock::new);
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

   public RoostBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
   }

   @NotNull
   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   @NotNull
   public VoxelShape getShape(
      @NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext
   ) {
      return SHAPE;
   }

   public BlockState getStateForPlacement(BlockPlaceContext pContext) {
      return (BlockState)this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
   }

   @NotNull
   public BlockState rotate(BlockState pState, Rotation pRotation) {
      return (BlockState)pState.setValue(FACING, pRotation.rotate((Direction)pState.getValue(FACING)));
   }

   @NotNull
   public BlockState mirror(BlockState pState, Mirror pMirror) {
      return pState.rotate(pMirror.getRotation((Direction)pState.getValue(FACING)));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   public void setPlacedBy(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
      super.setPlacedBy(world, pos, state, placer, stack);
      if (stack.getComponents().has(DataComponents.CONTAINER)) {
         ItemContainerContents itemContainerContents = (ItemContainerContents)stack.get(DataComponents.CONTAINER);
         if (itemContainerContents != null) {
            if (world.getBlockEntity(pos) instanceof RoostTile tile) {
               int slots = itemContainerContents.getSlots();

               for (int i = 0; i < slots; i++) {
                  ItemStack itemStack = itemContainerContents.getStackInSlot(i);
                  if (!itemStack.isEmpty()) {
                     tile.itemHandler.setStackInSlot(i, itemStack);
                  }
               }
            }
         }
      }
   }

   @Override
   protected void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @Nullable net.minecraft.core.BlockPos neighborPos, boolean movedByPiston) {
      super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
      if (!level.isClientSide() && level.getBlockEntity(pos) instanceof RoostTile tile) {
         tile.updateRedstoneSignal();
      }
   }

   public void onPlace(@NotNull BlockState blockstate, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean moving) {
      super.onPlace(blockstate, world, pos, oldState, moving);
      world.scheduleTick(pos, this, 20);
   }

   @NotNull
   public RenderShape getRenderShape(@NotNull BlockState blockState) {
      return RenderShape.MODEL;
   }

   public void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
      if (pState.getBlock() != pNewState.getBlock()) {
         BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
         if (blockEntity instanceof RoostTile) {
            ((RoostTile)blockEntity).drops();
         }
      }

      super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
   }

   public void tick(@NotNull BlockState blockstate, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
      super.tick(blockstate, world, pos, random);
      world.scheduleTick(pos, this, 20);
   }

   @NotNull
   protected InteractionResult useWithoutItem(
      @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult
   ) {
      if (!level.isClientSide()) {
         BlockEntity entity = level.getBlockEntity(pos);
         ServerPlayer theplayer = (ServerPlayer)player;
         if (!(entity instanceof RoostTile)) {
            throw new IllegalStateException("Our Container provider is missing!");
         }

         theplayer.openMenu((RoostTile)entity, pos);
      }

      return InteractionResult.PASS;
   }

   @NotNull
   protected ItemInteractionResult useItemOn(
      @NotNull ItemStack stack,
      @NotNull BlockState state,
      Level level,
      @NotNull BlockPos pos,
      @NotNull Player player,
      @NotNull InteractionHand hand,
      @NotNull BlockHitResult hitResult
   ) {
      if (!level.isClientSide()) {
         BlockEntity entity = level.getBlockEntity(pos);
         ServerPlayer theplayer = (ServerPlayer)player;
         if (!(entity instanceof RoostTile)) {
            throw new IllegalStateException("Our Container provider is missing!");
         }

         theplayer.openMenu((RoostTile)entity, pos);
      }

      return ItemInteractionResult.sidedSuccess(true);
   }

   @Nullable
   public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new RoostTile(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
      return createTickerHelper(type, ModBlockEntities.ROOST.get(), RoostTile::tick);
   }
}
