package com.chaosthedude.realistictorches.items;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMatchbox extends Item {

	public static final String name = "Matchbox";

	public ItemMatchbox() {
		super();

		setUnlocalizedName(RealisticTorches.MODID + "_" + name);
		maxStackSize = 1;
		setMaxDamage(ConfigHandler.matchboxDurability - 1);
		setCreativeTab(CreativeTabs.tabTools);
		setTextureName(RealisticTorches.MODID + ":" + name);
		setNoRepair();
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}

	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		if (ConfigHandler.matchboxDurability > 1) {
			int dmg = stack.getItemDamage();
			ItemStack newStack = copyStack(stack, 1);
			newStack.setItemDamage(dmg + 1);
			return newStack;
		} else {
			return stack;
		}
	}

	public static ItemStack copyStack(ItemStack stack, int n) {
		return new ItemStack(stack.getItem(), n, stack.getItemDamage());
	}

}
