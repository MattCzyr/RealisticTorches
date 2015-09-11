package com.chaosthedude.realistictorches.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.chaosthedude.realistictorches.RealisticTorches;

public class ItemGlowstoneCrystal extends Item {
	
	public static final String name = "GlowstoneCrystal";
	
	public ItemGlowstoneCrystal() {
		super();
		
		this.setUnlocalizedName(RealisticTorches.ID + "_" + name);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setTextureName(RealisticTorches.ID + ":" + name);
	}

}
