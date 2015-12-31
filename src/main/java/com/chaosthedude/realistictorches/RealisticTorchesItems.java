package com.chaosthedude.realistictorches;

import com.chaosthedude.realistictorches.items.ItemMatchbox;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class RealisticTorchesItems {

	public static ItemMatchbox matchbox;
	public static Item glowstoneCrystal;

	public static void mainRegistry() {
		initialize();
		register();
	}

	public static void initialize() {
		matchbox = new ItemMatchbox();
		glowstoneCrystal = new Item().setUnlocalizedName(RealisticTorches.MODID + "_GlowstoneCrystal").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RealisticTorches.MODID + ":GlowstoneCrystal");
	}

	public static void register() {
		GameRegistry.registerItem(matchbox, matchbox.name);
		GameRegistry.registerItem(glowstoneCrystal, "GlowstoneCrystal");
	}

}
