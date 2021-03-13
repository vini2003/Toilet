package vini2003.xyz.withermorph.mixin.common;

import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vini2003.xyz.withermorph.client.dimension.DimensionRefresher;
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
	
	@Override
	public boolean shouldRenderOverlay() {
		return ((LivingEntity) (Object) this).getHealth() <= ((LivingEntity) (Object) this).getMaxHealth() / 2.0F;
	}
	
	
}
