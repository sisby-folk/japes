package dev.sisby.japes;

import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.regex.Pattern;

public class Japes implements ModInitializer {
	public static final String ID = "japes";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	Pattern GAME_PATTERN = Pattern.compile("the[^a-z0-9]+game");

	public static final AttachmentType<Boolean> SHOELACES_TIED = AttachmentRegistry.createPersistent(Identifier.fromNamespaceAndPath(Japes.ID, "shoelaces_tied"), Codec.BOOL);

	@Override
	public void onInitialize() {
		JapesComponents.initialize();
		JapesEntityTypes.initialize();
		JapesBlocks.initialize();
		JapesItems.initialize();
		ServerMessageEvents.CHAT_MESSAGE.register((message, player, _) -> {
			if (GAME_PATTERN.matcher(message.signedContent().toLowerCase(Locale.ROOT)).find()) {
				player.createCommandSourceStack().getServer().getPlayerList().broadcastSystemMessage(Component.translatable("message.japes.lost_the_game").withStyle(ChatFormatting.LIGHT_PURPLE), true);
			}
		});
		LOGGER.info("[Japes!] :̶.̶|̶:̶;̶");
	}
}
