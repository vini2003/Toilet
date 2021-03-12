package vini2003.xyz.withermorph.client.entity.renderer.feature;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.WitherEntityModel;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import vini2003.xyz.withermorph.client.entity.BoundEnergySwirlOverlayFeatureRenderer;
import vini2003.xyz.withermorph.client.entity.model.WitherMorphEntityModel;

public class WitherMorphArmorFeatureRenderer extends BoundEnergySwirlOverlayFeatureRenderer<AbstractClientPlayerEntity, WitherMorphEntityModel<AbstractClientPlayerEntity>> {
	private static final Identifier SKIN = new Identifier("textures/entity/wither/wither_armor.png");
	private final WitherMorphEntityModel<AbstractClientPlayerEntity> model = new WitherMorphEntityModel(0.5F);
	
	public WitherMorphArmorFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, WitherMorphEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
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
