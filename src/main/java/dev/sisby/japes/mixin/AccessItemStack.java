package dev.sisby.japes.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemStack.class)
public interface AccessItemStack {
	@Accessor
	Holder<Item> getItem();

	@Accessor
	PatchedDataComponentMap getComponents();
}
