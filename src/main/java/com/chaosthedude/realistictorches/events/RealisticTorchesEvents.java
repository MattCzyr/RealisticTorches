package com.chaosthedude.realistictorches.events;

import com.chaosthedude.realistictorches.blocks.BlockMovingLightSource;
import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;
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
		if (!ConfigHandler.handheldLightEnabled || event.phase != TickEvent.Phase.START || event.player.world.isRemote || event.player.getHeldEquipment() == null
				|| !LightSourceHandler.containsLightSource(event.player.getHeldEquipment()) || event.player.world.getTotalWorldTime() % ConfigHandler.handheldLightUpdateTicks != 0) {
			return;
		}

		final int blockX = MathHelper.floor(event.player.posX);
		final int blockY = MathHelper.floor(event.player.posY - 0.2D - event.player.getYOffset());
		final int blockZ = MathHelper.floor(event.player.posZ);
		final BlockPos pos = new BlockPos(blockX, blockY + 1, blockZ);

		if (event.player.world.isAirBlock(pos)
				&& !(event.player.world.getBlockState(pos).getBlock() instanceof BlockMovingLightSource)) {

			final BlockMovingLightSource lightSource = RealisticTorchesBlocks.movingLightSource;
			event.player.world.setBlockState(pos, lightSource.setPlayer(event.player).getDefaultState());
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
