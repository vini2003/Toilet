package vini2003.xyz.withermorph.registry.common;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import vini2003.xyz.withermorph.WitherMorph;
import vini2003.xyz.withermorph.common.component.WitherComponent;

public class WitherMorphNetworking {
	public static final Identifier TOGGLE = WitherMorph.identifier("toggle");
	
	private static void toggle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		server.execute(() -> {
			WitherComponent component = WitherMorphComponents.WITHER.get(player);
			
			component.setActive(!component.isActive());
			
			WitherMorphComponents.WITHER.sync(player);
			
			if (component.isActive()) {
				player.sendMessage(new TranslatableText("message.withermorph.enabled"), true);
			} else {
				player.sendMessage(new TranslatableText("message.withermorph.disabled"), true);
			}
		});
	}
	
	public static void initialize() {
		ServerPlayNetworking.registerGlobalReceiver(TOGGLE, WitherMorphNetworking::toggle);
	}
}
