package dev.sisby.japes.item;

import dev.sisby.japes.entity.PaperBall;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class PaperBallItem extends Item implements ProjectileItem {
	public PaperBallItem(final Item.Properties properties) {
		super(properties);
	}

	@Override
	public @NonNull InteractionResult use(final @NonNull Level level, final Player player, final @NonNull InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (player.isCrouching()) { // unfold
			return super.use(level, player, hand);
		} else { // throw
			level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WITCH_THROW, SoundSource.NEUTRAL, 0.5F, 0.6F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
			if (level instanceof ServerLevel serverLevel) {
				Projectile.spawnProjectileFromRotation(PaperBall::new, serverLevel, itemStack, player, 0.0F, 1.0F, 1.0F);
			}
			itemStack.consume(1, player);
		}
		player.awardStat(Stats.ITEM_USED.get(this));
		return InteractionResult.SUCCESS;
	}

	@Override
	public @NonNull InteractionResult interactLivingEntity(@NonNull ItemStack itemStack, @NonNull Player player, @NonNull LivingEntity target, @NonNull InteractionHand type) {
		return use(player.level(), player, type);
	}

	@Override
	public @NonNull Projectile asProjectile(final @NonNull Level level, final Position position, final @NonNull ItemStack itemStack, final @NonNull Direction direction) {
		return new PaperBall(level, position.x(), position.y(), position.z(), itemStack);
	}
}
