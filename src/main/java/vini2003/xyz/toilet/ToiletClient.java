package vini2003.xyz.toilet;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import vini2003.xyz.toilet.registry.client.ToiletBlockRenderLayers;
import vini2003.xyz.toilet.registry.client.ToiletEntities;
import vini2003.xyz.toilet.registry.client.ToiletKeybinds;
import vini2003.xyz.toilet.registry.client.ToiletNetworking;

@Environment(EnvType.CLIENT)
public class ToiletClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ToiletKeybinds.initialize();
		ToiletNetworking.initialize();
		ToiletEntities.initialize();
		ToiletBlockRenderLayers.initialize();
	}
}
