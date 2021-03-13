package vini2003.xyz.withermorph.mixin.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import vini2003.xyz.withermorph.client.dimension.DimensionRefresher;

@Mixin(Entity.class)
public abstract class EntityMixin implements DimensionRefresher {
	@Shadow private EntityDimensions dimensions;
	
	@Shadow public abstract EntityPose getPose();
	
	@Shadow public abstract EntityDimensions getDimensions(EntityPose pose);
	
	@Shadow private float standingEyeHeight;
	
	@Shadow public abstract float getEyeHeight(EntityPose pose);
	
	@Shadow public abstract Box getBoundingBox();
	
	@Shadow public abstract void setBoundingBox(Box boundingBox);
	
	@Shadow public abstract void move(MovementType type, Vec3d movement);
	
	@Shadow protected boolean firstUpdate;
	
	@Shadow protected abstract float getEyeHeight(EntityPose pose, EntityDimensions dimensions);
	
	@Override
	public void withermorph_refreshDimensions() {
		EntityDimensions currentDimensions = dimensions;
		EntityPose newPose = getPose();
		EntityDimensions newDimensions = getDimensions(newPose);
		
		dimensions = newDimensions;
		standingEyeHeight = getEyeHeight(newPose, newDimensions);
		
		Box box = getBoundingBox();
		setBoundingBox(new Box(box.minX, box.minY, box.minZ, box.minX + newDimensions.width, box.minY + newDimensions.height, box.minZ + newDimensions.width));
		
		if(!firstUpdate) {
			float distance = currentDimensions.width - newDimensions.width;
			move(MovementType.SELF, new Vec3d(distance, 0, distance));
		}
	}
}
