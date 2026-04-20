package dev.sisby.japes;

import dev.sisby.japes.component.ScrumpledName;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.Identifier;

public class JapesComponents {
	public static final DataComponentType<ScrumpledName> SCRUMPLED_NAME = Registry.register(
		BuiltInRegistries.DATA_COMPONENT_TYPE,
		Identifier.fromNamespaceAndPath(Japes.ID, "scrumpled_name"),
		DataComponentType.<ScrumpledName>builder().persistent(ComponentSerialization.CODEC.xmap(ScrumpledName::new, ScrumpledName::name)).networkSynchronized(ComponentSerialization.STREAM_CODEC.map(ScrumpledName::new, ScrumpledName::name)).cacheEncoding().build()
	);


	protected static void initialize() {
		ItemComponentTooltipProviderRegistry.addAfter(DataComponents.DAMAGE, SCRUMPLED_NAME);
	}
}
