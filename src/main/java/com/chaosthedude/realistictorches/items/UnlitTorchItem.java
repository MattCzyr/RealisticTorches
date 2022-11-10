package com.chaosthedude.realistictorches.items;

import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;

import net.minecraft.world.item.StandingAndWallBlockItem;

public class UnlitTorchItem extends StandingAndWallBlockItem {

	public static final String NAME = "unlit_torch";

	public UnlitTorchItem(Properties properties) {
		super(RealisticTorchesBlocks.TORCH, RealisticTorchesBlocks.WALL_TORCH, properties);
	}

}
