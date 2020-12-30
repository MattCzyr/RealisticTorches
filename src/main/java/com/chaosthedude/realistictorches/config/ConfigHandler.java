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
	public static int lightSourceRegistryThreshold = 1;
	public static int handheldLightUpdateTicks = 10;

	public static boolean handheldLightEnabled = true;
	public static boolean unlitParticlesEnabled = false;
	public static boolean oreDictionaryEnabled = false;
	public static boolean noRelightEnabled = false;
	public static boolean registerLightSourceBlocks = true;
	public static boolean matchboxCreatesFire = false;
	public static boolean generateLitTorches = true;
	public static boolean vanillaTorchDropsUnlit = false;

	public static String[] lightSourceItems = {
			"minecraft:lava_bucket",
			"minecraft:glowstone_dust",
			"minecraft:blaze_powder",
			"minecraft:blaze_rod",
			"realistictorches:glowstone_crystal"
	};

	public static String[] lightSourceBlocks = {};

	public static String[] removeRecipes = {
			"minecraft:torch",
			"tconstruct:stone_torch"
	};

	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		config.load();
		init();

		MinecraftForge.EVENT_BUS.register(new ChangeListener());
	}

	public static void init() {
		String comment;

		comment = "The amount of time until a torch burns out, in ticks (20 ticks = 1 second). Setting this to a negative value will disable torch burnout.";
		torchBurnout = loadInt("torch.burnoutTime", comment, "general", torchBurnout);

		comment = "The durability of the matchbox. Setting this to a negative value will result in unlimited uses.";
		matchboxDurability = loadInt("matchbox.durability", comment, "general", matchboxDurability);

		comment = "The threshold for which all blocks with a light level above will be registered as handheld light sources. Acceptable values are between 0 and 15, inclusive.";
		lightSourceRegistryThreshold = loadInt("handheldLight.registry.threshold", comment, "light_sources", lightSourceRegistryThreshold);

		comment = "Set this to false to disable certain blocks and items emitting light when held.";
		handheldLightEnabled = loadBool("handheldLight.enabled", comment, "light_sources", handheldLightEnabled);

		comment = "The number of ticks between handheld light updates. Lower values cause more updates and can result in framerate issues.";
		handheldLightUpdateTicks = loadInt("handheldLight.updateTicks", comment, "light_sources", handheldLightUpdateTicks);
		if(handheldLightUpdateTicks < 1) handheldLightUpdateTicks = 1;

		comment = "Set this to true to enable unlit torch particles.";
		unlitParticlesEnabled = loadBool("unlitTorch.particles", comment, "general", unlitParticlesEnabled);

		comment = "Set this to true to register both the lit torch and the vanilla torch in the Ore Dictionary under blockTorch.";
		oreDictionaryEnabled = loadBool("torch.oreDictionary", comment, "general", oreDictionaryEnabled);

		comment = "Set this to true to make lit torches disappear after they are extinguished, rather than turning into unlit torches.";
		noRelightEnabled = loadBool("torch.noRelight", comment, "general", noRelightEnabled);

		comment = "Set this to false to disable light emitting blocks from automatically being registered as handheld light sources.";
		registerLightSourceBlocks = loadBool("handheldLight.registerBlocks", comment, "light_sources", registerLightSourceBlocks);

		comment = "Set this to true to enable matchboxes lighting fires in the world like flint and steel.";
		matchboxCreatesFire = loadBool("matchbox.createsFire", comment, "general", matchboxCreatesFire);

		comment = "Set this to false to disable vanilla torches being replaced with lit torches during world generation.";
		generateLitTorches = loadBool("world.generateLitTorches", comment, "general", generateLitTorches);

		comment = "Set this to true to enable vanilla torches dropping unlit torches when harvested.";
		vanillaTorchDropsUnlit = loadBool("torch.vanilla.dropUnlit", comment, "general", vanillaTorchDropsUnlit);

		comment = "A list of items that will emit light when held, if handheldLight.enabled is set to true.";
		lightSourceItems = loadStringArray("lightSources.items", comment, "light_sources", lightSourceItems);

		comment = "A list of blocks that will emit light when held, if handheldLight.enabled is set to true. This list will be used ONLY if handheldLight.registerBlocks is set to false.";
		lightSourceBlocks = loadStringArray("lightSources.blocks", comment, "light_sources", lightSourceBlocks);

		comment = "A list blocks and items of remove the recipes of.";
		removeRecipes = loadStringArray("recipes.removeRecipes", comment, "remove_recipes", removeRecipes);

		if (config.hasChanged()) {
			config.save();
		}
	}

	public static int loadInt(String name, String comment, String category, int def) {
		Property prop = config.get(category, name, def);
		prop.setComment(comment);
		int val = prop.getInt(def);
		if (val == 0) {
			val = def;
			prop.set(def);
		}

		return val;
	}

	public static boolean loadBool(String name, String comment, String category, boolean def) {
		Property prop = config.get(category, name, def);
		prop.setComment(comment);
		return prop.getBoolean(def);
	}

	public static String[] loadStringArray(String name, String comment, String category, String[] def) {
		Property prop = config.get(category, name, def);
		prop.setComment(comment);
		return prop.getStringList();
	}

	public static void printConfigInfo() {
		RealisticTorches.logger.info("Torch burnout rate: " + ConfigHandler.torchBurnout + " ticks (" + ConfigHandler.torchBurnout / 1200 + " minutes)");

		if (ConfigHandler.handheldLightEnabled == true) {
			RealisticTorches.logger.info("Handheld light sources are enabled.");
		} else {
			RealisticTorches.logger.info("Handheld light sources are disabled.");
		}
	}

	public static class ChangeListener {
		@SubscribeEvent
		public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
			if (eventArgs.getModID().equals(RealisticTorches.MODID)) {
				init();
			}
		}
	}

}
