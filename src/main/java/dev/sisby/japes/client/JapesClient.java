package dev.sisby.japes.client;

import dev.sisby.japes.JapesEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class JapesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRenderers.register(JapesEntityTypes.PAPER_BALL, ThrownItemRenderer::new);
	}
}
