package vini2003.xyz.withermorph.registry.common;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import vini2003.xyz.withermorph.WitherMorph;
import vini2003.xyz.withermorph.common.component.WitherComponent;

public class WitherMorphComponents implements EntityComponentInitializer {
	public static final ComponentKey<WitherComponent> WITHER = ComponentRegistry.getOrCreate(WitherMorph.identifier("wither"), WitherComponent.class);
	
	public static void initialize() {
	
	}
	
	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
		entityComponentFactoryRegistry.registerForPlayers(WITHER, WitherComponent::new);
	}
}
