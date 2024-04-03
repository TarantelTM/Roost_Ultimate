
//ackage net.tarantel.chickenroost.block;

//mport net.tarantel.chickenroost.world.inventory.TrainerGUIMenu;
//mport net.tarantel.chickenroost.procedures.TrainerUpdateTickProcedure;
//mport net.tarantel.chickenroost.procedures.ChickenTrainerBlockAddedProcedure;
//mport net.tarantel.chickenroost.block.tile.ChickenTrainerBlockEntity;

//mport net.minecraftforge.network.NetworkHooks;
//mport net.minecraftforge.api.distmarker.OnlyIn;
//mport net.minecraftforge.api.distmarker.Dist;

//mport net.minecraft.world.phys.BlockHitResult;
//mport net.minecraft.world.level.storage.loot.LootContext;
//mport net.minecraft.world.level.material.Material;
//mport net.minecraft.world.level.block.state.properties.DirectionProperty;
//mport net.minecraft.world.level.block.state.StateDefinition;
//mport net.minecraft.world.level.block.state.BlockState;
//mport net.minecraft.world.level.block.state.BlockBehaviour;
//mport net.minecraft.world.level.block.entity.BlockEntity;
//mport net.minecraft.world.level.block.SoundType;
//mport net.minecraft.world.level.block.Rotation;
//mport net.minecraft.world.level.block.Mirror;
//mport net.minecraft.world.level.block.HorizontalDirectionalBlock;
//mport net.minecraft.world.level.block.EntityBlock;
//mport net.minecraft.world.level.block.Block;
//mport net.minecraft.world.level.Level;
//mport net.minecraft.world.level.BlockGetter;
//mport net.minecraft.world.item.context.BlockPlaceContext;
//mport net.minecraft.world.item.ItemStack;
//mport net.minecraft.world.inventory.AbstractContainerMenu;
//mport net.minecraft.world.entity.player.Player;
//mport net.minecraft.world.entity.player.Inventory;
//mport net.minecraft.world.MenuProvider;
//mport net.minecraft.world.InteractionResult;
//mport net.minecraft.world.InteractionHand;
//mport net.minecraft.world.Containers;
//mport net.minecraft.server.level.ServerPlayer;
//mport net.minecraft.server.level.ServerLevel;
//mport net.minecraft.network.chat.TextComponent;
//mport net.minecraft.network.chat.Component;
//mport net.minecraft.network.FriendlyByteBuf;
//mport net.minecraft.core.Direction;
//mport net.minecraft.core.BlockPos;
//mport net.minecraft.client.renderer.RenderType;
//mport net.minecraft.client.renderer.ItemBlockRenderTypes;

//mport java.util.Random;
//mport java.util.List;
//mport java.util.Collections;

//mport io.netty.buffer.Unpooled;

//ublic class ChickenTrainerBlock extends Block
//		implements

//			EntityBlock {
//	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

//	public ChickenTrainerBlock() {
//		super(BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion()
//				.isRedstoneConductor((bs, br, bp) -> false));
//		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
//	}

//	@Override
//	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
//		return true;
//	}

//	@Override
//	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
//		return 0;
//	}

//	@Override
//	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
//		builder.add(FACING);
//	}

//	public BlockState rotate(BlockState state, Rotation rot) {
//		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
//	}

//	public BlockState mirror(BlockState state, Mirror mirrorIn) {
//		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
//	}

//	@Override
//	public BlockState getStateForPlacement(BlockPlaceContext context) {
//		;
//		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
//	}

//	@Override
//	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
//		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
//		if (!dropsOriginal.isEmpty())
//			return dropsOriginal;
//		return Collections.singletonList(new ItemStack(this, 1));
//	}

//	@Override
//	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
//		super.onPlace(blockstate, world, pos, oldState, moving);
//		world.scheduleTick(pos, this, 1);
//		ChickenTrainerBlockAddedProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
//	}

//	@Override
//	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, Random random) {
//		super.tick(blockstate, world, pos, random);
//		int x = pos.getX();
//		int y = pos.getY();
//		int z = pos.getZ();

//		TrainerUpdateTickProcedure.execute(world, x, y, z);
//		world.scheduleTick(pos, this, 1);
//	}

//	@Override
//	public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
//		super.use(blockstate, world, pos, entity, hand, hit);
//		if (entity instanceof ServerPlayer player) {
//			NetworkHooks.openGui(player, new MenuProvider() {
//				@Override
//				public Component getDisplayName() {
//					return new TextComponent("Chicken Trainer");
//				}

//				@Override
//				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
//					return new TrainerGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
//				}
//			}, pos);
//		}
//		return InteractionResult.SUCCESS;
//	}

//	@Override
//	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
//		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
//		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
//	}

//	@Override
//	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//		return new ChickenTrainerBlockEntity(pos, state);
//	}

//	@Override
//	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
//		super.triggerEvent(state, world, pos, eventID, eventParam);
//		BlockEntity blockEntity = world.getBlockEntity(pos);
//		return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
//	}

//	@Override
//	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
//		if (state.getBlock() != newState.getBlock()) {
//			BlockEntity blockEntity = world.getBlockEntity(pos);
//			if (blockEntity instanceof ChickenTrainerBlockEntity be) {
//				Containers.dropContents(world, pos, be);
//				world.updateNeighbourForOutputSignal(pos, this);
//			}
//			super.onRemove(state, world, pos, newState, isMoving);
//		}
//	}

//	@Override
//	public boolean hasAnalogOutputSignal(BlockState state) {
//		return true;
//	}

//	@Override
//	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
//		BlockEntity tileentity = world.getBlockEntity(pos);
//		if (tileentity instanceof ChickenTrainerBlockEntity be)
//			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
//		else
//			return 0;
//	}

//	@OnlyIn(Dist.CLIENT)
//	public static void registerRenderLayer() {
//		ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHICKEN_TRAINER.get(), renderType -> renderType == RenderType.cutout());
//	}
//
