package vini2003.xyz.withermorph.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class BoundWitherEntity extends WitherEntity {
	private final PlayerEntity player;
	
	public BoundWitherEntity(EntityType<? extends WitherEntity> entityType, World world, PlayerEntity player) {
		super(entityType, world);
		
		this.player = player;
	}
	
	
}
