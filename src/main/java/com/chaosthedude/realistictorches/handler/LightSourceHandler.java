package com.chaosthedude.realistictorches.handler;

import java.util.ArrayList;
import java.util.List;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LightSourceHandler {

	public static List<Item> lightSources = new ArrayList<Item>();

	public static void registerLightSources() {
		int lightSourceBlocks = 0;
		if (ConfigHandler.registerLightSourceBlocks) {
			for (Block block : Block.REGISTRY) {
				if (block.getLightValue(block.getDefaultState()) > ConfigHandler.lightSourceRegistryThreshold) {
					lightSources.add(Item.getItemFromBlock(block));
					lightSourceBlocks++;
				}
			}
		} else {
			for (String blockName : ConfigHandler.lightSourceItems) {
				final Block block = Block.getBlockFromName(blockName);
				if (block != null) {
					lightSources.add(Item.getItemFromBlock(block));
					lightSourceBlocks++;
				}
			}
		}

		int lightSourceItems = 0;
		for (String itemName : ConfigHandler.lightSourceItems) {
			final Item item = Item.REGISTRY.getObject(new ResourceLocation(itemName));
			if (item != null) {
				lightSources.add(item);
				lightSourceItems++;
			}
		}

		RealisticTorches.logger.info("Registered " + lightSourceBlocks + " blocks and " + lightSourceItems + " items as light sources.");
	}

	public static boolean isLightSource(Item item) {
		return lightSources.contains(item);
	}

	public static boolean containsLightSource(Iterable<ItemStack> items) {
		if (items != null) {
			for (ItemStack stack : items) {
				if (!stack.isEmpty() && isLightSource(stack.getItem())) {
					return true;
				}
			}
		}

		return false;
	}

}
