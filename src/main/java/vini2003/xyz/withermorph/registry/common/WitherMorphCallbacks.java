package vini2003.xyz.withermorph.registry.common;

import me.shedaniel.cloth.api.common.events.v1.PlayerJoinCallback;
import me.shedaniel.cloth.api.common.events.v1.PlayerLeaveCallback;
import me.shedaniel.cloth.api.utils.v1.GameInstanceUtils;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;

public class WitherMorphCallbacks {
	private static void join(ClientConnection connection, ServerPlayerEntity playerEntity) {
		WitherMorphCommands.trackBossBars(GameInstanceUtils.getServer().getPlayerManager().getPlayerList());
	}
	
	private static void leave(ServerPlayerEntity playerEntity) {
		WitherMorphCommands.trackBossBars(GameInstanceUtils.getServer().getPlayerManager().getPlayerList());
	}
	
	public static void initialize() {
		PlayerJoinCallback.EVENT.register(WitherMorphCallbacks::join);
		
		PlayerLeaveCallback.EVENT.register(WitherMorphCallbacks::leave);
	}
}
