package com.chaosthedude.realistictorches.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import com.chaosthedude.realistictorches.RealisticTorches;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	
	public static Configuration config;
	
	public static int torchBurnout = 72000;
	public static int matchboxDurability = 64;
	
	public static boolean handheldLightEnabled = false;
	public static boolean removeRecipesEnabled = true;
	public static boolean unlitParticlesEnabled = false;
	public static boolean oreDictionaryEnabled = false;
	public static boolean noRelightEnabled = false;
	
	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);
		
		config.load();
		init();
		
		FMLCommonHandler.instance().bus().register(new ChangeListener());
	}
	
	public static void init() {
		String comment;
		
		comment = "The amount of time until a torch burns out, in ticks (20 ticks = 1 second). Default: 72000";
		torchBurnout = loadInt("Torch Burnout Time", comment, torchBurnout);
		
		comment = "The durability of the matchbox (the number of torches that it can light before it breaks).";
		matchboxDurability = loadInt("Lighter Durability", comment, matchboxDurability);
		
		comment = "Should certain blocks and items emit light when held? Disabled by default due to possible performance issues.";
		handheldLightEnabled = loadBool("Handheld Light", comment, handheldLightEnabled);
		
		comment = "Should other mods' recipes for the vanilla torch be removed? Enabled by default.";
		removeRecipesEnabled = loadBool("Remove Recipes", comment, removeRecipesEnabled);
		
		comment = "Should unlit torches emit smoke particles? Disabled by default.";
		unlitParticlesEnabled = loadBool("Unlit Particles", comment, unlitParticlesEnabled);
		
		comment = "Should lit torches be registered in the Ore Dictionary as vanilla torch variants? Disabled by default.";
		oreDictionaryEnabled = loadBool("Vanilla Torch Variants", comment, oreDictionaryEnabled);
		
		comment = "Should torches disappear after they are extinguished and be unable to be relit? Disabled by default.";
		noRelightEnabled = loadBool("No Torch Relight", comment, noRelightEnabled);
		
		if(config.hasChanged())
			config.save();
	}
	
	public static int loadInt(String name, String comment, int def) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, name, def);
		prop.comment = comment;
		int val = prop.getInt(def);
		if(val <= 0) {
			val = def;
			prop.set(def);
		}
		
		return val;
		
	}
	
	public static boolean loadBool(String name, String comment, boolean def) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, name, def);
		prop.comment = comment;
		return prop.getBoolean(def);
	}
	
	public static class ChangeListener {
		
		@SubscribeEvent
		public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
			if (eventArgs.modID.equals(RealisticTorches.ID))
				init();
		}
	}
	
}
