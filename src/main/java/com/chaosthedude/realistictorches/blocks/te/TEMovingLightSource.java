package com.chaosthedude.realistictorches.blocks.te;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.handler.LightSourceHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TEMovingLightSource extends TileEntity implements ITickable {

	public static final String NAME = "TEMovingLightSource";

	public TEMovingLightSource() {

	}

	@Override
	public void update() {
		EntityPlayer player = worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 2.0D);
		if (player == null || player.getHeldItem() == null || !LightSourceHandler.isLightSource(player.getCurrentEquippedItem().getItem())) {
			if (worldObj.getBlockState(pos).getBlock() == RealisticTorchesBlocks.movingLightSource) {
				worldObj.setBlockToAir(pos);
			}
		}
	}
}