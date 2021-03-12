package vini2003.xyz.withermorph.mixin.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vini2003.xyz.withermorph.client.entity.renderer.WitherMorphEntityRenderer;
import vini2003.xyz.withermorph.client.util.ClientUtils;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
	@Shadow protected abstract void setModelPose(AbstractClientPlayerEntity abstractClientPlayerEntity);
	
	WitherMorphEntityRenderer withermorph_renderer;
	
	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V")
	void withermorph_init(EntityRenderDispatcher dispatcher, boolean bl, CallbackInfo ci) {
		withermorph_renderer = new WitherMorphEntityRenderer(dispatcher, ClientUtils.getPlayer());
	}
	
	@Inject(at = @At("HEAD"), method = "render", cancellable = true)
	void withermorph_render(AbstractClientPlayerEntity player, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
		this.setModelPose(player);
		
		withermorph_renderer.render(player, f, g, matrixStack, vertexConsumerProvider, i);
		
		ci.cancel();
	}
}
