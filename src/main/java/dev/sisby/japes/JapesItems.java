package dev.sisby.japes;

import dev.sisby.japes.item.PaperBallItem;
import dev.sisby.japes.item.TauntingSignItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.UseEffects;

import java.util.function.Function;

public class JapesItems {
	public static final PaperBallItem PAPER_BALL = register("paper_ball", PaperBallItem::new, new Item.Properties().stacksTo(8)
		.useCooldown(0.1F)
		.usingConvertsTo(Items.STONE) // replaced with paper by mixin to avoid cyclic convert
		.food(new FoodProperties(0, 0, true), Consumable.builder().animation(ItemUseAnimation.TOOT_HORN).sound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.BOOK_PAGE_TURN)).soundAfterConsume(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ITEM_PICKUP)).consumeSeconds(0.1F).build())
		.component(DataComponents.USE_EFFECTS, new UseEffects(true, false, 0.8F))
	);

	public static final TauntingSignItem TAUNTING_SIGN = register("taunting_sign", TauntingSignItem::new, new Item.Properties().stacksTo(1));

	public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Japes.ID, name));
		T item = itemFactory.apply(settings.setId(itemKey));
		Registry.register(BuiltInRegistries.ITEM, itemKey, item);
		return item;
	}

	public static void initialize() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(tab -> tab.insertBefore(Items.SNOWBALL, PAPER_BALL));
	}
}
