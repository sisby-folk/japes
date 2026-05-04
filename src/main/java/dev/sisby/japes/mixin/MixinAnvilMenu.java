package dev.sisby.japes.mixin;

import dev.sisby.japes.JapesItems;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Locale;

@Mixin(AnvilMenu.class)
public class MixinAnvilMenu {
	@ModifyArg(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V", ordinal = 4), index = 1)
	private ItemStack tauntingSignCraft(ItemStack stack) {
		if (stack.is(Items.PAPER) && "kick me".equals(stack.getHoverName().getString().toLowerCase(Locale.ROOT))) {
			return JapesItems.TAUNTING_SIGN.getDefaultInstance().copy();
		}
		return stack;
	}
}
