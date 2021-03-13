package vini2003.xyz.withermorph.mixin.common;

import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vini2003.xyz.withermorph.client.dimension.DimensionRefresher;
import vini2003.xyz.withermorph.client.util.ClientUtils;
import vini2003.xyz.withermorph.common.component.WitherComponent;
import vini2003.xyz.withermorph.registry.common.WitherMorphComponents;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements SkinOverlayOwner {
	@Inject(at = @At("HEAD"), method = "getDimensions", cancellable = true)
	void withermorph_getDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
		if (pose == EntityPose.STANDING || pose == EntityPose.CROUCHING) {
			if (WitherMorphComponents.WITHER.isProvidedBy(this)) {
				WitherComponent component = WitherMorphComponents.WITHER.get(this);
				
				if (component.isActive()) {
					cir.setReturnValue(EntityDimensions.changing(0.9F, 3.5F));
					cir.cancel();
				}
			}
		}
	}
	
	@Inject(at = @At("RETURN"), method = "getActiveEyeHeight", cancellable = true)
	void withermorph_getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
		try {
			if (pose == EntityPose.STANDING || pose == EntityPose.CROUCHING) {
				if (WitherMorphComponents.WITHER.isProvidedBy(this)) {
					WitherComponent component = WitherMorphComponents.WITHER.get(this);
					
					if (component.isActive()) {
						cir.setReturnValue(dimensions.height * 0.85F);
						cir.cancel();
					}
				}
			}
		} catch (Exception ignored) {
		
		}
	}
	
	@Inject(at = @At("HEAD"), method = "tick")
	void withermorph_tick(CallbackInfo ci) {
		WitherComponent component = WitherMorphComponents.WITHER.get(this);
		
		if (component.getExplosionTimer() > -1) {
			component.setExplosionTimer(component.getExplosionTimer() - 1);
			
			if (component.getExplosionTimer() == 0) {
				PlayerEntity player = ((PlayerEntity) (Object) this);
				
				player.world.createExplosion(null, player.getX(), player.getEyeY(), player.getZ(), 7.0F, false, Explosion.DestructionType.DESTROY);
				
				player.world.syncGlobalEvent(1023, player.getBlockPos(), 0);
				
				player.move(MovementType.SELF, new Vec3d(0.0D, 0.25D, 0.0D));
			}
		}
	}
	
	@Override
	public boolean shouldRenderOverlay() {
		return ((LivingEntity) (Object) this).getHealth() <= ((LivingEntity) (Object) this).getMaxHealth() / 2.0F;
	}
}
