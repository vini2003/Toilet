package vini2003.xyz.toilet.registry.common;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vini2003.xyz.toilet.Toilet;
import vini2003.xyz.toilet.common.block.ToiletBlock;
import vini2003.xyz.toilet.common.util.ToiletUtils;

public class ToiletNetworking {
	public static final Identifier PEE = Toilet.identifier("pee");
	public static final Identifier POOP = Toilet.identifier("poop");
	public static final Identifier SPAWN_ENTITY = Toilet.identifier("spawn_entity");
	
	public static Packet<?> createEntitySpawnPacket(Entity entity) {
		PacketByteBuf buf = PacketByteBufs.create();
		
		try {
			new EntitySpawnS2CPacket(entity).write(buf);
		} catch (Exception ignored) {}
		
		return ServerPlayNetworking.createS2CPacket(SPAWN_ENTITY, buf);
	}
	
	private static void pee(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		World world = player.world;
		
		BlockPos pos = ToiletUtils.nearest(world, player.getBlockPos());
		
		if (pos != null) {
			BlockState state = world.getBlockState(pos);
			
			world.setBlockState(pos, state.with(ToiletBlock.PEE, true));
			
			player.getHungerManager().add(-2, 0F);
		}
	}
	
	private static void poop(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		World world = player.world;
		
		BlockPos pos = ToiletUtils.nearest(world, player.getBlockPos());
		
		if (pos != null) {
			BlockState state = world.getBlockState(pos);
			
			world.setBlockState(pos, state.with(ToiletBlock.POOP, true));
			
			if (world.random.nextInt(3) == 0) {
				world.setBlockState(pos, world.getBlockState(pos).with(ToiletBlock.CLOGGED, true));
			}
			
			player.getHungerManager().add(-4, 0F);
		}
	}
	
	public static void initialize() {
		ServerPlayNetworking.registerGlobalReceiver(PEE, ToiletNetworking::pee);
		ServerPlayNetworking.registerGlobalReceiver(POOP, ToiletNetworking::poop);
	}
}
