package com.chaosthedude.realistictorches.blocks;

import javax.annotation.Nullable;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.registry.RealisticTorchesRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RealisticWallTorchBlock extends RealisticTorchBlock {

	public static final String NAME = "torch_wall";
	public static final int TICK_RATE = 1200;

	public static final DirectionProperty HORIZONTAL_FACING = HorizontalDirectionalBlock.FACING;

	public RealisticWallTorchBlock() {
		super();
		registerDefaultState(stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (state.getValue(LITSTATE) == LIT || (state.getValue(LITSTATE) == SMOLDERING && level.getRandom().nextInt(2) == 1)) {
			Direction direction = state.getValue(HORIZONTAL_FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + 0.7D;
			double d2 = (double) pos.getZ() + 0.5D;
			Direction direction1 = direction.getOpposite();
			level.addParticle(ParticleTypes.SMOKE, d0 + 0.27D * (double) direction1.getStepX(), d1 + 0.22D, d2 + 0.27D * (double) direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
			level.addParticle(ParticleTypes.FLAME, d0 + 0.27D * (double) direction1.getStepX(), d1 + 0.22D, d2 + 0.27D * (double) direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void changeToLit(Level level, BlockPos pos, BlockState state) {
		level.setBlock(pos, RealisticTorchesRegistry.TORCH_WALL_BLOCK.get().defaultBlockState().setValue(LITSTATE, LIT).setValue(BURNTIME, getInitialBurnTime()).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)), 2);
		if (SHOULD_BURN_OUT) {
			level.scheduleTick(pos, this, TICK_RATE);
		}
	}

	@Override
	public void changeToSmoldering(Level level, BlockPos pos, BlockState state, int newBurnTime) {
		if (SHOULD_BURN_OUT) {
			level.setBlock(pos, RealisticTorchesRegistry.TORCH_WALL_BLOCK.get().defaultBlockState().setValue(LITSTATE, SMOLDERING).setValue(BURNTIME, newBurnTime).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)), 2);
			level.scheduleTick(pos, this, TICK_RATE);
		}
	}

	@Override
	public void changeToUnlit(Level level, BlockPos pos, BlockState state) {
		if (SHOULD_BURN_OUT) {
			if (ConfigHandler.noRelightEnabled.get()) {
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
			} else {
				level.setBlock(pos, RealisticTorchesRegistry.TORCH_WALL_BLOCK.get().defaultBlockState().setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)), 2);
				level.scheduleTick(pos, this, TICK_RATE);
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HORIZONTAL_FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
		return WallTorchBlock.getShape(state);
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState otherState, LevelAccessor level, BlockPos pos, BlockPos otherPos) {
		return facing.getOpposite() == state.getValue(HORIZONTAL_FACING) && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		Direction direction = state.getValue(HORIZONTAL_FACING);
		BlockPos onPos = pos.relative(direction.getOpposite());
		BlockState onState = level.getBlockState(onPos);
		return onState.isFaceSturdy(level, onPos, direction);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = Blocks.WALL_TORCH.getStateForPlacement(context);
		return blockstate == null ? null : defaultBlockState().setValue(HORIZONTAL_FACING, blockstate.getValue(HORIZONTAL_FACING));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return Blocks.WALL_TORCH.rotate(state, rot);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return Blocks.WALL_TORCH.mirror(state, mirror);
	}

}
