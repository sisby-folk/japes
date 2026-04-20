package dev.sisby.japes.entity;

import dev.sisby.japes.JapesEntityTypes;
import dev.sisby.japes.JapesItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.NonNull;

public class PaperBall extends ThrowableItemProjectile {
	public PaperBall(final EntityType<? extends PaperBall> type, final Level level) {
		super(type, level);
	}

	public PaperBall(final Level level, final LivingEntity mob, final ItemStack itemStack) {
		super(JapesEntityTypes.PAPER_BALL, mob, level, itemStack);
	}

	public PaperBall(final Level level, final double x, final double y, final double z, final ItemStack itemStack) {
		super(JapesEntityTypes.PAPER_BALL, x, y, z, level, itemStack);
	}

	@Override
	protected @NonNull Item getDefaultItem() {
		return JapesItems.PAPER_BALL;
	}

	private ParticleOptions getParticle() {
		ItemStack item = this.getItem();
		return item.isEmpty() ? ParticleTypes.ITEM_COBWEB : new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(item));
	}

	@Override
	public void handleEntityEvent(final byte id) {
		if (id == EntityEvent.DEATH) {
			ParticleOptions particle = this.getParticle();
			for (int i = 0; i < 8; i++) {
				this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
			}
		}
	}

	@Override
	protected void onHitBlock(@NonNull BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		this.playSound(SoundEvents.BOOK_PUT, 1.0F, 2.0F);
	}

	@Override
	protected void onHitEntity(final @NonNull EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		Entity entity = hitResult.getEntity();
		if (entity.isInvulnerable() || entity instanceof ArmorStand || !(entity instanceof LivingEntity)) this.playSound(SoundEvents.BOOK_PUT, 1.0F, 2.0F);
		if (entity instanceof Player p && (p.getMainHandItem().isEmpty() || p.getOffhandItem().isEmpty())) { // catch!
			p.setItemInHand(p.getMainHandItem().isEmpty() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, this.getItem());
			this.playSound(SoundEvents.BOOK_PUT, 1.0F, 2.0F);
			setItem(ItemStack.EMPTY);
		} else { // hit, will drop
			entity.hurt(this.damageSources().thrown(this, this.getOwner()), 0);
		}
	}

	@Override
	protected void onHit(final @NonNull HitResult hitResult) {
		super.onHit(hitResult);
		if (this.level() instanceof ServerLevel sl) {
			sl.broadcastEntityEvent(this, EntityEvent.DEATH);
			if (!this.getItem().isEmpty()) sl.addFreshEntity(new ItemEntity(sl, hitResult.getLocation().x(), hitResult.getLocation().y(), hitResult.getLocation().z(), this.getItem()));
			this.discard();
		}
	}
}
