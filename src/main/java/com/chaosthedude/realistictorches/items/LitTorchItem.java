package com.chaosthedude.realistictorches.items;

import com.chaosthedude.realistictorches.blocks.RealisticTorchBlock;
import com.chaosthedude.realistictorches.registry.RealisticTorchesRegistry;

import net.minecraft.core.Direction;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class LitTorchItem extends StandingAndWallBlockItem {

	public static final String NAME = "lit_torch";

	public LitTorchItem(Properties properties, Direction direction) {
		super(RealisticTorchesRegistry.TORCH_BLOCK.get(), RealisticTorchesRegistry.TORCH_WALL_BLOCK.get(), properties, direction);
	}

	@Override
	public BlockState getPlacementState(BlockPlaceContext context) {
		BlockState state = super.getPlacementState(context);
		if (state != null) {
			return state.setValue(RealisticTorchBlock.getLitState(), RealisticTorchBlock.LIT).setValue(RealisticTorchBlock.getBurnTime(), RealisticTorchBlock.getInitialBurnTime());
		}
		return null;
	}

}
