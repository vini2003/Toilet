package vini2003.xyz.toilet.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import vini2003.xyz.toilet.common.util.VoxelShapeUtils;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public class ToiletBlock extends SeatBlock {
	public static final BooleanProperty PEE = BooleanProperty.of("pee");
	public static final BooleanProperty POOP = BooleanProperty.of("poop");
	public static final BooleanProperty CLOGGED = BooleanProperty.of("clogged");
	
	private static final VoxelShape NORTH_SHAPE;
	private static final VoxelShape SOUTH_SHAPE;
	private static final VoxelShape WEST_SHAPE;
	private static final VoxelShape EAST_SHAPE;
	
	private static final float[] CUBOIDS = new float[] {
			11.0F, 6.0F, 4.0F, 13.0F, 12.0F, 14.0F,
			9.0F, 6.0F, 4.0F, 11.0F, 8.0F, 14.0F,
			3.0F, 6.0F, 4.0F, 5.0F, 12.0F, 14.0F,
			5.0F, 6.0F, 4.0F, 7.0F, 8.0F, 14.0F,
			3.0F, 6.0F, 14.0F, 13.0F, 12.0F, 16.0F,
			7.0F, 6.0F, 10.0F, 9.0F, 8.0F, 14.0F,
			7.0F, 6.0F, 4.0F, 9.0F, 8.0F, 6.0F,
			3.0F, 6.0F, 0.0F, 13.0F, 19.0F, 4.0F,
			6.0F, 19.0F, 1.0F, 10.0F, 19.5F, 3.0F,
			7.0F, 6.0F, 6.0F, 9.0F, 7.0F, 10.0F,
			4.0F, 0.0F, 0.0F, 12.0F, 6.0F, 14.0F
	};
	
	public ToiletBlock(Settings settings) {
		super(settings);
		
		setDefaultState(getDefaultState().with(PEE, false).with(POOP, false).with(CLOGGED, false));
	}
	
	@Override
	public float getYOffset() {
		return 0.15F;
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			if (player.isSneaking()) {
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.NEUTRAL, 0.6F, 1.0F, false);
				
				((ServerWorld) world).spawnParticles(ParticleTypes.SPLASH, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 32, world.random.nextFloat() * 0.25F, world.random.nextFloat() * 0.25F, world.random.nextFloat() * 0.25F, 1.0D);
				
				boolean clogged = state.get(CLOGGED);
				
				if (!clogged) {
					world.setBlockState(pos, world.getBlockState(pos).with(PEE, false));
					world.setBlockState(pos, world.getBlockState(pos).with(POOP, false));
				}
				
				return ActionResult.SUCCESS;
			} else {
				return super.onUse(state, world, pos, player, hand, hit);
			}
		} else {
			return ActionResult.SUCCESS;
		}
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(CLOGGED);
		builder.add(PEE);
		builder.add(POOP);
		builder.add(HORIZONTAL_FACING);

		super.appendProperties(builder);
	}
	
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return getShape(state);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return getShape(state);
	}
	
	private VoxelShape getShape(BlockState state) {
		switch (state.get(HORIZONTAL_FACING)) {
			case NORTH:
				return NORTH_SHAPE;
			case SOUTH:
				return SOUTH_SHAPE;
			case WEST:
				return WEST_SHAPE;
			case EAST:
				return EAST_SHAPE;
		}
		
		return NORTH_SHAPE;
	}
	
	static {
		VoxelShape shape = VoxelShapes.empty();
		
		for (int i = 0; i < CUBOIDS.length; i += 6) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(CUBOIDS[i], CUBOIDS[i + 1], CUBOIDS[i + 2], CUBOIDS[i + 3], CUBOIDS[i + 4], CUBOIDS[i + 5]));
		}
		
		NORTH_SHAPE = shape;
		SOUTH_SHAPE = VoxelShapeUtils.rotateOneHundredAndEighty(Direction.Axis.Y, NORTH_SHAPE);
		WEST_SHAPE = VoxelShapeUtils.rotateTwoHundredAndSeventy(Direction.Axis.Y, NORTH_SHAPE);
		EAST_SHAPE = VoxelShapeUtils.rotateNinety(Direction.Axis.Y, NORTH_SHAPE);
	}
}
