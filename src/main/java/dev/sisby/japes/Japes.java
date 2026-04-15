package dev.sisby.japes;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Japes implements ModInitializer {
	public static final String ID = "japes";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		JapesItems.initialize();
		LOGGER.info("[Japes!] :̶.̶|̶:̶;̶");
	}
}
