package com.chaosthedude.realistictorches.blocks.te;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TETorch extends TileEntity implements ITickable {

	public static final String NAME = "TETorch";

	public TETorch() {

	}

	@Override
	public void update() {
		if (worldObj.canLightningStrike(pos)) {
			int meta = RealisticTorchesBlocks.torchLit.getMetaFromState(worldObj.getBlockState(pos));
			worldObj.setBlockState(pos, RealisticTorchesBlocks.torchUnlit.getStateFromMeta(meta), 2);
		}
	}

}
