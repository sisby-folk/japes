package dev.sisby.japes.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.sisby.japes.JapesComponents;
import dev.sisby.japes.component.ScrumpledName;
import dev.sisby.japes.item.PaperBallItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.UseRemainder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(UseRemainder.class)
public class MixinUseRemainder {
	@ModifyExpressionValue(method = "convertIntoRemainder", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStackTemplate;create()Lnet/minecraft/world/item/ItemStack;"))
	private ItemStack scrumplePaperName(ItemStack convertedStack, final ItemStack usedStack, int stackCountBeforeUsing, boolean hasInfiniteMaterials, UseRemainder.OnExtraCreatedRemainder onExtraCreatedRemainder) {
		if (convertedStack.getItem() instanceof PaperBallItem) {
			Component name = ((AccessItemStack) (Object) usedStack).getComponents().get(DataComponents.CUSTOM_NAME);
			if (name != null) convertedStack.set(JapesComponents.SCRUMPLED_NAME, new ScrumpledName(name)); // keep name but scrumple it
		} else if (((AccessItemStack) (Object) usedStack).getItem().value() instanceof PaperBallItem) { // fix cyclic conversion
			ItemStack fixedStack = Items.PAPER.getDefaultInstance().copy();
			ScrumpledName scrumpled = ((AccessItemStack) (Object) usedStack).getComponents().get(JapesComponents.SCRUMPLED_NAME);
			if (scrumpled != null) {
				fixedStack.set(DataComponents.CUSTOM_NAME, scrumpled.name()); // unscrumple
			}
			return fixedStack;
		}
		return convertedStack;
	}
}
