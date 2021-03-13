package vini2003.xyz.withermorph.registry.client;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;
import vini2003.xyz.withermorph.client.dimension.DimensionRefresher;
import vini2003.xyz.withermorph.client.util.ClientUtils;
import vini2003.xyz.withermorph.common.component.WitherComponent;
import vini2003.xyz.withermorph.registry.common.WitherMorphComponents;
import vini2003.xyz.withermorph.registry.common.WitherMorphNetworking;

public class WitherMorphKeybinds {
	public static final KeyBinding keyToggle = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.withermorph.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "category.withermorph.withermorph"));
	
	public static void initialize() {
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (keyToggle.wasPressed()) {
				WitherComponent component = WitherMorphComponents.WITHER.get(ClientUtils.getPlayer());
				
				component.setActive(!component.isActive());
				
				((DimensionRefresher) ClientUtils.getPlayer()).withermorph_refreshDimensions();
				
				ClientPlayNetworking.send(WitherMorphNetworking.TOGGLE, new PacketByteBuf(Unpooled.buffer()));
			}
		});
	}
}
