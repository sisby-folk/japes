package dev.sisby.japes;

import dev.sisby.japes.block.BuggyBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class JapesBlocks {
	public static final BuggyBlock BUGGY = register("buggy", BuggyBlock::new, BlockBehaviour.Properties.of());

	private static <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties settings) {
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Japes.ID, name));
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Japes.ID, name));
		T block = blockFactory.apply(settings.setId(blockKey));
		BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
		Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
		Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
		return block;
	}

	public static void initialize() {}
}
