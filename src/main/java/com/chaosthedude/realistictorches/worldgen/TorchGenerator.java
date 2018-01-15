package com.chaosthedude.realistictorches.worldgen;

import java.util.Random;

import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class TorchGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (ConfigHandler.generateLitTorches) {
			for (int x = 0; x < 16; x++) {
				for (int y = 0; y < world.getHeight(); y++) {
					for (int z = 0; z < 16; z++) {
						final BlockPos pos = new BlockPos(chunkX * 16 + x, y, chunkZ * 16 + z);
						if (world.getBlockState(pos).getBlock() == Blocks.TORCH) {
							final BlockTorch torch = (BlockTorch) world.getBlockState(pos).getBlock();
							final IBlockState state = RealisticTorchesBlocks.torchLit.getStateFromMeta(torch.getMetaFromState(world.getBlockState(pos)));
							world.setBlockState(pos, state);
						}
					}
				}
			}
		}
	}

}
