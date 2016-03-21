package com.chaosthedude.realistictorches.events;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.util.LightSources;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.util.MathHelper;

public class MovingLightHandler {

	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void movingLightHandler(PlayerTickEvent event) {
		if (!ConfigHandler.handheldLightEnabled || event.phase != TickEvent.Phase.START || event.player.worldObj.isRemote || event.player.getCurrentEquippedItem() == null
				|| !LightSources.isLightSource(event.player.getCurrentEquippedItem().getItem())) {
			return;
		}

		int x = MathHelper.floor_double(event.player.posX);
		int y = MathHelper.floor_double(event.player.posY - 0.2D - event.player.getYOffset()) + 1;
		int z = MathHelper.floor_double(event.player.posZ);
		if (event.player.worldObj.getBlock(x, y, z) != RealisticTorchesBlocks.movingLightSource && event.player.worldObj.isAirBlock(x, y, z)) {
			event.player.worldObj.setBlock(x, y, z, RealisticTorchesBlocks.movingLightSource);
		}
	}

}
