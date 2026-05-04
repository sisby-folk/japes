package dev.sisby.japes;

import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Japes implements ModInitializer {
	public static final String ID = "japes";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static final AttachmentType<Boolean> SHOELACES_TIED = AttachmentRegistry.createPersistent(Identifier.fromNamespaceAndPath(Japes.ID, "shoelaces_tied"), Codec.BOOL);

	@Override
	public void onInitialize() {
		JapesComponents.initialize();
		JapesEntityTypes.initialize();
		JapesItems.initialize();
		LOGGER.info("[Japes!] :̶.̶|̶:̶;̶");
	}
}
