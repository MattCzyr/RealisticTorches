package com.chaosthedude.realistictorches.worldgen;

import com.chaosthedude.realistictorches.blocks.RealisticTorchBlock;
import com.chaosthedude.realistictorches.blocks.RealisticWallTorchBlock;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.registry.RealisticTorchesRegistry;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TorchFeature extends Feature<NoneFeatureConfiguration> {

	public static final String NAME = "torch_feature";

	public TorchFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		MutableBlockPos replacePos = new MutableBlockPos();

		if (ConfigHandler.generateLitTorches.get()) {
			for (int x = 0; x < 16; x++) {
				for (int y = 0; y < context.level().getHeight(); y++) {
					for (int z = 0; z < 16; z++) {
						replacePos.set(context.origin().getX(), 0, context.origin().getZ()).move(x, y, z);
						if (context.level().getBlockState(replacePos).getBlock() == Blocks.TORCH) {
							context.level().setBlock(replacePos, RealisticTorchesRegistry.TORCH_BLOCK.get().defaultBlockState().setValue(RealisticTorchBlock.getLitState(), RealisticTorchBlock.LIT).setValue(RealisticTorchBlock.getBurnTime(), RealisticTorchBlock.getInitialBurnTime()), 3);
							context.level().scheduleTick(replacePos, context.level().getBlockState(replacePos).getBlock(), ConfigHandler.torchBurnoutTime.get());
						} else if (context.level().getBlockState(replacePos).getBlock() == Blocks.WALL_TORCH) {
							context.level().setBlock(replacePos, RealisticTorchesRegistry.TORCH_WALL_BLOCK.get().defaultBlockState().setValue(RealisticWallTorchBlock.getLitState(), RealisticTorchBlock.LIT).setValue(RealisticWallTorchBlock.getBurnTime(), RealisticWallTorchBlock.getInitialBurnTime()).setValue(BlockStateProperties.HORIZONTAL_FACING, context.level().getBlockState(replacePos).getValue(BlockStateProperties.HORIZONTAL_FACING)), 3);
							context.level().scheduleTick(replacePos, context.level().getBlockState(replacePos).getBlock(), ConfigHandler.torchBurnoutTime.get());
						}
					}
				}
			}
		}
		return true;
	}

}
