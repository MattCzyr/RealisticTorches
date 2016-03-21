package com.chaosthedude.realistictorches;

import com.chaosthedude.realistictorches.items.ItemMatchbox;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RealisticTorchesItems {

	public static ItemMatchbox matchbox;
	public static Item glowstoneCrystal;

	public static void initialize() {
		matchbox = new ItemMatchbox();
		glowstoneCrystal = new Item().setUnlocalizedName(RealisticTorches.MODID + "_GlowstoneCrystal").setCreativeTab(CreativeTabs.tabMaterials);;
	}

	public static void register() {
		GameRegistry.registerItem(matchbox, matchbox.name);
		GameRegistry.registerItem(glowstoneCrystal, "GlowstoneCrystal");
	}

}
