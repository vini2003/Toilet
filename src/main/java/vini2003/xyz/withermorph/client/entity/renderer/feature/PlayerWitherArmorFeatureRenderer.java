package vini2003.xyz.withermorph.client.entity.renderer.feature;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import vini2003.xyz.withermorph.client.entity.BoundEnergySwirlOverlayFeatureRenderer;
import vini2003.xyz.withermorph.client.entity.model.PlayerWitherEntityModel;

public class PlayerWitherArmorFeatureRenderer extends BoundEnergySwirlOverlayFeatureRenderer<AbstractClientPlayerEntity, PlayerWitherEntityModel<AbstractClientPlayerEntity>> {
	private static final Identifier SKIN = new Identifier("textures/entity/wither/wither_armor.png");
	private final PlayerWitherEntityModel<AbstractClientPlayerEntity> model = new PlayerWitherEntityModel(0.5F);
	
	public PlayerWitherArmorFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerWitherEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
		super(featureRendererContext);
	}
	
	protected float getEnergySwirlX(float partialAge) {
		return MathHelper.cos(partialAge * 0.02F) * 3.0F;
	}
	
	protected Identifier getEnergySwirlTexture() {
		return SKIN;
	}
	
	@Override
	protected EntityModel<AbstractClientPlayerEntity> getEnergySwirlModel() {
		return this.model;
	}
}
