package com.chaosthedude.realistictorches.events;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TorchDropHandler {

	@SubscribeEvent
	public void torchDropEvent(HarvestDropsEvent event) {
		if (event.harvester != null && event.state == Blocks.torch) {
			event.drops.clear();
			event.dropChance = 1.0F;
			event.drops.add(new ItemStack(RealisticTorchesBlocks.torchUnlit));
		}
	}

}
