package com.chaosthedude.realistictorches.blocks.te;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.util.LightSources;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

public class TEMovingLightSource extends TileEntity implements IUpdatePlayerListBox {

	public static final String name = "TEMovingLightSource";

	public TEMovingLightSource() {

	}

	@Override
	public void update() {
		EntityPlayer player = worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 2.0D);
		if (player == null || player.getHeldItem() == null) {
			if (worldObj.getBlockState(pos).getBlock() == RealisticTorchesBlocks.movingLightSource) {
				worldObj.setBlockToAir(pos);
			}
		} else if (!LightSources.isLightSource(player.getCurrentEquippedItem().getItem())) {
			if (worldObj.getBlockState(pos).getBlock() == RealisticTorchesBlocks.movingLightSource) {
				worldObj.setBlockToAir(pos);
			}
		}
	}

}