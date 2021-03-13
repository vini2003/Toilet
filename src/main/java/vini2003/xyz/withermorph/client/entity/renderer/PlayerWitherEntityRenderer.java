package vini2003.xyz.withermorph.client.entity.renderer;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import vini2003.xyz.withermorph.client.entity.model.PlayerWitherEntityModel;
import vini2003.xyz.withermorph.client.entity.renderer.feature.PlayerWitherArmorFeatureRenderer;
import vini2003.xyz.withermorph.client.util.ClientUtils;
import vini2003.xyz.withermorph.common.component.WitherComponent;
import vini2003.xyz.withermorph.registry.common.WitherMorphComponents;

public class PlayerWitherEntityRenderer
		extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerWitherEntityModel<AbstractClientPlayerEntity>>implements FeatureRendererContext<AbstractClientPlayerEntity, PlayerWitherEntityModel<AbstractClientPlayerEntity>> {
	private static final Identifier INVULNERABLE_TEXTURE = new Identifier("textures/entity/wither/wither_invulnerable.png");
	private static final Identifier TEXTURE = new Identifier("textures/entity/wither/wither.png");
	
	private PlayerEntity player;
	
	public PlayerWitherEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, PlayerEntity player) {
		super(entityRenderDispatcher, new PlayerWitherEntityModel(0.0F), 1.0F);
		
		PlayerWitherArmorFeatureRenderer armorFeatureRenderer = new PlayerWitherArmorFeatureRenderer(this);
		armorFeatureRenderer.setShouldRender(() -> {
			return WitherMorphComponents.WITHER.get(ClientUtils.getPlayer()).getExplosionTimer() > 100;
		});
		
		this.addFeature(armorFeatureRenderer);
		
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
		matrixStack.scale(2.0F, 2.0F, 2.0F);
	}
}