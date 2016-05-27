package com.chaosthedude.realistictorches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.events.MovingLightHandler;
import com.chaosthedude.realistictorches.handler.LightSourceHandler;
import com.chaosthedude.realistictorches.handler.RecipeHandler;
import com.chaosthedude.realistictorches.util.Util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = RealisticTorches.MODID, name = RealisticTorches.NAME, version = RealisticTorches.VERSION, acceptedMinecraftVersions = "[1.9,1.9.4]")

public class RealisticTorches {
	public static final String MODID = "RealisticTorches";
	public static final String NAME = "Realistic Torches";
	public static final String VERSION = "1.5.6";

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
		RecipeHandler.removeRecipe(new ItemStack(Blocks.TORCH));
		RecipeHandler.registerRecipes();
		RecipeHandler.registerOres();

		if (event.getSide() == Side.CLIENT) {
			Util.registerModels();
		}

		MinecraftForge.EVENT_BUS.register(new MovingLightHandler());

	}

	@EventHandler
	public void init(FMLPostInitializationEvent event) {
		if (ConfigHandler.removeRecipesEnabled) {
			RecipeHandler.removeRecipe(new ItemStack(Blocks.TORCH));
		}

		GameRegistry.addRecipe(new ItemStack(Blocks.TORCH), "x", "y", 'x', RealisticTorchesItems.glowstoneCrystal, 'y', Items.STICK);

		LightSourceHandler.registerLightSources();
	}

}