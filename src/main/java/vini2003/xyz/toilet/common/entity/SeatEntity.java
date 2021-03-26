package vini2003.xyz.toilet.common.entity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vini2003.xyz.toilet.common.block.SeatBlock;
import vini2003.xyz.toilet.registry.common.ToiletEntities;
import vini2003.xyz.toilet.registry.common.ToiletNetworking;

public class SeatEntity extends Entity {
	public SeatEntity(EntityType<?> type, World world) {
		super(type, world);
	}
	
	public SeatEntity(World world) {
		super(ToiletEntities.SEAT, world);
	}
	
	private BlockPos seatPos = new BlockPos(getPos());
	
	public void setPos(BlockPos pos) {
		super.updatePosition(pos.getX() + 0.5D, pos.getY() + 0.25D + ((SeatBlock) world.getBlockState(pos).getBlock()).getYOffset(), pos.getZ() + 0.5D);
		
		seatPos = new BlockPos(getPos());
	}
	
	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		player.startRiding(this);
		return ActionResult.SUCCESS;
	}
	
	@Override
	protected void removePassenger(Entity passenger) {
		super.removePassenger(passenger);
		kill();
	}
	
	@Override
	public void kill() {
		removeAllPassengers();
		
		if (!world.isClient) {
			for (ServerPlayerEntity player : PlayerLookup.tracking(this)) {
				ServerPlayNetworking.getSender(player).sendPacket(new EntityPassengersSetS2CPacket(this));
			}
		}
		
		super.kill();
		
		BlockState state = world.getBlockState(seatPos);
		
		if (state.getBlock() instanceof SeatBlock) {
			world.setBlockState(seatPos, state.with(SeatBlock.OCCUPIED, false));
		}
	}
	
	@Override
	protected void initDataTracker() { }
	
	@Override
	protected void readCustomDataFromTag(CompoundTag tag) {
		seatPos = new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
	}
	
	@Override
	protected void writeCustomDataToTag(CompoundTag tag) {
		tag.putInt("X", seatPos.getX());
		tag.putInt("Y", seatPos.getY());
		tag.putInt("Z", seatPos.getZ());
	}
	
	@Override
	public Packet<?> createSpawnPacket() {
		return ToiletNetworking.createEntitySpawnPacket(this);
	}
}
