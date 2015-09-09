package com.chaosthedude.realistictorches.handlers;

import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.chaosthedude.realistictorches.blocks.BlockRegistry;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TorchHandler {
	
	@SubscribeEvent
	public void TorchEvent(PlayerInteractEvent event) {
		EntityPlayer player = event.entityPlayer;
		
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			if (player.getCurrentEquippedItem() != null) {
				if (player.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.torch)) {
					
					if (event.world.isBlockNormalCubeDefault(event.x, event.y, event.z, true) == true) {
						
						if (event.face == 1 && (event.world.getBlock(event.x, event.y + 1, event.z) instanceof BlockLiquid ||
								event.world.getBlock(event.x + 1, event.y + 1, event.z) instanceof BlockLiquid ||
								event.world.getBlock(event.x - 1, event.y + 1, event.z) instanceof BlockLiquid ||
								event.world.getBlock(event.x, event.y + 1, event.z + 1) instanceof BlockLiquid ||
								event.world.getBlock(event.x, event.y + 1, event.z - 1) instanceof BlockLiquid ||
								event.world.getBlock(event.x, event.y + 2, event.z) instanceof BlockLiquid)) {
							event.setCanceled(true);
						}
						
						else  if (event.face > 1 && (event.world.getBlock(event.x, event.y, event.z) instanceof BlockLiquid ||
								event.world.getBlock(event.x + 1, event.y, event.z) instanceof BlockLiquid ||
								event.world.getBlock(event.x - 1, event.y, event.z) instanceof BlockLiquid ||
								event.world.getBlock(event.x, event.y, event.z + 1) instanceof BlockLiquid ||
								event.world.getBlock(event.x, event.y, event.z - 1) instanceof BlockLiquid ||
								event.world.getBlock(event.x, event.y + 1, event.z) instanceof BlockLiquid)) {
							event.setCanceled(true);
						}
						
						else if (event.face == 1) {
							if (!event.world.isRemote) {
								event.world.setBlock(event.x, event.y + 1, event.z, BlockRegistry.torchLit, 5, 2);
								if (!player.capabilities.isCreativeMode) {
									player.inventory.consumeInventoryItem(Item.getItemFromBlock(Blocks.torch));
								}
							}
						}
					
						else if (event.face == 2) {
							if (!event.world.isRemote) {
								event.world.setBlock(event.x, event.y, event.z - 1, BlockRegistry.torchLit, 4, 2);
								if (!player.capabilities.isCreativeMode) {
									player.inventory.consumeInventoryItem(Item.getItemFromBlock(Blocks.torch));
								}
							}
						}
					
						else if (event.face == 3) {
							if (!event.world.isRemote) {
								event.world.setBlock(event.x, event.y, event.z + 1, BlockRegistry.torchLit, 3, 2);
								if (!player.capabilities.isCreativeMode) {
									player.inventory.consumeInventoryItem(Item.getItemFromBlock(Blocks.torch));
								}
							}
						}
					
						else if (event.face == 4) {
							if (!event.world.isRemote) {
								event.world.setBlock(event.x - 1, event.y, event.z, BlockRegistry.torchLit, 2, 2);
								if (!player.capabilities.isCreativeMode) {
									player.inventory.consumeInventoryItem(Item.getItemFromBlock(Blocks.torch));
								}
							}
						}
					
						else if (event.face == 5) {
							if (!event.world.isRemote) {
								event.world.setBlock(event.x + 1, event.y, event.z, BlockRegistry.torchLit, 1, 2);
								ItemStack stack = player.getCurrentEquippedItem();
								if (!player.capabilities.isCreativeMode) {
									player.inventory.consumeInventoryItem(Item.getItemFromBlock(Blocks.torch));
								}
							}
						}
					
						else {
							event.setCanceled(true);
						}
					}
					
					else {
						event.setCanceled(true);
					}
				}
			}
		}
	}
	
}