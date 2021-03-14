package vini2003.xyz.withermorph.registry.client;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;
import vini2003.xyz.withermorph.client.dimension.DimensionRefresher;
import vini2003.xyz.withermorph.client.util.ClientUtils;
import vini2003.xyz.withermorph.common.component.WitherComponent;
import vini2003.xyz.withermorph.registry.common.WitherMorphComponents;
import vini2003.xyz.withermorph.registry.common.WitherMorphNetworking;

public class WitherMorphKeybinds {
	public static final KeyBinding keyExplode = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.withermorph.explode", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "category.withermorph.withermorph"));
	public static final KeyBinding keyFire = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.withermorph.fire", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F, "category.withermorph.withermorph"));
	
	public static void initialize() {
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (keyExplode.wasPressed()) {
				WitherComponent component = WitherMorphComponents.WITHER.get(ClientUtils.getPlayer());
				
				if (component.isActive()) {
					component.setExplosionTimer(220);
				}
			}
		});
		
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (keyFire.wasPressed()) {
				WitherComponent component = WitherMorphComponents.WITHER.get(client.player);
				
				if (component.isActive()) {
					ClientPlayNetworking.send(WitherMorphNetworking.FIRE, new PacketByteBuf(Unpooled.buffer()));
				}
			}
		});
	}
}
