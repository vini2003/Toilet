package vini2003.xyz.toilet.registry.common;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import vini2003.xyz.toilet.Toilet;
import vini2003.xyz.toilet.common.block.ToiletBlock;

public class ToiletBlocks {
	public static final Block TOILET = Registry.register(
			Registry.BLOCK,
			Toilet.identifier("toilet"),
			new ToiletBlock(
					FabricBlockSettings.copy(Blocks.QUARTZ_BLOCK)
			)
	);
	
	public static void initialize() {
	
	}
}
