package com.chaosthedude.realistictorches.blocks.te;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.util.LightSources;
import com.chaosthedude.realistictorches.util.Util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TEMovingLightSource extends TileEntity implements ITickable {

	public static final String name = "TEMovingLightSource";

	public TEMovingLightSource() {

	}

	@Override
	public void update() {
		EntityPlayer player = Util.getClosestPlayer(worldObj, pos, 2.0D);
		if (player == null || player.getHeldEquipment() == null || !LightSources.containsLightSource(player.getHeldEquipment())) {
			if (worldObj.getBlockState(pos).getBlock() == RealisticTorchesBlocks.movingLightSource) {
				worldObj.setBlockToAir(pos);
			}
		}
	}
}