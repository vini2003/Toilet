package vini2003.xyz.withermorph.common.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

public class WitherComponent implements PlayerComponent<WitherComponent>, AutoSyncedComponent {
	private int explosionTimer = 0;
	
	private boolean active = false;
	
	private PlayerEntity player;
	
	public WitherComponent(PlayerEntity player) {
		this.player = player;
	}
	
	@Override
	public void readFromNbt(CompoundTag compoundTag) {
		this.explosionTimer = compoundTag.getInt("ExplosionTimer");
		this.active = compoundTag.getBoolean("Active");
	}
	
	@Override
	public void writeToNbt(CompoundTag compoundTag) {
		compoundTag.putInt("ExplosionTimer", explosionTimer);
		compoundTag.putBoolean("Active", active);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getExplosionTimer() {
		return explosionTimer;
	}
	
	public void setExplosionTimer(int explosionTimer) {
		this.explosionTimer = explosionTimer;
	}
}
