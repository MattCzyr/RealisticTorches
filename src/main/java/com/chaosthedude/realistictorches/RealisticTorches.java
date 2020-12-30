package com.chaosthedude.realistictorches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.events.RealisticTorchesEvents;
import com.chaosthedude.realistictorches.handler.LightSourceHandler;
import com.chaosthedude.realistictorches.handler.RecipeHandler;
import com.chaosthedude.realistictorches.items.RealisticTorchesItems;
import com.chaosthedude.realistictorches.proxy.CommonProxy;
import com.chaosthedude.realistictorches.worldgen.TorchGenerator;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = RealisticTorches.MODID, name = RealisticTorches.NAME, version = RealisticTorches.VERSION, acceptedMinecraftVersions = "[1.12.2]")

public class RealisticTorches {

	public static final String MODID = "realistictorches";
	public static final String NAME = "Realistic Torches";
	public static final String VERSION = "2.1.2";

	public static final Logger logger = LogManager.getLogger(MODID);

	@SidedProxy(clientSide = "com.chaosthedude.realistictorches.proxy.ClientProxy", serverSide = "com.chaosthedude.realistictorches.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
		ConfigHandler.printConfigInfo();
		
		RealisticTorchesBlocks.register();
		RealisticTorchesItems.register();
		RecipeHandler.registerOres();

		proxy.registerModels();

		GameRegistry.registerWorldGenerator(new TorchGenerator(), 0);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new RealisticTorchesEvents());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		RecipeHandler.removeRecipes();
		LightSourceHandler.registerLightSources();
	}

}
