package vini2003.xyz.toilet.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import vini2003.xyz.toilet.common.entity.SeatEntity;
import vini2003.xyz.toilet.registry.common.ToiletEntities;

public abstract class SeatBlock extends Block {
	public static final BooleanProperty OCCUPIED = Properties.OCCUPIED;
	
	public SeatBlock(Settings settings) {
		super(settings);
		
		setDefaultState(getDefaultState().with(OCCUPIED, false));
	}
	
	public abstract float getYOffset();
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!(state.getBlock() instanceof SeatBlock)) {
			return ActionResult.PASS;
		}
		
		boolean occupied = state.get(OCCUPIED);
		
		if (!occupied && !player.isSneaking()) {
			if (!world.isClient) {
				SeatEntity entity = ToiletEntities.SEAT.create(world);
				entity.setPos(pos);
				world.spawnEntity(entity);
				world.setBlockState(pos, state.with(OCCUPIED, true));
				player.startRiding(entity);
			}
			
			return ActionResult.SUCCESS;
		} else {
			return ActionResult.PASS;
		}
	}
	
	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		super.onStateReplaced(state, world, pos, newState, moved);
		
		if (state.getBlock() != newState.getBlock()) {
			if (!world.isClient) {
				world.getEntitiesByType(
						ToiletEntities.SEAT,
						new Box(pos),
						entity -> true
				).forEach(it -> {
					it.removeAllPassengers();
					it.kill();
				});
			}
		}
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(OCCUPIED);
		
		super.appendProperties(builder);
	}
}
