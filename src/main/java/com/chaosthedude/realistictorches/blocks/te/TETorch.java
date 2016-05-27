package com.chaosthedude.realistictorches.blocks.te;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.handler.TorchHandler;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TETorch extends TileEntity implements ITickable {

	public static final String NAME = "TETorch";

	public TETorch() {

	}

	@Override
	public void update() {
		TorchHandler.updateTorch(worldObj, getPos());
	}

}
