package com.chaosthedude.realistictorches;

import com.chaosthedude.realistictorches.blocks.BlockMovingLightSource;
import com.chaosthedude.realistictorches.blocks.BlockTorchLit;
import com.chaosthedude.realistictorches.blocks.BlockTorchSmoldering;
import com.chaosthedude.realistictorches.blocks.BlockTorchUnlit;
import com.chaosthedude.realistictorches.blocks.te.TEMovingLightSource;
import com.chaosthedude.realistictorches.blocks.te.TETorch;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RealisticTorchesBlocks {

	public static BlockTorchUnlit torchUnlit;
	public static BlockTorchLit torchLit;
	public static BlockTorchSmoldering torchSmoldering;
	public static BlockMovingLightSource movingLightSource;

	public static void register() {
		torchUnlit = registerBlock(new BlockTorchUnlit(), BlockTorchUnlit.NAME);
		torchLit = registerBlock(new BlockTorchLit(), BlockTorchLit.NAME);
		torchSmoldering = registerBlock(new BlockTorchSmoldering(), BlockTorchSmoldering.NAME);
		movingLightSource = registerBlock(new BlockMovingLightSource(), BlockMovingLightSource.NAME);

		GameRegistry.registerTileEntity(TEMovingLightSource.class, TEMovingLightSource.NAME);
		GameRegistry.registerTileEntity(TETorch.class, TETorch.NAME);
	}

	protected static <T extends Block> T registerBlock(T blockType, String name) {
		T block = blockType;
		block.setRegistryName(name);
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(name));

		return block;
	}

}