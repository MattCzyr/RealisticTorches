package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RealisticWallTorchBlock extends RealisticTorchBlock {

	public static final String NAME = "torch_wall";
	public static final int TICK_RATE = 1200;

	public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.FACING;

	public RealisticWallTorchBlock() {
		super();
		registerDefaultState(stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
	}

	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.getValue(LITSTATE) == LIT || (state.getValue(LITSTATE) == SMOLDERING && world.getRandom().nextInt(2) == 1)) {
			Direction direction = state.getValue(HORIZONTAL_FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + 0.7D;
			double d2 = (double) pos.getZ() + 0.5D;
			Direction direction1 = direction.getOpposite();
			world.addParticle(ParticleTypes.SMOKE, d0 + 0.27D * (double) direction1.getStepX(), d1 + 0.22D, d2 + 0.27D * (double) direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
			world.addParticle(ParticleTypes.FLAME, d0 + 0.27D * (double) direction1.getStepX(), d1 + 0.22D, d2 + 0.27D * (double) direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void changeToLit(World world, BlockPos pos, BlockState state) {
		world.setBlock(pos, RealisticTorchesBlocks.WALL_TORCH.defaultBlockState().setValue(LITSTATE, LIT).setValue(BURNTIME, getInitialBurnTime()).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)), 2);
		if (SHOULD_BURN_OUT) {
			world.getBlockTicks().scheduleTick(pos, this, TICK_RATE);
		}
	}

	@Override
	public void changeToSmoldering(World world, BlockPos pos, BlockState state, int newBurnTime) {
		if (SHOULD_BURN_OUT) {
			world.setBlock(pos, RealisticTorchesBlocks.WALL_TORCH.defaultBlockState().setValue(LITSTATE, SMOLDERING).setValue(BURNTIME, newBurnTime).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)), 2);
			world.getBlockTicks().scheduleTick(pos, this, TICK_RATE);
		}
	}

	@Override
	public void changeToUnlit(World world, BlockPos pos, BlockState state) {
		if (SHOULD_BURN_OUT) {
			if (ConfigHandler.noRelightEnabled.get()) {
				world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
			} else {
				world.setBlock(pos, RealisticTorchesBlocks.WALL_TORCH.defaultBlockState().setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)), 2);
				world.getBlockTicks().scheduleTick(pos, this, TICK_RATE);
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HORIZONTAL_FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return WallTorchBlock.getShape(state);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		Direction direction = state.getValue(HORIZONTAL_FACING);
		BlockPos onPos = pos.relative(direction.getOpposite());
		BlockState onState = world.getBlockState(onPos);
		return onState.isFaceSturdy(world, onPos, direction);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
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
