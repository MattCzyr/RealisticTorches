package com.chaosthedude.realistictorches.worldgen;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.blocks.RealisticTorchBlock;
import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.blocks.RealisticWallTorchBlock;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class TorchFeature extends Feature<NoneFeatureConfiguration> {

	public static final String NAME = "torch_feature";

	public static final Feature<NoneFeatureConfiguration> TORCH_FEATURE = new TorchFeature(NoneFeatureConfiguration.CODEC);
	public static final ConfiguredFeature<?, ?> TORCH_CONFIGURED_FEATURE = TORCH_FEATURE.configured(FeatureConfiguration.NONE).decorated(FeatureDecorator.NOPE.configured(DecoratorConfiguration.NONE));

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
							context.level().setBlock(replacePos, RealisticTorchesBlocks.TORCH.defaultBlockState().setValue(RealisticTorchBlock.getLitState(), RealisticTorchBlock.LIT).setValue(RealisticTorchBlock.getBurnTime(), RealisticTorchBlock.getInitialBurnTime()), 3);
							context.level().getBlockTicks().scheduleTick(replacePos, context.level().getBlockState(replacePos).getBlock(), ConfigHandler.torchBurnoutTime.get());
						} else if (context.level().getBlockState(replacePos).getBlock() == Blocks.WALL_TORCH) {
							context.level().setBlock(replacePos, RealisticTorchesBlocks.WALL_TORCH.defaultBlockState().setValue(RealisticWallTorchBlock.getLitState(), RealisticTorchBlock.LIT).setValue(RealisticWallTorchBlock.getBurnTime(), RealisticWallTorchBlock.getInitialBurnTime()).setValue(BlockStateProperties.HORIZONTAL_FACING, context.level().getBlockState(replacePos).getValue(BlockStateProperties.HORIZONTAL_FACING)), 3);
							context.level().getBlockTicks().scheduleTick(replacePos, context.level().getBlockState(replacePos).getBlock(), ConfigHandler.torchBurnoutTime.get());
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
			Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealisticTorches.MODID, NAME), TORCH_CONFIGURED_FEATURE);
		}

	}

}
