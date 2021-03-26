package vini2003.xyz.toilet.registry.client;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;
import vini2003.xyz.toilet.registry.common.ToiletNetworking;

public class ToiletKeybinds {
	public static final KeyBinding keyPee = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.toilet.pee", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UP, "category.toilet.toilet"));
	public static final KeyBinding keyPoop = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.toilet.poop", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, "category.toilet.toilet"));

	public static void initialize() {
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (keyPee.wasPressed()) {
				ClientPlayNetworking.send(ToiletNetworking.PEE, PacketByteBufs.create());
			}
			
			if (keyPoop.wasPressed()) {
				ClientPlayNetworking.send(ToiletNetworking.POOP, PacketByteBufs.create());
			}
		});
	}
}
