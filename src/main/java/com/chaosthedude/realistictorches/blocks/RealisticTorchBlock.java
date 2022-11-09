package com.chaosthedude.realistictorches.blocks;

import java.util.Random;
import java.util.function.ToIntFunction;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.items.RealisticTorchesItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.getValue(LITSTATE) == LIT || (state.getValue(LITSTATE) == SMOLDERING && world.getRandom().nextInt(2) == 1)) {
			super.animateTick(state, world, pos, random);
		}
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if (world.isClientSide()) {
			return ActionResultType.SUCCESS;
		}
		if (player.getItemInHand(hand).getItem() == Items.FLINT_AND_STEEL || player.getItemInHand(hand).getItem() == RealisticTorchesItems.MATCHBOX) {
			playLightingSound(world, pos);
			if (!player.isCreative() && (player.getItemInHand(hand).getItem() != RealisticTorchesItems.MATCHBOX || ConfigHandler.matchboxDurability.get() > 0)) {
				ItemStack heldStack = player.getItemInHand(hand);
				heldStack.hurtAndBreak(1, player, playerEntity -> {
					playerEntity.broadcastBreakEvent(hand);
				});
			}
			if (world.isRainingAt(pos)) {
				playExtinguishSound(world, pos);
			} else {
				changeToLit(world, pos, state);
			}
			return ActionResultType.SUCCESS;
		}
		return super.use(state, world, pos, player, hand, hit);
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!world.isClientSide() && SHOULD_BURN_OUT && state.getValue(LITSTATE) > UNLIT) {
			if (world.isRainingAt(pos)) {
				playExtinguishSound(world, pos);
				changeToUnlit(world, pos, state);
				return;
			}
			int newBurnTime = state.getValue(BURNTIME) - 1;
			if (newBurnTime <= 0) {
				playExtinguishSound(world, pos);
				changeToUnlit(world, pos, state);
				world.updateNeighborsAt(pos, this);
			} else if (state.getValue(LITSTATE) == LIT && (newBurnTime <= INITIAL_BURN_TIME / 10 || newBurnTime <= 1)) {
				changeToSmoldering(world, pos, state, newBurnTime);
				world.updateNeighborsAt(pos, this);
			} else {
				world.setBlock(pos, state.setValue(BURNTIME, newBurnTime), 2);
				world.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
			}
		}
	}

	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
		super.setPlacedBy(world, pos, state, entity, stack);
		world.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
	}

	@Override
	public void onPlace(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!isMoving && state.getBlock() != newState.getBlock()) {
			defaultBlockState().updateNeighbourShapes(world, pos, 3);
		}
		super.onPlace(state, world, pos, newState, isMoving);
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

	public void changeToLit(World world, BlockPos pos, BlockState state) {
		world.setBlock(pos, RealisticTorchesBlocks.TORCH.defaultBlockState().setValue(LITSTATE, LIT).setValue(BURNTIME, getInitialBurnTime()), 2);
		if (SHOULD_BURN_OUT) {
			world.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
		}
	}

	public void changeToSmoldering(World world, BlockPos pos, BlockState state, int newBurnTime) {
		if (SHOULD_BURN_OUT) {
			world.setBlock(pos, RealisticTorchesBlocks.TORCH.defaultBlockState().setValue(LITSTATE, SMOLDERING).setValue(BURNTIME, newBurnTime), 2);
			world.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
		}
	}

	public void changeToUnlit(World world, BlockPos pos, BlockState state) {
		if (SHOULD_BURN_OUT) {
			if (ConfigHandler.noRelightEnabled.get()) {
				world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
			} else {
				world.setBlock(pos, RealisticTorchesBlocks.TORCH.defaultBlockState(), 2);
				world.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
			}
		}
	}

	public void playLightingSound(World world, BlockPos pos) {
		world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
	}

	public void playExtinguishSound(World world, BlockPos pos) {
		world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
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
