package dev.sisby.japes.mixin;

import dev.sisby.japes.Japes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class MixinEntity {
	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	private void tieShoelaces(Player player, InteractionHand hand, Vec3 location, CallbackInfoReturnable<InteractionResult> cir) {
		if (!((Entity) (Object) this instanceof Player p)) return;
		if (location.y() < 0.3F && player.isCrouching()) {
			if (p.getKnownMovement().horizontalDistanceSqr() >= Mth.square(p.getSpeed())) { // Flat Tire!
				p.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100, 1));
				p.playSound(SoundEvents.BOOK_PUT, 0.5F, 1.0F);
				p.sendOverlayMessage(Component.translatable("message.japes.flat_tire").withStyle(ChatFormatting.LIGHT_PURPLE));
			} else { // Shoelaces Tied!
				p.playSound(SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 0.5F, 0.5F);
				p.setAttached(Japes.SHOELACES_TIED, true);
			}
			cir.setReturnValue(InteractionResult.SUCCESS);
		}
	}
}
