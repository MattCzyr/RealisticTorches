package com.chaosthedude.realistictorches.config;

import java.io.File;

import com.chaosthedude.realistictorches.RealisticTorches;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {

	public static Configuration config;

	public static int torchBurnout = 72000;
	public static int matchboxDurability = 64;

	public static boolean handheldLightEnabled = true;
	public static boolean removeRecipesEnabled = true;
	public static boolean unlitParticlesEnabled = false;
	public static boolean oreDictionaryEnabled = false;
	public static boolean noRelightEnabled = false;

	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		config.load();
		init();

		MinecraftForge.EVENT_BUS.register(new ChangeListener());
	}

	public static void init() {
		String comment;

		comment = "The amount of time until a torch burns out, in ticks (20 ticks = 1 second). Set this to a negative value to make torches never burn out. Default: 72000";
		torchBurnout = loadInt("Torch Burnout Time", comment, torchBurnout);

		comment = "The durability of the matchbox (the number of torches that it can light before it breaks). Set this to a negative value for unlimited uses. Default: 64";
		matchboxDurability = loadInt("Lighter Durability", comment, matchboxDurability);

		comment = "Should certain blocks and items emit light when held? This is still experimental and may cause performance issues. Default: false";
		handheldLightEnabled = loadBool("Handheld Light", comment, handheldLightEnabled);

		comment = "Should other mods' recipes for the vanilla torch be removed? Default: true";
		removeRecipesEnabled = loadBool("Remove Recipes", comment, removeRecipesEnabled);

		comment = "Should unlit torches emit smoke particles? Default: false";
		unlitParticlesEnabled = loadBool("Unlit Particles", comment, unlitParticlesEnabled);

		comment = "Should lit torches be registered in the Ore Dictionary as vanilla torch variants? Default: false";
		oreDictionaryEnabled = loadBool("Vanilla Torch Variants", comment, oreDictionaryEnabled);

		comment = "Should torches disappear after they are extinguished and be unable to be relit? Default: false";
		noRelightEnabled = loadBool("No Torch Relight", comment, noRelightEnabled);

		if (config.hasChanged())
			config.save();
	}

	public static int loadInt(String name, String comment, int def) {
		Property prop = config.get("Integer", name, def);
		prop.comment = comment;
		int val = prop.getInt(def);
		if (val == 0) {
			val = def;
			prop.set(def);
		}

		return val;

	}

	public static boolean loadBool(String name, String comment, boolean def) {
		Property prop = config.get("Boolean", name, def);
		prop.comment = comment;
		return prop.getBoolean(def);
	}

	public static class ChangeListener {

		@SubscribeEvent
		public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
			if (eventArgs.modID.equals(RealisticTorches.MODID))
				init();
		}
	}

}
