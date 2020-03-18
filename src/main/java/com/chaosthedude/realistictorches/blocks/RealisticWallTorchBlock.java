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
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RealisticWallTorchBlock extends RealisticTorchBlock {

	public static final String NAME = "torch_wall";

	public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.HORIZONTAL_FACING;

	protected RealisticWallTorchBlock() {
		super();
		setDefaultState(getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH));
	}

	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(LITSTATE) == LIT || (state.get(LITSTATE) == SMOLDERING && world.getRandom().nextInt(2) == 1)) {
			Direction direction = state.get(HORIZONTAL_FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + 0.7D;
			double d2 = (double) pos.getZ() + 0.5D;
			Direction direction1 = direction.getOpposite();
			world.addParticle(ParticleTypes.SMOKE, d0 + 0.27D * (double) direction1.getXOffset(), d1 + 0.22D,
					d2 + 0.27D * (double) direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
			world.addParticle(ParticleTypes.FLAME, d0 + 0.27D * (double) direction1.getXOffset(), d1 + 0.22D,
					d2 + 0.27D * (double) direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void changeToLit(World world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, RealisticTorchesBlocks.WALL_TORCH.getDefaultState().with(LITSTATE, LIT)
				.with(BURNTIME, initialBurnTime).with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING)));
		world.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(world));
	}

	@Override
	public void changeToSmoldering(World world, BlockPos pos, BlockState state, int newBurnTime) {
		world.setBlockState(pos, RealisticTorchesBlocks.WALL_TORCH.getDefaultState().with(LITSTATE, SMOLDERING)
				.with(BURNTIME, newBurnTime).with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING)));
		world.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(world));
	}

	@Override
	public void changeToUnlit(World world, BlockPos pos, BlockState state) {
		if (ConfigHandler.noRelightEnabled.get()) {
    		world.setBlockState(pos, Blocks.AIR.getDefaultState());
    	} else {
			world.setBlockState(pos, RealisticTorchesBlocks.WALL_TORCH.getDefaultState().with(HORIZONTAL_FACING,
					state.get(HORIZONTAL_FACING)));
			world.getPendingBlockTicks().scheduleTick(pos, this, tickRate(world));
    	}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(HORIZONTAL_FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return WallTorchBlock.func_220289_j(state);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return Blocks.WALL_TORCH.isValidPosition(state, worldIn, pos);
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world,
			BlockPos currentPos, BlockPos facingPos) {
		return Blocks.WALL_TORCH.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = Blocks.WALL_TORCH.getStateForPlacement(context);
		return blockstate == null ? null
				: this.getDefaultState().with(HORIZONTAL_FACING, blockstate.get(HORIZONTAL_FACING));
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
