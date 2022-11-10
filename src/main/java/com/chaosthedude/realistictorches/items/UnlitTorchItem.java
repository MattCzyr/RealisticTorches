package com.chaosthedude.realistictorches.items;

import com.chaosthedude.realistictorches.registry.RealisticTorchesRegistry;

import net.minecraft.world.item.StandingAndWallBlockItem;

public class UnlitTorchItem extends StandingAndWallBlockItem {

	public static final String NAME = "unlit_torch";

	public UnlitTorchItem(Properties properties) {
		super(RealisticTorchesRegistry.TORCH_BLOCK.get(), RealisticTorchesRegistry.TORCH_WALL_BLOCK.get(), properties);
	}

}
