package dev.sisby.japes.client;

import dev.sisby.japes.JapesEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.network.chat.Component;

import java.util.Locale;
import java.util.regex.Pattern;

public class JapesClient implements ClientModInitializer {
	Pattern GAME_PATTERN = Pattern.compile("the[^a-z0-9]+game");

	@Override
	public void onInitializeClient() {
		EntityRenderers.register(JapesEntityTypes.PAPER_BALL, ThrownItemRenderer::new);
		ClientReceiveMessageEvents.CHAT.register(((_, playerChatMessage, _, _, _) -> {
			if (playerChatMessage != null && GAME_PATTERN.matcher(playerChatMessage.decoratedContent().getString().toLowerCase(Locale.ROOT)).find()) {
				Minecraft.getInstance().getChatListener().handleOverlay(Component.translatable("message.japes.lost_the_game").withStyle(ChatFormatting.LIGHT_PURPLE));
			}
		}));
	}
}
