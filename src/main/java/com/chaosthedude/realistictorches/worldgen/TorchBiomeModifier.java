package com.chaosthedude.realistictorches.worldgen;

import com.chaosthedude.realistictorches.registry.RealisticTorchesRegistry;
import com.mojang.serialization.Codec;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public record TorchBiomeModifier(Holder<PlacedFeature> feature) implements BiomeModifier {

	@Override
	public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase == Phase.AFTER_EVERYTHING) {
			builder.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, feature);
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return RealisticTorchesRegistry.TORCH_BIOME_MODIFIER.get();
	}

}