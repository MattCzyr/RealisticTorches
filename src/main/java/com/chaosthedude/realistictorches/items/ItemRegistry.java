package com.chaosthedude.realistictorches.items;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemRegistry {
	
	public static ItemMatchbox matchbox;
	public static ItemGlowstoneCrystal glowstoneCrystal;
	
	public static void mainRegistry() {
		initializeItem();
		registerItem();
	}
	
	public static void initializeItem() {
		matchbox = new ItemMatchbox();
		glowstoneCrystal = new ItemGlowstoneCrystal();
	}
	
	public static void registerItem() {
		GameRegistry.registerItem(matchbox, matchbox.name);
		GameRegistry.registerItem(glowstoneCrystal, glowstoneCrystal.name);
	}

}
