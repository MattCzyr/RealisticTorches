package com.chaosthedude.realistictorches.handler;

import java.util.List;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.RealisticTorchesItems;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipeHandler {
	
	public static void registerRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RealisticTorchesBlocks.torchUnlit, 4), "C", "S", 'C', Items.coal, 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RealisticTorchesBlocks.torchUnlit, 4), "C", "S", 'C', new ItemStack(Items.coal, 1, 1), 'S', "stickWood"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RealisticTorchesBlocks.torchUnlit, 4), "C", "S", 'C', "itemCharcoalSugar", 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RealisticTorchesItems.glowstoneCrystal, 1), " G ", "GCG", " G ", 'G', Items.glowstone_dust, 'C', "itemCharcoalSugar"));

		GameRegistry.addShapelessRecipe(new ItemStack(RealisticTorchesBlocks.torchLit, 1), new ItemStack(RealisticTorchesBlocks.torchUnlit, 1), new ItemStack(RealisticTorchesItems.matchbox, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addShapelessRecipe(new ItemStack(RealisticTorchesItems.matchbox, 1), new ItemStack(RealisticTorchesItems.matchbox), new ItemStack(RealisticTorchesItems.matchbox));

		GameRegistry.addShapedRecipe(new ItemStack(RealisticTorchesItems.glowstoneCrystal, 1), " G ", "GCG", " G ", 'G', Items.glowstone_dust, 'C', Items.coal);
		GameRegistry.addShapedRecipe(new ItemStack(RealisticTorchesItems.glowstoneCrystal, 1), " G ", "GCG", " G ", 'G', Items.glowstone_dust, 'C', new ItemStack(Items.coal, 1, 1));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RealisticTorchesItems.matchbox, 1), "PPP", "SSS", 'P', Items.paper, 'S', "slabWood"));
	}
	
	public static void registerOres() {
		if (ConfigHandler.oreDictionaryEnabled) {
			OreDictionary.registerOre("blockTorch", RealisticTorchesBlocks.torchLit);
			OreDictionary.registerOre("blockTorch", Blocks.torch);
		}
	}
	
	public static void removeRecipe(ItemStack s) {
		int recipeCount = 0;
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();

		for (int i = 0; i < recipeList.size(); i++) {
			IRecipe currentRecipe = recipeList.get(i);
			ItemStack output = currentRecipe.getRecipeOutput();

			if (output != null && output.getItem() == s.getItem()) {
				recipeList.remove(i);
				recipeCount++;
			}
		}

		if (recipeCount == 1) {
			RealisticTorches.logger.info("Removed " + recipeCount + " torch recipe.");
		} else {
			RealisticTorches.logger.info("Removed " + recipeCount + " torch recipes.");
		}
	}

}
