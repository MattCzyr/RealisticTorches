package com.chaosthedude.realistictorches.items;

import java.util.ArrayList;
import java.util.List;

import com.chaosthedude.realistictorches.RealisticTorches;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class RealisticTorchesItems {

	public static final List<Item> REGISTRY = new ArrayList<Item>();

	public static ItemMatchbox matchbox;
	public static Item glowstoneCrystal;
	public static Item glowstonePaste;

	public static void register() {
		matchbox = registerItem(new ItemMatchbox(), ItemMatchbox.NAME);
		glowstoneCrystal = registerItem(new Item().setUnlocalizedName(RealisticTorches.MODID + ".glowstone_crystal").setCreativeTab(CreativeTabs.MATERIALS), "glowstone_crystal");
		glowstonePaste = registerItem(new Item().setUnlocalizedName(RealisticTorches.MODID + ".glowstone_paste").setCreativeTab(CreativeTabs.MATERIALS), "glowstone_paste");
	}

	protected static <T extends Item> T registerItem(T itemType, String name) {
		T item = itemType;
		item.setRegistryName(name);
		ForgeRegistries.ITEMS.register(item);
		REGISTRY.add(item);

		return item;
	}

}
