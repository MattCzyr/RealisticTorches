package com.chaosthedude.realistictorches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.events.MovingLightHandler;
import com.chaosthedude.realistictorches.handler.LightSourceHandler;
import com.chaosthedude.realistictorches.handler.RecipeHandler;
import com.chaosthedude.realistictorches.util.Util;
import com.chaosthedude.realistictorches.worldgen.TorchGenerator;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = RealisticTorches.MODID, name = RealisticTorches.NAME, version = RealisticTorches.VERSION, acceptedMinecraftVersions = "[1.10,1.10.2]")

public class RealisticTorches {
	public static final String MODID = "RealisticTorches";
	public static final String NAME = "Realistic Torches";
	public static final String VERSION = "1.6.0";

	public static final Logger logger = LogManager.getLogger(MODID);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		RealisticTorchesBlocks.register();
		RealisticTorchesItems.register();

		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
		ConfigHandler.printConfigInfo();
		
		GameRegistry.registerWorldGenerator(new TorchGenerator(), 0);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		RecipeHandler.removeRecipe(new ItemStack(Blocks.TORCH));
		RecipeHandler.registerRecipes();
		RecipeHandler.registerOres();

		if (event.getSide() == Side.CLIENT) {
			Util.registerModels();
		}

		MinecraftForge.EVENT_BUS.register(new MovingLightHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (ConfigHandler.removeRecipesEnabled) {
			RecipeHandler.removeRecipe(new ItemStack(Blocks.TORCH));
		}

		GameRegistry.addRecipe(new ItemStack(Blocks.TORCH), "x", "y", 'x', RealisticTorchesItems.glowstoneCrystal, 'y', Items.STICK);

		LightSourceHandler.registerLightSources();
	}

}