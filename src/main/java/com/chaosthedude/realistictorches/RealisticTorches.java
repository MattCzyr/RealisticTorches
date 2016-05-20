package com.chaosthedude.realistictorches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.events.MovingLightHandler;
import com.chaosthedude.realistictorches.handler.LightSourceHandler;
import com.chaosthedude.realistictorches.handler.RecipeHandler;
import com.chaosthedude.realistictorches.handler.TorchHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = RealisticTorches.MODID, name = RealisticTorches.NAME, version = RealisticTorches.VERSION, acceptedMinecraftVersions = "[1.7.10]")

public class RealisticTorches {

	public static final String MODID = "RealisticTorches";
	public static final String NAME = "Realistic Torches";
	public static final String VERSION = "1.5.5";

	public static final Logger logger = LogManager.getLogger(MODID);

	@EventHandler
	public void init(FMLPreInitializationEvent event) {
		RealisticTorchesBlocks.init();
		RealisticTorchesBlocks.register();

		RealisticTorchesItems.init();
		RealisticTorchesItems.register();

		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
		ConfigHandler.printConfigInfo();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		RecipeHandler.removeRecipe(new ItemStack(Blocks.torch));
		
		RecipeHandler.registerRecipes();
		RecipeHandler.registerOres();

		FMLCommonHandler.instance().bus().register(new MovingLightHandler());
	}

	@EventHandler
	public void init(FMLPostInitializationEvent event) {
		if (ConfigHandler.removeRecipesEnabled) {
			RecipeHandler.removeRecipe(new ItemStack(Blocks.torch));
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.torch, 4), "G", "S", 'G', RealisticTorchesItems.glowstoneCrystal, 'S', "stickWood"));
		LightSourceHandler.registerLightSources();
	}

}