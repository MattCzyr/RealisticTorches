package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class LitWallTorchBlock extends RealisticWallTorchBlock {
	
	public static final String NAME = "lit_wall_torch";
	
	@Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 14;
    }
	
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
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
