package com.chaosthedude.realistictorches.blocks;

import java.util.function.ToIntFunction;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.registry.RealisticTorchesRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class RealisticTorchBlock extends TorchBlock {

	public static final String NAME = "torch";

	public static final int TICK_INTERVAL = 1200;
	protected static final int INITIAL_BURN_TIME = ConfigHandler.torchBurnoutTime.get();
	protected static final boolean SHOULD_BURN_OUT = INITIAL_BURN_TIME > 0;
	protected static final IntegerProperty BURNTIME = IntegerProperty.create("burntime", 0, SHOULD_BURN_OUT ? INITIAL_BURN_TIME : 1);
	protected static final IntegerProperty LITSTATE = IntegerProperty.create("litstate", 0, 2);

	public static final int LIT = 2;
	public static final int SMOLDERING = 1;
	public static final int UNLIT = 0;

	public RealisticTorchBlock() {
		super(Block.Properties.copy(Blocks.TORCH).lightLevel(getLightValueFromState()), ParticleTypes.FLAME);
		registerDefaultState(stateDefinition.any().setValue(LITSTATE, 0).setValue(BURNTIME, 0));
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (state.getValue(LITSTATE) == LIT || (state.getValue(LITSTATE) == SMOLDERING && level.getRandom().nextInt(2) == 1)) {
			super.animateTick(state, level, pos, random);
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		}
		if (player.getItemInHand(hand).getItem() == Items.FLINT_AND_STEEL || player.getItemInHand(hand).getItem() == RealisticTorchesRegistry.MATCHBOX_ITEM.get()) {
			playLightingSound(level, pos);
			if (!player.isCreative() && (player.getItemInHand(hand).getItem() != RealisticTorchesRegistry.MATCHBOX_ITEM.get() || ConfigHandler.matchboxDurability.get() > 0)) {
				ItemStack heldStack = player.getItemInHand(hand);
				heldStack.hurtAndBreak(1, player, playerEntity -> {
					playerEntity.broadcastBreakEvent(hand);
				});
			}
			if (level.isRainingAt(pos)) {
				playExtinguishSound(level, pos);
			} else {
				changeToLit(level, pos, state);
			}
			return InteractionResult.SUCCESS;
		}
		return super.use(state, level, pos, player, hand, hit);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!level.isClientSide() && SHOULD_BURN_OUT && state.getValue(LITSTATE) > UNLIT) {
			if (level.isRainingAt(pos)) {
				playExtinguishSound(level, pos);
				changeToUnlit(level, pos, state);
				return;
			}
			int newBurnTime = state.getValue(BURNTIME) - 1;
			if (newBurnTime <= 0) {
				playExtinguishSound(level, pos);
				changeToUnlit(level, pos, state);
				level.updateNeighborsAt(pos, this);
			} else if (state.getValue(LITSTATE) == LIT && (newBurnTime <= INITIAL_BURN_TIME / 10 || newBurnTime <= 1)) {
				changeToSmoldering(level, pos, state, newBurnTime);
				level.updateNeighborsAt(pos, this);
			} else {
				level.setBlock(pos, state.setValue(BURNTIME, newBurnTime), 2);
				level.scheduleTick(pos, this, TICK_INTERVAL);
			}
		}
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
		super.setPlacedBy(level, pos, state, entity, stack);
		level.scheduleTick(pos, this, TICK_INTERVAL);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!isMoving && state.getBlock() != newState.getBlock()) {
			defaultBlockState().updateNeighbourShapes(level, pos, 3);
		}
		super.onPlace(state, level, pos, newState, isMoving);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BURNTIME);
		builder.add(LITSTATE);
	}

	public static IntegerProperty getBurnTime() {
		return BURNTIME;
	}

	public static IntegerProperty getLitState() {
		return LITSTATE;
	}

	public static int getInitialBurnTime() {
		return SHOULD_BURN_OUT ? INITIAL_BURN_TIME : 0;
	}

	public void changeToLit(Level Level, BlockPos pos, BlockState state) {
		Level.setBlock(pos, RealisticTorchesRegistry.TORCH_BLOCK.get().defaultBlockState().setValue(LITSTATE, LIT).setValue(BURNTIME, getInitialBurnTime()), 2);
		if (SHOULD_BURN_OUT) {
			Level.scheduleTick(pos, this, TICK_INTERVAL);
		}
	}

	public void changeToSmoldering(Level level, BlockPos pos, BlockState state, int newBurnTime) {
		if (SHOULD_BURN_OUT) {
			level.setBlock(pos, RealisticTorchesRegistry.TORCH_BLOCK.get().defaultBlockState().setValue(LITSTATE, SMOLDERING).setValue(BURNTIME, newBurnTime), 2);
			level.scheduleTick(pos, this, TICK_INTERVAL);
		}
	}

	public void changeToUnlit(Level level, BlockPos pos, BlockState state) {
		if (SHOULD_BURN_OUT) {
			if (ConfigHandler.noRelightEnabled.get()) {
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
			} else {
				level.setBlock(pos, RealisticTorchesRegistry.TORCH_BLOCK.get().defaultBlockState(), 2);
				level.scheduleTick(pos, this, TICK_INTERVAL);
			}
		}
	}

	public void playLightingSound(Level level, BlockPos pos) {
		level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
	}

	public void playExtinguishSound(Level level, BlockPos pos) {
		level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
	}

	private static ToIntFunction<BlockState> getLightValueFromState() {
		return (state) -> {
			if (state.getValue(RealisticTorchBlock.LITSTATE) == RealisticTorchBlock.LIT) {
				return 14;
			} else if (state.getValue(RealisticTorchBlock.LITSTATE) == RealisticTorchBlock.SMOLDERING) {
				return 12;
			}
			return 0;
		};
	}

}
