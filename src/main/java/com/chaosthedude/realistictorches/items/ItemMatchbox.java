package com.chaosthedude.realistictorches.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.handlers.ConfigHandler;

public class ItemMatchbox extends Item {
	
	private ItemStack emptyItem = null;
	
	public static final String name = "Matchbox";
	
	public ItemMatchbox() {
		super();
		
		this.setUnlocalizedName(RealisticTorches.ID + "_" + name);
		this.maxStackSize = 1;
        this.setMaxDamage(ConfigHandler.matchboxDurability - 1);
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setTextureName(RealisticTorches.ID + ":" + name);
	}
	
	@Override
	public boolean hasContainerItem() {
		return true;
	}
	
	public void setEmptyItem(ItemStack ei) {
		this.emptyItem = ei;
	}
	
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
		return false;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		int dmg = stack.getItemDamage();
		ItemStack newStack = copyStack(stack, 1);
		newStack.setItemDamage(dmg + 1);
		return newStack;
	}
	
	public static ItemStack copyStack(ItemStack stack, int n) {
		return new ItemStack(stack.getItem(), n, stack.getItemDamage());
	}
	
}
