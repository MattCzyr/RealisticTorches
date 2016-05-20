package com.chaosthedude.realistictorches.util;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.RealisticTorchesItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Util {

	public static void registerModels() {
		ItemModelMesher modelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		modelMesher.register(RealisticTorchesItems.matchbox, 0, new ModelResourceLocation(RealisticTorches.MODID + ":" + RealisticTorchesItems.matchbox.NAME, "inventory"));
		modelMesher.register(RealisticTorchesItems.glowstoneCrystal, 0, new ModelResourceLocation(RealisticTorches.MODID + ":GlowstoneCrystal", "inventory"));

		modelMesher.register(Item.getItemFromBlock(RealisticTorchesBlocks.torchLit), 0, new ModelResourceLocation(RealisticTorches.MODID + ":" + RealisticTorchesBlocks.torchLit.NAME, "inventory"));
		modelMesher.register(Item.getItemFromBlock(RealisticTorchesBlocks.torchSmoldering), 0, new ModelResourceLocation(RealisticTorches.MODID + ":" + RealisticTorchesBlocks.torchSmoldering.NAME, "inventory"));
		modelMesher.register(Item.getItemFromBlock(RealisticTorchesBlocks.torchUnlit), 0, new ModelResourceLocation(RealisticTorches.MODID + ":" + RealisticTorchesBlocks.torchUnlit.NAME, "inventory"));
	}

}
