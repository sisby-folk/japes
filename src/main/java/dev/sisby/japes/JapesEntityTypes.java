package dev.sisby.japes;

import dev.sisby.japes.entity.PaperBall;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class JapesEntityTypes {
	public static final EntityType<PaperBall> PAPER_BALL = register("paper_ball", EntityType.Builder.<PaperBall>of(PaperBall::new, MobCategory.MISC)
		.noLootTable().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));

	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Japes.ID, name));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
	}

	public static void initialize() {
	}
}
