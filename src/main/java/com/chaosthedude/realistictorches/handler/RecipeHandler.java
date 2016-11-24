package com.chaosthedude.realistictorches.handler;

import java.util.List;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.items.RealisticTorchesItems;

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
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RealisticTorchesBlocks.torchUnlit, 4), "C", "S", 'C', Items.COAL, 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RealisticTorchesBlocks.torchUnlit, 4), "C", "S", 'C', new ItemStack(Items.COAL, 1, 1), 'S', "stickWood"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RealisticTorchesBlocks.torchUnlit, 4), "C", "S", 'C', "itemCharcoalSugar", 'S', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RealisticTorchesItems.glowstoneCrystal, 1), " G ", "GCG", " G ", 'G', Items.GLOWSTONE_DUST, 'C', "itemCharcoalSugar"));

		GameRegistry.addShapelessRecipe(new ItemStack(RealisticTorchesBlocks.torchLit, 1), new ItemStack(RealisticTorchesBlocks.torchUnlit, 1), new ItemStack(RealisticTorchesItems.matchbox, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addShapelessRecipe(new ItemStack(RealisticTorchesItems.matchbox, 1), new ItemStack(RealisticTorchesItems.matchbox), new ItemStack(RealisticTorchesItems.matchbox));

		GameRegistry.addShapedRecipe(new ItemStack(RealisticTorchesItems.glowstoneCrystal, 1), " G ", "GCG", " G ", 'G', Items.GLOWSTONE_DUST, 'C', Items.COAL);
		GameRegistry.addShapedRecipe(new ItemStack(RealisticTorchesItems.glowstoneCrystal, 1), " G ", "GCG", " G ", 'G', Items.GLOWSTONE_DUST, 'C', new ItemStack(Items.COAL, 1, 1));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RealisticTorchesItems.matchbox, 1), "PPP", "SSS", 'P', Items.PAPER, 'S', "slabWood"));
	}

	public static void registerOres() {
		if (ConfigHandler.oreDictionaryEnabled) {
			OreDictionary.registerOre("blockTorch", RealisticTorchesBlocks.torchLit);
			OreDictionary.registerOre("blockTorch", Blocks.TORCH);
		}
	}

	public static void removeRecipe(ItemStack s) {
		int recipeCount = 0;
		final List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipeList.size(); i++) {
			final IRecipe currentRecipe = recipeList.get(i);
			final ItemStack output = currentRecipe.getRecipeOutput();
			if (output != null && output.getItem() == s.getItem()) {
				recipeList.remove(i);
				recipeCount++;
			}
		}

		RealisticTorches.logger.info("Removed " + recipeCount + " torch recipe" + (recipeCount > 1 ? "s." : "."));
	}

}
