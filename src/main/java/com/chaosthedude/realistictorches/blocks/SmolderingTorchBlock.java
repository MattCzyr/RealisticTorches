package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SmolderingTorchBlock extends RealisticTorchBlock {
	
	public static final String NAME = "smoldering_torch";
	
	@Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 12;
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.getRandom().nextInt(2) == 1) {
        	super.animateTick(state, world, pos, random);
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isRemote() && initialBurnTime > 0) {
        	if (world.isRainingAt(pos)) {
                playExtinguishSound(world, pos);
                changeToUnlit(world, pos, state);
                return;
            }
            int newBurnTime = state.get(BURNTIME) - 1;
            if (newBurnTime <= 0) {
                playExtinguishSound(world, pos);
                changeToUnlit(world, pos, state);
                world.notifyNeighborsOfStateChange(pos, this);
            } else {
	            world.setBlockState(pos, state.with(BURNTIME, newBurnTime));
	            world.getPendingBlockTicks().scheduleTick(pos, this, TICK_RATE);
            }
        }
    }

}
