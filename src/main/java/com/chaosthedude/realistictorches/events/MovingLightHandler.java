package com.chaosthedude.realistictorches.events;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.handler.LightSourceHandler;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class MovingLightHandler {

	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void movingLightHandler(PlayerTickEvent event) {
		if (!ConfigHandler.handheldLightEnabled || event.phase != TickEvent.Phase.START || event.player.worldObj.isRemote || event.player.getHeldEquipment() == null || !LightSourceHandler.containsLightSource(event.player.getHeldEquipment())) {
			return;
		}

		int blockX = MathHelper.floor_double(event.player.posX);
		int blockY = MathHelper.floor_double(event.player.posY - 0.2D - event.player.getYOffset());
		int blockZ = MathHelper.floor_double(event.player.posZ);
		BlockPos pos = new BlockPos(blockX, blockY + 1, blockZ);

		if (event.player.worldObj.getBlockState(pos) != RealisticTorchesBlocks.movingLightSource && event.player.worldObj.isAirBlock(pos)) {
			event.player.worldObj.setBlockState(pos, RealisticTorchesBlocks.movingLightSource.getDefaultState());
		}
	}

}
