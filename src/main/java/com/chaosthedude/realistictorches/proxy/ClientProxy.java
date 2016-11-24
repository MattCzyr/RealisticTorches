package com.chaosthedude.realistictorches.proxy;

import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.items.RealisticTorchesItems;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerModels() {
		for (Item item : RealisticTorchesItems.REGISTRY) {
			registerModel(item);
		}

		for (Block block : RealisticTorchesBlocks.REGISTRY) {
			registerModel(Item.getItemFromBlock(block));
		}
	}

	public void registerModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

}
