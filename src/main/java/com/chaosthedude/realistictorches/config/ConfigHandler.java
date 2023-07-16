package com.chaosthedude.realistictorches.config;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ConfigHandler {

	public static final String CATEGORY_GENERAL = "general";
	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec COMMON_CONFIG;

	public static ForgeConfigSpec.IntValue torchBurnoutTime;
	public static ForgeConfigSpec.IntValue matchboxDurability;
	public static ForgeConfigSpec.BooleanValue generateLitTorches;
	public static ForgeConfigSpec.BooleanValue noRelightEnabled;
	public static ForgeConfigSpec.BooleanValue matchboxCreatesFire;
	public static ForgeConfigSpec.BooleanValue vanillaTorchesDropUnlit;
	public static ForgeConfigSpec.ConfigValue<List<String>> lightTorchItems;

	static {
		COMMON_BUILDER.comment("General Settings").push(CATEGORY_GENERAL);
		String desc;

		desc = "The amount of time until a torch burns out, in minutes. Setting this to a negative value will disable torch burnout.";
		torchBurnoutTime = COMMON_BUILDER.comment(desc).defineInRange("torchBurnoutTime", 60, -1, 2880);

		desc = "The durability of the matchbox. Setting this to a negative value will result in unlimited uses.";
		matchboxDurability = COMMON_BUILDER.comment(desc).defineInRange("matchboxDurability", 64, -1, 512);

		//desc = "Determines whether vanilla torches will be replaced with lit torches during world generation.";
		//generateLitTorches = COMMON_BUILDER.comment(desc).define("generateLitTorches", true);

		desc = "Determines whether lit torches disappear after they are extinguished, rather than turning into unlit torches.";
		noRelightEnabled = COMMON_BUILDER.comment(desc).define("torchNoRelight", false);

		desc = "Determines whether matchboxes can light fires in the world like flint and steel.";
		matchboxCreatesFire = COMMON_BUILDER.comment(desc).define("matchboxCreatesFire", false);

		desc = "Determines whether vanilla torches drop unlit torches when broken.";
		vanillaTorchesDropUnlit = COMMON_BUILDER.comment(desc).define("vanillaTorchesDropUnlit", true);
		
		desc = "The list of items that should be allowed to light torches placed in the world, besides the matchbox and flint and steel. Ex: [\"minecraft:lava_bucket\"]";
		lightTorchItems = COMMON_BUILDER.comment(desc).define("lightTorchItems", new ArrayList<String>());
		
		COMMON_BUILDER.pop();

		COMMON_CONFIG = COMMON_BUILDER.build();
	}

	public static void loadConfig(ForgeConfigSpec spec, Path path) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();

		configData.load();
		spec.setConfig(configData);
	}

}
