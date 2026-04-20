package dev.sisby.japes.component;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public record ScrumpledName(Component name) implements TooltipProvider {
	@Override
	public void addToTooltip(Item.@NonNull TooltipContext tooltip, Consumer<Component> textConsumer, @NonNull TooltipFlag type, @NonNull DataComponentGetter components) {
		String string = this.name.getString();
		textConsumer.accept(Component.translatable("component.japes.scrumpled_name", Component.literal(string.substring(0, Math.min(2, string.length()))).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.DARK_PURPLE));
	}
}
