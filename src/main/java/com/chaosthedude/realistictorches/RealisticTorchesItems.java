package com.chaosthedude.realistictorches;

import com.chaosthedude.realistictorches.items.ItemMatchbox;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RealisticTorchesItems {

	public static ItemMatchbox matchbox;
	public static Item glowstoneCrystal;

	public static void init() {
		matchbox = new ItemMatchbox();
		glowstoneCrystal = new Item().setUnlocalizedName(RealisticTorches.MODID + "_GlowstoneCrystal").setCreativeTab(CreativeTabs.MATERIALS);
	}

	public static void register() {
		registerItem(matchbox, matchbox.NAME);
		registerItem(glowstoneCrystal, "GlowstoneCrystal");
	}

	private static void registerItem(Item item, String name) {
		item.setRegistryName(name);
		GameRegistry.register(item);
	}

}
