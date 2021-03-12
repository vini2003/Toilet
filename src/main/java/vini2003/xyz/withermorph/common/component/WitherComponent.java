package vini2003.xyz.withermorph.common.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

public class WitherComponent implements PlayerComponent<WitherComponent> {
	private int explosionTimer = 0;
	
	private PlayerEntity player;
	
	public WitherComponent(PlayerEntity player) {
		this.player = player;
	}
	
	@Override
	public void readFromNbt(CompoundTag compoundTag) {
		this.explosionTimer = compoundTag.getInt("ExplosionTimer");
	}
	
	@Override
	public void writeToNbt(CompoundTag compoundTag) {
		compoundTag.putInt("ExplosionTimer", explosionTimer);
	}
	
	public int getExplosionTimer() {
		return explosionTimer;
	}
	
	public void setExplosionTimer(int explosionTimer) {
		this.explosionTimer = explosionTimer;
	}
}
