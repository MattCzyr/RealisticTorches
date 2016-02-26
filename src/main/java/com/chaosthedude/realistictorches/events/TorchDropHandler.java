package com.chaosthedude.realistictorches.events;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class TorchDropHandler {

	@SubscribeEvent
	public void torchDropHandler(HarvestDropsEvent event) {
		if (event.harvester != null && event.block == Blocks.torch) {
			event.drops.clear();
			event.dropChance = 1.0F;
			event.drops.add(new ItemStack(RealisticTorchesBlocks.torchUnlit));
		}
	}

}
