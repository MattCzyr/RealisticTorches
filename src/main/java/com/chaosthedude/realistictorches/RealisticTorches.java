package com.chaosthedude.realistictorches;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chaosthedude.realistictorches.blocks.BlockRegistry;
import com.chaosthedude.realistictorches.handlers.TorchHandler;
import com.chaosthedude.realistictorches.handlers.ConfigHandler;
import com.chaosthedude.realistictorches.handlers.MovingLightHandler;
import com.chaosthedude.realistictorches.handlers.TorchDropHandler;
import com.chaosthedude.realistictorches.items.ItemRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = RealisticTorches.ID, name = RealisticTorches.NAME, version = RealisticTorches.VERSION, acceptedMinecraftVersions = "[1.7.10]")

public class RealisticTorches {
	public static final String ID = "RealisticTorches";
	public static final String NAME = "Realistic Torches";
	public static final String VERSION = "1.2.1";
	
	public static final Logger logger = LogManager.getLogger(ID);
	
	@EventHandler
	public void init(FMLPreInitializationEvent event) {
		BlockRegistry.mainRegistry();
		ItemRegistry.mainRegistry();
		
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
		logger.info("Torch burnout rate: " + ConfigHandler.torchBurnout + " ticks (" + (float)(ConfigHandler.torchBurnout / 1200) + " minutes)");
		if(ConfigHandler.handheldLightEnabled == true) {
			logger.info("Handheld light sources are enabled.");
		}
		else {
			logger.info("Handheld light sources are disabled.");
		}
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (ConfigHandler.oreDictionaryEnabled == true) {
			OreDictionary.registerOre("blockTorch", BlockRegistry.torchLit);
			OreDictionary.registerOre("blockTorch", Blocks.torch);
		}
		
		for (ItemStack stick : OreDictionary.getOres("stickWood")) {
			GameRegistry.addRecipe(new ItemStack(BlockRegistry.torchUnlit, 4), "x", "y", 'x', Items.coal, 'y', (stick));
			GameRegistry.addRecipe(new ItemStack(BlockRegistry.torchUnlit, 4), "x", "y", 'x', new ItemStack(Items.coal, 1, 1), 'y', (stick));
		}
		
		for (ItemStack coal : OreDictionary.getOres("itemCharcoalSugar")) {
			for (ItemStack stick : OreDictionary.getOres("stickWood")) {
				GameRegistry.addRecipe(new ItemStack(BlockRegistry.torchUnlit, 4), "x", "y", 'x', (coal), 'y', (stick));
			}
			GameRegistry.addShapedRecipe(new ItemStack(ItemRegistry.glowstoneCrystal, 1), " x ", "xyx", " x ", 'x', Items.glowstone_dust, 'y', (coal));
		}
		
		for (ItemStack slab : OreDictionary.getOres("slabWood")) {
			GameRegistry.addRecipe(new ItemStack(ItemRegistry.matchbox, 1), "xxx", "yyy", 'x', Items.paper, 'y', (slab));
		}
		
		GameRegistry.addShapelessRecipe(new ItemStack(BlockRegistry.torchLit, 1), new ItemStack(BlockRegistry.torchUnlit, 1), new ItemStack(ItemRegistry.matchbox, 1, OreDictionary.WILDCARD_VALUE));
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemRegistry.glowstoneCrystal, 1), " x ", "xyx", " x ", 'x', Items.glowstone_dust, 'y', Items.coal);
		GameRegistry.addShapedRecipe(new ItemStack(ItemRegistry.glowstoneCrystal, 1), " x ", "xyx", " x ", 'x', Items.glowstone_dust, 'y', new ItemStack(Items.coal, 1, 1));
		
		MinecraftForge.EVENT_BUS.register(new TorchDropHandler());
		//MinecraftForge.EVENT_BUS.register(new TorchHandler());
		
		if(ConfigHandler.handheldLightEnabled == true) {
			FMLCommonHandler.instance().bus().register(new MovingLightHandler());
		}		
	}
	
	@EventHandler
	public void init(FMLPostInitializationEvent event) {
		if (ConfigHandler.removeRecipesEnabled == true) {
			removeRecipe(new ItemStack(Blocks.torch));
		}
		GameRegistry.addRecipe(new ItemStack(Blocks.torch, 4), "x", "y", 'x', ItemRegistry.glowstoneCrystal, 'y', Items.stick);
	}
	
	public static void removeRecipe(ItemStack s)
	{
		 int recipeCount = 0;
		
	     List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
	     
	     for (int i = 0; i < recipeList.size(); i++) {
	          IRecipe currentRecipe = recipeList.get(i);
	          ItemStack output = currentRecipe.getRecipeOutput();
	          if (output != null) {
	        	  Item a = output.getItem();
	              Item b = s.getItem();
	              
	              if (a == b) {
	            	  recipeList.remove(i);
	            	  recipeCount++;
	              }
	          }
	     }
	     logger.info("Successfully removed " + recipeCount + " torch recipe(s).");
	}
	
}