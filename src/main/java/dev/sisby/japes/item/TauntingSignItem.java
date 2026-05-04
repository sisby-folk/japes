package dev.sisby.japes.item;

import eu.pb4.trinkets.api.TrinketAttachment;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketsApi;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import eu.pb4.trinkets.impl.TrinketSlot;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.function.BiConsumer;

public class TauntingSignItem extends Item implements TrinketCallback {
	public TauntingSignItem(Properties properties) {
		super(properties);
	}

	@Override
	public void forEachTrinketModifier(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity, Identifier id, BiConsumer<Holder<Attribute>, AttributeModifier> consumer) {
		consumer.accept(Attributes.ARMOR, new AttributeModifier(id.withSuffix("japes/broken_armor"), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
	}

	@Override
	public @NonNull InteractionResult interactLivingEntity(@NonNull ItemStack stack, @NonNull Player player, @NonNull LivingEntity target, @NonNull InteractionHand type) {
		int count = stack.count();
		TrinketAttachment attachment = TrinketsApi.getAttachment(target);
		attachment.forEach(slot -> {
			if (count == stack.count() && slot.get().isEmpty() && TrinketSlot.canInsert(stack, slot, target)) {
				slot.set(stack.split(1));
			}
		});
		if (count != stack.count()) return InteractionResult.SUCCESS.heldItemTransformedTo(stack);
		return super.interactLivingEntity(stack, player, target, type);
	}
}
