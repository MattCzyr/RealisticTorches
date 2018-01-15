package com.chaosthedude.realistictorches.handler;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeHandler {
	public static void removeRecipes() {
		for (String name : ConfigHandler.removeRecipes) {
			final Block block = Block.getBlockFromName(name);
			if (block != null) {
				removeRecipe(new ItemStack(block));
			} else {
				final Item item = Item.REGISTRY.getObject(new ResourceLocation(name));
				if (item != null) {
					removeRecipe(new ItemStack(item));
				}
			}
		}
	}

	public static void removeRecipe(ItemStack s) {
		int recipeCount = 0;
		for (IRecipe currentRecipe: ForgeRegistries.RECIPES) {
			final ItemStack output = currentRecipe.getRecipeOutput();
			if (output != null && output.getItem() == s.getItem() && !currentRecipe.getRegistryName().toString().contains(RealisticTorches.MODID)) {
				// Per http://www.minecraftforge.net/forum/topic/59153-112-removing-vanilla-recipes/#comment-274722
				// it is recommended to replace recipes with a "null recipe" rather than removing them.
				ForgeRegistries.RECIPES.register(new NullReplacementRecipe(currentRecipe));
				recipeCount++;
			}
		}

		RealisticTorches.logger.info("Removed " + recipeCount + " torch recipe" + (recipeCount > 1 ? "s." : "."));
	}

    public static void registerOres() {
        if (ConfigHandler.oreDictionaryEnabled) {
            OreDictionary.registerOre("blockTorch", RealisticTorchesBlocks.torchLit);
            OreDictionary.registerOre("blockTorch", Blocks.TORCH);
        }
    }
}
