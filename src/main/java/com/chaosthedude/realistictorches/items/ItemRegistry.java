package com.chaosthedude.realistictorches.items;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemRegistry {
	
	public static ItemMatchbox matchbox;
	
	public static void mainRegistry() {
		initializeItem();
		registerItem();
	}
	
	public static void initializeItem() {
		matchbox = new ItemMatchbox();
	}
	
	public static void registerItem() {
		GameRegistry.registerItem(matchbox, matchbox.name);
	}

}
