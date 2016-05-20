package com.chaosthedude.realistictorches.blocks.te;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.handler.TorchHandler;

import net.minecraft.tileentity.TileEntity;

public class TETorch extends TileEntity {

	public static final String NAME = "TETorch";

	public TETorch() {

	}

	@Override
	public void updateEntity() {
		TorchHandler.updateTorch(worldObj, xCoord, yCoord, zCoord);
	}

}
