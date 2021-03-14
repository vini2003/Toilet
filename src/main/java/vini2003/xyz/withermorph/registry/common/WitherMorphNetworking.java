package vini2003.xyz.withermorph.registry.common;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import vini2003.xyz.withermorph.WitherMorph;
import vini2003.xyz.withermorph.client.dimension.DimensionRefresher;
import vini2003.xyz.withermorph.client.util.ClientUtils;
import vini2003.xyz.withermorph.common.component.WitherComponent;

public class WitherMorphNetworking {
	public static final Identifier TOGGLE = WitherMorph.identifier("toggle");
	public static final Identifier EXPLODE = WitherMorph.identifier("explode");
	public static final Identifier FIRE = WitherMorph.identifier("fire");
	
	private static void toggle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		server.execute(() -> {
			WitherComponent component = WitherMorphComponents.WITHER.get(player);
			
			component.setActive(!component.isActive());
			
			WitherMorphComponents.WITHER.sync(player);
			
			((DimensionRefresher) player).withermorph_refreshDimensions();
		});
	}
	
	private static void explode(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		server.execute(() -> {
			WitherComponent component = WitherMorphComponents.WITHER.get(player);
			
			component.setExplosionTimer(220);
			
			WitherMorphComponents.WITHER.sync(player);
		});
	}
	
	private static void fire(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		server.execute(() -> {
			Vec3d rotation = player.getRotationVector();
			
			WitherSkullEntity witherSkullEntity = new WitherSkullEntity(player.world, player, rotation.getX(), rotation.getY(), rotation.getZ());
			witherSkullEntity.setPos(witherSkullEntity.getX(), witherSkullEntity.getY() + player.getStandingEyeHeight(), witherSkullEntity.getZ());
			witherSkullEntity.setOwner(player);
			
			player.world.spawnEntity(witherSkullEntity);
		});
	}
	
	public static void initialize() {
		ServerPlayNetworking.registerGlobalReceiver(TOGGLE, WitherMorphNetworking::toggle);
		ServerPlayNetworking.registerGlobalReceiver(EXPLODE, WitherMorphNetworking::explode);
		ServerPlayNetworking.registerGlobalReceiver(FIRE, WitherMorphNetworking::fire);
	}
}
