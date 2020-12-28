package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class LitTorchBlock extends RealisticTorchBlock {
	
	public static final String NAME = "lit_torch";
	
	@Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 14;
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
            } else if (newBurnTime <= initialBurnTime / 10 || newBurnTime <= 1) {
            	changeToSmoldering(world, pos, state, newBurnTime);
            	world.notifyNeighborsOfStateChange(pos, this);
            } else {
	            world.setBlockState(pos, state.with(BURNTIME, newBurnTime));
	            world.getPendingBlockTicks().scheduleTick(pos, this, TICK_RATE);
            }
        }
    }

}
