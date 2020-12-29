package com.chaosthedude.realistictorches.items;

import com.chaosthedude.realistictorches.blocks.RealisticTorchBlock;
import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.WallOrFloorItem;

public class LitTorchItem extends WallOrFloorItem {
	
	public static final String NAME = "lit_torch";

	public LitTorchItem(Properties properties) {
		super(RealisticTorchesBlocks.TORCH, RealisticTorchesBlocks.WALL_TORCH, properties);
	}

	@Override
	protected BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state != null) {
			return state.with(RealisticTorchBlock.getLitState(), RealisticTorchBlock.LIT).with(RealisticTorchBlock.getBurnTime(), RealisticTorchBlock.getInitialBurnTime());
		}
		return null;
	}

}
