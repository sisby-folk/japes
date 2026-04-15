package dev.sisby.japes.mixin;

import dev.sisby.japes.JapesItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.UseEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Items.class)
public class MixinItems {
	@ModifyArgs(method = "registerItem(Ljava/lang/String;)Lnet/minecraft/world/item/Item;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;"))
	private static void convertToPaperToBall(Args args, String name) {
		if ("paper".equals(name)) args.set(2, ((Item.Properties) args.get(2)).usingConvertsTo(JapesItems.PAPER_BALL).food(new FoodProperties(0, 0, true), Consumable.builder().animation(ItemUseAnimation.TOOT_HORN).sound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.BOOK_PUT)).soundAfterConsume(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.BOOK_PAGE_TURN)).consumeSeconds(0.3F).build()).component(DataComponents.USE_EFFECTS, new UseEffects(true, false, 0.8F)));
	}
}
