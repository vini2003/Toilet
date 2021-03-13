package vini2003.xyz.withermorph;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import vini2003.xyz.withermorph.registry.client.WitherMorphKeybinds;
import vini2003.xyz.withermorph.registry.client.WitherMorphShaders;

@Environment(EnvType.CLIENT)
public class WitherMorphClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		WitherMorphShaders.initialize();
		WitherMorphKeybinds.initialize();
	}
}
