package com.chaosthedude.realistictorches.handler;

import java.util.ArrayList;
import java.util.List;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;

public class LightSourceHandler {

	public static List<Item> lightSources = new ArrayList<Item>();

	public static void registerLightSources() {
		int lightSourceBlocks = 0;
		if (ConfigHandler.registerLightSourceBlocks) {
			for (Object b : GameData.getBlockRegistry()) {
				Block block = (Block) b;
				if (block.getLightValue() > ConfigHandler.lightSourceRegistryThreshold) {
					lightSources.add(Item.getItemFromBlock(block));
					lightSourceBlocks++;
				}
			}
		} else {
			for (String blockName : ConfigHandler.lightSourceItems) {
				Block block = (Block) Block.getBlockFromName(blockName);
				if (block != null) {
					lightSources.add(Item.getItemFromBlock(block));
					lightSourceBlocks++;
				}
			}
		}

		int lightSourceItems = 0;
		for (String itemName : ConfigHandler.lightSourceItems) {
			Item item = (Item) Item.itemRegistry.getObject(new ResourceLocation(itemName));
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

}
