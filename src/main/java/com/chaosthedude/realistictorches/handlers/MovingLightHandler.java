package com.chaosthedude.realistictorches.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

import com.chaosthedude.realistictorches.blocks.BlockMovingLightSource;
import com.chaosthedude.realistictorches.blocks.BlockRegistry;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class MovingLightHandler {

	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void MovingLightEvent(PlayerTickEvent event) {
		if (ConfigHandler.handheldLightEnabled) {
			if (event.phase == TickEvent.Phase.START && !event.player.worldObj.isRemote) {
				if (event.player.getCurrentEquippedItem() != null) {
					if (BlockMovingLightSource.isLightEmittingItem(event.player.getCurrentEquippedItem().getItem())
							|| event.player.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(BlockRegistry.torchLit)) {
						int blockX = MathHelper.floor_double(event.player.posX);
						int blockY = MathHelper.floor_double(event.player.posY - 0.2D - event.player.getYOffset());
						int blockZ = MathHelper.floor_double(event.player.posZ);

						if (event.player.worldObj.getBlock(blockX, blockY + 1, blockZ) == Blocks.air) {
							event.player.worldObj.setBlock(blockX, blockY + 1, blockZ, BlockRegistry.movingLightSource);
						}

						else if (event.player.worldObj.getBlock(blockX, blockY + 1, blockZ) == Blocks.air) {
							event.player.worldObj.setBlock(blockX, blockY + 1, blockZ, BlockRegistry.movingLightSource);
						}
					}
				}
			}
		}
	}

}
