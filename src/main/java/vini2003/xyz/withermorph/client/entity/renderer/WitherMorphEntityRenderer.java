package vini2003.xyz.withermorph.client.entity.renderer;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.WitherEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.WitherArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.model.WitherEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import vini2003.xyz.withermorph.client.entity.model.WitherMorphEntityModel;
import vini2003.xyz.withermorph.client.entity.renderer.feature.WitherMorphArmorFeatureRenderer;
import vini2003.xyz.withermorph.common.component.WitherComponent;
import vini2003.xyz.withermorph.registry.common.WitherMorphComponents;

public class WitherMorphEntityRenderer
		extends LivingEntityRenderer<AbstractClientPlayerEntity, WitherMorphEntityModel<AbstractClientPlayerEntity>>implements FeatureRendererContext<AbstractClientPlayerEntity, WitherMorphEntityModel<AbstractClientPlayerEntity>> {
	private static final Identifier INVULNERABLE_TEXTURE = new Identifier("textures/entity/wither/wither_invulnerable.png");
	private static final Identifier TEXTURE = new Identifier("textures/entity/wither/wither.png");
	
	private PlayerEntity player;
	
	public WitherMorphEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, PlayerEntity player) {
		super(entityRenderDispatcher, new WitherMorphEntityModel(0.0F), 1.0F);
		this.addFeature(new WitherMorphArmorFeatureRenderer(this));
		
		this.player = player;
	}
	
	protected int getBlockLight(AbstractClientPlayerEntity witherEntity, BlockPos blockPos) {
		return 15;
	}
	
	public Identifier getTexture(AbstractClientPlayerEntity witherEntity) {
		WitherComponent component = WitherMorphComponents.WITHER.get(witherEntity);
		
		int i = component.getExplosionTimer();
		
		return i > 0 && (i > 80 || i / 5 % 2 != 1) ? INVULNERABLE_TEXTURE : TEXTURE;
	}
	
	protected void scale(AbstractClientPlayerEntity witherEntity, MatrixStack matrixStack, float f) {
		float g = 2.0F;
		
		WitherComponent component = WitherMorphComponents.WITHER.get(witherEntity);
		
		int i = component.getExplosionTimer();
		
		if (i > 0) {
			g -= ((float)i - f) / 220.0F * 0.5F;
		}
		
		matrixStack.scale(g, g, g);
	}
}