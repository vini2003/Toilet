package vini2003.xyz.toilet.registry.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import vini2003.xyz.toilet.Toilet;

public class ToiletNetworking {
	public static final Identifier SPAWN_ENTITY = Toilet.identifier("spawn_entity");

	private static void spawnEntity(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		EntitySpawnS2CPacket packet = new EntitySpawnS2CPacket();
		try {
			packet.read(buf);
		} catch (Exception ignore) {
		}
		
		client.execute(() -> {
			World world = client.world;
			Entity entity = packet.getEntityTypeId().create(world);
			
			entity.setEntityId(packet.getId());
			entity.setUuid(packet.getUuid());
			entity.updatePositionAndAngles(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw() * 360F / 256F, packet.getPitch() * 360F / 256F);
			entity.updateTrackedPosition(packet.getX(), packet.getY(), packet.getZ());
			
			((ClientWorld) world).addEntity(packet.getId(), entity);
		});
	}
	
	public static void initialize() {
		ClientPlayNetworking.registerGlobalReceiver(SPAWN_ENTITY, ToiletNetworking::spawnEntity);
	}
}
