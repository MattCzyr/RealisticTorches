package com.chaosthedude.realistictorches.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import com.chaosthedude.realistictorches.blocks.BlockRegistry;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TorchEventHandler {
	
	@SubscribeEvent
	public void TorchHarvestEvent(HarvestDropsEvent event) {
		if (event.harvester != null) {
			if (event.block == Blocks.torch) {
				event.drops.clear();
				event.dropChance = (float) 1.0;
				event.drops.add(new ItemStack(BlockRegistry.torchUnlit));
		 	 }
	 	 }
	 }
	
}
