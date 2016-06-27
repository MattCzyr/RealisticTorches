package com.chaosthedude.realistictorches;

import com.chaosthedude.realistictorches.blocks.BlockMovingLightSource;
import com.chaosthedude.realistictorches.blocks.BlockTorchLit;
import com.chaosthedude.realistictorches.blocks.BlockTorchSmoldering;
import com.chaosthedude.realistictorches.blocks.BlockTorchUnlit;
import com.chaosthedude.realistictorches.blocks.te.TEMovingLightSource;
import com.chaosthedude.realistictorches.blocks.te.TETorch;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		registerBlock(torchUnlit, torchUnlit.NAME);
		registerBlock(torchLit, torchLit.NAME);
		registerBlock(torchSmoldering, torchSmoldering.NAME);
		registerBlock(movingLightSource, movingLightSource.NAME);

		GameRegistry.registerTileEntity(TEMovingLightSource.class, teMovingLightSource.NAME);
		GameRegistry.registerTileEntity(TETorch.class, teTorch.NAME);
	}

	private static void registerBlock(Block block, String name) {
		block.setRegistryName(name);
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block), block.getRegistryName());
	}

}