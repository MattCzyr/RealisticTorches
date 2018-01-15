package com.chaosthedude.realistictorches.blocks;

import java.util.ArrayList;
import java.util.List;

import com.chaosthedude.realistictorches.blocks.te.TEMovingLightSource;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RealisticTorchesBlocks {

	public static final List<Block> REGISTRY = new ArrayList<Block>();

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
	}

	protected static <T extends Block> T registerBlock(T blockType, String name) {
		T block = blockType;
		block.setRegistryName(name);
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(name));
		REGISTRY.add(block);

		return block;
	}

}
