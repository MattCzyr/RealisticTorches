package com.chaosthedude.realistictorches.blocks;

import com.chaosthedude.realistictorches.blocks.tileentities.TileEntityMovingLightSource;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockRegistry {
	
	public static BlockTorchUnlit torchUnlit;
	public static BlockTorchLit torchLit;
	public static BlockTorchSmoldering torchSmoldering;
	public static BlockMovingLightSource movingLightSource;
	public static TileEntityMovingLightSource tileEntityMovingLightSource;
	
	public static void mainRegistry() {
		initializeBlock();
		registerBlock();
	}
	
	public static void initializeBlock() {
		torchUnlit = new BlockTorchUnlit();
		torchLit = new BlockTorchLit();
		torchSmoldering = new BlockTorchSmoldering();
		movingLightSource = new BlockMovingLightSource();
		tileEntityMovingLightSource = new TileEntityMovingLightSource();
	}
	
	public static void registerBlock() {
		GameRegistry.registerBlock(torchUnlit, torchUnlit.name);
		GameRegistry.registerBlock(torchLit, torchLit.name);
		GameRegistry.registerBlock(torchSmoldering, torchSmoldering.name);
		GameRegistry.registerBlock(movingLightSource, "MovingLightSource");
		GameRegistry.registerTileEntity(com.chaosthedude.realistictorches.blocks.tileentities.TileEntityMovingLightSource.class, "TileEntityMovingLightSource");
	}
	
}