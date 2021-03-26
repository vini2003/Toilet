package vini2003.xyz.toilet.client.entity.renderer;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Identifier;
import vini2003.xyz.toilet.common.entity.SeatEntity;

public class InvisibleEntityRenderer extends EntityRenderer<SeatEntity> {
	private static final Identifier TEXTURE = new Identifier("missingno");
	
	public static final EntityRendererRegistry.Factory FACTORY = (dispatcher, context) -> new InvisibleEntityRenderer(dispatcher);
	
	public InvisibleEntityRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}
	
	@Override
	public Identifier getTexture(SeatEntity entity) {
		return TEXTURE;
	}
}
