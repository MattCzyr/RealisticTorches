package com.chaosthedude.realistictorches.config;

import java.io.File;

import com.chaosthedude.realistictorches.RealisticTorches;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

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

		FMLCommonHandler.instance().bus().register(new ChangeListener());
	}

	public static void init() {
		String comment;

		comment = "The amount of time until a torch burns out, in ticks (20 ticks = 1 second). Setting this to a negative value will disable torch burnout.";
		torchBurnout = loadInt("torch.burnoutTime", comment, torchBurnout);

		comment = "The durability of the matchbox. Setting this to a negative value will result in unlimited uses.";
		matchboxDurability = loadInt("matchbox.durability", comment, matchboxDurability);

		comment = "Set this to false to disable certain blocks and items emitting light when held.";
		handheldLightEnabled = loadBool("handheldLight.enabled", comment, handheldLightEnabled);

		comment = "Set this to false to disable the removal of other mods' recipes for the vanilla torch.";
		removeRecipesEnabled = loadBool("recipes.removeModded", comment, removeRecipesEnabled);

		comment = "Set this to true to enable unlit torch particles.";
		unlitParticlesEnabled = loadBool("unlitTorch.particles", comment, unlitParticlesEnabled);

		comment = "Set this to true to register both the lit torch and the vanilla torch in the Ore Dictionary under blockTorch.";
		oreDictionaryEnabled = loadBool("torch.oreDictionary", comment, oreDictionaryEnabled);

		comment = "Set this to true to make lit torches disappear after they are extinguished, rather than turning into unlit torches.";
		noRelightEnabled = loadBool("torch.noRelight", comment, noRelightEnabled);

		if (config.hasChanged()) {
			config.save();
		}
	}

	public static int loadInt(String name, String comment, int def) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, name, def);
		prop.comment = comment;
		int val = prop.getInt(def);
		if (val == 0) {
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
			if (eventArgs.modID.equals(RealisticTorches.MODID)) {
				init();
			}
		}
	}

}
