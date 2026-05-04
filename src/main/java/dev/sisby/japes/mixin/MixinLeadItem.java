package dev.sisby.japes.mixin;

import dev.sisby.japes.Japes;
import dev.sisby.japes.JapesItems;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.LeadItem;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeadItem.class)
public class MixinLeadItem implements FabricItem {
	@Override
	public @NonNull String getCreatorNamespace(@NonNull ItemStack stack) {
		if (JapesItems.SKYHOOK.equals(stack.get(DataComponents.ITEM_MODEL))) return Japes.ID;
		return FabricItem.super.getCreatorNamespace(stack);
	}
}
