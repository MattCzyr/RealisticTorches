package com.chaosthedude.realistictorches;

import java.util.HashMap;
import java.util.Map;

import com.chaosthedude.realistictorches.items.ItemMatchbox;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RealisticTorchesItems {
	
	public static Map<String, Item> registry = new HashMap<String, Item>();

	public static ItemMatchbox matchbox;
	public static Item glowstoneCrystal;

	public static void register() {
		matchbox = registerItem(new ItemMatchbox(), ItemMatchbox.NAME);
		glowstoneCrystal = registerItem(new Item().setUnlocalizedName(RealisticTorches.MODID + "_GlowstoneCrystal").setCreativeTab(CreativeTabs.MATERIALS), "GlowstoneCrystal");
	}

	protected static <T extends Item> T registerItem(T itemType, String name) {
		T item = itemType;
		item.setRegistryName(name);
		GameRegistry.register(item);
		registry.put(name, item);

		return item;
	}

}
