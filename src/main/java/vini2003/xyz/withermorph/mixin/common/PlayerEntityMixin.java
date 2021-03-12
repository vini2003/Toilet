package vini2003.xyz.withermorph.mixin.common;

import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements SkinOverlayOwner {
	@Override
	public boolean shouldRenderOverlay() {
		return ((LivingEntity) (Object) this).getHealth() <= ((LivingEntity) (Object) this).getMaxHealth() / 2.0F;
	}
}
