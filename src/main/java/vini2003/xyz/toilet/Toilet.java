package vini2003.xyz.toilet;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import vini2003.xyz.toilet.registry.common.*;

public class Toilet implements ModInitializer {
	public static final String ID = "toilet";
	
	public static Identifier identifier(String path) {
		return new Identifier(ID, path);
	}
	
	@Override
	public void onInitialize() {
		ToiletNetworking.initialize();
		ToiletCallbacks.initialize();
		ToiletEntities.initialize();
		ToiletBlocks.initialize();
		ToiletItems.initialize();
		ToiletItemGroups.initialize();
	}
}
