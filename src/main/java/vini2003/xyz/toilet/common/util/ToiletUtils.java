package vini2003.xyz.toilet.common.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import vini2003.xyz.toilet.registry.common.ToiletBlocks;

public class ToiletUtils {
	@Nullable
	public static BlockPos nearest(World world, BlockPos pos) {
		for (int x = -1; x <= 1; ++x) {
			for (int z = -1; z <= 1; ++z) {
				for (int y = -1; y <= 1; ++y) {
					if (world.getBlockState(pos.add(x, y, z)).getBlock() == ToiletBlocks.TOILET) {
						return pos.add(x, y, z);
					}
				}
			}
		}
		
		return null;
	}
}
