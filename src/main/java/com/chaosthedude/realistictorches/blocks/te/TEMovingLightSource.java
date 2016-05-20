package com.chaosthedude.realistictorches.blocks.te;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.handler.LightSourceHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TEMovingLightSource extends TileEntity {

	public static final String NAME = "TEMovingLightSource";

	public TEMovingLightSource() {

	}

	@Override
	public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
		return (oldMeta != newMeta);
	}

	@Override
	public void updateEntity() {
		EntityPlayer player = worldObj.getClosestPlayer(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, 2.0D);
		if (player == null || player.getHeldItem() == null || !LightSourceHandler.isLightSource(player.getCurrentEquippedItem().getItem())) {
			if (worldObj.getBlock(xCoord, yCoord, zCoord) == RealisticTorchesBlocks.movingLightSource) {
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
		}
	}

}