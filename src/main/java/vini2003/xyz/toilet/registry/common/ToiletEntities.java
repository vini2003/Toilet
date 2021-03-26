package vini2003.xyz.toilet.registry.common;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import vini2003.xyz.toilet.common.entity.SeatEntity;

public class ToiletEntities {
	public static final EntityType<SeatEntity> SEAT = Registry.register(
			Registry.ENTITY_TYPE,
			"seat",
			FabricEntityTypeBuilder.<SeatEntity>create(SpawnGroup.MISC, SeatEntity::new).dimensions(EntityDimensions.fixed(0.0F, 0.0F)).build());
	
	public static void initialize() {
	}
}
