//
//package net.tarantel.chickenroost.block;
//
//import net.tarantel.chickenroost.world.inventory.Roostguiv1Menu;
//import net.tarantel.chickenroost.procedures.ChickenOutputHandlerProcedure;
//import net.tarantel.chickenroost.procedures.RoostboxwithchickenBlockAddedProcedure;
//
//import net.tarantel.chickenroost.block.tile.RoostBlockEntity;
//
//import net.minecraftforge.network.NetworkHooks;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.api.distmarker.Dist;
//
//import net.minecraft.world.phys.BlockHitResult;
//import net.minecraft.world.level.storage.loot.LootContext;
//import net.minecraft.world.level.block.state.properties.DirectionProperty;
//import net.minecraft.world.level.block.state.StateDefinition;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.Rotation;
//import net.minecraft.world.level.block.Mirror;
//import net.minecraft.world.level.block.HorizontalDirectionalBlock;
//import net.minecraft.world.level.block.EntityBlock;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.item.context.BlockPlaceContext;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.inventory.AbstractContainerMenu;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.MenuProvider;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.Containers;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.core.Direction;
//import net.minecraft.core.BlockPos;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.ItemBlockRenderTypes;
//
//import java.util.Random;
//import java.util.List;
//import java.util.Collections;
//
//import io.netty.buffer.Unpooled;
//import net.minecraft.world.level.block.state.properties.IntegerProperty;
////import net.tarantel.chickenroost.BlockProperties;
////import net.minecraftforge.common.property.Properties;
//
//
//public class RoostBlock extends Block
// implements
// EntityBlock {
//
//
//    /*public enum ChickenType implements StringRepresentable{
//
//    	private final String name;
//
//    	ChickenType(String name){
//    		this.name = name;
//    	}
//
//    	public String toString(){
//    		return this.name;
//    	}
//
//        @Override
//    	public String getSerializedName(){
//    		return this.name;
//    	}
//
//    	public String getName(){
//    		return this.name;
//    	}
//    }
//    public static final */
//    //public static final EnumProperty CHICKEN = new EnumProperty.create("chicken" );
//	//public static final PropertyChickenType CHICKENTYPE = new PropertyChickenType("chicken");
//	//public static final EnumProperty<Mynewparty> MYNEWPARTY = EnumProperty.create("mynnewparty", Mynewparty.class);
//	public static final IntegerProperty TEST_MYNEWSTATE = IntegerProperty.create("mynewstate", 0, 17);
//	//public static final IntegerProperty TEST_MYNEWSTATE = BlockProperties.TEST_MYNEWSTATE;
//	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
//	//public static final EnumProperty TEST_MYNEWSTATE = EnumProperty.create("testenum", p_61589_)
//	//public static final EnumProperty<PillarSection> TEST_MYNEWSTATE = EnumProperty.create("testenum", Half.class);
//	//public static final EnumProperty TEST_MYNEWSTATE = EnumProperty.create("mynewstate", Half.class, "basestate");
//	//public static final EnumProperty<PistonType> BBBBBBB;
//
//	public RoostBlock(Properties properties) {
//		super(properties);
//		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TEST_MYNEWSTATE, 0));
//	}
//
//	@Override
//	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
//		return true;
//	}
//
//	@Override
//	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
//		return 0;
//	}
//
//	@Override
//	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
//		builder.add(FACING, TEST_MYNEWSTATE);
//
//	}
//
//	public BlockState rotate(BlockState state, Rotation rot) {
//		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
//	}
//
//	public BlockState mirror(BlockState state, Mirror mirrorIn) {
//		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
//	}
//
//	@Override
//	public BlockState getStateForPlacement(BlockPlaceContext context) {
//		;
//		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
//	}
//
//	@Override
//	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
//		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
//		if (!dropsOriginal.isEmpty())
//			return dropsOriginal;
//		return Collections.singletonList(new ItemStack(ModBlocks.ROOSTV_1.get()));
//	}
//
//	@Override
//	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
//		super.onPlace(blockstate, world, pos, oldState, moving);
//		world.scheduleTick(pos, this, 1);
//		RoostboxwithchickenBlockAddedProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
//	}
//
//
//	@Override
//	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, Random random) {
//		super.tick(blockstate, world, pos, random);
//		int x = pos.getX();
//		int y = pos.getY();
//		int z = pos.getZ();
//
//		ChickenOutputHandlerProcedure.execute(world, x, y, z);
//		world.scheduleTick(pos, this, 1);
//	}
//
//	@Override
//	public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
//		super.use(blockstate, world, pos, entity, hand, hit);
//		if (entity instanceof ServerPlayer player) {
//			NetworkHooks.openGui(player, new MenuProvider() {
//				@Override
//
//				public Component getDisplayName() {
//					return new TextComponent("Roost V1");
//				}
//
//				@Override
//				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
//					return new Roostguiv1Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
//				}
//			}, pos);
//		}
//		return InteractionResult.SUCCESS;
//	}
//
//	@Override
//	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
//		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
//		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
//	}
//
//	@Override
//	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//		return new RoostBlockEntity(pos, state);
//	}
//
//	@Override
//	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
//		super.triggerEvent(state, world, pos, eventID, eventParam);
//		BlockEntity blockEntity = world.getBlockEntity(pos);
//		return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
//	}
//
//	@Override
//	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
//		if (state.getBlock() != newState.getBlock()) {
//			BlockEntity blockEntity = world.getBlockEntity(pos);
//			if (blockEntity instanceof RoostBlockEntity be) {
//				Containers.dropContents(world, pos, be);
//				world.updateNeighbourForOutputSignal(pos, this);
//			}
//			super.onRemove(state, world, pos, newState, isMoving);
//		}
//	}
//
//	@Override
//	public boolean hasAnalogOutputSignal(BlockState state) {
//		return true;
//	}
//
//	@Override
//	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
//		BlockEntity tileentity = world.getBlockEntity(pos);
//		if (tileentity instanceof RoostBlockEntity be)
//			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
//		else
//			return 0;
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public static void registerRenderLayer() {
//		ItemBlockRenderTypes.setRenderLayer(ModBlocks.ROOSTV_1.get(), renderType -> renderType == RenderType.cutout());
//	}
//}
//