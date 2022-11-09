package com.chaosthedude.realistictorches.items;

import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;

import net.minecraft.item.WallOrFloorItem;

public class UnlitTorchItem extends WallOrFloorItem {

	public static final String NAME = "unlit_torch";

	public UnlitTorchItem(Properties properties) {
		super(RealisticTorchesBlocks.TORCH, RealisticTorchesBlocks.WALL_TORCH, properties);
	}

}
