package com.chaosthedude.realistictorches.items;

import java.util.ArrayList;
import java.util.List;

import com.chaosthedude.realistictorches.RealisticTorches;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RealisticTorchesItems {
	
	public static final List<Item> REGISTRY = new ArrayList<Item>();

	public static ItemMatchbox matchbox;
	public static Item glowstoneCrystal;

	public static void register() {
		matchbox = registerItem(new ItemMatchbox(), ItemMatchbox.NAME);
		glowstoneCrystal = registerItem(new Item().setUnlocalizedName(RealisticTorches.MODID + ".GlowstoneCrystal").setCreativeTab(CreativeTabs.MATERIALS), "glowstone_crystal");
	}

	protected static <T extends Item> T registerItem(T itemType, String name) {
		T item = itemType;
		item.setRegistryName(name);
		GameRegistry.register(item);
		REGISTRY.add(item);

		return item;
	}

}
