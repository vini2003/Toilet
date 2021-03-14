package vini2003.xyz.withermorph.registry.common;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.BossBarManager;
import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.BossBarCommand;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import vini2003.xyz.withermorph.WitherMorph;
import vini2003.xyz.withermorph.client.dimension.DimensionRefresher;
import vini2003.xyz.withermorph.client.util.ClientUtils;
import vini2003.xyz.withermorph.common.component.WitherComponent;

import java.util.*;

public class WitherMorphCommands {
	public static final Map<UUID, BossBar> bossBars = new HashMap<>();
	
	private static int toggle(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		PlayerEntity player = context.getSource().getPlayer();
		
		WitherComponent component = WitherMorphComponents.WITHER.get(ClientUtils.getPlayer());
		
		component.setActive(!component.isActive());
		
		((DimensionRefresher) ClientUtils.getPlayer()).withermorph_refreshDimensions();
		
		ClientPlayNetworking.send(WitherMorphNetworking.TOGGLE, new PacketByteBuf(Unpooled.buffer()));
		
		if (component.isActive()) {
			context.getSource().getPlayer().sendMessage(new TranslatableText("message.withermorph.enabled"), true);
			
			BossBar bossBar = context.getSource().getMinecraftServer().getBossBarManager().add(WitherMorph.identifier(player.getUuidAsString()), player.getDisplayName());
			bossBar.setColor(BossBar.Color.PURPLE);
			
			bossBars.put(player.getUuid(), bossBar);
			
			trackBossBars(context.getSource().getMinecraftServer().getPlayerManager().getPlayerList());
		} else {
			context.getSource().getPlayer().sendMessage(new TranslatableText("message.withermorph.disabled"), true);
			
			context.getSource().getMinecraftServer().getBossBarManager().remove((CommandBossBar) bossBars.get(player.getUuid()));
			
			((CommandBossBar) bossBars.get(player.getUuid())).clearPlayers();
			bossBars.remove(player.getUuid());
			
			trackBossBars(context.getSource().getMinecraftServer().getPlayerManager().getPlayerList());
		}
		
		return 1;
	}
	
	public static void trackBossBars(Collection<ServerPlayerEntity> players) {
		for (BossBar bossBar : bossBars.values()) {
			((CommandBossBar) bossBar).clearPlayers();
			((CommandBossBar) bossBar).addPlayers(players);
		}
	}
	
	public static void initialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			LiteralCommandNode<ServerCommandSource> witherMorphRoot = CommandManager.literal("bodyshuffle").build();
			
			LiteralCommandNode<ServerCommandSource> witherMorphToggle = CommandManager.literal("toggle").executes(WitherMorphCommands::toggle).build();
			
			witherMorphRoot.addChild(witherMorphToggle);
			
			dispatcher.getRoot().addChild(witherMorphRoot);
		});
	}
}
