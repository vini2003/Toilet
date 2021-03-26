package vini2003.xyz.toilet.registry.client;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import vini2003.xyz.toilet.client.entity.renderer.InvisibleEntityRenderer;

import static vini2003.xyz.toilet.registry.common.ToiletEntities.SEAT;

public class ToiletEntities {
	public static void initialize() {
		EntityRendererRegistry.INSTANCE.register(SEAT, InvisibleEntityRenderer.FACTORY);
	}
}
