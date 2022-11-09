package com.chaosthedude.realistictorches.worldgen;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.blocks.RealisticTorchBlock;
import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.blocks.RealisticWallTorchBlock;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.mojang.serialization.Codec;

import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class TorchFeature extends Feature<NoFeatureConfig> {

	public static final String NAME = "torch_feature";

	public static final Feature<NoFeatureConfig> TORCH_FEATURE = new TorchFeature(NoFeatureConfig.CODEC);
	public static final ConfiguredFeature<?, ?> TORCH_CONFIGURED_FEATURE = TORCH_FEATURE.configured(IFeatureConfig.NONE).decorated(Placement.NOPE.configured(IPlacementConfig.NONE));

	public TorchFeature(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		BlockPos.Mutable replacePos = new BlockPos.Mutable();

		if (ConfigHandler.generateLitTorches.get()) {
			for (int x = 0; x < 16; x++) {
				for (int y = 0; y < world.getHeight(); y++) {
					for (int z = 0; z < 16; z++) {
						replacePos.set(pos.getX(), 0, pos.getZ()).move(x, y, z);
						if (world.getBlockState(replacePos).getBlock() == Blocks.TORCH) {
							world.setBlock(replacePos, RealisticTorchesBlocks.TORCH.defaultBlockState().setValue(RealisticTorchBlock.getLitState(), RealisticTorchBlock.LIT).setValue(RealisticTorchBlock.getBurnTime(), RealisticTorchBlock.getInitialBurnTime()), 3);
							world.getBlockTicks().scheduleTick(replacePos, world.getBlockState(replacePos).getBlock(), ConfigHandler.torchBurnoutTime.get());
						} else if (world.getBlockState(replacePos).getBlock() == Blocks.WALL_TORCH) {
							world.setBlock(replacePos, RealisticTorchesBlocks.WALL_TORCH.defaultBlockState().setValue(RealisticWallTorchBlock.getLitState(), RealisticTorchBlock.LIT).setValue(RealisticWallTorchBlock.getBurnTime(), RealisticWallTorchBlock.getInitialBurnTime()).setValue(BlockStateProperties.HORIZONTAL_FACING, world.getBlockState(replacePos).getValue(BlockStateProperties.HORIZONTAL_FACING)), 3);
							world.getBlockTicks().scheduleTick(replacePos, world.getBlockState(replacePos).getBlock(), ConfigHandler.torchBurnoutTime.get());
						}
					}
				}
			}
		}
		return true;
	}

	@Mod.EventBusSubscriber(modid = RealisticTorches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ModEvents {

		@SubscribeEvent
		public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
			event.getRegistry().register(TORCH_FEATURE.setRegistryName(new ResourceLocation(RealisticTorches.MODID, NAME)));
			Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealisticTorches.MODID, NAME), TORCH_CONFIGURED_FEATURE);
		}

	}

}
