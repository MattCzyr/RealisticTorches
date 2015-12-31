package com.chaosthedude.realistictorches.blocks.te;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TETorch extends TileEntity implements ITickable {

	public static final String name = "TETorch";

	public TETorch() {

	}

	@Override
	public void update() {
		if (worldObj.canLightningStrike(pos)) {
			worldObj.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.fizz", 1.0F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			int meta = RealisticTorchesBlocks.torchLit.getMetaFromState(worldObj.getBlockState(pos));
			worldObj.setBlockState(pos, RealisticTorchesBlocks.torchUnlit.getStateFromMeta(meta), 2);
		}
	}

}
