package com.chaosthedude.realistictorches.blocks;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.blocks.te.TEMovingLightSource;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockMovingLightSource extends BlockAir implements ITileEntityProvider {

	public static final String NAME = "MovingLightSource";

	public BlockMovingLightSource() {
		super();
		setBlockName(RealisticTorches.MODID + "_" + NAME);
		setTickRandomly(false);
		setLightLevel(0.9F);
		setBlockBounds(0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TEMovingLightSource();
	}

}