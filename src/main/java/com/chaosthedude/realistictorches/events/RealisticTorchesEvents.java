package com.chaosthedude.realistictorches.events;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.blocks.BlockMovingLightSource;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.handler.LightSourceHandler;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class RealisticTorchesEvents {
	
	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void movingLightHandler(PlayerTickEvent event) {
		if (!ConfigHandler.handheldLightEnabled || event.phase != TickEvent.Phase.START || event.player.worldObj.isRemote || event.player.getHeldEquipment() == null || !LightSourceHandler.containsLightSource(event.player.getHeldEquipment())) {
			return;
		}

		final int blockX = MathHelper.floor_double(event.player.posX);
		final int blockY = MathHelper.floor_double(event.player.posY - 0.2D - event.player.getYOffset());
		final int blockZ = MathHelper.floor_double(event.player.posZ);
		final BlockPos pos = new BlockPos(blockX, blockY + 1, blockZ);

		if (event.player.worldObj.isAirBlock(pos)) {
			final BlockMovingLightSource lightSource = RealisticTorchesBlocks.movingLightSource;
			event.player.worldObj.setBlockState(pos, lightSource.setPlayer(event.player).getDefaultState());
		}
	}

	@SubscribeEvent
	public void onBlockHarvest(HarvestDropsEvent event) {
		if (ConfigHandler.vanillaTorchDropsUnlit && event.getState().getBlock() == Blocks.TORCH) {
			event.getDrops().clear();
			event.setDropChance(1.0F);
			event.getDrops().add(new ItemStack(RealisticTorchesBlocks.torchUnlit));
	 	 }
	}

}
