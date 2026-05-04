package dev.sisby.japes.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class BuggyBlock extends Block {
	public BuggyBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return Shapes.or(Shapes.box(0.25, 0, 0.0625, 0.75, 0.375, 0.9375), Shapes.box(0.25, 0.375, 0.25, 0.75, 0.5, 0.75));
	}

	@Override
	protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull BlockHitResult hitResult) {
		boolean found = false;
		for (Entity entity : level.getEntities(player, AABB.ofSize(pos.getCenter(), 10, 5, 10))) {
			if (entity instanceof Player otherPlayer) {
				otherPlayer.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 2));
				otherPlayer.sendOverlayMessage(Component.translatable("message.japes.buggy").withStyle(ChatFormatting.LIGHT_PURPLE));
				found = true;
			}
		}
		if (found) {
			player.sendOverlayMessage(Component.translatable("message.japes.buggy").withStyle(ChatFormatting.AQUA));
			player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 40, 2));
			level.playSound(player, pos, SoundEvents.ALLAY_THROW, SoundSource.BLOCKS, 2.0F, 1.0F);
		} else {
			player.sendOverlayMessage(Component.translatable("message.japes.buggy.fail").withStyle(ChatFormatting.GRAY));
		}
		return InteractionResult.SUCCESS;
	}
}
