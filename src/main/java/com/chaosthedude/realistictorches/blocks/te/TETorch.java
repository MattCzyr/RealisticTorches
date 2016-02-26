package com.chaosthedude.realistictorches.blocks.te;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;

import net.minecraft.tileentity.TileEntity;

public class TETorch extends TileEntity {

	public static final String NAME = "TETorch";

	public TETorch() {

	}

	@Override
	public void updateEntity() {
		if (worldObj.canLightningStrikeAt(xCoord, yCoord, zCoord)) {
			worldObj.setBlock(xCoord, yCoord, zCoord, RealisticTorchesBlocks.torchUnlit, worldObj.getBlockMetadata(xCoord, yCoord, zCoord), 2);
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.fizz", 1.0F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
	}

}
