package vini2003.xyz.toilet.registry.client;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import vini2003.xyz.toilet.registry.common.ToiletBlocks;

public class ToiletBlockRenderLayers {
	public static void initialize() {
		BlockRenderLayerMap.INSTANCE.putBlock(ToiletBlocks.TOILET, RenderLayer.getTranslucent());
	}
}
