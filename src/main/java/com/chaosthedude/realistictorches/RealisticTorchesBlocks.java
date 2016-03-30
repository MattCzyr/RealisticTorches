package com.chaosthedude.realistictorches;

import com.chaosthedude.realistictorches.blocks.BlockMovingLightSource;
import com.chaosthedude.realistictorches.blocks.BlockTorchLit;
import com.chaosthedude.realistictorches.blocks.BlockTorchSmoldering;
import com.chaosthedude.realistictorches.blocks.BlockTorchUnlit;
import com.chaosthedude.realistictorches.blocks.te.TEMovingLightSource;
import com.chaosthedude.realistictorches.blocks.te.TETorch;

import cpw.mods.fml.common.registry.GameRegistry;

public class RealisticTorchesBlocks {

	public static BlockTorchUnlit torchUnlit;
	public static BlockTorchLit torchLit;
	public static BlockTorchSmoldering torchSmoldering;
	public static BlockMovingLightSource movingLightSource;

	public static TEMovingLightSource teMovingLightSource;
	public static TETorch teTorch;

	public static void init() {
		torchUnlit = new BlockTorchUnlit();
		torchLit = new BlockTorchLit();
		torchSmoldering = new BlockTorchSmoldering();
		movingLightSource = new BlockMovingLightSource();

		teMovingLightSource = new TEMovingLightSource();
		teTorch = new TETorch();
	}

	public static void register() {
		GameRegistry.registerBlock(torchUnlit, torchUnlit.NAME);
		GameRegistry.registerBlock(torchLit, torchLit.NAME);
		GameRegistry.registerBlock(torchSmoldering, torchSmoldering.NAME);
		GameRegistry.registerBlock(movingLightSource, movingLightSource.NAME);

		GameRegistry.registerTileEntity(TEMovingLightSource.class, teMovingLightSource.NAME);
		GameRegistry.registerTileEntity(TETorch.class, teTorch.NAME);
	}

}