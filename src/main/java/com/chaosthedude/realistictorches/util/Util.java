package com.chaosthedude.realistictorches.util;

import java.util.List;

import com.chaosthedude.realistictorches.RealisticTorches;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class Util {

	public static void removeRecipe(ItemStack s) {
		int recipeCount = 0;
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipeList.size(); i++) {
			IRecipe currentRecipe = recipeList.get(i);
			ItemStack output = currentRecipe.getRecipeOutput();
			if (output != null) {
				if (output.getItem() == s.getItem()) {
					recipeList.remove(i);
					recipeCount++;
				}
			}
		}

		if (recipeCount == 1) {
			RealisticTorches.logger.info("Removed " + recipeCount + " torch recipe.");
		} else {
			RealisticTorches.logger.info("Removed " + recipeCount + " torch recipes.");
		}
	}
	
	public static ItemStack copyStack(ItemStack stack, int n) {
		return new ItemStack(stack.getItem(), n, stack.getItemDamage());
	}

}
