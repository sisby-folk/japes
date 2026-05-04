package dev.sisby.japes;

import dev.sisby.japes.item.PaperBallItem;
import dev.sisby.japes.item.TauntingSignItem;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.UseEffects;

import java.util.List;
import java.util.function.Function;

public class JapesItems {
	public static final PaperBallItem PAPER_BALL = register("paper_ball", PaperBallItem::new, new Item.Properties().stacksTo(8)
		.useCooldown(0.1F)
		.usingConvertsTo(Items.STONE) // replaced with paper by mixin to avoid cyclic convert
		.food(new FoodProperties(0, 0, true), Consumable.builder().animation(ItemUseAnimation.TOOT_HORN).sound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.BOOK_PAGE_TURN)).soundAfterConsume(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ITEM_PICKUP)).consumeSeconds(0.1F).build())
		.component(DataComponents.USE_EFFECTS, new UseEffects(true, false, 0.8F))
	);

	public static final TauntingSignItem TAUNTING_SIGN = register("taunting_sign", TauntingSignItem::new, new Item.Properties().stacksTo(1));

	public static final Identifier SKYHOOK = Identifier.fromNamespaceAndPath(Japes.ID, "skyhook");

	public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Japes.ID, name));
		T item = itemFactory.apply(settings.setId(itemKey));
		Registry.register(BuiltInRegistries.ITEM, itemKey, item);
		return item;
	}

	public static final ResourceKey<CreativeModeTab> CUSTOM_CREATIVE_TAB_KEY = ResourceKey.create(
		BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(Japes.ID, "creative_tab")
	);

	public static final CreativeModeTab CUSTOM_CREATIVE_TAB = FabricCreativeModeTab.builder()
		.icon(() -> TAUNTING_SIGN.getDefaultInstance().copy())
		.title(Component.translatable("creativeTab.japes"))
		.displayItems((_, output) -> {
			output.accept(PAPER_BALL);
			output.accept(TAUNTING_SIGN);
			ItemStack skyhook = Items.LEAD.getDefaultInstance().copy();
			skyhook.set(DataComponents.ITEM_NAME, Component.translatable("item.japes.skyhook"));
			skyhook.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath(Japes.ID, "skyhook"));
			skyhook.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(List.of(), List.of(), List.of("skyhook"), List.of()));
			output.accept(skyhook);
		})
		.build();

	public static void initialize() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_CREATIVE_TAB_KEY, CUSTOM_CREATIVE_TAB);
	}
}
