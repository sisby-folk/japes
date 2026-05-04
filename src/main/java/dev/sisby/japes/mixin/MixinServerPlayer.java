package dev.sisby.japes.mixin;

import dev.sisby.japes.Japes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer {
	@Inject(method = "tick", at = @At("TAIL"))
	private void checkShoelaces(CallbackInfo ci) {
		ServerPlayer self = (ServerPlayer) (Object) this;
		if (Boolean.TRUE.equals(self.getAttached(Japes.SHOELACES_TIED))) {
			if (self.isSprinting()) {
				self.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 200));
				self.playSound(SoundEvents.BOOK_PUT, 0.5F, 1.0F);
				self.sendOverlayMessage(Component.translatable("message.japes.shoelaces_tied").withStyle(ChatFormatting.LIGHT_PURPLE));
			} else if (self.getKnownMovement().horizontalDistanceSqr() < Mth.square(self.getSpeed()) && self.getXRot() > 89.0F) { // Untie Shoelaces
				self.sendOverlayMessage(Component.translatable("message.japes.shoelaces_untied").withStyle(ChatFormatting.AQUA));
				self.playSound(SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 0.5F, 0.5F);
				self.removeAttached(Japes.SHOELACES_TIED);
			}
		}
	}
}
