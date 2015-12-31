package com.chaosthedude.realistictorches;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.events.MovingLightHandler;
import com.chaosthedude.realistictorches.events.TorchDropHandler;
import com.chaosthedude.realistictorches.util.LightSources;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = RealisticTorches.MODID, name = RealisticTorches.NAME, version = RealisticTorches.VERSION, acceptedMinecraftVersions = "[1.7.10]")

public class RealisticTorches {

	public static final String MODID = "RealisticTorches";
	public static final String NAME = "Realistic Torches";
	public static final String VERSION = "1.5.0";

	public static final Logger logger = LogManager.getLogger(MODID);

	@EventHandler
	public void init(FMLPreInitializationEvent event) {
		RealisticTorchesBlocks.mainRegistry();
		RealisticTorchesItems.mainRegistry();

		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

		logger.info("Torch burnout rate: " + ConfigHandler.torchBurnout + " ticks (" + (float) (ConfigHandler.torchBurnout / 1200) + " minutes)");

		if (ConfigHandler.handheldLightEnabled) {
			logger.info("Handheld light sources are enabled.");
		} else {
			logger.info("Handheld light sources are disabled.");
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (ConfigHandler.oreDictionaryEnabled) {
			OreDictionary.registerOre("blockTorch", RealisticTorchesBlocks.torchLit);
			OreDictionary.registerOre("blockTorch", Blocks.torch);
		}

		for (ItemStack stick : OreDictionary.getOres("stickWood")) {
			GameRegistry.addRecipe(new ItemStack(RealisticTorchesBlocks.torchUnlit, 4), "x", "y", 'x', Items.coal, 'y', stick);
			GameRegistry.addRecipe(new ItemStack(RealisticTorchesBlocks.torchUnlit, 4), "x", "y", 'x', new ItemStack(Items.coal, 1, 1), 'y', stick);
		}

		for (ItemStack coal : OreDictionary.getOres("itemCharcoalSugar")) {
			for (ItemStack stick : OreDictionary.getOres("stickWood")) {
				GameRegistry.addRecipe(new ItemStack(RealisticTorchesBlocks.torchUnlit, 4), "x", "y", 'x', coal, 'y', stick);
			}
			GameRegistry.addShapedRecipe(new ItemStack(RealisticTorchesItems.glowstoneCrystal, 1), " x ", "xyx", " x ", 'x', Items.glowstone_dust, 'y', coal);
		}

		GameRegistry.addShapelessRecipe(new ItemStack(RealisticTorchesBlocks.torchLit, 1), new ItemStack(RealisticTorchesBlocks.torchUnlit, 1), new ItemStack(RealisticTorchesItems.matchbox, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addShapelessRecipe(new ItemStack(RealisticTorchesItems.matchbox, 1), new ItemStack(RealisticTorchesItems.matchbox), new ItemStack(RealisticTorchesItems.matchbox));

		GameRegistry.addShapedRecipe(new ItemStack(RealisticTorchesItems.glowstoneCrystal, 1), " x ", "xyx", " x ", 'x', Items.glowstone_dust, 'y', Items.coal);
		GameRegistry.addShapedRecipe(new ItemStack(RealisticTorchesItems.glowstoneCrystal, 1), " x ", "xyx", " x ", 'x', Items.glowstone_dust, 'y', new ItemStack(Items.coal, 1, 1));

		MinecraftForge.EVENT_BUS.register(new TorchDropHandler());
		FMLCommonHandler.instance().bus().register(new MovingLightHandler());
	}

	@EventHandler
	public void init(FMLPostInitializationEvent event) {
		removeRecipe(new ItemStack(RealisticTorchesItems.matchbox));

		for (ItemStack slab : OreDictionary.getOres("slabWood")) {
			GameRegistry.addRecipe(new ItemStack(RealisticTorchesItems.matchbox, 1), "xxx", "yyy", 'x', Items.paper, 'y', slab);
		}

		if (ConfigHandler.removeRecipesEnabled) {
			removeRecipe(new ItemStack(Blocks.torch));
		}

		GameRegistry.addRecipe(new ItemStack(Blocks.torch, 4), "x", "y", 'x', RealisticTorchesItems.glowstoneCrystal, 'y', Items.stick);

		int lightSources = 0;
		
		for (Object i : GameData.getBlockRegistry()) {
			Block block = (Block) i;
			if (block.getLightValue() > 0) {
				LightSources.lightSourceList.add(Item.getItemFromBlock(block));
				lightSources++;
			}
		}
		
		logger.info("Registered " + lightSources + " blocks as light sources.");
	}

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
			logger.info("Removed " + recipeCount + " torch recipe.");
		} else {
			logger.info("Removed " + recipeCount + " torch recipes.");
		}
	}

}