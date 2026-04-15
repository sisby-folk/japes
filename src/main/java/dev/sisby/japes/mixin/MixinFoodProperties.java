package dev.sisby.japes.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.sisby.japes.JapesItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoodProperties.class)
public class MixinFoodProperties {
	@WrapWithCondition(method = "onConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
	private boolean applyPaperBallEffects(Level instance, Entity except, double x, double y, double z, SoundEvent sound, SoundSource source, float volume, float pitch, Level level, LivingEntity user, ItemStack stack, Consumable consumable) {
		if (stack.is(Items.PAPER)) {
			if (user instanceof Player p) p.getCooldowns().addCooldown(JapesItems.PAPER_BALL.getDefaultInstance(), 20);
			return false;
		}
		return true;
	}
}
