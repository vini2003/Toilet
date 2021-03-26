package vini2003.xyz.toilet.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vini2003.xyz.toilet.registry.common.ToiletBlocks;

import static vini2003.xyz.toilet.common.block.ToiletBlock.*;

public class PlungerItem extends Item {
	public PlungerItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		
		if (!world.isClient) {
			BlockPos pos = context.getBlockPos();
			
			BlockState state = world.getBlockState(context.getBlockPos());
			
			if (state.getBlock() == ToiletBlocks.TOILET) {
				boolean clogged = state.get(CLOGGED);
				
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.NEUTRAL, 0.6F, 1.0F, false);
				
				if (clogged && world.random.nextInt(3) == 0) {
					world.setBlockState(pos, state.with(CLOGGED, false));
					world.setBlockState(pos, world.getBlockState(pos).with(PEE, false));
					world.setBlockState(pos, world.getBlockState(pos).with(POOP, false));
				}
				
				((ServerWorld) world).spawnParticles(ParticleTypes.SPLASH, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 32, world.random.nextFloat() * 0.25F, world.random.nextFloat() * 0.25F, world.random.nextFloat() * 0.25F, 1.0D);
				
				return ActionResult.SUCCESS;
			} else {
				return super.useOnBlock(context);
			}
		} else {
			return super.useOnBlock(context);
		}
	}
}
