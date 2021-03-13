package vini2003.xyz.withermorph;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import vini2003.xyz.withermorph.registry.common.*;

public class WitherMorph implements ModInitializer {
	public static final String ID = "withermorph";
	
	public static boolean timerEnabled = true;
	
	public static long timerSeconds = 300L;
	
	public static Identifier identifier(String path) {
		return new Identifier(ID, path);
	}
	
	@Override
	public void onInitialize() {
		WitherMorphCommands.initialize();
		WitherMorphComponents.initialize();
		WitherMorphNetworking.initialize();
	}
}
