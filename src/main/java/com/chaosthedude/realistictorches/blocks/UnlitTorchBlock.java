package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class UnlitTorchBlock extends RealisticTorchBlock {
	
	public static final String NAME = "unlit_torch";
	
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
	}
	
	@Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 0;
    }

}
