package com.chaosthedude.realistictorches.worldgen;

import com.chaosthedude.realistictorches.blocks.RealisticTorchBlock;
import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.blocks.RealisticWallTorchBlock;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class TorchFeature extends Feature<NoFeatureConfig> {

    public TorchFeature(Codec<NoFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int startX = pos.getX();
        int startZ = pos.getZ();
        BlockPos.Mutable replacePos = new BlockPos.Mutable();

        if (ConfigHandler.generateLitTorches.get()) {
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < world.getHeight(); y++) {
                    for (int z = 0; z < 16; z++) {
                        //Use mutable instead which saves ram usage as you are not creating a new object each time and instead changing the existing object.
                        replacePos.setPos(startX, 0, startZ).move(x, y, z);
                        if (world.getBlockState(replacePos).getBlock() == Blocks.TORCH) {
                            world.setBlockState(replacePos, RealisticTorchesBlocks.TORCH.getDefaultState().with(RealisticTorchBlock.getLitState(), RealisticTorchBlock.LIT).with(RealisticTorchBlock.getBurnTime(), RealisticTorchBlock.getInitialBurnTime()), 3);
                            world.getPendingBlockTicks().scheduleTick(replacePos, world.getBlockState(replacePos).getBlock(), 1200);
                        } else if (world.getBlockState(replacePos).getBlock() == Blocks.WALL_TORCH) {
                            world.setBlockState(replacePos, RealisticTorchesBlocks.WALL_TORCH.getDefaultState().with(RealisticWallTorchBlock.getLitState(), RealisticTorchBlock.LIT).with(RealisticWallTorchBlock.getBurnTime(), RealisticWallTorchBlock.getInitialBurnTime()).with(BlockStateProperties.HORIZONTAL_FACING, world.getBlockState(replacePos).get(BlockStateProperties.HORIZONTAL_FACING)), 3);
                            world.getPendingBlockTicks().scheduleTick(replacePos, world.getBlockState(replacePos).getBlock(), 1200);
                        }
                    }
                }
            }
        }
        return true;
    }
}
