package com.chaosthedude.realistictorches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.events.MovingLightHandler;
import com.chaosthedude.realistictorches.util.LightSources;
import com.chaosthedude.realistictorches.util.Util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
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

@Mod(modid = RealisticTorches.MODID, name = RealisticTorches.NAME, version = RealisticTorches.VERSION, acceptedMinecraftVersions = "[1.8.9]")

public class RealisticTorches {
	public static final String MODID = "RealisticTorches";
	public static final String NAME = "Realistic Torches";
	public static final String VERSION = "1.5.4";

	public static final Logger logger = LogManager.getLogger(MODID);

	@EventHandler
	public void init(FMLPreInitializationEvent event) {
		RealisticTorchesBlocks.initialize();
		RealisticTorchesBlocks.register();

		RealisticTorchesItems.initialize();
		RealisticTorchesItems.register();

		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

		logger.info("Torch burnout rate: " + ConfigHandler.torchBurnout + " ticks (" + ConfigHandler.torchBurnout / 1200 + " minutes)");

		if (ConfigHandler.handheldLightEnabled == true) {
			logger.info("Handheld light sources are enabled.");
		} else {
			logger.info("Handheld light sources are disabled.");
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
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

		for (ItemStack slab : OreDictionary.getOres("slabWood")) {
			GameRegistry.addRecipe(new ItemStack(RealisticTorchesItems.matchbox, 1), "xxx", "yyy", 'x', Items.paper, 'y', slab);
		}

		GameRegistry.addShapelessRecipe(new ItemStack(RealisticTorchesBlocks.torchLit, 1), new ItemStack(RealisticTorchesBlocks.torchUnlit, 1), new ItemStack(RealisticTorchesItems.matchbox, 1, OreDictionary.WILDCARD_VALUE));

		GameRegistry.addShapedRecipe(new ItemStack(RealisticTorchesItems.glowstoneCrystal, 1), " x ", "xyx", " x ", 'x', Items.glowstone_dust, 'y', Items.coal);
		GameRegistry.addShapedRecipe(new ItemStack(RealisticTorchesItems.glowstoneCrystal, 1), " x ", "xyx", " x ", 'x', Items.glowstone_dust, 'y', new ItemStack(Items.coal, 1, 1));

		if (event.getSide() == Side.CLIENT) {
			RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

			renderItem.getItemModelMesher().register(RealisticTorchesItems.matchbox, 0, new ModelResourceLocation(this.MODID + ":" + RealisticTorchesItems.matchbox.name, "inventory"));
			renderItem.getItemModelMesher().register(RealisticTorchesItems.glowstoneCrystal, 0, new ModelResourceLocation(this.MODID + ":GlowstoneCrystal", "inventory"));

			renderItem.getItemModelMesher().register(Item.getItemFromBlock(RealisticTorchesBlocks.torchLit), 0, new ModelResourceLocation(this.MODID + ":" + RealisticTorchesBlocks.torchLit.name, "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(RealisticTorchesBlocks.torchSmoldering), 0, new ModelResourceLocation(this.MODID + ":" + RealisticTorchesBlocks.torchSmoldering.name, "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(RealisticTorchesBlocks.torchUnlit), 0, new ModelResourceLocation(this.MODID + ":" + RealisticTorchesBlocks.torchUnlit.name, "inventory"));
		}

		if (ConfigHandler.oreDictionaryEnabled) {
			OreDictionary.registerOre("blockTorch", RealisticTorchesBlocks.torchLit);
			OreDictionary.registerOre("blockTorch", Blocks.torch);
		}

		MinecraftForge.EVENT_BUS.register(new MovingLightHandler());

	}

	@EventHandler
	public void init(FMLPostInitializationEvent event) {
		if (ConfigHandler.removeRecipesEnabled) {
			Util.removeRecipe(new ItemStack(Blocks.torch));
		}

		GameRegistry.addRecipe(new ItemStack(Blocks.torch), "x", "y", 'x', RealisticTorchesItems.glowstoneCrystal, 'y', Items.stick);

		LightSources.registerLightSources();
	}

}