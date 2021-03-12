package vini2003.xyz.withermorph.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class ClientUtils {
	public static PlayerEntity getPlayer() {
		return MinecraftClient.getInstance().player;
	}
}
