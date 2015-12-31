package com.chaosthedude.realistictorches.util;

import java.util.ArrayList;
import java.util.List;

import com.chaosthedude.realistictorches.RealisticTorchesItems;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class LightSources {

	public static List<Item> lightSourceList = new ArrayList<Item>() {
		{
			add(Items.lava_bucket);
			add(Items.glowstone_dust);
			add(Items.blaze_powder);
			add(Items.blaze_rod);
			add(RealisticTorchesItems.glowstoneCrystal);
		}
	};

	public static boolean isLightSource(Item item) {
		return lightSourceList.contains(item);
	}

}
